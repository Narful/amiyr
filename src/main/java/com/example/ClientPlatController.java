package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.List;

public class ClientPlatController {

    @FXML
    private HBox menuContainer;

    @FXML
    private AnchorPane menuitems;

    public void initialize() {

        Plat plat = new Plat();
        List<Plat> plats = plat.consulterPlats();

        for (Plat p : plats) {
            System.out.println("Plat trouvé: " + p.nom + " - " + p.categorie);
        }

        for (Plat p : plats) {
            try {
                // Charger le fichier FXML pour chaque plat
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cartePlatClient.fxml"));
                Node cartePlatNode = loader.load();

                // Obtenir le contrôleur associé au fichier FXML
                CartePlatClientController controller = loader.getController();
                // Passer les données du plat au contrôleur de la carte
                controller.setPlat(p.nom, p.categorie, p.photo, p.prix);

                // Ajouter le node au HBoxContainer
                menuContainer.getChildren().add(cartePlatNode);

                // Ajouter un espaceur entre les cartes
                //Region spacer = new Region();
                //spacer.setPrefWidth(20); // Taille de l'espace
                //menuContainer.getChildren().add(spacer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}