import java.util.ArrayList;

public class ChainageParPaquet implements Strategie{


    ArrayList<ArrayList<String>> _blocs = new ArrayList<>();

    public void setBlocs(ArrayList<ArrayList<String>> _blocs) {
        this._blocs = _blocs;
    }

    @Override
    public void executer(BaseDeFaits baseDeFaits, BaseDeRegles baseDeRegles, boolean trace, Tri tri) {
        BaseDeFaits baseDeFaitTampon = BaseDeFaits.copy(baseDeFaits);
        BaseDeRegles baseDeReglesTampon = BaseDeRegles.copy(baseDeRegles);

        if (_blocs.isEmpty()){
            Moteur.print("Pas de paquets.");
            return;
        }

        //on fais les calculs sur tous les blocs
        for (int i = 0;i < _blocs.size();i++){
            for (int j = 0; j < _blocs.get(i).size();j++){
                Regle r = baseDeReglesTampon.avoirRegleParNom(_blocs.get(i).get(j));
                
                //Si aucune règle avec ce nom, on laisse l'utilisateur choisir
                if (r == null){
                    String reponse = Moteur.lireReponse("La règle \""+_blocs.get(i).get(j) +"\" n'existe pas.\n1:Passer la règle\n2:Renommer la règle\n3:ArrÃªter le programme");
                    if (reponse.contains("1"))
                        continue;
                    else if (reponse.contains("2")){
                        while(baseDeReglesTampon.avoirRegleParNom(reponse) == null)
                            reponse = Moteur.lireReponse("\n\nLa règle de nom : '"+_blocs.get(i).get(j)+"' n'existe pas.\n.On a la base de règles : "+baseDeReglesTampon.toString()+"\nChoisir la règle (nom) :").trim();
                        _blocs.get(i).set(j, reponse);
                    }else
                        return;
                    
                }
                r = baseDeReglesTampon.avoirRegleParNom(_blocs.get(i).get(j));

                //ici on va vérifier si la base de faits vérifie les prémices de la règle
                boolean regleUtile = true;
                baseDeReglesTampon = tri.trier(baseDeReglesTampon, baseDeFaitTampon);
                for (int k = 0; k < r.taillePremice();k++){
                    if (!baseDeFaitTampon.contient(r.avoirPremiceParIndice(k))){
                        if (trace)
                            Moteur.print(" On a la règle "+r.nom()+" car :"+r.avoirPremiceParIndice(k).toString()+" n'est pas valide.\n");
                        regleUtile = false;
                    }
                }

                //si la règle peut etre utilisé on ajoute toutes ses conséquents Ã  la base de faits
                if (regleUtile){
                    String message = "La règle est utilisable : "+r.toString()+"\n On rajoute donc les conséquents : ";
                    for (int k = 0; k < r.tailleConsequent();k++){
                        if (!baseDeFaitTampon.contient(r.avoirConsequentParIndice(k))){
                            r.setDecouvertTempsPremice(i);
                            message+=" ; "+r.avoirConsequentParIndice(k).toString();
                            baseDeFaitTampon.ajouterFait(new Element(r.avoirConsequentParIndice(k).nom(), r.avoirConsequentParIndice(k).estVrai(), i));
                            baseDeFaitTampon.ajouterFait(r.avoirConsequentParIndice(k).clone());
                        }
                    }
                    if (trace)
                        Moteur.print(message);
                }
                
            }
        }
        
        Moteur.print("Chainage par paquets réussi avec les paquets :\n "+_blocs.toString()+"\nNouvelle base de faits : \n"+baseDeFaitTampon.toString());
    }


}