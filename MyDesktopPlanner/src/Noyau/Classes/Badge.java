package Noyau.Classes;

public enum Badge {
    GOOD,VERYGOOD,EXCELENT;
    public  boolean equals(Badge b){
        return (this.name().equals(b.name()));
    }
}
