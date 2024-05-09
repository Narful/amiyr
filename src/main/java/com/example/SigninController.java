package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SigninController {

    @FXML
    private TextField email;

    @FXML
    private PasswordField pwd;

    @FXML
    private HBox errorBox;

    @FXML
    private HBox errorBox1;

    @FXML
    private Button ToSignup;

    public void initialize() {
        // Define the colors for normal state
        Color normalTextColor = Color.rgb(64, 64, 64);

        // Define the colors for hover state
        Color hoverTextColor = Color.WHITE;

        // Set normal state colors
        ToSignup.setTextFill(normalTextColor);
        ToSignup.setStyle("-fx-background-color: #ffffff;" + "-fx-border-radius: 3; "
                + "-fx-border-color: #404040;");

        // Add hover effect
        ToSignup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            ToSignup.setTextFill(hoverTextColor);
            ToSignup.setStyle("-fx-background-color: #404040;" + "-fx-border-radius: 5; "
                    + "-fx-border-color: #404040;");
        });

        // Reset to normal state on exit
        ToSignup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            ToSignup.setTextFill(normalTextColor);
            ToSignup.setStyle("-fx-background-color: #ffffff;" + "-fx-border-radius: 3;"
                    + "-fx-border-color: #404040;");
        });

        errorBox.setVisible(false); // Set visibility to false when the controller is initialized
        errorBox1.setVisible(false); // Set visibility to false when the controller is initialized
    }

    @FXML
    void forgetPwd(MouseEvent event) {
        String userEmail = email.getText();
        boolean receiveNewPwd = Utilisateur.motDePasseOublie(userEmail);
        if (receiveNewPwd) {
            System.out.println("Done.");
        } else {
            System.out.println("Failed.");
        }
    }

    @FXML
    void onclickSignin(ActionEvent event) {
        initialize();
        String userEmail = email.getText();
        String userPwd = pwd.getText();
        if (userEmail.isEmpty() || userPwd.isEmpty()) {
            errorBox1.setVisible(true);
            return;
        }
        boolean isUserFound = Utilisateur.seConnecter(userEmail, userPwd);
        if (isUserFound) {
            if (Utilisateur.etat.equals("suspendu")) {
                // Create an instance of the controller
                NotifSuspenduController controller = new NotifSuspenduController();
                controller.showNotification();
                return;
            } else {
                System.out.println("Login succeeded");
            }
        } else {
            errorBox.setVisible(true);
        }
    }

    @FXML
    void sendtosignup(ActionEvent event) {
        try {
            App.setRoot("signup");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
