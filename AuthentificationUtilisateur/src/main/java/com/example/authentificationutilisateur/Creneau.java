package com.example.authentificationutilisateur;
import java.time.LocalDate;
import java.time.LocalTime;

public class Creneau {
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
   // private Tache tache;
    //Etat est une enumeration : bloqué, libre,occupé ….

  //  private EtatCreneau etat=EtatCreneau.LIBRE ;//initialement Libre

    public Creneau (LocalDate date,LocalTime heureDebut ,LocalTime heureFin) {
        this.date=date;
        this.heureDebut=heureDebut;
        this.heureFin=heureFin;
        //this.tache=tache;
      //  this.etat=etat;
    }
}