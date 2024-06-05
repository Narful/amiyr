package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AdminHistoriqueController {

    @FXML
    private Label par_mois;

    @FXML
    private Label par_semaine;

    @FXML
    private TableView<Commande> commandesTable;

    @FXML
    private TableColumn<Commande, String> date;

    @FXML
    private TableColumn<Commande, Integer> idCommande;

    @FXML
    private TableColumn<Commande, Boolean> status;

    @FXML
    private TableColumn<Commande, Float> montant;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Commande commandes = null;

    ObservableList<Commande> commandesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        par_semaine.getStyleClass().add("label-button");
        {
            par_mois.getStyleClass().add("label-button");
            commandesTable.setMouseTransparent(true);

            par_semaine.setOnMouseClicked(event -> {
                handleLabelClick(true);
                applyUnderlineStyle(par_semaine);
            });
            par_mois.setOnMouseClicked(event -> {
                handleLabelClick(false);
                applyUnderlineStyle(par_mois);
            });
            loadCommandesByMonth();
        }
    }

    private void handleLabelClick(boolean isParSemaine) {
        if (isParSemaine) {
            loadCommandesByWeek();
        } else {
            loadCommandesByMonth();
        }
    }

    private void applyUnderlineStyle(Label clickedLabel) {
        par_semaine.getStyleClass().remove("underline");
        par_mois.getStyleClass().remove("underline");

        clickedLabel.getStyleClass().add("underline");
    }

    public ObservableList<Commande> loadCommandesByMonth() {
        commandesList.clear();
        try {
            connection = DatabaseConnection.getConnection();
            query = "SELECT * FROM commande WHERE Creadate >= DATE_FORMAT(CURDATE() - INTERVAL DAY(CURDATE()) - 1 DAY, '%Y-%m-01')";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.date = resultSet.getString("Creadate");
                commande.idCommande = resultSet.getInt("idCommande");
                commande.status = resultSet.getBoolean("status");
                commande.montant = resultSet.getFloat("montant");
                System.out.println("Montant: " + commande.montant); // Vérifiez les valeurs ici
                commandesList.add(commande);

            }
            commandesTable.setItems(commandesList);
            idCommande.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            status.setCellFactory(column -> new TextFieldTableCell<>(new BooleanStringConverter()));
            montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // Close resultSet, preparedStatement, and connection in finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return commandesList;
    }

    public ObservableList<Commande> loadCommandesByWeek() {
        commandesList.clear();
        try {
            connection = DatabaseConnection.getConnection();
            query = "SELECT * FROM commande WHERE Creadate >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY)  AND Creadate < DATE_ADD(DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY), INTERVAL 1 WEEK)";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.date = resultSet.getString("Creadate");
                commande.idCommande = resultSet.getInt("idCommande");
                commande.status = resultSet.getBoolean("status");
                commande.montant = resultSet.getFloat("montant");
                commandesList.add(commande);

            }
            commandesTable.setItems(commandesList);
            idCommande.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            status.setCellFactory(column -> new TextFieldTableCell<>(new BooleanStringConverter()));
            montant.setCellValueFactory(new PropertyValueFactory<>("montant"));

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // Close resultSet, preparedStatement, and connection in finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return commandesList;
    }

    public class BooleanStringConverter extends javafx.util.StringConverter<Boolean> {
        @Override
        public String toString(Boolean object) {
            return object ? "livré" : "annulé";
        }

        @Override
        public Boolean fromString(String string) {
            return "livré".equals(string);
        }

    }

    

}
