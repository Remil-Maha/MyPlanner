package com.example.authentificationutilisateur;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class AccueilPage extends Stage {

    public AccueilPage() {
        setTitle("Page d'accueil");

        // Création du contenu de la page d'accueil
        Label welcomeLabel = new Label("Bienvenue sur la page d'accueil!");

        // Création des sidebars
        StackPane sidebarLeft = new StackPane();
        sidebarLeft.setPrefWidth(100);
        sidebarLeft.setStyle("-fx-background-color: #033854;");

        StackPane sidebarTop = new StackPane();
        sidebarTop.setPrefHeight(70);
        sidebarTop.setStyle("-fx-background-color: #033854;");

        // Création du layout principal avec le contenu et les sidebars
        BorderPane root = new BorderPane();
        root.setCenter(welcomeLabel);
        root.setLeft(sidebarLeft);
        root.setTop(sidebarTop);

        // Personnalisation du layout principal
        root.setMargin(welcomeLabel, new Insets(10));
        root.setMargin(sidebarLeft, new Insets(10));
        root.setMargin(sidebarTop, new Insets(10));

        // Création de la scène
        Scene scene = new Scene(root, 1000, 700);
        scene.setFill(Color.WHITE);

        setScene(scene);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
