package Noyau.Classes;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.TreeSet;


public class Journee implements Comparable<Journee>{

    private LocalDate date;//date de la jounee
    private TreeSet<Creneau> sesCreneaux=new TreeSet<Creneau>();//doit contenier que des creneaux libres
    private TreeSet<Creneau> creneauxTachesPlanifiees=new TreeSet<Creneau>();
    //les crenaux sont ordonnes ppur cette meme journee selon l'heure
    private int nombreTachesAcomplies; //le nombre de tache accomplies pour cette journee
    private boolean recuEncouragement; //true si dans cette journee l'utilisateur a recu un encouragement du systeme, false sinon

    public TreeSet<Creneau> getCreneauxTachesPlanifiees() {
        return creneauxTachesPlanifiees;
    }

    public void setCreneauxTachesPlanifiees(TreeSet<Creneau> creneauxTachesPlanifiees) {
        this.creneauxTachesPlanifiees = creneauxTachesPlanifiees;
    }

    public int getNombreTachesAcomplies() {
        return nombreTachesAcomplies;
    }
    public void setNombreTachesAcomplies(int nombreTachesAcomplies) {
        this.nombreTachesAcomplies = nombreTachesAcomplies;
    }
    public boolean isRecuEncouragement() {
        return recuEncouragement;
    }
    public void setRecuEncouragement(boolean recuEncouragement) {
        this.recuEncouragement = recuEncouragement;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public TreeSet<Creneau> getSesCreneaux() {
        return sesCreneaux;
    }
    public void setSesCreneaux(TreeSet<Creneau> sesCreneaux) {
        this.sesCreneaux = sesCreneaux;
    }

    public Journee(LocalDate date){
        this.date=date;
    }
    public Journee(){
    }


    public boolean equals(Object obj){
        Journee j=(Journee) obj;
        return this.date.equals(j.getDate());
    }
    public int compareTo(Journee j){
        if(this.date.isBefore(j.getDate())){
            return -1;
        }else{
            if(this.date.isAfter(j.getDate())){
                return +1;
            }else{
                return 0;
            }
        }
    }
    // cette methode est utlisée pour ajouter un creneau libre a la liste des creneau libre
    public void ajouterCreneauLibre(Creneau creneau,Utilisateur user) {

        if(creneau.calculerDuree()>= user.getDureeMin()){
            boolean intersection=false;
            Iterator<Creneau> it=this.sesCreneaux.iterator();
            while(intersection==false && it.hasNext()){
                Creneau creneau1=it.next();
                intersection=creneau.intersecteAvec(creneau1);
            }
            if(intersection==true){
                // exception car il y une exception avec un autre creneau
                System.out.println("Intersection des creneaux!!!!! ");
            }else{
                this.sesCreneaux.add(creneau);
            }
        }else{
            System.out.println("la duree d'un creneau doit etre superieure ou egale "+user.getDureeMin());
        }

    }
    public void ajouterCreneauLibre(TreeSet<Creneau> creneaux){
        Iterator<Creneau> it=creneaux.iterator();
        while(it.hasNext()){
            this.sesCreneaux.add(it.next());
        }
    }

    public TreeSet<Creneau> rechercherCreneauxLibres(){
        //rechercher des creneaux libre dans cette journee
        //s'il y a au moins un il sera dans la set sinon il sera empty
        TreeSet<Creneau> creneauxLibres=new TreeSet<Creneau>();
        Iterator<Creneau> it=this.sesCreneaux.iterator();
        while (it.hasNext()){
            Creneau creneau=it.next();
            if(creneau.getEtat()==EtatCreneau.LIBRE){
                creneauxLibres.add(creneau);
            }
        }
        return creneauxLibres;
    }

    public void affecterTacheCreneau(Tache tache, Creneau creneau){
        if(creneau.getEtat()==EtatCreneau.LIBRE){
            //il se peut que ce creneau n'est pas programme dans cette journee donc il faut verifier
            if(this.sesCreneaux.contains(creneau)==true){
                this.sesCreneaux.remove(creneau);
                creneau.setTache(tache);
                creneau.setEtat(EtatCreneau.OCCUPE);
                this.creneauxTachesPlanifiees.add(creneau);
            }else{
                //exception
            }


        }else{
            //exception
        }
    }
    public void rendreCreneauBloque(Creneau creneau){
        //ce creneau contain une tahce si l'utilisateur souhaite le bloque (ne ser pas modifier en cas re-plannification )
        if(creneau.getEtat()==EtatCreneau.OCCUPE){
            //il se peut que ce creneau n'est pas programme dans cette journee donc il faut verifier
            if(this.creneauxTachesPlanifiees.contains(creneau)==true){
                this.creneauxTachesPlanifiees.remove(creneau);
                creneau.setEtat(EtatCreneau.BLOQUE);
                this.creneauxTachesPlanifiees.add(creneau);
            }else{
                //exception
            }


        }else{
            //exception
        }
    }

    public Tache libererCreneau( Creneau  creneau){
        //cad supprimer la tahce du creneua le rendre libre à condition qu'il ne soit pas bloque
        Tache tache=null;
        if(creneau.getEtat()!=EtatCreneau.BLOQUE){
            if(creneau.getEtat()==EtatCreneau.OCCUPE){
                if(this.creneauxTachesPlanifiees.contains(creneau)){
                    this.creneauxTachesPlanifiees.remove(creneau);
                    creneau.setEtat(EtatCreneau.LIBRE);
                    tache=creneau.getTache();
                }
            }
        }
        return tache;
    }
    public void incrementerNnbTachesAcomplies(){
        this.nombreTachesAcomplies++;
    }

    public void supprimerCreneauplanifie(Creneau creneau) {
        this.creneauxTachesPlanifiees.remove(creneau);
    }
    public void supprimerCreneauLibre(Creneau creneau) {
        this.sesCreneaux.remove(creneau);
    }

    // Cette methode est utilisée pour ajouter un creneau de la liste des creneaux planifie)
    public void  ajouterCreneauOccupee(Creneau creneau ){
        this.creneauxTachesPlanifiees.add(creneau);
    }
}
