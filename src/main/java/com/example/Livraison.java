package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Livraison {

   public int idLivraison;
   public Boolean status;
   private Connection connection; // Assurez-vous que cette variable est initialisée

   public int notifier(int idCommande, int idCpt) {
      return 0;
   }

   public int envoyer(int idCommande, int idCpt) {
      try {
         // Exécuter une requête SQL pour mettre à jour le statut de la livraison à envoyée
         String sql = "UPDATE livraison SET status = ? WHERE idCommande = ?";
         PreparedStatement statement = connection.prepareStatement(sql);
         statement.setBoolean(1, true); // Mettre à jour le statut à true (livraison envoyée)
         statement.setInt(2, idCommande); // Identifier la commande associée à la livraison
         int rowsAffected = statement.executeUpdate();
         return rowsAffected; // Retourne le nombre de lignes affectées
      } catch (SQLException e) {
         e.printStackTrace();
         return 0;
      }
   }


   public int confirmer(int idCommande, int idCpt) {
      try {
         // Vérifier d'abord le statut actuel de la livraison
         String checkStatusSql = "SELECT status FROM livraison WHERE idCommande = ?";
         PreparedStatement checkStatusStatement = connection.prepareStatement(checkStatusSql);
         checkStatusStatement.setInt(1, idCommande);
         ResultSet resultSet = checkStatusStatement.executeQuery();

         if (resultSet.next()) {
            int currentStatus = resultSet.getInt("status");
            if (currentStatus == 1) { // Si le statut actuel est 1 (livraison envoyée)
               // Exécuter une requête SQL pour mettre à jour le statut de la livraison à 2 (livraison confirmée)
               String sql = "UPDATE livraison SET status = ? WHERE idCommande = ?";
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setInt(1, 2); // Mettre à jour le statut à 2 (livraison confirmée)
               statement.setInt(2, idCommande); // Identifier la commande associée à la livraison
               int rowsAffected = statement.executeUpdate();
               return rowsAffected; // Retourner le nombre de lignes affectées
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }

}