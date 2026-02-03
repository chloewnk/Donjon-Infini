import java.awt.*;
import javax.swing.*;

/**
 * Représente la vue graphique du plateau de jeu Donjon Infini.
 * Affiche la grille du donjon, les informations du héros (PV, arme, score)
 * et les règles du jeu dans un panneau latéral.
 */
public class VuePlateau extends JPanel{
    private VueCase[][] cases;
    private int vuePoints;
    
    private JLabel labelPV;
    private JLabel labelArme;
    private JLabel labelScore;
    
    /**
     * Construit la vue du plateau avec une grille 3x3 et un panneau d'informations.
     * Initialise les cases, les labels d'information et le panneau des règles.
     */
    public VuePlateau(){
        super();
        this.setLayout(new BorderLayout());
        
        // Initialisation JLabel du panel d'information
        labelPV=new JLabel("PV : ?");
        labelArme=new JLabel("Arme : ?");
        labelScore=new JLabel ("Score : 0");
        
        JPanel heroPanel=new JPanel();
        heroPanel.setPreferredSize(new Dimension(150,160)); //dimension du panel d'information
        heroPanel.setBorder(BorderFactory.createTitledBorder("Infos Héros")); 
        heroPanel.setLayout(new GridLayout(3,1));
        
        heroPanel.add(labelPV);
        heroPanel.add(labelArme);
        heroPanel.add(labelScore);


        Regle reglePanel = new Regle();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); //affiche le infoPanel et le reglePanel l'un en dessous de l'autre
        infoPanel.add(heroPanel);
        infoPanel.add(reglePanel);
        
        // initialisation de la grille du donjon
        JPanel gridPanel=new JPanel(new GridLayout(3,3));
        this.cases=new VueCase[3][3];
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                cases[i][j]=new VueCase();
                gridPanel.add(cases[i][j]);
            }
        }
        
        
        this.add(gridPanel,BorderLayout.CENTER);
        this.add(infoPanel,BorderLayout.EAST);
    }
    
    /**
     * Met à jour l'affichage d'une case du plateau et les informations du héros si besoin.
     * @param c La case à afficher
     * @param x La ligne de la case dans la grille
     * @param y La colonne de la case dans la grille
     */
    public void update(Case c, int x, int y){
        cases[x][y].setCase(c);

        //Mise à jour du panel d'information lorsque le Heros est mit à jour 
        if (c.getLabel().equals("Heros")){
            Heros h=(Heros) c;
            labelPV.setText("PV : "+h.getValeur());
            
            if (h.getArme()!=null){
                labelArme.setText("Arme : "+h.getArme().getValeur()+" dégâts");
            }else{
                labelArme.setText("Arme : Aucune");
            }
            labelScore.setText("Score : "+vuePoints);
        }
        repaint();
    }
    
    /**
     * Met à jour le score affiché dans le panneau d'informations.
     * @param c Le nouveau score à afficher
     */
    public void upPoint(int c){
        this.vuePoints=c;
        labelScore.setText("Score : "+vuePoints); // Pellel a rajouter pour mettre à jour le score dans la vue en debut de partie. cas où on charge une partie
    }


}
