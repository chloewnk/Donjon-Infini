import java.awt.*;
import javax.swing.*;

/**
 * Représente la vue graphique d'une case du plateau dans le jeu Donjon Infini.
 * Affiche l'image et la valeur associée à la case (points de vie, or, etc.).
 */
public class VueCase extends JPanel{
    private JLabel labelValeur;
    private Image img;

    /**
     * Construit une vue de case vide avec bordure noire et zone de texte pour la valeur.
     */
    public VueCase(){
        super();
        this.setMinimumSize(new Dimension(160,160)); // dimension de l'image
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // affiche les bords de case en noir
        this.setLayout(new BorderLayout()); // Permet de changer la position des éléments du JLabel

        labelValeur = new JLabel("");
        labelValeur.setHorizontalAlignment(JLabel.LEFT);
        labelValeur.setVerticalAlignment(JLabel.TOP);
        this.add(labelValeur);
    }

    /**
     * Met à jour l'affichage de la case selon le type de case du plateau.
     * Affiche la valeur ou les points de vie, et charge l'image correspondante.
     * @param c La case à afficher
     */
    public void setCase(Case c){
        String s = c.getLabel();
        // Utilise la bonne fonction selon le type de case pour afficher les PV / la valeur de celle-ci
        if (c.getMystere()){
            this.img = Toolkit.getDefaultToolkit().getImage("../assets/images/mystere.png");
            labelValeur.setText("");
            
        }else{
            if (s.equals("Heros") || s.equals("Monstre") || s.equals("Arme")){
                labelValeur.setText(c.getLabelPv());
            } else {
                labelValeur.setText(String.valueOf(c.getValeur()));
            }
            labelValeur.setForeground(Color.RED);
            this.img = Toolkit.getDefaultToolkit().getImage("../assets/images/" + s + ".png");
        }
        repaint();
    }

    /**
     * Redéfinit la méthode de dessin pour afficher l'image de la case à la taille du panneau.
     * @param pinceau Le contexte graphique utilisé pour dessiner
     */
    @Override
    protected void paintComponent(Graphics pinceau){
        Graphics secondPinceau = pinceau.create();
        super.paintComponent(secondPinceau);
        secondPinceau.drawImage(this.img, 0, 0, this);
    }
}
