package wnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
custom graph  limited implementation
 */
public class myGraph {
    //hashMap for storing graph
    private HashMap<Integer, Node> Graph;

    //init graph
    public myGraph () {
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
        Graph.put(value, new Node());
    }

    /*
    get node from hashmap with base as a key and add new neighbor to node
    @param adjacent
    key for neighbor in hashMap
     */
    public void addNeighbor(int base, int adjacent) {
        Graph.get(base).adjacentNodes.add(adjacent);
    }

    /*
    get neighboring vortices using v as a key for the HashMap
    and copying array buy passing it in constructor
    @param v
    key for Graph(HashMap)
    @return
    List of integers representing vortices
     */
    public List<Integer> getAdjacent(int v) {
        return new ArrayList<Integer>(Graph.get(v).adjacentNodes);
    }
}
