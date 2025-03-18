package wnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;


/*
Used to traverse graph and get corresponding values from hashMap
 */
public class TraverseGraph<T> {
    MyGraph<T> mg;

    /*
     Takes graph and HashMap holding corresponding values for vortices
     @param graph
     connected graph with matching values in map
     @param map
     matching values to graph keys
     */
    public TraverseGraph(MyGraph<T> graph) {
        mg = graph;
    }

    /*
    Traverse Graph and return list(mb tree list for unique values)
    @param pos
    starting vertices in graph
    @return
    List of vertices  values
     */
    public List<String> findHyponyms (List<T> words) {
        List<T> PQ = new ArrayList<>();
        if(words == null)
            return List.of();
        PQ.addAll(words);
        return new ArrayList<>(find(PQ));
    }

    //helper function for TG, uses breath first search to traverse graph

    private TreeSet<String> find(List<T> PQ) {
        //first position in Priority Queue
        T firstInPQ;
        TreeSet<String> hyponyms = new TreeSet<>();
       // hyponyms.add(startingNode);
       // mark(startingNode);
        //add starting node to hyponyms List (list of all nodes traversed)
        //extend for marked needs graph size

        // do breath first search, add nodes to list
        while (!PQ.isEmpty()) {
            firstInPQ = PQ.removeFirst();
            hyponyms.addAll(mg.getSynonyms(firstInPQ));
            PQ.addAll(mg.getAdjacent(firstInPQ));
        }
        return hyponyms;
    }

}
