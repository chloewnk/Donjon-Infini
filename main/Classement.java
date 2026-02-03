import java.io.*;
import javax.swing.*;

/**
 * Fenêtre d'affichage du classement des meilleurs scores du jeu Donjon Infini.
 * Affiche jusqu'à 5 scores lus depuis le fichier "classement.dat" dans une zone de texte non éditable.
 * La fenêtre se ferme sans quitter le programme principal.
 */
public class Classement extends JFrame {

    /**
     * Construit la fenêtre de classement et affiche les scores.
     * Lit le fichier "classement.dat" (s'il existe) et affiche jusqu'à 5 scores.
     * Si le fichier n'existe pas ou est vide, la zone de texte reste vide ou vide.
     */
    public Classement() {
        setTitle("Classement");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // exit on close fermerait le programme et pas que la fenetre

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        try { // lecture du fichier classement
            FileReader fr = new FileReader("classement.dat");
            BufferedReader br = new BufferedReader(fr);
            String ligne;
            int i = 0;

            try{
                while ((ligne = br.readLine()) != null && i < 5) { // si le fichier n'est pas vide (methode readline) et qu'on n'a pas lu 5 lignes on fait : 
                    textArea.append((i + 1) + ". " + ligne + "\n"); 
                    i++;
                }

            }catch(IOException e ){
                // System.out.println("erreur de lecture");
            }
            try {
                br.close();
            } catch(IOException e){
                // System.out.println("pb de fermeture");
            }
                if (i == 0) {
                     textArea.setText("Aucun score enregistré.");
                }
        } catch (IOException e) {
            // System.out.println("Erreur d'ouverture du fichier.");
        }

        this.add(new JScrollPane(textArea));
        setVisible(true);
    }
}

