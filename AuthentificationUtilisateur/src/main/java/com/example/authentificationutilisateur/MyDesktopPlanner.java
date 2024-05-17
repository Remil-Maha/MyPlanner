package com.example.authentificationutilisateur;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/*********** classes utiles *********************/
import ClassesUtilisees.*;
import java.util.HashMap;
/************************************************/

public class MyDesktopPlanner extends Application {

    private Stage primaryStage;
    private TextField usernameTextField;
    private Systeme myDesktopPlannerSysteme;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /***********                 le code du noyau                                                   *****/
        HashMap<String,Utilisateur> users=new HashMap<String,Utilisateur>();
        this.myDesktopPlannerSysteme=new Systeme(users);

        /*************************************************************************************************/
        this.primaryStage = primaryStage;

        // Créer la partie supérieure avec une couleur moderne
        VBox modernPart = createModernPart();

        // Créer la partie inférieure avec une couleur neutre
        VBox formPart = createFormPart();

        // Créer le layout principal pour les deux parties
        BorderPane layout = new BorderPane();
        layout.setPrefSize(1000, 700);

        // Ajouter la partie bleue à gauche
        VBox leftPart=createModernPart();
        layout.setLeft(leftPart);
        // Ajouter la partie formulaire à droite
        VBox rightPart = new VBox(10);
        rightPart.setAlignment(Pos.CENTER);
        rightPart.setPrefWidth(1000-450);
        rightPart.setPrefHeight(700);
        rightPart.getChildren().add(formPart);
        layout.setCenter(rightPart);

        // Créer la scène et afficher la fenêtre
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Authentification");
        primaryStage.show();
    }
    private VBox createFormPart() {
        VBox formPart = new VBox(150);
        formPart.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Authentification");
        usernameLabel.setStyle("-fx-text-fill: #033854;");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        usernameTextField = new TextField();
        usernameTextField.setPromptText("Nom Utilisateur:");
        usernameTextField.setText("Nom Utilisateur:");
        usernameTextField.setMaxWidth(250);


        StackPane textFieldPane = new StackPane();
        textFieldPane.getChildren().addAll(usernameTextField);
        Button loginButton = new Button("Connexion");
        loginButton.setStyle("-fx-background-color: #288D8A; -fx-border-radius: 20;");
        loginButton.setPrefWidth(90.0);
        loginButton.setMaxWidth(90.0);
        loginButton.setTextFill(Color.WHITE);


        loginButton.setOnAction(e -> handleLogin());

        Button signUpButton = new Button("Inscription");
        signUpButton.setStyle("-fx-background-color: #288D8A; -fx-border-radius: 20;");
        signUpButton.setPrefWidth(90.0);
        signUpButton.setMaxWidth(90.0);
        signUpButton.setTextFill(Color.WHITE);
        signUpButton.setOnAction(e -> handleSignUp());

        HBox buttonBox = new HBox(100);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, signUpButton);

        formPart.getChildren().addAll(usernameLabel,textFieldPane, buttonBox);

        return formPart;
    }
/*
//version1:
    private VBox createModernPart() {
        VBox modernPart = new VBox(10);
        modernPart.setAlignment(Pos.CENTER);
        modernPart.setPrefWidth(450);
        modernPart.setPrefHeight(700);
        modernPart.setBackground(new Background(new BackgroundFill(Color.web("#288D8A"), CornerRadii.EMPTY, Insets.EMPTY)));

       /* // Charger l'image à partir du répertoire "resources"
        Image image = new Image((getClass().getResource("Calender.jpeg")).toString());
        ImageView imageView = new ImageView(image);
         // Ajouter l'ImageView à votre mise en page
        modernPart.getChildren().add(imageView);
        Label titleLabel = new Label("My Desktop Planner");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        modernPart.getChildren().add(titleLabel);

        return modernPart;
    }*/

//version2:
private VBox createModernPart() {
    VBox modernPart = new VBox();
    modernPart.setAlignment(Pos.CENTER);
    modernPart.setPrefWidth(450);
    modernPart.setPrefHeight(700);
    Label titleLabel = new Label("My Desktop Planner");
    titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #033854;");

    AnchorPane anchorPane = new AnchorPane();

    Circle circle1 = new Circle(450, Color.web("#94e1b4"));
    circle1.setLayoutX(216);
    circle1.setLayoutY(241);

    Circle circle2 = new Circle(450, Color.web("#69c5a0"));
    circle2.setLayoutX(286);
    circle2.setLayoutY(300);

    Circle circle3 = new Circle(450, Color.web("#45a994"));
    circle3.setLayoutX(252);
    circle3.setLayoutY(226);

    anchorPane.getChildren().addAll(circle1, circle2, circle3,titleLabel);

    AnchorPane.setTopAnchor(circle1, -100.0);
    AnchorPane.setLeftAnchor(circle1, -400.0);
    AnchorPane.setBottomAnchor(circle1, -100.0);

    AnchorPane.setTopAnchor(circle2, -100.0);
    AnchorPane.setLeftAnchor(circle2, -500.0);
    AnchorPane.setBottomAnchor(circle2, -100.0);

    AnchorPane.setTopAnchor(circle3, -100.0);
    AnchorPane.setLeftAnchor(circle3, -600.0);
    AnchorPane.setBottomAnchor(circle3, -100.0);

    modernPart.getChildren().add(anchorPane);


    return modernPart;
}


    private void handleLogin() {
        String username = usernameTextField.getText();
        // TODO: Connecter l'utilisateur
        System.out.println("nom " + username);
        boolean connecte=this.myDesktopPlannerSysteme.authentifier(username);
        if(connecte){
            System.out.println("Connexion avec succès");
            // Navigation vers la page d'accueil
           // AccueilPage accueil = new AccueilPage();
           // accueil.show();
            AcceuilPageCalendrier acceuilPageCalendrier=new AcceuilPageCalendrier();
            acceuilPageCalendrier.show();

            // Fermer la fenêtre actuelle
            Stage primaryStage = (Stage) usernameTextField.getScene().getWindow();
            primaryStage.close();

        }else{
            System.out.println("Échec ! Nom incorrect ou créez un compte");

            // Affichage d'un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Nom d'utilisateur incorrect ou compte inexistant.");
            alert.showAndWait();
        }

    }

    private void handleSignUp() {
        // TODO: Inscrire l'utilisateur
        String username = usernameTextField.getText();
        // TODO: Connecter l'utilisateur
        System.out.println("nom " + username);
        boolean connecte=this.myDesktopPlannerSysteme.authentifier(username);
        if(connecte==true){
            System.out.println("vous avez un compte, veuillez vous connectez");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Vous avez un compte, veuillez vous connectez.");
            alert.showAndWait();
        }else{
            Utilisateur nouveau=new Utilisateur(username);
            this.myDesktopPlannerSysteme.ajouter(nouveau);
            System.out.println("inscription avec succès");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Inscription avec succès");
            alert.showAndWait();

        }
    }
}