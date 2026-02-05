import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    WeightedQuickUnionUF helper;
    boolean[][]opened;
    int length;
    int opentime;

    public Percolation(int N){
        length=N;
        helper=new WeightedQuickUnionUF(N*N+2);
        opened=new boolean[N][N];
        opentime=0;
    }/**注意，由于是从（0，0）开始的，因此第N*N+1个是顶部，第N*N+2个是底部。但是对应的是N*N和N*N+1**/
    /**此外，boolean的值在实例化过后默认为false，不用再额外进行操作**/
    /**换算应该是row*length+col**/

    public void open(int row, int col) {
        assert ((row < length) && (row >= 0) && (col < length) && (col >= 0));
        if(opened[row][col]==false){
            opentime=opentime+1;
        }
        opened[row][col] = true;
        if (row == 0) {
            helper.union(length * length, col);/**这里应该要连到顶部，就是N*N**/
        }
        if (row == length - 1) {
            helper.union(length * length + 1, length * (length - 1) + col);/**这里连到底部，N*N+1**/
        }
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int rowx = row + dx[i];
            int coly = col + dy[i];
            if(rowx>=0&&rowx<length&&coly>=0&&coly<length){
                if(isOpen(rowx,coly)){
                    helper.union(rowx*length+coly,row*length+col);
                }
            }
        }/**这里需要检查row和col在0到N-1之间**/
    }

    public boolean isOpen(int row, int col){
        if(opened[row][col]){
            return true;
        }
        return false;
    }


    public boolean isFull(int row, int col){
        if(helper.connected(row*length+col,length*length)){
            return true;
        }
        return false;
    }
    public int numberOfOpenSites(){
        return opentime;
    }

    public boolean percolates(){
        return helper.connected(length*length,length*length+1);
    }
}
