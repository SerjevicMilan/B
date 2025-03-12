import org.junit.jupiter.api.Test;
import wnet.ListHashMap;
import wnet.MyGraph;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class TestListHashMap {
    @Test
    public void TestLHM() {
        ListHashMap<String, Integer> lhm = new ListHashMap<>();

        //test if it adds to the list
        lhm.add("Milan", 1);

        assertThat(lhm.get("Milan")).isEqualTo(List.of(1));

        lhm.add("Milan", 1);
        lhm.add("Milan", 1);

        assertThat(lhm.get("Milan")).isEqualTo(List.of(1, 1, 1));

        //test for bad inputs
        assertThat(lhm.put("Milan", null)).isEqualTo(null);

        List<Integer> arrayWithNull = new ArrayList<>(List.of(1, 2));
        arrayWithNull.add(null);
        assertThat(lhm.put("Milan", arrayWithNull)).isEqualTo(null);
        assertThat(lhm.put(null, List.of(1, 1, 1))).isEqualTo(null);
        lhm.add("Ser", null);
        lhm.add(null, 1);
        assertThat(lhm.containsKey("Ser")).isFalse();
        assertThat(lhm.containsKey(null)).isFalse();

    }
}
