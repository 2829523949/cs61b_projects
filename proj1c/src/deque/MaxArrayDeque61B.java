package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T>{
    Comparator<T>fixedway;
    public MaxArrayDeque61B(Comparator<T> x){
        /**函数必须以接口的实例的形式传入，Comparator是一个自带的接口**/
        /**注意，记得显示调用父类的构造函数来提醒自己。当父类的构造函数含参的时候需要把参数给传进去**/
        super();
        fixedway=x;
    }
    public T max(){
        return Max(fixedway);
    }
    public T max(Comparator<T> y){
        return Max(y);/**这提供了一个可选的非默认的比较方法情况**/
    }
    public T Max(Comparator<T> way){
        /**注意，传入的是一个Comparator的类，需要调用其compare方法，其本身不能比较**/
        if(isEmpty()){
            return null;
        }
        else{
            T max=get(0);
            for(T item:this){
                if(way.compare(max,item)<0){
                    max=item;
                }/*Comparator的返回结果是大于则1小于则-1等于则0**/
            }
            return max;
        }
    }
}
