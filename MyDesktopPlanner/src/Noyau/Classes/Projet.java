package Noyau.Classes;

import java.util.*;

public class Projet {
	private String nom;
	private String description;
	private ArrayList<Tache> taches;

	public Projet(String nom,String description, ArrayList<Tache> taches) {
		this.nom=nom;
		this.description=description;
		this.taches=taches;
	}
	public void ajouterTache (Tache tache) {
		 taches.add(tache);
	}
	// Cette methode evalue le projet selon l'etat de realisation de toutes ses taches
	public EtatRealisation evaluerEtatProjet() {
	    int nbTachesTotales = taches.size();
	    int nbTachesCompletees = 0;
	    int nbTachesEnCours = 0;
	    int nbTachesAnnulees = 0;
	    int nbTachesEnRetard = 0;

	    for (Tache tache : taches) {
	        switch (tache.getEtat()) {
	            case COMPLETED:
	                nbTachesCompletees++;
	                break;
	            case INPROGRESS:
	                nbTachesEnCours++;
	                break;
	            case CANCELED:
	                nbTachesAnnulees++;
	                break;
	            case DELAYED:
	                nbTachesEnRetard++;
	                break;
	            default:
	                // Do nothing
	                break;
	        }
	    }

	    // Évaluer l'état de réalisation du projet en se basant sur les tâches
	    if (nbTachesTotales == nbTachesCompletees) {
	        return EtatRealisation.COMPLETED;
	    } else if (nbTachesEnCours > 0 || nbTachesAnnulees > 0 || nbTachesEnRetard > 0) {
	        return EtatRealisation.INPROGRESS;
	    } else {
	        return EtatRealisation.NOTREALISED;
	    }
	}

	public void supprimerTache(Tache tache) {
		 taches.remove(tache);
	}

	public void afficherProjet() {
		System.out.println("Le nom de ce projet "+this.getNom());
		System.out.println("La description "+this.getDescription());
		this.afficherTaches();
	}
	public void afficherTaches() {
		for (int i = 0; i < taches.size(); i++) {
		    Tache tache = taches.get(i);
		    tache.afficher();
		    // Traitement sur la tache
		}
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Tache> getTaches() {
		return taches;
	}
	public void setTaches(ArrayList<Tache> taches) {
		this.taches = taches;
	}
	// on doit redefinir la methode equals and compareTO
    

}
