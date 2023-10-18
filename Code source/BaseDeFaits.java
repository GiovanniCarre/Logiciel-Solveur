import java.util.ArrayList;

public class BaseDeFaits {
    private ArrayList<Element> _base = new ArrayList<Element>();

    public BaseDeFaits(){}
    
    public void ajouterFait(Element nouveauFait){
        if (!contient(nouveauFait))this._base.add(nouveauFait);
    }

    public void retireFait(Element faitARetirer) {
        this._base.remove(faitARetirer);
    }

    public Element recupererFaitParIndice(int id){
        return this._base.get(id);
    }

    public ArrayList<Element> recupererBase() {
        return this._base;
    }

    public int nombreFaits(){
        return _base.size();
    }

    public String toString(){
        String str = "[";
        for (int i = 0; i < _base.size(); i++){
            str += "["+_base.get(i).toString()+"]";
            if (i < _base.size()-1) str += ", ";
        }
        str += "]";
        return str;
    }

    public boolean contient(Element element){
        return _base.contains(element);
    }
    

    public static BaseDeFaits copy(BaseDeFaits b){
        BaseDeFaits nouvelleBase = new BaseDeFaits();
        for (int i = 0; i < b.nombreFaits();i++)
            nouvelleBase.ajouterFait(b.recupererFaitParIndice(i).clone());
        return nouvelleBase;
    }


    //alors 1 == true
    //0 == false
    //Sinon renvoie -1 si on sait pas
    public int avoirValeurFait(String premiceNom) {
        for (int i = 0; i < _base.size();i++){
            if (_base.get(i).nom().equals(premiceNom)){
                if (_base.get(i).estVrai())
                    return 1;
                else 
                    return 0;
            }

        }
        return -1;
    }

    public void verifierIncoherences() throws Exception {
        verifierDoublons();
        verifierNoms();
        verifierPresenceVraiFaux();//on doit enlever les doublons en 1er
    }


    //on vérifie si on a pas A et !A
    private void verifierPresenceVraiFaux() throws Exception {
        //on considère qu'avant on a enlevé les doublons
        ArrayList<Element> listesNomsDejaFaits = new ArrayList<>();
        ArrayList<Element> listesPb = new ArrayList<>();
        boolean appartient = false;
        for (int i = 0; i < _base.size();i++){
            appartient = false;
            for (int j = 0; j < listesNomsDejaFaits.size();j++){
                if (listesNomsDejaFaits.get(j).nom().equals(_base.get(i).nom()) && i != j)
                    appartient = true;

            }

            if (appartient)
                listesPb.add(_base.get(i));
            else
                listesNomsDejaFaits.add(_base.get(i));

        }

        if (listesPb.isEmpty())
            return;

        String msg = "Erreur valeurs incohérentes, Ã  la fois : ";
        for (int i = 0; i < listesPb.size();i++){
            msg+=listesPb.get(i).toString()+" et !" +listesPb.get(i).toString()+"; ";
        }
        msg+="\n1: ArrÃªter le programme\n2: Supprimer tous ses faits\n3: Choisir le fait Ã  garder";
        String reponse = Moteur.lireReponse(msg);
        if (reponse.contains("1"))
            throw new Exception("Valeurs incohérentes : Vrai/Faux en mÃªme temps dans la base des faits");
        else if (reponse.contains("2")){
            _base = listesNomsDejaFaits;
        }else{
            for (int i = 0; i < listesPb.size();i++){
                reponse = Moteur.lireReponse("--------------------\n\n1 : Supprimer "+listesPb.get(i).toString()+ "\n2 : Supprimer son contraire");
                if (reponse.contains("1")){
                    for (int j = 0;j < _base.size();j++)
                        if (_base.get(j).toString().equals(listesPb.get(i).toString())){
                            _base.remove(j);
                            j--;
                        }
                }else{
                    for (int j = 0;j < _base.size();j++)
                        if (_base.get(j).nom().equals(listesPb.get(i).nom()) && _base.get(j).estVrai() != listesPb.get(i).estVrai()){
                            _base.remove(j);
                            j--;
                        }
                }
            }
        }
        Moteur.print("Nouvelle base de faits : "+toString());
    }

    //on regarde si les mots clés/symboles ne sont pas dans le nom
    private void verifierNoms() throws Exception {
        ArrayList<Element> listesInterdites = new ArrayList<>();
        for (int i = 0; i < _base.size();i++)
            if (_base.get(i).nom().contains("!") || _base.get(i).nom().contains("ET") || 
            _base.get(i).nom().contains("->"))
                listesInterdites.add(_base.get(i));
                
        
        //cas d'arrÃªt
        if (listesInterdites.isEmpty())
            return;

        String msg = "Problème mots clés / symboles interdits dans des noms d'éléments de la base de faits.\n Il ne faut pas de '!', de 'ET' ou de '->' : Les éléments de problèmes : ";
        for (int i = 0; i < listesInterdites.size();i++)
            msg+=listesInterdites.get(i).nom()+" / ";
        msg +="1 : arrÃªter le programme\n2: Enlever ces symboles/noms interdits";

        if (Moteur.lireReponse(msg).contains("1"))
            throw new Exception("Caractères interdits dans les symboles / mots");
        else  {
            for (int i = 0; i < _base.size();i++){
                _base.set(i, new Element(_base.get(i).nom().replace("Et", "").replace("!", "").replace("->", ""), _base.get(i).estVrai(),0));
            }
        }

        msg = "Nouvelle base de faits : "+_base.toString();
        Moteur.print(msg);
            
    }

    public void verifierDoublons() throws Exception{
        ArrayList<Element> elementsTampon = new ArrayList<>();
        ArrayList<Element> doublons = new ArrayList<>();

        for (int i = 0; i < _base.size();i++)
            if (elementsTampon.contains(_base.get(i)))
                doublons.add(_base.get(i));
            else
                elementsTampon.add(_base.get(i));

        if (doublons.size() == 0)return;

        String msg = "Attention doublon(s) détecté(s) dans la base de faits : \n";
        for (int i = 0; i < doublons.size();i++){
            msg+=" / "+doublons.get(i).toString();
        }

        msg+="\n1 : pour enlever les doublons\n2 : pour ne pas lancer la simulation : \n";
        if (Moteur.lireReponse(msg).contains("1")){
            throw new Exception("Doublons dans la base de faits");
        }else{
            _base = elementsTampon;
        }
        Moteur.print("Nouvelle base de faits : "+toString());
        
    }
}
