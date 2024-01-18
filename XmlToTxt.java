import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class XmlToTxt {

    public static void extract(String nomFichier) {
        try (FileInputStream file = new FileInputStream(nomFichier);
             Scanner scanner = new Scanner(file);
             FileWriter writer = new FileWriter("POLITIQUE_RSS.txt", true)) {

            ArrayList<String> resultat = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                int index = ligne.indexOf("<description");

                while (index != -1) {
                    int closingTagIndex = ligne.indexOf(">", index);
                    String descriptionContent = ligne.substring(closingTagIndex + 1, ligne.indexOf("</description", closingTagIndex + 1));
                    descriptionContent = descriptionContent.replace("&nbsp;", " ");
                    resultat.add(descriptionContent);

                    StringBuilder allDescriptionContent = new StringBuilder();
                    allDescriptionContent.append(descriptionContent);

                    while (ligne.indexOf("</description", closingTagIndex + 1) == -1 && scanner.hasNextLine()) {
                        ligne = scanner.nextLine();
                        ligne = ligne.replace("&nbsp;", " ");
                        allDescriptionContent.append(ligne);
                    }

                    System.out.println(allDescriptionContent.toString());

                    index = ligne.indexOf("<description", closingTagIndex + 1);
                }
            }

            for (String r : resultat) {
                writer.append(r + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        extract("politique.rss.txt");
    }
}
