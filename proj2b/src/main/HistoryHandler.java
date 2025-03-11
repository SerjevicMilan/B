package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;


public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngMap;

    public HistoryHandler(NGramMap map) {
        ngMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for(String word : words) {
            labels.add(word);
            if (ngMap.weightHistory(word, startYear, endYear).years().isEmpty()) {//if word doesn't exist fill ts with 0
                lts.add(fillTsWithZeros(startYear, endYear));
            }
            else {
                lts.add(ngMap.weightHistory(word, startYear, endYear));
            }
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }

    private TimeSeries fillTsWithZeros(int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (int i = startYear; i <= endYear; i++) {
            ts.put(i, 0.0);
        }
        return ts;
    }
}

