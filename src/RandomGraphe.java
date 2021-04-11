import java.util.ArrayList;
import java.util.LinkedList;

public class RandomGraphe extends Graphe {
    private int v;
    private int w;
    ArrayList<Arc> arcs;

    /**
     * Génération d'un graphe sous le modèle de Gilbert
     *
     * @param n nombre de noeuds
     * @param p probabilité des arcs
     */
    public RandomGraphe(int n, double p) {
        if ((n > 0) && (p >= 0) && (p <= 1)) {
            setNoeuds(new LinkedList<Noeud>());
            v = 1;
            w = -1;
            while (v < n) {
                double r = Math.random();
                w = w + 1 + (int) ((Math.log(1 - r)) / Math.log(1 - p));
                while ((w >= v) & (v < n)) {
                    w -= v;
                    v++;
                }
                if (v < n) {
                    addNoeud(v);
                    addNoeud(w);
                    addArc(v, w);
                }
            }
        }
    }

    public RandomGraphe(int n, int m) {
        double deuxParmisN = ((n * (n - 1)) / 2);
        arcs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            addNoeud(i);
        }
        if ((n > 0) && (m >= 0) && (m <= deuxParmisN)) {
            for (int i = 0; i < m - 1; i++) {
                int r;
                Arc Ar;
                int[][] bijections = bijection((int)deuxParmisN);
                do {
                    r = (int) (Math.random() * (deuxParmisN));

                    Ar = new Arc(getNoeud(bijections[r][0]), getNoeud(bijections[r][1]));
                }
                while (getNoeud(bijections[r][0]).getSuccesseurs().contains(Ar));
                addArc(bijections[r][0], bijections[r][1]);
            }
        }
    }

    /**
     * Ne fonctionne pas
     */
    /*public RandomGraphe(int n, int m){
        double deuxParmisN = ((n * (n - 1)) / 2);
        int[][] bijections = bijection((int)deuxParmisN);
        for (int i = 0; i < n; i++) {
            addNoeud(i);
        }
        for (int i = 0; i < m-1; i++) {
            int r = (int) (Math.random() * (deuxParmisN - i + 1) + i);
            Arc Ar = new Arc(getNoeud(bijections[r][0]), getNoeud(bijections[r][1]));
            //if(getNoeud(bijections[r][0]).getSuccesseurs().contains(Ar))
        }
    }*/

    private int[][] bijection(int j) {
        int[][] tab = new int[j][2];
        for (int i = 0; i < j; i++) {
            int v = (int) (1 + Math.floor(-(0.5) + Math.sqrt(0.25 + 2 * i)));
            int w = (i - ((v * (v - 1)) / 2));
                tab[i][0] = v;
                tab[i][1] = w;
        }
        return tab;
    }

}
