package Noyau.Classes;
import java.util.*;
import java.time.*;
public class Planning implements Comparable<Planning> {
	private Periode periode;
	private HashMap<Badge,Integer> badgePlanning=new HashMap<Badge, Integer>(){{
	        put(Badge.GOOD,0);
	        put(Badge.VERYGOOD,0);
	        put(Badge.EXCELENT,0);
	    }};
	private TreeSet<Projet>  mesProjet;//les projet(s) palanifiers
	private LocalDate journeePlusRentable;
	
	public Planning(Periode p,HashMap<Badge,Integer>  badgePlanning,TreeSet<Projet>  mesProjet,LocalDate journeePlusRentable) {
		this.periode=p;
		this.badgePlanning=badgePlanning;
		this.mesProjet=mesProjet;
		this.journeePlusRentable=journeePlusRentable;
	}
    //la comparaison entre deux palnings revient a faire la comparaison entre leurs periodes
	@Override
	public int compareTo(Planning o) {
		return this.periode.compareTo(o.getPeriode());
	}
	//deux palaning sont egaux s'ils ont le meme date debut et la meme date fin càd la meme periode
	//donc il suffit d'utiliser equals de periode
	public boolean equals(Object p){
		Planning planning=(Planning) p;
		return this.periode.equals(planning.getPeriode());
	}

	public Planning(Periode p){
		this.periode=p;
	}
	public Periode getPeriode() {
		return this.periode;
	}
	public void setPeriode(Periode p) {
		this.periode=p;
	}
	
	public HashMap<Badge,Integer> getbadgePlanning(){
		return this.badgePlanning;
	}
	public void setbadgePlanning(HashMap<Badge,Integer> badges){
		 this.badgePlanning=badges;
	}
	public TreeSet<Projet> getmesProjets(){
		return this.mesProjet;
	}
	public void setmesProjets(TreeSet<Projet>  mesProjets){
		 this.mesProjet=mesProjets;
	}
	public LocalDate getjourneePlusRentable(){
		return this.getjourneePlusRentable();
	}
	public void setjourneePlusRentable(LocalDate date){
		 this.journeePlusRentable=date;
	}

	//Cette methode est utlisée pour consulter tous les badges gagnés dans ce palnning
	public void consulterBadge() {	
		for(Map.Entry<Badge,Integer> entry: this.badgePlanning.entrySet()){
            System.out.println("Badge "+entry.getKey().toString()+"   Nombre: "+entry.getValue());
        }
	}
	// Pour trouver le nombre des taches completées dans ce palnning on doit utiliser le nombre 
	//des taches complétées (attribut) dans une journée et faire la somme pour toutes les journées d'une periode 
	public int calculerNombreTacheCompletees(){
		int nbTotal=0;
		 for (Journee journee : this.periode.getJournees_Creneaux()) {
	            nbTotal += journee.getNombreTachesAcomplies();
	        }	
		return nbTotal;
	}
	// Cette methode calcule le nombre des projets completes dans ce plnning
	public int calculerNombreProjetCompletees(){
		int nbTotal=0;
		Iterator<Projet> iterator = this.mesProjet.iterator();
		while (iterator.hasNext()) {
		    Projet projet = iterator.next();
		    if(projet.evaluerEtatProjet()==EtatRealisation.COMPLETED) {
		    	nbTotal+=1;
		    }
		    
		}
        return nbTotal;
	}
	// Cette methode calcule le nombre des taches qui restent c'est a dire le nombre des taches non complétées
	// c'est pourquoi on doit utliser l'attribut creneauxTachesPlanifiees de la periode de ce palnning
	public int calculerNombreTacheRestante() {
		int nbTotal=0;
		 for (Journee journee_creneau : this.periode.getJournees_Creneaux()) {
	          for(Creneau creneau :journee_creneau.getCreneauxTachesPlanifiees()) {
	        	  if(creneau.getTache().getEtat()!=EtatRealisation.COMPLETED) {
	        		  nbTotal+=1;
	        	  }
	          }
	        }
		 return nbTotal;
	}
	// Cette methode est utlisée pour retourner la liste des taches restantes
	public ArrayList<Tache> donnerTacheRestantes(){
		ArrayList <Tache>tacheRestantes = null;
		for(Journee journee :this.periode.getJournees_Creneaux()) {
			for(Creneau creneau :journee.getCreneauxTachesPlanifiees()) {
				 if(creneau.getTache().getEtat()!=EtatRealisation.COMPLETED) {
	        		tacheRestantes.add(creneau.getTache());
	        	  }
			}
		}
		return tacheRestantes;
	}
	// Cette methode donne la liste des taches completees
	public ArrayList<Tache> donnerTacheCompletees(){
		ArrayList <Tache>tacheCompletees = null;
		for(Journee journee :this.periode.getJournees_Creneaux()) {
			for(Creneau creneau :journee.getCreneauxTachesPlanifiees()) {
				 if(creneau.getTache().getEtat()==EtatRealisation.COMPLETED) {
	        		tacheCompletees.add(creneau.getTache());
	        	  }
			}
		}
		return tacheCompletees;
	}
	// Cette methode donne la journée la plus rentable dans tous le planning
	public RendementJournee donneerJourneePlusRentable() {
		TreeSet<Journee> jours=this.periode.getJournees_Creneaux();
        Iterator<Journee> it=jours.iterator();

        ArrayList<RendementJournee> list=new ArrayList<RendementJournee>();
        //pour chaque Planning on trouve le jour le plsu rentable ainsi que son rendement
        while (it.hasNext()){
           Journee j=it.next();
           double rendement=j.getNombreTachesAcomplies()/j.getCreneauxTachesPlanifiees().size();
            RendementJournee rend=new RendementJournee(j.getDate(),rendement);
            list.add(rend);
        }
        Collections.sort(list);
        return list.get(list.size()-1);
	}

}
