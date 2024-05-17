package Noyau.Classes;

import java.time.LocalDate;

public class TacheSimple extends Tache {
    private Creneau creneau; //elle est planifeir sur un seul creneau
    private  int periodicite; // elle est planifier tout

    public Creneau getCreneau() {
        return creneau;
    }
    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }
    public int getPeriodicite() {
        return periodicite;
    }
    public void setPeriodicite(int periodicite) {
        this.periodicite = periodicite;
    }

    public TacheSimple(String nom, Priorite priorite, int duree, LocalDate dateLimite,String categorie) {
        super(nom, priorite, duree, dateLimite, categorie);
        //super(nom, priorite, duree, dateLimite, categorie,LocalTime heureDebut)
    }
    public TacheSimple(String nom, Priorite priorite, int duree, LocalDate dateLimite,String categorie, int periodicite) {
        super(nom, priorite, duree, dateLimite, categorie);
        //super(nom, priorite, duree, dateLimite, categorie,LocalTime heureDebut)
        this.periodicite = periodicite;
    }
    public TacheSimple(String nom, Priorite priorite, int duree, LocalDate dateLimite,String categorie,Creneau creneau, int periodicite) {
        super(nom, priorite, duree, dateLimite, categorie);
        //super(nom, priorite, duree, dateLimite, categorie,LocalTime heureDebut)
        this.creneau = creneau;
        this.periodicite = periodicite;
    }
    public TacheSimple(String nom, Priorite priorite, int duree, LocalDate dateLimite,String categorie,Creneau creneau, int periodicite,LocalDate DatePlanification) {
        super(nom, priorite, duree, dateLimite, categorie,DatePlanification);
        //super(nom, priorite, duree, dateLimite, categorie,LocalTime heureDebut)
        this.creneau = creneau;
        this.periodicite = periodicite;
    }
    public TacheSimple(String nom, Priorite priorite, int duree, LocalDate dateLimite,String categorie,Creneau creneau,LocalDate DatePlanification) {
        super(nom, priorite, duree, dateLimite, categorie,DatePlanification);
        //super(nom, priorite, duree, dateLimite, categorie,LocalTime heureDebut)
        this.creneau = creneau;

    }
    public TacheSimple(String nom, Priorite priorite, int duree, LocalDate dateLimite,String categorie,LocalDate DatePlanification) {
        super(nom, priorite, duree, dateLimite, categorie,DatePlanification);
        //super(nom, priorite, duree, dateLimite, categorie,LocalTime heureDebut)


    }
    public void planifierTachePeriodique(Calendrier calendrier){
        // cette methode replanifier les tâches périodiques tous les n jours :
        // on doit faire une addition (la date de creneau +période)
        // et verifier si on a des créneau libre dans ce jour la ou pas
        LocalDate nouvelleDate=creneau.getDate().plusDays(periodicite);
        //rechercher un crenau libre dans le calendrien
        //si oui => Planifier
        //si non=> ne pas Planifier
    }
    public void planiferTache(){

    }



}
