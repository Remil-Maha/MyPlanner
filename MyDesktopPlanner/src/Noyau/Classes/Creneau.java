package Noyau.Classes;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
public class Creneau implements Comparable<Creneau>{
	private LocalDate date;
	private LocalTime heureDebut;
	private LocalTime heureFin;
	private Tache tache;
	//Etat est une enumeration : bloqué, libre,occupé ….

	private EtatCreneau etat=EtatCreneau.LIBRE ;//initialement Libre

	public Creneau (LocalDate date,LocalTime heureDebut ,LocalTime heureFin,Tache tache,EtatCreneau etat) {
		this.date=date;
		this.heureDebut=heureDebut;
		this.heureFin=heureFin;
		this.tache=tache;
		this.etat=etat;
	}
	// On a redefini le constructeur car il y a des creneaux libres qui n'ont pas des taches
	public Creneau (LocalDate date,LocalTime heureDebut ,LocalTime heureFin,EtatCreneau etat) {
		this.date=date;
		this.heureDebut=heureDebut;
		this.heureFin=heureFin;
		this.etat=etat;
	}
	public Creneau (LocalDate date,LocalTime heureDebut ,LocalTime heureFin) {
		this.date=date;
		this.heureDebut=heureDebut;
		this.heureFin=heureFin;
	}
	public LocalDate getDate() {
		return this.date;
	}
	public void setDate( LocalDate date) {
		this.date=date;
	}
	public LocalTime getheureDebut() {
		return this.heureDebut;
	}
	public void setheureDebut( LocalTime heure) {
		this.heureDebut=heure;
	}
	public LocalTime getheureFin() {
		return this.heureFin;
	}
	public void setheureFin( LocalTime heure) {
		this.heureFin=heure;
	}
	public Tache getTache() {
		return this.tache;
	}
	public void setTache( Tache tache) {
		this.tache=tache;
	}
	public EtatCreneau getEtat() {
		return this.etat;
	}
	public void setEtat( EtatCreneau etat) {
		this.etat=etat;
	}
	public int calculerDuree() {
		LocalTime time1=this.heureDebut;
		LocalTime time2=this.heureFin;
		long dureeMinutes = Duration.between(time1, time2).toMinutes();
		return (int) dureeMinutes;
	}
	public int calculerDuree2(LocalTime time1, LocalTime time2) {
		if (time1.isAfter(time2)) {
	        LocalTime temp = time1;
	        time1 = time2;
	        time2 = temp;
	    }
		long dureeMinutes = Duration.between(time1, time2).toMinutes();
		return (int) dureeMinutes;
	}

	public  LocalTime ajouterDuree(LocalTime heure, int dureeEnMinutes) {
		return heure.plusMinutes(dureeEnMinutes);
	}
	public boolean decomposerCreneau(Utilisateur user, Tache tache){
		boolean ajoutAvecSucces=false;
		// on doit faire une recheche pour trouver le creneau convenable
		int dureeCreneau=this.calculerDuree();
		int dureeTache=tache.getDuree();
		Journee journeeCreneau=user.getCalendrier().trouvJournee(this.getDate());
		// le premier cas le creneau a une duree qui egale a la duree de la tache
		// dans ce cas on doit le prendre : on change son etat , on doit ausii le supprimer de la liste des creneaux
		// libres de la journee et le rejouter a la liste des creneaux occupes
		if(dureeCreneau==dureeTache){
			System.out.println("meme duree avec le tache ");
			this.setTache(tache);
			this.rendreOccupe();
			//tu as oublie de modifier la date de planification de la tache!!!!!
			tache.setDatePlanification(this.getDate());
			// on doit chercher la journee de ce creneau pour pouvoir modifier les listes des creneaux
			journeeCreneau.supprimerCreneauLibre(this);
			journeeCreneau.ajouterCreneauOccupee(this);
			ajoutAvecSucces=true;
		}else{
			System.out.println("### duree avec le tache ");

			// le deuxieme cas est le fait que la duree de la atche est <> a celle du creneau
			// dans ce cas on a deux cas possibles
			if(dureeCreneau>dureeTache){
				System.out.println("dureeCreneau>dureeTache => decomposition possible");

				// dans ce cas on doit calculer la duree restante pour verifier qu'elle >= la duree minimale
				// la duree restante est la difference entre l'heure fin de la tache et l'heure fin du creneau
				// c'est pourquoi on doit trouver l'heure fin du creneau qui est :l'heure debut de la tache+la duree
				LocalTime heureFinTache=this.ajouterDuree(this.getheureDebut(),tache.getDuree());
				System.out.print("Cette tache se termine a "+heureFinTache);
				int dureeRestante=this.calculerDuree2(this.getheureFin(),heureFinTache);
				System.out.print("La duree restante est "+dureeRestante);

				if(dureeRestante>=user.getDureeMin()){
					System.out.println("dureeCreneau>dureeTache => la decmpositon est possible 100%");

					// la decmpositon est possible
					// on obtient dans ce cas la deux creneaux : un creneau occupe et un autre libre
					tache.setDatePlanification(this.getDate());
					Creneau creneauOccupe=new Creneau(this.getDate(),this.getheureDebut() , heureFinTache,tache,EtatCreneau.OCCUPE);

					Creneau creneauLibre=new Creneau(this.getDate(),heureFinTache ,this.getheureFin(),EtatCreneau.LIBRE);
					// maintenant on doit ajouter ces creneaux resultants a la listes des creneaux : planifies ou libres dans la journee correspondante
					journeeCreneau.supprimerCreneauLibre(this);
			    	System.out.println("le nombre des creneaux apres la supprssion de l'ancien creneau "+journeeCreneau.getSesCreneaux().size());
					journeeCreneau.ajouterCreneauOccupee(creneauOccupe);
					System.out.println("le nombre des creneaux apres l'ajout du Creneau occuppe "+journeeCreneau.getCreneauxTachesPlanifiees().size());
					journeeCreneau.ajouterCreneauLibre(creneauLibre,user);
					System.out.println("le nombre des creneaux apres l'ajout du nouveau creneau libre  "+journeeCreneau.getSesCreneaux().size());

					ajoutAvecSucces=true;
					// on veut verifier si les deux creneaux ont ete ajoute correctement
								}else{
					System.out.println("dureeCreneau>dureeTache => la decompositon est impossible 100%");

					//dans ce cas on doit allouer tout le creneau pour cette tache
					this.setTache(tache);
					this.rendreOccupe();
					// on doit chercher la journee de ce creneau pour pouvoir modifier les listes des creneaux
					tache.setDatePlanification(this.date);
					journeeCreneau.supprimerCreneauLibre(this);
					journeeCreneau.ajouterCreneauOccupee(this);
					ajoutAvecSucces=true;
				}
			}
			if(dureeCreneau<dureeTache){
			System.out.println("Vous ne pouvez pas allouer ce creneau pour cette tache : sa duree est inferiuere de la duree de cette tache");
			ajoutAvecSucces=false;
		}
		}
		
		return ajoutAvecSucces;
	}

