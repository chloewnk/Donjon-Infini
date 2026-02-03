import java.util.Random;

/**
 * Représente une potion bonus sur le plateau du jeu Donjon Infini.
 * Une potion permet de restaurer des points de vie au héros.
 * Sa valeur est aléatoire entre 20 et 50 points de vie.
 */
public class Potion extends CaseBonus{
    /** 
     * Construit une potion avec une valeur aléatoire entre 20 et 50 points de vie.
     */
    public Potion(){
        if (genererMystere()){
            this.mystere=true;
            this.valeur = (new Random().nextInt(31)+20)*2;
        }else{
            this.valeur = new Random().nextInt(31)+20;
        }
    }

    /**
     * Retourne le label de la potion pour l'affichage.
     * @return la chaîne "Potion"
     */
    @Override
    public String getLabel(){
        return "Potion";
    }

    /**
     * Retourne le label de la valeur de la potion pour l'affichage.
     * @return une chaîne du type "+X PV" où X est la valeur de la potion
     */
    @Override
    public String getLabelPv() {
        return "+" + valeur + " PV";
    }
}