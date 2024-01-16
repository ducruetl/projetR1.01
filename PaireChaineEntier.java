public class PaireChaineEntier {
    private String chaine;
    private int entier;

    // constructeur:
    public PaireChaineEntier(String chaine, int entier) {
        this.chaine = chaine;
        this.entier = entier;
    }

    public String getChaine(){return chaine;}
    public int getentier(){return entier;}

    @Override
    public String toString(){
        return chaine+"\n";
    }
}
