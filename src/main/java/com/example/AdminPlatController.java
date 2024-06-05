package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class AdminPlatController {

    @FXML
    private HBox HBoxContainer;

    public void initialize() {
        HBoxContainer.getChildren().clear(); // Vider le conteneur avant de le recharger

        // Charger les plats
        Plat plat = new Plat();
        List<Plat> plats = plat.consulterPlats();

        // Afficher les plats dans la console pour vérification
        for (Plat p : plats) {
            System.out.println("Plat trouvé: " + p.nom + " - " + p.categorie);
        }

        // Ajouter les plats au HBoxContainer pour affichage
        for (Plat p : plats) {
            try {
                // Charger le fichier FXML pour chaque plat
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cartePlatAdmin.fxml"));
                Node cartePlatNode = loader.load();

                // Obtenir le contrôleur associé au fichier FXML
                CartePlatAdminController controller = loader.getController();
                // Passer les données du plat au contrôleur de la carte
                controller.setPlat(p.idPlat, p.nom, p.categorie, p.photo, this);

                // Ajouter le node au HBoxContainer
                HBoxContainer.getChildren().add(cartePlatNode);

                // Ajouter un espaceur entre les cartes
                Region spacer = new Region();
                spacer.setPrefWidth(20); // Taille de l'espace
                HBoxContainer.getChildren().add(spacer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void AjoutPlatPopUp(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AjouterPlat.fxml"));
            Parent parent = fxmlLoader.load();

            // Create the new stage for the popup
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(parent));

            // Set the width and height of the popup window manually
            double popupWidth = 700; // Adjust as necessary
            double popupHeight = 412; // Adjust as necessary

            stage.setWidth(popupWidth);
            stage.setHeight(popupHeight);

            // Get the parent stage (main window)
            Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the popup to be modal and block input events to other windows
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            // Center the popup on the parent stage based on fixed size
            double parentWidth = 1550;
            double parentHeight = 790;

            stage.setX(primaryStage.getX() + parentWidth / 2 - popupWidth / 2);
            stage.setY(primaryStage.getY() + parentHeight / 2 - popupHeight / 2);

            // Show the popup
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toCommande(ActionEvent event) {

    }

    @FXML
    void toCpt(ActionEvent event) {

    }

    @FXML
    void toLiv(ActionEvent event) {

    }

    @FXML
    void toHome(ActionEvent event) {
        try {
            App.setRoot("DashboardAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toHis(ActionEvent event) {
        try {
            App.setRoot("historiqueAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toLogout(ActionEvent event) {
        try {
            App.setRoot("signin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toSettings(ActionEvent event) {

    }
}