package wnet;

import edu.princeton.cs.algs4.In;
import ngrams.TimeSeries;

import java.util.*;

/*
creating data struct that has
attribute Graph and ListHashMap
parsing hyponyms data file and synsets data file, reverseSynset(key, values reversely stored and filling up Graph,
HashMap with key(words from data) and values(vertices location in Graph).
 */
public class WordNet {
    //Graph for storing integers corresponding to words
    private MyGraph mG = new MyGraph();
    //storing word(key) and corresponding hyponyms(list of Integers)
    private ListHashMap<String, Integer> lhmHyponyms;
    //storing key Integers(graph nodes) and corresponding word
    private HashMap<Integer, String> hmSynset;
    //reverse key values storing of hmSynset
    private HashMap<String, Integer> hmSynsetReverse = new HashMap<>();

    /*
    Construct WordNet by parsing text files and filling data structs
    @param synset text file of synsets (synonyms)
    @param hyponym tect file of hyponym represented as integers(word and hyponyms of word)
     */
    public WordNet(String synset,String hyponym) {
        hmSynset = processSynsetFile(synset);
       // lhmHyponyms = processHyponymFile(hyponym);
        //hmSynsetReverse = reverseKeyValue();
        //fillAndConnectGraph();//add keys from synset to graph and connect using hyponyms
    }

    private HashMap<String, Integer> reverseKeyValue() {
        hmSynsetReverse = new HashMap<>();
        for(Integer key : hmSynset.keySet()) {
            hmSynsetReverse.put(hmSynset.get(key), key);
        }

        return hmSynsetReverse;
    }

    private void fillAndConnectGraph() {
        fillGraph();
        connectGraph();
    }

    private void fillGraph() {
        Set<Integer> nodes = hmSynset.keySet();

        for (Integer node : nodes) {
            mG.addNode(node);
        }
    }

    private void connectGraph() {
        Set<String> hyponymsSet = lhmHyponyms.keySet();
        for (String hyponym : hyponymsSet) {
            connectGrphHelper(hmSynsetReverse.get(hyponym), lhmHyponyms.get(hyponym));
        }
    }

    private void connectGrphHelper(Integer firstNode, List<Integer> nodes) {
        for (Integer secondNode : nodes) {
            mG.addNeighbor(firstNode, secondNode);
        }
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
        return hm;
    }

    private void updateHashMap(String[] line, HashMap<Integer, String> hm) {
        if (line == null || line.length < 2) {
            throw new InputMismatchException("input is badly format");
        }


        hm.put(Integer.parseInt(line[0]), line[1]);
    }

    public List<Integer> getSynset() {
        return new ArrayList<>(hmSynset.keySet());
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
