package backtrack;

import DSatur.Graph;
import DSatur.Node;
import DSatur.RandomGraph;

import java.util.List;

public class Backtrack {
    Graph g;
    int initialNumColor;
    public Backtrack(Graph g, int numColor){
        this.g = g;
        this.initialNumColor = numColor;
        /*for (Node n :g.nodes) {
            n.color = -1;
        }*/
        boolean coloring = graphColoring(g.nodes);
        if(coloring) System.out.println("Solution");
        else System.out.println("Aucune solution");
    }

    /**
     *
     * @param node
     * @return true if the color of the node in param is different that the colors of his neighbors
     * else return false
     */
    public boolean safe(Node node, int color){
        for (Node n: g.nodes) {
            if(g.hasEdge(node, n) && color == n.color)
                return false;
        }
        return true;
    }

    /**
     *
     * @param list
     * @return return true if all of the colored nodes are safe with their neighbors else return false
     */
    public boolean graphColoring(List<Node> list){
        if(list.size() == 0)
            return true;

        for(int i = 1; i <= initialNumColor; i++){
            if(safe(list.get(0), i)){
                list.get(0).color = i;
                System.out.println("Node safe :"+list.get(0));
                if(graphColoring(list.subList(1, list.size()))) {
                    System.out.println(list.subList(1, list.size()));
                    return true;
                }
                list.get(0).color = 0;
                System.out.println("Node not safe :"+list.get(0));
            }
        }
        return false;
    }

    public static void main(String[] args) {
        /*Graph g = new Graph();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(1,2);
        g.addEdge(1,3);
        g.addEdge(2,3);
        System.out.println(g);*/
        Graph g = new RandomGraph(20, 0.5);
        g.export();
        Backtrack b = new Backtrack(g, 16);
        System.out.println(g);
    }
}
