import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GraphADJ {
    int n; //size of graphe
    List valeurs; // all ths id of noeuds in graphe
    boolean [][] adj;// adjacent table

    int k ; //number of colors
    int [] x = new int[1000];
    int fs = 0; // number of collision
    int best_fs = 0;
    int bestdelt = 10000; // best delt

    int [][] Adjacent_Color_Table ; //Adjacent color table
    int [] adj_count = new int[1000];// Store the number of adjacent vertices for each vertex

    int [][] TabuTenure;// table tabu

    int equal_count = 0; // Counting of equivalent solutions
    int [][] equal_list; // using for save equivalent solutions

    int select_vex; // point to move
    int select_color; // color to move

    int iter = 0;




    public GraphADJ(Graphe g){
        // Initialize k :
        k = g.MethodeSeq();

        // Initialize table tabu:
        TabuTenure = new int [1000][1000];
        for (int i = 0; i < n; i++) {
            TabuTenure[i] = new int[k];
            for (int j = 0; j < k; j++)
                TabuTenure[i][j] = 0;
        }

        //Initialize table adjacent color:

        //  Adjacent_Color_Table = new int [n][k];
        Adjacent_Color_Table = new int [1000][1000];
        for (int i = 0; i < n; i++) {
            Adjacent_Color_Table[i] = new int[k];
            for (int j = 0; j < k; j++)
                Adjacent_Color_Table[i][j] = 0;
        }

        //Initialize equal_list:
      //  equal_list = new int[][(n*(k-1))];
        for (int i = 0; i < n*(k - 1); i++)
            equal_list[i] = new int[2];

        // Initialize table adj:
        this.valeurs = new ArrayList<>();
        n = g.getNoeuds().size();
        adj = new boolean[n][n];
        for (Noeud noeud:g.getNoeuds()){
            valeurs.add(noeud.getId());
        }
        for(Noeud noeud : g.getNoeuds()){
         for (Arc a : noeud.getSuccesseurs()){
            // int source_index = valeurs.indexOf(a.getSource());
             int source_index = valeurs.indexOf(noeud.getId());
             int voisin_index = valeurs.indexOf(a.getCible().getId());
             adj[source_index][voisin_index] = true;
         }
        }
    }

    public int heuristiqueControle(){
        int[] grandGamma = new int[n];
        for (int i = 0; i < n; i++) {
            int gamma = 1;
            for (int j = 0; j < n; j++) {
                if (adj[i][j] && grandGamma[j] == gamma)
                     gamma = gamma + 1;
            }
            grandGamma[i] = gamma;
        }
        return max(grandGamma);
    }

    public int max(int[] a){
        int m = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > m)
                m = a[i];
        }
        return m;
    }


    /**---------------------------------Algo tabou----------------------------------------*/


    /**
     * Generating random solutions
     */

    public void initial() {
        int a;
        for (int i = 0; i < n; i++) {
            a = (int) (Math.random() * (k));
            x[i] = a; // i = idN 每个i是一个节点 ，a 是给其上的颜色
        }
    }

    /**
     Calculate the number of conflict： Fs
     总冲突数
     */

    public void caculatesFs(){
        for(int i = 0; i<n;i++){
            for(int j = i+1; j<adj_count[i];j++){
                if(x[i] == x[j]){ // if they have same color
                    if(adj[i][j] == true){
                        fs++;
                    }
                }
            }
        }
        fs = fs/2  ;
        best_fs = fs;

    }

    /**
     * Generating Adjacent_Color_Table
     * Record the number of different conflict for each dot with different colors
     * 更新的原则：对于move(u,i,j)，对于与u顶点相邻的顶点的行，第i列减一，第j列加一
     */

    public void updateACTable(){
        int [] count = new int[k]; //每个位置是颜色的种类，该位置下的数字是这个颜色出现的次数
        for(int i = 0; i < n; i++){
            int s = 0;
            int [] store = new int[1000];
            for (int j = 0; j < k; j++)
                count[j] = 0;
            for(int j = 0; j < n ; j++)
                if (adj[i][j] == true) {
                    store[s++] = j;
                    for (int h = 0; h < k; h++) {
                        if (x[j] == h)
                            count[h]++; //h处的颜色数增加
                    }
                }
            for(int j = 0; j < k; j++)
                Adjacent_Color_Table[i][j] = count[j]; // i 是 node 的id, j是颜色种类代号。该位置下存的是该节点某种颜色的个数
                adj_count[i] = s; //储存了每个节点的successeur 个数
            /*
            * vex_adj[i] = new int[s];
	          for (int j = 0; j < s; j++) {
			  vex_adj[i][j] = store[j];
		}*/
        }
    }




    /**
     * Finding the optimal movement
     */
    public void findmove(){
        bestdelt = 10000;
        int delt;

        for(int i = 0; i < n; i++){
            if(Adjacent_Color_Table[i][x[i]] > 0){
                for(int j = 0; j < k; j++){
                    if(j != x[i]){ // if it's not the same color
                        delt = Adjacent_Color_Table[i][j] - Adjacent_Color_Table[i][x[i]]; //Δ(u,i,j) = M[u][j] - M[u][i]
                        if(delt <= bestdelt && ((fs + delt) < best_fs))
                            if (delt < bestdelt) {
                                equal_count = 0;
                                bestdelt = delt;
                            }
                            equal_list[equal_count][0] = i;
                            equal_list[equal_count][1] = j;
                            equal_count++;
                    }
                }
            }
        }
        int t;
        t = (int) Math.random() * (equal_count);
        select_vex = equal_list[t][0];
        select_color = equal_list[t][1];
    }

    /**
     * Movement
     */

    public void makeMove(){
        fs = fs + bestdelt;
        int old_color = x[select_vex];
        x[select_vex] = select_color;
        if(fs < best_fs)
            best_fs = fs;
        int t;
        t  = (int) Math.random() * (10);
        TabuTenure[select_vex][old_color] = fs + t + iter;
        for(int i = 0; i < n; i++){
            if(adj[select_vex][i] == true){
                Adjacent_Color_Table[i][old_color]--;
                Adjacent_Color_Table[i][select_color]++;
            }

        }
    }

    /**
     * Find the optimal value of k
     * @return optimal chromatic number
     */

   public int TabuSearch(){
       // k = heuristiqueControle();
     //  System.out.println(fs);

        int caculate_count =  4;
       while(iter < caculate_count && k > 0){
            k--;
            initial();
            caculatesFs();
            updateACTable();
          //  System.out.println(fs);

            while(fs > 0) {
                System.out.println(fs);
                findmove();
                makeMove();
            }
                iter ++;
                caculate_count --;

        }
        return k;
    }





}
