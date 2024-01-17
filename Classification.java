import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Classification {

    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        //creation d'un tableau de dépêches
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String id = ligne.substring(3);
                ligne = scanner.nextLine();
                String date = ligne.substring(3);
                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);
                ligne = scanner.nextLine();
                String lignes = ligne.substring(3);
                while (scanner.hasNextLine() && !ligne.equals("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equals("")) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
                Depeche uneDepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }


    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        FileWriter file;
        ArrayList<String> listeDepeche = new ArrayList<>();
        try {
            file = new FileWriter(nomFichier);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

// Associe à chaque dépêche, un score par catégorie en fonction des mots du lexique pour déterminer la catégorie la plus probable

        for(Depeche d : depeches){
            String max = "sans categorie";
            int cpt = 0;
            for(Categorie cat : categories){
                if(cpt < cat.score(d)){
                    cpt = cat.score(d);
                    max = cat.getNom();
                }
            }
            listeDepeche.add(max);
            try{
                file.append(d.getId()+":"+max+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        float moyenne = 0;
        for(Categorie cat : categories){
            int cpt = 0;
            int pourcent = 0;
            for(String s: listeDepeche){
                if(s.equalsIgnoreCase(cat.getNom()) & s.equalsIgnoreCase(depeches.get(cpt).getCategorie())){
                    pourcent++;
                }
                cpt++;
            }
            moyenne += pourcent;
            try{
                file.append(cat.getNom()+" :"+pourcent+"%\n");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            file.append("MOYENNE :"+moyenne/categories.size()+"%\n");
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();

        for (Depeche d : depeches) {
            if(d.getCategorie().equals(categorie)){
                for(String mot : d.getMots()){
                    if(!resultat.contains(new PaireChaineEntier(mot, 0))){
                        resultat.add(new PaireChaineEntier(mot, 0));
                    }
                }
            }
        }
        return resultat;
    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
         for(int i=0;i < dictionnaire.size();i++){
             for (Depeche d : depeches) {
                 for(String mot: d.getMots()){
                     if(dictionnaire.get(i).getChaine().equals(mot) & d.getCategorie().equals(categorie)){
                        dictionnaire.set(i,new PaireChaineEntier(dictionnaire.get(i).getChaine(),dictionnaire.get(i).getentier()+1));
                     }
                     else if(dictionnaire.get(i).getChaine().equals(mot)){
                         dictionnaire.set(i,new PaireChaineEntier(dictionnaire.get(i).getChaine(),dictionnaire.get(i).getentier()-1));
                     }
                 }
             }
         }
    }

    public static int poidsPourScore(int score) {
        int poid = 0;

        if(score > 0){poid = 1;}
        if(score > 2){poid =  2;}
        if(score > 5){poid =  3;}

        return poid;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {
        ArrayList<PaireChaineEntier> dico = initDico(depeches, categorie);
        calculScores(depeches, categorie, dico);
        for(PaireChaineEntier pe : dico){
            try {
                FileWriter file = new FileWriter(nomFichier);
                file.write(pe.getChaine() + ":" + poidsPourScore(pe.getentier()) + "\n");
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        for (int i = 0; i < depeches.size(); i++) {
            depeches.get(i).afficher();
        }
        Categorie cat1 = new Categorie("Environnement-Sciences", "./ENVIRONNEMENT-SCIENCE");
        Categorie cat2 = new Categorie("Economie", "./ECONOMIE");
        Categorie cat3 = new Categorie("Sports", "./SPORTS");
        Categorie cat4 = new Categorie("Politique", "./POLITIQUE");
        Categorie cat5 = new Categorie("Culture", "./CULTURE");





//        Scanner sc = new Scanner(System.in);
//        System.out.println("String?");
//        String mot = sc.nextLine();
//        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(cat1.getLexique(), mot));
//        System.out.println(cat1.score(depeches.get(10)));
        ArrayList<Categorie> Categories = new ArrayList<>(Arrays.asList(cat1, cat2,cat3,cat4, cat5));
//        for(Categorie cat : Categories){
//            System.out.println(cat.getNom()+cat.score(depeches.get(10)));
//        }
//        System.out.println(depeches.get(10).getContenu());
        classementDepeches(depeches, Categories, "oui.txt");
    }
}