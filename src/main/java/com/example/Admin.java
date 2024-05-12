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
         String sql = "INSERT INTO Utilisateur (nom, prenom, email, motDePasse, etat, role) VALUES (?, ?, ?, ?, ?, ?)";
         PreparedStatement statement = connection.prepareStatement(sql);
         statement.setString(1, nom);
         statement.setString(2, prenom);
         statement.setString(3, email);
         statement.setString(4, motDePasse);
         statement.setString(5, "actif"); // Affecter l'état "actif"
         statement.setString(6, role); // Utiliser le rôle fourni

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

   public Map<String, Integer> calculerRevenuPlats(Connection connection, Date dateDebut, Date dateFin) {
      Map<String, Integer> revenuPlats = new HashMap<>();
      String query = "SELECT nom, SUM(prix * qte) AS revenu_total FROM plat " +
              "INNER JOIN panier_plat ON plat.idPlat = panier_plat.idPlat " +
              "INNER JOIN panier ON panier_plat.idPanier = panier.idPanier " +
              "INNER JOIN commande ON panier.idPanier = commande.idPanier " +
              "WHERE commande.Creadate BETWEEN ? AND ? GROUP BY plat.nom ORDER BY revenu_total DESC LIMIT 5";

      try (PreparedStatement statement = connection.prepareStatement(query)) {
         statement.setDate(1, new java.sql.Date(dateDebut.getTime()));
         statement.setDate(2, new java.sql.Date(dateFin.getTime()));
         ResultSet resultSet = statement.executeQuery();
         while (resultSet.next()) {
            String plat = resultSet.getString("nom");
            int revenuTotal = resultSet.getInt("revenu_total");
            revenuPlats.put(plat, revenuTotal);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return revenuPlats;
   }


   public int[] calculerCommandesLivreesEtAnnulees(Connection connection, Date dateDebut, Date dateFin) {
      int[] commandes = new int[2]; // Index 0 pour les commandes livrées, index 1 pour les commandes annulées
      String queryLivrees = "SELECT COUNT(*) AS livrees FROM livraison INNER JOIN commande ON livraison.idCommande = commande.idCommande " +
              "WHERE commande.Creadate BETWEEN ? AND ? AND livraison.status = 1";
      String queryAnnulees = "SELECT COUNT(*) AS annulees FROM livraison INNER JOIN commande ON livraison.idCommande = commande.idCommande " +
              "WHERE commande.Creadate BETWEEN ? AND ? AND livraison.status = -1";

      try {
         // Calculer les commandes livrées
         PreparedStatement statementLivrees = connection.prepareStatement(queryLivrees);
         statementLivrees.setDate(1, new java.sql.Date(dateDebut.getTime()));
         statementLivrees.setDate(2, new java.sql.Date(dateFin.getTime()));
         ResultSet resultSetLivrees = statementLivrees.executeQuery();
         if (resultSetLivrees.next()) {
            commandes[0] = resultSetLivrees.getInt("livrees");
         }

         // Calculer les commandes annulées
         PreparedStatement statementAnnulees = connection.prepareStatement(queryAnnulees);
         statementAnnulees.setDate(1, new java.sql.Date(dateDebut.getTime()));
         statementAnnulees.setDate(2, new java.sql.Date(dateFin.getTime()));
         ResultSet resultSetAnnulees = statementAnnulees.executeQuery();
         if (resultSetAnnulees.next()) {
            commandes[1] = resultSetAnnulees.getInt("annulees");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return commandes;
   }



}