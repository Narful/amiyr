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
import java.util.regex.Pattern;

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
    @FXML
    private Label errorLabelEmail;
    @FXML
    private Label errorLabelInvalidDomain;



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
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).find();
    }

    @FXML
    void onclickSignup(ActionEvent event) {
        condition.setVisible(false);
        hideErrorMessages();

        if (areFieldsEmpty()) {
            errorBoxChamps.setVisible(true);
            return;
        }

        if (!isValidEmail(email.getText())) {
            errorLabelEmail.setVisible(true);
            return;
        }

        if (Utilisateur.checkEmail(email.getText())) {
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

        String role = profil.getText().toLowerCase(); // Convertir le rôle en minuscules

        if (role.equals("admin") || role.equals("client")) { // Vérifier que le rôle est valide
            Utilisateur utilisateur; // Déclarer une variable de type Utilisateur
            if (role.equals("admin")) {
                utilisateur = new Admin(); // Créer une instance d'Admin si le rôle est "admin"
            } else {
                utilisateur = new Client(); // Créer une instance de Client si le rôle est "client"
            }

            // Vérifier le type d'utilisateur et appeler la méthode appropriée
            if (utilisateur instanceof Admin) {
                ((Admin) utilisateur).creerCompte(nom.getText(), prenom.getText(), email.getText(), pwd.getText(), role);
            } else if (utilisateur instanceof Client) {
                ((Client) utilisateur).creerCompte(nom.getText(), prenom.getText(), email.getText(), pwd.getText(), role);
            }

            try {
                App.setRoot("signin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur si le rôle n'est pas valide
            System.out.println("Le rôle spécifié n'est pas valide.");
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
