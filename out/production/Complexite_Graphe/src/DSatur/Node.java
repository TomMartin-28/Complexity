package DSatur;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{
    public int value;
    public List<Node> neighbors;
    public int color, degree, dsat;

    public Node(int elem) {
        this.value = elem;
        this.neighbors = new ArrayList<>();
    }

    public Node(int value, List<Node> neighbors) {
        this.value = value;
        this.neighbors = neighbors;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", neighbors=" + neighbors() +
                " color=" + color +
                '}';
    }

    private String neighbors(){
        StringBuffer str = new StringBuffer();
        for (Node n:neighbors) {
            str.append(n.value+",");
        }
        return str.toString();
    }

    @Override
    public int compareTo(Node o) {
        if(value < o.value) return -1;
        else if(value > o.value) return 1;
        else return 0;
    }
}
