import java.util.*;

public class k_Color_Tabu {
    int equal_count = 0; // Counting of equivalent solutions
    int [][] equal_list; // using for save equivalent solutions
    int best_fs;
    int select_vex; // vertex to move
    int select_color; // color to move
    int bestDelt = 10000;
    int adj_count[] = new int[1000]; // Store the number of adjacent

    int n;
    int k;
    int fs; // number of collision
    int [][] Adjacent_Color_Table ; //Adjacent color table
    int [][] TabuTenure;// table tabu
    int [][] edge; // Relation of vertex
    int [] x; // solutions
    int[][] vex_adj; // 储存每个节点的邻接节点 voisin

    int iter = 0;

    public k_Color_Tabu(Graphe g){
        n =  g.getNoeuds().size();
        k =  g.MethodeSeq();
       // k = 2;
        fs = 0;
        edge = new int[n][n];
        Adjacent_Color_Table = new int[n][k];
        TabuTenure = new int[n][n];
        vex_adj = new int [n][n];
        x = new int[n];

        //Initialize adj_count:
        for(Noeud node : g.getNoeuds()){
                adj_count[node.getId()] = node.getSuccesseurs().size();
        }

        //Initialize vex_adj:
        for (Noeud noeud:g.getNoeuds()){
            vex_adj[noeud.getId()] = new int[noeud.getSuccesseurs().size()];
            for(int j=0; j<adj_count[noeud.getId()];j++){
                vex_adj[noeud.getId()][j] = noeud.getSuccesseurs().indexOf(j);
            }
        }

        // Initialize edge:
        for(Noeud noeud : g.getNoeuds()){
            for (Arc a : noeud.getSuccesseurs()){
               // addEdge(noeud.getId(),a.getCible().getId());
                edge[noeud.getId()][a.getCible().getId()] = 1;
               // edge[a.getCible().getId()][noeud.getId()] = 1;
            }
        }

        // Initialize equal_list :
        equal_list = new int[n*(k-1)][n*(k-1)];
        for (int i = 0; i < n*(k-1); i++)
            equal_list[i] = new int[2];

        /*Initialize edge :
        for(int i =0; i<n; i++){
            edge[i] = new int[n];
            for(int j= 0;j<n; j++)
                edge[i][j] = 0;
        }*/

        //Initialize table adjacent color:
        for (int i = 0; i < n; i++) {
            Adjacent_Color_Table[i] = new int[k];
            for (int j = 0; j < k; j++)
                Adjacent_Color_Table[i][j] = 0;
        }

        // Initialize table tabu:
        for (int i = 0; i < n; i++) {
            TabuTenure[i] = new int[k];
            for (int j = 0; j < k; j++)
                TabuTenure[i][j] = 0;
        }
    }

    /**
     *
     * @param v1 vertex 1
     * @param v2 vertex 2
     */
    public void addEdge (int v1, int v2){
        edge[v1][v2] = 1;
        edge[v2][v1] = 1;
    }

    /**
     * Determine if fs is greater than 0
     * @return boolean
     */

    public boolean fsTure(){
        if(fs > 0)
            return true;
        else
            return false;
    }

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

    public void caculateFs(){
        for(int i=0; i<n; i++){
            /*for(int j=0;j<adj_count[i];j++){
                if(x[i] == x[vex_adj[i][j]])
                    fs++;

            }*/
        fs = fs + Adjacent_Color_Table[i][x[i]];
        }
        fs = fs/2;
        best_fs = fs;
    }

    /**
     * Generating Adjacent_Color_Table
     * Record the number of different conflict for each dot with different colors
     */

    public void updateACTable(){
        int [] count = new int[k]; //每个位置是颜色的种类，该位置下的数字是这个颜色出现的次数
        for(int i = 0; i < n; i++){
            int s = 0;
            int [] store = new int[1000];
            for (int j = 0; j < k; j++)
                count[j] = 0;
            for(int j = 0; j < n ; j++)
                if (edge[i][j] == 1) {
                    store[s++] = j;
                    for (int h = 0; h < k; h++) {
                        if (x[j] == h)
                            count[h]++; //h处的颜色数增加
                    }
                }
            for(int j = 0; j < k; j++)
                Adjacent_Color_Table[i][j] = count[j]; // i 是 node 的id, j是颜色种类代号。该位置下存的是该节点某种颜色的个数
                adj_count[i] = s; //储存了每个节点的successeur 个数

            vex_adj[i] = new int[s];
            for (int j = 0; j < s; j++) {
                vex_adj[i][j] = store[j];
            }
        }
    }


    /**
     * Finding the optimal movement
     */
    public void findmove(){
        bestDelt = 10000;
        int delt;

        for(int i = 0; i < n; i++){
            if(Adjacent_Color_Table[i][x[i]] > 0){
                for(int j = 0; j < k; j++){
                    if(j != x[i]){ // if it's not the same color
                        delt = Adjacent_Color_Table[i][j] - Adjacent_Color_Table[i][x[i]]; //Δ(u,i,j) = M[u][j] - M[u][i]
                        if(delt <= bestDelt && (iter > TabuTenure[i][j] ||(fs + delt) < best_fs))
                            if (delt < bestDelt) {
                                equal_count = 0;
                                bestDelt = delt;
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
        fs = fs + bestDelt;
        int old_color = x[select_vex];
        x[select_vex] = select_color;
        if(fs < best_fs)
            best_fs = fs;
        int t;
        t  = (int) Math.random() * (10);
        TabuTenure[select_vex][old_color] = fs + t + iter;
        for(int i = 0; i < n; i++){
            if(edge[select_vex][i] == 1){
                Adjacent_Color_Table[i][old_color]--;
                Adjacent_Color_Table[i][select_color]++;
            }

        }
    }


    /**
     * Find the optimal value of k
     * @return optimal chromatic number
     */

    public int TabuSearch() {
        int nb_Test = 4;
        HashMap<Integer, Integer> M = new HashMap<>(); // Save fs of one k

        while (iter < nb_Test && k > 0) {
            initial();
            // System.out.println("Fs initial:"+fs);
            updateACTable();
            // System.out.println("Second Fs:"+fs);
            caculateFs();
            // System.out.println("Third Fs: "+fs);

            while (fsTure()) {
             //   System.out.println("Current collision number: " + fs);
                M.put(k,fs);
                findmove();
                makeMove();
                iter++;
            }
            nb_Test--;
            --k;

        }

        int minValue = (Collections.min(M.values()));
        for (Map.Entry<Integer, Integer> entry : M.entrySet()) {  // Itrate through hashmap
            if (entry.getValue()==minValue) {
                k = entry.getKey();     // Print the key with max value
            }
        }

        return k;

    }










}
