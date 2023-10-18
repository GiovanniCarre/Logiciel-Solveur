import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanneauRegle extends PanneauPersonnalise{

    JLabel titre = new JLabel("Aide");
    JTextArea aide = new JTextArea("Base de faits : Un fait par ligne on peut avoir par exemple sur une ligne :\n\"fievre(elevee)\" ou \"!A\" indiquant que le fait A est faux avec '!' devant\n\nBase de règles, une règle par ligne sachant que une règle est : \n[nom : premice(s) -> consequent(s)] avec par exemple:\n\"R1:A ET !B ET C -> S ET !G\" ou \"reègle test : A -> B\".\n\nPour plus d'informations, voir la documentation technique\n\nSinon le survol des éléments est possible pour plus d'informations.");
    JButtonCustom retour = new JButtonCustom("Retour");
    JScrollPane scroll = new JScrollPane(aide);

    @Override
    void initialiser() {
        setLayout(null);

        add(titre);

        add(scroll);
        ActionListener[] actionListeners = retour.getActionListeners();
        for (ActionListener a : actionListeners)
            retour.removeActionListener(a);
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Graphism.setPanel(new PanneauPrincipale());
            }
        });
        retour.setToolTipText("Retour au menu principal");

        add(retour);

    }

    @Override
    void refresh() {
        int w = getWidth();
        int h = getHeight();
     
        titre.setFont(new Font("Gabriela", Font.BOLD, 30));
        titre.setSize(w/3, h/5);
        titre.setLocation(w/2, h/20);        

        retour.setFont(new Font("Gabriela", Font.BOLD, 30));
        retour.setSize(w/5, h/8);
        retour.setLocation(w/10, h/10*9);
        
        aide.setFont(new Font("Gabriela", Font.BOLD, 30));
        scroll.setSize(w/5*4, h/2);
        scroll.setLocation(w/10, h/5);
    }
    
}
