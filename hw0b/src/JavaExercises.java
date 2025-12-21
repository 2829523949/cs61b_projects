import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        int[]container=new int [6];
        for(int i=0;i<6;i=i+1){
            container[i]=i+1;//注意，对于原始的八种基本类型不需要new而对象需要new ...
            };
        return container;}

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        //java里面只有局部变量需要声明，其他的可以直接使用
        if (customer.equals("Ergun")){
            return new String[]{"beyti","pizza","hamburger","tea"};
        }
        else if (customer.equals("Erik")){
            return new String[]{"sushi","pasta","avocado","coffee"};
        }
        else {
            return new String[3];
        }//注意，if和else if的条件需要用括号括起来并且不能使用;只能在body;
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        int max=array[0];
        int min=array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            } else if (value < min) {
                min = value;
            }
        }//补充：这是一个增强版的for循环，直接遍历array，实际上与正常的写法等价
        return max-min;
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        list.add(x);
        if (x==1){//注意，java里面[]的含义是数组，是长度不可变，内容可变的，同时数组不是八个基本类型，是引用类型。
            return list;
        }
        else if(1==(x%2)){
            return hailstoneHelper(3*x+1,list);
        }
        else{
            return hailstoneHelper(x/2,list);
        }//注意，列表的add(针对于一个元素)和addAll(添加所有元素)的返回值都是True/False，要返回结果需要创建空列表。
    }

}
