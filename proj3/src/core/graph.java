package core;

import edu.princeton.cs.algs4.Heap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class graph {
    public Set<Room> rooms;
    public Set<edge> edges;
    public graph(){
        rooms=new HashSet<>();
        edges=new HashSet<>();
    }
    public void AddRoom(Room room1){
        if(rooms.isEmpty()){
            rooms.add(room1);
        }
        else{
            for(Room room2:rooms){
                edges.add(new edge(room1,room2));
            }
            rooms.add(room1);
        }
    }
}
