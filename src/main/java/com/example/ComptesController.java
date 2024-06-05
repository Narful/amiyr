package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ComptesController {

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private ImageView logoAmiyr;

    @FXML
    private AnchorPane menuitems1;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> role;

    @FXML
    private TableView<?> userTable;

    @FXML
    void SuppRowCpt(ActionEvent event) {

    }

    @FXML
    void handleAddChef(ActionEvent event) {

    }

    @FXML
    void toCmd(ActionEvent event) {

    }

    @FXML
    void toHis(ActionEvent event) {
        try {
            App.setRoot("historiqueAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toHome(ActionEvent event) {
        try {
            App.setRoot("DashboardAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toLiv(ActionEvent event) {

    }

    @FXML
    void toLogout(ActionEvent event) {
        try {
            App.setRoot("signin");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void toPlat(ActionEvent event) {
        try {
            App.setRoot("PlatAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
