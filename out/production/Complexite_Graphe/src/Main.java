import base.Graphe;
import base.RandomGraphe;
import Tabu.k_Color_Tabu;
import backtrack.CouleurProblem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime=System.currentTimeMillis();   //Start time
        //Graphe g = new RandomGraphe(10,  0.6);
        //Graphe g = new Graphe("Classeur1.csv");
       // g.export();
        Graphe g = new Graphe("RandomGraphe.csv");
        System.out.println(g.toString());
       // System.out.println(g.MethodeSeq());
        //k_Color_Tabu kcT = new k_Color_Tabu(g);
        //System.out.println("The best chromatic number is: "+kcT.TabuSearch());
        //CouleurProblem Coloring = new CouleurProblem();
        //int[][] graph = Coloring.transformation(g);
        //int m = 2; // Nombre de couleurs
        //Coloring.graphColoring(graph, m);
        //long endTime=System.currentTimeMillis(); //End time
        //System.out.println("Program run time： "+(endTime-startTime)+"ms");
    }
}
