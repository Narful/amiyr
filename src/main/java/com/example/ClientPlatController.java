package com.example;

import javafx.event.ActionEvent;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;

public class ClientPlatController {

    @FXML
    private HBox menuContainer;

    @FXML
    private VBox panierContainer;

    @FXML
    private AnchorPane menuitems;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private AnchorPane CompteForm;

    @FXML
    private AnchorPane commandeForm;

    @FXML
    private AnchorPane menuForm;

    @FXML
    private JFXButton CommandeBtn;

    @FXML
    private JFXButton accueilBtn;

    @FXML
    private JFXButton compteBtn;

    @FXML
    private JFXButton disconnectBtn;

    @FXML
    private AnchorPane panierForm;

    @FXML
    private Button panierBtn;

    @FXML
    private Button commanderBtn;



    double progress;

    public void initialize() {

        Plat plat = new Plat();
        List<Plat> plats = plat.consulterPlats();

        for (Plat p : plats) {
            System.out.println("Plat trouvé: " + p.nom + " - " + p.categorie);
        }

        for (Plat p : plats) {
            try {
                // Charger le fichier FXML pour chaque plat
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cartePlatClient.fxml"));
                Node cartePlatNode = loader.load();

                // Obtenir le contrôleur associé au fichier FXML
                CartePlatClientController controller = loader.getController();
                // Passer les données du plat au contrôleur de la carte
                controller.setPlat(p.idPlat,p.nom, p.categorie, p.photo, p.prix);

                // Ajouter le node au HBoxContainer
                menuContainer.getChildren().add(cartePlatNode);

                // Ajouter un espaceur entre les cartes
                //Region spacer = new Region();
                //spacer.setPrefWidth(20); // Taille de l'espace
                //menuContainer.getChildren().add(spacer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPanier(Plat plat){
        
        
        //!! ghadi nakhdo l'objet plat ou n'ajoutiwh fl HBox dial lpanier 

    }

    public void startProgressBar(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final int totalSeconds = 600;
                for(int i = 0;i<=totalSeconds;i++){
                    updateProgress(i, totalSeconds);
                    Thread.sleep(1000);

                }
                return null;
            }
        };
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }


    //for testing 
    public void increaseProgressBar(){
        progress = progress + 0.01;
        progressBar.setProgress(progress);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == accueilBtn) {
            menuForm.setVisible(true);
            commandeForm.setVisible(false);
            CompteForm.setVisible(false);
        } else if (event.getSource() == compteBtn) {
            menuForm.setVisible(false);
            commandeForm.setVisible(false);
            CompteForm.setVisible(true);
        } else if (event.getSource() == CommandeBtn){
            menuForm.setVisible(false);
            commandeForm.setVisible(true);
            CompteForm.setVisible(false);
        }else if (event.getSource() == panierBtn){
            if (panierForm.isVisible()){
                panierForm.setVisible(false);
            }else{
                panierForm.setVisible(true);
            }
        }else if (event.getSource() == commanderBtn){
            menuForm.setVisible(false);
            commandeForm.setVisible(true);
            CompteForm.setVisible(false);
            startProgressBar();
        }else if (event.getSource() == disconnectBtn){
            try {
                App.setRoot("signin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } 


}