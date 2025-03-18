import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wnet.MyGraph;
import wnet.TraverseGraph;

import java.util.HashMap;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestTraverseGraph {

    @Test
            public void TestBasicTG() {
        MyGraph<String> mG = new MyGraph<>();
        HashMap<Integer, String> hm = new HashMap<>();

        //fill graph
        for (int i = 1; i <= 10; i++) {
            mG.addNode("m" + i);
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
        mG.addNeighbor("m1", "m2");
        mG.addNeighbor("m1", "m3");
        mG.addNeighbor("m2", "m4");
        mG.addNeighbor("m2", "m5");
        mG.addNeighbor("m3", "m6");
        mG.addNeighbor("m4", "m7");
        mG.addNeighbor("m4", "m8");
        mG.addNeighbor("m4", "m9");
        mG.addNeighbor("m5", "m10");

        //make tg with prev filled and conn graph
        TraverseGraph<String> tg = new TraverseGraph<>(mG);

        //check if it finds all hyponyms
        assertThat(tg.findHyponyms("m2")).isEqualTo(List.of("m2", "m4", "m5", "m7", "m8", "m9", "m10"));

        //make graph cyclic
        mG.addNeighbor("m9", "m2");

        TraverseGraph<String>  tg1 = new TraverseGraph<>(mG);

        //check if traverseGraph finds a bad graph state
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> {
                    tg1.findHyponyms("m2");
                });

        Assertions.assertEquals("Graph is not acyclic", exception.getMessage());
    }
}
