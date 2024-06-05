package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AjouterCuisinierController {

    @FXML
    private TextField confEmailCuisi;

    @FXML
    private TextField emailCuisi;

    @FXML
    private TextField nomCuisi;

    @FXML
    private TextField prenomCuisi;

    public void ajouterCuisinier() {
        String nom = nomCuisi.getText();
        String prenom = prenomCuisi.getText();
        String email = emailCuisi.getText();
        String confEmail = confEmailCuisi.getText();

        if (!nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty() && !confEmail.isEmpty()) {
            if (!email.equals(confEmail)) {
                // Afficher un toast indiquant que les emails ne correspondent pas
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Les adresses email ne correspondent pas !");
                alert.showAndWait();
            } else {
                if (Utilisateur.checkEmail(email)) {
                    // L'email existe déjà, gérer ce cas d'erreur
                    System.out.println("Erreur : L'email existe déjà dans la base de données.");
                } else {
                    // Ajouter le nouveau cuisinier
                    if (ajouterCuisinier(nom, prenom, email)) {
                        System.out.println("Nouveau cuisinier ajouté avec succès !");
                        // Réinitialiser les champs TextField après l'ajout
                        nomCuisi.clear();
                        prenomCuisi.clear();
                        emailCuisi.clear();
                        confEmailCuisi.clear();
                    } else {
                        System.out.println("Erreur lors de l'ajout du nouveau cuisinier.");
                    }
                }
            }
        } else {
            System.out.println("Veuillez remplir tous les champs.");
        }
    }

    private Boolean ajouterCuisinier(String nom, String prenom, String email) {
        String newPassword = Utilisateur.generatePassword();

        // Ajouter le nouveau cuisinier à la base de données
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO utilisateur (nom, prenom, email, motDePasse, role) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nom);
                statement.setString(2, prenom);
                statement.setString(3, email);
                statement.setString(4, newPassword);
                statement.setString(5, "cuisinier");
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Envoi de l'email au nouveau cuisinier
        Utilisateur.sendEmail(email, newPassword);

        return true;
    }
}
