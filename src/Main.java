import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Graphe g = new RandomGraphe(10, 0.5);
        g.DSatur();
    }
}
