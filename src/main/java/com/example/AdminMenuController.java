package com.example;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class AdminMenuController {

    @FXML
    private ImageView logoAmiyr;

    @FXML
    private HBox HBoxItems;

    public void initialize() {
        HBoxItems.getChildren().clear(); // Clear previous children

        Menu menu = new Menu(); // Assurez-vous d'initialiser Menu correctement si nécessaire
        List<Plat> plats = menu.consulterPlatsEnMenu();

        // Afficher les plats dans la console pour vérification
        for (Plat p : plats) {
            System.out.println("Plat trouvé: " + p.nom + " - " + p.categorie);
        }

        // Ajouter les plats au HBoxItems pour affichage
        for (Plat p : plats) {
            try {
                // Charger le fichier FXML pour chaque plat
                FXMLLoader loader = new FXMLLoader(getClass().getResource("carteMenuPlatAdmin.fxml"));
                Node cartePlatNode = loader.load();

                // Obtenir le contrôleur associé au fichier FXML
                CarteMenuPlatAdminController controller = loader.getController();
                // Passer les données du plat au contrôleur de la carte
                controller.setPlat(p.idPlat, p.nom, p.categorie, p.photo, this);

                // Ajouter le node au HBoxItems
                HBoxItems.getChildren().add(cartePlatNode);

                // Ajouter un espaceur entre les cartes
                Region spacer = new Region();
                spacer.setPrefWidth(20); // Taille de l'espace
                HBoxItems.getChildren().add(spacer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void genererPdf(ActionEvent event) {
        // Générer le PDF
        Menu menu = new Menu(); // Assurez-vous d'initialiser Menu correctement si nécessaire
        menu.genererPdf();
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
    void toLiv(ActionEvent event) {

    }

    @FXML
    void toLogout(ActionEvent event) {

    }

    @FXML
    void toPlat(ActionEvent event) {

    }

    @FXML
    void toSettings(ActionEvent event) {

    }

}