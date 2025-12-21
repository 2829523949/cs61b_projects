import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int total=0;
        for(int element:L){
            total=total+element;
        }
        return total;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer>result=new ArrayList<Integer>();
        for(int element:L){
            if(0==(element%2)){
                result.add(element);
            }
        }
        return result;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer>common=new ArrayList<Integer>();
        for(int a:L1){
            for(int b:L2){
                if(a==b){
                    common.add(a);
                }
            }
        }
        return common;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int total=0;//注意，字符不能直接用索引但是可以用.charAt(index)
        for(String word:words){
            for(int i=0;i<word.length();i=i+1){
                if(word.charAt(i)==c){
                    total=total+1;
                }
            }
            }
        return total;}}
