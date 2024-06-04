package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Panier {

   public int idPanier;

   public Plat[] plats;

   public float prixTotal;

   public Boolean ajouter(int idPlat) {
      if (idPlat == 0) {
         try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO panier (idCpt) VALUES (?)";
            String query2 = "INSERT INTO plat_panier (idPlat, idPanier) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
               statement.setInt(1, Utilisateur.idCpt);
               statement.executeUpdate();
               try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
                  statement2.setInt(1, idPlat);
                  statement2.setInt(2, idPanier);
                  statement2.executeUpdate();
                  return true;
               } catch (Exception e) {
                  e.printStackTrace();
                  return false;
               }
            } catch (Exception e) {
               e.printStackTrace();
               return false;
            }
         } catch (Exception e) {
            e.printStackTrace();
            return false;
         }
      } else {
         try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO plat_panier (idPlat, idPanier) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
               statement.setInt(1, idPlat);
               statement.setInt(2, idPanier);
               statement.executeUpdate();
               return true;
            } catch (Exception e) {
               e.printStackTrace();
               return false;
            }
         } catch (Exception e) {
            e.printStackTrace();
            return false;
         }
      }
   }

   public Boolean retirer(int idPlat) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "DELETE FROM plat_panier WHERE idPlat = ? AND idPanier = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPlat);
            statement.setInt(2, idPanier);
            statement.executeUpdate();
            return true;
         } catch (Exception e) {
            e.printStackTrace();
            return false;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   public Plat[] consulter(int idPanier) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "SELECT p.photo, p.nom, p.prix FROM plat_panier pp JOIN plat p ON pp.idPlat = p.idPlat WHERE pp.idPanier = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPanier);
            statement.executeUpdate();
            for (int i = 0; statement.getResultSet().next(); i++) {
               //plats[i].photo = statement.getResultSet().getString(1);
               plats[i].nom = statement.getResultSet().getString(2);
               plats[i].prix = statement.getResultSet().getFloat(3);
            }
            return plats;
         } catch (Exception e) {
            e.printStackTrace();
            return null;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public Float afficherTotal(int idPanier) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "SELECT SUM(p.prix) FROM plat_panier pp JOIN plat p ON pp.idPlat = p.idPlat WHERE pp.idPanier = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPanier);
            statement.executeUpdate();
            return statement.getResultSet().getFloat(1);
         } catch (Exception e) {
            e.printStackTrace();
            return null;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public Boolean sauvegarderPanier(int idPanier) {
      // TODO: implement
      return null;
   }

   public Boolean viderPanier(int idPanier) {
      try (Connection connection = DatabaseConnection.getConnection()) {
         String query = "DELETE FROM plat_panier WHERE idPanier = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPanier);
            statement.executeUpdate();
            return true;
         } catch (Exception e) {
            e.printStackTrace();
            return false;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

}