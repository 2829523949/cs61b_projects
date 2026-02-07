package ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /**
     * If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here.
     */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int year = startYear; year <= endYear; year++) {
            if (ts.containsKey(year)) {
                double value = ts.get(year);
                this.put(year, value);
            }
        }/**事实上可以直接使用this.putAll(ts.subMap(startYear,true,endYear,true)来解决问题)**/
    }/**注意，这是要创造一个新的对象而不是直接把原对象的一部分给截出来剩下的直接不要。**/

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        Set<Integer> all = this.keySet();
        List<Integer> result = new ArrayList<Integer>();
        for (int year : all) {
            result.add(year);
        }
        return result;
    }/**Arraylist支持直接把set给转换，可以直接return new ArrayList<>(this.keySet())**/

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        return new ArrayList<Double>(this.values());
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     * <p>
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries combined = new TimeSeries();
        Set<Integer> combinedyear = new TreeSet<>();
        combinedyear.addAll(this.keySet());
        combinedyear.addAll(ts.keySet());
        List<Integer> years = new ArrayList<>(combinedyear);
        for (int year : years) {
            double value = 0;
            if (this.containsKey(year)) {
                value = value + this.get(year);
            }
            if (ts.containsKey(year)) {
                value = value + ts.get(year);
            }
            combined.put(year, value);
        }
        return combined;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     * <p>
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries divided = new TimeSeries();
        for (int year : this.keySet()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException();
            } else {
                double personalvalue = this.get(year);
                double allvalue = ts.get(year);
                double value = personalvalue / allvalue;
                divided.put(year, value);
            }
        }
        return divided;
        }
    }

