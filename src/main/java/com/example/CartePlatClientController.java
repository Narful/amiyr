package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CartePlatClientController {

    @FXML
    private Button addCartBtn;

    @FXML
    private Label categoriePlatLabel;

    @FXML
    private ImageView imagePlat;

    @FXML
    private Label nomPlatLabel;

    @FXML
    private Label prixPlatLabel;

    @FXML
    private Label ratingPlatLabel;

    public void setCardData(String name, String categorie, Double prix){
        //imagePlat.setImage(imagePlatdata);
        nomPlatLabel.setText(name);
        categoriePlatLabel.setText(categorie);
        prixPlatLabel.setText(prix + "Dh");

    }


    public void setPlat(String nomPlat, String categoriePlat, byte[] image,float prix) {
        nomPlatLabel.setText(nomPlat);
        categoriePlatLabel.setText(categoriePlat);
        prixPlatLabel.setText(prix + "Dh");
        if (image != null && image.length > 0) {
            imagePlat.setImage(Plat.convertToJavaFXImage(image, 200, 150)); // Conversion et affichage de l'image
            imagePlat.setFitWidth(215);
            imagePlat.setFitHeight(150);
            imagePlat.setPreserveRatio(false);
            imagePlat.setSmooth(true);
        }
    }




}