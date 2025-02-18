import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import java.util.Comparator;

public class MaxArrayDeque61BTest {

    @Test
    public void basicMaxTest() {
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        for (int i = 0; i <= 10; i++) {
            m.addLast(i);
        }

        assertThat(m.max()).isEqualTo(10);
    }
}
