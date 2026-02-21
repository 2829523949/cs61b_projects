package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    public Map<String,TimeSeries> words;
    public TimeSeries counts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        words=new HashMap<String, TimeSeries>();
        In containwords=new In(wordsFilename);
        while(!containwords.isEmpty()){
            String name=containwords.readString();
            int year=containwords.readInt();
            double time= containwords.readDouble();
            containwords.readInt();
            if(words.containsKey(name)){
                words.get(name).put(year,time);
            }
            else{
                TimeSeries word=new TimeSeries();
                word.put(year,time);
                words.put(name,word);
            }
        }/**这样就处理好了wordsfile,wordsfile是没有逗号只有空格的**/
        counts=new TimeSeries();
        In containcounts=new In(countsFilename);
        while(containcounts.hasNextLine()){
            String box=containcounts.readLine();
            String[] purebox=box.split(",");
            int year=Integer.parseInt(purebox[0]);
            double time=Double.parseDouble(purebox[1]);
            counts.put(year,time);
        }/**这样就处理好了有逗号的countsfile**/
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if(words.containsKey(word)){
            return new TimeSeries(words.get(word),startYear,endYear);
        }
        return new TimeSeries();
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries result=new TimeSeries();
        if(words.containsKey(word)){
            result.putAll(words.get(word));/**这里把wordput进去是传递数据，不会直接给链接**/
        }
        return result;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries result=new TimeSeries();
        result.putAll(counts);
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if(words.containsKey(word)){
            TimeSeries result=new TimeSeries(words.get(word),startYear,endYear);
            result=result.dividedBy(counts);
            return result;
        }
        return new TimeSeries();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if(words.containsKey(word)){
            TimeSeries result=new TimeSeries();
            result.putAll(words.get(word));
            result=result.dividedBy(counts);
            return result;
        }
        return new TimeSeries();
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> currentwords,
                                          int startYear, int endYear) {
        TimeSeries result=new TimeSeries();
        for(String word:currentwords){
            if(words.containsKey(word)){
                TimeSeries plused=new TimeSeries(words.get(word),startYear,endYear);
                result=result.plus(plused);
            }
        }
        return result.dividedBy(counts);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> currentwords) {
        TimeSeries result=new TimeSeries();
        for(String word:currentwords){
            if(words.containsKey(word)){
                result=result.plus(words.get(word));
            }
        }
        result=result.dividedBy(counts);
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
