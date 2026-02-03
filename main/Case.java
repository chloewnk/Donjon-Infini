import java.io.Serializable;
import java.util.*;

/**
 * Classe abstraite représentant une case du plateau dans le jeu Donjon Infini.
 * Une case peut être de différents types (or, potion, arme, monstre) et possède une valeur.
 * Les sous-classes doivent définir le label de la case.
 */
public abstract class Case implements Serializable {
    /** Valeur associée à la case (ex : points, puissance, etc.). */
    protected int valeur;


    protected boolean mystere;

    /**
     * Retourne le label de la case pour l'affichage.
     * Doit être implémenté par chaque sous-classe.
     * @return le nom de la case
     */
    public abstract String getLabel();

    /**
     * Retourne le label des points de vie ou de la valeur de la case pour l'affichage.
     * Par défaut, retourne le label de la case.
     * @return le label des points de vie ou de la valeur
     */
    public String getLabelPv(){
        return getLabel();
    }

    /**
     * Retourne la valeur de la case.
     * @return la valeur de la case
     */
    public int getValeur(){
        return valeur;
    }

    public boolean getMystere(){
        return mystere;
    }

    /*public void setMystere(boolean b){
        this.mystere = b;
    }*/

    protected static boolean genererMystere(){
        return (new Random().nextFloat()) <= 0.05;
    }

    /**
     * Génère une nouvelle case aléatoire parmi Or, Potion, Arme ou Monstre.
     * Les probabilités sont : Or (20%), Potion (12,5%), Arme (17,5%), Monstre (50%).
     * @return une nouvelle instance de Case aléatoire
     */
    public static Case newRandomCase(){ 
        double r = new Random().nextDouble(); 
        if (r<0.20){
            return new Or(true);
        }
        if (r<0.325){
            return new Potion();
        }
        if (r<0.50){
            return new Arme();
        }
        return new Monstre();
    }
}