import org.junit.jupiter.api.Test;
import wnet.myGraph;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class TestMyGraph {

    @Test
    public void TestGraph() {
        myGraph mG = new myGraph();

        //test empty graph
        assertThat(mG.toList()).isEqualTo(List.of());


        //test filed graph no connection
        for (int i = 1; i <= 10; i++) {
            mG.addNode(i);
        }
        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertThat(mG.toList()).isEqualTo(expected);
        for (int i = 1; i <= 10; i++) {
            assertThat(mG.isConnected(1, i)).isFalse();
        }

        mG.addNeighbor(1, 2);
        assertThat(mG.isConnected(1, 2)).isTrue();
        assertThat(mG.isConnected(22, 2)).isFalse();//test bad input
    }
}
