package DSatur;

import java.util.LinkedList;

public class RandomGraph extends Graph{
    private int v;
    private int w;
    /**
     * Génération d'un graphe sous le modèle de Gilbert
     *
     * @param n nombre de noeuds
     * @param p probabilité des arcs
     */
    public RandomGraph(int n, double p) {
        super();
        if ((n > 0) && (p >= 0) && (p <= 1)) {
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
                    addNode(v);
                    addNode(w);
                    addEdge(v, w);
                }
            }
        }
    }


}
