import java.util.*;

public class BSTMap <K extends Comparable<K>,V> implements Map61B<K,V>{
    private class Node{
        public K key;
        public V value;
        Node left,right;
        public Node(K inputkey,V inputvalue){
            key=inputkey;
            value=inputvalue;
            left=null;
            right=null;
        }
        private Node findplace(K inputkey){
            if(key==null){
                return this;
            }/**这对应root不存在和递归到末端的情况**/
            if(this.key.compareTo(inputkey)>0){
                if(this.left==null){
                    return this.left;
                }
                else {
                    return this.left.findplace(inputkey);
                }
            }/**如果当前的比输入的大则放左边**/
            else if(this.key.compareTo(inputkey)<0){
                if(this.right==null){
                    return this.right;
                }
                else {
                    return this.right.findplace(inputkey);
                }
            }/**如果当前的比输入的小则放右边**/
            else{
                return this;
            }/**按照map的使用逻辑，相同的时候就直接替代了**/
        }/**这个方法用于得到位置，在BSTMap中直接对root使用，便于BSTMap的后续方法使用（直接赋值给此函数）**/
        public Set<K>helperset(){
            Set<K>response=new TreeSet<>();
            if(key==null){
                return null;
            }
            response.add(key);
            Set<K>leftset=left.helperset();
            if(leftset!=null) {
                response.addAll(left.helperset());
            }
            Set<K>rightset=right.helperset();
            if(rightset!=null) {
                response.addAll(right.helperset());
            }
            return response;
        }/**这是一个辅助递归来把BSTMap的所有key放入集合**/
        public Node findleftest(){
            Node response=this;
            while(response.left!=null){
                response=response.left;
            }
            return response;
        }
        public Node findrightest(){
            Node response=this;
            while(response.right!=null){
                response=response.right;
            }
            return response;
        }
        public Node findparent(K inputkey){
            Node place=this;
            while(!(place.left!=null&&place.left.key==inputkey)||(place.right!=null&&place.right.key==inputkey)){
                if(place.left!=null&&place.left.key.compareTo(inputkey)>0){
                    place=place.left;
                }
                place=place.right;
            }
            return place;
        }/**这是直接为BSTMap的remove服务的，remove已经检查了null，这里直接认为包含了key并且this不考虑null**/
    }
    private Node root;
    private int size;
    public BSTMap(){
        root=null;
        size=0;
    }
    @Override
    public void put(K key, V value) {
        if(root==null){
            root=new Node(key,value);
            size=size+1;
        }
        else {
            if (!containsKey(key)) {
                size = size + 1;
                Node place=root;
                 while(!((place.left==null&&place.key.compareTo(key)>0)||(place.right==null&&place.key.compareTo(key)<0))){
                    if(place.left!=null&&place.key.compareTo(key)>0){
                        place=place.left;
                    }
                    else{
                        place=place.right;
                    }
                }
                if(place.key.compareTo(key)>0){
                    place.left=new Node(key,value);
                }
                else{
                    place.right=new Node(key,value);
                }
            }
            else{
                Node place=root.findplace(key);
                place.value=value;
            }/**这按理来说应该直接调用findplace，但是我不知道当时为什么没有这样做 **/
        }
    }

    @Override
    public V get(K key) {
        if(containsKey(key)){
            return root.findplace(key).value;
        }
        return null;
    }/**这里要注意，如果key本身就不在BSTMap里面，那么findplace会返回null，null.value直接报错**/

    @Override
    public boolean containsKey(K key) {
        if(root==null){
            return false;
        }
        if(root.findplace(key)==null){
            return false;
        }
        return true;
    }/**findplace的逻辑是没有那么就会给一个Key是null的位置，如果有则给key相同的位置，以此可以判断是否有key**/

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root=null;
        size=0;
    }

    @Override
    public Set<K> keySet() {
        return root.helperset();
    }

    @Override
    public V remove(K inputkey) {
        if(!containsKey(inputkey)){
            return null;
        }
        if(root.key==inputkey){
            V response=root.value;
            root.value=null;
            return response;
        }
        Node toberemove=root.findplace(inputkey);
        V response= toberemove.value;
        Node parent=root.findparent(inputkey);
        if(parent.key.compareTo(inputkey)>0){
            Node replace=toberemove.findrightest();
            replace.left=toberemove.left;
            replace.right=toberemove.right;
            Node replaceparent=root.findparent(replace.key);
            parent.left=replace;
            if(replaceparent.key.compareTo(replace.key)>0){
                replaceparent.left=null;
            }
            else{
                replaceparent.right=null;
            }
        }/**在左边就把最右边的给提上来**/
        else{
            Node replace=toberemove.findleftest();
            replace.left=toberemove.left;
            replace.right=toberemove.right;
            Node replaceparent=root.findparent(replace.key);
            parent.right=replace;
            if(replaceparent.key.compareTo(replace.key)>0){
                replaceparent.left=null;
            }
            else{
                replaceparent.right=null;
            }/**在右边就把最左边的提上来**/
        }/**中间的很多语句是一模一样的，我这里直接复制粘贴了，懒得整合。**/
        return response;
    }/**这里使用时注意，root是没有parent的，要单独讨论root**/

    @Override
    public Iterator<K> iterator() {
        Set<K>aimset=keySet();
        if(aimset!=null) {
            return keySet().iterator();
        }
        return null;
    }
}
