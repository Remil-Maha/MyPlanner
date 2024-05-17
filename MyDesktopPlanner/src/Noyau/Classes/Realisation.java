package Noyau.Classes;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;



import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.*;
public class Realisation {

    private ArrayList<Tache> tachesRealisees=new ArrayList<Tache>();
    private ArrayList<Projet> projetRealises=new ArrayList<Projet>();
    private HashMap<Badge,Integer> mesBagdes=new HashMap<Badge, Integer>(){{
        put(Badge.GOOD,0);
        put(Badge.VERYGOOD,0);
        put(Badge.EXCELENT,0);
    }};
    private int nbEncouragement=0;

    public int getNbEncouragement() {
        return nbEncouragement;
    }

    public void setNbEncouragement(int nbEncouragement) {
        this.nbEncouragement = nbEncouragement;
    }

    public ArrayList<Tache> getTachesRealisees() {
        return tachesRealisees;
    }

    public void setTachesRealisees(ArrayList<Tache> tachesRealisees) {
        this.tachesRealisees = tachesRealisees;
    }

    public ArrayList<Projet> getProjetRealises() {
        return projetRealises;
    }

    public void setProjetRealises(ArrayList<Projet> projetRealises) {
        this.projetRealises = projetRealises;
    }

    public HashMap<Badge, Integer> getMesBagdes() {
        return mesBagdes;
    }

    public void setMesBagdes(HashMap<Badge, Integer> mesBagdes) {
        this.mesBagdes = mesBagdes;
    }

    public Realisation() {
    }


    //--------------------------------------------------------------------------------------
    //------------       METHODES D'AJOUT                                    -------------
    //-------------------------------------------------------------------------------------


    public void ajouterBadge(Badge b){
        //on ajoute un badge gagne dans mesBadges
        switch (b){
            case GOOD -> {
                this.mesBagdes.put(Badge.GOOD,this.mesBagdes.get(Badge.GOOD)+1);
            }
            case VERYGOOD -> {
                this.mesBagdes.put(Badge.VERYGOOD,this.mesBagdes.get(Badge.VERYGOOD)+1);
            }
            case  EXCELENT -> {
                this.mesBagdes.put(Badge.EXCELENT,this.mesBagdes.get(Badge.EXCELENT)+1);
            }
        }
    }
    public  void ajouterTache(Tache tache){
        this.tachesRealisees.add(tache);
    }
    public  void ajouterProjet(Projet projet) {
        //un projet est realise <=> toutes ses taches sont completees
        this.projetRealises.add(projet);
    }

    public void incrementerEncouragement(){
        this.nbEncouragement++;
    }

    //--------------------------------------------------------------------------------------
    //------------       METHODES DE CONSULTATION DE STATISTIQUES             -------------
    //-------------------------------------------------------------------------------------

    public void realisationTache(Journee jour){
        //consulter l'etat de realisation des taches dans la journee
        //on parcour le set des creneaX avec les taches palnifiees
        //parmi celles-ci on trouve tous les etas
        TreeSet<Creneau> creneauxTachesPlanifiees =jour.getCreneauxTachesPlanifiees();
        Iterator<Creneau> it=creneauxTachesPlanifiees.iterator();
        while (it.hasNext()){
            Tache tache=it.next().getTache();//la tache du creneau
            System.out.println("Nom : "+ tache.getNom()+"  Etat: "+tache.getEtat().toString());
        }
    }
    public void realisationProjet(Projet projet){
        //on affiche l'etat de realisation de ses atche
        ArrayList<Tache> taches=projet.getTaches();
        for(int i=0;i<taches.size();i++){
            System.out.println("Nom : "+ taches.get(i).getNom()+"  Etat: "+taches.get(i).getEtat().toString());
        }
    }
    public void voirBadges(){
        for(Map.Entry<Badge,Integer> entry: this.mesBagdes.entrySet()){
            System.out.println("Badge "+entry.getKey().toString()+"   Nombre: "+entry.getValue());
        }
    }

    public double calculerRendementjournalier(Journee jour) {
        //calculer le rendement journalier d’une journée donnée
        // nombre de tache completed / le nombre total des taches
        return (double) (jour.getNombreTachesAcomplies())/(double)jour.getCreneauxTachesPlanifiees().size();
    }


    public double calulerMoyenneRendementJournalier(Periode periode){
        //calculer le rendement journalier depuis la date début de la periode actuelle
        //on calcul le rendement journalier de toute la periode
        //la moyenne est la somme / nombre de jours dans la periode
        long nbJours= ChronoUnit.DAYS.between(periode.getDateDebut(), periode.getDateFin());
        double somme=0;
        LocalDate datecourante=periode.getDateDebut();
        while (!datecourante.isAfter(periode.getDateFin())){

            Journee jour=periode.donnerJournee(datecourante);
            if(jour.getCreneauxTachesPlanifiees().isEmpty()==false){
                somme=somme+this.calculerRendementjournalier(jour);
            }
            datecourante=datecourante.plusDays(1);
        }

        return somme/(double) nbJours;

    }
    public int nbEncouragement(Periode periode){
        //le nombre de jours ou' il a eu des encouragements
        int nb=0;
        LocalDate datecourante=periode.getDateDebut();
        while (!datecourante.isAfter(periode.getDateFin())){
            Journee jour=periode.donnerJournee(datecourante);
            if(jour.isRecuEncouragement()){
                nb=nb+1;
            }
            datecourante=datecourante.plusDays(1);
        }
        return nb;
    }
    public RendementJournee jourRentable( Historique historique){
        //le jour le plus rentable est le jour avec le plus de "rendement"
        //sur tout l'historique
        Iterator<Planning> it=historique.getHistoriqueDesPlannings().iterator();
        ArrayList<RendementJournee> list=new ArrayList<RendementJournee>();
        //pour chaque Planning on trouve le jour le plsu rentable ainsi que son rendement
        while (it.hasNext()){
            Planning p=it.next();
            RendementJournee rend=p.donneerJourneePlusRentable();
            list.add(rend);
        }
        Collections.sort(list);
        return list.get(list.size()-1);
    }
    public  int  dureeCategorie(Categorie categorie){
        int duree=0;
        Iterator<Tache> it=categorie.getCategorieTache().iterator();
        while (it.hasNext()){
            Tache tache=it.next();
            duree=duree+tache.getDuree();
        }
        return duree;
    }

    public RendementJournee jourRentable(Periode periode){
        //le jour le plus rentable est le jour avec le plus de "rendement"
        TreeSet<Journee> jours=periode.getJournees_Creneaux();
        Iterator<Journee> it=jours.iterator();

        ArrayList<RendementJournee> list=new ArrayList<RendementJournee>();
        //pour chaque Planning on trouve le jour le plsu rentable ainsi que son rendement
        while (it.hasNext()){
            Journee j=it.next();
            RendementJournee rend=new RendementJournee(j.getDate(),this.calculerRendementjournalier(j));
            list.add(rend);
        }
        Collections.sort(list);
        return list.get(list.size()-1);
    }
    public RendementJournee jourRentable(TreeSet<Journee> jours){
        //le jour le plus rentable est le jour avec le plus de "rendement"
        Iterator<Journee> it=jours.iterator();

        ArrayList<RendementJournee> list=new ArrayList<RendementJournee>();
        //pour chaque Planning on trouve le jour le plsu rentable ainsi que son rendement
        while (it.hasNext()){
            Journee j=it.next();
            RendementJournee rend=new RendementJournee(j.getDate(),this.calculerRendementjournalier(j));
            list.add(rend);
        }
        Collections.sort(list);
        return list.get(list.size()-1);
    }

}