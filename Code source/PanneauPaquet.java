import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanneauPaquet extends PanneauPersonnalise{

    private static JButtonCustom retour = new JButtonCustom("Retour / sauvegarder");
    private static JLabel label = new JLabel("Définitions des paquets"), reglesLabel = new JLabel("Base de règles");
    private static JTextArea labelExplications = new JTextArea("Dans le panneau de gauche, on a un paquet = une ligne.\nPour créer un paquet, il faut le nom des règles, on aura donc pour un paquet : \nnom1;regle2;modus ponens\n Il faut donc que les noms des règles soient séparés par un ';'");
    public static JTextArea paquets = new JTextArea();
    private static JScrollPane scroll = new JScrollPane(), scroll2 = new JScrollPane();

    void initialiser() {
        retour.setBackground(Graphism.couleurBouton);
        retour.setForeground(Graphism.couleurTextBouton);
        add(retour);

        scroll = new JScrollPane(paquets);
        add(scroll);


        labelExplications.setOpaque(false);
        labelExplications.setForeground(Color.black);
        labelExplications.setBorder(null);
        add(labelExplications);


        scroll2 = new JScrollPane(PanneauPrincipale.regles);
        add(scroll2);


        add(reglesLabel);
        add(label);

        ActionListener[] actionListeners = retour.getActionListeners();
        for (ActionListener listener : actionListeners) 
            retour.removeActionListener(listener);

        retour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphism.setPanel(new PanneauPrincipale());    
            }
        });
        repaint();
    }

    void refresh() {
        final Font texteFont = new Font("Gabriela", Font.PLAIN,getWidth()/90);
        final Font titreFont = new Font("Gabriela", Font.BOLD,getHeight()/30);
        final int w = getWidth();
        final int h = getHeight();
        
        retour.setFont(texteFont);
        retour.setSize(w/6, h/10);
        retour.setLocation(w/10, h/10*9);

        paquets.setFont(texteFont);
        scroll.setFont(texteFont);
        scroll.setSize(w/2, h/3*2);
        scroll.setLocation(w/20, h/15);

        labelExplications.setFont(texteFont);
        labelExplications.setSize(w/2, h/4);
        labelExplications.setLocation(w/3, h/20*15);

        reglesLabel.setFont(titreFont);
        reglesLabel.setSize(w/2, h/20);
        reglesLabel.setLocation(w/3*2, h/100);

        scroll2.setFont(texteFont);
        scroll2.setSize(w/30*11, h/3*2);
        scroll2.setLocation(w/20*12, h/15);
        
        label.setFont(titreFont);
        label.setSize(w/2, h/20);
        label.setLocation(w/20, h/100);
        
    }
    
}
