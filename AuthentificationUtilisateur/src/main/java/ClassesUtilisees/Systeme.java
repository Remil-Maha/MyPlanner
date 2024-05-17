package ClassesUtilisees;
import java.util.HashMap;

public  class Systeme {

    private HashMap<String,Utilisateur> users=new HashMap<>();

    public HashMap<String,Utilisateur> getUsers() {
        return users;
    }
    public void setUsers(HashMap<String,Utilisateur> users) {
        this.users = users;
    }
    public Systeme(HashMap<String,Utilisateur> users) {
        this.users = users;
    }
    public boolean authentifier(String nom){
        //on verifie s'il y a un user dont le nom est "nom"
        if(this.users.isEmpty()){
            return false;
        }else{
            if(this.users.keySet().contains(nom)){

                return true;
            }else{

                return false;
            }
        }

    }

    public  void ajouter(Utilisateur user){
        try {
            this.users.put(user.getNom(),user);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

    }
}
