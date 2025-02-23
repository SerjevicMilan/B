import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import java.util.Comparator;

public class MaxArrayDeque61BTest {

    private static class Dog {
        int size;
        String name;
        String race;

        public Dog(int s, String n, String r) {
            size = s;
            name = n;
            race = r;
        }
    }

    private static class dogNameCompare implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
        }
    }

    private static class dogSizeCompare implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.size - b.size;
        }
    }

    private static class stringCompare implements Comparator<String> {
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    private static class intCompare implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    @Test
    public void basicMaxTest() {
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        for (int i = 0; i <= 10; i++) {
            m.addLast(i);
        }

        assertThat(m.max()).isEqualTo(10);
        assertThat(m.max(new intCompare())).isEqualTo(10);

        MaxArrayDeque61B<String> sm = new MaxArrayDeque61B<String>(new stringCompare());

        for (int i = 0; i <= 3; i++) {
            sm.addLast("A" + i);
        }

        assertThat(sm.max()).isEqualTo("A3");
    }

    @Test
    public void dogMaxTest() {
        MaxArrayDeque61B<Dog> m = new MaxArrayDeque61B<Dog>(new dogNameCompare());

        m.addLast(new Dog(10, "jeki", "mutt"));
        m.addLast(new Dog(5, "lara", "huski"));
        m.addLast(new Dog(15, "dzoni", "german shepard"));

        assertThat(m.max().name).isEqualTo("lara");

        assertThat(m.max(new dogSizeCompare()).name).isEqualTo("dzoni");

    }

    @Test
    public void emptyAndMullMaxTest() {
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        assertThat(m.max()).isEqualTo(null);

        for (int i = 0; i <= 10; i++) {
            m.addLast(i);
        }
        for (int i = 0; i <= 10; i++) {
            m.addLast(i);
        }

        assertThat(m.max()).isEqualTo(10);

        MaxArrayDeque61B<Integer> sm = new MaxArrayDeque61B<Integer>(null);

        for (int i = 0; i <= 10; i++) {
            sm.addLast(i);
        }

        assertThat(sm.max()).isEqualTo(10);
        assertThat(sm.max(null)).isEqualTo(10);

    }
}