package com.example;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Menu {

   private static final String PDF_PATH = "menu.pdf";

   public int idMenu;
   public Plat[] plats;

   public Boolean ajouter(int idPlat) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "UPDATE plat SET isMenu = 1 WHERE idPlat = ?";
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setInt(1, idPlat);
         int rowsUpdated = preparedStatement.executeUpdate();
         return rowsUpdated > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public Boolean retirer(int idPlat) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "UPDATE plat SET isMenu = 0 WHERE idPlat = ?";
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setInt(1, idPlat);
         int rowsUpdated = preparedStatement.executeUpdate();
         return rowsUpdated > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public List<Plat> consulterPlatsEnMenu() {
      List<Plat> plats = new ArrayList<>();
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "SELECT idPlat, nom, categorie, photo FROM plat WHERE isMenu = 1";
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
            Plat plat = new Plat();
            plat.idPlat = resultSet.getInt("idPlat");
            plat.nom = resultSet.getString("nom");
            plat.categorie = resultSet.getString("categorie");
            plat.photo = resultSet.getBytes("photo");
            plats.add(plat);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return plats;
   }

   public Boolean genererPdf() {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "SELECT nom, description, prix, categorie, photo FROM plat WHERE isMenu = 1";
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery();

         // Utiliser TreeMap pour garantir l'ordre et placer "platdujour" en premier
         Map<String, Map<String, Dish>> dishesByCategory = new TreeMap<>((cat1, cat2) -> {
            if ("platdujour".equals(cat1))
               return -1;
            if ("platdujour".equals(cat2))
               return 1;
            return cat1.compareTo(cat2);
         });

         while (resultSet.next()) {
            String name = resultSet.getString("nom");
            String description = resultSet.getString("description");
            float price = resultSet.getFloat("prix");
            String category = resultSet.getString("categorie");
            byte[] photo = resultSet.getBytes("photo");

            Dish dish = new Dish(name, description, price, photo);

            dishesByCategory.computeIfAbsent(category, k -> new LinkedHashMap<>()).put(name, dish);
         }

         try {
            PdfWriter writer = new PdfWriter(PDF_PATH);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.setBackgroundColor(ColorConstants.LIGHT_GRAY);

            // Ajouter le titre principal
            Paragraph title = new Paragraph("Le Menu de Amiyr")
                  .setBold()
                  .setFontSize(24)
                  .setMarginBottom(20)
                  .setTextAlignment(TextAlignment.CENTER)
                  .setFontColor(ColorConstants.BLACK);
            document.add(title);

            for (Map.Entry<String, Map<String, Dish>> entry : dishesByCategory.entrySet()) {
               String category = entry.getKey();
               Map<String, Dish> dishes = entry.getValue();

               // Ajouter l'image de la catégorie (premier plat de la catégorie)
               Dish firstDish = dishes.values().iterator().next();
               if (firstDish.getPhoto() != null) {
                  try {
                     ImageData imageData = ImageDataFactory.create(firstDish.getPhoto());
                     Image image = new Image(imageData);
                     image.setWidth(500);
                     document.add(image);
                  } catch (com.itextpdf.io.IOException e) {
                     System.err.println("Failed to load image for category: " + category);
                  }
               }

               for (Dish dish : dishes.values()) {
                  document.add(new Paragraph(dish.getName() + " - " + dish.getPrice() + " DH")
                        .setBold()
                        .setFontSize(14)
                        .setFontColor(ColorConstants.DARK_GRAY));
                  document.add(new Paragraph(dish.getDescription())
                        .setFontSize(12)
                        .setFontColor(ColorConstants.DARK_GRAY)
                        .setMarginBottom(20));
               }
            }

            document.close();
            System.out.println("PDF généré avec succès!");

            return true;

         } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   static class Dish {
      private final String name;
      private final String description;
      private final float price;
      private final byte[] photo;

      public Dish(String name, String description, float price, byte[] photo) {
         this.name = name;
         this.description = description;
         this.price = price;
         this.photo = photo;
      }

      public String getName() {
         return name;
      }

      public String getDescription() {
         return description;
      }

      public float getPrice() {
         return price;
      }

      public byte[] getPhoto() {
         return photo;
      }
   }
}
