import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Classification {

    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        // { nomFichier existant } => { Séparation et placement dans la liste depeches
        // des différentes dépêches présentes dans le fichier nomFichier }

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
        // { } =>
        // { * Crée un fichier nomFichier contenant, pour chaque dépêche, la catégorie la plus probable en fonction
        // du score attribué pour chaque catégorie.
        //   * Affiche le pourcentage de précision pour chaque catégorie et la moyenne de ces pourcentages. }

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
                file.append(d.getId() + ":" + max + "\n");
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
                file.append(cat.getNom() + " :" + pourcent + "%\n");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try{
            file.append("MOYENNE :" + (moyenne/categories.size()) + "%\n");
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
        // { } => { Renvoie une liste de PaireChaineEntier contenant de manière unique tout les mots apparaissant au
        // moins une fois dans les dépêches de la catégorie spécifiée }

        Set<String> mots = new HashSet<>();
        ArrayList<PaireChaineEntier> dictionnaire = new ArrayList<>();

        for (Depeche d : depeches) {
            if(d.getCategorie().equals(categorie)){

                mots.addAll(d.getMots());

                }
            }

        for (String mot : mots) {

            dictionnaire.add(new PaireChaineEntier(mot, 0));

        }



        return dictionnaire;
    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
        // { } => { Attribue à chaque mot contenu dans la liste dictionnaire, un score calculé en fonction de son
        // nombre d'apparitions dans les différentes catégories }

        for (Depeche d : depeches) {

            for (int i = 0; i < dictionnaire.size(); i++) {

                if (d.getMots().contains(dictionnaire.get(i).getChaine())) {

                    for (String mot : d.getMots()) {
                        if (dictionnaire.get(i).getChaine().equals(mot) && d.getCategorie().equals(categorie)) {
                            dictionnaire.set(i, new PaireChaineEntier(dictionnaire.get(i).getChaine(), dictionnaire.get(i).getentier() + 1));
                        } else if (dictionnaire.get(i).getChaine().equals(mot)) {
                            dictionnaire.set(i, new PaireChaineEntier(dictionnaire.get(i).getChaine(), dictionnaire.get(i).getentier() - 1));
                        }
                    }

                }

            }

        }

    }

    public static int poidsPourScore(int score) {
        // { } => { Renvoie un poids en fonction de score reçu }

        int poids = 0;

        if(score >= 2){poids = 3;}
        else if(score >= 1){poids = 2;}
        else if(score >= 0){poids = 1;}

        return poids;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {
        // { } => { Génère un fichier nomFichier contenant le lexique (mot + poids) d'une catégorie donnée
        // à partir des données de la liste depeches }

        ArrayList<PaireChaineEntier> dico = initDico(depeches, categorie);
        calculScores(depeches, categorie, dico);

            try {
                FileWriter file = new FileWriter(nomFichier);

                for(PaireChaineEntier pe : dico) {

                    file.write(pe.getChaine() + ":" + poidsPourScore(pe.getentier()) + "\n");

                }

                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");
        ArrayList<Depeche> test = lectureDepeches("./test.txt");

        for (int i = 0; i < depeches.size(); i++) {
            depeches.get(i).afficher();
        }

        // Génération automatique des lexiques et attribution des lexiques aux
        // différentes catégories ( Partie 2 )

        generationLexique(depeches, "ENVIRONNEMENT-SCIENCES", "ENVIRONNEMENT-SCIENCES_V2");
        generationLexique(depeches, "ECONOMIE", "ECONOMIE_V2");
        generationLexique(depeches, "SPORTS", "SPORTS_V2");
        generationLexique(depeches, "POLITIQUE", "POLITIQUE_V2");
        generationLexique(depeches, "CULTURE", "CULTURE_V2");

        Categorie cat1 = new Categorie("ENVIRONNEMENT-SCIENCES", "./ENVIRONNEMENT-SCIENCES_V2");
        Categorie cat2 = new Categorie("ECONOMIE", "./ECONOMIE_V2");
        Categorie cat3 = new Categorie("SPORTS", "./SPORTS_V2");
        Categorie cat4 = new Categorie("POLITIQUE", "./POLITIQUE_V2");
        Categorie cat5 = new Categorie("CULTURE", "./CULTURE_V2");

        // Attribution des lexiques aux différentes catégories ( Partie 1 )
        // Laisser commenté pour exécuter la partie 2

//        Categorie cat1 = new Categorie("ENVIRONNEMENT-SCIENCES", "./ENVIRONNEMENT-SCIENCES");
//        Categorie cat2 = new Categorie("ECONOMIE", "./ECONOMIE");
//        Categorie cat3 = new Categorie("SPORTS", "./SPORTS");
//        Categorie cat4 = new Categorie("POLITIQUE", "./POLITIQUE");
//        Categorie cat5 = new Categorie("CULTURE", "./CULTURE");

//        Scanner sc = new Scanner(System.in);
//        System.out.println("String?");
//        String mot = sc.nextLine();
//        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(cat1.getLexique(), mot));
//        System.out.println(cat1.score(depeches.get(10)));

//        for(Categorie cat : Categories){
//            System.out.println(cat.getNom()+cat.score(depeches.get(10)));
//        }
//        System.out.println(depeches.get(10).getContenu());

        ArrayList<Categorie> Categories = new ArrayList<>(Arrays.asList(cat1, cat2,cat3,cat4, cat5));

        classementDepeches(depeches, Categories, "testDepeches.txt");
        classementDepeches(test, Categories, "testTest.txt");

        System.out.println("Temps d'exécution : " + (System.currentTimeMillis() - startTime));

    }
}