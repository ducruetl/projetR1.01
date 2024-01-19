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
    public static int indicePourChaineDicho(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return iPCDichoWk(listePaires,0,listePaires.size()-1,chaine);
    }
    public static int iPCDichoWk(ArrayList<PaireChaineEntier> lst, int deb, int fin, String ch){
        if (fin >= deb){
            int mid = deb + (fin - deb)/2;
            if (lst.get(mid).getChaine().equals(ch)){
                return mid;
            }
            if (lst.get(mid).getChaine().compareTo(ch) > 0){
                return iPCDichoWk(lst, deb, mid-1, ch);
            }else{
                return iPCDichoWk(lst, mid+1, fin, ch);
            }
        }
        return -1;
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


    public static int entierPourChaineDicho(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return ePCDichowk(listePaires,0,listePaires.size()-1,chaine);
    }
    public static int ePCDichowk(ArrayList<PaireChaineEntier> lst, int deb, int fin, String ch){
        if (fin >= deb){
            int mid = deb + (fin - deb)/2;
            if (lst.get(mid).getChaine().equals(ch)){
                return lst.get(mid).getentier();
            }
            if (lst.get(mid).getChaine().compareTo(ch) > 0){
                return iPCDichoWk(lst, deb, mid-1, ch);
            }else{
                return iPCDichoWk(lst, mid+1, fin, ch);
            }
        }
        return -1;
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

    public static int compromis_trie(ArrayList<PaireChaineEntier> listePaires){return listePaires.get(listePaires.size()/2).getentier();}
    // en admettant que c'est une liste trié, si on prend la valeur au milieu on pourrait conculre a une moyenne aproximative?

}
