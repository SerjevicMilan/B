import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
    @Test
    void addFirstTest() {
       ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

       assertThat(arr1.toList()).containsExactly();

        arr1.addFirst(6);
        arr1.addFirst(5);
        arr1.addFirst(4);
        arr1.addFirst(3);
        arr1.addFirst(2);
        arr1.addFirst(1);

        assertThat(arr1.toList()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void addLastTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        assertThat(arr1.toList()).containsExactly();

        arr1.addLast(6);
        arr1.addLast(5);
        arr1.addLast(4);
        arr1.addLast(3);
        arr1.addLast(2);
        arr1.addLast(1);

        assertThat(arr1.toList()).containsExactly(6, 5, 4, 3, 2, 1);
    }
    @Test
    void addFirstLastTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        assertThat(arr1.toList()).containsExactly();

        arr1.addLast(6);
        arr1.addFirst(5);
        arr1.addLast(4);
        arr1.addFirst(3);
        arr1.addLast(2);
        arr1.addFirst(1);

        assertThat(arr1.toList()).containsExactly(1, 3, 5, 6, 4, 2);
    }

    @Test
    void getTest() {
         ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

         for(int i = 1; i <= 6; i++) {
             arr1.addLast(i);
         }

        assertThat(arr1.get(0)).isEqualTo(1);
        assertThat(arr1.get(5)).isEqualTo(6);
        assertThat(arr1.get(2)).isEqualTo(3);
        assertThat(arr1.get(-2)).isEqualTo(null);
        assertThat(arr1.get(10)).isEqualTo(null);

        //check after resize and remove
    }

    @Test
    void isEmptySizeTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        assertThat(arr1.isEmpty()).isTrue();
        assertThat(arr1.size()).isEqualTo(0);


        for(int i = 1; i <= 6; i++) {
            arr1.addLast(i);
        }

        assertThat(arr1.size()).isEqualTo(6);
        assertThat(arr1.isEmpty()).isFalse();

        // check for remove and resize
    }

    @Test
    void removeFirstTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        for(int i = 1; i <= 6; i++) {
            arr1.addLast(i);
        }

        arr1.removeFirst();
        assertThat(arr1.toList()).containsExactly(2, 3, 4, 5, 6);
        arr1.removeFirst();
        arr1.removeFirst();
        arr1.removeFirst();
        arr1.removeFirst();
        arr1.removeFirst();
        assertThat(arr1.toList()).containsExactly();
    }

}
