package Noyau.Classes;

import java.util.*;


    public class Categorie {
   
        private HashSet<Tache> categorieTache=new HashSet<Tache>();
        private String couleur;
        private String  categorie;
        public HashSet<Tache> getCategorieTache() {
    return categorieTache;
}
        public void setCategorieTache(HashSet<Tache> categorieTache) {
    this.categorieTache = categorieTache;
}
         public String getCouleur() {
    return couleur;
}
         public void setCouleur(String couleur) {
    this.couleur = couleur;
}
          public String getCategorie() {
    return categorie;
}
          public void setCategorie(String categorie) {
    this.categorie = categorie;
}

          public Categorie(String couleur, String categorie) {
             this.couleur = couleur;
             this.categorie = categorie;
          }

    //-----------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------
    public  void ajouterTache(Tache tache){
            this.categorieTache.add(tache);
    }

}
