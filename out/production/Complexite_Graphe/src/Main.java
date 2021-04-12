import DSatur.Graph;
import DSatur.RandomGraph;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /*Graph g = new Graph();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addEdge(0,1);
        g.addEdge(1,2);
        g.addEdge(2,4);
        g.addEdge(0,4);
        g.addEdge(0,3);
        g.addEdge(3,4);*/
        Graph g = new RandomGraph(2000, 0.5);
        g.DSatur();
    }
}
