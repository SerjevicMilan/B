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
    //synonym as a key and chosen main word as value
    private HashMap<String,String> hmWords = new HashMap<>();

    /*
    Construct WordNet by parsing text files and filling data structs
    @param synset text file of synsets (synonyms)
    @param hyponym tect file of hyponym represented as integers(word and hyponyms of word)
     */
    public WordNet(String synset,String hyponym) {
        hmSynset = processSynsetFile(synset);
        lhmHyponyms = processHyponymFile(hyponym);
        hmSynsetReverse = reverseKeyValue();
        //fillAndConnectGraph();//add keys from synset to graph and connect using hyponyms
    }

    private HashMap<String, Integer> reverseKeyValue() {
        hmSynsetReverse = new HashMap<>();
        for(Integer key : getSynset()) {
            hmSynsetReverse.put(hmSynset.get(key), key);
        }

        return hmSynsetReverse;
    }

    public Integer getSynsetReverse(String word) {
        return hmSynsetReverse.get(word);
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

    /*
    parses synset text file and fills up hashMap with intigers keys corresponding to words
    @param synsetFilename txt file (format "number, word word word ..., rubbish)
    @return hm Hashmap with number as key and first word as value
     */
    private HashMap<Integer, String> processSynsetFile(String synsetFilename) {
        if (synsetFilename == null)
            throw new IllegalArgumentException("no file name arg");
        //create new HashMap and start reading from file
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

    /*
    Adds key(number) and value(firstWord) and returns HashMap,
    calls RestOfSynonymsToFirstWord to connect synonyms to first word
    @param array line from synset txt file, format[number, word, word,word,...,rubbish]
    @param hm hashMap for adding new values
     */
    private void updateHashMap(String[] line, HashMap<Integer, String> hm) {
        if (line == null || line.length < 2) {
            throw new InputMismatchException("input is badly format");
        }
        RestOfSynonymsToFirstWord(line[1]);

        hm.put(Integer.parseInt(line[0]), line[1]);
    }
/*
Takes String from line one or more words separated by white space("word1 word2 word3")
and make reference from all synonyms(from 2nd word) to first word(key word2, value word1)
@param line String of words separated by white space
 */
    private void RestOfSynonymsToFirstWord(String line) {
        String[] splitLine = line.split(" ");
        for (int i = 0; i < splitLine.length;i++) {
            hmWords.put(splitLine[i], splitLine[0]);
        }
    }

    /*
    Gets word from hashMap (main word for all synonyms).
    Main word was previously decided
    @param word
     */
    public String getFirstWord(String word) {
        return hmWords.get(word);
    }

    /*
    return array of integers (keys corresponding to words)
     */
    public List<Integer> getSynset() {
        return new ArrayList<>(hmSynset.keySet());
    }

    /*
    Parses hyponyms text file, format "number, number, number, ..."
    create new ListHashMap,pass it and nextLine to updateListMap
    returns ListHashMap
    @param hyponymFilename text file, format example "1, 2"
    @return ListHashMap of word as key and integer array of hyponyms as values
     */
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

    /*
    gets array of strings, example [4, 6, 2, 20]
    where first number is reference to word and rest to its direct hyponyms
    get word for first number and
    store it in ListHashMap, word as a key and integers references of hyponyms as values(jump: [1, 3, 5])
    @param splitLine array of integers
    @param lhm ListHashMap
     */
    private void updateListMap(String[] splitLine, ListHashMap<String, Integer> lhm) {
        if (splitLine == null || splitLine.length == 0) {
            throw new InputMismatchException("input is badly format");
        }
        String word = hmSynset.get(Integer.parseInt(splitLine[0]));//conv string to int and get word
        if (word == null) {
            throw new InputMismatchException("input is badly formated");
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < splitLine.length; i++) {
            list.add(Integer.parseInt(splitLine[i]));//create new list containing everything except first num
        }
        lhm.put(word, list);//add it to listHashMap
    }

    /*
    return list of integers representing hyponyms
    @param word is a String
    @return List of integers
     */
    public List<Integer> getDirectHyponyms(String word) {
        return lhmHyponyms.get(word);
    }
}
