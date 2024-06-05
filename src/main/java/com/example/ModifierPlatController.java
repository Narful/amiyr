package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ModifierPlatController {

    @FXML
    private ComboBox<String> categorie;

    @FXML
    private TextArea description;

    @FXML
    private ImageView imgModif;

    @FXML
    private TextField nom;

    @FXML
    private TextField prix;

    @FXML
    private Spinner<Integer> qte;

    private int idPlat;

    private Plat plat;

    @FXML
    void ModifIMG(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Charger l'image sélectionnée dans l'ImageView
                Image image = new Image(selectedFile.toURI().toString());
                imgModif.setImage(image);

                // Lire l'image en tant que tableau d'octets
                byte[] imageBytes;
                try (FileInputStream input = new FileInputStream(selectedFile);
                     ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = input.read(buffer)) != -1) {
                        output.write(buffer, 0, length);
                    }
                    imageBytes = output.toByteArray();
                }

                // Stocker l'image dans l'objet Plat
                plat.setPhoto(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void SaveModifPlat(ActionEvent event) {
        System.out.println("SaveModifPlat called");
        // Vérifier si l'id du plat est valide
        if (idPlat > 0) {
            System.out.println("idPlat is valid: " + idPlat);
            // Récupérer les données saisies dans les champs
            String nomPlat = nom.getText();
            String categoriePlat = categorie.getValue();
            String descriptionPlat = description.getText();
            int quantitePlat = qte.getValue();
            float prixPlat = 0;
            try {
                prixPlat = Float.parseFloat(prix.getText());
            } catch (NumberFormatException e) {
                System.out.println("Prix invalide.");
                return;
            }

            // Mettre à jour les données du plat dans la base de données
            plat.setIdPlat(idPlat);
            plat.setNom(nomPlat);
            plat.setCategorie(categoriePlat);
            plat.setDescription(descriptionPlat);
            plat.setQte(quantitePlat);
            plat.setPrix(prixPlat);

            // Convertir l'image chargée dans l'ImageView en byte array
            Image image = imgModif.getImage();
            byte[] photoPlat = null;
            if (image != null) {
                photoPlat = convertImageToByteArray(image);
            }
            plat.setPhoto(photoPlat);

            System.out.println("Updating plat: " + plat);

            // Appeler une méthode pour mettre à jour le plat dans la base de données
            boolean success = plat.updatePlat();

            // Vérifier si la mise à jour a réussi
            if (success) {
                System.out.println("Les modifications du plat ont été sauvegardées avec succès.");
            } else {
                System.out.println("Échec de la sauvegarde des modifications du plat.");
            }
        } else {
            System.out.println("L'ID du plat n'est pas valide.");
        }
    }

    private byte[] convertImageToByteArray(Image image) {
        if (image == null) {
            return null;
        }

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        PixelReader pixelReader = image.getPixelReader();

        if (pixelReader == null) {
            return null;
        }

        // IntBuffer pour stocker les pixels ARGB
        IntBuffer buffer = IntBuffer.allocate(width * height);
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), buffer, width);

        // ByteBuffer pour convertir l'IntBuffer en ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(width * height * 4);
        buffer.rewind();
        while (buffer.hasRemaining()) {
            int pixel = buffer.get();
            byteBuffer.put((byte) ((pixel >> 16) & 0xFF)); // Red
            byteBuffer.put((byte) ((pixel >> 8) & 0xFF));  // Green
            byteBuffer.put((byte) (pixel & 0xFF));         // Blue
            byteBuffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha
        }

        return byteBuffer.array();
    }

    // Méthode pour initialiser les champs avec les données du plat
    public void initPlatData(int idPlat) {
        System.out.println("initPlatData called with idPlat: " + idPlat);
        // Récupérer les données du plat depuis la base de données
        plat = Plat.getPlatById(idPlat);

        // Vérifier si le plat existe
        if (plat != null) {
            System.out.println("Plat found: " + plat);
            // Stocker l'id du plat
            this.idPlat = idPlat;

            // Remplir les champs avec les données du plat
            nom.setText(plat.getNom());
            categorie.setValue(plat.getCategorie());
            description.setText(plat.getDescription());
            qte.getValueFactory().setValue(plat.getQte());
            prix.setText(String.valueOf(plat.getPrix()));
            // Charger l'image du plat dans l'ImageView
            imgModif.setImage(new Image(new ByteArrayInputStream(plat.getPhoto())));
        } else {
            System.out.println("Plat non trouvé.");
        }
    }

    public void initialize() {
        System.out.println("initialize called");
        // Autres initialisations...

        plat = new Plat();

        // Charger les catégories disponibles dans la ComboBox
        List<String> categories = plat.recupererCategories();
        categorie.getItems().addAll(categories);

        // Initialiser le Spinner pour la quantité avec des valeurs par défaut
        qte.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
        qte.setEditable(true);
    }
}