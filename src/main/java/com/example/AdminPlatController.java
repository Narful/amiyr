package com.example;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.sql.*;

public class AdminPlatController {

    @FXML
    private ImageView imgp;

    public void initialize() {
        Image image = getImageFromDatabase();
        if (image != null) {
            imgp.setImage(image);
        }
    }

    // Méthode pour récupérer l'image depuis la base de données
    private Image getImageFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT photo FROM plat WHERE idPlat = 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Blob blob = resultSet.getBlob("photo");
                    if (blob != null) {
                        byte[] imageData = blob.getBytes(1, (int) blob.length());
                        return new Image(new ByteArrayInputStream(imageData));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
