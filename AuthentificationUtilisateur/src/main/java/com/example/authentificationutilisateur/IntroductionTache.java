package com.example.authentificationutilisateur;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;

public class IntroductionTache  extends Application {

    // Champs du formulaire
    private TextField nomField;
    private ComboBox<Priorite> prioriteComboBox;
    private TextField dureeField;
    private DatePicker dateLimitePicker;
    private ComboBox<String> categorieComboBox;
    private ComboBox<TypeTache> typeComboBox;
    private TextField periodiciteField;
    private Stage periodiciteStage;
    private TacheSimple tacheSimple=null;

    private Tache tacheIntroduite=null;

    @Override
    public void start(Stage primaryStage) {
        // Création des éléments du formulaire
        Label nomLabel = new Label("Nom:");
        nomField = new TextField();

        Label prioriteLabel = new Label("Priorité:");
        ObservableList<Priorite> prioriteOptions = FXCollections.observableArrayList(Priorite.values());
        prioriteComboBox = new ComboBox<>(prioriteOptions);

        Label dureeLabel = new Label("Durée:");
        dureeField = new TextField();

        Label dateLimiteLabel = new Label("Date limite:");
        dateLimitePicker = new DatePicker();

        Label categorieLabel = new Label("Catégorie:");
        ObservableList<String> categorieOptions = FXCollections.observableArrayList("Études", "Sport", "Travail", "Loisirs");
        categorieComboBox = new ComboBox<>(categorieOptions);

        Label typeLabel = new Label("Type:");
        ObservableList<TypeTache> typeOptions = FXCollections.observableArrayList(TypeTache.values());
        typeComboBox = new ComboBox<>(typeOptions);

        Label periodiciteLabel = new Label("Périodicité:");
        periodiciteField = new TextField();

        Button creerButton = new Button("Créer");
        creerButton.setOnAction(event -> creerTache());

        // Création de la grille de mise en page
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(nomLabel, 0, 0);
        gridPane.add(nomField, 1, 0);
        gridPane.add(prioriteLabel, 0, 1);
        gridPane.add(prioriteComboBox, 1, 1);
        gridPane.add(dureeLabel, 0, 2);
        gridPane.add(dureeField, 1, 2);
        gridPane.add(dateLimiteLabel, 0, 3);
        gridPane.add(dateLimitePicker, 1, 3);
        gridPane.add(categorieLabel, 0, 4);
        gridPane.add(categorieComboBox, 1, 4);
        gridPane.add(typeLabel, 0, 5);
        gridPane.add(typeComboBox, 1, 5);
        gridPane.add(creerButton, 0, 6, 2, 1);

        // Création de la scène
        Scene scene = new Scene(gridPane);

        // Configuration de la fenêtre principale
        primaryStage.setTitle("Formulaire de création de tâche");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void creerTache() {
        String nom = nomField.getText();
        Priorite priorite = prioriteComboBox.getValue();
        int duree = Integer.parseInt(dureeField.getText());
        LocalDate dateLimite = dateLimitePicker.getValue();
        String categorie = categorieComboBox.getValue();
        TypeTache type = typeComboBox.getValue();
        Tache tache=null;
        if (type == TypeTache.SIMPLE) {
            tache=donnerPeriodicite(nom, priorite, duree, dateLimite, categorie);

        } else if (type == TypeTache.DECOMPOSABLE) {
           tache= new TacheDecomposable(nom, priorite, duree, dateLimite, categorie);
        }

        if (type == TypeTache.SIMPLE) {
            TacheSimple tacheSimple1=(TacheSimple)tache;
            if(duree<=0|| tacheSimple1.periodicite<0 ){
                resetForm();
                afficherErreur("Attention! Veuillez donner une durée >0 et une periodicité>=0.");
            }else{
                afficherTacheCreee(tache);
            }

        } else if (type == TypeTache.DECOMPOSABLE) {
            if(duree<=0 ){
                resetForm();
                afficherErreur("Attention! Veuillez donner une durée >0 .");
            }else{
                afficherTacheCreee(tache);
            }
        }


        //la tache introduite
        this.tacheIntroduite=tache;
    }

    private TacheSimple donnerPeriodicite(String nom, Priorite priorite, int duree, LocalDate dateLimite, String categorie) {
        // Création de la fenêtre pour la périodicité
        TacheSimple tacheSimple=null;
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label periodiciteLabel = new Label("Périodicité:");
        TextField periodiciteField = new TextField();
        Button finButton = new Button("Fin");

        gridPane.add(periodiciteLabel, 0, 0);
        gridPane.add(periodiciteField, 1, 0);
        gridPane.add(finButton, 0, 1, 2, 1);
        Scene scene = new Scene(gridPane);
        stage.setTitle("Périodicité");
        stage.setScene(scene);
        stage.show();


        finButton.setOnAction(event -> {
            int periodicite = 0;
            String periodiciteText = periodiciteField.getText();
            if (!periodiciteText.isEmpty()) {
                periodiciteText="0";
                periodicite = Integer.parseInt(periodiciteText);
            }
             this.tacheSimple = new TacheSimple(nom, priorite, duree, dateLimite, categorie, periodicite);

            stage.close();

        });


        return this.tacheSimple;
    }

    private void afficherTacheCreee(Tache tache) {
        JOptionPane.showMessageDialog(null, "Tâche créée : " + tache.toString());
    }

    private void resetForm() {
        nomField.clear();
        prioriteComboBox.getSelectionModel().clearSelection();
        dureeField.clear();
        dateLimitePicker.setValue(null);
        categorieComboBox.getSelectionModel().clearSelection();
        typeComboBox.getSelectionModel().clearSelection();
        periodiciteField.clear();
    }
    private void afficherErreur(String message) {
        // Affichage d'un message d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreurs");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Enumération pour les priorités
    public enum Priorite {
        LOW, MEDIUM, HIGH
    }

    // Enumération pour les types de tâches
    public enum TypeTache {
        SIMPLE, DECOMPOSABLE
    }

    // Classe Tache
    public static class Tache {
        protected String nom;
        protected Priorite priorite;
        protected int duree;
        protected LocalDate dateLimite;
        protected String categorie;

        public Tache(String nom, Priorite priorite, int duree, LocalDate dateLimite, String categorie) {
            this.nom = nom;
            this.priorite = priorite;
            this.duree = duree;
            this.dateLimite = dateLimite;
            this.categorie = categorie;
        }

        @Override
        public String toString() {
            return "Tache{" +
                    "nom='" + nom + '\'' +
                    ", priorite=" + priorite +
                    ", duree=" + duree +
                    ", dateLimite=" + dateLimite +
                    ", categorie='" + categorie + '\'' +
                    '}';
        }
    }

    // Classe TacheSimple, sous-classe de Tache
    public static class TacheSimple extends Tache {
        private int periodicite;

        public TacheSimple(String nom, Priorite priorite, int duree, LocalDate dateLimite, String categorie, int periodicite) {
            super(nom, priorite, duree, dateLimite, categorie);
            this.periodicite = periodicite;
        }

        @Override
        public String toString() {
            return super.toString() + ", periodicite=" + periodicite;
        }
    }

    // Classe TacheDecomposable, sous-classe de Tache
    public static class TacheDecomposable extends Tache {
        public TacheDecomposable(String nom, Priorite priorite, int duree, LocalDate dateLimite, String categorie) {
            super(nom, priorite, duree, dateLimite, categorie);
        }
    }
}
