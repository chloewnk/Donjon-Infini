import java.awt.event.*;
import javax.swing.*;

/**
 * Contrôleur du jeu Donjon Infini.
 * Gère les déplacements du héros, la gestion des points, la détection des collisions,
 * la mise à jour de la vue et la gestion des événements clavier.
 */
public class Controleur implements KeyListener {

    private int xHeros;
    private int yHeros;
    public int points;
    private final JFrame fenetre;
    private final Heros hero;
    private final Case[][] plateau;
    private final VuePlateau vue;
    
    

    /**
     * Crée un contrôleur pour le jeu.
     * Initialise la position du héros, le plateau, la vue et la fenêtre.
     *
     * @param h Le héros à contrôler
     * @param pla Le plateau de jeu (tableau de cases)
     * @param v La vue graphique du plateau
     * @param win La fenêtre principale du jeu
     */
    public Controleur(Heros h, Case[][] pla, VuePlateau v, JFrame win) {
        this.hero = h;
        this.plateau = pla;
        this.vue = v;
        // this.xHeros = 1;
        // this.yHeros = 1;    
        this.fenetre = win;
        trouverPositionHeros();
        this.points = 0;
    }

    /**
     * Recherche la position du héros sur le plateau et met à jour les coordonnées internes.
     * Évite la duplication du héros lors du chargement d'une partie.
     */
    private void trouverPositionHeros() { // pour eviter la duplication du heros dans le plateau
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == hero) {
                    this.yHeros = i;
                    this.xHeros = j;
                    return;
                }
            }
        }
    }

    /**
     * Gère la rencontre entre le héros et la case cible.
     * Met à jour le score, la vue, le plateau et la position du héros selon le type de case.
     * Si le héros meurt, affiche le score final et propose d'enregistrer le pseudo.
     *
     * @param xNew Nouvelle abscisse cible sur le plateau
     * @param yNew Nouvelle ordonnée cible sur le plateau
     */
    private void rencontre(int xNew, int yNew){
        // Verifions que les cordonne sont dans les limites (dans le plateau)
        if ((xNew >= 3) || (xNew < 0) || (yNew >= 3) || (yNew < 0)) {
            return;
        }

        Case destination = this.plateau[yNew][xNew];
        
        Boolean sedeplacer = this.hero.rencontrer(destination); // déplacement + stockage dans une variable de l'execution de rencontrer pour éviter d'avoir à vérifer que le héros est mort dans le if et dans le else

        if (this.hero.getValeur() <= 0) {
            System.out.println("Game over.");
            System.out.println("Score : " + points);
            fenetre.dispose();

            //Pour le classement, demander le pseudo
            JOptionPane optionPane = new JOptionPane("Votre pseudo :", JOptionPane.QUESTION_MESSAGE);
            String pseudo = (String) optionPane.showInputDialog(null, "Votre pseudo :");

            if (pseudo != null && !pseudo.trim().isEmpty()) { // trim = methode utile pour la propreté du pseudo au lieu d'utiliser une regex, et pseudo vide pas valide
                SaveMGR.ecrireClassement(pseudo, points); // Enregistrer le score
                new Classement(); // Afficher la fenêtre du classement 
            }
            if (DonjonInfini.estContiner) {
                SaveMGR.supprimerSauvegarde();
            }
           
            System.exit(0); // ferme le jeu proprement
        }



        if (sedeplacer) { // je veux savoir ce que le hero fais en fonction de la case destination si true on se deplace veut dire que case bonus detecter
            
            if (destination instanceof Or) {
                this.points += destination.getValeur();
            }else if (destination instanceof Potion) {
                this.points += destination.getValeur();
            }else if (destination instanceof Arme) {
                this.points += destination.getValeur();
            }
            vue.upPoint(points); // MAJ de point version graphique
            

            // je creer une nouvelle case aléatoire à l'ancienne position
            plateau[this.yHeros][this.xHeros] = Case.newRandomCase();
            vue.update(plateau[this.yHeros][this.xHeros], this.yHeros, this.xHeros); 

            // je MAJ les coordonne du héros
            this.xHeros = xNew;
            this.yHeros = yNew;
            
            // je déplace le héros vers la nouvelle case
            plateau[this.yHeros][this.xHeros]= this.hero;
            vue.update(this.hero, this.yHeros, this.xHeros);    

           // System.out.println("Le hero est à la case(l,c) : ("+this.yHeros+";"+this.xHeros+")"); // debug
        }else{

            if (destination instanceof Monstre && destination.getValeur() > 0){
            /*puisque le héros n'a pas pu se déplacer, la case du monstre est mise à jour et la case du héro aussi puisque 
            c'est cette actualisation qui met à jour le tableau d'info sur le côté*/
                vue.update(plateau[yNew][xNew],yNew,xNew);
                vue.update(plateau[this.yHeros][this.xHeros],this.yHeros,this.xHeros);
            } else if (destination instanceof Monstre && destination.getValeur() <= 0){
                Or newCaseOr = new Or(false);       //ajout booleen lors de la mort d'un monstre pour qu'une case mystère n'apparaisse pas
                Monstre caseDuMonstre = (Monstre) plateau[yNew][xNew];

                newCaseOr.setOr(caseDuMonstre.dropOr());
                plateau[yNew][xNew] = newCaseOr;
                vue.update(plateau[yNew][xNew], yNew, xNew); 

                // je déplace le héros vers la nouvelle case
                plateau[this.yHeros][this.xHeros]= this.hero;
                vue.update(this.hero, this.yHeros, this.xHeros);
            }
        }
        
    }

    /**
     * Gère l'appui sur une touche du clavier (non utilisé ici).
     *
     * @param e L'événement de touche pressée
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
    }


    /**
     * Gère le relâchement d'une touche du clavier.
     * Déplace le héros si une flèche directionnelle est relâchée.
     *
     * @param e L'événement de touche relâchée
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        int X = this.xHeros;
        int Y = this.yHeros;
        switch (key) { 
            case KeyEvent.VK_LEFT: 
                X = this.xHeros -1; 
                break;
            case KeyEvent.VK_RIGHT: 
                X = this.xHeros +1; 
                break;
            case KeyEvent.VK_DOWN: 
                Y = this.yHeros + 1; 
                break;
            case KeyEvent.VK_UP: 
                Y = this.yHeros - 1; 
                break;
            default:
                
               return; // Je ne fais rien si la touche n’est pas une flèche.
        }
        rencontre(X, Y);       
    }

    /**
     * Gère la frappe d'une touche du clavier (non utilisé ici).
     *
     * @param e L'événement de touche tapée
     */
    @Override
    public void keyTyped(KeyEvent e) {}
}