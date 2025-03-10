package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeMap;

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

    // TODO: Add any necessary static/instance variables.
    private Map<String, TimeSeries> wordsMap;
    private TimeSeries wordCountTs;


    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordsMap = processFileInToMap(wordsFilename);
        wordCountTs = processCountFileToTS(countsFilename);
    }

    private TimeSeries processCountFileToTS(String countsFilename) {
        if (countsFilename == null)
            throw new IllegalArgumentException("no file name arg");

        TimeSeries ts = new TimeSeries();
        In in = new In(countsFilename);
        String nextLine;
        String[] splitLine;

        while (in.hasNextLine()) {//while it has line continue reading new line
            nextLine = in.readLine();
            splitLine = nextLine.split(",");
            if (splitLine.length != 4) {
                throw new InputMismatchException("input is badly format");
            }
            ts.put(Integer.parseInt(splitLine[0]), Double.parseDouble(splitLine[1]));
        }
        in.close();
        return ts;

    }

    /**
     * process file and fill map
     * @param wordsFilename -file name
     * @return filled map with values from file
     */
    private Map<String, TimeSeries> processFileInToMap(String wordsFilename) {
        if (wordsFilename == null)
            throw new IllegalArgumentException("no file name arg");
        //create new map and start reading from file
        Map<String, TimeSeries> wm = new TreeMap<>();
        In in = new In(wordsFilename);
        if (!in.exists()) {
            throw new IllegalArgumentException("can't find file");
        }

        //for storing next line and splitting it
        String nextLine;
        String[] splitLine;

        while (in.hasNextLine()) {//while it has line continue reading new line
            nextLine = in.readLine();
            splitLine = nextLine.split("\t");
            updateMap(splitLine, wm);
        }
        in.close();
        return wm;
    }

    /**
     * add new value to map or update timeSeries if key already exists
     * @param spitLine array of string values for map
     * @param wm map with String for key and TimeSeries for val
     */
    private void updateMap(String[] spitLine, Map<String, TimeSeries> wm) {
        if (spitLine == null || spitLine.length != 4) {
            throw new InputMismatchException("input is badly format");
        }
        //getting values and converting them to right type
        String word = spitLine[0];
        int year = Integer.parseInt(spitLine[1]);
        Double countByYear = Double.parseDouble(spitLine[2]);
        if (wm.containsKey(word)) {//if contains key update ts
            wm.get(word).put(year, countByYear);
        }
        else {// create new ts and add it as value to curr key
            TimeSeries ts = new TimeSeries();
            ts.put(year, countByYear);
            wm.put(word,ts);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(word == null || startYear - endYear > 0)
            return new TimeSeries();
        return new TimeSeries(wordsMap.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return wordCountTs;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries ts = new TimeSeries(wordsMap.get(word), startYear, endYear);
        return ts.dividedBy(wordCountTs);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries returnTs = new TimeSeries();
        TimeSeries ts;

        if(words == null || startYear - endYear > 0)
            return returnTs;

        for (String word : words) {
            if (word == null)
                continue;
            ts = new TimeSeries(wordsMap.get(word), startYear, endYear);//get new ts for every word in year range
            returnTs = ts.plus(returnTs);// update sum of all words per year
        }
        return returnTs.dividedBy(wordCountTs);// div sum of words by total count of words per year
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
