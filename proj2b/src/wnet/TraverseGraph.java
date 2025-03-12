package wnet;

import java.util.HashMap;
import java.util.List;

/*
Adds Graph attribute and has findHyponyms function
 */
public class TraverseGraph {
    MyGraph mg;
    HashMap<Integer, String> map;

    /*

     */
    public TraverseGraph(MyGraph graph, HashMap<Integer, String> map) {
        mg = graph;
        this.map = map;
    }

    /*
    Traverse Graph and return list(mb tree list for unique values)
    @param pos
    starting vertices in graph
    @return
    List of vertices  values
     */
    public List findHyponyms (int pos) {
        return null;
    }
}
