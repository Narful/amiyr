package com.example;

import java.sql.*;
import java.util.LinkedList;

public class Commande {
   public int idCommande;
   public String note;
   public String date;
   public Boolean status;
   public float montant;
   private Connection connection;

   // Getter for date
   public String getDate() {
      return date;
   }

   // Getter for idCommande
   public int getIdCommande() {
      return idCommande;
   }

   // Getter for status
   public boolean isStatus() {
      return status;
   }

   // Setter for date
   public void setDate(String date) {
      this.date = date;
   }

   // Setter for idCommande
   public void setIdCommande(int idCommande) {
      this.idCommande = idCommande;
   }

   // Setter for status
   public void setStatus(boolean status) {
      this.status = status;
   }

   public boolean passerCommande(int idPanier) {
      // Implémentation de la méthode passerCommande
      try {
         // Exécuter une requête SQL pour insérer une nouvelle commande
         String sql = "INSERT INTO commande (idPanier, note, status, Creadate) VALUES (?, ?, ?, ?)";
         PreparedStatement statement = connection.prepareStatement(sql);
         statement.setInt(1, idPanier); // ID du panier associé à la commande
         statement.setString(2, "rien"); // Note initiale
         statement.setBoolean(3, false); // Status initial à 0 (non validée)
         // Date de création de la commande (timestamp actuel)
         statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public LinkedList<Commande> consulterCommandes() {
      // Implémentation de la méthode consulterCommandes
      try {
         // Exécuter une requête SQL pour consulter toutes les commandes
         String sql = "SELECT * FROM commande";
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql);
         LinkedList<Commande> commandes = new LinkedList<>();
         while (resultSet.next()) {
            Commande commande = new Commande();
            commande.idCommande = resultSet.getInt("idCommande");
            commande.date = resultSet.getString("Creadate");
            commande.status = resultSet.getBoolean("status");
            commandes.add(commande);
         }
         return commandes;
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }

   public boolean consulterCommande(int idCommande) {
      // Implémentation de la méthode consulterCommande
      try {
         // Exécuter une requête SQL pour consulter la commande
         String sql = "SELECT * FROM commande WHERE idCommande = ?";
         PreparedStatement statement = connection.prepareStatement(sql);
         statement.setInt(1, idCommande);
         ResultSet resultSet = statement.executeQuery();
         return resultSet.next(); // Retourner true si la commande existe, sinon false
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public boolean validerCommande(int idCommande) {
      try {
         // Exécuter une requête SQL pour mettre à jour le statut de la commande
         String sql = "UPDATE commande SET status = ? WHERE idCommande = ?";
         PreparedStatement statement = connection.prepareStatement(sql);
         statement.setBoolean(1, true); // Mettre à jour le statut à 1 (validée)
         statement.setInt(2, idCommande); // Identifier la commande à mettre à jour
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public boolean creationlivraison(int idCommande, String note) {
      try {
         // Vérifier d'abord si la commande est validée
         String sqlCheckCommande = "SELECT status FROM commande WHERE idCommande = ?";
         PreparedStatement checkStatement = connection.prepareStatement(sqlCheckCommande);
         checkStatement.setInt(1, idCommande);
         ResultSet resultSet = checkStatement.executeQuery();

         if (resultSet.next()) {
            boolean status = resultSet.getBoolean("status");
            if (status) { // Si le statut de la commande est valide (1)
               // Créer une livraison pour cette commande
               String sql = "INSERT INTO livraison (idCommande, status) VALUES (?, ?)";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setInt(1, idCommande); // ID de la commande associée à la livraison
               statement.setBoolean(2, false); // Statut initial à false (non notifié)
               int rowsAffected = statement.executeUpdate();
               return rowsAffected > 0;
            } else {
               System.out.println("La commande n'est pas validée. Impossible de créer une livraison.");
               return false;
            }
         } else {
            System.out.println("La commande n'existe pas.");
            return false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public boolean annulerCommande(int idCommande) {
      try {
         // Exécuter une requête SQL pour mettre à jour le statut de la commande
         String sql = "UPDATE commande SET status = ? WHERE idCommande = ?";
         PreparedStatement statement = connection.prepareStatement(sql);
         statement.setInt(1, -1); // Mettre à jour le statut à -1 (annulée)
         statement.setInt(2, idCommande); // Identifier la commande à mettre à jour
         int rowsAffected = statement.executeUpdate();
         return rowsAffected > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

}