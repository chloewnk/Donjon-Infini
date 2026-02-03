import java.util.Random;

/**
 * Représente un monstre sur le plateau du jeu Donjon Infini.
 * Un monstre possède un nombre de points de vie (pv) aléatoire entre 10 et 35.
 * Il peut subir des dégâts et, à sa mort, il peut lâcher de l'or équivalent à sa vie.
 */
public class Monstre extends Case {
    private int pv;

    /**
     * Construit un monstre avec un nombre de points de vie aléatoire entre 10 et 35.
     */
    public Monstre(){
        if (genererMystere()){
            this.mystere = true;
            this.pv = (new Random().nextInt(26)+10)*2;
        }else{
            this.pv = new Random().nextInt(26)+10;
        }
        this.valeur = pv;
    }

    /**
     * Retourne le label du monstre pour l'affichage.
     * @return la chaîne "Monstre"
     */
    @Override
    public String getLabel(){
        return "Monstre";
    }

    /**
     * Retourne le label des points de vie du monstre pour l'affichage.
     * @return une chaîne du type "X PV" où X est le nombre de points de vie
     */
    @Override
    public String getLabelPv() {
        return pv + " PV";
    }

    /**
     * Retourne le nombre de points de vie du monstre.
     * @return les points de vie du monstre
     */
    @Override  
    public int getValeur() {
        return this.pv;
    }

    /**
     * Retourne la quantité d'or lâchée par le monstre à sa mort.
     * Correspond à la valeur initiale du monstre.
     * @return la quantité d'or lâchée
     */
    public int dropOr(){
        return this.valeur;
    }

    /**
     * Inflige des dégâts au monstre.
     * Réduit ses points de vie du montant indiqué.
     * Si les points de vie deviennent négatifs, ils sont ramenés à zéro.
     * @param valeur le nombre de points de dégâts à infliger
     */
    public void subiDegats(int valeur) {
        this.pv -= valeur;
        if (this.pv < 0 ) {
            this.pv = 0;
        }
    }
}