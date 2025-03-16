import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wnet.ListHashMap;
import wnet.MyGraph;
import wnet.TraverseGraph;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestTraverseGraph {

    @Test
            public void TestBasicTG() {
        MyGraph mG = new MyGraph();
        HashMap<Integer, String> hm = new HashMap<>();

        //fill graph
        for (int i = 1; i <= 10; i++) {
            mG.addNode(i);
        }
        //add connections
        /*
                1
                |---2
                |---3
                    |---|---6

                2
                |---4
                |    |---|---7
                |        |
                |---5    |---8
                     |   |---9
                     |
                     |---10
                3
                |---6

         */
        mG.addNeighbor(1, 2);
        mG.addNeighbor(1, 3);
        mG.addNeighbor(2, 4);
        mG.addNeighbor(2, 5);
        mG.addNeighbor(3, 6);
        mG.addNeighbor(4, 7);
        mG.addNeighbor(4, 8);
        mG.addNeighbor(4, 9);
        mG.addNeighbor(5, 10);

        //make tg with prev filled and conn graph
        TraverseGraph tg = new TraverseGraph(mG, hm);

        //check if it finds all hyponyms
        assertThat(tg.findHyponyms(2)).isEqualTo(List.of(2, 4, 5, 7, 8, 9, 10));

        //make graph cyclic
        mG.addNeighbor(9, 2);

        TraverseGraph  tg1 = new TraverseGraph(mG, hm);

        //check if traverseGraph finds a bad graph state
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> {
                    tg1.findHyponyms(2);
                });

        Assertions.assertEquals("Graph is not acyclic", exception.getMessage());
    }
}
