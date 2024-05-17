package com.example.authentificationutilisateur;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class FixerPeriode extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Création d'une période");

        // Création des DatePicker pour les dates de début et de fin
        DatePicker datePickerDebut = new DatePicker();
        DatePicker datePickerFin = new DatePicker();

        // Création des labels pour les DatePicker
        Label lblDebut = new Label("Date de début:");
        Label lblFin = new Label("Date de fin:");

        // Création du bouton de validation
        Button btnValider = new Button("Valider");
        btnValider.setOnAction(event -> {
            LocalDate dateDebut = datePickerDebut.getValue();
            LocalDate dateFin = datePickerFin.getValue();

            if (dateFin.isBefore(dateDebut)) {
                // Affichage d'une alerte si la date de fin est antérieure à la date de début
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("La date de fin est antérieure à la date de début");
                alert.showAndWait();
            } else {
                // Création de l'objet Periode avec les dates sélectionnées
                Periode periode = new Periode(dateDebut, dateFin);
                System.out.println("Période créée : " + periode);
            }
        });

        // Création de la grille pour le formulaire
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        // Ajout des composants à la grille
        gridPane.add(lblDebut, 0, 0);
        gridPane.add(datePickerDebut, 1, 0);
        gridPane.add(lblFin, 0, 1);
        gridPane.add(datePickerFin, 1, 1);

        // Création de la boîte horizontale pour le bouton de validation
        HBox hbox = new HBox(btnValider);
        hbox.setSpacing(10);

        // Création de la boîte verticale pour contenir la grille et la boîte horizontale
        VBox vbox = new VBox(gridPane, hbox);
        vbox.setSpacing(10);

        // Création de la scène et affichage de la fenêtre
        Scene scene = new Scene(vbox, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Classe Periode
    public static class Periode {
        private LocalDate dateDebut;
        private LocalDate dateFin;

        public Periode(LocalDate dateDebut, LocalDate dateFin) {
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
        }

    }
}