package deque;

import java.util.Iterator;
import java.util.List;
import static com.google.common.truth.Truth.assertThat;
public class ArrayDeque61B<T> implements Deque61B<T> {
    /**
     * 这一部分是在project1c里面新加的iterator()功能，与原功能区分
     **/
    @Override
    public boolean equals(Deque61B<T> x){
        if(x != null){
            List<T>original=this.toList();
            List<T>compare=x.toList();
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
    public Iterator<T> iterator(){
        return new arrayiterator();
    }
    public class arrayiterator<T>implements Iterator<T>{
        private int size=ArrayDeque61B.this.size();/**内部的类也可以访问外部的属性，但是要注意格式不让默认访问内部属性**/
        private int place=-1;
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
        public T next() {
            place=place+1;
            return (T) ArrayDeque61B.this.get(place);/**注意这里要填上一个（T）**/
        }
    }
    /*如果需要，可以在ArrayDeque61B.java中添加任何私有辅助类或方法。**/
    /*由于超出本课程范围的原因，无法在 Java 中创建泛型数组（例如 new T[1000]）。**/
    /**需要使用语法 (T[]) new Object[1000]。**/
    private T[]innerlist;
    private int maxcontent;
    private int nextFirst;
    private int nextLast;
    private int size;
    public ArrayDeque61B(){
        innerlist=(T[]) new Object[8];
        maxcontent=8;
        nextFirst=7;
        nextLast=0;
        size=0;
    }
    public T[] Addresize(){//这个函数应该对innerlist使用
        if(size!=maxcontent){
            return innerlist;
        }
        else{
            T[]newList=(T[])new Object[2*maxcontent];
            if(nextLast==0){
                for(int i=0;i<size;i++){
                    newList[i+maxcontent]=innerlist[i];
                }
                nextFirst=maxcontent-1;
            }
            else if(nextFirst==maxcontent-1){
                for(int i=0;i<size;i++){
                    newList[i]=innerlist[i];
                }
                nextFirst=nextFirst+maxcontent;
            }
            else{
                for(int i=0;i<=nextLast-1;i++){
                    newList[i]=innerlist[i];
                }
                for(int i=nextFirst+1;i<maxcontent;i++){
                    newList[i+maxcontent]=innerlist[i];
                }
                nextFirst=nextFirst+maxcontent;}
            maxcontent=2*maxcontent;
            return newList;
        }
    }
    public T[]removeresize(){//这个函数是直接赋值给innerlist的
        if (size>=0.25*maxcontent){
            return innerlist;
        }
        else{
            T[]newlist=(T[])new Object[maxcontent/2];
            if(nextLast==0){
                for(int i=0;i<size;i++){
                    newlist[i%(maxcontent/2)]=innerlist[i];
                }
            }
            else if(nextFirst==maxcontent-1){
                for(int i=0;i<size;i++){
                    newlist[i]=innerlist[i];
                }
            }
            else{
                for(int i=0;i<=nextLast-1;i++){
                    newlist[i]=innerlist[i];
                }
                for(int i=nextFirst+1;i<maxcontent;i++){
                    newlist[i%(maxcontent/2)]=innerlist[i];
                }}
            nextFirst=nextFirst%(maxcontent/2);
            maxcontent=maxcontent/2;
            return newlist;}}

    @Override
    public void addFirst(T x) {//这里要注意nextFirst为0的情况
        assertThat(size<=maxcontent-1);
        size=size+1;
        innerlist[nextFirst]=x;
        nextFirst=(nextFirst-1+maxcontent)%maxcontent;
        innerlist=Addresize();
    }

    @Override
    public void addLast(T x) {
        assertThat(size<=maxcontent-1);
        size=size+1;
        innerlist[nextLast]=x;
        nextLast=(nextLast+1+maxcontent)%maxcontent;
        innerlist=Addresize();
    }

    @Override
    public List<T> toList() {
        T[] showList=(T[])new Object[size];//可以用List.of()来转换数组为列表
        for(int i=0;i<size;i++){
            showList[i]=get(i);
        }
        return List.of(showList);
    }

    @Override
    public boolean isEmpty() {
        return (size==0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        assertThat(size>=1);
        T returnvalue=innerlist[(nextFirst+1+maxcontent)%maxcontent];
        innerlist[(nextFirst+1+maxcontent)%maxcontent]=null;
        nextFirst=(nextFirst+1+maxcontent)%maxcontent;
        size=size-1;
        innerlist=removeresize();
        return returnvalue;
    }

    @Override
    public T removeLast() {
        assertThat(size>=1);
        T returnvalue=innerlist[(nextLast-1+maxcontent)%maxcontent];
        innerlist[(nextLast-1+maxcontent)%maxcontent]=null;
        nextLast=(nextLast-1+maxcontent)%maxcontent;
        size=size-1;
        innerlist=removeresize();
        return returnvalue;
    }

    @Override
    public T get(int index) {//注意，这个get是从First开始的计数而不是从innerfirst的0号位开始的计数。
        T target=innerlist[(nextFirst+1+index+maxcontent)%maxcontent];
        return target;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
