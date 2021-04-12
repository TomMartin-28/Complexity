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
        Graphe g = new RandomGraphe(10,  34);
       // g.export();
        System.out.println(g.toString());
          //System.out.println(g.MethodeSeq());

        k_Color_Tabu kcT = new k_Color_Tabu(g);
        System.out.println("The best chromatic number is: "+kcT.TabuSearch());
    }
}
