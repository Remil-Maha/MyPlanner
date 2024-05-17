package com.example.authentificationutilisateur;
import javafx.scene.layout.Border;

import javax.swing.*;
import java.awt.*;

public class AcceuilPageCalendrier extends JFrame{

    private com.example.authentificationutilisateur.CalendarCustom calendarCustom2;
    private javax.swing.JPanel jPanel1;

    public AcceuilPageCalendrier() {
            setTitle("Aceuil");
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(1210, 700));
            setLayout(new BorderLayout());

//----------------------------------------------- left panel

            JPanel leftPanel = new JPanel();
            leftPanel.setPreferredSize(new Dimension(222, 700));
            leftPanel.setBackground(new Color(40, 141, 141));
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            add(leftPanel, BorderLayout.WEST);

// Element 1: Icône + Nom d'utilisateur
            JPanel userInfoPanel = new JPanel();
            userInfoPanel.setBackground(new Color(40, 141, 141));
            userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marge intérieure
            userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.X_AXIS));
           // userInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 20));

            ImageIcon userIcon = new ImageIcon("user_icon.png"); // Remplacez "user_icon.png" par le chemin de votre icône
            JLabel userIconLabel = new JLabel(userIcon);
            userInfoPanel.add(userIconLabel);

            userInfoPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Espacement horizontal de 10px

            String username = "Nom d'utilisateur"; // Remplacez par le nom d'utilisateur réel
            JLabel usernameLabel = new JLabel(username);
            usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.BOLD, 25f)); // Ajuster la police du texte de l'utilisateur
            usernameLabel.setForeground(Color.WHITE);
            usernameLabel.setHorizontalAlignment(JLabel.RIGHT);
            userInfoPanel.add(usernameLabel);

            leftPanel.add(userInfoPanel);

            leftPanel.add(Box.createRigidArea(new Dimension(0, 80))); // Espacement vertical de 80px

// 6 boutons
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

            buttonsPanel.setBackground(new Color(40, 141, 141));
           String[] noms={
                   "Mes tâches",
                   "Mes palanings",
                   "Mes catégories",
                   "Mes projets",
                   "Mes réalisation",
                   "Historique"
           };

            for (int i = 1; i <= 6; i++) {
                    JButton button = new JButton(noms[i-1]);
                    button.setPreferredSize(new Dimension(180, button.getPreferredSize().height)); // Ajuster la largeur des boutons
                    buttonsPanel.add(button);
                    buttonsPanel.add(Box.createRigidArea(new Dimension(0, 35))); // Espacement vertical de 30px entre les boutons
            }

            leftPanel.add(buttonsPanel);
            leftPanel.add(Box.createVerticalGlue()); // Espace vertical extensible

// Boutons Paramètre et Déconnexion
            JPanel bottomButtonsPanel = new JPanel();
            bottomButtonsPanel.setLayout(new BoxLayout(bottomButtonsPanel, BoxLayout.Y_AXIS));

            bottomButtonsPanel.setBackground(new Color(40, 141, 141));

            JButton parametreButton = new JButton("Paramètre");
            JButton deconnexionButton = new JButton("Déconnexion");

            bottomButtonsPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Espacement de 50px avant les boutons Paramètre et Déconnexion
            bottomButtonsPanel.add(parametreButton);
            bottomButtonsPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Espacement de 20px entre les boutons Paramètre et Déconnexion
            bottomButtonsPanel.add(deconnexionButton);

            leftPanel.add(bottomButtonsPanel);

            //--------------
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setPreferredSize(new Dimension(594, 700));
//-------------------------------------------------------------------
            JPanel topPanel = new JPanel();
            topPanel.setPreferredSize(new Dimension(594, 96));
            topPanel.setBackground(new Color(40, 141, 141));
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Utilisation de BoxLayout pour centrer les composants verticalement

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Espacement horizontal de 20px
            buttonPanel.setBackground(new Color(40, 141, 141));
            JButton boutton1 = new JButton("Etudes");
            JButton boutton2 = new JButton("Travail");
            JButton boutton3 = new JButton("Sport");
            JButton boutton4 = new JButton("Famille");
            JButton boutton5 = new JButton("Personnelle");

            Dimension buttonSize = new Dimension(105, boutton1.getPreferredSize().height); // Taille personnalisée (30px de largeur)
            boutton1.setPreferredSize(buttonSize);
            boutton2.setPreferredSize(buttonSize);
            boutton3.setPreferredSize(buttonSize);
            boutton4.setPreferredSize(buttonSize);
            boutton5.setPreferredSize(buttonSize);

            buttonPanel.add(boutton1);
            buttonPanel.add(boutton2);
            buttonPanel.add(boutton3);
            buttonPanel.add(boutton4);
            buttonPanel.add(boutton5);

            topPanel.add(buttonPanel);
            centerPanel.add(topPanel);
//-------------------------------------------------------------------------------------------------
            JPanel bottomPanel = new JPanel();
            bottomPanel.setPreferredSize(new Dimension(614, 627));

            calendarCustom2 = new com.example.authentificationutilisateur.CalendarCustom();//888
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            bottomPanel.setBackground(new java.awt.Color(255,255,255));
            calendarCustom2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(40, 141, 141)));

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(bottomPanel);
            bottomPanel.setLayout(jPanel1Layout);

            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(40, 40, 40)
                                    .addComponent(calendarCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(381, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addComponent(calendarCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(122, Short.MAX_VALUE))
            );

            //************
            centerPanel.add(bottomPanel);

            add(centerPanel, BorderLayout.CENTER);
            //right
            //--------- right panel
            JPanel rightPanel = new JPanel();
            rightPanel.setPreferredSize(new Dimension(186, 700));
            rightPanel.setBackground(new Color(40, 141, 141));
            add(rightPanel, BorderLayout.EAST);

            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            rightPanel.setLayout( new FlowLayout(FlowLayout.CENTER, 20, 50) );

            // 3 boutons alignés au centre

            // Boutons "Introduire Tache", "Ajouter Creneau", "Fixer Periode", "Planifier Tache"
            JButton introduireTacheButton = new JButton("Introduire Tache");
            JButton ajouterCreneauButton = new JButton("Ajouter Creneau");
            JButton fixerPeriodeButton = new JButton("Fixer Periode");
            JButton planifierTacheButton = new JButton("Planifier Tache");
            rightPanel.add(introduireTacheButton);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement vertical de 10px
            rightPanel.add(ajouterCreneauButton);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement vertical de 10px
            rightPanel.add(fixerPeriodeButton);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement vertical de 10px
            rightPanel.add(planifierTacheButton);

            pack();
            setVisible(true);
        }

        public static void main(String[] args) {
            AcceuilPageCalendrier swingExample = new AcceuilPageCalendrier();
        }


}
