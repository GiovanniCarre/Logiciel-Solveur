import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanneauVariable extends PanneauPersonnalise{

    private static JLabel titre = new JLabel("Variables"), explications = new JLabel("1 ligne = 1 variable, Syntaxe : Nom de ma variable : val1; val2; val3"),exemple =  new JLabel("Exemple  Vitesse : lente; rapide;moyenne");
    private static JButtonCustom retour = new JButtonCustom("Retour");
    public static JTextArea variablesEntree = new JTextArea();
    private static JScrollPane scrollbar;

    @Override
    void initialiser() {
        setLayout(null);

        add(titre);

        scrollbar = new JScrollPane(variablesEntree);

        ActionListener[] actionListeners = retour.getActionListeners();
        for (ActionListener listener : actionListeners) 
            retour.removeActionListener(listener);
        
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Graphism.setPanel(new PanneauPrincipale());
            }
        });

        add(explications);
        add(exemple);
        
        add(scrollbar);
        add(retour);
    }

    @Override
    void refresh() {
        int w = getWidth();
        int h = getHeight();

        titre.setFont(new Font("Gabriela", Font.BOLD, 30));
        titre.setSize(w/3, h/20);
        titre.setLocation(w/2, h/50);
        
        retour.setFont(new Font("Gabriela", Font.BOLD, 30));
        retour.setSize(w/3, h/20);
        retour.setLocation(w/20, h/10*9);
        

        variablesEntree.setFont(new Font("Gabriela", Font.BOLD, 30));
        scrollbar.setSize(w/5*4, h/10*6);
        scrollbar.setLocation(w/10, h/10);

        explications.setFont(new Font("Gabriela", Font.BOLD, 25));
        explications.setSize(w/2, h/10);
        explications.setLocation(w/4, h/10*7);

        exemple.setFont(new Font("Gabriela", Font.BOLD, 25));
        exemple.setSize(w/2, h/10);
        exemple.setLocation(w/4, h/10*8);
    }
    
}
