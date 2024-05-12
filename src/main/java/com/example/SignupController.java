package com.example;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class SignupController {

    @FXML
    private TextField email;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private SplitMenuButton profil;
    @FXML
    private PasswordField pwd;
    @FXML
    private PasswordField pwd1;
    @FXML
    private HBox errorBoxChamps;
    @FXML
    private HBox errorBoxEmail;
    @FXML
    private HBox errorBoxPwdNoCondition;
    @FXML
    private HBox errorBoxPwd;
    @FXML
    private Label condition;
    @FXML
    private Button ToSignin;

    public void initialize() {
        // Define the colors for normal state
        Color normalTextColor = Color.rgb(64, 64, 64);

        // Define the colors for hover state
        Color hoverTextColor = Color.WHITE;

        // Set normal state colors
        ToSignin.setTextFill(normalTextColor);
        ToSignin.setStyle("-fx-background-color: #ffffff;" + "-fx-border-radius: 3; "
                + "-fx-border-color: #404040;");

        // Add hover effect
        ToSignin.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            ToSignin.setTextFill(hoverTextColor);
            ToSignin.setStyle("-fx-background-color: #404040;" + "-fx-border-radius: 5; "
                    + "-fx-border-color: #404040;");
        });

        // Reset to normal state on exit
        ToSignin.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            ToSignin.setTextFill(normalTextColor);
            ToSignin.setStyle("-fx-background-color: #ffffff;" + "-fx-border-radius: 3;"
                    + "-fx-border-color: #404040;");
        });

        hideErrorMessages();
    }

    @FXML
    void onclickSignup(ActionEvent event) {
        condition.setVisible(false);
        hideErrorMessages();

        if (areFieldsEmpty()) {
            errorBoxChamps.setVisible(true);
            return;
        }

        if (Utilisateur.CheckEmail(email.getText())) {
            errorBoxEmail.setVisible(true);
            return;
        }

        if (!isPasswordValid(pwd.getText())) {
            errorBoxPwdNoCondition.setVisible(true);
            return;
        }

        if (!pwd.getText().equals(pwd1.getText())) {
            errorBoxPwd.setVisible(true);
            return;
        }

        if (profil.getText().equals("Admin")) {
            Admin admin = new Admin();
            admin.creerCompte(nom.getText(), prenom.getText(), email.getText(), pwd.getText(), profil.getText());
            try {
                App.setRoot("signin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (profil.getText().equals("Client")) {
            Client client = new Client();
            client.creerCompte(nom.getText(), prenom.getText(), email.getText(), pwd.getText(), profil.getText());
            try {
                App.setRoot("signin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean areFieldsEmpty() {
        return nom.getText().isEmpty() || prenom.getText().isEmpty() || email.getText().isEmpty()
                || pwd.getText().isEmpty() || pwd1.getText().isEmpty() || profil.getText().equals("Choisir un profil");
    }

    private void hideErrorMessages() {
        errorBoxChamps.setVisible(false);
        errorBoxEmail.setVisible(false);
        errorBoxPwd.setVisible(false);
        errorBoxPwdNoCondition.setVisible(false);
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasSpecialCharacter = false;
        boolean hasNumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
            }
        }

        return hasUpperCase && hasSpecialCharacter && hasNumber;
    }

    @FXML
    void sendtosignin() throws IOException {
        App.setRoot("signin");
    }

    @FXML
    void handleMenuItemClick(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        profil.setText(menuItem.getText());
    }

}
