package wnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/*
Used to traverse graph and get corresponding values from hashMap
 */
public class TraverseGraph {
    MyGraph mg;
    HashMap<Integer, String> map;
    HashMap<Integer, Boolean> marked;

    /*
     Takes graph and HashMap holding corresponding values for vortices
     @param graph
     connected graph with matching values in map
     @param map
     matching values to graph keys
     */
    public TraverseGraph(MyGraph graph, HashMap<Integer, String> map) {
        mg = graph;
        this.map = map;
        marked = new HashMap<>();
    }

    /*
    Traverse Graph and return list(mb tree list for unique values)
    @param pos
    starting vertices in graph
    @return
    List of vertices  values
     */
    public List<Integer> findHyponyms (int pos) {
        return find(mg.getAdjacent(pos), pos);
    }

    //helper function for TG, uses breath first search to traverse graph

    private List<Integer> find(List<Integer> PQ, int startingNode) {
        //first position in Priority Queue
        int firstInPQ;
        List<Integer> hyponyms = new ArrayList<>();
        hyponyms.add(startingNode);
        mark(startingNode);
        //add starting node to hyponyms List (list of all nodes traversed)
        //extend for marked needs graph size

        // do breath first search, add nodes to list
        while (!PQ.isEmpty()) {
            firstInPQ = PQ.removeFirst();
            if (isMarked(firstInPQ)) {
                throw new IllegalStateException("Graph is not acyclic");
            }
            mark(firstInPQ);
            hyponyms.addLast(firstInPQ);
            PQ.addAll(mg.getAdjacent(firstInPQ));
        }

        return hyponyms;
    }

    // mark visited nodes
    private void mark(int pos) {
        marked.put(pos, true);
    }

    //check if node was visited
    private boolean isMarked(int pos) {
        return marked.containsKey(pos);
    }
}
