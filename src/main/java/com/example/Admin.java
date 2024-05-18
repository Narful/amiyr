package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Admin extends Utilisateur {

   public Boolean creerCompte(String nom, String prenom, String email, String motDePasse, String role) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String sql = "INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role) VALUES (?, ?, ?, ?, ?)";
         PreparedStatement statement = connection.prepareStatement(sql);
         statement.setString(1, nom);
         statement.setString(2, prenom);
         statement.setString(3, email);
         statement.setString(4, motDePasse);
         statement.setString(5, role); // Utiliser le rôle fourni

         int rowsInserted = statement.executeUpdate();
         return rowsInserted > 0; // Si au moins une ligne a été insérée, retourne vrai, sinon retourne faux
      } catch (SQLException e) {
         System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
         return false; // Retourne faux en cas d'erreur
      }
   }

   public Boolean supprimerCompte(int idCpt) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "DELETE FROM utilisateur WHERE idCpt = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCpt);
            statement.executeUpdate();
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public Map<String, Integer> calculerPlatsLesPlusVendus(Connection connection, Date dateDebut, Date dateFin) {
      Map<String, Integer> platsVendus = new HashMap<>();
      String query = "SELECT plat.nom, COUNT(*) AS qte_vendue " +
              "FROM plat " +
              "INNER JOIN panier_plat ON plat.idPlat = panier_plat.idPlat " +
              "INNER JOIN panier ON panier_plat.idPanier = panier.idPanier " +
              "INNER JOIN commande ON panier.idPanier = commande.idPanier " +
              "WHERE commande.Creadate BETWEEN ? AND ? " +
              "GROUP BY plat.nom " +
              "ORDER BY qte_vendue DESC " +
              "LIMIT 5";

      try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setDate(1, new java.sql.Date(dateDebut.getTime()));
         statement.setDate(2, new java.sql.Date(dateFin.getTime()));
         ResultSet resultSet = statement.executeQuery();
         while (resultSet.next()) {
            String plat = resultSet.getString("nom");
            int qteVendue = resultSet.getInt("qte_vendue");
            platsVendus.put(plat, qteVendue);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return platsVendus;
   }

   public Map<String, Integer> calculerCommandesLivreesEtAnnulees(Connection connection, Date dateDebut, Date dateFin) {
      Map<String, Integer> commandes = new HashMap<>();
      String queryLivrees = "SELECT COUNT(*) AS livrees " +
              "FROM commande c " +
              "INNER JOIN livraison l ON c.idCommande = l.idCommande " +
              "WHERE c.Creadate BETWEEN ? AND ? " +
              "AND l.status = 1";

      String queryAnnulees = "SELECT COUNT(*) AS annulees " +
              "FROM commande c " +
              "INNER JOIN livraison l ON c.idCommande = l.idCommande " +
              "WHERE c.Creadate BETWEEN ? AND ? " +
              "AND l.status = -1";

      try (PreparedStatement statementLivrees = connection.prepareStatement(queryLivrees);
           PreparedStatement statementAnnulees = connection.prepareStatement(queryAnnulees)) {
         statementLivrees.setDate(1, new java.sql.Date(dateDebut.getTime()));
         statementLivrees.setDate(2, new java.sql.Date(dateFin.getTime()));
         statementAnnulees.setDate(1, new java.sql.Date(dateDebut.getTime()));
         statementAnnulees.setDate(2, new java.sql.Date(dateFin.getTime()));

         ResultSet resultSetLivrees = statementLivrees.executeQuery();
         if (resultSetLivrees.next()) {
            commandes.put("livrees", resultSetLivrees.getInt("livrees"));
         } else {
            commandes.put("livrees", 0); // Ajouter 0 si aucune commande livrée trouvée
         }

         ResultSet resultSetAnnulees = statementAnnulees.executeQuery();
         if (resultSetAnnulees.next()) {
            commandes.put("annulees", resultSetAnnulees.getInt("annulees"));
         } else {
            commandes.put("annulees", 0); // Ajouter 0 si aucune commande annulée trouvée
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return commandes;
   }

   public Integer[] getOrdersForDay(Connection connection, Date day) {
      Integer[] orders = new Integer[2];
      orders[0] = getDeliveredOrdersForDay(connection, day);
      orders[1] = getCancelledOrdersForDay(connection, day);
      return orders;
   }

   private int getDeliveredOrdersForDay(Connection connection, Date day) {
      String query = "SELECT COUNT(*) AS livrees " +
              "FROM commande c " +
              "INNER JOIN livraison l ON c.idCommande = l.idCommande " +
              "WHERE c.Creadate = ? " +
              "AND l.status = 1";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setDate(1, new java.sql.Date(day.getTime()));
         ResultSet resultSet = statement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt("livrees");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }

   private int getCancelledOrdersForDay(Connection connection, Date day) {
      String query = "SELECT COUNT(*) AS annulees " +
              "FROM commande c " +
              "INNER JOIN livraison l ON c.idCommande = l.idCommande " +
              "WHERE c.Creadate = ? " +
              "AND l.status = -1";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setDate(1, new java.sql.Date(day.getTime()));
         ResultSet resultSet = statement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt("annulees");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }
}
