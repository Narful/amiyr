package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class CartePlatAdminController {

    @FXML
    private Label platNom;
    @FXML
    private Label platCategorie;
    @FXML
    private ImageView platImage;

    public void setPlat(String nom, String categorie, byte[] photo) {
        platNom.setText(nom);
        platCategorie.setText(categorie);

        if (photo != null && photo.length > 0) {
            Image image = Plat.convertToJavaFXImage(photo, 100, 100); // Vous pouvez ajuster la taille de l'image
            platImage.setImage(image);
        }
    }

    @FXML
    void AjouterAuMenu(ActionEvent event) {
        // Implémentation de l'ajout au menu
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