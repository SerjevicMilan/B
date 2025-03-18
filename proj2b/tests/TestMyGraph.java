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
        MyGraph<String> mG = new MyGraph<>();

        assertThat(mG.size()).isEqualTo(0);

        //test filed graph no connection
        for (int i = 1; i <= 10; i++) {
            mG.addNode("m" + i);
        }

        assertThat(mG.size()).isEqualTo(10);

        List<String> expected = List.of("m1", "m2", "m3", "m4", "m5", "m6", "m7", "m8", "m9", "m10");
        assertThat(mG.toList()).isEqualTo(expected);
        for (int i = 1; i <= 10; i++) {
            assertThat(mG.isConnected("m1", "m" + i)).isFalse();
        }

        mG.addNeighbor("m1", "m2");
        assertThat(mG.isConnected("m1", "m2")).isTrue();
        assertThat(mG.isConnected("m22", "m2")).isFalse();//test bad input


        //test getting neighbors
        mG.addNeighbor("m1", "m3");
        mG.addNeighbor("m2", "m4");
        mG.addNeighbor("m2", "m5");
        mG.addNeighbor("m3", "m6");
        mG.addNeighbor("m4", "m7");
        mG.addNeighbor("m4", "m8");
        mG.addNeighbor("m4", "m9");
        mG.addNeighbor("m5", "m10");

        expected = List.of("m7", "m8", "m9");
        assertThat(mG.getAdjacent("m4")).isEqualTo(expected);

        //test adding and getting synonyms
        mG.addSynonyms("m4", "m71");
        mG.addSynonyms("m4", "m81");
        mG.addSynonyms("m4", "m91");

        expected = List.of("m71", "m81", "m91");
        assertThat(mG.getSynonyms("m4")).isEqualTo(expected);
    }

    //test methods with empty graph
    @Test
    public void TestEmptyGraph() {
        MyGraph<String> mG = new MyGraph<>();

        //test empty graph
        assertThat(mG.toList()).isEqualTo(List.of());
        assertThat(mG.getAdjacent("m1")).isEqualTo(List.of());
        assertThat(mG.getAdjacent("m-1")).isEqualTo(List.of());
        assertThat(mG.isConnected("m1" , "m2")).isFalse();
        assertThat(mG.isConnected("m-1" , "m2")).isFalse();


        //connecting nodes that do not exist
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    mG.addNeighbor("m1", "m2");
                });

        Assertions.assertEquals("nodes are not in graph", exception.getMessage());

        //connecting nodes that do not exist
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    mG.addNeighbor("m-1", "m2");
                });


        assertThat(mG.isConnected("m1", "m2")).isFalse();
        assertThat(mG.isConnected("m-1", "m2")).isFalse();
    }

    @Test
    public void TestBadInput() {
        MyGraph<String> mG = new MyGraph<>();


        //filling up graph
        for (int i = 1; i <= 10; i++) {
            mG.addNode("m" + i);
        }


        //connecting after adding with none existing node
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    mG.addNeighbor("m1", "m11");
                });


        Assertions.assertEquals("nodes are not in graph", exception.getMessage());
    }


}
