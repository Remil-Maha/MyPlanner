package com.example.authentificationutilisateur;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;

public class IntroductionCreneau extends Application {

    private DatePicker datePicker;
    private Spinner<Integer> startHourSpinner;
    private Spinner<Integer> startMinuteSpinner;
    private Spinner<Integer> endHourSpinner;
    private Spinner<Integer> endMinuteSpinner;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Formulaire de Créneau");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label dateLabel = new Label("Date:");
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        Label startTimeLabel = new Label("Heure de début:");
        startHourSpinner = createHourSpinner();
        startMinuteSpinner = createMinuteSpinner();

        Label endTimeLabel = new Label("Heure de fin:");
        endHourSpinner = createHourSpinner();
        endMinuteSpinner = createMinuteSpinner();

        Button submitButton = new Button("Enregistrer");
        submitButton.setOnAction(event -> saveCreneau());

        gridPane.add(dateLabel, 0, 0);
        gridPane.add(datePicker, 1, 0);
        gridPane.add(startTimeLabel, 0, 1);
        gridPane.add(startHourSpinner, 1, 1);
        gridPane.add(new Label(":"), 2, 1);
        gridPane.add(startMinuteSpinner, 3, 1);
        gridPane.add(endTimeLabel, 0, 2);
        gridPane.add(endHourSpinner, 1, 2);
        gridPane.add(new Label(":"), 2, 2);
        gridPane.add(endMinuteSpinner, 3, 2);
        gridPane.add(submitButton, 0, 3, 2, 1);

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Spinner<Integer> createHourSpinner() {
        Spinner<Integer> spinner = new Spinner<>(0, 23, 0);
        spinner.setEditable(true);
        return spinner;
    }

    private Spinner<Integer> createMinuteSpinner() {
        Spinner<Integer> spinner = new Spinner<>(0, 59, 0);
        spinner.setEditable(true);
        return spinner;
    }

    private void saveCreneau() {
        LocalDate selectedDate = datePicker.getValue();
        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();
        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();

        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);

        // Vérifier que la date n'est pas antérieure à la date du système
        if (selectedDate.isBefore(LocalDate.now())) {
            showAlert("Date invalide", "La date sélectionnée est antérieure à la date du système.");
            return;
        }

        // Vérifier que l'heure de fin n'est pas après l'heure de début
        if (endTime.isBefore(startTime)) {
            showAlert("Heures invalides", "L'heure de fin doit être après l'heure de début.");
            return;
        }

        // Créer un nouvel objet Creneau avec les valeurs saisies
        Creneau creneau = new Creneau(selectedDate, startTime, endTime);

        // Faire quelque chose avec l'objet Creneau (par exemple, l'enregistrer dans une base de données)
        System.out.println("Créneau enregistré: " + creneau);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
