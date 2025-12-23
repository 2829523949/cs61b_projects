package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;

import java.util.Formatter;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        for(int x=0;x<=3;x=x+1){
            for(int y=0;y<=3;y=y+1){
                if (this.tile(x,y)==null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public boolean maxTileExists() {
        for(int x=0;x<=3;x=x+1){
            for(int y=0;y<=3;y=y+1){
                Tile copy=this.tile(x,y);
                if (copy!=null){
                    if(copy.value()==MAX_PIECE){
                        return true;
                    }
                }}}
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public boolean atLeastOneMoveExists() {
        if(emptySpaceExists()){
            return true;
        }
        else{
            for(int x=1;x<=2;x=x+1){
                for(int y=1;y<=2;y=y+1){
                    Tile up=this.tile(x,y+1);
                    Tile down=this.tile(x,y-1);
                    Tile left=this.tile(x-1,y);
                    Tile right=this.tile(x+1,y);
                    int[]same=new int[]{up.value(),down.value(),left.value(),right.value()};
                    Tile copy=this.tile(x,y);
                    for(int value:same){
                        if(copy.value()==value){
                            return true;
                        }
                    }
                    }}
        Tile corner1=this.tile(0,0);
        int[]same1=new int[]{(this.tile(1,0)).value(),(this.tile(0,1)).value()};
            for(int value:same1){
                if(corner1.value()==value){
                    return true;}}
            Tile corner2=this.tile(3,0);
            int[] same2 =new int[]{(this.tile(2,0)).value(),(this.tile(3,1)).value()};
            for(int value: same2){
                if(corner2.value()==value){
                    return true;}}
            Tile corner3=this.tile(0,3);
            int[]same3=new int[]{(this.tile(0,2)).value(),(this.tile(1,3)).value()};
            for(int value:same3){
                if(corner3.value()==value){
                    return true;}}
            Tile corner4=this.tile(3,3);
            int[] same4 =new int[]{(this.tile(3,2)).value(),(this.tile(2,3)).value()};
            for(int value: same4){
                if(corner4.value()==value){
                    return true;}}}
        return false;
    }

    /**
     * Moves the tile at position (x, y) as far up as possible.
     *
     * Rules for Tilt:
     * 1. If two Tiles are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void moveTileUpAsFarAsPossible(int x, int y) {
        Tile currTile = board.tile(x, y);
        int myValue = currTile.value();
        int targetY = y;
        while(true){
            if(targetY==3){
                break;
            }
            else{
                if(board.tile(x,targetY+1)!=null) {
                    Tile maybemerged=board.tile(x,targetY+1);
                    if((!maybemerged.wasMerged())&&(maybemerged.value()==myValue)){
                        board.move(x,targetY+1,currTile);
                        score=score+2*myValue;
                        return;
                        //注意，.value()是方法调用而不是属性，不能赋值
                    }
                    else{break;}
                }
                else{
                    targetY=targetY+1;
                }
                }
            }//java里面也可以用break来停止while循环
        if(y==targetY){return;}
        else{
        board.move(x,targetY,currTile);}}


    /** Handles the movements of the tilt in column x of the board
     * by moving every tile in the column as far up as possible.
     * The viewing perspective has already been set,
     * so we are tilting the tiles in this column up.
     * */
    public void tiltColumn(int x) {
       for(int y=3;y>=0;y--){
           if(board.tile(x,y)!=null){
               moveTileUpAsFarAsPossible(x,y);
           }
       }
    }

    public void tilt(Side side) {
        board.setViewingPerspective(side);
        for(int i=0;i<=3;i++)
        {tiltColumn(i);}
        board.setViewingPerspective(Side.NORTH);
    }

    /** Tilts every column of the board toward SIDE.
     */
    public void tiltWrapper(Side side) {
        board.resetMerged();
        tilt(side);
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