	public boolean decomposerCreneau(Utilisateur user, Tache tache,Periode periodeDuCreneau){
		boolean ajoutAvecSucces=false;
		// on doit faire une recheche pour trouver le creneau convenable
		int dureeCreneau=this.calculerDuree();
		int dureeTache=tache.getDuree();
		Journee journeeCreneau=user.getCalendrier().trouvJournee(this.getDate());
		// le premier cas le creneau a une duree qui egale a la duree de la tache
		// dans ce cas on doit le prendre : on change son etat , on doit ausii le supprimer de la liste des creneaux
		// libres de la journee et le rejouter a la liste des creneaux occupes
		if(dureeCreneau==dureeTache){
			this.setTache(tache);
			this.rendreOccupe();
			//tu as oublie de modifier la date de planification de la tache!!!!!
			tache.setDatePlanification(this.getDate());
			// on doit chercher la journee de ce creneau pour pouvoir modifier les listes des creneaux
			journeeCreneau.supprimerCreneauLibre(this);
			journeeCreneau.ajouterCreneauOccupee(this);
			//les meme modification sur la periode
			Journee journeeCorrespondantePeriode=periodeDuCreneau.donnerJournee(this.getDate());
			journeeCorrespondantePeriode.supprimerCreneauLibre(this);
			journeeCorrespondantePeriode.ajouterCreneauOccupee(this);
			ajoutAvecSucces=true;
		}else{

			// le deuxieme cas est le fait que la duree de la atche est <> a celle du creneau
			// dans ce cas on a deux cas possibles
			if(dureeCreneau>dureeTache){

				// dans ce cas on doit calculer la duree restante pour verifier qu'elle >= la duree minimale
				// la duree restante est la difference entre l'heure fin de la tache et l'heure fin du creneau
				// c'est pourquoi on doit trouver l'heure fin du creneau qui est :l'heure debut de la tache+la duree

				LocalTime heureFinTache=this.ajouterDuree(this.getheureDebut(),tache.getDuree());
				System.out.print("Cette tache se termine a "+heureFinTache);
				int dureeRestante=this.calculerDuree2(this.getheureFin(),heureFinTache);
				System.out.print("La duree restante est "+dureeRestante);

				if(dureeRestante>=user.getDureeMin()){

					// la decmpositon est possible
					// on obtient dans ce cas la deux creneaux : un creneau occupe et un autre libre
					tache.setDatePlanification(this.getDate());
					Creneau creneauOccupe=new Creneau(this.getDate(),this.getheureDebut() , heureFinTache,tache,EtatCreneau.OCCUPE);

					Creneau creneauLibre=new Creneau(this.getDate(),heureFinTache ,this.getheureFin(),EtatCreneau.LIBRE);
					// maintenant on doit ajouter ces creneaux resultants a la listes des creneaux : planifies ou libres dans la journee correspondante
					journeeCreneau.supprimerCreneauLibre(this);
					System.out.println("le nombre des creneaux apres la supprssion de l'ancien creneau "+journeeCreneau.getSesCreneaux().size());
					journeeCreneau.ajouterCreneauOccupee(creneauOccupe);
					System.out.println("le nombre des creneaux apres l'ajout du Creneau occuppe "+journeeCreneau.getCreneauxTachesPlanifiees().size());
					journeeCreneau.ajouterCreneauLibre(creneauLibre,user);
					System.out.println("le nombre des creneaux apres l'ajout du nouveau creneau libre  "+journeeCreneau.getSesCreneaux().size());

					//modification de la periode
					Journee journeeCorrespondantePeriode=periodeDuCreneau.donnerJournee(this.getDate());
					journeeCorrespondantePeriode.supprimerCreneauLibre(this);
					journeeCorrespondantePeriode.ajouterCreneauOccupee(creneauOccupe);
					journeeCorrespondantePeriode.ajouterCreneauLibre(creneauLibre,user);

					ajoutAvecSucces=true;
					// on veut verifier si les deux creneaux ont ete ajoute correctement
				}else{
					//dans ce cas on doit allouer tout le creneau pour cette tache
					this.setTache(tache);
					this.rendreOccupe();
					// on doit chercher la journee de ce creneau pour pouvoir modifier les listes des creneaux
					tache.setDatePlanification(this.date);
					journeeCreneau.supprimerCreneauLibre(this);
					journeeCreneau.ajouterCreneauOccupee(this);
					//les meme modification sur la periode
					Journee journeeCorrespondantePeriode=periodeDuCreneau.donnerJournee(this.getDate());
					journeeCorrespondantePeriode.supprimerCreneauLibre(this);
					journeeCorrespondantePeriode.ajouterCreneauOccupee(this);
					ajoutAvecSucces=true;
				}
			}
			if(dureeCreneau<dureeTache){
				System.out.println("Vous ne pouvez pas allouer ce creneau pour cette tache : sa duree est inferiuere de la duree de cette tache");
				ajoutAvecSucces=false;
			}
		}
		return ajoutAvecSucces;
	}
	public boolean ajouterTache (Periode periode,Tache tache,Utilisateur user) {
		// on doit calculer la durée de ce creneau
		// on doit verifier qu'elle est inferieure ou egale la duree minimale definie par l'utilisateur
		// Si la durée de la tache est inferieure a la duree du creneau on doit verifier si la durée restante est inferieure a la durée minimale definie par l'utilisateur

		// on doit verifier que le creneau est libre
		if(this.etat.equals(EtatCreneau.LIBRE) ) {
			System.out.println("le creneua est libre");
			return this.decomposerCreneau(user, tache,periode);
		}else{
			System.out.println("le creneau n'est pas libre");
			return false;
		}
	}
	public boolean ajouterTache (Tache tache,Utilisateur user) {
		// on doit calculer la durée de ce creneau
		// on doit verifier qu'elle est inferieure ou egale la duree minimale definie par l'utilisateur
		// Si la durée de la tache est inferieure a la duree du creneau on doit verifier si la durée restante est inferieure a la durée minimale definie par l'utilisateur

		// on doit verifier que le creneau est libre
		if(this.etat.equals(EtatCreneau.LIBRE) ) {
			System.out.println("le creneua est libre");
			return this.decomposerCreneau(user, tache);
		}else{
			System.out.println("le crenzua n'est pas libre");
			return false;
		}
	}
	//Cette methode est utlisée pour bloquer le creneau
	public void bloquer() {
		this.setEtat(EtatCreneau.BLOQUE);
	}
	public void liberer() {
		this.setEtat(EtatCreneau.LIBRE);
	}
	public void rendreOccupe() {
		this.setEtat(EtatCreneau.OCCUPE);
	}
	public boolean equals(Object o){
		Creneau r=(Creneau) o;
		if(r.getDate()==this.getDate() && r.getheureDebut()==this.getheureDebut() && r.getheureFin()==this.getheureFin()) {
			return true;
		}else {
			return false;
		}
	}

	public  int compareTo(Creneau creneau){
		int dateComparee=this.date.compareTo(creneau.getDate());
		if(dateComparee !=0) {
			return dateComparee;
		}
		int tempsDebut=this.heureDebut.compareTo(creneau.getheureDebut());
		if(tempsDebut !=0) {
			return tempsDebut;
		}
		int tempsFin = this.heureFin.compareTo(creneau.heureFin);
		if (tempsFin != 0) {
			return tempsFin;
		}

		return 0;
	}
	public boolean intersecteAvec(Creneau creneauAutre){
		boolean intersect=false;
		if(this.date.equals(creneauAutre.getDate())){
			if((this.heureDebut.isAfter(creneauAutre.getheureDebut())&& this.heureDebut.isBefore(creneauAutre.getheureFin()))||
					(this.heureFin.isAfter(creneauAutre.getheureDebut())) && this.heureFin.isBefore(creneauAutre.getheureFin())){
				return true;
			}
		}
		return intersect;
	}
	
}
