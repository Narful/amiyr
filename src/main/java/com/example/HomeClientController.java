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
    private TableColumn<Plat, Void> ajouterAuPanier;
    @FXML
    private TableColumn<Plat, String> nomP;
    @FXML
    private TableColumn<Plat, Integer> qteP;
    @FXML
    private TableColumn<Plat, Float> prixTotalP;
    @FXML
    private TableColumn<Plat, Void> retirer;

    @FXML
    private ImageView logoAmiyr;

    @FXML
    private Label montantTotalP;

    @FXML
    void toLogout(ActionEvent event) {
        // Handle logout logic
    }

    private List<Plat> menuPlats;
    private List<Plat> panierPlats = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize the table columns for the menu
        photo.setCellValueFactory(param -> {
            ImageView imageView = new ImageView();
            Image img = Plat.convertToJavaFXImage(param.getValue().photo, 50, 50);
            imageView.setImage(img);
            return new ReadOnlyObjectWrapper<>(imageView);
        });
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        ajouterAuPanier.setCellFactory(param -> new TableCell<Plat, Void>() {
            private final Button btn = new Button("Ajouter");

            {
                btn.setOnAction(event -> {
                    Plat plat = getTableView().getItems().get(getIndex());
                    addToPanier(plat);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        // Initialize the table columns for the basket
        nomP.setCellValueFactory(new PropertyValueFactory<>("nom"));
        qteP.setCellValueFactory(new PropertyValueFactory<>("qte"));
        prixTotalP.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().prix * param.getValue().qte));
        retirer.setCellFactory(param -> new TableCell<Plat, Void>() {
            private final Button btn = new Button("Retirer");

            {
                btn.setOnAction(event -> {
                    Plat plat = getTableView().getItems().get(getIndex());
                    removeFromPanier(plat);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        // Load data into the menu table
        menuPlats = new Plat().consulterPlats();
        tableMenu.getItems().setAll(menuPlats);
    }

    private void addToPanier(Plat plat) {
        if (panierPlats.contains(plat)) {
            plat.setQte(plat.getQte() + 1);
        } else {
            plat.setQte(1);
            panierPlats.add(plat);
        }
        updatePanier();
    }

    private void removeFromPanier(Plat plat) {
        if (panierPlats.contains(plat)) {
            if (plat.getQte() > 1) {
                plat.setQte(plat.getQte() - 1);
            } else {
                panierPlats.remove(plat);
            }
            updatePanier();
        }
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