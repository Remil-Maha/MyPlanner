package com.example.authentificationutilisateur;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PageJourSelectione extends JFrame {


    public PageJourSelectione() {
        // Configuration de la fenêtre
        setTitle("Journee");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ajout d'un label pour afficher le titre
        JLabel titleLabel = new JLabel("Titre de la nouvelle page");
        add(titleLabel);

        // Centrer la fenêtre sur l'écran
        setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        setVisible(true);
    }
}
