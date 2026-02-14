package hashmap;

import org.checkerframework.checker.units.qual.C;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public  class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void put(K key, V value) {
        int code=key.hashCode();
        int box=((code%capacity)+capacity)%capacity;
        boolean contained=false;
        LinkedList<Node>bucket= (LinkedList<Node>) buckets[box];
        for(Node node:bucket){
            if(node.key.equals(key)){
                node.value=value;
                contained=true;
            }
        }
        if(contained==false){
            bucket.add(new Node(key,value));
            size=size+1;
            resize();
        }
    }
    private void resize(){
        if(((double) size /capacity)>=maxrate){
            Collection<Node>[]newbuckets=createBucket(capacity*2);
            for(int i=0;i<capacity;i++){
                for(Node node:buckets[i]){
                    int code=node.key.hashCode();
                    int box=((code%(2*capacity))+(2*capacity))%(2*capacity);
                    newbuckets[box].add(node);
                }
            }
            buckets=newbuckets;
            capacity=capacity*2;
        }
    }

    @Override
    public V get(K key) {
        int code=key.hashCode();
        int box=(code%capacity+capacity)%capacity;
        if(buckets==null){
            return null;
        }
        LinkedList<Node>bucket= (LinkedList<Node>) buckets[box];
        if(bucket==null){
            return null;
        }
        else{
            for(Node node:bucket){
                if(node.key.equals(key)){
                    return node.value;
                }
            }
            return null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        int code=key.hashCode();
        int box=((code%capacity)+capacity)%capacity;
        if(buckets==null){
            return false;
        }
        LinkedList<Node>bucket= (LinkedList<Node>) buckets[box];
        for(Node node:bucket){
            if(node.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.buckets = createBucket(16);
        this.capacity = 16;
    }

    @Override
    public Set<K> keySet() {
        Set<K> response = new TreeSet<>();
         for (int i = 0;i < capacity;i++){
            for(Node node:buckets[i]){
                response.add(node.key);
            }
         }
         return response;
    }


    @Override
    public V remove(K key) {
        int code=key.hashCode();
        int box=((code%capacity)+capacity)%capacity;
        for(Node node:buckets[box]){
            if(node.key.equals(key)){
                V value= node.value;
                buckets[box].remove(node);
                return value;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }




    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int capacity;
    private double maxrate;
    private int size;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets=createBucket(16);
        capacity=16;
        maxrate=0.75;
    }

    public MyHashMap(int initialCapacity) {
        buckets=createBucket(initialCapacity);
        capacity=initialCapacity;
        maxrate=0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets=createBucket(initialCapacity);
        capacity=initialCapacity;
        maxrate=loadFactor;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node>[] createBucket(int capacity) {
        Collection<Node>[] table=(Collection<Node>[])new Collection[capacity];
        for(int i=0;i<capacity;i++){
            table[i]=new LinkedList<>();
        }
        return table;
    }/**这是初始化**/


    // Your code won't compile until you do so!

}
