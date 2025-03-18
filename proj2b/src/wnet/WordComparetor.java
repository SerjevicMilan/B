package wnet;

import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.Comparator;
import java.util.List;


//used for comparing two words(by there total count of appearance in book in certain time period)
public class WordComparetor implements Comparator<String> {
    NGramMap ngm;
    int startYear;
    int endYear;

    /*
    Constructor take NGramMap and range of years
    @param ngm NGramMap used for finding words mentions
    @param start (year)
    @param end (year)
     */
    public WordComparetor(NGramMap ngm,int start, int end) {
        this.ngm = ngm;
        startYear = start;
        endYear = end;
    }

    @Override
    public int compare(String s1, String s2) {
        return totalCount( s2) - totalCount( s1);
    }

    /*
    get a total count of word mentions per year range
    @param s word
    @return number of mentions
     */
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
