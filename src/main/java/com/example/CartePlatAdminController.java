package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class CartePlatAdminController {

    @FXML
    private Label nomPlatLabel;
    @FXML
    private Label categoriePlatLabel;
    @FXML
    private ImageView imagePlat;

    private AdminPlatController adminPlatController;
    private int idPlat;

    public void setAdminPlatController(AdminPlatController adminPlatController) {
        this.adminPlatController = adminPlatController;
    }

    public void setPlat(int idPlat, String nomPlat, String categoriePlat, byte[] photo,
            AdminPlatController adminPlatController) {
        this.idPlat = idPlat; // Stocker l'ID du plat
        this.adminPlatController = adminPlatController;
        nomPlatLabel.setText(nomPlat);
        categoriePlatLabel.setText(categoriePlat);

        if (photo != null && photo.length > 0) {
            Image image = Plat.convertToJavaFXImage(photo, 100, 100); // Ajustez la taille de l'image si nécessaire
            imagePlat.setImage(image);
        }
    }

    @FXML
    void AjouterAuMenu(ActionEvent event) {
        System.out.println("Bouton Ajouter au Menu cliqué pour le plat ID: " + idPlat);
        Menu menu = new Menu();
        if (menu.ajouter(idPlat)) {
            System.out.println("Plat ajouté au menu avec succès.");
            adminPlatController.initialize();
        } else {
            System.out.println("Échec de l'ajout du plat au menu.");
        }
    }

    @FXML
    void ModifPlat(ActionEvent event) {
        // Implémentation de la modification du plat
    }

    @FXML
    void SuppPlat(ActionEvent event) {
        // Implémentation de la suppression du plat
    }
}