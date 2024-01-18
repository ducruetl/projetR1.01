import java.awt.desktop.PreferencesEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class UtilitairePaireChaineEntier {

    public static void triRapide(ArrayList<PaireChaineEntier> listePaires){
        triRapidewk(listePaires,0, listePaires.size()-1);
    }

    public static void triRapidewk(ArrayList<PaireChaineEntier> listePaires, int deb, int fin){
        if (deb < fin){
            int pivot = triRapidePartition(listePaires , deb ,fin);
            triRapidewk(listePaires , deb , pivot-1);
            triRapidewk(listePaires , pivot+1 , fin);
        }
    }
    static int triRapidePartition(ArrayList<PaireChaineEntier> listePaires , int debut ,int fin){
        PaireChaineEntier pivot = listePaires.get(debut);
        int i , cpt = debut;
        for ( i = debut+1 ; i <=fin ; i++){
            if (listePaires.get(i).compareTo(pivot)<0 ){
                cpt +=1;
                Collections.swap(listePaires, cpt, i);
            }
        }
        Collections.swap(listePaires , debut , cpt);
        return cpt;
    }



    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int cpt = 0;
        for(PaireChaineEntier p : listePaires){
            if(p.getChaine().equals(chaine)){
                break;
            }
            cpt++;
        }
        if(cpt == listePaires.size()){
            return -1;
        }
        return cpt;
    }

    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int x = 0;
        for(PaireChaineEntier e : listePaires){
            if(e.getChaine().equals(chaine)){
                x = e.getentier();
            }
        }

        return x;
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        PaireChaineEntier max = listePaires.get(0);     // la plaus grande valeur etant trois un parcour en entier n'est pas forcement necessaire.
        for(PaireChaineEntier e: listePaires){
            if(e.getentier()>max.getentier()){
                max = e;
            }
        }
        return max.getChaine();
    }

    public static String chaineMaxSpe(ArrayList<PaireChaineEntier> listePaires) {
        PaireChaineEntier max = listePaires.get(0);     // la plaus grande valeur etant trois un parcour en entier n'est pas forcement necessaire.
        try{
            for(PaireChaineEntier e: listePaires){
                if(e.getentier()==3){
                    return e.getChaine();
                }
            }
            throw new IllegalArgumentException("le max n'est pas 3, pire des cas, passage sur chaineMax sans erreur.");
        }catch (IllegalArgumentException e){
            return (chaineMax(listePaires));
        }
    }


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        int x = 0;
        for(PaireChaineEntier e : listePaires){
            x += e.getentier();
        }

        return x/listePaires.size();
    }

}
