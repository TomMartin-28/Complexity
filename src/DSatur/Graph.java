package DSatur;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Not oriented graph
 */
public class Graph {
    public List<Node> nodes;

    public Graph() {
        nodes = new ArrayList<>();
    }
    public Graph(String file) throws IOException {
        nodes = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get("Classeur1.csv"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            List<CSVRecord> list = csvParser.getRecords();
            for (int i = 0; i < list.size(); i++) {
                int source = -1;
                int cible = -1;
                if (i == 0) {
                    if (list.get(i).get(0).charAt(1) == '0')
                        source = 0;
                    else if (list.get(i).get(0).charAt(1) == '1')
                        source = 1;
                    else if (list.get(i).get(0).charAt(1) == '2')
                        source = 2;
                    else if (list.get(i).get(0).charAt(1) == '3')
                        source = 3;
                    else if (list.get(i).get(0).charAt(1) == '4')
                        source = 4;
                    else if (list.get(i).get(0).charAt(1) == '5')
                        source = 5;
                    else if (list.get(i).get(0).charAt(1) == '6')
                        source = 6;
                    else if (list.get(i).get(0).charAt(1) == '7')
                        source = 7;
                    else if (list.get(i).get(0).charAt(1) == '8')
                        source = 8;
                    else if (list.get(i).get(0).charAt(1) == '9')
                        source = 9;
                }
                if (list.get(i).get(0).compareTo("0") == 0)
                    source = 0;
                else if (list.get(i).get(0).compareTo("1") == 0)
                    source = 1;
                else if (list.get(i).get(0).compareTo("2") == 0)
                    source = 2;
                else if (list.get(i).get(0).compareTo("3") == 0)
                    source = 3;
                else if (list.get(i).get(0).compareTo("4") == 0)
                    source = 4;
                else if (list.get(i).get(0).compareTo("5") == 0)
                    source = 5;
                else if (list.get(i).get(0).compareTo("6") == 0)
                    source = 6;
                else if (list.get(i).get(0).compareTo("7") == 0)
                    source = 7;
                else if (list.get(i).get(0).compareTo("8") == 0)
                    source = 8;
                else if (list.get(i).get(0).compareTo("9") == 0)
                    source = 9;


                if (list.get(i).get(1).compareTo("0") == 0)
                    cible = 0;
                else if (list.get(i).get(1).compareTo("1") == 0)
                    cible = 1;
                else if (list.get(i).get(1).compareTo("2") == 0)
                    cible = 2;
                else if (list.get(i).get(1).compareTo("3") == 0)
                    cible = 3;
                else if (list.get(i).get(1).compareTo("4") == 0)
                    cible = 4;
                else if (list.get(i).get(1).compareTo("5") == 0)
                    cible = 5;
                else if (list.get(i).get(1).compareTo("6") == 0)
                    cible = 6;
                else if (list.get(i).get(1).compareTo("7") == 0)
                    cible = 7;
                else if (list.get(i).get(1).compareTo("8") == 0)
                    cible = 8;
                else if (list.get(i).get(1).compareTo("9") == 0)
                    cible = 9;

                addNode(source);
                addNode(cible);
                addEdge(source, cible);
            }
        }
    }

    public boolean addNode(int n) {
        if (getNode(n) == null) {
            return nodes.add(new Node(n));
        } else return false;
    }

    private Node getNode(int n) {
        Node res = null;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).value == n)
                res = nodes.get(i);
        }
        return res;
    }

    public boolean addEdge(int a, int b) {
        Node a1 = getNode(a);
        Node b1 = getNode(b);
        if (!a1.neighbors.contains(b1))
            a1.neighbors.add(b1);
        if (!b1.neighbors.contains(a1))
            b1.neighbors.add(a1);
        return true;
    }

    public void DSatur() {
        long start = System.currentTimeMillis();
        ArrayList<Node> coloredNodes = new ArrayList<>();
        Set<Integer> colorUsed = new HashSet<>();
        for (Node n : nodes) {
            n.color = 0;
        }

        // getting degrees of nodes and initialize their degree attribute
        Node maxDegree = MaxDegrees();

        // getting the node with the bigger degree and put his color at 1
        maxDegree.color = 1;
        coloredNodes.add(maxDegree);
        colorUsed.add(1);

        while (!coloredNodes.containsAll(nodes)) {
            // update the dsat attribute of each node
            for (Node p : nodes) {
                DSAT(p);
            }

            // getting node with the biggest dsat attribute
            int dsat = -1;
            ArrayList<Node> dsatNode = new ArrayList<>();
            for (Node n : nodes) {
                //System.out.println("node: "+n);
                if(n.color == 0) {
                    if (n.dsat > dsat){
                        dsat = n.dsat;
                        dsatNode.add(n);
                    }
                    else if (n.dsat == dsat)
                        dsatNode.add(n);
                }
            }

            // getting the node with the biggest degree if there are equalities
            Node biggest = new Node(-1);
            if (dsatNode.size() > 1) {
                for (Node n : dsatNode) {
                    int degree = -1;
                    if (n.degree > degree) {
                        degree = n.degree;
                        biggest = n;
                    }
                }
            } else biggest = dsatNode.get(0);

            /**
             * Doesn"t work
             */
            // searching the smallest color of neighbors node of the "biggest" node (variable biggest)
            /*int finalColor = Integer.MAX_VALUE;
            for (Node n : biggest.neighbors) {
                if (n.color != 0) {
                    colorUsed.add(n.color);
                    if (n.color < finalColor)
                        finalColor = n.color;
                }
            }
            biggest.color = finalColor+1;
            while(colorUsed.contains(biggest.color)){
                biggest.color++;
            }
            coloredNodes.add(biggest);
            */

            // search the smallest color available
            Set<Integer> neighborsColors = new HashSet<>();
            int finalColor = 1;
            for (Node n : biggest.neighbors) {
                if (n.color != 0) {
                    neighborsColors.add(n.color);
                }
            }
            while(neighborsColors.contains(finalColor)) finalColor++;
            biggest.color = finalColor;
            colorUsed.add(biggest.color);
            coloredNodes.add(biggest);
        }
        long end = System.currentTimeMillis();
        System.out.println("Temps de calcul: " + (end - start));
        System.out.println("nombre de couleurs: "+colorUsed.size());
    }

    /**
     * @return a list of pairs of nodes with their degrees (number of link with others nodes)
     * and initialize the number of degree of a node
     */
    private Node MaxDegrees() {
        Node res = new Node(-1);
        int size = -1;
        for (Node n : nodes) {
            int num = n.neighbors.size();
            n.degree = num;
            if(num > size){
                size = num;
                res = n;
            }
        }
        return res;
    }

    /**
     * @return the number of different colors in adjacent nodes
     * and initialize the dsat number of a node (which is in parameter)
     */
    private int DSAT(Node n) {
        int res = 0;
        for (Node neighbor : n.neighbors) {
            if (neighbor.color > 0) {
                res++;
            }
        }
        n.dsat = res;
        return res;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }

    public void export() {
        String buff = "Source,Target\n";
        String sep = ",";
        for (Node n : nodes) {
            for (Node neighbor : n.neighbors) {
                buff += n.value + sep +
                        neighbor.value + "\n";
            }
        }
        File outputFile = new File(this.getClass().getName() + ".csv");
        FileWriter out;
        try {
            out = new FileWriter(outputFile);
            out.write(buff);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
