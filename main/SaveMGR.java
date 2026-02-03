import java.io.*;

/**
 * Classe utilitaire pour la gestion de la sauvegarde et du classement du jeu Donjon Infini.
 * Permet de sauvegarder, charger et supprimer l'état du jeu, ainsi que de gérer le classement des scores.
 */
public class SaveMGR {

    /** Nom du fichier de sauvegarde du jeu. */
    private static final String FICHIER = "./saveJeu.dat";

    /**
     * Sauvegarde l'état du jeu dans un fichier.
     *
     * @param heros   Le héros à sauvegarder
     * @param plateau Le plateau de jeu à sauvegarder
     * @param score   Le score actuel à sauvegarder
     */
    public static void sauvegarderJeu(Heros heros, Case[][] plateau, int score) {
        try {
            // Création d'un flux de sortie vers le fichier
            FileOutputStream fos = new FileOutputStream(SaveMGR.FICHIER);
            ObjectOutputStream oos = new ObjectOutputStream(fos); //Permet d'écrire des objets

            // Écriture des objets dans le fichier
            oos.writeObject(heros);
            oos.writeObject(plateau);
            oos.writeInt(score);
            
            // Fermeture des flux
            try {
                oos.close();
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture." + e);
            }
            System.out.println("Sauvegarde réussie !");
        } catch (IOException e) {
            System.out.println("Impossible d'ouvrir le fichier." + e);
        }
    }

    /**
     * Charge l'état du jeu depuis le fichier de sauvegarde.
     *
     * @return Un tableau d'objets contenant le héros, le plateau et le score chargés,
     *         ou un tableau avec des éléments nuls si la sauvegarde est absente ou corrompue
     */
    public static Object[] chargerJeu(){
        Object[] data =new Object[3];
        try {
            // Creation d'un flux d'entrée depuis le fichier
            FileInputStream fis = new FileInputStream(SaveMGR.FICHIER);
            ObjectInputStream ois = new ObjectInputStream(fis); //Permet de lire des objets
            try {
                // Lecture des objets depuis le fichier
                data[0] = ois.readObject();
                data[1] = ois.readObject();
                data[2] = ois.readInt();
                try {
                    ois.close();
                } catch (IOException e) {
                    // System.out.println("Erreur lors de la fermeture." + e);
                }
            } catch (IOException | ClassNotFoundException e) {
                // System.out.println("Erreur lors de la lecture." + e);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Impossible d'ouvrir le fichier." + e);
        } catch (IOException e) {
            // System.out.println("Erreur d'entrée/sortie." + e);
        }
        return data;
    }

    /**
     * Supprime le fichier de sauvegarde du jeu s'il existe.
     */
    public static void supprimerSauvegarde() {
        File fichier = new File("./saveJeu.dat");
        if (fichier.exists()) {
            fichier.delete();
            System.out.println("Sauvegarde supprimée.");
        }else{
            // System.out.println("Erreur de la suppression");
        }
    }

    /**
     * Écrit un score dans le classement, en l'insérant à la bonne position parmi les 5 meilleurs scores.
     * Si le classement contient déjà 5 scores, le plus bas est supprimé si besoin.
     *
     * @param pseudo Le pseudo du joueur
     * @param score  Le score à enregistrer
     */
    public static void ecrireClassement(String pseudo, int score) { 
        String[] lignes = new String[5]; // pour stocker les 5 meilleurs scores, chaque case est une ligne
        int nblignes = 0;
        int scorePosition;
        String scoreClassement;
        int scorePrecedent;
        int positionNvScore = 6; // initailisation par defaut, j'ai choisit 6 car au moins on est sur qu'il n'apparait pas

        try {
            FileReader fr = new FileReader("classement.dat");
            BufferedReader br = new BufferedReader(fr);
            String ligne;

            while ((ligne = br.readLine()) != null && nblignes < 5) { // fichier non vide et a 5 valeurs 
                lignes[nblignes] = ligne;
                scorePosition = ligne.lastIndexOf(" = "); // recupere position
                scoreClassement = ligne.substring(scorePosition + 3); // +3 pour sauter " = "
                scorePrecedent = Integer.parseInt(scoreClassement);

                if (score >= scorePrecedent && positionNvScore == 6) {
                    positionNvScore = nblignes; // on insère à cette position
                }

                nblignes++;
            }

            br.close();
        } catch (IOException e) {
            System.out.println("erreur de lecture");
        }

        try {
            FileWriter fw = new FileWriter("classement.dat");
            BufferedWriter bw = new BufferedWriter(fw);

            int i = 0;
            boolean aEcrit = false;

            for (int j = 0; j < nblignes && i < 5; j++) {
                if (j == positionNvScore && !aEcrit) {
                    bw.write(pseudo + " = " + score);
                    bw.newLine();
                    aEcrit = true;
                    i++;
                }

                if (i < 5) {
                    bw.write(lignes[j]);
                    bw.newLine();
                    i++;
                }
            }

            if (!aEcrit && i < 5) { // si il n'y a pas encore 5 scores et que le score n'a pas encore été ajouté :
                bw.write(pseudo + " = " + score); // ecriture
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("erreur d'écriture");
        }
    }
}





