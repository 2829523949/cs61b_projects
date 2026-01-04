package deque;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    public Node sentinel;
    public int size;
    /**这个部分是project1c里面新加的**/
    /**之所以不能直接在Deque里面default写equals和tostring是因为每个类都继承自Object，会冲突，但是允许deque声明**/
    @Override
    public boolean equals(Deque61B<T> other){/**注意建议再写一个Object的不然当涉及外部类比较的时候会直接报错而不是返回一个false，这会影响程序运行**/
        if(other != null){
            List<T>original=this.toList();
            List<T>compare=other.toList();
            if(original.equals(compare)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    @Override
    public boolean equals(Object x){
        if (x==null){
            return false;
        }
        else{
            if(x instanceof Deque61B compared){
                return this.equals(compared);
            }
            else{
                return false;
            }
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new linkediterator<T>();
    }
    public class linkediterator<T> implements Iterator<T>{
        private Node show=LinkedListDeque61B.this.sentinel;
        private int place=-1;
        private int size=LinkedListDeque61B.this.size();

        @Override
        public boolean hasNext() {
            if(place<size-1){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        public T next() {/**注意算法的时间复杂度，如果使用get那么是n2阶的，如果直接指针就是n的**/
            place=place+1;
           show=show.back;
           return (T) show.item;
        }
    }

    //注意，Deque61B后面也要写<T>
    //在内部类的定义时，不需要额外包一层{}，但是要注意是public还是private，有无static
    private class Node{
        public T item;
        public Node front;
        public Node back;
        public Node(T item, Node front, Node back){
            this.item=item;
            this.front=front;
            this.back=back;//注意不要写反了
        }
        public T recursiveIndex(int index){
            if(index==0){
                return item;
            }
            else{
                return back.recursiveIndex(index-1);
            }//这是帮助LinkedListDeque61B递归的辅助函数
        }
    }
    public LinkedListDeque61B(){
        sentinel=new Node(null,null,null);//注意，不能写sentinel=...(...,sentinel)右侧的sentinel还没有赋值，必须先赋值再自指向
        sentinel.front=sentinel;
        sentinel.back=sentinel;
        size=0;
    }


    @Override
    public void addFirst(T x) {
        Node newNode=new Node(x,sentinel,sentinel.back);
        sentinel.back.front=newNode;
        sentinel.back=newNode;//注意，不能直接把sentinel.back的指针改变，而是要建立原本的旧的后面和新的的联系
        size=size+1;
    }

    @Override
    public void addLast(T x) {
        Node newNode=new Node(x,sentinel.front,sentinel);
        sentinel.front.back=newNode;
        sentinel.front=newNode;
        size=size+1;
    }

    @Override
    public List<T> toList() {
        List<T>returnlist=new ArrayList<>();
        Node start=sentinel.back;
        while(start.item!=null){
            returnlist.add(start.item);
            start=start.back;
        }
        return returnlist;
    }

    @Override
    public boolean isEmpty() {
        if(sentinel.front==sentinel){
            return true;}
        else{return false;}
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(isEmpty()){
            return null;
        }
        else{
            T copy=sentinel.back.item;
            sentinel.back.back.front=sentinel;
            sentinel.back=sentinel.back.back;
            size=size-1;
            return copy;
        }
    }

    @Override
    public T removeLast() {
        if(isEmpty()){
            return null;
        }
        else{
            T copy=sentinel.front.item;
            sentinel.front.front.back=sentinel;
            sentinel.front=sentinel.front.front;
            size=size-1;
            return copy;
        }
    }

    @Override
    public T get(int index) {
        Node placenow=sentinel;
        for(int i=0;i<=index;i++){
            placenow=placenow.back;
        }
        return placenow.item;
    }

    @Override
    public T getRecursive(int index) {
        if(index<0||index>size()-1){
            return null;
        }
        else{//注意，不能赋值一个copy过后对copy操作，自定义赋值是共享引用。建议使用辅助方法。
            return sentinel.back.recursiveIndex(index);}
    }
}
