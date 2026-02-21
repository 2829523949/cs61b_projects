package main;

import java.util.*;

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
    public Set<String> up(){
        Set<String>box=new TreeSet<>();
        box.addAll(words);
        if(upper==null){
            return box;
        }
        else{
            for(Node node:upper){
                box.addAll(node.up());
            }
            return box;
        }
    }
}
