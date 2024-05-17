package Noyau.Exceptions;
import java.util.*;
import Noyau.Classes.*;

public class NomUtilisateurExisteException extends Exception {
    public NomUtilisateurExisteException(){
        super("Nom d'utilisateur existant, choisissez un autre nom.");
    }
    public static void doSomthing(String pseudo,HashMap<String,Utilisateur> utilisateurs) throws NomUtilisateurExisteException{
       
        if(utilisateurs.keySet().contains(pseudo)){
            throw new NomUtilisateurExisteException();
        }
    }
}
