package Noyau.Classes;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.TreeSet;

public class Periode implements Comparable<Periode>{
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private TreeSet<Journee> journees_Creneaux;

    //pour creer une periode l'utilisateur donne uniquement les dates
    //ensuite il doit donner des crenaux libre pour les jounees de cette periode
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    public TreeSet<Journee> getJournees_Creneaux() {
        return journees_Creneaux;
    }
    public void setJournees_Creneaux(TreeSet<Journee> journees_Creneaux) {
        this.journees_Creneaux = journees_Creneaux;
    }

    public Periode(LocalDate dateDebut, LocalDate dateFin){
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
        this.journees_Creneaux=creerJournees();
    }

    @Override
    public int compareTo(Periode b) {
        int comp=0;
        if(this.equals(b)){
            comp=0;
        }else{
            //periode this est avant(-1) dans le temps sur la periode B ssi
            //this={ 20-05-2023-> 30-05-2023}
            //B={ 30-05-2023-> 25-06-2023}
            //this.dateFin.isBefor(b.getDateDebut()) || this.dateFin.equals(b.getDateDebut())
            if(this.dateFin.isBefore(b.getDateDebut()) || this.dateFin.equals(b.getDateDebut())){
                comp= -1;
            }else{
                //periode this est apres(+1) dans le temps sur la periode B ssi
                //this={ 20-05-2023-> 30-05-2023}
                //B={ 03-05-2023-> 20-05-2023}
                //this.dateDebut.isAfter(b.getDateFin()) || this.dateDebut.equals(b.getDateFin())
                if(this.dateDebut.isAfter(b.getDateFin()) || this.dateDebut.equals(b.getDateFin())){
                    comp= +1;
                }
            }
        }
        return comp;
    }
    //a utiliser lorsque on donne en entree des periodes
    //deux periode ne doivent pas avoir d'intersection entre elles
    public boolean intersectePeriode(Periode periode){
        //tester if this intersect with periode
        boolean intersecte=false;
        if((this.dateFin.isAfter(periode.getDateDebut())&&this.dateFin.isBefore(periode.getDateFin()))||
                ( this.dateDebut.isAfter(periode.getDateDebut())&&this.dateDebut.isBefore(periode.getDateDebut()))){
            intersecte=true;
        }
        return intersecte;
    }
    //deux periode sont egales ssi elles ont la meme dateDebut et la meme date fin
    public boolean equals(Object o){
        Periode periode=(Periode)o;
        if(this.dateDebut.equals(periode.getDateDebut())&&this.dateFin.equals(periode.getDateFin())){
            return true;
        }else{
            return false;
        }
    }
    public TreeSet<Journee> creerJournees(){

        TreeSet<Journee> journeesPeriode = new TreeSet<Journee>();
        LocalDate dateCourante =this.dateDebut;
        while (!dateCourante.isAfter(this.dateFin)) {
            Journee jour=new Journee(dateCourante);
            journeesPeriode.add(jour);
            dateCourante = dateCourante.plusDays(1);
        }
        return journeesPeriode;
    }
    public void affecterCreneau(LocalDate date, Creneau creneau,Utilisateur user){
        //je recoit la date de la journee
        //voir si elle est dans la periode
        //en suite recuperer sa Journee
        //affcter a celle-ci son creneau
        Journee journee=donnerJournee(date);
        if(journee!=null){
            this.journees_Creneaux.remove(journee);
            journee.ajouterCreneauLibre(creneau,user);
            this.journees_Creneaux.add(journee);
        }else{
            //nullException
        }
    }
    //recherche de crneau pour une decompostion
    public  TreeSet<Creneau> trouverCreneaux(int duree,LocalDate dateLimite){
        TreeSet<Creneau> trouves=new TreeSet<>();
        LocalDate dateCourante=this.dateDebut;
        int sommeDuree=0;
        boolean arret=false;
        while (!dateCourante.isAfter(this.dateFin) && arret==false){
            if(!dateCourante.isAfter(dateLimite)){
                //on peut chercher des crneaux dans cette journee
                Journee journee=this.donnerJournee(dateCourante);
                for(Creneau c:journee.getSesCreneaux()){
                    sommeDuree=sommeDuree+c.calculerDuree();
                    trouves.add(c);
                }
                dateCourante=dateCourante.plusDays(1);
            }else {
                //cette date depasse la date limite on arrete le parcoure
                arret=true;
            }
        }
        if(sommeDuree<duree){
            //l'ensemble des crneaux collectes dans cette periode suffiront pour planifier la tache
            trouves.clear();
        }
        return trouves;
    }
    public void affecterCreneau(LocalDate date, TreeSet<Creneau> creneaux){
        //je recoit la date de la journee
        //voir si elle est dans la periode
        //en suite recuperer sa Journee
        //affcter a celle-ci son creneau
        Journee journee=donnerJournee(date);
        if(journee!=null){
            this.journees_Creneaux.remove(journee);
            journee.ajouterCreneauLibre(creneaux);
            this.journees_Creneaux.add(journee);
        }else{
            //nullException
        }
    }
    public boolean isInPeriode(LocalDate date){
        if (!date.isBefore(dateDebut) && !date.isAfter(dateFin)){
            return true;
        }else{
            if(date.equals(dateDebut)|| date.equals(dateFin)){
                return true;
            }else{
                return  false;
            }
        }
    }
    public Journee donnerJournee(LocalDate date){
        Journee jour=null;
        if(this.isInPeriode(date)==true){
            Iterator<Journee> it=this.journees_Creneaux.iterator();
            boolean trouve=false;
            while(it.hasNext() && trouve==false){
                Journee j=it.next();
                if(j.getDate().equals(date)){
                   jour= j;
                   trouve=true;
                }
            }
        }
        return jour;
    }

    //ctte methode sera utilisee lorsqu'on plannifie une tache dans une periode
    public void affecterTache(Tache tache, Creneau creneau){//dans cette periode on met une tache dans un creneau
        if(this.isInPeriode(creneau.getDate())){
            //la date de ce crenau est dans la periode
            //possible qu'il existe

            Journee j=this.donnerJournee(creneau.getDate());//j est la journee de ce creneau
            j.affecterTacheCreneau(tache, creneau);
        }
    }

}
