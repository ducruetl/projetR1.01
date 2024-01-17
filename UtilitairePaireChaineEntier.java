import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


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


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        int x = 0;
        for(PaireChaineEntier e : listePaires){
            x += e.getentier();
        }

        return x/listePaires.size();
    }

}
