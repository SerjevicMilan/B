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
    private MyGraph<String> mG = new MyGraph<>();
    //storing key Integers(graph nodes) and corresponding word
    private HashMap<Integer, String> hmSynset;
    //synonym as a key and chosen main word as value
    private HashMap<String,String> hmWords = new HashMap<>();

    private TraverseGraph<String> tg = new TraverseGraph<>(mG);

    /*
    Construct WordNet by parsing text files and filling data structs
    @param synset text file of synsets (synonyms)
    @param hyponym tect file of hyponym represented as integers(word and hyponyms of word)
     */
    public WordNet(String synset,String hyponym) {
        hmSynset = processSynsetFile(synset);
        processHyponymFile(hyponym);
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

        String[] splitSynonyms = line[1].split(" ");

        hm.put(Integer.parseInt(line[0]), splitSynonyms[0]);
    }
/*
Takes String from line one or more words separated by white space("word1 word2 word3")
and make reference from all synonyms(from 2nd word) to first word(key word2, value word1)
adds node and synonyms to graph
@param line String of words separated by white space
 */
    private void RestOfSynonymsToFirstWord(String line) {
        String[] splitLine = line.split(" ");
        mG.addNode(splitLine[0]);
        for (int i = 0; i < splitLine.length;i++) {
            hmWords.put(splitLine[i], splitLine[0]);
            mG.addSynonyms(splitLine[0], splitLine[i]);
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
    private void processHyponymFile(String hyponymFilename) {
        if (hyponymFilename == null)
            throw new IllegalArgumentException("no file name arg");
        //create new listHashMap and start reading from file
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
            addHyponymsToGraph(splitLine);
        }
        in.close();
    }

    /*
    gets array of strings, example [4, 6, 2, 20]
    where first number is reference to word and rest to its direct hyponyms
    get word representation for numbers and
    store it graph, word as a key and string hyponyms as values
    @param splitLine array of integers
     */
    private void addHyponymsToGraph(String[] splitLine) {
        if (splitLine == null || splitLine.length == 0) {
            throw new InputMismatchException("input is badly format");
        }
        String word = hmSynset.get(Integer.parseInt(splitLine[0]));//conv string to int and get word
        if (word == null) {
            throw new InputMismatchException("input is badly formated");
        }
        for (int i = 1; i < splitLine.length; i++) {
            String hyponym = hmSynset.get(Integer.parseInt(splitLine[i]));
            mG.addNeighbor(word, hmSynset.get(Integer.parseInt(splitLine[i])));
        }
    }

    /*
    return list of integers representing hyponyms
    @param word is a String
    @return List of integers
     */
    public List<String> getDirectHyponyms(String word) {
        word = hmWords.get(word);
        return mG.getAdjacent(word);
    }

    public List<String> getAllHyponyms(String word) {
        if (!mG.containsNode(word))
            word = hmWords.get(word);
        return tg.findHyponyms(word);
    }
}
