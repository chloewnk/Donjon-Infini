import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Fenêtre de menu de démarrage du jeu Donjon Infini.
 * Permet à l'utilisateur de choisir entre continuer une partie, démarrer une nouvelle partie,
 * afficher le classement ou quitter le jeu.
 */
public class MenuDemarrage extends JFrame implements ActionListener{
    private int choix = -1; // 0 = sauvegarde, 1 = nouvelle
    private final JButton btnContinuer;
    private final JButton btnNouvelle;
    private final JButton bntClassement;
    private final JButton btnQuitter;

    /**
     * Construit la fenêtre du menu de démarrage avec les boutons d'action.
     * Initialise le fond, le titre, les boutons et leur style.
     */
    public MenuDemarrage() {
        this.setSize(400, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crée le fond avec image
        FondPanel fond = new FondPanel("../assets/images/fond.png");
        fond.setLayout(new BoxLayout(fond, BoxLayout.Y_AXIS));
        this.setContentPane(fond);

        // Titre stylisé
        JLabel titre = new JLabel("Donjon Infini !", JLabel.CENTER);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(new Font("Serif", Font.BOLD, 40));
        titre.setForeground(new Color(218, 165, 32)); // Or foncé
        titre.setOpaque(false);
        fond.add(Box.createVerticalStrut(20)); // espace haut
        fond.add(titre);

        // Panneau de boutons
        JPanel boutons = new JPanel();
        boutons.setOpaque(false); // transparence
        GridLayout btnLayout = new GridLayout(2, 2, 20, 20);
        boutons.setLayout(btnLayout); // vertical 3 lignes

        btnContinuer = new JButton("Continuer");
        btnNouvelle = new JButton("Nouvelle partie");
        bntClassement = new JButton("Top 5");
        btnQuitter = new JButton("Quitter");

        JButton[] listeDeBouton =new JButton[]{btnContinuer, btnNouvelle, bntClassement, btnQuitter};

        // Style transparent sur tous les boutons
        for (JButton btn : listeDeBouton) {
            btn.setContentAreaFilled(false);
            btn.setOpaque(false);
            btn.setBorderPainted(true);
            btn.setForeground(Color.white);
            Font btnfont = new Font("SansSerif", Font.PLAIN, 22);
            btn.setFont(btnfont);
            btn.addActionListener(this);
        }

        // Ajout des boutons au panneau
        boutons.add(btnContinuer);
        boutons.add(btnNouvelle);
        boutons.add(bntClassement);
        boutons.add(btnQuitter);

        fond.add(Box.createVerticalStrut(20));
        fond.add(boutons);
    }


    /**
     * Gère les actions des boutons du menu.
     * Met à jour le choix de l'utilisateur ou lance l'action correspondante.
     *
     * @param e L'événement d'action déclenché par un bouton
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnContinuer) {
            choix = 0;
            dispose();
        } else if (source == btnNouvelle) {
            choix = 1;
            //System.out.println("l="+choix);
            dispose();
        } else if (source == btnQuitter) {
            System.exit(0);
        } else if (source == bntClassement){
            new Classement();

        }
    }

    /**
     * Affiche le menu de démarrage et attend que l'utilisateur fasse un choix.
     * La méthode bloque tant que la fenêtre est ouverte.
     *
     * @return 0 si l'utilisateur choisit "Continuer", 1 pour "Nouvelle partie"
     */
    public static int afficherMenu() {
        MenuDemarrage menu = new MenuDemarrage();
        menu.setVisible(true);

        // j'attend que la fenêtre soit fermée
        while (menu.isVisible()) {
            try {
                Thread.sleep(100); // pause pour ne pas saturer le CPU risque de sur chauffe
            } catch (InterruptedException e) {
                System.out.println("Erreur: "+e);
            }
        }

        return menu.choix;
    }

    

   


}