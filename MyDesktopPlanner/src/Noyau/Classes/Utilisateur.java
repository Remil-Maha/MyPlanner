package Noyau.Classes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import Noyau.Exceptions.*;

public class Utilisateur {
    private String nom;
    private int dureeMin;//on aura besion dans la planifiaction
    private int nbTacheMin = 3;

    private Historique monHisto = new Historique();//historique des anciens plannings
    private Realisation mesRealisation = new Realisation(); // taches réalisées
    private HashSet<Tache> mesTaches = new HashSet<Tache>(); //sotcker les taches introduites non planifiées ou  (unscheduled)
    private HashSet<Projet> mesProjets = new HashSet<Projet>(); //deux projets sont égaux s’ils ont le même nom
    private ArrayList<Categorie> mesCategories = new ArrayList<Categorie>(); //pour connaitre ses catégories et peut-être les afficher
    private Calendrier calendrier = new Calendrier();
    private TreeSet<Planning> mesPlannings = new TreeSet<Planning>();

    public HashSet<Projet> getMesProjets() {
        return mesProjets;
    }

    public void setMesProjets(HashSet<Projet> mesProjets) {
        this.mesProjets = mesProjets;
    }

    public TreeSet<Planning> getMesPlannings() {
        return mesPlannings;
    }

