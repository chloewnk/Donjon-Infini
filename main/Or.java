/**
 * Représente une case bonus d'or sur le plateau du jeu Donjon Infini.
 * L'or rapporte des points au héros lorsqu'il est ramassé.
 * Sa valeur par défaut est 25, mais peut être modifiée (par exemple, lors de la mort d'un monstre).
 */
public class Or extends CaseBonus{
    /**
     * Construit une case Or avec une valeur par défaut de 25.
     */
    public Or(boolean dropMonstre){     //ajout booleen lors de la mort d'un monstre pour qu'une case mystère n'apparaisse pas

        if (genererMystere() && dropMonstre){
            this.mystere = true;
            this.valeur = 50;
        }else{
            this.valeur = 25;
        }
    }

    /**
     * Définit la quantité d'or à déposer sur la case.
     * Généralement utilisée pour déposer l'or équivalent à la vie d'un monstre vaincu.
     * @param vieM la valeur d'or à déposer sur la case
     */
    public void setOr(int vieM){
        this.valeur = vieM;
    }

    /**
     * Retourne le label de la case pour l'affichage.
     * @return la chaîne "Or"
     */
    @Override
    public String getLabel(){
        return "Or";
    }
}