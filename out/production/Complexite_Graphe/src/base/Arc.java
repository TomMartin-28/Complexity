package base;

public class Arc {
    private Noeud source, cible;
    public Arc(Noeud x, Noeud y){
        source = x; cible = y;
    }

    public Noeud getCible() {
        return cible;
    }

    public Noeud getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Source: "+source.getId()+"/Cible: "+ cible.getId();
    }
}
