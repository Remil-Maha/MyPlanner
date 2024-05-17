package com.example.authentificationutilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ClassesUtilisees.Systeme;
import ClassesUtilisees.Utilisateur;

import java.util.HashSet;

public class LoginController {
    private Systeme desktopPlannerSysteme;
    @FXML
    private TextField textField;


    @FXML
    private Button login;


    @FXML
    private TextField infoUser;
    @FXML
    private Button signUp;

    public Systeme getDesktopPlannerSysteme() {
        return desktopPlannerSysteme;
    }

    public void setDesktopPlannerSysteme(Systeme desktopPlannerSysteme) {
        this.desktopPlannerSysteme = desktopPlannerSysteme;
    }

    @FXML
   public void onLoginButtonClick(ActionEvent event) {
        System.out.println("nom " + infoUser.getText());
        boolean connecte= desktopPlannerSysteme.authentifier(infoUser.getText());
        if(connecte){
            System.out.println("Connexion avec succes");
        }
    }


    /* void onLoginButtonClick(ActionEvent event) {
        System.out.println("nom " + infoUser.getText());
        HashSet<Utilisateur> utilisateurs=new HashSet<>();
        Utilisateur moi=new Utilisateur("mellissa");
        utilisateurs.add(moi);
        Systeme myDesktopPlanner =new Systeme(utilisateurs);
       boolean connecte= myDesktopPlanner.authentifier(infoUser.getText());
       if(connecte){
           System.out.println("Connexion avec succes");
       }
    }*/
}
