package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import edu.princeton.cs.algs4.In;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private class AnotherNode implements Comparable<AnotherNode>{
        public String word;
        public double relativity;
        public AnotherNode(String word,double relativity){
            this.word=word;
            this.relativity=relativity;
        }

        @Override
        public int compareTo(AnotherNode o) {
            if(this.relativity<o.relativity){
                return -1;
            }
            else if(this.relativity>o.relativity){
                return 1;
            }
            else{
                if(this.word.compareTo(o.word)>0){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        }
    }
    private Map<Integer,Node> map;
    private Map<String,List<Node>>findstring;
    private String wordfile;
    private String countfile;

    public HyponymsHandler(String sfile,String hfile,String wordfile,String countfile) {
        this.wordfile=wordfile;
        this.countfile=countfile;
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
        int k= q.k();
        int startyear= q.startYear();
        int endyear= q.endYear();
        NGramMap ngm = new NGramMap(wordfile, countfile);
       List<String>words=q.words();
       Set<String>response=new TreeSet<>();
       if(q.ngordnetQueryType()== NgordnetQueryType.HYPONYMS) {
           for (String word : words) {
               if (response.isEmpty()) {
                   List<Node> nodes = findstring.get(word);
                   for (Node node : nodes) {
                       response.addAll(node.down());
                   }
               } else {
                   List<Node> nodes = findstring.get(word);
                   Set<String> otherset = new TreeSet<>();
                   for (Node node : nodes) {
                       otherset.addAll(node.down());
                   }
                   response.retainAll(otherset);
               }
           }
           if (k == 0) {
               return response.toString();
           } else {
               PriorityQueue<AnotherNode> heap = new PriorityQueue<>(k, (n1, n2) -> Double.compare(n1.relativity, n2.relativity));
               for (String word : response) {
                   TimeSeries box = ngm.countHistory(word, startyear, endyear);
                   if (box != null) {
                       List<Double> data = box.data();
                       double total = data.stream().mapToDouble(d -> d).sum();
                       AnotherNode node = new AnotherNode(word, total);
                       if (total > 0) {
                           heap.add(node);
                           if (heap.size() > k) {
                               heap.poll();
                           }
                       }
                   }
               }
               Set<String> finalresponse = new TreeSet<>();
               for (AnotherNode node : heap) {
                   finalresponse.add(node.word);
               }
               return finalresponse.toString();
           }/**这里是调用queue来实现只拿频率的前k个**/
       }
       else{
           for(String word:words){
               if(response.isEmpty()){
                   List<Node>nodes=findstring.get(word);
                   for(Node node:nodes){
                       response.addAll(node.up());
                   }
               }
               else{
                   Set<String>tobeultered=new TreeSet<>();
                   for(Node node:findstring.get(word)){
                       tobeultered.addAll(node.up());
                   }
                   response.retainAll(tobeultered);
               }
           }
           return response.toString();
       }/**这是处理共同祖先的情况**/
    }
}
