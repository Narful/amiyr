package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class AdminPlatController extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Récupération de l'image depuis la base de données
        Image image = getImageFromDatabase();

        // Affichage de l'image
        ImageView imageView = new ImageView(image);
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image from Database");
        primaryStage.show();
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

    public static void main(String[] args) {
        launch(args);
    }
}
