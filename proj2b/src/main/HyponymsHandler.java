package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private Map<Integer,Node> map;
    private Map<String,List<Node>>findstring;

    public HyponymsHandler(String sfile,String hfile) {
        this.map=new HashMap<>();
        this.findstring=new HashMap<>();
        In s= new In(sfile);
        while(s.hasNextLine()){
            String whole=s.readLine();
            String[]box=whole.split(",",3);
            int ID=Integer.parseInt(box[0]);
            String wildwords=box[1];
            String[]wordslist=wildwords.split(" ");/**注意，这里是用空格切割的**/
            Set<String>wordsset=new TreeSet<>(List.of(wordslist));
            Node node=new Node(wordsset,ID);
            map.put(ID,node);
            for(String word:wordslist){
                if(!findstring.containsKey(word)){
                    List<Node>list=new ArrayList<>();
                    list.add(node);
                    findstring.put(word,list);
                }
                else{
                    List<Node>list=findstring.get(word);
                    list.add(node);
                }
            }
        }
        In h=new In(hfile);
        while(h.hasNextLine()){
            String whole=h.readLine();
            String[]box=whole.split(",");
            List<Integer>ids=new ArrayList<>();
            int parentid=Integer.parseInt(box[0]);
            Node parent=map.get(parentid);
            for(int i=1;i< box.length;i++){
                int id=Integer.parseInt(box[i]);
                Node child=map.get(id);
                child.upper.add(parent);
                parent.downer.add(child);
            }
        }
        System.out.println("开始读取文件...");
    }

    @Override
    public String handle(NgordnetQuery q) {
       List<String>words=q.words();
       Set<String>response=new TreeSet<>();
       for(String word:words){
           if(response.isEmpty()){
               List<Node>nodes=findstring.get(word);
               for(Node node:nodes){
                   response.addAll(node.down());
               }
           }
           else{
               List<Node>nodes=findstring.get(word);
               Set<String>otherset=new TreeSet<>();
               for(Node node:nodes){
                  otherset.addAll(node.down());
               }
               response.retainAll(otherset);
           }
       }
       return response.toString();
    }
}
