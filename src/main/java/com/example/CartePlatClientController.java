package com.example;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CartePlatClientController {

    @FXML
    private Button addCartBtn;

    @FXML
    private Label categoriePlatLabel;

    @FXML
    private ImageView imagePlat;

    @FXML
    private Label nomPlatLabel;

    @FXML
    private Label prixPlatLabel;

    @FXML
    private Label ratingPlatLabel;

    @FXML
    private Label idPlatLabel;

    


    public void setPlat(int idPlat, String nomPlat, String categoriePlat, byte[] image,float prix) {
        idPlatLabel.setText(idPlat + "");
        nomPlatLabel.setText(nomPlat);
        categoriePlatLabel.setText(categoriePlat);
        prixPlatLabel.setText(prix + "Dh");
        if (image != null && image.length > 0) {
            imagePlat.setImage(Plat.convertToJavaFXImage(image, 200, 150)); // Conversion et affichage de l'image
            imagePlat.setFitWidth(215);
            imagePlat.setFitHeight(150);
            imagePlat.setPreserveRatio(false);
            imagePlat.setSmooth(true);
        }
    }

    public Plat returnPlatObj(){
        //List<Plat> platlist = new ArrayList<>();
        int idPlat = Integer.parseInt(idPlatLabel.getText());
        String nomPlat = nomPlatLabel.getText();
        String categoriePlat = categoriePlatLabel.getText();
        float prixPlat = Float.parseFloat(prixPlatLabel.getText().replace("Dh", ""));
        
        Plat plat = new Plat();
        plat.idPlat = idPlat;
        plat.nom = nomPlat;
        plat.prix = prixPlat;
        //platlist.add(plat);
        
        ClientPlatController clientPlatController = new ClientPlatController();
        clientPlatController.addPanier(plat);
        return plat;
    }




}