    public void setMesPlannings(TreeSet<Planning> mesPlannings) {
        this.mesPlannings = mesPlannings;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDureeMin() {
        return dureeMin;
    }

    public void setDureeMin(int dureeMin) {
        this.dureeMin = dureeMin;
    }

    public int getNbTacheMin() {
        return nbTacheMin;
    }

    public void setNbTacheMin(int nbTacheMin) {
        this.nbTacheMin = nbTacheMin;
    }

    public Historique getMonHisto() {
        return monHisto;
    }

    public void setMonHisto(Historique monHisto) {
        this.monHisto = monHisto;
    }

    public Realisation getMesRealisation() {
        return mesRealisation;
    }

    public void setMesRealisation(Realisation mesRealisation) {
        this.mesRealisation = mesRealisation;
    }

    public HashSet<Tache> getMesTaches() {
        return mesTaches;
    }

    public void setMesTaches(HashSet<Tache> mesTaches) {
        this.mesTaches = mesTaches;
    }

    public ArrayList<Categorie> getMesCategories() {
        return mesCategories;
    }

    public void setMesCategories(ArrayList<Categorie> mesCategories) {
        this.mesCategories = mesCategories;
    }

    public void ajouterParametres(int dureeMin, int nbTacheMin) {
        this.dureeMin = dureeMin;
        this.nbTacheMin = nbTacheMin;
    }

    public Calendrier getCalendrier() {
        return calendrier;
    }

    public void setCalendrier(Calendrier calendrier) {
        this.calendrier = calendrier;
    }

    public Utilisateur(String user, int dureeMin, int nbTacheMin) {
        this.nom = user;
        this.dureeMin = dureeMin;
        this.nbTacheMin = nbTacheMin;

        //on doit lui associer un calendrier
    }

    public Utilisateur(String user) {
        this.nom = user;
    }

    public boolean equals(Object obj) {
        Utilisateur user = (Utilisateur) obj;
        return (this.nom.equals(user.getNom()));
    }

    public int hashCode() {
        return this.nom.hashCode();
    }
    //--------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------

    public void introduireTache(Tache tache) {
        try {
            this.mesTaches.add(tache);
        } catch (NullPointerException e) {

        }
    }

    public void creerProjet(Projet projet) {
        try {
            this.mesProjets.add(projet);
        } catch (NullPointerException e) {

        }
    }

    public void classerTache(Tache tache, String cat) {
        //affecter la tache a la categorie donnee (cette categorie elle peut exister dans le sysyteem (app)
        //si elle n'existe pas on doit la creer pour l'utilisateur
        //elle sera une nouvelle categorie
        tache.setCategorie(cat);

    }


    public void affecterCouleur(String categoie, String couleur) {
        Categorie cat = trouverCat(categoie);
        try {
            cat.setCouleur(couleur);
        } catch (NullPointerException e) {

        }

    }

    public Categorie trouverCat(String cat) {
        boolean trouve = false;
        Categorie catt = null;
        int i = 0;
        while (trouve == false) {
            if (this.mesCategories.get(i).getCategorie().equals(cat)) {
                trouve = true;
                catt = this.mesCategories.get(i);
            }
            i++;
        }
        return catt;
    }

    //definir une periode
    public Periode fixerPeriode(LocalDate dateDebut, LocalDate datefin) {
        //creer une periode
        try {
            DateAnterieureException.doSomthing(dateDebut, LocalDate.now());
            DateAnterieureException.doSomthing(datefin, dateDebut);
            Periode periode = new Periode(dateDebut, datefin);
            //ajout de ces jours dans le calrndrir
            this.calendrier.ajouterJourneesPeriode(periode);
            return periode;

        } catch (DateAnterieureException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    //fixer des creneau pour une periode definie

    public void fixerCreneauxLibres(Periode periode) {
        //l'utilisateur donne des creneaux libre pour chaque journee de la periode

        LocalDate dateCourante = periode.getDateDebut();
        Journee jour;
        Journee jourCorrespondantCalendrier;
        Scanner sc = new Scanner(System.in);
        int h = 0, m = 0;
        Creneau creneau;
        while (!dateCourante.isAfter(periode.getDateFin())) {
            jour = periode.donnerJournee(dateCourante);
            int reponse = 1;
            //on donne des crenaux ppour cette journee "jour"
            System.out.println("Donner un/des creneau(x) pour la journee " + dateCourante.toString());

            do {
                System.out.println("Donner l'heure debut:  ");
                System.out.print("       -> L'heure: ");
                h = sc.nextInt();
                System.out.print("       -> Les minutes: ");
                m = sc.nextInt();
                LocalTime hdebut = LocalTime.of(h, m);
                System.out.println("Donner l'heure fin:  ");
                System.out.print("       -> L'heure: ");
                h = sc.nextInt();
                System.out.print("       -> Les minutes: ");
                //------------------------------------------
                //execption heurefin
                //il faut des controles
                //--------------------------------------------
                m = sc.nextInt();
                LocalTime hfin = LocalTime.of(h, m);

                creneau = new Creneau(dateCourante, hdebut, hfin, EtatCreneau.LIBRE);

                jour.ajouterCreneauLibre(creneau, this);
                jourCorrespondantCalendrier = this.calendrier.trouvJournee(jour.getDate());
                jourCorrespondantCalendrier.ajouterCreneauLibre(creneau, this);
                System.out.print("Un autre creneau? (1 pour oui, 0 pour non):  ");
                reponse = sc.nextInt();

            } while (reponse != 0);
            dateCourante = dateCourante.plusDays(1);
        }
    }

    public void ajouterPlanning(Planning planning) {
        this.mesPlannings.add(planning);
    }

    //********************************************************************************/
    //                          Plannification manuelle                              */
    //********************************************************************************/
    //planification manuelle d'une tache simple
    public boolean planifierTache(Periode periode, Creneau creneau, Tache tache) {
        //planification manuelle (on donne la tache + son creneau)
        //le creneau est choisi par l'utilisateur a partir du calendrier
        // on doit assurer que la date du creneau est avant la date limite de la tache
        boolean palifieeAvecSucces = false;
        if (tache.getDateLimite().isAfter(creneau.getDate()) || tache.getDateLimite().equals(creneau.getDate())) {
            palifieeAvecSucces = creneau.ajouterTache(periode, tache, this);
            //si la planifiaction est bien faite
            //alors, elle est dans calendrier
            //ceci doit aporter des modification sur le planning de la periode
            if (palifieeAvecSucces == true) {
                this.mesTaches.remove(tache);
                periode.affecterTache(tache, creneau);//elle met ce creneau dans les creneuaxPlanifies
                //-------------------------- Interface || a travers la consol
                System.out.print("Souhaitez-vous bloquer ce creneau pour cette tache? (0 pour non 1 pour oui) : ");
                Scanner sc = new Scanner(System.in);
                int reponse = sc.nextInt();
                if (reponse == 1) {
                    creneau.bloquer(); //rendre son Etat BLoque
                }
            } else {
                //la mettre dans uscheduled
                System.out.print("Echec de planification! la tache est unscheduled");

            }

        } else {
            System.out.println("Vous ne pouvez pas choisir ce creneau car sa date depasse la date limite de la tache ");
        }
        return palifieeAvecSucces;
    }

    //planification manuelle d'une tache decomposable
    //planification manuelle d'une tache decomposable
    public void planifierTache (Periode periode,Creneau creneau,TacheComposee tache ){
        //cette tache est décomposable donc :
        // si dureeCreneau>= dureeTache=> plannifier normalement
        //sinon => decomposition de la tache
        if(creneau.calculerDuree()>=tache.getDuree()){
            //la palannifier comme une tache simple
            System.out.println(" le creneau a une duree suffisante => planifiee comme une tache simple");
            planifierTache(periode,creneau,tache);
        }else{
            System.out.println("le temps du creneau ne suffit pas il faut proposer une decomposition");
            TreeMap<Creneau,Tache> decompositionProposee= tache.planifierTacheComposee(periode,creneau,this);
            if(decompositionProposee.isEmpty()==false){
                System.out.println("Voici la decomposition qu'on vous propose pour cette tache: ");
                for(Map.Entry<Creneau,Tache> entry:decompositionProposee.entrySet()){
                    System.out.println("--> Creneau: ");
                    System.out.println("date: "+entry.getKey().getDate());
                    System.out.println("heure debut: "+entry.getKey().getheureDebut());
                    System.out.println("heure fin: "+entry.getKey().getheureFin());
                    System.out.println("--> La sousTaCHE: ");
                    System.out.println(" nonm: "+entry.getValue().getNom());
                    System.out.println(" duree: "+entry.getValue().getDuree());
                }
                System.out.println("voulez-vous la valider?");
                Scanner sc=new Scanner(System.in);
                int choix=sc.nextInt();
                if(choix==1){
                    tache.setMapSousTaches(decompositionProposee);
                    tache.setValide(true);
                    //lancement de la planification
                    for(Map.Entry<Creneau,Tache> entry:decompositionProposee.entrySet()){
                        this.planifierTache(periode,entry.getKey(),entry.getValue());//planifier la sous-tache comme une tache simple
                    }

                }
            } //sinon elle est deja unscheduled
        }

    }


    //********************************************************************************/
    //                          Planification automatique                            */
    //********************************************************************************/

    //ces taches sont ordonnees par priorité
    public Planning palnifierTache(Systeme system, TreeSet<Tache> taches, Periode periode) {
        return system.planifierAutomatiqueTaches(taches, periode, this);
    }

    //Verfier le planning propose par le systeme
    public void verfierPlaning(Planning planning) {
        //on doit faire appel a une methode GUI
        //pour l'instant dans la console

        //--> afficher le Planning
        //--> demande

        System.out.print("Voulez-vous valider cette planification? (1 pout oui, 0 pour non): ");
        Scanner sc = new Scanner(System.in);
        int reponse = sc.nextInt();
        if (reponse == 1) {
            //ajouter ce planning dans l'historique
            this.monHisto.ajouterPlanning(planning);
        }

    }

    public void ajouterBadge(Badge b) {
        //on ajoute un badge gagne dans mesBadges
        switch (b) {
            case GOOD -> {
                this.mesRealisation.ajouterBadge(Badge.GOOD);
            }
            case VERYGOOD -> {
                this.mesRealisation.ajouterBadge(Badge.VERYGOOD);
            }
            case EXCELENT -> {
                this.mesRealisation.ajouterBadge(Badge.EXCELENT);
            }
        }
    }

    public void ajouterRealisation(Tache tache) {
        //on sauvgarde la tache realisee dans mesRealisation
        mesRealisation.ajouterTache(tache);

    }

    public void ajouterRealisation(Projet projet) {
        mesRealisation.ajouterProjet(projet);
    }

    public void evaluerTache(Tache tache, EtatRealisation etat) {
        tache.setEtat(etat);
    }

    public void replanifierTache(Tache tache, int duree, Periode periode) {
        if (tache.getEtat().equals(EtatRealisation.INPROGRESS)) {
            tache.replanifier(duree, this, periode);
        } else {
            System.out.println("La replanification est impossible.");
        }
    }

    public void repalnifierTache(Tache tache, int duree, LocalDate deadline, Periode periode) {
        if (tache.getEtat().equals(EtatRealisation.INPROGRESS)) {
            tache.replanifier(duree, this, deadline, periode);
        } else {
            System.out.println("La replanification est impossible.");

        }
    }

    //reporter une tache notRealised dans la meme journnee
    public void reporterTache(Tache tache, LocalTime nouvelleHeureDebut, Periode periode) {
        // on doit vérifier que la tache est non realisee
        if (tache.getEtat().equals(EtatRealisation.NOTREALISED)) {
            tache.reporter(nouvelleHeureDebut, this, periode);
        } else {
            System.out.println("Le report est impossible.");
        }
    }

    //report dans une nouvelle journee
    public void reporterTache(Tache tache, LocalTime nouvelleHeureDebut, LocalDate nouvelleJournee, Periode periode) {
        if (tache.getEtat().equals(EtatRealisation.NOTREALISED)) {
            tache.reporter(nouvelleHeureDebut, this, nouvelleJournee, periode);
        } else {
            System.out.println("Le report est impossible.");
        }
    }
    //-------------------------------------------------------------------------------------------------
    //---------------------        Les methode consulter les statistiques de realisation   ------------
    //-------------------------------------------------------------------------------------------------

    //consulter l'etat de realisation d'une tache dans une journee
    public void realisationTaches(Journee jour) {
        this.mesRealisation.realisationTache(jour);
    }

    public void realisationProjet(Projet projet) {
        this.mesRealisation.realisationProjet(projet);
    }

    public void voirBadges() {
        this.mesRealisation.voirBadges();
    }

    public double donnerRendementJournalier(LocalDate dateJournee) {
        Journee journee = new Journee();//correspondant a dateJournee dans le calendrier
        return this.mesRealisation.calculerRendementjournalier(journee);
    }

    public double calulerMoyenneRendementJournalier(Periode periode) {
        return this.mesRealisation.calulerMoyenneRendementJournalier(periode);
    }

    public int nbEncouragement(Periode periode) {
        return this.mesRealisation.nbEncouragement(periode);
    }

    public void jourRentable(Periode periode) {
        RendementJournee rend = this.mesRealisation.jourRentable(periode);
        System.out.println("Le jour le plus rentable dans cette periode est: " + rend.getDate().toString() + " son rendement est: " + rend.getRendement());
    }

    public void jourRentable() {
        RendementJournee rend = this.mesRealisation.jourRentable(this.monHisto);
        System.out.println("Le jour le plus rentable depuis le debut: " + rend.getDate().toString() + " son rendement est: " + rend.getRendement());
    }

    public void dureeCategorie(Categorie categorie) {
        int duree = this.mesRealisation.dureeCategorie(categorie);
        System.out.println("La duree que vous passez dans la categorie " + categorie.getCategorie() + "est: " + duree);
    }

    // Cette methode est utlisée pour planifier un projet
    // sachant que le projet est un ensemble de taches simples
    // on doit trouver pour chaque tache le creneau correspandant 
    // on commence d'abord par la recherche du creneau 
    public void planifierEnsembleTaches(ArrayList<Tache> listTache, Periode periode) {
        ArrayList<Tache> listOrdonnee = this.ordonnerTaches(listTache);

        for (int i = 0; i < listOrdonnee.size(); i++) {
            Tache tache = listOrdonnee.get(i);
            Calendrier calendrier = this.getCalendrier();
            Creneau creneauTrouve = calendrier.CreneauLibre(tache.getDateLimite(), tache.getDuree(), periode);

            if (creneauTrouve == null) {
                System.out.println("On n'a pas trouve un creneau libre pour la tache numero " + (i + 1) + " :");

            } else {
                boolean ajouterTache = false;
                ajouterTache = creneauTrouve.ajouterTache(periode, tache, this);
                if (ajouterTache == true) {
                    System.out.println("La tache numero " + (i + 1) + "a ete bien palanifiee");
                    System.out.println("la duree de cette tache " + tache.getDuree());
                    System.out.println(" la date de planification " + tache.getDatePlanification());
                } else {
                    System.out.println("La tache numero " + (i + 1) + " n a pas ete palanifiee");

                }
            }


        }
        // Cette methode est utlisée pour ordonner les taches selon leurs deadline et leurs priorite
    }



    public void planifierProjet(Projet projet, Periode periode) {

        ArrayList<Tache> listOrdonnee = this.ordonnerTaches(projet.getTaches());

        for (int i = 0; i < listOrdonnee.size(); i++) {
            Tache tache = listOrdonnee.get(i);
            Calendrier calendrier = this.getCalendrier();
            Creneau creneauTrouve = calendrier.CreneauLibre(tache.getDateLimite(), tache.getDuree(), periode);

            if (creneauTrouve == null) {
                System.out.println("On n'a pas trouve un creneau libre pour la tache numero " + (i + 1) + " :");

            } else {
                boolean ajouterTache = false;
                ajouterTache = creneauTrouve.ajouterTache(periode, tache, this);
                if (ajouterTache == true) {
                    System.out.println("La tache numero " + (i + 1) + "a ete bien palanifiee");
                    System.out.println("la duree de cette tache " + tache.getDuree());
                    System.out.println(" la date de planification " + tache.getDatePlanification());
                } else {
                    System.out.println("La tache numero " + (i + 1) + " n a pas ete palanifiee");

                }
            }


        }
    }
    public ArrayList<Tache> ordonnerTaches(ArrayList<Tache> taches) {
        Collections.sort(taches);
        return taches;
    }
    // cette methode nous aide a planifie un ensemble de taches sans specifier une periode
    // on donne comme parametre la liste des taches selectionnes par l'utilisateur
    public void planifierEnsembleTache(ArrayList<Tache> listTache) {
        ArrayList<Tache> listOrdonnee = this.ordonnerTaches(listTache);
        for (int i = 0; i < listOrdonnee.size(); i++) {
            Tache tache = listOrdonnee.get(i);
            Calendrier calendrier = this.getCalendrier();
            Creneau creneauTrouve = calendrier.CreneauLibre(tache.getDateLimite(), tache.getDuree());

            if (creneauTrouve == null) {
                System.out.println("On n'a pas trouve un creneau libre pour la tache numero " + (i + 1) + " :");

            } else {
                boolean ajouterTache = false;
                ajouterTache = creneauTrouve.ajouterTache(tache, this);
                if (ajouterTache == true) {
                    System.out.println("La tache numero " + (i + 1) + "a ete bien palanifiee");
                    System.out.println("la duree de cette tache " + tache.getDuree());
                    System.out.println(" la date de planification " + tache.getDatePlanification());
                } else {
                    System.out.println("La tache numero " + (i + 1) + " n a pas ete palanifiee");

                }
            }


        }
    }

    public void jourRentable(TreeSet<Journee> jours) {
        RendementJournee rend = this.mesRealisation.jourRentable(jours);
        System.out.println("Le jour le plus rentable dans cette periode est: " + rend.getDate().toString() + " son rendement est: " + rend.getRendement());
    }

    public double donnerRendementJournalier(Journee journee) {
        return this.mesRealisation.calculerRendementjournalier(journee);
    }

    public void afficherMesRealisation(){
        System.out.println("le nombre d'encouragemnts: "+this.mesRealisation.getNbEncouragement());
        System.out.println("le nombre de badge good: "+this.mesRealisation.getMesBagdes().get(Badge.GOOD));
        System.out.println("le nombre de badge very good: "+this.mesRealisation.getMesBagdes().get(Badge.VERYGOOD));
        System.out.println("le nombre de badge excelent: "+this.mesRealisation.getMesBagdes().get(Badge.EXCELENT));
    }
}