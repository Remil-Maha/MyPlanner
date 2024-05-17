package Noyau.Classes;

import java.time.LocalDate;

public class RendementJournee implements  Comparable<RendementJournee>{
    private LocalDate date;
    private double  rendement=0;

    public RendementJournee() {
    }

    public RendementJournee(LocalDate date, double rendement) {
        this.date = date;
        this.rendement = rendement;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getRendement() {
        return rendement;
    }

    public void setRendement(double rendement) {
        this.rendement = rendement;
    }

    public boolean equals(Object o){
        RendementJournee r=(RendementJournee) o;
        return ((Double)(this.rendement)).equals(r.getRendement());
    }
    public  int compareTo(RendementJournee r){
        return  ((Double)(this.rendement)).compareTo(r.getRendement());
    }
}
