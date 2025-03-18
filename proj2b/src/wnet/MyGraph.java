package wnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
custom graph  limited implementation
of Directed Acyclic Graph
 */
public class MyGraph<T> {
    //hashMap for storing graph
    private HashMap<T , Node> Graph;

    private int size = 0;

    //init graph
    public MyGraph() {
        Graph = new HashMap<>();
    }

    //node for storing neighbors and synonyms
    private class Node {
        List<T> adjacentNodes;
        List<String> synonymsNodes;

        public Node () {
            adjacentNodes = new ArrayList<>();
            synonymsNodes = new ArrayList<>();
        }
    }

    /*adding value as new hash key and empty node
    @param value
    new vortice
     */
    public  void addNode(T value) {
        Graph.put(value, new Node());
        size += 1;
    }

    /*
    get node from hashmap with base as a key and add new neighbor to node
    @param base key for node in hashMap
    @param adjacent node to a base node
     */
    public void addNeighbor(T base, T adjacent) {
        if (!Graph.containsKey(base) || !Graph.containsKey(adjacent))
            throw new IllegalArgumentException("nodes are not in graph");

        Graph.get(base).adjacentNodes.add(adjacent);
    }
    /*
    get node from hashmap with word as a key and add new synonym to node
    @param word key for node in hashMap
    @param synonym to a word
    */
    public void addSynonyms(T word, String synonym) {
        if (!Graph.containsKey(word))
            throw new IllegalArgumentException("nodes are not in graph");
        Graph.get(word).synonymsNodes.add(synonym);
    }

    public List<String> getSynonyms(T word) {
        if (!Graph.containsKey(word))
            return new ArrayList<>();
        return Graph.get(word).synonymsNodes;
    }

    /*
    get neighboring vortices using v as a key for the HashMap
    and copying array by passing it in constructor
    @param v
    key for Graph(HashMap)
    @return
    List of T representing vortices
     */
    public List<T> getAdjacent(T v) {
        if (!Graph.containsKey(v))
            return new ArrayList<>();
        return new ArrayList<T>(Graph.get(v).adjacentNodes);
    }

    /*
    check if v1 node has v2 for a neighbor
    @param v1
    key for graph(hashmap)
    @param v2
    key for graph as well
    @return
    if connected return true otherwise false
     */
    public boolean isConnected(T v1, T v2) {
        if (!Graph.containsKey(v1) || !Graph.containsKey(v2))//check for bad input
            return false;
        return Graph.get(v1).adjacentNodes.contains(v2);
    }

    /*
    gets all keys from HashMap(Graph)
    and add them to a list
    @return
    List of T
     */
    public List<T> toList() {
        List<T> list = new ArrayList();
        for(T k : Graph.keySet()) {
            list.add(k);
        }
        return list;
    }

    //return size of a graph
    public int size() {
        return size;
    }

    public boolean containsNode(T node) {
        return Graph.containsKey(node);
    }
}
