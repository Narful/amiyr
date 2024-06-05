package com.example;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Plats {
    private int idPlat;
    private String nom;
    private String categorie;
    private String description;
    private byte[] photo; // To store image as byte array
    private int qte;
    private Float prix;

    public Plats() {
    }

    public Plats(int idPlat, String nom, String categorie, String description, byte[] photo, int qte, Float prix) {
        this.idPlat = idPlat;
        this.nom = nom;
        this.categorie = categorie;
        this.description = description;
        this.photo = photo;
        this.qte = qte;
        this.prix = prix;
    }

    // Getters and setters
    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Float getPrix() {
        return prix;
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
        String sql = "SELECT idPlat, nom, categorie, description, photo, qte, prix FROM plat";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Plat plat = new Plat();
                plat.setIdPlat(resultSet.getInt("idPlat"));
                plat.setNom(resultSet.getString("nom"));
                plat.setCategorie(resultSet.getString("categorie"));
                plat.setDescription(resultSet.getString("description"));
                plat.setPhoto(resultSet.getBytes("photo"));
                plat.setQte(resultSet.getInt("qte"));
                plat.setPrix(resultSet.getFloat("prix"));
                plats.add(plat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }

    // Method to convert byte array to JavaFX Image
    public static Image convertToJavaFXImage(byte[] raw, int width, int height) {
        return new Image(new ByteArrayInputStream(raw), width, height, false, true);
    }
}