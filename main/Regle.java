import java.awt.*;
import javax.swing.*;


/**
 * Panneau affichant les règles du jeu Donjon Infini.
 * Affiche un texte explicatif sur les objectifs et les commandes du jeu.
 */
public class Regle extends JPanel{
    /**
     * Construit le panneau des règles du jeu avec un texte explicatif.
     * Le texte est affiché dans une zone non éditable et adaptée à la taille du panneau.
     */
    public Regle(){
        super();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(260,160));
        this.setBorder(BorderFactory.createTitledBorder("Règles du jeu"));

        JTextArea textRegle = new JTextArea(
            "Le but du jeu est de marqué un maximum de points\n\n" +
            "Utiliser les flèches pour déplacer le héro\n\n"+
            "Tuer des monstres pour gagner de l'or\n\n" + 
            "Ramasser l'or, les potions et les armes pour gagner des points\n\n" +
            "Des cases mystères peuvent apparaitre qui peuvent apporter : \n" + 
            "- un bonus, des Potions, Arme ou sac d'Or qui ont des valeurs jusqu'à 2 fois supérieur aux cases normal\n"+
            "- un malus, des monstres qui ont jusqu'à 2 fois plus de PV que les monstres normaux\n\n"+
            "Si les PV du héro tombent à 0 la partie est perdue "
        );

        textRegle.setLineWrap(true); // retour à la ligne automatique
        textRegle.setWrapStyleWord(true); //retour à la ligne pour les mots que ne tiennent pas entièrement sur la ligne précédente
        textRegle.setFocusable(false); // empêche la modification du texte
        textRegle.setOpaque(false); 
        this.add(textRegle,BorderLayout.CENTER);
    }
}