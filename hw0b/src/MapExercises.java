import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character,Integer>lower=new HashMap<>();//char的大写是Character，记得写HashMap或者TreeMap
        int i=1;//char在java里面也可以进行+1迭代，牛逼了
        for(char letter='a';letter<='z';letter++){
            lower.put(letter,i);
            i=i+1;
        }
        return lower;//''是char，""是String，此外，要写letter++，如果直接用加法会被判定为int
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map <Integer,Integer>culculate=new HashMap<Integer,Integer>();
        for(int i:nums){
            culculate.put(i,i*i);
        }
        return culculate;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String,Integer>times=new HashMap<>();//可以用.containsValue()和.containsKey()来判断是否存在字典里面
        for(String word:words){
            if(times.containsKey(word)){
                times.put(word,times.get(word)+1);//注意，time.get(word)返回的是Integer，不可变
                // 不能用times.get(word)=times.get(word)+1来进行赋值
                //同时字典里面的Key不能重复，可以通过times.put()放入相同的Key来取代同时放入新的Value
            }
            else{
                times.put(word,1);
            }
        }
        return times;
    }
}
