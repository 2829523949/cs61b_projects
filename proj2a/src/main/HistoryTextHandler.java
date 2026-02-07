package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.ArrayList;
import java.util.List;

public class HistoryTextHandler extends DummyHistoryHandler {
    public NGramMap handler;
    public HistoryTextHandler(NGramMap map){
        handler=map;
    }
    public String handle(NgordnetQuery q){
        String response=new String();
        List<String> words = q.words();
        for(String word:words){
            TimeSeries frequency=handler.weightHistory(word,q.startYear(),q.endYear());
            response=response+word+":"+" "+frequency.toString()+"\n";
        }
        return response;
    }
}
