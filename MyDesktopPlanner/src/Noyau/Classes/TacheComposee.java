package Noyau.Classes;
import java.time.*;
import java.util.*;
public class TacheComposee extends Tache {

    private TreeMap<Creneau,Tache> mapSousTaches;
    private boolean valide;//decomposition validee ou pas

    public TreeMap<Creneau, Tache> getMapSousTaches() {
        return mapSousTaches;
    }

    public void setMapSousTaches(TreeMap<Creneau, Tache> mapSousTaches) {
        this.mapSousTaches = mapSousTaches;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public void changerNomSTache(String nouveauNom, Tache st){
        st.setNom(nouveauNom);
    }
    public TacheComposee(){}
    public TacheComposee(String nom, Priorite priorite, int duree, LocalDate dateLimite, String categorie, LocalDate DatePlanification) {
        super(nom, priorite, duree, dateLimite, categorie, DatePlanification);
    }
    public TacheComposee(String nom, Priorite priorite, int duree, LocalDate dateLimite, String categorie) {
        super(nom, priorite, duree, dateLimite, categorie);
    }
    public TreeMap<Creneau,Tache> planifierTacheComposee(Periode periode, Creneau creneau,Utilisateur utilisateur){
        //recherche de creneux disponibles d'etat "libre" dans la periode "periode"
        Calendrier calendrier=utilisateur.getCalendrier();

        TreeMap<Creneau,Tache> decomposition=new TreeMap<>();
        int dureeInit=this.duree;
        // d'abord allouer tout le creneau pour une sous-tache donnee
        Tache sousTache= new TacheSimple(
                (this.getNom()+"1"),
                this.getPriorite(),
                creneau.calculerDuree(),
                this.getDateLimite(),
                this.getCategorie()
        );
        decomposition.put(creneau,sousTache);

        //maintenant on supprime ce creneau de l'ensemple des creneaux disponibles
        periode.donnerJournee(creneau.getDate()).supprimerCreneauLibre(creneau);
        calendrier.trouvJournee(creneau.getDate()).supprimerCreneauLibre(creneau);
        //apres avoir palnifier une sousTache
        //mettre a jours la duree de la tache
        dureeInit=dureeInit-creneau.calculerDuree();
        //recuperer tous les creneaux libres restant dans la periode tels que:
        //  ((ils respectes la DateLimite))
        // la somme de leurs duree>=dureeInit sinon ils vont jamais suffir pour planifier le restant de la tache

        TreeSet<Creneau> listeCreneauxDispo=periode.trouverCreneaux(dureeInit,this.dateLimite);
        if(listeCreneauxDispo.isEmpty()==false){
            //parcourire la liste des creneaux trouves
            //jusqu'à ce que toute la tache soit planifiee
            TreeSet<Creneau> creneauxDispo = new TreeSet<>(new DureeComparator());
            creneauxDispo.addAll(listeCreneauxDispo);
            //si ces creneaux sont tries en ordre decroissant selon leurs duree
            //alors, forcement il n'aura que la derniere partie(derniere sousTache) qui pourra provoquer une decomposition de creneau

            int dureeSousTache=0;//on la calcule pour ceer la sous tache
            int i=1;
            Iterator<Creneau> it=creneauxDispo.iterator();
            while (it.hasNext() &&dureeInit>0){
                Creneau creneauChoisi=it.next();

                if( creneauChoisi.calculerDuree()>=dureeInit){
                    dureeSousTache= dureeInit;
                }else{
                    dureeSousTache=creneauChoisi.calculerDuree();
                }
                Integer numeroSousTache=i+1;
                sousTache=new TacheSimple(
                        (this.getNom()+numeroSousTache.toString()),
                        this.getPriorite(),
                        dureeSousTache,
                        this.getDateLimite(),
                        this.getCategorie()
                );
                //cette sous tache sera planifiee dans creneau choisi
                creneauChoisi.ajouterTache(periode,sousTache,utilisateur);
                decomposition.put(creneauChoisi,sousTache);//+sousTache
                dureeInit=dureeInit-dureeSousTache;
            }

        }else{
            //le reste des crneaux dans la periode ne suffira jamais pour faire ka decomposition
            //pas de planification
            //le creneau qu'on a supprime au debut doit etre remis a sa place
            periode.donnerJournee(creneau.getDate()).ajouterCreneauLibre(creneau,utilisateur);
            calendrier.trouvJournee(creneau.getDate()).ajouterCreneauLibre(creneau,utilisateur);
            //cette tache sera pour l'instant unschuduled jusqu'a nouvel order
           // utilisateur.getTachesUnscheduled().add(this);
            System.out.println("La tache est uschduled");
            decomposition.clear();
        }
        return decomposition;
    }
    public void refuser(HashMap<Creneau,Tache> decomposition){
        //a refuser
    }
    public void valider(HashMap<Creneau,Tache> decomposition){
        // a valider
    }


}

