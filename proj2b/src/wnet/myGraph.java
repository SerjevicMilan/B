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
        if (value < 0)
            return;
        Graph.put(value, new Node());
    }

    /*
    get node from hashmap with base as a key and add new neighbor to node
    @param adjacent
    key for neighbor in hashMap
     */
    public void addNeighbor(int base, int adjacent) {
        if (base < 0 || base > Graph.size() || adjacent < 0 || adjacent > Graph.size())
            return ;
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
        return new ArrayList<Integer>(Graph.get(v).adjacentNodes);
    }

    public boolean isConnected(int v1, int v2) {
        if (v1 < 0 || v1 > Graph.size() || v2 < 0 || v2 > Graph.size())
            return false;
        return Graph.get(v1).adjacentNodes.contains(v2);
    }


    public List<Integer> toList() {
        List<Integer> list = new ArrayList();
        for(int k : Graph.keySet()) {
            list.add(k);
        }
        return list;
    }
}
