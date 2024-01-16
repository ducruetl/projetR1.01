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
        FileWriter file = null;
        ArrayList<String> l = new ArrayList<>();
        try {
            file = new FileWriter(nomFichier);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Depeche d : depeches){
            String max = "sans categorie";
            int cpt = 0;
            for(Categorie cat : categories){
                if(cpt < cat.score(d)){
                    cpt = cat.score(d);
                    max = cat.getNom();
                }
            }
            l.add(max);
            try{
                file.append(d.getId()+":"+max+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(Categorie cat : categories){
            int cpt = 0;
            int pourcent = 0;
            for(String s: l){
                if(!depeches.get(cpt).getCategorie().equalsIgnoreCase(s)){
                    pourcent++;
                }
                cpt++;
            }
            try{
                file.append(cat.getNom()+" :"+pourcent+"\n");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try {
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
        return resultat;

    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
    }

    public static int poidsPourScore(int score) {
        return 0;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {

    }

    public static void main(String[] args) {

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        for (int i = 0; i < depeches.size(); i++) {
            depeches.get(i).afficher();
        }
        Categorie cat1 = new Categorie("Environnement-Science", "./ENVIRONNEMENT-SCIENCE");
        Categorie cat2 = new Categorie("Economie", "./ECONOMIE");
        Categorie cat3 = new Categorie("Sport", "./SPORTS");
        Categorie cat5 = new Categorie("Culture", "./CULTURE");




//        Scanner sc = new Scanner(System.in);
//        System.out.println("String?");
//        String mot = sc.nextLine();
//        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(cat1.getLexique(), mot));
//        System.out.println(cat1.score(depeches.get(10)));
        ArrayList<Categorie> Categories = new ArrayList<>(Arrays.asList(cat1, cat2,cat3, cat5));
//        for(Categorie cat : Categories){
//            System.out.println(cat.getNom()+cat.score(depeches.get(10)));
//        }
//        System.out.println(depeches.get(10).getContenu());
        classementDepeches(depeches, Categories, "oui.txt");
    }
}