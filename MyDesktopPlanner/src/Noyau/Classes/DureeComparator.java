package Noyau.Classes;

import java.util.Comparator;

public class DureeComparator implements Comparator<Creneau> {
    @Override
    public int compare(Creneau creneau1, Creneau creneau2) {
        int duree1 =creneau1.calculerDuree();
        int duree2 = creneau2.calculerDuree();

        return -Integer.compare(duree1, duree2);
    }
}