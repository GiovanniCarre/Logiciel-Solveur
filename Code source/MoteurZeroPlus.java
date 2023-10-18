import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoteurZeroPlus extends Moteur{

    ArrayList<Predicat> _predicats = new ArrayList<Predicat>();
    HashMap<String, Variable> _variables;

    
    public MoteurZeroPlus(BaseDeFaits baseDeFaits, BaseDeRegles baseDeRegles, Strategie strategie, 
            HashMap<String, Variable> variables, boolean trace, boolean verifierIncoherences, Tri tri){

        super(baseDeFaits, baseDeRegles, strategie, trace, verifierIncoherences, tri);
        if (variables == null) _variables = new HashMap<String, Variable>();
        else _variables = variables;
    }

    @Override
    public void run() {
        
         try {
            if (_verifierIncoherences)
                verifierIncoherences();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (_strategie == null) {
            if (_trace) print("Aucune stratégie n'a été définie");
            return;
        }

        //remplace les variables
        remplacerVariables();
        
        _strategie.executer(_baseDeFaits, _baseDeRegles, _trace, _methodeDeTrie);

    }

    public static void executer(MoteurZeroPlus m){
        new Thread(m).start();
       
    }

    public void setVariables(HashMap<String, Variable> _variables) {
        this._variables = _variables;
    }

    public boolean estPredicat(String chaine) {
        // Utilisation d'une expression régulière pour vérifier le format abc(E, M, pf5)
        
        String regex = "([A-Za-z0-9]_?)+\\((!?([A-Za-z0-9]_?)+,(\\s?)+)*!?([A-Za-z0-9]_?)+\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chaine);
        
        return matcher.matches();
    }

    public String stringAleatoire(int taille){
        String alphabet = "0123456789abcdefghijklmnopqrstuvxyz";
        String stringAleatoire = "";
        for (int j = 0; j < taille; j++){
            int ch = (int)(alphabet.length() * Math.random());
            stringAleatoire += alphabet.charAt(ch);
        }
        return stringAleatoire;
    }


    //genere des valeurs possibles aleatoires temporairement, Ã  remplacer dès que possible
    public void valeursPossiblesAleatoires(ArrayList<String> array){
        for (int i = 0; i < 3; i++){
            array.add(stringAleatoire(5));
        }
    }


    public void remplacerVariables(){
        //if (this._trace) print("Début de l'analyse des règles afin de remplacer les variables ...");

        //_predicats.clear();
        
        //liste ordonnée de toutes les variables trouvées dans nos règles
        //Si certaines ne sont pas définies dans this._variables, on demandera Ã  l'utilisateur de les définir
        ArrayList<String> listeVariablesTrouvees = new ArrayList<String>();

        ArrayList<ArrayList<String>> valeursPossiblesVariables = new ArrayList<ArrayList<String>>();

        int i = 0;
        while (i < this._baseDeRegles.taille()){
            Regle r = this._baseDeRegles.avoirRegleParIndice(i);
            //if (this._trace) print("  Analyse la regle "+r.toString()+" ...");


            //recuperer tous les Elements de la règle et regarder si certains sont des Predicats
            ArrayList<Element> elementsDeR = new ArrayList<Element>(r.avoirPremicesListe());
            elementsDeR.addAll(r.avoirConsequentsListe());

            for (int p_i = 0; p_i < elementsDeR.size(); p_i++){
                //cherche s'il y a des parenthèses dans l'element     
                String pr = elementsDeR.get(p_i).toString().replaceAll("\\s", "");  
                
                //if (this._trace) print("\n    Analyse de: '"+pr+"'");

                if (estPredicat(pr)){
                    String[] parametres = pr.substring(pr.indexOf("(")+1, pr.indexOf(")")).split(",");

                    //if (this._trace) print("        Predicat trouvé\n");
                    
                    /*
                    _predicats.add(new 
                        Predicat(pr_nom,        //nom du Predicat
                        parametres.length)      //nombre de parametres dans le predicat
                    );
                    */

                    for (String param: parametres) {
                        //si le paramètre commence par une majuscule, alors c'est une variable
                        if (param.charAt(0) >= 65 && param.charAt(0) <= 90){

                            //if (this._trace) print("Nouvelle variable: "+param);
                            
                            listeVariablesTrouvees.add(param);

                            ArrayList<String> vals;
                            if (this._variables.containsKey(param)){
                                vals = this._variables.get(param)._valeursPossibles;
                            } else {
                                // erreur, variable non définié. demander Ã  l'utilisateur
                                //if (this._trace) print("Variable "+param+" non définie");
                                vals = new ArrayList<String>();
                                valeursPossiblesAleatoires(vals);
                            }
                            valeursPossiblesVariables.add(vals);
                        }
                    }
                } else {
                    //if (this._trace) print("        Aucun prédicat trouvé\n");
                }              
            }

            //Ã  chaque regle, remplacer les variables par leurs valeurs possibles s'il y en a
            Integer nombreReglesSupprimees = 
            remplacerVariablesUneRegle(r, listeVariablesTrouvees, valeursPossiblesVariables);
            i -= nombreReglesSupprimees;

            i++;
        }

        //afficher les nouvelles regles
        if (this._trace) {
            //print("nouvelles regles: ");
            //for (i = 0; i < this._baseDeRegles.taille(); i++) print("  "+this._baseDeRegles.avoirRegleParIndice(i).toString());
        }
    }

    //renvoie le nombre de regles supprimées afin de ne pas perdre le compte dans la boucle principale
    public int remplacerVariablesUneRegle( Regle r, ArrayList<String> listeVariables, 
                ArrayList<ArrayList<String>> valeursPossiblesVariables){
        if (listeVariables.size() == 0) return 0;

        //on remplace les occurences de la variable en tÃªte de la pile
        String varRemplacee = listeVariables.get(0);


        this._baseDeRegles.enleverRegle(r.nom());


        for (int i = 0; i < valeursPossiblesVariables.get(0).size(); i++){
            //copie de la regle d'origine avec les occurences de la variable remplacées
            String stringNouvelleRegle = r.toString().replaceAll("\\b" + varRemplacee + "\\b", valeursPossiblesVariables.get(0).get(i));
            stringNouvelleRegle = stringNouvelleRegle.replaceFirst(r.nom() + " : ", "");
            String nomNouvelleRegle = stringAleatoire(6);

            //if (this._trace) print("    Nouvelle combinaison:   " + nomNouvelleRegle + " : "+ stringNouvelleRegle);

            Regle nouvelleRegle = new Regle(
                //nouvelle regle avec un nouveau nom
                nomNouvelleRegle + " : " + stringNouvelleRegle
            );

        
            this._baseDeRegles.ajouterRegle(nouvelleRegle);
        }

        listeVariables.remove(0);
        valeursPossiblesVariables.remove(0);

        remplacerVariablesUneRegle(r, listeVariables, valeursPossiblesVariables);

        return 1;
    }


}
