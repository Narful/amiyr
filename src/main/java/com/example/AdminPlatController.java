package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

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
    void toCommande(ActionEvent event) {

    }

    @FXML
    void toCpt(ActionEvent event) {

    }

    @FXML
    void toHis(ActionEvent event) {

    }

    @FXML
    void toHome(ActionEvent event) {

    }

    @FXML
    void toLiv(ActionEvent event) {

    }

    @FXML
    void toLogout(ActionEvent event) {

    }

    @FXML
    void toSettings(ActionEvent event) {

    }
}