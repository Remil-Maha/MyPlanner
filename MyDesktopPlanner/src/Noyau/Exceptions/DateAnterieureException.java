package Noyau.Exceptions;

import java.time.LocalDate;

public class DateAnterieureException  extends Exception{

    public DateAnterieureException(){
        super("Les dates sont erronées!");
    }
    public static void doSomthing(LocalDate date1,LocalDate date2) throws DateAnterieureException{
        if(date1.isBefore(date2)){
            throw new DateAnterieureException();
        }
    }
}
