package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboardController {

    @FXML
    private AnchorPane menuitems;
    private LineChart<String, Number> revenuMensuelChart;

    @FXML
    private BarChart<String, Number> platChart;
    private BarChart<String, Number> revenuPlatChart;


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
    void toPlat(MouseEvent event) {
        try {
            App.setRoot("PlatAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    @FXML
    void forMonth(ActionEvent event) {
        // Réinitialiser les dates pour le mois actuel
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date dateDebut = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date dateFin = cal.getTime();

        // Mettre à jour les données dans les graphiques
        updateGraphs(dateDebut, dateFin);
    }

    @FXML
    void forWeek(ActionEvent event) {
        // Réinitialiser les dates pour la semaine actuelle
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date dateDebut = cal.getTime();
        cal.add(Calendar.DATE, 6);
        Date dateFin = cal.getTime();

        // Mettre à jour les données dans les graphiques
        updateGraphs(dateDebut, dateFin);
    }

    private void updateGraphs(Date dateDebut, Date dateFin) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            populateRevenuPlatChart(connection, dateDebut, dateFin, platChart, false);
            populateStackedBarChart(connection, dateDebut, dateFin, stackedBarChart);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBarChart(Date dateDebut, Date dateFin, boolean isForMonth) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            populateRevenuPlatChart(connection, dateDebut, dateFin, platChart, isForMonth);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        try {
            // Récupérer le mois actuel
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1

            // Créer la date de début pour le mois actuel
            cal.set(Calendar.DAY_OF_MONTH, 1); // Mettre le jour au premier jour du mois
            Date dateDebut = cal.getTime(); // Convertir en objet Date

            // Créer la date de fin pour le mois actuel
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Mettre le jour au dernier jour du mois
            Date dateFin = cal.getTime(); // Convertir en objet Date

            // Appel des méthodes pour peupler les graphiques
            Connection connection = DatabaseConnection.getConnection();
            populateRevenuPlatChart(connection, dateDebut, dateFin, platChart, false);
            populateStackedBarChart(connection, dateDebut, dateFin, stackedBarChart);

            // Appel de la méthode pour peupler le graphique de revenus mensuels
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour peupler les graphes avec les données des plats les plus vendus
    public void populateRevenuPlatChart(Connection connection, Date dateDebut, Date dateFin,
            BarChart<String, Number> revenuPlatChart, boolean isForMonth) {
        Admin admin = new Admin();
        Map<String, Integer> revenuPlats = admin.calculerPlatsLesPlusVendus(connection, dateDebut, dateFin);

        revenuPlatChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenu par plat");

        for (Map.Entry<String, Integer> entry : revenuPlats.entrySet()) {
            String platName = entry.getKey();
            // Tronquer le nom du plat si nécessaire pour éviter le chevauchement
            if (platName.length() > 10) {
                platName = platName.substring(0, 10) + "..."; // Tronquer le nom à 10 caractères
            }
            series.getData().add(new XYChart.Data<>(platName, entry.getValue()));
        }

        revenuPlatChart.getData().add(series);
        revenuPlatChart.setTitle("Revenu des plats les plus vendus");

        // Ajuster la position et l'alignement des étiquettes
        for (Node bar : revenuPlatChart.lookupAll(".default-color0.chart-bar")) {
            if (bar instanceof StackPane) {
                for (Node node : ((StackPane) bar).getChildren()) {
                    if (node instanceof Text) {
                        if (isForMonth) {
                            ((Text) node).setRotate(-90); // Rotation de l'étiquette à 90 degrés pour un meilleur
                                                          // ajustement vertical
                            ((Text) node).setTranslateX(-10); // Déplacer l'étiquette vers la gauche pour un meilleur
                                                              // alignement
                        } else {
                            ((Text) node).setRotate(90); // Rotation de l'étiquette à -90 degrés pour afficher du haut
                                                         // vers le bas
                            ((Text) node).setTranslateX(10); // Déplacer l'étiquette vers la droite pour un meilleur
                                                             // alignement
                        }
                    }
                }
            }
        }

        // Désactiver l'animation des barres pour éviter les mouvements indésirables
        revenuPlatChart.setAnimated(false);
    }

    // Votre méthode calculerCommandesLivreesEtAnnulees
    public void populateStackedBarChart(Connection connection, Date dateDebut, Date dateFin,
            StackedBarChart<String, Number> stackedBarChart) {
        Map<String, Integer[]> dailyOrders = getDailyOrders(connection, dateDebut, dateFin);

        stackedBarChart.getData().clear();

        XYChart.Series<String, Number> seriesDelivered = new XYChart.Series<>();
        seriesDelivered.setName("Commandes Livrées");

        XYChart.Series<String, Number> seriesCancelled = new XYChart.Series<>();
        seriesCancelled.setName("Commandes Annulées");

        for (Map.Entry<String, Integer[]> entry : dailyOrders.entrySet()) {
            String day = entry.getKey();
            Integer[] orders = entry.getValue();
            seriesDelivered.getData().add(new XYChart.Data<>(day, orders[0]));
            seriesCancelled.getData().add(new XYChart.Data<>(day, orders[1]));
        }

        stackedBarChart.getData().addAll(seriesDelivered, seriesCancelled);
        stackedBarChart.setAnimated(false);
        stackedBarChart.setTitle("Statistiques de commandes par jour");
    }

    // Méthode pour obtenir les commandes livrées et annulées par jour
    private Map<String, Integer[]> getDailyOrders(Connection connection, Date dateDebut, Date dateFin) {
        Map<String, Integer[]> dailyOrders = new HashMap<>();
        String query = "SELECT DATE_FORMAT(Creadate, '%Y-%m-%d') AS day, " +
                "SUM(CASE WHEN l.status = 1 THEN 1 ELSE 0 END) AS delivered, " +
                "SUM(CASE WHEN l.status = -1 THEN 1 ELSE 0 END) AS cancelled " +
                "FROM commande c " +
                "INNER JOIN livraison l ON c.idCommande = l.idCommande " +
                "WHERE c.Creadate BETWEEN ? AND ? " +
                "GROUP BY DATE_FORMAT(Creadate, '%Y-%m-%d')";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(dateDebut.getTime()));
            statement.setDate(2, new java.sql.Date(dateFin.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String day = resultSet.getString("day");
                int delivered = resultSet.getInt("delivered");
                int cancelled = resultSet.getInt("cancelled");
                dailyOrders.put(day, new Integer[] { delivered, cancelled });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dailyOrders;
    }

    private Map<String, Integer[]> getWeeklyOrders(Connection connection, Date dateDebut, Date dateFin) {
        Map<String, Integer[]> weeklyOrders = new HashMap<>();
        String query = "SELECT YEARWEEK(Creadate, 1) AS week, " +
                "SUM(CASE WHEN l.status = 1 THEN 1 ELSE 0 END) AS delivered, " +
                "SUM(CASE WHEN l.status = -1 THEN 1 ELSE 0 END) AS cancelled " +
                "FROM commande c " +
                "INNER JOIN livraison l ON c.idCommande = l.idCommande " +
                "WHERE c.Creadate BETWEEN ? AND ? " +
                "GROUP BY YEARWEEK(Creadate, 1)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(dateDebut.getTime()));
            statement.setDate(2, new java.sql.Date(dateFin.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String week = resultSet.getString("week");
                int delivered = resultSet.getInt("delivered");
                int cancelled = resultSet.getInt("cancelled");
                weeklyOrders.put(week, new Integer[] { delivered, cancelled });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklyOrders;
    }

    // Méthode pour obtenir les commandes livrées et annulées pour un jour
    // spécifique
    private Integer[] getOrdersForDay(Connection connection, Date day) {
        Admin admin = new Admin();
        return admin.getOrdersForDay(connection, day);
    }

    // Méthode pour formater une date en chaîne de caractères
    private String formatDate(Date date) {
        // Ici, vous pouvez utiliser SimpleDateFormat ou une autre méthode pour formater
        // la date selon vos préférences
        return ""; // Implémentez votre logique de formatage ici
    }

    private Map<String, Integer[]> getMonthlyOrders(Connection connection, Date dateDebut, Date dateFin) {
        Map<String, Integer[]> monthlyOrders = new HashMap<>();
        // Votre requête SQL pour récupérer les commandes par mois
        // Assurez-vous de grouper les commandes par mois
        String query = "SELECT DATE_FORMAT(Creadate, '%Y-%m') AS month, " +
                "SUM(CASE WHEN l.status = 1 THEN 1 ELSE 0 END) AS delivered, " +
                "SUM(CASE WHEN l.status = -1 THEN 1 ELSE 0 END) AS cancelled " +
                "FROM commande c " +
                "INNER JOIN livraison l ON c.idCommande = l.idCommande " +
                "WHERE c.Creadate BETWEEN ? AND ? " +
                "GROUP BY DATE_FORMAT(Creadate, '%Y-%m')";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(dateDebut.getTime()));
            statement.setDate(2, new java.sql.Date(dateFin.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int delivered = resultSet.getInt("delivered");
                int cancelled = resultSet.getInt("cancelled");
                monthlyOrders.put(month, new Integer[] { delivered, cancelled });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyOrders;
    }

    // Votre méthode calculerPlatsLesPlusVendus
    public Map<String, Integer> calculerPlatsLesPlusVendus(Connection connection, Date dateDebut, Date dateFin) {
        Admin admin = new Admin();
        return admin.calculerPlatsLesPlusVendus(connection, dateDebut, dateFin);
    }

    // Votre méthode calculerCommandesLivreesEtAnnulees
    public int[] getCommandesLivreesEtAnnulees(Connection connection, Date dateDebut, Date dateFin) {
        Admin admin = new Admin();

        // Calculer les commandes livrées et annulées pour la période spécifiée
        Map<String, Integer> commandes = admin.calculerCommandesLivreesEtAnnulees(connection, dateDebut, dateFin);

        // Créer un tableau de deux éléments pour stocker les commandes livrées et
        // annulées
        int[] result = new int[2];

        // Remplir le tableau avec les valeurs de la map
        result[0] = commandes.getOrDefault("livrees", 0);
        result[1] = commandes.getOrDefault("annulees", 0);

        return result;
    }
*/
}
