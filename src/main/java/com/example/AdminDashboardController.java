package com.example;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AdminDashboardController {

    @FXML
    private AnchorPane menuitems;

    void initialize() {

        menuitems.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), menuitems);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), menuitems);
        translateTransition.setByX(-600);
        translateTransition.play();
    }

    @FXML
    void hidemenu(MouseEvent event) {
        menuitems.setVisible(false);
    }

    @FXML
    void showmenu(MouseEvent event) {

        menuitems.setVisible(true);

    }

}
