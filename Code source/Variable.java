import java.util.ArrayList;

public class Variable {
    String _nom;

    ArrayList<String> _valeursPossibles = new ArrayList<String>();

    public Variable(String nom){
        this._nom = nom;
    };
    public Variable(String nom, ArrayList<String> valeursPossibles){        
        this._nom = nom;
        this._valeursPossibles = valeursPossibles;
    };


    public String get_nom(){
        return _nom;
    }

    public ArrayList<String> get_valeursPossibles() {
        return _valeursPossibles;
    }
}
