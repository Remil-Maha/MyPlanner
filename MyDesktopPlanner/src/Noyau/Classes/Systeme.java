package Noyau.Classes;
import java.util.*;

import Noyau.Exceptions.NomUtilisateurExisteException;

public class Systeme {
    private HashMap<String,Utilisateur> utilisateurs=new HashMap<String, Utilisateur>();
    private static int nbGoodRequis=3;
    private static int nbVeryGoodRequis=3;
    private static int nbEncouragementsRequis=5;
    private static String encouragement="Bravo!";
    private  static HashSet<Categorie> categoriesDuSysteme=new HashSet<Categorie>();

    public static HashSet<Categorie> getCategoriesDuSysteme() {
        return categoriesDuSysteme;
    }

    public Systeme() {
    }

    public static void setCategoriesDuSysteme(HashSet<Categorie> categoriesDuSysteme) {
        Systeme.categoriesDuSysteme = categoriesDuSysteme;
    }

    public HashMap<String, Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }
    public void setUtilisateurs(HashMap<String, Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    public static int getNbGoodRequis() {
        return nbGoodRequis;
    }
    public static void setNbGoodRequis(int nbGoodRequis) {
        Systeme.nbGoodRequis = nbGoodRequis;
    }
    public static int getNbVeryGoodRequis() {
        return nbVeryGoodRequis;
    }
    public static void setNbVeryGoodRequis(int nbVeryGoodRequis) {
        Systeme.nbVeryGoodRequis = nbVeryGoodRequis;
    }
    public static int getNbEncouragementsRequis() {
        return nbEncouragementsRequis;
    }
    public static void setNbEncouragementsRequis(int nbEncouragementsRequis) {
        Systeme.nbEncouragementsRequis = nbEncouragementsRequis;
    }
    public static String getEncouragement() {
        return encouragement;
    }
    public static void setEncouragement(String encouragement) {
        Systeme.encouragement = encouragement;
    }

    public  static void initCategories(){
        Categorie cat=new Categorie("bleu","STUDIES");
        categoriesDuSysteme.add(cat);
        cat=new Categorie("rouge","WORK");
        categoriesDuSysteme.add(cat);
        cat=new Categorie("jaune","SPORT");
        categoriesDuSysteme.add(cat);
        cat=new Categorie("vert","HEALTH");
        categoriesDuSysteme.add(cat);
        cat=new Categorie("gris","PERSO");
        categoriesDuSysteme.add(cat);
        cat=new Categorie("orange","FAMILLY");
        categoriesDuSysteme.add(cat);

    }

    public Systeme(HashMap<String, Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    //----------------------------------------------------------------------------------------------------------
    //                                  AUTHENTIFICATION UTILISATEUR
    //----------------------------------------------------------------------------------------------------------

    //authentifier l'user
    public boolean authentifier(String nom){
        //on verifie s'il y a un user dont le nom est "nom"
        if(this.utilisateurs.keySet().contains(nom)){
            System.out.println("Acces avec succes");
            return true;
        }else{
            //afficher un message d'erreur
            System.out.println("Veuillez créer un compte");
            return false;
        }
    }

    //ajouter un user dans le systeme
    public void ajouterNouveauUtilisateur(Utilisateur utilisateur){
        try{
            NomUtilisateurExisteException.doSomthing(utilisateur.getNom(), this.utilisateurs);
            //on lui donne les categroeiss
            utilisateur.setMesCategories(new ArrayList<Categorie>(categoriesDuSysteme));
            this.utilisateurs.put(utilisateur.getNom(),utilisateur);
            System.out.println("Bienvenu sur MyDesktopPlanner");
        }catch(NomUtilisateurExisteException e){
            System.out.println(e.getMessage());
        }
    }


    //feliciter l'user
    public void feliciterUtilisateur(Utilisateur user){
        System.out.println("Bravo "+user.getNom()+"! vous avez realisé "+user.getNbTacheMin()+" aujourd'hui.");
    }
    //accorderBadge Good
    public void accorderBadgeGood(Utilisateur user){
        user.ajouterBadge(Badge.GOOD);
    }
    //accorderBadgeVeryGood
    public void accorderBadgeVeryGood(Utilisateur user){
        user.ajouterBadge(Badge.VERYGOOD);
    }
    //accorderBadgeExcellent
    public void accorderBadgeExcellent(Utilisateur user){
        user.ajouterBadge(Badge.EXCELENT);
    }
    public Planning planifierAutomatiqueTaches(TreeSet<Tache> taches,Periode periode,Utilisateur utilisateur){
        Planning planning=null;
        Iterator<Tache> it=taches.iterator();
        while (it.hasNext()){
            Tache tache=it.next();
            if(tache instanceof TacheSimple){

            }else{

            }
        }
        return planning;
    }
    //afficherCalendrier
    //demanderUser de fixer une peride pour son planning==> execption possible DateAnterieurSystemeException
    //demander de fixer des creneaux (entrer des creneaux) pour la periode chosi
    //planifier + classer +valider annuler modifier la proposition


}
