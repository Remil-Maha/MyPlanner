package Noyau.Classes;
import java.util.*;
import java.time.*;

public class Calendrier  {
	private TreeSet<Journee> journees=new TreeSet<Journee>();
	private Calendar calendrier;

	public Calendrier() {
		this.calendrier = Calendar.getInstance();
	}
	public TreeSet<Journee> getJournees() {
		return journees;
	}
	public void setJournees(TreeSet<Journee> journees) {
		this.journees = journees;
	}
	public void creerJournees(LocalDate dateDebut,LocalDate dateFin){

		LocalDate dateCourante =dateDebut;
		while (!dateCourante.isAfter(dateFin)) {
			Journee jour=new Journee(dateCourante);
			this.journees.add(jour);
			dateCourante = dateCourante.plusDays(1);
		}
	}
	public void ajouterJourneesPeriode(Periode periode){
		this.creerJournees(periode.getDateDebut(),periode.getDateFin());
	}
	//Si je vais ajouter un creneau au mon calendrier je dois d'abord:
	//sachant que le calendrier est un ensmeble de journées et la journée contient des creneaux libres
	//bien sur le creneau est libre donc je vais l'ajouter a l'ensemble des creneaux libres d'une journée donnée
	//alors on doit faire premierement la recherche de la journée convenable a ce creneau
	/*public void ajouterCreneau(Creneau creneau) {
		// recherche de la journée correspondante
		Journee jour =this.chercherJournee (creneau.getDate());
		jour.ajouterCreneauL(creneau);
	}*/

	/*public void modifierCreneau(Creneau creneau1,Creneau creneau2) {
		// pour modifier un creneau dans une journée donnée on doit trouver d'abord la journée correspondante
		// ensuite il va supprimer ce creneau et ajouter le creneau voulu
		Journee jour =this.chercherJournee (creneau1.getDate());
		jour.supprimerCreneau(creneau1);
        jour.ajouterCreneau(creneau2);
	}
	public void supprimerCreneau (Creneau creneau) {
		Journee jour =this.chercherJournee (creneau.getDate());
		jour.supprimerCreneau(creneau);
	}*/



	// pour qu'il trouve les creneaux selon une durée et une dateLimite donnée on doit
	// parcourire la liste des journées et atteter le parcours lorsqu'on depasse la date limite
	// ensuite on doit parcourire dans chaque journée la liste des creneaux pour trouver les creneaux qui ont la durée voulue

	public    ArrayList<Creneau>  rechercherCreneauLibre(LocalDate dateDebut, LocalDate dateFin, int duree){
		ArrayList<Creneau> creneauxVoulus = new ArrayList<Creneau>();
		for( Journee journee :journees) {
			if(journee.getDate().isAfter(dateDebut) && journee.getDate().isBefore(dateFin)) {
				for(Creneau creneau : journee.getSesCreneaux()) {
					if(creneau.calculerDuree()== duree ) {
						creneauxVoulus.add(creneau);
					}

				}

			}
		}
		return creneauxVoulus;
	}

	public   ArrayList<Creneau> rechercherCreneauLibre(LocalDate dateDebut, LocalDate dateFin){
		ArrayList<Creneau> creneauxVoulus = new ArrayList<>();
		for( Journee journee :journees) {
			if(journee.getDate().isAfter(dateDebut) && journee.getDate().isBefore(dateFin)) {
				for(Creneau creneau : journee.getSesCreneaux()) {
					creneauxVoulus.add(creneau);
				}
			}
		}
		return creneauxVoulus;
	}


	// on a besoin de cette methode pour touver une journee dans le calendrier
	public Journee trouvJournee(LocalDate date) {
		Iterator<Journee> iterator = journees.iterator();
		boolean trouve = false;
		Journee journee =null;
		while (iterator.hasNext() && trouve==false) {
			journee = iterator.next();
			if (journee.getDate().equals(date)) {
				trouve = true;
			}
		}
		return journee;
	}
	// cette methode nous donne creneau libre qui a  une duree voulue et ne depassent pas le deadline
	public Creneau CreneauLibre(LocalDate datelimite, int duree){
		Iterator<Journee> iterator = this.journees.iterator();
		Creneau creneau=null;
		boolean stop=false; // ce booleen est utlisé pour arreter la rechercher si on trouve le premier creneau voulu

		while (iterator.hasNext() && stop==false) {
			Journee journee = iterator.next();
			if (journee.getDate().isBefore(datelimite) || journee.getDate().equals(datelimite)) {
				Iterator<Creneau> creneauIterator = journee.getSesCreneaux().iterator();
				while (creneauIterator.hasNext() && stop==false) {
					creneau = creneauIterator.next();
					if (creneau.calculerDuree() >= duree) {
						stop=true;
					}
				}

			}
		}
		return creneau ;
	}

