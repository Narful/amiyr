package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CartePlatAdminController {

    @FXML
    private Label categoriePlatLabel;

    @FXML
    private ImageView imagePlat;

    @FXML
    private Label nomPlatLabel;

    public void setPlat(String nomPlat, String categoriePlat, byte[] image) {
        nomPlatLabel.setText(nomPlat);
        categoriePlatLabel.setText(categoriePlat);
        if (image != null && image.length > 0) {
            imagePlat.setImage(Plat.convertToJavaFXImage(image, 200, 150)); // Conversion et affichage de l'image
            imagePlat.setFitWidth(215);
            imagePlat.setFitHeight(150);
            imagePlat.setPreserveRatio(false);
            imagePlat.setSmooth(true);
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