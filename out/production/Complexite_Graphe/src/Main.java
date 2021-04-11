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
        Graphe g = new RandomGraphe(10,32);
       // g.export();
        System.out.println(g.toString());
          //System.out.println(g.MethodeSeq());

        GraphADJ g_adj = new GraphADJ(g);
       // System.out.println (g_adj.heuristiqueControle());
        System.out.println(g_adj.TabuSearch());
    }
}
