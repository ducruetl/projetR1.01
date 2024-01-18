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

    public int compareTo(PaireChaineEntier pair){
        if (this.entier > pair.entier) {
            return 1;
        }
        else if (this.entier < pair.entier) {
            return -1;
        }
        else{
            return this.chaine.compareTo(pair.chaine);
        }
    }

    @Override
    public String toString(){
        return entier+" :"+ chaine+"\n";
    }
}
