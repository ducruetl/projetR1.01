import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


class XmlTotxt {

    public static void Extract(String nomFichier) {
        try {
            // lecture du fichier d'entrée
            FileWriter write;
            try {
                write = new FileWriter("POLITIQUE_RSS.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);
            ArrayList<String> resultat = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                for(int n = 0; n < ligne.length();n++){
                    int index = -1;
                    index = ligne.indexOf("<", n);
                    if(index != -1 && ligne.substring(index+1,ligne.indexOf(">", index+1)).equals("description")){ // Il peut y avoir autre balise apres description
                        resultat.add(ligne.substring(index+14, ligne.indexOf("<", index+14)));
                    }

                }
            }
            scanner.close();

            for (String r : resultat) {

                write.append(r + "\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        Extract("politique.rss.txt");
        System.out.println();

    }

}
