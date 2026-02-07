package main;

import browser.NgordnetQuery;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends DummyHistoryHandler {
    NGramMap helper;

    public HistoryHandler(NGramMap map) {
        helper = map;
    }

    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        List<TimeSeries> requiredlist = new ArrayList<>();
        for (String word : words) {
            if (helper.words.containsKey(word)) {
                TimeSeries adder = helper.weightHistory(word, q.startYear(), q.endYear());
                requiredlist.add(adder);
            }
        }
        XYChart returned = Plotter.generateTimeSeriesChart(words, requiredlist);
        String result = Plotter.encodeChartAsString(returned);
        return result;
    }
}
