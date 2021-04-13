package backtrack;


import base.Graphe;
import base.Arc;
import base.Noeud;

import java.util.Iterator;
import java.util.LinkedList;

public class CouleurProblem {
    int S = 4;
    int[] couleur;


    /* Vérifier si on peut attribuer une couleur à un sommet donné en considérant les
    couleurs des sommets adjacents
    */
    boolean Solution(int s, int graph[][], int[] couleur, int c) {
        for (int i = 0; i < S; i++)
            if (graph[s][i] == 1 && c == couleur[i])
                return false;
        return true;
    }

    /* Fonction récursive pour essayer différentes configurations de couleurs */
    boolean graphColoringUtil(int graph[][], int m, int color[], int s) {
        /* Retourne True si tous les sommets ont des couleurs */
        if (s == S)
            return true;



        /* Essayer différentes couleurs à un somment donné*/
        for (int c = 1; c <= m; c++) {
            /* Verifier si la couleur c peut etre attribuée au sommet s*/
            if (Solution(s, graph, color, c)) {
                color[s] = c;



                /* Attribuer des couleurs aux sommets restants grâce à la fonction récursive */
                if (graphColoringUtil(graph, m, color, s + 1))
                    return true;



/* Si on ne parvient pas à avoir une solution avec la couleur attribuée,
on la retire */
                color[s] = 0;
            }
        }


        return false;
    }



/* Fonction pour resoudre le probleme de coloraiton de graphes qui utilise principalement
graphColoringUtil(). Elle retourne false si le nombre maximal de couleurs
ne peut etre attribue ou retourne true and affiche ainsi les couleurs des sommets
*/


    public boolean graphColoring(int graph[][], int m) {
// Initialise toutes les valeurs de couleurs
// à 0.


        couleur = new int[S];
        for (int i = 0; i < S; i++)
            couleur[i] = 0;



/* Vérifier si on peut colorier les sommets du graphe avec
la fonction graphColoringUtil.
*/
        if (!graphColoringUtil(graph, m, couleur, 0)) {
            System.out.println("Pas de solution");
            return false;
        }


// Affiche la solution
        printSolution(couleur);
        return true;
    }


    /* Fonction qui affiche la solution */
    void printSolution(int color[]) {
        System.out.println("Il ya une solution: Les couleurs suivantes sont attribuées");
        for (int i = 0; i < S; i++)
            System.out.print(" " + color[i] + " ");
    }

    public int[][] transformation(Graphe g) {
        S = g.getNoeuds().size();
        int[][] graphe = new int[g.getNumNoeud()][g.getNumNoeud()];
        for (int i = 0; i < g.getNumNoeud(); i++) {
            Noeud node_s = g.getNoeud(i);
            LinkedList<Arc> arcList = node_s.getSuccesseurs();
            for (int j = 0; j < g.getNumNoeud(); j++) {
                if (i == j) {
                    graphe[i][j] = 1;
                } else {
                    Iterator<Arc> it = arcList.iterator();
                    while (it.hasNext()) {
                        Arc arc = it.next();
                        if (arc.getCible().getId() == j) {
                            graphe[i][j] = 1;
                        } else {
                            graphe[i][j] = 0;
                        }
                    }
                }

            }
        }
        return graphe;
    }



    // Test du programme
    public static void main(String args[]) {
        CouleurProblem Coloring = new CouleurProblem();
/*
(0)---(1)
| / |
| / |
| / |
(3)---(2)
*/
        int graph[][] = {
                {1, 1, 0, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1},
                {1, 1, 1, 1},
        };





        int m = 3; // Nombre de couleurs
        Coloring.graphColoring(graph, m);
    }
}
