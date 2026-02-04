import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    int[][]map;
    int opened;
    int length;
    int time;

    public Percolation(int N) {
        map=new int[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                map[i][j]= 0;
            }
        }
        opened=0;/**设计的是：0表示堵塞，1表示打开**/
        length=N;
        time=0;
    }/**用opened来表示已经打开的数量，time来表示isfull的次数防止死循环**/

    public void open(int row, int col) {
        if(map[row][col]!=1){
            map[row][col]=1;
            opened=opened+1;}
    }/**row是行，col是列。**/

    public boolean isOpen(int row, int col) {
        if(map[row][col]==1){
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        if(time>(length*length)){
            return false;
        }
        if(map[row][col]==0){
            return false;
        }
        else{
            if(row==0){
                return true;
            }
            else{
                if(col==0){
                    if(row==length-1){
                        return (upfull(row,col)||rightfull(row,col));
                    }
                    else{
                        return (upfull(row,col)||rightfull(row,col)||downfull(row,col));
                    }
                }
                else if(col==length-1){
                    if(row==length-1){
                        return (upfull(row,col)||leftfull(row,col));
                    }
                    else{
                        return (upfull(row,col)||leftfull(row,col)||downfull(row,col));
                    }
                }
                else{
                    if(row==length-1){
                        return (upfull(row,col)||leftfull(row,col)||rightfull(row,col));
                    }
                    else{
                        return (upfull(row,col)||rightfull(row,col));
                    }
                }
            }
        }
    }
    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        for(int i=0;i<length;i++){
            if(isFull(length-1,i)==true){
                return true;
            }
        }
        return false;
    }
    private boolean leftfull(int row,int col){
        if(isFull(row,col-1)==true){
            time=0;
            return true;
        }
        return false;
    }
    private boolean rightfull(int row,int col){
        if(isFull(row,col+1)){
            time=0;
            return true;
        }
        return false;
    }
    private boolean upfull(int row,int col){
        if(isFull(row-1,col)){
            time=0;
            return true;
        }
        return false;
    }
    private boolean downfull(int row,int col){
        if(isFull(row+1,col)){
            time=0;
            return true;
        }
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
