package wnet;

import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.Comparator;
import java.util.List;

public class WordComparetor implements Comparator<String> {
    NGramMap ngm;
    int startYear;
    int endYear;

    public WordComparetor(NGramMap ngm,int start, int end) {
        this.ngm = ngm;
        startYear = start;
        endYear = end;
    }

    @Override
    public int compare(String s1, String s2) {
        return totalCount( s2) - totalCount( s1);
    }

    private int totalCount(String s) {
        double sum = 0;
        TimeSeries ts = ngm.countHistory(s, startYear, endYear);
        List<Double> years = ts.data();
        for(Double year : years) {
            sum = sum + year;
        }
        return (int) sum;
    }

}
