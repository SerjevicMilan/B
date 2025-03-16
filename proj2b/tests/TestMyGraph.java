import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wnet.MyGraph;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.assertEquals;
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


        //connecting nodes that do not exist
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    mG.addNeighbor(1, 2);
                });

        Assertions.assertEquals("nodes are not in graph", exception.getMessage());

        //connecting nodes that do not exist
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    mG.addNeighbor(-1, 2);
                });


        assertThat(mG.isConnected(1, 2)).isFalse();
        assertThat(mG.isConnected(-1, 2)).isFalse();
    }

    @Test
    public void TestBadInput() {
        MyGraph mG = new MyGraph();

        //adding  negative node
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    mG.addNode(-1);
                });


        Assertions.assertEquals("only positive values allowed", exception.getMessage());


        //filling up graph
        for (int i = 1; i <= 10; i++) {
            mG.addNode(i);
        }


        //connecting after adding with none existing node
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    mG.addNeighbor(1, 11);
                });


        Assertions.assertEquals("nodes are not in graph", exception.getMessage());
    }


}
