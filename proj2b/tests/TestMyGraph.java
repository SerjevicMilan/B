import org.junit.jupiter.api.Test;
import wnet.MyGraph;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestMyGraph {

    @Test
    public void TestGraph() {
        MyGraph mG = new MyGraph();

        assertThat(mG.size()).isEqualTo(0);

        //test filed graph no connection
        for (int i = 1; i <= 10; i++) {
            mG.addNode(i);
        }

        assertThat(mG.size()).isEqualTo(10);

        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertThat(mG.toList()).isEqualTo(expected);
        for (int i = 1; i <= 10; i++) {
            assertThat(mG.isConnected(1, i)).isFalse();
        }

        mG.addNeighbor(1, 2);
        assertThat(mG.isConnected(1, 2)).isTrue();
        assertThat(mG.isConnected(22, 2)).isFalse();//test bad input


        //test getting neighbors
        mG.addNeighbor(1, 3);
        mG.addNeighbor(2, 4);
        mG.addNeighbor(2, 5);
        mG.addNeighbor(3, 6);
        mG.addNeighbor(4, 7);
        mG.addNeighbor(4, 8);
        mG.addNeighbor(4, 9);
        mG.addNeighbor(5, 10);

        expected = List.of(7, 8, 9);
        assertThat(mG.getAdjacent(4)).isEqualTo(expected);
    }

    //test methods with empty graph
    @Test
    public void TestEmptyGraph() {
        MyGraph mG = new MyGraph();

        //test empty graph
        assertThat(mG.toList()).isEqualTo(List.of());
        assertThat(mG.getAdjacent(1)).isEqualTo(List.of());
        assertThat(mG.getAdjacent(-1)).isEqualTo(List.of());
        assertThat(mG.isConnected(1 , 2)).isFalse();
        assertThat(mG.isConnected(-1 , 2)).isFalse();

        try {
            mG.addNeighbor(1, 2);
            mG.addNeighbor(-1, 2);
        } catch (Exception e) {

        }

        mG.addNeighbor(1, 2);
        mG.addNeighbor(-1, 2);
        assertThat(mG.isConnected(1, 2)).isFalse();
        assertThat(mG.isConnected(-1, 2)).isFalse();
    }

    @Test
    public void TestBadInput() {
        MyGraph mG = new MyGraph();

        mG.addNode(-1);
        assertThat(mG.toList()).isEqualTo(List.of());

        for (int i = 1; i <= 10; i++) {
            mG.addNode(i);
        }

        try {
            mG.addNeighbor(1, 11);
        }
        catch (Exception e) {
            ;
        }
        assertThat(mG.isConnected(1, 11)).isFalse();

        assertThat(mG.isConnected(-1,2)).isFalse();
        assertThat(mG.isConnected(2, -1)).isFalse();
    }


}
