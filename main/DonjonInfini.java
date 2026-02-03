import java.awt.event.*;
import javax.swing.*;

public class DonjonInfini{
    /**
     * Classe principale du jeu Donjon Infini.
     * Elle gère l'affichage du menu, la création d'une nouvelle partie
     * ou le chargement d'une partie sauvegardée.
     *
     * Le jeu se joue sur un plateau 3x3 avec une interface graphique Swing.
     * Chaque tour est contrôlé par un écouteur clavier, et la sauvegarde
     * automatique est déclenchée à la fermeture de la fenêtre.
     *
     * Auteur : Diallo
     */
    /** Taille du plateau de jeu (nombre de cases par côté). */
    public static final int TAILLE_PLATEAU = 3;

    /** Largeur de la fenêtre principale en pixels. */
    public static final int LARGEUR_PLATEAU = 1000;

    /** Hauteur de la fenêtre principale en pixels. */
    public static final int HAUTEUR_PLATEAU = 800;

    /** Nom de la fenêtre principale du jeu. */
    public static final String NOM_FENETRE = "Donjon Infini"; // Nom de la fenêtre de jeu
    public static JFrame win;
    /** Variable qui permet de biensuprimer la savegarde qpres lq ;ort du hero  si c une acienne partie */
    public static boolean estContiner;
    /**
     * Méthode principale du programme.
     * Elle affiche le menu en boucle et attend la fermeture de la fenêtre
     * de jeu avant de relancer le menu.
     */
    public static void main(String[] args) {
        int choix;
        while (true){

            try {
                Thread.sleep(100); // pause pour ne pas saturer le CPU risque de sur chauffe
            } catch (InterruptedException e) {
                System.out.println("Erreur: "+e);
            }

            choix = MenuDemarrage.afficherMenu();
            if (choix == 0){
                win = DonjonInfini.LancerLaSauvegarde();
            }else if (choix == 1) {
                // System.out.println(choix);
                win = nouvellePartie();
            }
            while (win.isVisible()){
                try {
                        Thread.sleep(100); // pause pour ne pas saturer le CPU risque de sur chauffe
                    } catch (InterruptedException e) {
                        System.out.println("Erreur: "+e);
                }
            }
        }
    }

