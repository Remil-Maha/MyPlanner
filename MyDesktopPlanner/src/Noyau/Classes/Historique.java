package Noyau.Classes;
import java.util.*;
import java.util.TreeSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;

public class Historique {
	private TreeSet<Planning>  historiqueDesPlannings;

	public TreeSet<Planning> getHistoriqueDesPlannings() {
		return historiqueDesPlannings;
	}
	public void setHistoriqueDesPlannings(TreeSet<Planning> historiqueDesPlannings) {
		this.historiqueDesPlannings = historiqueDesPlannings;
	}
	// Cette methode est utilisée pour chercher le planning qui a une date donnée
	// en parcourant notre TreeSet jusqu'a trouver le planing convenable
	// Pour le parcours je vais utiliser un iterateur 
	public Planning rechercherPlaning(LocalDate date) {
		Iterator<Planning> iterator = this.historiqueDesPlannings.iterator();
		Planning planningRecherche=null;
		while (iterator.hasNext() && planningRecherche==null) {
		    Planning planning = iterator.next();
		    if (planning.getPeriode().getDateDebut().equals(date)) {
		        // Faites quelque chose avec le planning trouvé, par exemple:
		       planningRecherche=planning;
		       // Vous pouvez arrêter la boucle car vous avez trouvé le planning recherché
		    }
		    			   
		}	
		return planningRecherche;
	}
	// Cette methode est utlisée pour consulter les badges gagnés dans planning donnée  
	public void consulterBadgs(Planning p) {
		 p.consulterBadge();
	}
	// Cette methode est utilisée pour calculer le nombre des taches que l'utlisateur a compléter dans le planning p
	public int calculerNombreTachesCompletees(Planning p) {
		return p.calculerNombreTacheCompletees();
	}
	//Cette methode parcoure tout l'historique et calcule le nombre total des taches complétées dasn  tous les plannings  
	public int calculerNombreTachesCompletees() {
		Iterator<Planning> iterator = historiqueDesPlannings.iterator();
	    int nbTotal=0;
		while (iterator.hasNext()) {
			 Planning planning = iterator.next();
			 nbTotal=planning.calculerNombreTacheCompletees();
		}
		return nbTotal;

	}
	// Cette methode est utlisée pour calculer le nombre des projets complétés dans un planning donné
	 public int calculerNombreProjetCompletes(Planning p) {
		 return p.calculerNombreProjetCompletees();
	 }
		//Cette methode parcoure tout l'historique et calcule le nombre total des projets complétés dasn  tous les plannings  

	 public int calculerNombreProjetCompletees() {
			Iterator<Planning> iterator =this.historiqueDesPlannings.iterator();
		    int nbTotal=0;
			while (iterator.hasNext()) {
				 Planning planning = iterator.next();
				 nbTotal=planning.calculerNombreProjetCompletees();
			}
			return nbTotal;
	}
	public void ajouterPlanning(Planning planning){
		this.historiqueDesPlannings.add(planning);
	}
	 
	 
}
