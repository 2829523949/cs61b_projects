package core;

public class edge implements Comparable<edge>{
    public Room room1;
    public Room room2;
    public int distance;
    public edge(Room room1,Room room2){
        int[]room1x={room1.startx,room1.startx+ room1.width-1};
        int[]room2x={room2.startx,room2.startx+room2.width-1};
        int[]room1y={room1.starty,room1.starty+room1.length-1};
        int[]room2y={room2.starty,room2.starty+room2.length-1};
        int distancex=50;
        int distancey=50;
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                if(Math.abs(room1x[i]-room2x[j])<distancex){
                    distancex=Math.abs(room1x[i]-room2x[j]);
                }
                if(Math.abs(room1y[i]-room2y[j])<distancey){
                    distancey=Math.abs(room1y[i]-room2y[j]);
                }
            }
        }
        this.room1=room1;
        this.room2=room2;
        if((room1.startx+room1.width-1-room2.startx)>1&&(room2.startx+room2.width-1-room1.startx)>1){
            distancex=0;
        }
        if((room1.starty+room1.length-1-room2.starty)>1&&(room2.starty+room2.length-1-room1.starty)>1){
            distancey=0;
        }
        this.distance=distancex+distancey;
    }
    @Override
    public int compareTo(edge o) {
        return this.distance-o.distance;
    }
}
