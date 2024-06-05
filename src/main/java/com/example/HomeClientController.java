package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.ArrayList;
import java.util.List;

public class HomeClientController {

    @FXML
    private TableView<Plat> tableMenu;
    @FXML
    private TableView<Plat> tablePanier;
    @FXML
    private TableColumn<Plat, ImageView> photo;
    @FXML
    private TableColumn<Plat, String> nom;
    @FXML
    private TableColumn<Plat, String> categorie;
    @FXML
    private TableColumn<Plat, Float> prix;

    @FXML
    private TableColumn<Plat, String> nomP;
    @FXML
    private TableColumn<Plat, Integer> qteP;
    @FXML
    private TableColumn<Plat, Float> prixTotalP;

    @FXML
    private ImageView logoAmiyr;

    @FXML
    private Label montantTotalP;

    @FXML
    private Button ajouterButton;

    private List<Plat> menuPlats;
    private List<Plat> panierPlats = new ArrayList<>();

    @FXML
    public void initialize() {
    }

    @FXML
    void toLogout(ActionEvent event) {
        // Handle logout logic
    }

}
