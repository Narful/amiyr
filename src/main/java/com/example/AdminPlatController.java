package com.example;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.InputStream;
import java.sql.*;

public class AdminPlatController {

    @FXML
    private ImageView imgp;


    @FXML
    private AnchorPane menuitems;


    void test() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT photos FROM plat WHERE idPlat = 1");

            if (resultSet.next()) {
                // Récupérer le BLOB de la colonne photos
                InputStream inputStream = resultSet.getBlob("photos").getBinaryStream();
                // Créer une instance de Image à partir du InputStream
                Image image = new Image(inputStream);

                // Afficher l'image dans l'ImageView
                imgp.setImage(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void hidemenu(MouseEvent event) {

    }

    @FXML
    void showmenu(MouseEvent event) {

    }

}
