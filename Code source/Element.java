import java.util.Objects;

public class Element {
    private String _nom;
    private Boolean _estVrai;
    private int _decouvertTemps;//0 correspond Ã  la découverte Ã  partir de la base de faits, et > 0 : correspond Ã  la découverte Ã  l'itération n...

    public Element(String nom){
        this._nom = nom.trim();
        this._estVrai = true;
        _decouvertTemps = 0;
        if (nom.length() > 1&&  this._nom.charAt(0) == '!'){
            this._estVrai = false;
            _nom = nom.substring(1).trim();
        }
    }

    public Element(String nom, Boolean estVrai, int iteration){
        this._nom = nom;
        this._estVrai = estVrai;
    }

    public String nom() {
        return _nom;
    }

    public String toString(){
        
        return ((this._estVrai) ? "": "!") + _nom;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return _nom.equals(element._nom) && _estVrai == element._estVrai;
    }
    
    //création d'une copie indépendante
    public Element clone(){
        return new Element(_nom, _estVrai, _decouvertTemps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_nom, _estVrai);
    }

    public boolean estVrai() {
        return _estVrai;
    }
}
