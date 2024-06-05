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
    private TableView<Plats> tableMenu;
    @FXML
    private TableView<Plats> tablePanier;
    @FXML
    private TableColumn<Plats, ImageView> photo;
    @FXML
    private TableColumn<Plats, String> nom;
    @FXML
    private TableColumn<Plats, String> categorie;
    @FXML
    private TableColumn<Plats, Float> prix;

    @FXML
    private TableColumn<Plats, String> nomP;
    @FXML
    private TableColumn<Plats, Integer> qteP;
    @FXML
    private TableColumn<Plats, Float> prixTotalP;

    @FXML
    private ImageView logoAmiyr;

    @FXML
    private Label montantTotalP;

    @FXML
    private Button ajouterButton;

    private List<Plats> menuPlats;
    private List<Plats> panierPlats = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize the table columns for the menu
        photo.setCellValueFactory(param -> {
            ImageView imageView = new ImageView();
            Image img = Plat.convertToJavaFXImage(param.getValue().getPhoto(), 50, 50);
            imageView.setImage(img);
            return new ReadOnlyObjectWrapper<>(imageView);
        });
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Initialize the table columns for the basket
        nomP.setCellValueFactory(new PropertyValueFactory<>("nom"));
        qteP.setCellValueFactory(new PropertyValueFactory<>("qte"));
        prixTotalP.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrix() * param.getValue().getQte()));

        // Load data into the menu table
        menuPlats = new Plats().consulterPlats();
        tableMenu.getItems().setAll(menuPlats);

        // Disable the add button if no row is selected
        ajouterButton.setDisable(true);
        tableMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            ajouterButton.setDisable(newSelection == null);
        });
    }

    @FXML
    void toLogout(ActionEvent event) {
        // Handle logout logic
    }

    @FXML
    void AjoutAuPanier(ActionEvent event) {
        Plat selectedPlat = tableMenu.getSelectionModel().getSelectedItem();
        if (selectedPlat != null) {
            addToPanier(selectedPlat);
        }
    }

    private void addToPanier(Plat plat) {
        boolean found = false;
        for (Plat p : panierPlats) {
            if (p.getIdPlat() == plat.getIdPlat()) {
                p.setQte(p.getQte() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            Plat newPlat = new Plat();
            newPlat.setIdPlat(plat.getIdPlat());
            newPlat.setNom(plat.getNom());
            newPlat.setCategorie(plat.getCategorie());
            newPlat.setDescription(plat.getDescription());
            newPlat.setPhoto(plat.getPhoto());
            newPlat.setQte(1);
            newPlat.setPrix(plat.getPrix());
            panierPlats.add(newPlat);
        }
        updatePanier();
    }

    private void removeFromPanier(Plat plat) {
        panierPlats.remove(plat);
        updatePanier();
    }

    private void updatePanier() {
        tablePanier.getItems().setAll(panierPlats);
        float total = 0;
        for (Plat plat : panierPlats) {
            total += plat.getPrix() * plat.getQte();
        }
        montantTotalP.setText(String.format("Total : %.2f MAD", total));
    }
}
