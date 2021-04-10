import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Arrays;
import java.util.Iterator;



public class Graphe {
    private LinkedList<Noeud> noeuds;
    private HashMap<Integer, Noeud> hmap;

    public Graphe() {
        noeuds = new LinkedList<>();
        hmap = new HashMap<>();
    }

    public Graphe(int k) {
        noeuds = new LinkedList<>();
        hmap = new HashMap<>();
        for (int i = 1; i <= k; i++) {
            noeuds.add(new Noeud(i));
            hmap.put(i, new Noeud(i));
        }
    }

    public Graphe(String file) throws IOException {
        noeuds = new LinkedList<>();
        hmap = new HashMap<>();
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

                addNoeud(source);
                addNoeud(cible);
                addNoeudHMAP(source);
                addNoeudHMAP(cible);
                addArc(source, cible);
            }
        }
    }

    public void addArc(int x, int y) {
        Noeud n1 = getNoeud(x);
        LinkedList<Arc> nl = n1.getSuccesseurs();
        if (nl.isEmpty()) {
            Arc a = new Arc(n1, getNoeud(y));
            nl.add(a);
        } else {
            boolean pos = false;
            for (int i = 0; i < nl.size(); i++) {
                if (nl.get(i).getCible().getId() == y) {
                    pos = true;
                }
            }
            if (!pos) {
                Arc a = new Arc(getNoeud(x), getNoeud(y));
                nl.add(a);
                getNoeud(x).setSuccesseurs(nl);
            }
        }
    }

    public void addNoeud(int n) {
        if (getNoeud(n) == null) {
            noeuds.add(new Noeud(n));
        }
    }

    public void addNoeudHMAP(int n) {
        if (getNoeud(n) == null) {
            hmap.put(n, new Noeud(n));
        }
    }

    public Noeud getNoeud(int n) {
        Noeud res = null;
        for (int i = 0; i < noeuds.size(); i++) {
            if(noeuds.get(i).getId()== n)
                res = noeuds.get(i);
        }
        return res;
    }

    public Noeud getNoeudHMAP(int n){
        return hmap.get(n);
    }

    public void parcours() {
        for (Noeud n : hmap.values()) {
            n.setMark(false);
        }
        for (Noeud n : hmap.values()) {
            if (!n.getMark()) {
                profR(n);
            }
        }
    }

    public void profR(Noeud n) {
        n.setMark(true);
        System.out.println(n.showProfondeur() + n.getId());
        for (Arc a : n.getSuccesseurs()) {
            if (!a.getCible().getMark()) {
                a.getCible().addProfondeur(n.getProfondeur() + 1);
                profR(a.getCible());
            }
        }
    }

    public void profI() {
        for (Noeud n : hmap.values()) {
            n.setMark(false);
        }
        for (Noeud n : hmap.values()) {
            if (!n.getMark()) {
                profI(n);
            }
        }
    }

    private void profI(Noeud n) {
        Stack<Noeud> stack = new Stack<>();
        n.setMark(true);
        stack.push(n);
        System.out.println(n);
        while (!stack.isEmpty()) {
            n = stack.peek();
            boolean verif = true;
            for (Arc a : n.getSuccesseurs()) {
                if (!a.getCible().getMark())
                    verif = false;
            }
            if (verif) stack.pop();
            else {
                for (Arc a1 : n.getSuccesseurs()) {
                    if (!a1.getCible().getMark()) {
                        a1.getCible().setMark(true);
                        stack.push(a1.getCible());
                        System.out.println(a1.getCible());
                        break;
                    }
                }
            }
        }
    }

    public void largeur() {
        for (Noeud n : hmap.values()) {
            n.setMark(false);
        }
        for (Noeud n : hmap.values()) {
            if (!n.getMark()) {
                largeur(n);
            }
        }
    }

    public void largeur(Noeud n) {
        LinkedList<Noeud> l = new LinkedList<>();
        n.setMark(true);
        l.addFirst(n);
        System.out.println(n.getId());
        while (!l.isEmpty()) {
            n = l.getLast();
            l.removeLast();
            for (Arc a : n.getSuccesseurs()) {
                if (!a.getCible().getMark()) {
                    a.getCible().setMark(true);
                    l.addFirst(a.getCible());
                    System.out.println(a.getSource().getId() + "--" + a.getCible().getId());
                }
            }
        }
    }

    public void export() {
        String buff = "Source,Target\n";
        String sep = ",";
        for (Noeud n : this.noeuds) {
            for (Arc a : n.getSuccesseurs()) {
                buff += a.getCible().getId() + sep +
                        a.getSource().getId() + "\n";
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

    public int getNumNoeud(){
        return this.noeuds.size();
    }


    public int MethodeSeq() {
        int k = 0;// initialiser k;

        LinkedList<Integer> adj[] = new LinkedList[getNumNoeud()]; // Adjacency List

        for (int i = 0; i < getNumNoeud(); ++i){
            Noeud temp = getNoeud(i);
            adj[i] = new LinkedList();
            for(int j = 0;j< temp.getSuccesseurs().size();j++) {
                adj[i].add(temp.getSuccesseurs().get(j).getCible().getId());
            }
        }


        int result[] = new int[getNumNoeud()];

        // Initialiser toutes les noeuds
        Arrays.fill(result, -1);

        // On mets la première couleur pour la première noeud
        result[0] = 0;

        // On utilise le tableau pour enregistrer toutes les couleurs disponible
        // Quand la valeur de available[cr] est false，ca veut dire que la couleur cr s'est occupée par d'autre noeud
        boolean available[] = new boolean[getNumNoeud()];

        // En début, toutes les couleurs sont disponible
        Arrays.fill(available, true);

        // On distribue les couleurs pour les autres (getNumNoeud()-1) noeuds
        for (int u = 1; u < getNumNoeud(); u++) {
            // Traiter tous les voisins et marquer ses couleurs se sont occupées
            Iterator<Integer> it = adj[u].iterator();
            while (it.hasNext()) {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = false;
            }

            // Trouver la première couleur qui est disponible
            int cr;
            for (cr = 0; cr < getNumNoeud(); cr++) {
                if (available[cr])
                    break;
            }

            result[u] = cr; // Donner la valeur
            if(cr > k){
                k = cr;
            }

            // On reinitialiser toutes les couleur en disponible pour la nouvelle itération
            Arrays.fill(available, true);
        }

        // Printout le résultat
        for (int u = 0; u < getNumNoeud(); u++){
            System.out.println("ID " + u + " --->  Color " + result[u]);
        }
            return k + 1;
    }



    public LinkedList<Noeud> getNoeuds() {
        return noeuds;
    }

    public void setNoeuds(LinkedList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    public String toString() {
        return noeuds.toString();
    }
}
