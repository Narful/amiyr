package com.example;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plat {
   public int idPlat;
   public String nom;
   public String categorie;
   public String description;
   public byte[] photo; // Changement pour stocker l'image sous forme de byte array
   public int qte;
   public Float prix;

   public Plat() {
   }
   public void setNom(String nom) {
      this.nom = nom;
   }

   public void setCategorie(String categorie) {
      this.categorie = categorie;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setPhoto(byte[] photo) {
      this.photo = photo;
   }

   public void setQte(int qte) {
      this.qte = qte;
   }

   public void setPrix(Float prix) {
      this.prix = prix;
   }
   public boolean ajouterPlat() {
      String sql = "INSERT INTO plat (nom, categorie, description, photo, qte, prix, isMenu) VALUES (?, ?, ?, ?, ?, ?, 0)";
      try (Connection connection = DatabaseConnection.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setString(1, nom);
         statement.setString(2, categorie);
         statement.setString(3, description);
         statement.setBytes(4, photo);
         statement.setInt(5, qte);
         statement.setFloat(6, prix);
         statement.executeUpdate();
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }


   public List<String> recupererCategories() {
      List<String> categories = new ArrayList<>();
      String sql = "SELECT DISTINCT categorie FROM plat";
      try (Connection connection = DatabaseConnection.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet resultSet = statement.executeQuery()) {
         while (resultSet.next()) {
            String categorie = resultSet.getString("categorie");
            categories.add(categorie);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return categories;
   }

   public List<Plat> consulterPlats() {
      List<Plat> plats = new ArrayList<>();
      Connection connection = null;
      Statement statement = null;
      ResultSet resultSet = null;

      try {
         // Obtenir une connexion à la base de données
         connection = DatabaseConnection.getConnection();
         // Créer une instruction SQL
         statement = connection.createStatement();
         // Exécuter la requête SQL pour récupérer les noms, catégories, et images des
         // plats
         resultSet = statement.executeQuery("SELECT nom, categorie, photo, prix FROM plat");

         // Parcourir les résultats de la requête
         while (resultSet.next()) {
            // Créer un nouvel objet Plat et définir ses attributs
            Plat plat = new Plat();
            plat.nom = resultSet.getString("nom");
            plat.categorie = resultSet.getString("categorie");
            plat.photo = resultSet.getBytes("photo"); // Récupérer l'image en tant que byte array
            plat.prix = resultSet.getFloat("prix");
            // Ajouter le plat à la liste des plats
            plats.add(plat);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         // Fermer les ressources
         try {
            if (resultSet != null) {
               resultSet.close();
            }
            if (statement != null) {
               statement.close();
            }
            DatabaseConnection.closeConnection(connection);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      // Retourner la liste des plats
      return plats;
   }

   // Méthode pour convertir le byte array en Image (JavaFX)
   public static Image convertToJavaFXImage(byte[] raw, int width, int height) {
      return new Image(new ByteArrayInputStream(raw), width, height, false, true);
   }















}
