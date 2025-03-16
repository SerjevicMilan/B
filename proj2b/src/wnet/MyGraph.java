package wnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
custom graph  limited implementation
of Directed Acyclic Graph
 */
public class MyGraph {
    //hashMap for storing graph
    private HashMap<Integer, Node> Graph;

    private int size = 0;

    //init graph
    public MyGraph() {
        Graph = new HashMap<>();
    }

    //node for storing neighbors
    private class Node {
        List<Integer> adjacentNodes;

        public Node () {
            adjacentNodes = new ArrayList<>();
        }
    }

    /*adding value as new hash key and empty node
    @param value
    new vortice
     */
    public  void addNode(int value) {
        if (value < 0)//input cant be negative
            throw new IllegalArgumentException("only positive values allowed");
        Graph.put(value, new Node());
        size += 1;
    }

    /*
    get node from hashmap with base as a key and add new neighbor to node
    @param adjacent
    key for neighbor in hashMap
     */
    public void addNeighbor(int base, int adjacent) {
        if (base < 0 || base > Graph.size() || adjacent < 0 || adjacent > Graph.size())//check for bad input
            throw new IllegalArgumentException("nodes are not in graph");
        if (!Graph.containsKey(base) || !Graph.containsKey(adjacent))
            throw new IllegalArgumentException("nodes are not in graph");

        Graph.get(base).adjacentNodes.add(adjacent);
    }

    /*
    get neighboring vortices using v as a key for the HashMap
    and copying array by passing it in constructor
    @param v
    key for Graph(HashMap)
    @return
    List of integers representing vortices
     */
    public List<Integer> getAdjacent(int v) {
        if (v < 0 || v > Graph.size())
            return new ArrayList<>();
        return new ArrayList<Integer>(Graph.get(v).adjacentNodes);
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
    public boolean isConnected(int v1, int v2) {
        if (v1 < 0 || v1 > Graph.size() || v2 < 0 || v2 > Graph.size())//check for bad input
            return false;
        return Graph.get(v1).adjacentNodes.contains(v2);
    }

    /*
    gets all keys from HashMap(Graph)
    and add them to a list
    @return
    List of Integers
     */
    public List<Integer> toList() {
        List<Integer> list = new ArrayList();
        for(int k : Graph.keySet()) {
            list.add(k);
        }
        return list;
    }

    //return size of a graph
    public int size() {
        return size;
    }
}
