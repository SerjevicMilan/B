package wnet;

import edu.princeton.cs.algs4.In;
import ngrams.TimeSeries;

import java.util.*;

/*
creating data struct that has
attribute Graph and ListHashMap
parsing hyponyms data file and synsets data file and filling up Graph,
HashMap with key(words from data) and values(vertices location in Graph).
 */
public class WordNet {
    private MyGraph mG = new MyGraph();
    private ListHashMap<String, Integer> lhmHyponyms;
    private HashMap<Integer, String> hmSynset;

    public WordNet(String synset,String hyponym) {
        hmSynset = processSynsetFile(synset);
        lhmHyponyms = processHyponymFile(hyponym);
        fillAndConnectGraph();//add keys from synset to graph and connect using hyponyms
    }

    private HashMap<Integer, String> processSynsetFile(String synsetFilename) {
        if (synsetFilename == null)
            throw new IllegalArgumentException("no file name arg");
        //create new listHashMap and start reading from file
        HashMap<Integer, String> hm = new HashMap<>();
        In in = new In(synsetFilename);
        if (!in.exists()) {
            throw new IllegalArgumentException("can't find file");
        }

        //for storing next line and splitting it
        String nextLine;
        String[] splitLine;

        while (in.hasNextLine()) {//while it has line continue reading new line
            nextLine = in.readLine();
            splitLine = nextLine.split(",");
            updateHashMap(splitLine, hm);
        }
        in.close();
        return hmSynset;
    }

    private void updateHashMap(String[] line, HashMap<Integer, String> hm) {
        if (line == null || line.length != 2) {
            throw new InputMismatchException("input is badly format");
        }

        hm.put(Integer.parseInt(line[0]), line[1]);
    }

    private ListHashMap<String, Integer> processHyponymFile(String hyponymFilename) {
        if (hyponymFilename == null)
            throw new IllegalArgumentException("no file name arg");
        //create new listHashMap and start reading from file
        ListHashMap<String, Integer> lhm = new ListHashMap<>();
        In in = new In(hyponymFilename);
        if (!in.exists()) {
            throw new IllegalArgumentException("can't find file");
        }

        //for storing next line and splitting it
        String nextLine;
        String[] splitLine;

        while (in.hasNextLine()) {//while it has line continue reading new line
            nextLine = in.readLine();
            splitLine = nextLine.split(",");
            updateListMap(splitLine, lhm);
        }
        in.close();
        return lhm;
    }

    private void updateListMap(String[] splitLine, ListHashMap<String, Integer> lhm) {
        if (splitLine == null || splitLine.length == 0) {
            throw new InputMismatchException("input is badly format");
        }
        String word = hmSynset.get(Integer.parseInt(splitLine[0]));
        if (word == null) {
            throw new InputMismatchException("input is badly formated");
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < splitLine.length; i++) {
            list.add(Integer.parseInt(splitLine[i]));
        }
        lhm.put(word, list);
    }
}
