package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AjouterPlatController {

    @FXML
    private AnchorPane btnAjouter;

    @FXML
    private ComboBox<?> categorie;

    @FXML
    private TextArea description;

    @FXML
    private ImageView imgAdd;

    @FXML
    private TextField nom;

    @FXML
    private TextField prix;

    @FXML
    private Spinner<?> qte;

    @FXML
    void AjouterPlat(ActionEvent event) {

    }

    @FXML
    void addIMG(ActionEvent event) {

    }

}
