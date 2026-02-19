package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    public Set<String> words;
    public int ID;
    public Set<Node> upper;
    public Set<Node> downer;
    public Node(Set<String> words,int ID){
        this.words=words;
        this.ID=ID;
        this.upper=new HashSet<Node>();
        this.downer=new HashSet<Node>();
    }
    public Set<String> down(){
        Set<String>box=new HashSet<>();
        box.addAll(words);
        if(downer!=null){
            for(Node node:downer){
                box.addAll(node.down());
            }
        }
        return box;
    }/**down是要求包含自身的单词**/
}
