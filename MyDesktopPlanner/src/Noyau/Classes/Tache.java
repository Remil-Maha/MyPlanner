package Noyau.Classes;

import java.time.*;
import java.util.ArrayList;

public abstract class Tache implements Comparable<Tache> {
    protected String nom;
    protected Priorite priorite;
    protected int duree;
    protected LocalDate dateLimite;

    protected String categorie;

    protected EtatRealisation etat;
    // on a ajouté cet attribut pour sauvegarder la date de la planification
    protected LocalDate DatePlanification;
    public LocalDate getDatePlanification() {
        return DatePlanification;
    }

    public void setDatePlanification(LocalDate datePlanification) {
        DatePlanification = datePlanification;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public LocalDate getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(LocalDate dateLimite) {
        this.dateLimite = dateLimite;
    }

    public EtatRealisation getEtat() {
        return etat;
    }

    public void setEtat(EtatRealisation etat) {
        this.etat = etat;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

public Tache(){}

    public Tache(String nom, Priorite priorite,int duree, LocalDate dateLimite, String categorie, LocalDate DatePlanification) {
        this.nom = nom;
        this.priorite = priorite;
        this.duree = duree;
        this.dateLimite = dateLimite;
        this.categorie = categorie;
        this.DatePlanification = DatePlanification;
    }
    public Tache(String nom, Priorite priorite,int duree, LocalDate dateLimite, String categorie) {
        this.nom = nom;
        this.priorite = priorite;
        this.duree = duree;
        this.dateLimite = dateLimite;
        this.categorie = categorie;
    }

    public void afficher() {
        System.out.println("Nom : " + this.nom);
        System.out.println("Priorité : " + this.priorite);
        System.out.println("Durée : " + this.duree);
        System.out.println("Date limite : " + this.dateLimite);
        System.out.println("Etat de réalisation : " + this.etat);
        System.out.println("Catégorie : " + this.categorie);
    }

    public void replanifier(int duree, Utilisateur user,Periode periode) {
        // Si l'utilisateur decide de replanifier sa tache on doit trouver le creneau libre qui a la meme
        // duree que la duree supplementaire qu'il a ajouté
        // c'est pourquoi on doit faire une recherche pour trouver le creneau correspondant
    	//
        LocalDate dateDebut;
        if(LocalDate.now().isAfter(this.getDatePlanification())){
            dateDebut=LocalDate.now();
        }else{
            dateDebut=this.getDatePlanification().plusDays(1);
        }
        Creneau creneauTrouve = user.getCalendrier().CreneauLibre(this.getDateLimite(), duree,periode,dateDebut);
        if (creneauTrouve == null) {
            System.out.println("Il n'a pas un creneau libre pour faire la repalanification .Veuillez introduire un creneau libre correpondant");

        } else {
            String nom=this.nom+"Suite";
            Tache tache2=new TacheSimple(nom,this.priorite,duree,this.dateLimite,this.categorie,creneauTrouve,creneauTrouve.getDate());
            creneauTrouve.ajouterTache(periode,tache2, user);
            
        }
    }

    // on veut faire la meme methode que la precedente mais on prenant en consideration le nouveau deadline de cette tache replanifiee
    // la chose differente dans cette methode par rapport a la precedente c'est qu'on doit affecter a la tache le nouveau deadline
    public void replanifier(int duree, Utilisateur user, LocalDate nouveauDeadline ,Periode periode) {
        // Si l'utilisateur decide de replanifier sa tache on doit trouver le creneau libre qui a la meme
        // duree que la duree suplementaire qu'il a ajouté
        // c'est pourquoi on doit faire une recherche pour trouver le creneau correspondant
        LocalDate dateDebut;
        if(LocalDate.now().isAfter(this.getDatePlanification())){
            dateDebut=LocalDate.now();
        }else{
            dateDebut=this.getDatePlanification().plusDays(1);
            System.out.println("La date de planification : " +dateDebut);
        }
        Creneau creneauTrouve = user.getCalendrier().CreneauLibre(nouveauDeadline, duree,periode,dateDebut);
        System.out.println("-----------------------------------------");

        System.out.println("Affichage du creneau trouve pendant la replanification ");
        System.out.println("date: "+creneauTrouve.getDate());
        System.out.println("heure debut: "+creneauTrouve.getheureDebut());
        System.out.println("heure fin: "+creneauTrouve.getheureFin());
        System.out.println("-----------------------------------------");
        if (creneauTrouve == null) {
            System.out.println("Il n'a pas un creneau libre pour faire la repalanification .Veuillez introduire un creneau libre correpondant");

        } else {
            String nom=this.nom+"Suite";
            Tache tache2 = new TacheSimple(nom, this.priorite, duree, nouveauDeadline, this.categorie, creneauTrouve, creneauTrouve.getDate());
            creneauTrouve.ajouterTache(periode,tache2, user);
        }
    }
    public void reporter (LocalTime nouvelleHeureDebut, Utilisateur user ,Periode periode){
        // le report de la tache c est le fait de changer l'heure debut
        // donc on doit trouver le creneau correspondant : le creneau a la meme date que la tache
        // le report dans la meme journee
        Creneau creneauAncien = user.getCalendrier().rechercherCreneauOccpue(this);// on a recuperer le creneau correpondant
        System.out.println("-----------------------------------------");
        System.out.println("-----------------------------------------");

        System.out.println("Affichage de l'ancien  creneau trouve pendant le report ");
        System.out.println("date: "+creneauAncien.getDate());
        System.out.println("heure debut: "+creneauAncien.getheureDebut());
        System.out.println("heure fin: "+creneauAncien.getheureFin());
        System.out.println("-----------------------------------------");
        Creneau creneauNouveau = user.getCalendrier().CreneauLibreReport(this.getDatePlanification(), this.getDuree(), nouvelleHeureDebut);
        System.out.println("-----------------------------------------");
        System.out.println("-----------------------------------------");

        System.out.println("Affichage de le nouveau creneau trouve pendant le report ");
        System.out.println("date: "+creneauNouveau.getDate());
        System.out.println("heure debut: "+creneauNouveau.getheureDebut());
        System.out.println("heure fin: "+creneauNouveau.getheureFin());
        System.out.println("-----------------------------------------"); 
        if(creneauNouveau==null){
            System.out.println("Il n'y a pas un creneau libre dans la date d'houjourd'hui ! Veuillez introduire un nouveau crneau ");
        }else{
            // on doit supprimer l'ancien creneau de la liste des creneaux planifes et le rajouter a la liste des creneaux libres
            Journee journee=user.getCalendrier().trouvJournee(this.getDatePlanification());
            Journee journeeCorrespondantePeriode=periode.donnerJournee(creneauAncien.getDate());
            creneauAncien.liberer();
            System.out.println("etat apres le report: "+creneauAncien.getEtat());

            creneauAncien.setTache(null);
            journee.supprimerCreneauplanifie(creneauAncien);
            journeeCorrespondantePeriode.supprimerCreneauplanifie(creneauAncien);
            journee.ajouterCreneauLibre(creneauAncien, user);
            journeeCorrespondantePeriode.ajouterCreneauLibre(creneauAncien,user);
            creneauNouveau.rendreOccupe();
            creneauNouveau.setTache(this);
            creneauNouveau.ajouterTache(periode,this, user);
         

            // on doit changer les creneaux de la periode


        }
    }

    public void reporter(LocalTime heureDebut,Utilisateur user,LocalDate nouvelleDate,Periode periode){
        if(nouvelleDate.isAfter(this.getDateLimite()) || nouvelleDate.isBefore(LocalDate.now())){
            System.out.println("Vous ne pouvez pas reporter cette tache a cette date");
        }else{
            Creneau creneauAncien =user.getCalendrier().rechercherCreneauOccpue(this);
            Creneau creneauNouveau=user.getCalendrier().CreneauLibreReport(nouvelleDate,duree,heureDebut);
            System.out.println("Affichage ");

            System.out.println("date: "+creneauNouveau.getDate());
            System.out.println("heure debut: "+creneauNouveau.getheureDebut());
            System.out.println("heure fin: "+creneauNouveau.getheureFin());
            if(creneauNouveau==null){
                System.out.println("Il n'y a pas un creneau libre dans cette date! Veuillez introduire un nouveau crneau ");
            }else{
            	 Journee journee=user.getCalendrier().trouvJournee(this.getDatePlanification());
                 Journee journeeCorrespondantePeriode=periode.donnerJournee(creneauAncien.getDate());
                  creneauAncien.setEtat(EtatCreneau.LIBRE);
                 System.out.println("etat apres le report: "+creneauAncien.getEtat());
                 creneauAncien.setTache(null);
                 journee.supprimerCreneauplanifie(creneauAncien);
                 journeeCorrespondantePeriode.supprimerCreneauplanifie(creneauAncien);
                 journee.ajouterCreneauLibre(creneauAncien, user);
                 journeeCorrespondantePeriode.ajouterCreneauLibre(creneauAncien,user);                
                 creneauNouveau.setTache(this);
                 creneauNouveau.ajouterTache(periode,this, user);
                  creneauNouveau.rendreOccupe();
                // on doit changer les creneaux de la periode 
                

            }
        }
    }

    // l'utilisateur peut reporter une tache en donnant juste le nouveau deadline et nous donne la liberte de choisir le creneau *
    // disponible jusqu'a ce deadline
    // on doit chercher dans les creneaux libres jusqu'a trouver le creneau convenable (dans la classe calendrier)
   /* public void reporter(LocalTime nouvelleHeureDebut, Utilisateur user, LocalDate nouveauDeadline) {

        Creneau creneauAncien = user.getCalendrier().rechercherCreneauOccpue(this);// on a recuperer le creneau correpondant
        this.setheureDebut(nouvelleHeureDebut);
        Creneau creneauNouveau = user.getCalendrier().CreneauLibreReport2(this.getDuree(),nouveauDeadline, nouvelleHeureDebut);
        if (creneauNouveau == null) {
            System.out.println("Il n'y a pas un creneau libre dans cette date! Veuillez introduire un nouveau crneau ");

        } else {
            // on doit supprimer l'ancien creneau occupe de la liste des crneaux planifies
            Journee journee = user.getCalendrier().chercherJournee(this.getDatePlanification());
            creneauAncien.setEtat(EtatCreneau.LIBRE);  // il faut qu'il soit libre
            journee.supprimerCreneauplanifie(creneauAncien);
            creneauNouveau.setEtat(EtatCreneau.OCCUPE);
            this.setheureDebut(nouvelleHeureDebut);// on doit modifier l'heure debut de la tache
            this.setDateLimite(nouveauDeadline);// on doit changer le deadline de cette tache
            creneauNouveau.setTache(this);
            journee.ajouterCreneauOccupee(creneauNouveau);

        }

    }*/
   public int compareTo(Tache autre) {
        int comparaisonDeadline = this.getDateLimite().compareTo(autre.getDateLimite());
        if (comparaisonDeadline != 0) {
            return comparaisonDeadline;
        } else {
            return -this.priorite.compareTo(autre.getPriorite());
        }
    }

    /*public void replanifierEnsembleTache(ArrayList<Tache> listeTachePlanifiees,Utilisateur user ,Periode periode){
            // on doit trouver les creneaux des taches
            // on doit tester si ses creneaux sont bloques ou pas
            // si le creneau est bloqué on peut pas replanifier sa tache
        user.ordonnerTaches(listeTachePlanifiees);
        for (int i = 0; i < listeTachePlanifiees.size(); i++){
            Creneau creneauAncien =user.getCalendrier().rechercherCreneauOccpue(this);
            if(creneauAncien.getEtat().equals(EtatCreneau.BLOQUE)){
                System.out.println("La tache dans le nom est " +this.getNom()+" ne peut pas etre replanifier! Son creneau est bloque");

            }else{
              // si le creneau de cette tache n'etait pas bloque on doit rechecher
            }

        }

    }*/


}
