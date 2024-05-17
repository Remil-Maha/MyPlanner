import Noyau.Classes.*;
import java.util.*;
import java.time.*;
public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------------------------------------------------------------");
        System.out.println("       Creation Systeme MyDesktopPlaner    ");
        System.out.println("------------------------------------------------------------");
        Systeme myDesktopPlanner = new Systeme();

        Utilisateur user1 = new Utilisateur("utilisateur1");
        Utilisateur user2 = new Utilisateur("Utilisateur2");

        HashMap<String, Utilisateur> utilisateurs = new HashMap<String, Utilisateur>();

        utilisateurs.put(user1.getNom(), user1);
        utilisateurs.put(user2.getNom(), user2);
        myDesktopPlanner.setUtilisateurs(utilisateurs);

        System.out.println("------------------------------------------------------------");
        System.out.println("         Authentification        ");
        System.out.println("------------------------------------------------------------");

        System.out.println("    -1- utilisateur existant");
        Utilisateur user3 = new Utilisateur("utilisateur1");
        myDesktopPlanner.ajouterNouveauUtilisateur(user3);

        System.out.println("    -2- utilisateur existant essaye d'entrer dans l'application");
        myDesktopPlanner.authentifier(user1.getNom());

        System.out.println("    -3- utilisateur non existant essaye d'entrer");
        Utilisateur user4 = new Utilisateur("utilisateur4");
        myDesktopPlanner.authentifier(user4.getNom());

        System.out.println("    -4- inscription de l'utilisateur precendent ");
        myDesktopPlanner.ajouterNouveauUtilisateur(user4);


        System.out.println("    -> L'ensemble des utilisateur qui utilisent cette app sont:    <-");
        for (Map.Entry<String, Utilisateur> entry : myDesktopPlanner.getUtilisateurs().entrySet()) {
            String cle = entry.getKey();
            System.out.println(cle);
        }

        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("[[ La suite de la simulation est avec l'utilisateur1 (durée min=30min, nbTache min=3)  ]]");
        System.out.println("------------------------------------------------------------------------------------------------");

        user1.ajouterParametres(30, 3);

        System.out.println("------------------------------------------------------------");
        System.out.println("         Ajout de périodes        ");
        System.out.println("------------------------------------------------------------");

        System.out.println("------------------------------------------------------------");
        System.out.println(" 1-  FIXER UNE PERIODE ");
        System.out.println("------------------------------------------------------------");

        Periode periode =null;
        LocalDate date1;
        LocalDate date2;
        String dateString;


        do{
            System.out.println("  -Veuillez introduire les informations relatives a la periodes: ");
            System.out.print("         -Date début de la période (format: yyyy-MM-dd): ");
            dateString = scanner.nextLine();

            date1 = LocalDate.parse(dateString);
            System.out.print("         -Date fin de la période (format: yyyy-MM-dd): ");
            dateString = scanner.nextLine();

            date2 = LocalDate.parse(dateString);
            periode=user1.fixerPeriode(date1, date2);
            if(periode==null){
                System.out.println("Donner une autre periode svp");
            }
        }while (periode==null);

        System.out.println("------------------------------------------------------------");
        System.out.println("2-   Introduction de créneaux libres pour la période donnée ");
        System.out.println("------------------------------------------------------------");

        user1.fixerCreneauxLibres(periode);

        System.out.println(" ->Affichage des créneaux fixés par l'utilisateur: ");

        TreeSet<Journee> journeesPeriode = periode.getJournees_Creneaux();
        System.out.println("      -le nombre de jours dans cette periode: " + journeesPeriode.size());
        for (Journee journee : journeesPeriode) {
            System.out.println("      - le nomnbre de creneaux introduits : " + journee.getSesCreneaux().size());

            for (Creneau creneau : journee.getSesCreneaux()) {
                System.out.println("date: " + creneau.getDate());
                System.out.println("heure debut: " + creneau.getheureDebut());
                System.out.println("heure fin: " + creneau.getheureFin());
                System.out.println("-----------------------------------------");
            }
        }

        //maintenat on cree un planning correspondant a cette periode
        //il est affecté dans mesPlanning de l'utilisateur
        Planning planning1 = new Planning(periode);
        user1.ajouterPlanning(planning1);


        System.out.println("------------------------------------------------------------");
        System.out.println(" 3- Introduction des taches  ");
        System.out.println("------------------------------------------------------------");
        boolean stop = false;
        Tache tache;

        do {

            System.out.print("Donner le nom de la tache: ");
            Scanner sc1 = new Scanner(System.in);
            String nom = sc1.nextLine();
            System.out.print("Donner la duree de la tache: ");
            Scanner sc2 = new Scanner(System.in);
            int duree = sc2.nextInt();
            System.out.print("Donner le deadline de la tache: ");

            Scanner scanner1 = new Scanner(System.in);
            String dateString2 = scanner1.nextLine();
            date1 = LocalDate.parse(dateString2);

            System.out.println("Donner la priorite de la tache: ");
            System.out.println("    _1:" + Priorite.LOW + "-2:" + Priorite.MEDIUM + "-3:" + Priorite.HIGH);
            Scanner sc3 = new Scanner(System.in);
            int choix = sc3.nextInt();
            Priorite priorite = null;
            switch (choix) {
                case 1:
                    priorite = Priorite.LOW;
                    break;
                case 2:
                    priorite = Priorite.MEDIUM;
                    break;
                case 3:
                    priorite = Priorite.HIGH;
                    break;
            }
            System.out.print("Donner la catégorie de la tache: ");
            Scanner scanner2 = new Scanner(System.in);
            String cat = scanner2.nextLine();
            System.out.print("Est ce que cette tache est 'Simple' ou 'Decomposable': ");
            Scanner scanner4 = new Scanner(System.in);
            String type = scanner4.nextLine();
            if (type.equals("Simple")) {
                System.out.println("Vu que c'est une tache simple, donner la periodicite(>=0): ");
                Scanner scc = new Scanner(System.in);
                int periodicite = scc.nextInt();
                tache = new TacheSimple(
                        nom,
                        priorite,
                        duree,
                        date1,
                        cat,
                        periodicite
                );
            } else {
                tache = new TacheComposee(
                        nom,
                        priorite,
                        duree,
                        date1,
                        cat);
            }
            user1.introduireTache(tache);//mettre la tache introduite dans mesTAches du user1
            System.out.print(" Ajouter une autre Tache? ");
            Scanner scanner3 = new Scanner(System.in);
            int reponse = scanner3.nextInt();
            if (reponse == 0) {
                stop = true;
            }
        } while (stop != true);


        System.out.println("-------------------------------------------------------------------");
        System.out.println("  4- Simuler la planification des taches (Simple // Decomposable) ");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("   -> Indication: D'abord, on selectionne la tache à planifier.");
        Tache tacheChoisie = null;
        boolean arretPlanif=false;
        do {
            int i = 1;
            for (Tache tache1 : user1.getMesTaches()) {
                System.out.println("--------------------------------");
                System.out.println("       Tache numéro: " + i);
                System.out.println("--------------------------------");
                System.out.println("");
                tache1.afficher();
                System.out.println("");
                i++;
            }
            System.out.print("Veuillez entrer le numero de la tache a palanifier:  ");
            Scanner scanner1 = new Scanner(System.in);
            int num = scanner1.nextInt();
            ArrayList<Tache> lesTache = new ArrayList<>(user1.getMesTaches());
            tacheChoisie = lesTache.get(num - 1);
            System.out.println("   -> Indication2:  on selectionne un creneau.");

            System.out.println("VEUILLEZ CHOISIR UN CRENEAU POUR PLANIFIER la TACHE ");
            Creneau creneauChoisi = null;
            i = 1;
            boolean choisi = false;
            ArrayList<Creneau> lesCreneau = new ArrayList<>();

            for (Journee journee : periode.getJournees_Creneaux()) {
                System.out.println("----------------------------------------------------");
                System.out.println("      Les creneau de la journee :" + journee.getDate());
                System.out.println("----------------------------------------------------");
                System.out.println("");
                for (Creneau creneau : journee.getSesCreneaux()) {
                    System.out.println("   Le creneau numero " + i);
                    System.out.println("");
                    System.out.println("       -> La date: " + creneau.getDate());
                    System.out.println("       -> L'heure debut: " + creneau.getheureDebut());
                    System.out.println("       -> L'heure fin: " + creneau.getheureFin());
                    System.out.println("");
                    i++;
                    lesCreneau.add(creneau);
                }
                System.out.println("");
            }
            System.out.println("Veuillez entrer le numero du creneau que vous avez choisi ");
            Scanner scanner3 = new Scanner(System.in);
            int numChoisi = scanner3.nextInt();
            creneauChoisi = lesCreneau.get(numChoisi - 1);
            if (tacheChoisie instanceof TacheSimple) {
                // Code à exécuter si tacheChoisie est une instance de TacheSimple
                user1.planifierTache(planning1.getPeriode(), creneauChoisi, tacheChoisie); //palanification de tache dans periode

            } else {
                // Code à exécuter si tacheChoisie n'est pas une instance de TacheSimple
                user1.planifierTache(planning1.getPeriode(), creneauChoisi, (TacheComposee) tacheChoisie); //palanification de tache dans periode

            }
            System.out.println(" ----> voulez-vous planifier une autre tache? ");
            Scanner sc5=new Scanner(System.in);
            int responsee=sc5.nextInt();
            if(responsee==0){
                arretPlanif=true;
            }
        }while (arretPlanif!=true);



        System.out.println("---------------------------------------------------------------------------");
        System.out.println(" ---->>> Tester si la planification des tache s'est bien deroulée   <<<-----");
        System.out.println("----------------------------------------------------------------------------");

        System.out.println("\n                   ---> Teste sur la periode <---");
        journeesPeriode=planning1.getPeriode().getJournees_Creneaux();
        for(Journee journee:journeesPeriode){
            System.out.println("  -Le nomnbre de creneaux libres dans la journée "+journee.getDate()+"est: "+journee.getSesCreneaux().size());
            System.out.println("   ->Ses creneaux libres: ");
            for(Creneau creneau: journee.getSesCreneaux()){
                System.out.println("    * Date: "+creneau.getDate());
                System.out.println("    * "+creneau.getheureDebut()+"->"+creneau.getheureFin());
                System.out.println("------------------------------------------------------------------------");
            }
            System.out.println("   -Le nombre de creneaux occupés(avec tache) dans la journée "+journee.getDate()+" est: "+journee.getCreneauxTachesPlanifiees().size());
            for(Creneau creneau: journee.getCreneauxTachesPlanifiees()){
                System.out.println("    * Date: "+creneau.getDate());
                System.out.println("    * "+creneau.getheureDebut()+"->"+creneau.getheureFin());
                System.out.println("le nom de la tache qui a ete planifiée: "+creneau.getTache().getNom());
                System.out.println("------------------------------------------------------------------------");
            }
        }
        System.out.println("\n\n                   ---> Teste sur le calendrier <---");
        TreeSet<Journee> journeesCalendrier=user1.getCalendrier().getJournees();
        for(Journee journee:journeesCalendrier){
            System.out.println("  -Le nomnbre de creneaux libres dans la journée "+journee.getDate()+"est: "+journee.getSesCreneaux().size());
            System.out.println("   ->Ses creneaux libres: ");
            for(Creneau creneau: journee.getSesCreneaux()){
                System.out.println("    * Date: "+creneau.getDate());
                System.out.println("    * "+creneau.getheureDebut()+"->"+creneau.getheureFin());
                System.out.println("------------------------------------------------------------------------");
            }
            System.out.println("   -Le nombre de creneaux occupés(avec tache) dans la journée "+journee.getDate()+" est: "+journee.getCreneauxTachesPlanifiees().size());
            for(Creneau creneau: journee.getCreneauxTachesPlanifiees()){
                System.out.println("    * Date: "+creneau.getDate());
                System.out.println("    * "+creneau.getheureDebut()+"->"+creneau.getheureFin());
                System.out.println("le nom de la tache qui a ete planifiée: "+creneau.getTache().getNom());
                System.out.println("------------------------------------------------------------------------");
            }
        }

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("    5- Simuler la replanification d'une tache    ");
        System.out.println("---------------------------------------------------------------------------");
        //on va créer une tache et un creneau
        //on l'a planifie
        //puis on essaye de la replanifier

        String dateNouvelle="2023-06-01";
        LocalDate newDate=LocalDate.parse(dateNouvelle);
        //la tache
        TacheSimple tacheSimple=new TacheSimple("RevisionAnalyse",Priorite.HIGH,90,newDate,"Studies");
        tacheSimple.setEtat(EtatRealisation.INPROGRESS); //car pour replanifier il faut que la tache soit INPROGRESS

        // on doit creer un creneau
        String dateCreneau="2023-05-28";
        LocalDate newDateCreneau=LocalDate.parse(dateCreneau);
        String heureCreneau1="10:00";
        LocalTime newHeureDebut=LocalTime.parse(heureCreneau1);
        String heureCreneau2="12:00";
        LocalTime newHeureFin=LocalTime.parse(heureCreneau2);
        Creneau creneauNouveau =new Creneau(newDateCreneau,newHeureDebut ,newHeureFin,EtatCreneau.LIBRE);

        //on ajoute ce creneau dans la periode
        periode.affecterCreneau(creneauNouveau.getDate(),creneauNouveau,user1);

        //lancer ca première palnification
        user1.planifierTache(periode,creneauNouveau,tacheSimple);

        System.out.println("  ->Cas numero 1 -Test de la replanification de la tache dans une periode donnee sans changer le deadline de la tache");
        System.out.println("   ->Veuillez entrer la durre supplimentaire que vous voulez ajouter");
        Scanner scanner10= new Scanner(System.in);
        int dureeSup = scanner10.nextInt();

        user1.replanifierTache(tacheSimple,dureeSup,periode);

        System.out.println("\n\n                   ---> Teste sur le calendrier <---");
        for(Journee journee:journeesCalendrier){
            System.out.println("  -Le nomnbre de creneaux libres dans la journée "+journee.getDate()+"est: "+journee.getSesCreneaux().size());
            System.out.println("   ->Ses creneaux libres: ");
            for(Creneau creneau: journee.getSesCreneaux()){
                System.out.println("    * Date: "+creneau.getDate());
                System.out.println("    * "+creneau.getheureDebut()+"->"+creneau.getheureFin());
                System.out.println("------------------------------------------------------------------------");
            }
            System.out.println("   -Le nombre de creneaux occupés(avec tache) dans la journée "+journee.getDate()+" est: "+journee.getCreneauxTachesPlanifiees().size());
            for(Creneau creneau: journee.getCreneauxTachesPlanifiees()){
                System.out.println("    * Date: "+creneau.getDate());
                System.out.println("    * "+creneau.getheureDebut()+"->"+creneau.getheureFin());
                System.out.println("le nom de la tache qui a ete planifiée: "+creneau.getTache().getNom());
                System.out.println("------------------------------------------------------------------------");
            }
        }


        dateNouvelle="2023-06-01";
        newDate=LocalDate.parse(dateNouvelle);
        TacheSimple tacheSimple1=new TacheSimple("Revision LOGM",Priorite.HIGH,60,newDate,"STUDIES");
         tacheSimple1.setEtat(EtatRealisation.INPROGRESS);
        // on doit creer un creneau
         dateCreneau="2023-05-30";
        newDateCreneau=LocalDate.parse(dateCreneau);
        heureCreneau1="14:00";
        newHeureDebut=LocalTime.parse(heureCreneau1);
         heureCreneau2="15:00";
       newHeureFin=LocalTime.parse(heureCreneau2);
        creneauNouveau =new Creneau(newDateCreneau,newHeureDebut ,newHeureFin,EtatCreneau.LIBRE);
        periode.affecterCreneau(creneauNouveau.getDate(),creneauNouveau,user1);
        user1.planifierTache(periode,creneauNouveau,tacheSimple1);

        System.out.println("  Cas numero 2-Test de la replanification de la tache dans une periode + changer le deadline ");
        System.out.println("   ->Veuillez entrer la durre supplimentaire que vous voulez ajouter:");
        Scanner scanner11= new Scanner(System.in);
         dureeSup = scanner11.nextInt();
        Scanner scanner12 = new Scanner(System.in);

        System.out.println("   ->Veuillez entrer la nouvelle date limite: ");
        String dateString20 = scanner12.nextLine();
        LocalDate nouveauDeadline = LocalDate.parse(dateString20);

        user1.repalnifierTache(tacheSimple1,dureeSup,nouveauDeadline,periode);

        System.out.println("\n\n                   ---> Teste sur le calendrier <---");
        for(Journee journee:journeesCalendrier){
            System.out.println("  -Le nomnbre de creneaux libres dans la journée "+journee.getDate()+"est: "+journee.getSesCreneaux().size());
            System.out.println("   ->Ses creneaux libres: ");
            for(Creneau creneau: journee.getSesCreneaux()){
                System.out.println("    * Date: "+creneau.getDate());
                System.out.println("    * "+creneau.getheureDebut()+"->"+creneau.getheureFin());
                System.out.println("------------------------------------------------------------------------");
            }
            System.out.println("   -Le nombre de creneaux occupés(avec tache) dans la journée "+journee.getDate()+" est: "+journee.getCreneauxTachesPlanifiees().size());
            for(Creneau creneau: journee.getCreneauxTachesPlanifiees()){
                System.out.println("    * Date: "+creneau.getDate());
                System.out.println("    * "+creneau.getheureDebut()+"->"+creneau.getheureFin());
                System.out.println("le nom de la tache qui a ete planifiée: "+creneau.getTache().getNom());
                System.out.println("------------------------------------------------------------------------");
            }
        }


        System.out.println("---------------------------------------------------------------------------");
        System.out.println("                6-Planification d'un projet");
        System.out.println("---------------------------------------------------------------------------");

        ArrayList<Tache> listeTaches=new ArrayList<>();
        TacheSimple tachep1=new TacheSimple(
                "Choisir une date et destination",
                Priorite.MEDIUM,
                120,
                LocalDate.parse("2023-05-31"),
                "PERSO"
        );
        TacheSimple tachep2=new TacheSimple(
                "reserver",
                Priorite.MEDIUM,
                120,
                LocalDate.parse("2023-06-20"),
                "PERSO"
        );
        TacheSimple tachep3=new TacheSimple(
                "Acheter des billets",
                Priorite.MEDIUM,
                60,
                LocalDate.parse("2023-06-21"),
                "PERSO"
        );
        listeTaches.add(tachep1);
        listeTaches.add(tachep2);
        listeTaches.add(tachep3);

        Projet projet1=new Projet("Vaccances","Preparer mes vacances pour cet ete ",listeTaches);
        //creation du projet
        //le projet est ajouté dans l'attribut mesProjet de l'utilisateur
        user1.creerProjet(projet1);
        user1.planifierProjet(projet1,periode);

        System.out.println("---------------------------------------------------------------------------");
        System.out.println(" ---->>> Tester si la planification du projet s'est bien deroulée   <<<-----");
        System.out.println("----------------------------------------------------------------------------");

        System.out.println("        ---->   le calendrier apres la planification du projet  <----");

        journeesCalendrier=user1.getCalendrier().getJournees();
        System.out.println(" le nombre de jours dans le calendrier: "+journeesCalendrier.size());
        for(Journee journee:journeesCalendrier){
            System.out.println(" le nomnbre de creneaux libres dans cette journee: "+journee.getSesCreneaux().size());

            for(Creneau creneau: journee.getSesCreneaux()){
                System.out.println("date: "+creneau.getDate());
                System.out.println("heure debut: "+creneau.getheureDebut());
                System.out.println("heure fin: "+creneau.getheureFin());
                System.out.println("-----------------------------------------");
            }
            System.out.println(" le nomnbre de creneaux occupes(avec tache) dans cette journee: "+journee.getCreneauxTachesPlanifiees().size());
            for(Creneau creneau: journee.getCreneauxTachesPlanifiees()){

                System.out.println("date: "+creneau.getDate());
                System.out.println("heure debut: "+creneau.getheureDebut());
                System.out.println("heure fin: "+creneau.getheureFin());
                System.out.println("le nom de la tache qui a ete planifiee: "+creneau.getTache().getNom());

                System.out.println("-----------------------------------------");
            }
        }



        System.out.println("---------------------------------------------------------------------------");
        System.out.println("           7- Simulation de la gestion des realisation est des badges   ");
        System.out.println("---------------------------------------------------------------------------");


        //quelques taches a utiliser

        TacheSimple t1=new TacheSimple(
                "faire serie analyse1",
                Priorite.MEDIUM,
                60,
                LocalDate.parse("2023-06-30"),
                "STUDIES"
        );
        TacheSimple t2=new TacheSimple(
                "reserver vol",
                Priorite.MEDIUM,
                60,
                LocalDate.parse("2023-06-20"),
                "PERSO"
        );
        TacheSimple t3=new TacheSimple(
                "aller au resto",
                Priorite.MEDIUM,
                60,
                LocalDate.parse("2023-06-21"),
                "PERSO"
        );


        TacheSimple t4=new TacheSimple(
                "faire serie analyse1",
                Priorite.MEDIUM,
                60,
                LocalDate.parse("2023-06-30"),
                "STUDIES"
        );
        TacheSimple t5=new TacheSimple(
                "reserver vol",
                Priorite.MEDIUM,
                60,
                LocalDate.parse("2023-06-20"),
                "PERSO"
        );
        TacheSimple t6=new TacheSimple(
                "aller au resto",
                Priorite.MEDIUM,
                60,
                LocalDate.parse("2023-06-21"),
                "PERSO"
        );
        //fixer une nouvelle periode pour planifier les taches ci-dessus
        //une periode de "2023-06-06" -> "2023-07-01"

        ArrayList<Journee> joursPlanif=new ArrayList<>();

        LocalDate dateDebutp = LocalDate.parse("2023-06-06");
        LocalDate datefinp = LocalDate.parse("2023-07-01");
        Periode periode2= user1.fixerPeriode(dateDebutp,datefinp);

        //sachant que le message Felicitations est attribué que si l'utilisateur arrive à réaliser "nb_min" taches par jour
        //" nb_min" de tache est un parametre donne par l'utilisateur
        //pour user1, nb_min=3
        //donc pour environ pour chaque jour on va essayer de palnifier au minimum 3 taches

        //3 creneaux dans la meme journee pour planifier 3 taches et les realiser

        //journee 1: ( 5 creneaux , 5 taches palanifiees, 3 realisee a 100% 2 autres INPROGRESS)

        Creneau creneautacheT1=new Creneau(dateDebutp,LocalTime.of(13,00),LocalTime.of(14, 00) );
        Creneau creneautacheT2=new Creneau(dateDebutp,LocalTime.of(15,00),LocalTime.of(16, 00) );
        Creneau creneautacheT3=new Creneau(dateDebutp,LocalTime.of(17,00),LocalTime.of(18, 00) );
        Creneau creneautacheT77=new Creneau(dateDebutp,LocalTime.of(20,00),LocalTime.of(21, 00) );
        Creneau creneautacheT88=new Creneau(dateDebutp,LocalTime.of(8,00),LocalTime.of(9, 00) );

        periode2.affecterCreneau(dateDebutp,creneautacheT1,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT2,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT3,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT77,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT88,user1);

        Planning planning2=new Planning(periode2);
        user1.ajouterPlanning(planning2);
        //lancer la planification
        user1.planifierTache(planning2.getPeriode(),creneautacheT1,t1);
        user1.planifierTache(planning2.getPeriode(),creneautacheT2,t2);
        user1.planifierTache(planning2.getPeriode(),creneautacheT3,t3);
        user1.planifierTache(planning2.getPeriode(),creneautacheT77,t4);
        user1.planifierTache(planning2.getPeriode(),creneautacheT88,t5);


        t1.setEtat(EtatRealisation.COMPLETED);
        t2.setEtat(EtatRealisation.COMPLETED);
        t3.setEtat(EtatRealisation.COMPLETED);
        t4.setEtat(EtatRealisation.INPROGRESS);
        t5.setEtat(EtatRealisation.INPROGRESS);
        //affecter les taches completees dans l'attribut mes realisations du user1
        user1.ajouterRealisation(t1);
        user1.ajouterRealisation(t2);
        user1.ajouterRealisation(t3);
        Journee journeeTravail;
        //verfier si le user1 a atteint le nombre min de tache,
        // si oui => le feliciter + marquer la journee comme "une journee qui a recu un encouragement"
        if(user1.getMesRealisation().getTachesRealisees().size()>=user1.getNbTacheMin()){
            myDesktopPlanner.feliciterUtilisateur(user1);
            user1.getMesRealisation().incrementerEncouragement();
            journeeTravail=periode2.donnerJournee(dateDebutp);
            joursPlanif.add(journeeTravail);
            journeeTravail.setNombreTachesAcomplies(3);
            journeeTravail.setRecuEncouragement(true);
            System.out.println("Aujourd'hui le "+journeeTravail.getDate()+" vous avez realisé: "+journeeTravail.getNombreTachesAcomplies()+ "Taches.");
            user1.donnerRendementJournalier(journeeTravail);
        }

        //journee 2: ( 3 creneaux , 3 taches palanifiees, 3 realisee a 100% )

        Creneau creneautacheT4=new Creneau(dateDebutp.plusDays(1),LocalTime.of(13,00),LocalTime.of(14, 00) );
        Creneau creneautacheT5=new Creneau(dateDebutp.plusDays(1),LocalTime.of(15,00),LocalTime.of(16, 00) );
        Creneau creneautacheT6=new Creneau(dateDebutp.plusDays(1),LocalTime.of(17,00),LocalTime.of(18, 00) );
        periode2.affecterCreneau(dateDebutp,creneautacheT4,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT5,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT6,user1);

        user1.planifierTache(planning2.getPeriode(),creneautacheT4,t4);
        user1.planifierTache(planning2.getPeriode(),creneautacheT5,t5);
        user1.planifierTache(planning2.getPeriode(),creneautacheT6,t6);

        t4.setEtat(EtatRealisation.COMPLETED);
        t5.setEtat(EtatRealisation.COMPLETED);
        t6.setEtat(EtatRealisation.COMPLETED);

        user1.ajouterRealisation(t4);
        user1.ajouterRealisation(t5);
        user1.ajouterRealisation(t6);

        myDesktopPlanner.feliciterUtilisateur(user1);
        user1.getMesRealisation().incrementerEncouragement();
        journeeTravail=periode2.donnerJournee(dateDebutp.plusDays(1));
        joursPlanif.add(journeeTravail);

        journeeTravail.setNombreTachesAcomplies(3);
        journeeTravail.setRecuEncouragement(true);
        System.out.println("Aujourd'hui le "+journeeTravail.getDate()+" vous avez realisé: "+journeeTravail.getNombreTachesAcomplies()+ "Taches.");

        //journee 3: ( 3 creneaux , 3 taches palanifiees, 3 realisee a 100% )


        creneautacheT4=new Creneau(dateDebutp.plusDays(2),LocalTime.of(13,00),LocalTime.of(14, 00) );
        creneautacheT5=new Creneau(dateDebutp.plusDays(2),LocalTime.of(15,00),LocalTime.of(16, 00) );
        creneautacheT6=new Creneau(dateDebutp.plusDays(2),LocalTime.of(17,00),LocalTime.of(18, 00) );
        periode2.affecterCreneau(dateDebutp,creneautacheT4,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT5,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT6,user1);

        user1.planifierTache(planning2.getPeriode(),creneautacheT4,t4);
        user1.planifierTache(planning2.getPeriode(),creneautacheT5,t5);
        user1.planifierTache(planning2.getPeriode(),creneautacheT6,t6);

        t4.setEtat(EtatRealisation.COMPLETED);
        t5.setEtat(EtatRealisation.COMPLETED);
        t6.setEtat(EtatRealisation.COMPLETED);

        user1.ajouterRealisation(t4);
        user1.ajouterRealisation(t5);
        user1.ajouterRealisation(t6);

        myDesktopPlanner.feliciterUtilisateur(user1);
        user1.getMesRealisation().incrementerEncouragement();
        journeeTravail=periode2.donnerJournee(dateDebutp.plusDays(2));
        joursPlanif.add(journeeTravail);

        journeeTravail.setNombreTachesAcomplies(3);
        journeeTravail.setRecuEncouragement(true);
        System.out.println("Aujourd'hui le "+journeeTravail.getDate()+" vous avez realisé: "+journeeTravail.getNombreTachesAcomplies()+ "Taches.");

        //journee 4: ( 3 creneaux , 3 taches palanifiees, 3 realisee a 100% )


        creneautacheT4=new Creneau(dateDebutp.plusDays(3),LocalTime.of(13,00),LocalTime.of(14, 00) );
        creneautacheT5=new Creneau(dateDebutp.plusDays(3),LocalTime.of(15,00),LocalTime.of(16, 00) );
        creneautacheT6=new Creneau(dateDebutp.plusDays(3),LocalTime.of(17,00),LocalTime.of(18, 00) );
        periode2.affecterCreneau(dateDebutp,creneautacheT4,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT5,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT6,user1);

        user1.planifierTache(planning2.getPeriode(),creneautacheT4,t4);
        user1.planifierTache(planning2.getPeriode(),creneautacheT5,t5);
        user1.planifierTache(planning2.getPeriode(),creneautacheT6,t6);

        t4.setEtat(EtatRealisation.COMPLETED);
        t5.setEtat(EtatRealisation.COMPLETED);
        t6.setEtat(EtatRealisation.COMPLETED);
        user1.ajouterRealisation(t4);
        user1.ajouterRealisation(t5);
        user1.ajouterRealisation(t6);

        myDesktopPlanner.feliciterUtilisateur(user1);
        user1.getMesRealisation().incrementerEncouragement();
        journeeTravail=periode2.donnerJournee(dateDebutp.plusDays(3));
        joursPlanif.add(journeeTravail);

        journeeTravail.setNombreTachesAcomplies(3);
        journeeTravail.setRecuEncouragement(true);
        System.out.println("Aujourd'hui le "+journeeTravail.getDate()+" vous avez realisé: "+journeeTravail.getNombreTachesAcomplies()+ "Taches.");
        //journee 5: ( 3 creneaux , 3 taches palanifiees, 3 realisee a 100% )


        creneautacheT4=new Creneau(dateDebutp.plusDays(4),LocalTime.of(13,00),LocalTime.of(14, 00) );
        creneautacheT5=new Creneau(dateDebutp.plusDays(4),LocalTime.of(15,00),LocalTime.of(16, 00) );
        creneautacheT6=new Creneau(dateDebutp.plusDays(4),LocalTime.of(17,00),LocalTime.of(18, 00) );
        Creneau creneautacheT7=new Creneau(dateDebutp.plusDays(4),LocalTime.of(8,00),LocalTime.of(9, 00) );
        Creneau creneautacheT8=new Creneau(dateDebutp.plusDays(4),LocalTime.of(10,00),LocalTime.of(11, 00) );

        periode2.affecterCreneau(dateDebutp,creneautacheT4,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT5,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT6,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT7,user1);
        periode2.affecterCreneau(dateDebutp,creneautacheT8,user1);

        user1.planifierTache(planning2.getPeriode(),creneautacheT4,t4);
        user1.planifierTache(planning2.getPeriode(),creneautacheT5,t5);
        user1.planifierTache(planning2.getPeriode(),creneautacheT6,t6);
        user1.planifierTache(planning2.getPeriode(),creneautacheT7,t6);
        user1.planifierTache(planning2.getPeriode(),creneautacheT8,t6);


        t4.setEtat(EtatRealisation.COMPLETED);
        t5.setEtat(EtatRealisation.COMPLETED);
        t6.setEtat(EtatRealisation.COMPLETED);

        user1.ajouterRealisation(t4);
        user1.ajouterRealisation(t5);
        user1.ajouterRealisation(t6);
        user1.ajouterRealisation(t6);
        user1.ajouterRealisation(t6);

        myDesktopPlanner.feliciterUtilisateur(user1);
        user1.getMesRealisation().incrementerEncouragement();
        journeeTravail=periode2.donnerJournee(dateDebutp.plusDays(4));
        joursPlanif.add(journeeTravail);
        journeeTravail.setNombreTachesAcomplies(5);
        journeeTravail.setRecuEncouragement(true);
        System.out.println("Aujourd'hui le "+journeeTravail.getDate()+" vous avez realisé: "+journeeTravail.getNombreTachesAcomplies()+ "Taches.");


        if(user1.getMesRealisation().getNbEncouragement()==5){
            myDesktopPlanner.accorderBadgeGood(user1);
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("          Affichage du rendement journalier de chaque jour ");
        System.out.println("------------------------------------------------------------");
        for(int k=0;k<joursPlanif.size();k++){
            double rendement= user1.donnerRendementJournalier(joursPlanif.get(k));
            System.out.println(" Jour: "+joursPlanif.get(k).getDate()+" - le rendement: "+rendement);
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("          Affichage du jour le plus rentable ");
        System.out.println("------------------------------------------------------------");

        user1.jourRentable(new TreeSet<>(joursPlanif));
        double moyenne= user1.calulerMoyenneRendementJournalier(planning2.getPeriode());
        System.out.println("------------------------------------------------------------");
        System.out.println("          La moyenne du rendement journalier ");
        System.out.println("------------------------------------------------------------");
        System.out.println("la moyenne du rendement journalier dans cette periode est: "+moyenne);
        planning2.setbadgePlanning(user1.getMesRealisation().getMesBagdes());
        System.out.println(" les badges gagnes dans la periode  "+periode2.getDateDebut()+" -> "+periode2.getDateFin()+" sont: ");
        user1.afficherMesRealisation();





        System.out.println("------------------------------------------------------------");
        System.out.println("         8- PLANIFICATION AUTOMATIQUE ");
        System.out.println("------------------------------------------------------------");


        TacheSimple tahce1=new TacheSimple(
                "reviser analyse",
                Priorite.HIGH,
                60,
                LocalDate.parse("2023-05-31"),
                "STUDIES"
        );
        TacheSimple tache2=new TacheSimple(
                "faire tp sfsd",
                Priorite.MEDIUM,
                60,
                LocalDate.parse("2023-05-29"),
                "ETUDES"
        );

        TacheSimple tache3=new TacheSimple(
                "faire le devoir de archi",
                Priorite.HIGH,
                30,
                LocalDate.parse("2023-05-29"),
                "ETUDES"
        );
        ArrayList<Tache> ensembleTache=new ArrayList<>();
        ensembleTache.add(tahce1);
        ensembleTache.add(tache2);
        ensembleTache.add(tache3);
        user1.planifierEnsembleTache(ensembleTache);
        System.out.println("------------------------------------------------------------");
        System.out.println("          Verification du calendrier  ");
        System.out.println("------------------------------------------------------------");
        System.out.println("---->   le calendrier apres la planification d'un ensemble de taches  <----");
        journeesCalendrier=user1.getCalendrier().getJournees();
        System.out.println("le nombre de jours dans le calendrier: "+journeesCalendrier.size());
        for(Journee journee:journeesCalendrier){
            System.out.println(" le nomnbre de creneaux libres dans cette journee: "+journee.getSesCreneaux().size());

            for(Creneau creneau: journee.getSesCreneaux()){
                System.out.println("date: "+creneau.getDate());
                System.out.println("heure debut: "+creneau.getheureDebut());
                System.out.println("heure fin: "+creneau.getheureFin());
                System.out.println("-----------------------------------------");
            }
            System.out.println(" le nomnbre de creneaux occupes(avec tache) dans cette journee: "+journee.getCreneauxTachesPlanifiees().size());
            for(Creneau creneau: journee.getCreneauxTachesPlanifiees()){

                System.out.println("date: "+creneau.getDate());
                System.out.println("heure debut: "+creneau.getheureDebut());
                System.out.println("heure fin: "+creneau.getheureFin());
                System.out.println("le nom de la tache qui a ete planifiee: "+creneau.getTache().getNom());

                System.out.println("-----------------------------------------");
            }
        }
    }
    }  

