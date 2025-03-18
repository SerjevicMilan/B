package wnet;

import edu.princeton.cs.algs4.In;
import ngrams.NGramMap;
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
    private MyGraph<Integer> mG = new MyGraph<>();
    //storing key Integers(graph nodes) and corresponding word
    private HashMap<Integer, String> hmSynset;
    //synonym as a key and chosen main word as value
    private HashMap<String,String> hmWords = new HashMap<>();
    //word as key and list of corresponding graph nodes as value
    private HashMap<String, List<Integer>> reverseSynset = new HashMap<>();
    //class used for traversing graph
    private TraverseGraph<Integer> tg = new TraverseGraph<>(mG);


    /*
    Construct WordNet by parsing text files and filling data structs
    @param synset text file of synsets (synonyms)
    @param hyponym tect file of hyponym represented as integers(word and hyponyms of word)
     */
    public WordNet(String synset, String hyponym) {
        hmSynset = processSynsetFile(synset);
        makeReverseSynset();
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
        RestOfSynonymsToFirstWord(line[1], line[0]);

        //gets first word of synonyms string
        String[] splitSynonyms = line[1].split(" ");

        hm.put(Integer.parseInt(line[0]), splitSynonyms[0]);
    }
/*
Takes String from line one or more words separated by white space("word1 word2 word3")
and make reference from all synonyms(from 2nd word) to first word(key word2, value word1)
adds node and synonyms to graph
@param line String of words separated by white space
 */
    private void RestOfSynonymsToFirstWord(String line,String n) {
        String[] splitLine = line.split(" ");
        Integer node = Integer.parseInt(n);
        mG.addNode(node);
        for (int i = 0; i < splitLine.length;i++) {
            hmWords.put(splitLine[i], splitLine[0]);
            mG.addSynonyms(node, splitLine[i]);
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
    swap values and keys storing keys in a list so repeating elements don't get overwritten
     */
    private void makeReverseSynset() {
        List<Integer> temp;
        for (int key : hmSynset.keySet()) {
            if (reverseSynset.containsKey(hmSynset.get(key))) {
                temp = new ArrayList<>(reverseSynset.get(hmSynset.get(key)));
                temp.add(key);
                reverseSynset.put(hmSynset.get(key),temp);
            } else  {
                reverseSynset.put(hmSynset.get(key), List.of(key));
            }
        }
    }

    /*
    Parses hyponyms text file, format "number, number, number, ..."
    and adds hyponyms to the graph
    @param hyponymFilename text file, format example "1, 2"
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
    store it graph, then connect hyponyms in graph
    @param splitLine array of integers
     */
    private void addHyponymsToGraph(String[] splitLine) {
        if (splitLine == null || splitLine.length == 0) {
            throw new InputMismatchException("input is badly format");
        }
        Integer word = Integer.parseInt(splitLine[0]);//conv string to int
        if (word == null) {
            throw new InputMismatchException("input is badly formated");
        }
        for (int i = 1; i < splitLine.length; i++) {
            Integer hyponym = Integer.parseInt(splitLine[i]);
            //connects first word to its hyponyms
            mG.addNeighbor(word, hyponym);
        }
    }
    /*
    return list of integers representing hyponyms
    @param word is a String
    @return List of integers
     */
    public List<String> getDirectHyponyms(String word) {
        word = hmWords.get(word);
        List<Integer> intHyponyms = new ArrayList<>();
        if(!reverseSynset.containsKey(word))
            return new ArrayList<>();
        for(Integer n: reverseSynset.get(word)) {
            intHyponyms.addAll(mG.getAdjacent(n));
        }
        return changeToString(intHyponyms);
    }

    /*
    change numbers representation of words to string using Synset
    @param intHyponyms List of intigers
    @return List of strings
     */
    private List<String> changeToString(List<Integer> intHyponyms) {
        List<String> stringHyponyms = new ArrayList<>();
        for (int i : intHyponyms) {
            stringHyponyms.add(hmSynset.get(i));
        }
        return stringHyponyms;
    }

    /*
    gets all hyponyms for the List of words including does words in list
    @param words List of strings
    @return list of string
     */
    public List<String> getAllHyponyms(List<String> words) {
        List<String> list = null;
        for(String word : words) {
            word = hmWords.get(word);//check if its a synonym and return main word
            if (list == null)//first time assign to the list
                list = tg.findHyponyms(reverseSynset.get(word));
            else//after first time only keep duplicates
                list.retainAll(tg.findHyponyms(reverseSynset.get(word)));
        }
        return list;

    }
}
