package base;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime=System.currentTimeMillis();   //Start time
        Graphe g = new RandomGraphe(10,  0.6);
        //Graphe g = new Graphe("Classeur1.csv");
       // g.export();
        System.out.println(g.toString());
        //System.out.println(g.MethodeSeq());
        //k_Color_Tabu kcT = new k_Color_Tabu(g);
        //System.out.println("The best chromatic number is: "+kcT.TabuSearch());

        CouleurProblem Coloring = new CouleurProblem();
        int m = 3; // Nombre de couleurs
        int[][] graph = Coloring.transformation(g);
        Coloring.graphColoring(graph, m);
        //long endTime=System.currentTimeMillis(); //End time
        //System.out.println("Program run time： "+(endTime-startTime)+"ms");
    }
}
