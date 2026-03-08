package core;

import edu.princeton.cs.algs4.Heap;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static class UnionFind{
        private int[]parents;
        private Map<Room,Integer> order;
        public UnionFind(int size){
            parents=new int[size];
            for(int i=0;i<size;i++){
                parents[i]=-1;
            }
            order=new HashMap<>();
        }/**创立了过后记得要紧跟着把Room给Put进去**/
        public int FindParent(int num){
            if(parents[num]<0){
                return num;
            }
            else{
                parents[num] = FindParent(parents[num]);
                return FindParent(parents[num]);
            }
        }/**这个函数直接给出了根部的序号，用parents搜索就i可以得到数目**/
        public void Union(int num1,int num2){
            int size1=parents[FindParent(num1)];
            int size2=parents[FindParent(num2)];
            if(size1<size2){
                parents[FindParent(num1)]=parents[FindParent(num1)]+parents[FindParent(num2)];
                parents[FindParent(num2)]=FindParent(num1);
            }
            else if(size1>size2){
                parents[FindParent(num2)]=parents[FindParent(num1)]+parents[FindParent(num2)];
                parents[FindParent(num1)]=FindParent(num2);
            }
            else{
                if(FindParent(num1)!=FindParent(num2)){
                    if(num1<num2){
                        parents[FindParent(num1)]=parents[FindParent(num1)]+parents[FindParent(num2)];
                        parents[FindParent(num2)]=FindParent(num1);
                    }
                    else{
                        parents[FindParent(num2)]=parents[FindParent(num1)]+parents[FindParent(num2)];
                        parents[FindParent(num1)]=FindParent(num2);
                    }
                }
            }
        }
        public boolean IsConnected(int num1,int num2){
            if(FindParent(num1)==FindParent(num2)){
                return true;
            }
            else{
                return false;
            }
        }
    }


    public static TETile[][] StartUp(){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] BlankTiles = new TETile[WIDTH][HEIGHT];
        for(int i=0;i<WIDTH;i++){
            for(int j=0;j<HEIGHT;j++){
                BlankTiles[i][j]= Tileset.NOTHING;
            }
        }
        ter.renderFrame(BlankTiles);
        return BlankTiles;
    }
    public static TETile[][] GenerateWallways(Random random,TETile[][] map,graph HelperGraph){
        PriorityQueue<edge>heap=new PriorityQueue<>(HelperGraph.edges);
        UnionFind UnionTool=new UnionFind(HelperGraph.rooms.size());
        int size=HelperGraph.rooms.size();
        int curr=0;
        for(Room room: HelperGraph.rooms){
            UnionTool.order.put(room,curr);
            curr++;
        }
        int time=1;
        while(time<=size-1){
            edge ShortEdge=heap.poll();
            int num1=UnionTool.order.get(ShortEdge.room1);
            int num2=UnionTool.order.get(ShortEdge.room2);
            if(!UnionTool.IsConnected(num1,num2)){
                time++;
                UnionTool.Union(num1,num2);
                Room room1=ShortEdge.room1;
                Room room2=ShortEdge.room2;
                if((room1.startx+room1.width-1-room2.startx)>1&&(room2.startx+room2.width-1-room1.startx)>1){
                    if(room1.starty>room2.starty){/**横向有覆盖且room1在上面**/
                        if(room1.startx<room2.startx){/**最左边是room1**/
                            int startx=room2.startx+1;
                            int starty=room2.starty+room2.length-1;
                            int WholeLength=room1.starty-room2.starty-room2.length+2;
                            for(int i=0;i<WholeLength;i++){
                                map[startx][starty+i]=Tileset.FLOOR;
                                map[startx-1][starty+i]=Tileset.WALL;
                                map[startx+1][starty+i]=Tileset.WALL;
                            }
                            TERenderer ter = new TERenderer();
                            ter.renderFrame(map);
                        }
                        else{/**最左边是room2，这里应该是让room1从上往下**/
                            int startx=room1.startx+1;
                            int starty=room1.starty;
                            int WholeLength=room1.starty-room2.starty-room2.length+2;
                            for(int i=0;i<WholeLength;i++){
                                map[startx][starty-i]=Tileset.FLOOR;
                                map[startx-1][starty-i]=Tileset.WALL;
                                map[startx+1][starty-i]=Tileset.WALL;
                            }
                            TERenderer ter = new TERenderer();
                            ter.renderFrame(map);
                        }
                    }
                    else{/**横向有覆盖且room2在上面**/
                        if(room2.startx<room1.startx){/**room2在左边**/
                            int startx=room1.startx+1;
                            int starty=room1.starty+room1.length-1;
                            int WholeLength=room2.starty-room1.starty-room1.length+2;
                            for(int i=0;i<WholeLength;i++){
                                map[startx][starty+i]=Tileset.FLOOR;
                                map[startx-1][starty+i]=Tileset.WALL;
                                map[startx+1][starty+i]=Tileset.WALL;
                            }
                            TERenderer ter = new TERenderer();
                            ter.renderFrame(map);
                        }
                        else{/**room1在左边**/
                            int startx=room2.startx+1;
                            int starty=room2.starty;
                            int WholeLength=room2.starty-room1.starty-room1.length+2;
                            for(int i=0;i<WholeLength;i++){
                                map[startx][starty-i]=Tileset.FLOOR;
                                map[startx-1][starty-i]=Tileset.WALL;
                                map[startx+1][starty-i]=Tileset.WALL;
                            }
                            TERenderer ter = new TERenderer();
                            ter.renderFrame(map);
                        }
                    }
                }
                else if((room1.starty+room1.length-1-room2.starty)>1&&(room2.starty+room2.length-1-room1.starty)>1){
                    if(room1.startx>room2.startx){/**纵向有覆盖且room2在左边**/
                        if(room1.starty<room2.starty){/**room1在下面，从room2往右**/
                            int starty=room2.starty+1;
                            int startx=room2.startx+room2.width-1;
                            int WholeLength=room1.startx-room2.startx-room2.width+2;
                            for(int i=0;i<WholeLength;i++){
                                map[startx+i][starty]=Tileset.FLOOR;
                                map[startx+i][starty-1]=Tileset.WALL;
                                map[startx+i][starty+1]=Tileset.WALL;
                            }
                            TERenderer ter = new TERenderer();
                            ter.renderFrame(map);
                        }
                        else{/**room2在下面，由room1往左**/
                            int starty=room1.starty+1;
                            int startx=room1.startx;
                            int WholeLength=room1.startx-room2.startx-room2.width+2;
                            for(int i=0;i<WholeLength;i++){
                                map[startx-i][starty]=Tileset.FLOOR;
                                map[startx-i][starty-1]=Tileset.WALL;
                                map[startx-i][starty+1]=Tileset.WALL;
                            }
                            TERenderer ter = new TERenderer();
                            ter.renderFrame(map);
                        }
                    }
                    else{/**纵向覆盖且room1在左边**/
                        if(room2.starty<room1.starty){/**room2在下面**/
                            int starty=room1.starty+1;
                            int startx=room1.startx+room1.width-1;
                            int WholeLength=room2.startx-room1.startx-room1.width+2;
                            for(int i=0;i<WholeLength;i++){
                                map[startx+i][starty]=Tileset.FLOOR;
                                map[startx+i][starty-1]=Tileset.WALL;
                                map[startx+i][starty+1]=Tileset.WALL;
                            }
                            TERenderer ter = new TERenderer();
                            ter.renderFrame(map);
                        }
                        else{/**room1在下面**/
                            int starty=room2.starty+1;
                            int startx=room2.startx;
                            int WholeLength=room2.startx-room1.startx-room1.width+2;
                            for(int i=0;i<WholeLength;i++){
                                map[startx-i][starty]=Tileset.FLOOR;
                                map[startx-i][starty-1]=Tileset.WALL;
                                map[startx-i][starty+1]=Tileset.WALL;
                            }
                            TERenderer ter = new TERenderer();
                            ter.renderFrame(map);
                        }
                    }
                }
                else{
                    if((room1.startx>room2.startx)&&(room1.starty>room2.starty)){
                        int CornerX=room1.startx+2;
                        int CornerY=room2.starty;
                        for(int i=room2.startx+room2.width-1;i<=CornerX-1;i++){
                            if(map[i][CornerY]!=Tileset.FLOOR){
                                map[i][CornerY]=Tileset.WALL;
                            }
                            map[i][CornerY+1]=Tileset.FLOOR;
                            if(map[i][CornerY+2]!=Tileset.FLOOR){
                                map[i][CornerY+2]=Tileset.WALL;
                            }
                        }
                        if(map[CornerX][CornerY]!=Tileset.FLOOR){
                            map[CornerX][CornerY]=Tileset.WALL;
                        }
                        for(int j=CornerY+1;j<=room1.starty;j++){
                            if(map[CornerX][j]!=Tileset.FLOOR){
                                map[CornerX][j]=Tileset.WALL;
                            }
                            map[CornerX-1][j]=Tileset.FLOOR;
                            if(map[CornerX-2][j]!=Tileset.FLOOR){
                                map[CornerX-2][j]=Tileset.WALL;
                            }
                        }
                    }/**2左下 1右上**/
                    else if((room2.startx>room1.startx)&&(room2.starty>room1.startx)){
                        int CornerX=room2.startx+2;
                        int CornerY=room1.starty;
                        for(int i=room1.startx+room1.width-1;i<=CornerX-1;i++){
                            if(map[i][CornerY]!=Tileset.FLOOR){
                                map[i][CornerY]=Tileset.WALL;
                            }
                            map[i][CornerY+1]=Tileset.FLOOR;
                            if(map[i][CornerY+2]!=Tileset.FLOOR){
                                map[i][CornerY+2]=Tileset.WALL;
                            }
                        }
                        if(map[CornerX][CornerY]!=Tileset.FLOOR){
                            map[CornerX][CornerY]=Tileset.WALL;
                        }
                        for(int j=CornerY+1;j<=room2.starty;j++){
                            if(map[CornerX][j]!=Tileset.FLOOR){
                                map[CornerX][j]=Tileset.WALL;
                            }
                            map[CornerX-1][j]=Tileset.FLOOR;
                            if(map[CornerX-2][j]!=Tileset.FLOOR){
                                map[CornerX-2][j]=Tileset.WALL;
                            }
                        }
                    }/**1左下 2右上**/
                    else if((room1.startx>room2.startx)&&(room1.starty<room2.starty)){
                        int CornerX=room2.startx;
                        int CornerY=room1.starty;
                        for(int i=CornerX+1;i<=room1.startx;i++){
                            if(map[i][CornerY]!=Tileset.FLOOR){
                                map[i][CornerY]=Tileset.WALL;
                            }
                            map[i][CornerY+1]=Tileset.FLOOR;
                            if(map[i][CornerY+2]!=Tileset.FLOOR){
                                map[i][CornerY+2]=Tileset.WALL;
                            }
                        }
                        if(map[CornerX][CornerY]!=Tileset.FLOOR){
                            map[CornerX][CornerY]=Tileset.WALL;
                        }
                        for(int j=CornerY+1;j<=room2.starty;j++){
                            if(map[CornerX][j]!=Tileset.FLOOR){
                                map[CornerX][j]=Tileset.WALL;
                            }
                            map[CornerX+1][j]=Tileset.FLOOR;
                            if(map[CornerX+2][j]!=Tileset.FLOOR){
                                map[CornerX+2][j]=Tileset.WALL;
                            }
                        }
                    }/**2左上 1右下**/
                    else{
                        int CornerX=room1.startx;
                        int CornerY=room2.starty;
                        for(int i=CornerX+1;i<=room2.startx;i++){
                            if(map[i][CornerY]!=Tileset.FLOOR){
                                map[i][CornerY]=Tileset.WALL;
                            }
                            map[i][CornerY+1]=Tileset.FLOOR;
                            if(map[i][CornerY+2]!=Tileset.FLOOR){
                                map[i][CornerY+2]=Tileset.WALL;
                            }
                        }
                        if(map[CornerX][CornerY]!=Tileset.FLOOR){
                            map[CornerX][CornerY]=Tileset.WALL;
                        }
                        for(int j=CornerY+1;j<=room1.starty;j++){
                            if(map[CornerX][j]!=Tileset.FLOOR){
                                map[CornerX][j]=Tileset.WALL;
                            }
                            map[CornerX+1][j]=Tileset.FLOOR;
                            if(map[CornerX+2][j]!=Tileset.FLOOR){
                                map[CornerX+2][j]=Tileset.WALL;
                            }
                        }
                    }/**1左上 2右下**/
                }/**这是处理横向和纵向没有覆盖的情况,基本想法是分为左下右上和左上右下来进行讨论问题**/
            }
        }
        TERenderer ter = new TERenderer();
        ter.renderFrame(map);
        return map;
    }
    public static TETile[][] GenerateRooms(Random random,TETile[][] map,graph HelperGraph){
        /**这里应该有一个缺漏的部分要检查是否会重叠**/
        int min=3;
        int max=10;
        int width=random.nextInt(min,max);
        int length=random.nextInt(min,max);
        int startx=random.nextInt(1,38);
        int starty=random.nextInt(1,38);
        if(World.CheckCover(startx, starty, width, length, map)){
            for(int i=startx;i<width+startx;i++){
                map[i][starty]=Tileset.WALL;
                map[i][starty+length-1]=Tileset.WALL;
            }
            for(int j=starty;j<length+starty;j++){
                map[startx][j]=Tileset.WALL;
                map[startx+width-1][j]=Tileset.WALL;
            }
            for(int m=startx+1;m<startx+width-1;m++){
                for(int n=starty+1;n<starty+length-1;n++){
                    map[m][n]=Tileset.FLOOR;
                }
            }
            Room room=new Room(startx,starty,width,length);
            HelperGraph.AddRoom(room);
            TERenderer ter = new TERenderer();
            ter.renderFrame(map);}
        return map;
    }
    public static boolean CheckCover(int startx,int starty,int width,int length,TETile[][] origin ){
        boolean blank=true;
        for(int i=startx-1;i<startx+width+1;i++){
            for(int j=starty-1;j<starty+length+1;j++){
                if(origin[i][j]!=Tileset.NOTHING){
                    blank=false;
                }
            }
        }
        return blank;
    }
    public static int[] SelectOrigin(Random random,TETile[][]map){
        int StartX=random.nextInt(0,49);
        int StartY= random.nextInt(0,49);
        int[]response={StartX,StartY};
        if(map[StartX][StartY]==Tileset.FLOOR){
            return response;
        }
        else{
            return SelectOrigin(random,map);
        }
    }
    public static void Beautiful(TETile[][]map){
        for(int i=0;i<WIDTH;i++){
            if(map[i][1]==Tileset.NOTHING){
                map[i][1]=Tileset.GRASS;
            }
            if(map[i][HEIGHT-2]==Tileset.NOTHING){
                map[i][HEIGHT-2]=Tileset.GRASS;
            }
        }
        for(int j=0;j<HEIGHT;j++){
            if(map[1][j]==Tileset.NOTHING){
                map[1][j]=Tileset.GRASS;
            }
            if(map[WIDTH-2][j]==Tileset.NOTHING){
                map[WIDTH-2][j]=Tileset.GRASS;
            }
        }
        TERenderer ter=new TERenderer();
        ter.renderFrame(map);
    }
    public static void Around(TETile[][]map){
        for(int i=0;i<WIDTH;i++){
            if(map[i][0]==Tileset.NOTHING){
                map[i][0]=Tileset.GRASS;
            }
            if(map[i][HEIGHT-1]==Tileset.NOTHING){
                map[i][HEIGHT-1]=Tileset.GRASS;
            }
        }
        for(int j=0;j<HEIGHT;j++){
            if(map[0][j]==Tileset.NOTHING){
                map[0][j]=Tileset.GRASS;
            }
            if(map[WIDTH-1][j]==Tileset.NOTHING){
                map[WIDTH-1][j]=Tileset.GRASS;
            }
        }
        TERenderer ter=new TERenderer();
        ter.renderFrame(map);
    }
    public static void CreateTreasures(TETile[][]map,Random random,int number){
        int existed=0;
        while(existed<number){
            int X=random.nextInt(0,49);
            int Y=random.nextInt(0,49);
            if(map[X][Y]==Tileset.FLOOR){
                map[X][Y]=Tileset.FLOWER;
                existed++;
            }
        }
    }
}
