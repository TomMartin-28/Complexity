import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

public class Noeud {
    private int id;
    private LinkedList<Arc> successeurs = new LinkedList<Arc>();
    private boolean mark;
    private String profondeur = "";
    public int prof = 0;

    public Noeud(int id){
        this.id = id;
    }
    public void setMark(boolean b){mark = b;}
    public int getId() {
        return id;
    }
    public boolean getMark(){return mark;}
    public LinkedList<Arc> getSuccesseurs() {
        return successeurs;
    }

    public int getProfondeur() {
        return prof;
    }

    public void setSuccesseurs(LinkedList<Arc> list){ successeurs=list;}

    public void addProfondeur(int i){
        prof += i;
    }

    public String showProfondeur(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < prof;i++){
            str.append("--");
        }
        return str.toString();
    }

    public String toString(){
        return "Id: "+ id + "\tSuccessors: "+ successeurs + "\n";
    }
}
