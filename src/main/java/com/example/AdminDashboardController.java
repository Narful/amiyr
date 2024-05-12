package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class AdminDashboardController {

    @FXML
    private AnchorPane menuitems;
    private LineChart<String, Number> revenuMensuelChart;

    @FXML
    private BarChart<String, Number> platChart;

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    void hidemenu(MouseEvent event) {
        menuitems.setVisible(false);
    }

    @FXML
    void showmenu(MouseEvent event) {
        menuitems.setVisible(true);
    }

    @FXML
    void forMonth(ActionEvent event) {

    }

    @FXML
    void forWeek(ActionEvent event) {

    }

    @FXML
    void initialize() {
        try {
            // Récupérer le mois actuel
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1

            // Créer la date de début pour le mois actuel
            cal.set(Calendar.DAY_OF_MONTH, 1); // Mettre le jour au premier jour du mois
            Date dateDebut = cal.getTime(); // Convertir en objet Date

            // Créer la date de fin pour le mois actuel
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Mettre le jour au dernier
                                                                                         // jour du mois
            Date dateFin = cal.getTime(); // Convertir en objet Date

            // Appel des méthodes pour peupler les graphiques
            Connection connection = DatabaseConnection.getConnection();
            populateRevenuPlatChart(connection, dateDebut, dateFin, platChart);
            populateStackedBarChart(connection, dateDebut, dateFin, stackedBarChart);

            // Appel de la méthode pour peupler le graphique de revenus mensuels
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour peupler les graphes avec les données des plats les plus vendus
    public void populateRevenuPlatChart(Connection connection, Date dateDebut, Date dateFin,
            BarChart<String, Number> revenuPlatChart) {
        Admin admin = new Admin();
        Map<String, Integer> revenuPlats = admin.calculerRevenuPlats(connection, dateDebut, dateFin);

        revenuPlatChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenu par plat");

        for (Map.Entry<String, Integer> entry : revenuPlats.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        revenuPlatChart.getData().add(series);
        revenuPlatChart.setTitle("Revenu des plats les plus vendus");
    }

    // Votre méthode calculerCommandesLivreesEtAnnulees
    public void populateStackedBarChart(Connection connection, Date dateDebut, Date dateFin,
            StackedBarChart<String, Number> stackedBarChart) {
        Admin admin = new Admin();
        int[] commandes = admin.calculerCommandesLivreesEtAnnulees(connection, dateDebut, dateFin);

        stackedBarChart.getData().clear();
        XYChart.Series<String, Number> seriesLivrees = new XYChart.Series<>();
        seriesLivrees.setName("Commandes Livrées");
        seriesLivrees.getData().add(new XYChart.Data<>("", commandes[0]));

        XYChart.Series<String, Number> seriesAnnulees = new XYChart.Series<>();
        seriesAnnulees.setName("Commandes Annulées");
        seriesAnnulees.getData().add(new XYChart.Data<>("", commandes[1]));

        stackedBarChart.getData().addAll(seriesLivrees, seriesAnnulees);

        // Ajoutez une classe CSS personnalisée pour positionner la légende
        stackedBarChart.getStyleClass().add("legend-position");
        stackedBarChart.setTitle("Statistiques de commandes");

    }

    // Votre méthode calculerPlatsLesPlusVendus
    public Map<String, Integer> calculerPlatsLesPlusVendus(Connection connection, Date dateDebut, Date dateFin) {
        Admin admin = new Admin();
        return admin.calculerRevenuPlats(connection, dateDebut, dateFin);
    }

    // Votre méthode calculerCommandesLivreesEtAnnulees
    public int[] calculerCommandesLivreesEtAnnulees(Connection connection, Date dateDebut, Date dateFin) {
        Admin admin = new Admin();
        return admin.calculerCommandesLivreesEtAnnulees(connection, dateDebut, dateFin);
    }

}
