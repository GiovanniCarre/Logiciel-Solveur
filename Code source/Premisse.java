import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Premisse {
    
    private ArrayList<Element> _elementListe = new ArrayList<>();

    public Premisse(ArrayList<Element> elementListe){
        this._elementListe = elementListe;
    }

    public boolean contient(Element e){
        return _elementListe.contains(e);
    }

    public Element avoirElementIndice(int indice){
        return _elementListe.get(indice);
    }

    public int taille(){
        return _elementListe.size();
    }

    public Premisse(String description){
        String[] tampon = description.split("ET");
        for (int i = 0; i < tampon.length;i++)
            _elementListe.add(new Element(tampon[i].trim()));
    }

    public Premisse() {
    }

    @Override
    public boolean equals(Object o){
        Premisse p = (Premisse)o;
        if (_elementListe.size() != p._elementListe.size()) 
            return false; // Si les listes n'ont pas la mÃªme taille, elles ne peuvent pas Ãªtre égales
    
        // Créez des copies des listes pour les manipuler sans affecter les originales
        List<Element> copieListe1 = new ArrayList<>(_elementListe);
        List<Element> copieListe2 = new ArrayList<>(p._elementListe);

        // Triez les copies des listes pour aligner les éléments
        Collections.sort(copieListe1, Comparator.comparing(Element::hashCode)); // Tri par valeur, ajustez-le selon vos besoins
        Collections.sort(copieListe2, Comparator.comparing(Element::hashCode)); // Tri par valeur, ajustez-le selon vos besoins

        // Comparez les listes triées
        return copieListe1.equals(copieListe2);
    }

    public String toString(){
        if (_elementListe.size() == 0)
            return "Premisse Vide";
        String resultat = "";
        for (int i = 0; i < _elementListe.size();i++){
            if (i == 0)
                resultat+=_elementListe.get(i).toString();
            else
                resultat+=" & " +_elementListe.get(i).toString();
        }
        return resultat;
    }

    public void ajouterElement(Element e){
        _elementListe.add(e);
    }

    public Premisse clone(){
        Premisse nouvelle = new Premisse();
        for (int i = 0; i < _elementListe.size();i++)
            nouvelle.ajouterElement(_elementListe.get(i).clone());
        return nouvelle;
    }

    public ArrayList<Element> listeElements(){
        return this._elementListe;
    }

}
