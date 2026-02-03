import java.awt.*;
import javax.swing.*;

/**
 * Panneau personnalisé permettant d'afficher une image de fond redimensionnée.
 * Utilisé pour afficher un fond graphique dans l'interface du jeu Donjon Infini.
 */
public class FondPanel extends JPanel {
    private Image image;

    /**
     * Construit un panneau de fond avec l'image spécifiée.
     * @param cheminImage le chemin vers l'image à afficher en fond
     */
    public FondPanel(String cheminImage) {
        this.image = new ImageIcon(cheminImage).getImage();
    }

    /**
     * Redéfinit la méthode de dessin pour afficher l'image de fond à la taille du panneau.
     * @param g le contexte graphique utilisé pour dessiner
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
