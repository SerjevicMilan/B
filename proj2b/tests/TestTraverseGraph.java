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
        MyGraph<Integer> mG = new MyGraph<>();
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
        mG.addSynonyms(1, "a");

        mG.addNeighbor(1, 3);
        mG.addSynonyms(1, "b");

        mG.addNeighbor(2, 4);
        mG.addSynonyms(2, "c");

        mG.addNeighbor(2, 5);
        mG.addSynonyms(2, "d");

        mG.addNeighbor(3, 6);
        mG.addSynonyms(3, "e");

        mG.addNeighbor(4, 7);
        mG.addSynonyms(4, "f");

        mG.addNeighbor(4, 8);
        mG.addSynonyms(4, "g");

        mG.addNeighbor(4, 9);
        mG.addSynonyms(4, "h");

        mG.addNeighbor(5, 10);
        mG.addSynonyms(5, "i");

        //make tg with prev filled and conn graph
        TraverseGraph<Integer> tg = new TraverseGraph<>(mG);

        //check if it finds all hyponyms
        assertThat(tg.findHyponyms(List.of(2))).isEqualTo(List.of("c", "d", "f", "g", "h", "i"));
    }
}
