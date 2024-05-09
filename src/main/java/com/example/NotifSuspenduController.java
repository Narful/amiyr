package com.example;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NotifSuspenduController {

    private Rectangle2D primaryScreenBounds; // Declare primaryScreenBounds here

    public void showNotification() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notifSuspendu.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root, 350, 200);
            scene.setFill(Color.TRANSPARENT);

            // Set up the stage
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);

            // Get the primary screen bounds
            primaryScreenBounds = Screen.getPrimary().getVisualBounds(); // Initialize primaryScreenBounds here

            // Set the stage position to the bottom right corner
            stage.setX(primaryScreenBounds.getMaxX() - 350); // Adjust 350 based on your stage width
            stage.setY(primaryScreenBounds.getMaxY() - 200); // Adjust 200 based on your stage height

            // Show the stage
            stage.show();

            // Close the stage after 10 seconds
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> stage.close()));
            timeline.setCycleCount(1);
            timeline.play();

            System.out.println("User is suspended");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void closeNotif(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
