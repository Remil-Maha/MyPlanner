package Noyau.Exceptions;

import Noyau.Classes.Creneau;

import java.time.LocalDate;

public class IntersectionCreneauxException extends Exception{
    public IntersectionCreneauxException(){
        super("Intersection de creneaux");
    }
}
