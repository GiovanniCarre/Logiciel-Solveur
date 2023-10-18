import java.util.ArrayList;

public class ChainageArriere implements Strategie{
    Element _objectif;
    private int _nbInf;

    public void setObjectif(Element e){
        this._objectif = e;
    }

    public void executer(BaseDeFaits baseDeFaits, BaseDeRegles baseDeRegles, boolean trace, Tri tri){
        _nbInf = 0;

        BaseDeFaits baseDeFaitsEnTampon = BaseDeFaits.copy(baseDeFaits);
        BaseDeRegles baseDeReglesTampon = BaseDeRegles.copy(baseDeRegles);

        if (_objectif == null)
            _objectif = new Element(Moteur.lireReponse("Question à poser : \nEntrez directement le fait (par exemple 'A' ou 'malDeTete')\nEt validez avec entr�e: "));
        
        boolean result = executerRecursif(baseDeFaitsEnTampon, baseDeReglesTampon, _objectif, trace, tri);
        if (result)   Moteur.print( this._objectif.toString() + " a �t� v�rifi�, avec un retour positif");
        else          Moteur.print( this._objectif.toString() + " n'a pas �t� r�solu");
    }

    public boolean executerRecursif(BaseDeFaits baseDeFaits, BaseDeRegles baseDeRegles, Element b, boolean trace, Tri tri){
        if (trace) Moteur.print("Base de faits: "+baseDeFaits.toString());
        boolean dem = false;
        _nbInf++;
        
        //1er cas
        if (baseDeFaits.contient(b)) {
            dem = true;
            if (trace) Moteur.print("Base de fait contient: "+b.toString());
        }

        baseDeRegles = tri.trier(baseDeRegles, baseDeFaits);
        //2nd cas, v�rifier si b est cons�quent d'une des r�gles de BR
        for (int i = 0; i < baseDeRegles.taille() && !dem; i++) {
            Regle r = baseDeRegles.avoirRegleParIndice(i);
            //verifier si b est en consequent
             if (r.consequentContient(b)){
                
                //v�rifier si les pr�misses d'une r�gle avec b en cons�quent sont connues
                dem = verif(r.avoirPremicesListe(), baseDeFaits, baseDeRegles, tri);
                if (dem && trace) Moteur.print("La regle "+r.toString()+" contient "+b.toString()+" en cons�quent et ses pr�mices sont v�rifi�s");
            }
        }

        //3eme cas, demander b
        if (!dem){
            String reponse = Moteur.lireReponse("\n"+b.toString()+" est non d�fini, pouvez-vous nous aider ?\n0: Il est faux\n1: Il est vrai\n2: Je ne sais pas");
            dem = (reponse.contains("1"));
        }

        if (dem){
            baseDeFaits.ajouterFait(new Element(b.nom(), b.estVrai(), _nbInf));
        }


        return dem;
    }



    public boolean verif(ArrayList<Element> B, BaseDeFaits BF, BaseDeRegles BR, Tri tri){
        boolean ver = true;

        for (int i = 0; i < B.size() && ver; i++){
            ver = executerRecursif(BF, BR, B.get(i), ver, tri);
        }
        
        return ver;
    }
}

