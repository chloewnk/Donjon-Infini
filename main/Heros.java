import java.io.Serializable;

/**
 * Représente le héros du jeu Donjon Infini.
 * Le héros possède des points de vie (pv) et peut porter une arme.
 * Il peut rencontrer différents types de cases (monstre, potion, arme, or) et réagit en conséquence.
 */
public class Heros extends Case implements Serializable { 
    private int pv;
    private Arme arme;

    /**
     * Construit un héros avec 100 points de vie et sans arme.
     */
    public Heros (){
        this.pv = 100; /* Par defaut il a 100pv et peut en accumuler 400 au total */
        this.arme = null; /* Par defaut il n'a pas d'arme */
    }

    /**
     * Retourne le label du héros pour l'affichage.
     * @return la chaîne "Heros"
     */
    @Override 
    public String getLabel() {
        return "Heros"; 
    }

    /**
     * Retourne le label des points de vie du héros pour l'affichage.
     * @return une chaîne du type "X PV" où X est le nombre de points de vie
     */
    @Override
    public String getLabelPv() {
        return pv + " PV"; 
    }

    /**
     * Retourne le nombre de points de vie du héros.
     * @return les points de vie du héros
     */
    @Override    
    public int getValeur() {
        return pv;
    }

    /**
     * Retourne l'arme actuellement portée par le héros.
     * @return l'arme du héros, ou null s'il n'en a pas
     */
    public Arme getArme() {
        return arme;
    }

    /**
     * Indique si le héros est mort (points de vie &lt;= 0).
     * @return true si le héros est mort, false sinon
     */
    public boolean estMort() {
        return pv <= 0;
    }

    /**
     * Gère la rencontre du héros avec une case du plateau.
     * Applique les effets selon le type de case (monstre, potion, arme, or).
     *
     * @param c La case rencontrée
     * @return true si le héros peut se déplacer sur la case, false sinon
     */
    public boolean rencontrer(Case c) {
        if (c instanceof Monstre) {
            Monstre m = (Monstre) c;

            // On verifie si il n'a pas une arme
            if(this.arme == null){
                // Donc il  subit les dégâts
                int degats = m.getValeur();  // recup pv du monstre
                m.subiDegats(this.pv); // le subit c pour le monstre 
                this.pv -= degats;
                
                //System.out.println("haha pas d'arme ! Le héros prend " + m.getValeur() + " dégâts.\n");

                // Vue que on le verifie aussi dans le cotroleur je sais pas trop ça fais du code en plus
                if (this.estMort()) {
                    //System.out.println("Le héros est mort !");
                    return true; 
                }
                return false; // Le héros se déplace pas si il n'est pas mort

            }else {
                // Le héros a une arme donc il attaque et larme perd aussi de la valeur
                int vieMonstreAvant = m.getValeur();
                m.subiDegats(this.arme.getValeur());
                this.arme.perdPuissancePV(vieMonstreAvant); 

                if (this.arme.getValeur() <= 0) {
                    //System.out.println("L'arme est cassée !\n");
                    this.arme = null;
                }

                if (m.getValeur() <= 0 ) {
                    //System.out.println("Monstre vaincu !");
                    return false; // Le héros peut pas avancer car lor est là 
                }else {
                    //System.out.println("Le monstre est encore en vie !\n");
                    return false; // Le héros reste sur place
                }
            }

           
        } else if (c instanceof Potion) {
            this.pv += c.getValeur();
            if (this.pv > 400) this.pv = 400;
            //System.out.println("PV du héros : " + this.pv);
            return true;
        
        } else if (c instanceof Arme) {
            Arme newArme = (Arme) c;
            if(this.arme == null){
                this.arme = newArme;
//                System.out.println("Nouvelle arme acquise : +" + arme.getValeur());
            }else if (newArme.getValeur() > this.arme.getValeur()){
                this.arme = newArme;
//                System.out.println("Arme change car plus puissante");
            }else{
//                System.out.println("Arme ignorer car faible");
            }
            return true;
        
        } else if (c instanceof Or) {
            //System.out.println(" Pièces trouvées.");
            return true;
        }  
        return true;
    }    


        /* s'il rencontre un monstre alors le boolean s'active à vrai 
         */


        /*
         * Logique que je propose (plus simple à gérer je pense de mon cote):
         * Si on veut aller dans une case (controlleur):
         *          Gestion de case avec monstre:
         *              On retourne True:
         *                  - Si le monstre est mort (battue, possibilité de se deplacer)
         *              On retourne False
         *                  - Si le monstre est toujour vivant (on se daplace pas)
         *         Gestion de case autre que monstre:
         *              On retorne  TRUE si:
         *                  - si la case destination est OR (gestion niveau du controlleur), POTION, ARME
         *                  - Aplique la gestion pour potion et sur arme
         *          
         * 
         */
}
