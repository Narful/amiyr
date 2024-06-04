package com.example;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

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

   // Méthode pour récupérer la liste des plats avec leurs noms, catégories, et
   // images
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
         resultSet = statement.executeQuery("SELECT idPlat, nom, categorie, photo, prix FROM plat");

         // Parcourir les résultats de la requête
         while (resultSet.next()) {
            // Créer un nouvel objet Plat et définir ses attributs
            Plat plat = new Plat();
            plat.idPlat = resultSet.getInt("idPlat");
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
