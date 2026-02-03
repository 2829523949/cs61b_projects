public class UnionFind {
    int[] set;
    int size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        size=N;
        set=new int[N];
        for(int i=0;i<N;i++){
            set[i]=-1;
        }
    }/**初始化为-1**/

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
       int root=find(v);
       return -(set[root]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // 假设 size 是数组长度。注意越界检查通常是 v < 0 || v >= size
        if (v < 0 || v >= size) {
            throw new IllegalArgumentException("Index " + v + " out of bounds");}
        return set[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if(find(v1)==find(v2)){
            return true;
        }
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        int parent=parent(v);
        if(parent<0){
            return v;
        }
        else if(set[parent]<0){
            return parent;
        }
        else{
            set[parent]=find(parent);
            return set[parent];
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int root1=find(v1);
        int root2=find(v2);
        if(root1!=root2){
            if(set[root1]>set[root2]){
                set[root2]=set[root1]+set[root2];
                set[root1]=v2;
            }
            else if(set[root1]<set[root2]){
                set[root1]=set[root1]+set[root2];
                set[root2]=v1;
            }
            else{
                set[root2]=set[root2]+set[root1];
                set[root1]=root2;
                }
            }
        }
    }

