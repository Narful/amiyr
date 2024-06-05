package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public class HomeClientController {

    @FXML
    private TableColumn<?, ?> ajouter;

    @FXML
    private TableColumn<?, ?> ajouterAuPanier;

    @FXML
    private TableColumn<?, ?> categorie;

    @FXML
    private ImageView logoAmiyr;

    @FXML
    private Label montantTotalP;

    @FXML
    private TableColumn<?, ?> nom;

    @FXML
    private TableColumn<?, ?> nomP;

    @FXML
    private TableColumn<?, ?> photo;

    @FXML
    private TableColumn<?, ?> prix;

    @FXML
    private TableColumn<?, ?> prixTotalP;

    @FXML
    private TableColumn<?, ?> qteP;

    @FXML
    private TableColumn<?, ?> retirer;

    @FXML
    void toLogout(ActionEvent event) {

    }

}