	public Creneau CreneauLibre(LocalDate datelimite, int duree,Periode periode,LocalDate dateDebut){


		LocalDate dateFin=periode.getDateFin();
		Creneau creneau=null;
		boolean stop=false; // ce booleen est utlisé pour arreter la rechercher si on trouve le premier creneau voulu
		for (LocalDate date = dateDebut; !date.isAfter(dateFin) && !stop; date = date.plusDays(1)) {
			Journee journee = this.trouvJournee(date);
			if (journee != null && (journee.getDate().isBefore(datelimite) || journee.getDate().equals(datelimite))) {
				Iterator<Creneau> creneauIterator = journee.getSesCreneaux().iterator();
				while (creneauIterator.hasNext() && !stop) {
					creneau = creneauIterator.next();
					if (creneau.calculerDuree() >= duree) {
						stop = true;
					}
				}
			}
		}

		return creneau;
	}
	public Creneau CreneauLibre(LocalDate datelimite, int duree,Periode periode){

        LocalDate dateDebut=periode.getDateDebut();
		LocalDate dateFin=periode.getDateFin();
		Creneau creneau=null;
		boolean stop=false; // ce booleen est utlisé pour arreter la rechercher si on trouve le premier creneau voulu
		for (LocalDate date = dateDebut; !date.isAfter(dateFin) && !stop; date = date.plusDays(1)) {
			Journee journee = this.trouvJournee(date);
			if (journee != null && (journee.getDate().isBefore(datelimite) || journee.getDate().equals(datelimite))) {
				Iterator<Creneau> creneauIterator = journee.getSesCreneaux().iterator();
				while (creneauIterator.hasNext() && !stop) {
					creneau = creneauIterator.next();
					if (creneau.calculerDuree() >= duree) {
						stop = true;
					}
				}
			}
		}

		return creneau;
	}
	// on a besoin d'une methode pour faire la recherche d'un creneau correspoandant a une date donnee et une duree donnee
	// pour l'utiliser dans le report
	public Creneau CreneauLibreReport(LocalDate date,int duree,LocalTime heureDebut){
		Iterator<Journee> iterator = this.journees.iterator();
		Creneau creneau=null;
		boolean stop=false; // ce booleen est utilisé pour arreter la rechercher si on trouve le premier creneau voulu
		while (iterator.hasNext() && stop==false) {
			Journee journee = iterator.next();
			if (journee.getDate().equals(date)) {
				Iterator<Creneau> creneauIterator = journee.getSesCreneaux().iterator();
				while (creneauIterator.hasNext() && stop==false) {
					creneau = creneauIterator.next();
					if (creneau.calculerDuree() >= duree && creneau.getheureDebut().equals(heureDebut)) {
						stop=true;
					}
				}

			}
		}
		return creneau ;
	}
	// faire la rechercher pour trouver le creneau convenable en donnant la  nouvelle date limite de la tache
	public Creneau CreneauLibreReport2(int duree,LocalDate dateLimite,LocalTime heureDebut){
		Iterator<Journee> iterator = this.journees.iterator();
		Creneau creneau=null;
		boolean stop=false; // ce booleen est utlisé pour arreter la rechercher si on trouve le premier creneau voulu
		while (iterator.hasNext() && stop==false) {
			Journee journee = iterator.next();
			// on cherche dans les creneaux dont la date est avant la date limite ou la date limite elle meme
			if (journee.getDate().isBefore(dateLimite) || journee.getDate().equals(dateLimite)) {
				Iterator<Creneau> creneauIterator = journee.getSesCreneaux().iterator();
				while (creneauIterator.hasNext() && stop==false) {
					creneau = creneauIterator.next();
					if (creneau.calculerDuree() >= duree && creneau.getheureDebut().equals(heureDebut)) {
						stop=true;
					}
				}

			}
		}
		return creneau ;
	}


	// Cette methode nous aide a trouver le creneau ou la tache a ete planifiee
	// donc on doit fournir la tache
	public Creneau rechercherCreneauOccpue(Tache tache){
		Iterator<Journee> iterator = this.journees.iterator();
		Creneau creneau=null;
		boolean stop=false; // ce booleen est utlisé pour arreter la rechercher si on trouve le premier creneau voulu
		while (iterator.hasNext() && stop==false) {
			Journee journee = iterator.next();
			if (journee.getDate()==tache.getDatePlanification()) {
				Iterator<Creneau> creneauIterator = journee.getCreneauxTachesPlanifiees().iterator();
				while (creneauIterator.hasNext() && stop==false) {
					creneau = creneauIterator.next();
					if (creneau.getTache()==tache) {
						stop=true;
					}
				}

			}
		}
		return creneau ;
	}

	// cette methode nous aide a suppimer le creneau libre de la liste des creneaux libres




}


