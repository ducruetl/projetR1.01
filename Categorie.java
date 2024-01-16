import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    private String nom; // le nom de la catégorie p.ex : sport, politique,...
    private ArrayList<PaireChaineEntier> lexique; //le lexique de la catégorie


    // constructeur
    public Categorie(String nom, String cat) {
        this.nom = nom;
        this.lexique = new ArrayList<>();
        initLexique(cat);
    }


    public String getNom() {
        return nom;
    }


    public  ArrayList<PaireChaineEntier> getLexique() {
        return lexique;
    }


    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {

        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                int index = ligne.indexOf(":");
                String mot = ligne.substring(0, index);
                int poid = Integer.valueOf(ligne.substring(index+1));
                PaireChaineEntier pair = new PaireChaineEntier(mot, poid);
                this.lexique.add(pair);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        int score = 0;
        for(PaireChaineEntier e : this.lexique){ // peut etre amelioré en modifiant e pour qu'i pcharge uniquement les chaines en memeoire ou pas car j'utilise pas get chiane entier
            for(String mot: d.getMots()){
                if(e.getChaine().equalsIgnoreCase(mot)){
                    score += e.getentier();
                }
            }
        }
        return score;
    }


}
