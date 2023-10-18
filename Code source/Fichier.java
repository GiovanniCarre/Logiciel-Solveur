public class Fichier {
    

    public static String sauvegardeEnString(){
        String result = PanneauPrincipale.faits.getText();
        result+="\nReglesSauvegarde:\n";
        result+=PanneauPrincipale.regles.getText();
        result+="\nVariables:\n";
        result+=PanneauVariable.variablesEntree.getText();
        result+="\nPaquets : \n";
        result+=PanneauPaquet.paquets.getText();
        return result;
    }

    public static void chargerFichier(String contenu){
        PanneauPrincipale.faits.setText(contenu.split("\nReglesSauvegarde:\n")[0]);
        contenu = contenu.split("\nReglesSauvegarde:\n")[1];
        PanneauPrincipale.regles.setText(contenu.split("\nVariables:\n")[0]);
        contenu = contenu.split("\nVariables:\n")[1];
        
        if (contenu.split("\nPaquets : \n").length == 0){
            PanneauVariable.variablesEntree.setText("");
            PanneauPaquet.paquets.setText("");        
        }else{
            PanneauVariable.variablesEntree.setText(contenu.split("\nPaquets : \n")[0]);
            PanneauPaquet.paquets.setText(contenu.split("\nPaquets : \n")[1]);
        }
    }
}
