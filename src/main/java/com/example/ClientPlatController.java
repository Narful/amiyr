package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class ClientPlatController {


    for(int i=0;i<5;i++){
        try {
            FXMLLoader load = new FXMLLoader();
            load.getClass().getResource("cartePlatClient.fxml");
            AnchorPane pane = load.load();
            CartePlatClientController cardC = load.getController();
            cardC.setCardData("name","categorie", 15.2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    




}
