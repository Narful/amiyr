package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class AjouterPlatController {

    @FXML
    private TextArea description;

    @FXML
    private ImageView imgAdd;

    @FXML
    private TextField nom;

    @FXML
    private TextField prix;

    @FXML
    private Spinner<Integer> qte;

    @FXML
    private ComboBox<String> categorie;

    private byte[] imageBytes;

    @FXML
    void AjouterPlat(ActionEvent event) {
        Plat plat = new Plat();
        plat.setNom(nom.getText());
        plat.setCategorie(categorie.getValue());
        plat.setDescription(description.getText());
        plat.setPhoto(imageBytes);
        plat.setQte(qte.getValue());
        plat.setPrix(Float.parseFloat(prix.getText()));

        if (plat.ajouterPlat()) {
            System.out.println("Plat ajouté avec succès.");
        } else {
            System.out.println("Erreur lors de l'ajout du plat.");
        }
    }

    @FXML
    void addIMG(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                FileInputStream input = new FileInputStream(selectedFile);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) != -1) {
                    output.write(buffer, 0, length);
                }
                imageBytes = output.toByteArray();
                input.close();
                output.close();

                // Afficher l'image sélectionnée dans l'interface
                Image image = new Image(new FileInputStream(selectedFile));
                imgAdd.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
        Plat plat = new Plat();        List<String> categories = plat.recupererCategories();
        categorie.getItems().addAll(categories);
        qte.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
        qte.setEditable(true);
    }
}
