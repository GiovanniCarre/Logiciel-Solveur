import java.util.ArrayList;

public class Regle {
    
    private String _nom;
    private Premisse _consequent;
    private Premisse _premice;
    private int _decouvertTempsPremice;

    public Regle(String nom, Premisse premice, Premisse consequent){
        this._nom = nom;
        this._premice = premice;
        this._consequent = consequent;
        _decouvertTempsPremice  = 0;
    }

    public void setDecouvertTempsPremice(int _decouvertTempsPremice) {
        this._decouvertTempsPremice = _decouvertTempsPremice;
    }

    //Mini-parser
    public Regle(String string) {
        _nom = string.split(":")[0].trim();
        _premice = new Premisse(string.split(":")[1].split("->")[0].trim());
        _consequent = new Premisse(string.split(":")[1].split("->")[1].trim());
        _decouvertTempsPremice = 0;
    }

    public int taillePremice(){
        return _premice.taille();
    }

    public Premisse avoirPremices() {
        return _premice;
    }

    @Override
    public boolean equals(Object o){
        Regle r = (Regle) o;
        return (r._premice.equals(_premice) && r._consequent.equals(_consequent));
    }

    public Premisse avoirConsequents() {
        return _consequent;
    }

    public Element avoirPremiceParIndice(int indice){
        return _premice.avoirElementIndice(indice);
    }

    public int tailleConsequent(){
        return _consequent.taille();
    }

    public int getValeur() {
        return _premice.taille();
    }

    public Element avoirConsequentParIndice(int indice){
        return _consequent.avoirElementIndice(indice);
    }

    public boolean consequentContient(Element e){
        for (int i = 0; i < this._consequent.taille(); i++){
            if (this._consequent.avoirElementIndice(i).equals(e)) return true;
        }
        return false;
    }

    public boolean premiceContient(Element e){
        for (int i = 0; i < this._premice.taille(); i++){
            if (this._premice.avoirElementIndice(i).equals(e)) return true;
        }
        return false;
    }

    public ArrayList<Element> avoirPremicesListe(){
        return this._premice.listeElements();
    }

    public int getDecouvertTempsPremice() {
        return _decouvertTempsPremice;
    }

    public ArrayList<Element> avoirConsequentsListe(){
        return this._consequent.listeElements();
    }


    public String toString(){
        return _nom + " : "+_premice.toString()+" -> "+_consequent.toString();
    }


    //si on a -1 alors le premice ne fais pas partie
    public int avoirValeurPremice(String nomPremice) {
        for (int i = 0; i < _premice.taille();i++){
            if (_premice.avoirElementIndice(i).nom() == nomPremice){
                if (_premice.avoirElementIndice(i).estVrai())
                return 1;
                else 
                    return 0;
            }
        }

        return -1;
    }

    public String nom(){
        return _nom;
    }

    public Regle clone(){
        Regle nouvelle = new Regle(new String(_nom), _premice.clone(), _consequent.clone());
        
        return nouvelle;
    }

    public void setNom(String nouveauNom) {
        this._nom = nouveauNom;
    }


}
