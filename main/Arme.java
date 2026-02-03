import java.util.Random;

/**
 * Représente une arme bonus sur le plateau du jeu Donjon Infini.
 * Une arme possède un nombre de points de vie (puissance) aléatoire entre 10 et 30.
 * Elle peut être ramassée par le héros et perdre de la puissance lors des combats.
 */
public class Arme extends CaseBonus {

    /** Points de vie (puissance) de l'arme. */
    private int pv;

    /**
     * Construit une arme avec une puissance aléatoire entre 10 et 30.
     */
    public Arme() {
        if (genererMystere()){
            this.mystere=true;
            this.pv = (new Random().nextInt(21)+10)*2;
        }else{
            pv = new Random().nextInt(21) + 10;
        }  
    }

    /**
     * Retourne la puissance actuelle de l'arme.
     * @return la valeur de l'arme (points de vie restants)
     */
    @Override
    public int getValeur() {
        return pv;
    }

    /**
     * Retourne le label de l'arme pour l'affichage.
     * @return la chaîne "Arme"
     */
    @Override
    public String getLabel() {
        return "Arme";
    }

    /**
     * Retourne le label de la puissance de l'arme pour l'affichage.
     * @return une chaîne du type "+X PV" où X est la puissance de l'arme
     */
    @Override
    public String getLabelPv() {
        return "+" + pv + " PV";
    }

    /**
     * Fait perdre de la puissance à l'arme.
     * Si la puissance devient négative, elle est ramenée à zéro.
     * @param valeur le nombre de points à retirer à la puissance de l'arme
     */
    public void perdPuissancePV(int valeur) {
        this.pv -= valeur;
        if (this.pv < 0) {
            this.pv = 0;
        }
    }
}