    /**
     * Initialise une nouvelle partie :
     * - Crée un héros
     * - Génère un plateau 3x3 avec des cases aléatoires
     * - Place le héros au centre
     * - Crée la vue graphique et le contrôleur
     * - Configure la fenêtre de jeu avec écouteur de fermeture
     *
     * Retourne la fenêtre Swing affichée.
     *
     * @return JFrame contenant la partie
     */
    public static JFrame nouvellePartie() {
        DonjonInfini.estContiner = false;
        // Je defini uen var pour la taille du plateau
        int taillePlateau = 3;
        int tailleFen = 800; //Modification Mathis pour afficher toutes la fenetre
        // création du héros
        Heros hero =new Heros();
        // La Creation du plateau de Jeu
        Case[][] plateau =new Case[taillePlateau][taillePlateau];
        // On Genere le contenue des cases
        for (int ligne = 0; ligne < taillePlateau; ligne++) {
            for (int col = 0; col < taillePlateau; col++) {
                plateau[ligne][col] = Case.newRandomCase();
            }
        }
        // placement du héros
        plateau[1][1] = hero;
        // création de la vue, du contrôleur, de la fenêtre
        VuePlateau vue =new VuePlateau();
        //On affiche chaque case creer sur la fenetre. c mettre à jour chaque case  
        for (int ligne = 0; ligne < taillePlateau; ligne++) {
            for (int col = 0; col < taillePlateau; col++) {
                vue.update(plateau[ligne][col], ligne, col);
            }
        }

        

        // Apres je configure la fenêtre
        JFrame fenetre =new JFrame("Donjon Infini");
        fenetre.setSize(LARGEUR_PLATEAU, HAUTEUR_PLATEAU);
        fenetre.setResizable(false);
        fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // permet de bien exécuter la méthode windowClosing

        Controleur controleur =new Controleur(hero, plateau, vue,fenetre);
        // creation de l'écouteur de fenêtre pour la fermeture avec une class anonyme
        // qui va sauvegarder l'état du jeu avant la fermeture de la fenêtre
        // On utilise un WindowAdapter pour ne pas avoir a redéfinir toutes les méthodes de WindowListener
        // car on ne veut que la méthode windowClosed
        WindowAdapter ecouteurFenetre = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                //System.out.println("fermeture de la fenetre");
                fenetre.dispose();
                //System.out.println("Sauvegarde de l'état du jeu...");
                SaveMGR.sauvegarderJeu(hero, plateau, controleur.points); // Sauvegarde de l'état du jeu avant la fermeture de la fenêtre
                //System.out.println("Fenêtre fermée, jeu sauvegardé. Fermeture du programme.");
                System.exit(0); // je quitte le programme
            }
        };

        fenetre.addWindowListener(ecouteurFenetre); // Ajout de l'écouteur a la fenêtre


        fenetre.setLocationRelativeTo(null); 
        fenetre.add(vue); // ajoute la vue a la fenetre (donc le plateau normalment)
        //fenetre.setContentPane(vue); // Remplace tout le contenu de la fenêtre par vue on verra after. alternative a add(vue) et bien plus propre mais on a aussi le score a afficher
        fenetre.addKeyListener(controleur); // c'est ici qu'on lie le contrôleur a la fenetre 
        fenetre.setVisible(true);
        return fenetre;
    }

    /**
     * Tente de charger une partie depuis un fichier de sauvegarde.
     * Si les données sont incomplètes, une nouvelle partie est lancée.
     *
     * Restaure le héros, le plateau et le score, puis configure l'affichage
     * et les écouteurs comme pour une nouvelle partie.
     *
     * @return JFrame contenant la partie chargée, ou null si une nouvelle a été lancée
     */
    public static JFrame LancerLaSauvegarde() {
        DonjonInfini.estContiner = true;
        // lecture du fichier de sauvegarde
        Object[] data = SaveMGR.chargerJeu();
        // si le fichier de sauvegarde est vide, on lance une nouvelle partie
        if (data[0] == null || data[1] == null || data[2] == null) {
            System.out.println("Aucune sauvegarde trouvée, lancement d'une nouvelle partie.");
            DonjonInfini.nouvellePartie();
            return null;
        }
        // sinon, on récupère les données de la sauvegarde
        // reconstitution du héros, du plateau et du contrôleur
        Heros hero = (Heros) data[0];
        Case[][] plateau = (Case[][]) data[1];
        int score = (int) data[2];
        System.out.println("Sauvegarde trouvée, chargement de la partie...");
        //System.out.println("Score: " + score);

        // on crée et on met à jour la vue
        VuePlateau vue = new VuePlateau();
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            for(int j=0; j < TAILLE_PLATEAU; j++) {
                vue.update(plateau[i][j], i, j);
            }
        }

               
        

        // Apres je configure la fenêtre
        JFrame fenetre =new JFrame(NOM_FENETRE);
        fenetre.setSize(LARGEUR_PLATEAU, HAUTEUR_PLATEAU);
        fenetre.setResizable(false);
        fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // permet de bien exécuter la méthode windowClosing
        Controleur controleur = new Controleur(hero, plateau, vue,fenetre);
        controleur.points = score; // on met à jour les points du contrôleur avec ceux de la sauvegarde
        vue.upPoint(score); // MAJ de point version graphique
        // creation de l'écouteur de fenêtre pour la fermeture avec une class anonyme
        // qui va sauvegarder l'état du jeu avant la fermeture de la fenêtre
        // On utilise un WindowAdapter pour ne pas avoir a redéfinir toutes les méthodes de WindowListener
        // car on ne veut que la méthode windowClosed
        WindowAdapter ecouteurFenetre = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                //System.out.println("fermeture de la fenetre");
                fenetre.dispose();
                //System.out.println("Sauvegarde de l'état du jeu...");
                SaveMGR.sauvegarderJeu(hero, plateau, controleur.points); // Sauvegarde de l'état du jeu avant la fermeture de la fenêtre
                //System.out.println("Fenêtre fermée, jeu sauvegardé. Fermeture du programme.");
                System.exit(0); // je quitte le programme
            }
        };

        fenetre.addWindowListener(ecouteurFenetre); // Ajout de l'écouteur a la fenêtre


        fenetre.setLocationRelativeTo(null); 
        fenetre.add(vue); // ajoute la vue a la fenetre (donc le plateau normalment)
        //fenetre.setContentPane(vue); // Remplace tout le contenu de la fenêtre par vue on verra after. alternative a add(vue) et bien plus propre mais on a aussi le score a afficher
        fenetre.addKeyListener(controleur); // c'est ici qu'on lie le contrôleur a la fenetre 
        fenetre.setVisible(true);  
        
        return fenetre;
    }
}
