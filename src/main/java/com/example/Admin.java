package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}