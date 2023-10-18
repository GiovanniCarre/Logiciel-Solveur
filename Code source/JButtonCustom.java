import javax.swing.JButton;

public class JButtonCustom extends JButton{
    JButtonCustom(String element){
        super(element);
        setForeground(Graphism.couleurTextBouton);
        setBackground(Graphism.couleurBouton);
    }
}
