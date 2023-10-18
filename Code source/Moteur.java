import java.awt.Color;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Moteur implements Runnable{
    protected BaseDeFaits _baseDeFaits = new BaseDeFaits();
    protected BaseDeRegles _baseDeRegles = new BaseDeRegles();
    protected Strategie _strategie = null;
    protected boolean _trace = true;
    protected Tri _methodeDeTrie;
    protected boolean _verifierIncoherences = true;
  
    public static boolean attenteReponseUtilisateur = false;

    protected static boolean enConsole = false;

    public Moteur(BaseDeFaits baseDeFaits, BaseDeRegles baseDeRegles, Strategie strategie, boolean trace, boolean verifierIncoherences, Tri trie){
        this._baseDeFaits = baseDeFaits;
        this._baseDeRegles = baseDeRegles;
        this._strategie = strategie;
        this._verifierIncoherences = verifierIncoherences;
        this._trace = trace;
        this._methodeDeTrie = trie;
    }

    public static void executer(Moteur m){
        new Thread(m).start();
    }

    //setter Strategie

    public String toString(){
        return this._baseDeFaits.toString() + " " + this._baseDeRegles.toString();
    }


    public void verifierIncoherences() throws Exception{
        _baseDeRegles.verifierIncoherences();
        _baseDeFaits.verifierIncoherences();    
    }

    public static void print(String msg) {
        if (enConsole)
            System.out.println(msg);
        else{
            PanneauPrincipale.resultat.append("\n"+msg);
            PanneauPrincipale.resultat.setCaretPosition(PanneauPrincipale.resultat.getDocument().getLength());
        }
    }

    public static String lireReponse(String msg) {
        if (enConsole){
            System.out.println(msg);
            Scanner sc = new Scanner(System.in);
            String reponse = sc.nextLine();
            //sc.close();
            return reponse;
        }
        else{
            SwingUtilities.invokeLater(() -> {
                PanneauPrincipale.input.setBackground(new Color(255,0,125));
                PanneauPrincipale.input.setText("");
                PanneauPrincipale.input.requestFocus();
                PanneauPrincipale.input.setEnabled(true);
                PanneauPrincipale.input.setBorder(new CompoundBorder(new LineBorder(Color.black, 4), new EmptyBorder(10, 10, 10, 10)));
            });
            Moteur.print(msg);
        // Activez l'attente de réponse
        attenteReponseUtilisateur = true;
        

        // Attendez que l'utilisateur appuie sur un bouton (ou utilisez un événement approprié)
        
        while (attenteReponseUtilisateur){
            PanneauPrincipale.calculer.setVisible(false);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        PanneauPrincipale.calculer.setVisible(true);
        PanneauPrincipale.input.setBackground(Color.white);
        String reponse = PanneauPrincipale.input.getText();
        PanneauPrincipale.input.setText("");
        PanneauPrincipale.input.setEnabled(false);
        PanneauPrincipale.input.setBorder(null);
        return reponse;
        }
    }

    @Override
    public void run() {
        
        try {
            if (_verifierIncoherences)
                verifierIncoherences();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this._strategie == null) return;
        this._strategie.executer(_baseDeFaits, _baseDeRegles, _trace, _methodeDeTrie);
    }



}
