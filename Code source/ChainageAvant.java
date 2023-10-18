public class ChainageAvant implements Strategie{
    

    public void executer(BaseDeFaits baseDeFaits, BaseDeRegles baseDeRegles, boolean trace, Tri tri){

        Chronometre.start();
        BaseDeFaits baseDeFaitsEnTampon = BaseDeFaits.copy(baseDeFaits);
        BaseDeRegles baseDeReglesEnTampon = BaseDeRegles.copy(baseDeRegles);

        boolean inf = true;
        int nbInf = 0;
        boolean dec = true;

        
        while (inf) {
            // R�initialisez inf à false au d�but de chaque it�ration
            inf = false;

            for (int i = 0; i < baseDeReglesEnTampon.taille(); i++) {
                baseDeReglesEnTampon = tri.trier(baseDeReglesEnTampon, baseDeFaitsEnTampon);
                dec = true;
                Regle regle = baseDeReglesEnTampon.avoirRegleParIndice(i);

                for (int j = 0; j < regle.taillePremice(); j++) {
                    Element premice = regle.avoirPremiceParIndice(j);
                    // V�rification si la pr�misse est satisfaite
                    if (!baseDeFaitsEnTampon.contient(premice) 
                    || (baseDeFaitsEnTampon.contient(premice) && 
                    baseDeFaitsEnTampon.avoirValeurFait(premice.nom()) != regle.avoirValeurPremice(premice.nom()))) {
                        dec = false; // La pr�misse n'est pas satisfaite, la r�gle ne peut pas être ex�cut�e
                        break;
                    }
                    
                }
                // Si toutes les pr�mises sont satisfaites, ex�cutez la r�gle
                if (dec) {
                    // Ajoutez le r�sultat de la r�gle à la base de faits
                    for (int k = 0; k < regle.tailleConsequent(); k++) {
                        if (!baseDeFaitsEnTampon.contient(regle.avoirConsequentParIndice(k))){
                            Element e = new Element(regle.avoirConsequentParIndice(k).nom(), regle.avoirConsequentParIndice(k).estVrai(), nbInf);
                            baseDeFaitsEnTampon.ajouterFait(e);
                            regle.setDecouvertTempsPremice(nbInf);
                            if (trace){
                                MoteurZeroPlus.print("\n--------- Nombre d'inf�rences : " +nbInf);
                                MoteurZeroPlus.print("\nOn a : "+regle.avoirPremices().toString()+" donc on utilise la r�gle : \n"+regle.toString()+" et on obtient : \n"+regle.avoirConsequents().toString()+" \nNouvelle base de faits : \n"+baseDeFaitsEnTampon.toString());
                            }
                        }
                        baseDeReglesEnTampon.enleverRegle(regle.nom());
                    }

                    inf = true; // Indique qu'au moins une r�gle a �t� ex�cut�e à cette it�ration
                    nbInf++; // Incr�mente le compteur de r�gles ex�cut�es
                }
            }
        }      
        Chronometre.stop();
        MoteurZeroPlus.print("R�sultat : Temps d'ex�cution : "+Chronometre.time()+" ms / Nombres d'inf�rences : "+nbInf+", \nOn a la base de faits : ");
        MoteurZeroPlus.print(baseDeFaitsEnTampon.toString());
    }
}
