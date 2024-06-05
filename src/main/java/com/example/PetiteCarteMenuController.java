package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PetiteCarteMenuController {

    @FXML
    private Label Categorie;

    @FXML
    private Label nom;

    @FXML
    private Label prix;

    public void setPlat(String nomPlat, String categoriePlat, float prix) {
        nom.setText(nomPlat);
        Categorie.setText(categoriePlat);
        this.prix.setText(String.format("%.2f MAD", prix));
    }
}