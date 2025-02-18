import com.google.common.truth.Truth;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertWithMessage;

public  class ArrayDeque61BTest {

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

        Truth.assertThat(arr1.toList()).containsExactly();

        arr1.addFirst(6);
        arr1.addFirst(5);
        arr1.addFirst(4);
        arr1.addFirst(3);
        arr1.addFirst(2);
        arr1.addFirst(1);

        Truth.assertThat(arr1.toList()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    void addLastTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        Truth.assertThat(arr1.toList()).containsExactly();

        arr1.addLast(6);
        arr1.addLast(5);
        arr1.addLast(4);
        arr1.addLast(3);
        arr1.addLast(2);
        arr1.addLast(1);

        Truth.assertThat(arr1.toList()).containsExactly(6, 5, 4, 3, 2, 1);
    }
    @Test
    void addFirstLastTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        Truth.assertThat(arr1.toList()).containsExactly();

        arr1.addLast(6);
        arr1.addFirst(5);
        arr1.addLast(4);
        arr1.addFirst(3);
        arr1.addLast(2);
        arr1.addFirst(1);

        Truth.assertThat(arr1.toList()).containsExactly(1, 3, 5, 6, 4, 2);
    }

    @Test
    void getTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        for(int i = 1; i <= 6; i++) {
            arr1.addLast(i);
        }

        Truth.assertThat(arr1.get(0)).isEqualTo(1);
        Truth.assertThat(arr1.get(5)).isEqualTo(6);
        Truth.assertThat(arr1.get(2)).isEqualTo(3);
        Truth.assertThat(arr1.get(-2)).isEqualTo(null);
        Truth.assertThat(arr1.get(10)).isEqualTo(null);

        arr1.removeFirst();
        Truth.assertThat(arr1.get(0)).isEqualTo(2);

        arr1.removeLast();
        Truth.assertThat(arr1.get(arr1.size() - 1)).isEqualTo(5);

        //down are test after resize
        ArrayDeque61B<Integer> arr2 = new ArrayDeque61B<>();

        for (int i = 1; i <= 20; i++){
            arr2.addLast(i);
        }
        Truth.assertThat(arr2.get(19)).isEqualTo(20);
        Truth.assertThat(arr2.get(0)).isEqualTo(1);
        Truth.assertThat(arr2.get(20)).isEqualTo(null);

        for (int i = 1; i <= 14; i++){
            arr2.removeLast();
        }
        Truth.assertThat(arr2.get(0)).isEqualTo(1);
        Truth.assertThat(arr2.get(5)).isEqualTo(6);
        Truth.assertThat(arr2.get(6)).isEqualTo(null);
    }

    @Test
    void isEmptySizeTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        Truth.assertThat(arr1.isEmpty()).isTrue();
        Truth.assertThat(arr1.size()).isEqualTo(0);


        for(int i = 1; i <= 6; i++) {
            arr1.addLast(i);
        }

        Truth.assertThat(arr1.size()).isEqualTo(6);
        Truth.assertThat(arr1.isEmpty()).isFalse();

        arr1.removeFirst();
        Truth.assertThat(arr1.size()).isEqualTo(5);

        arr1.removeLast();
        Truth.assertThat(arr1.size()).isEqualTo(4);

        for(int i = 0; i < 2; i++) {
            arr1.removeFirst();
            arr1.removeLast();
        }
        Truth.assertThat(arr1.size()).isEqualTo(0);

        arr1.removeFirst();
        arr1.removeLast();
        Truth.assertThat(arr1.size()).isEqualTo(0);

        //down are test after resize

        ArrayDeque61B<Integer> arr2 = new ArrayDeque61B<>();

        for (int i = 1; i <= 20; i++){
            arr2.addLast(i);
        }
        Truth.assertThat(arr2.size()).isEqualTo(20);

        for (int i = 1; i <= 14; i++){
            arr2.removeLast();
        }
        Truth.assertThat(arr2.size()).isEqualTo(6);

    }

    @Test
    void removeFirstTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        for(int i = 1; i <= 6; i++) {
            arr1.addLast(i);
        }

        arr1.removeFirst();
        Truth.assertThat(arr1.toList()).containsExactly(2, 3, 4, 5, 6);

        arr1.removeFirst();
        arr1.removeFirst();
        Truth.assertThat(arr1.removeFirst()).isEqualTo(4);

        arr1.removeFirst();
        Truth.assertThat(arr1.toList()).containsExactly(6);

        arr1.removeFirst();
        Truth.assertThat(arr1.toList()).containsExactly();

        Truth.assertThat(arr1.removeFirst()).isEqualTo(null);
    }

    @Test
    void removeLastTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        for(int i = 1; i <= 6; i++) {
            arr1.addLast(i);
        }

        arr1.removeLast();
        Truth.assertThat(arr1.toList()).containsExactly(1, 2, 3, 4, 5);

        arr1.removeLast();
        arr1.removeLast();
        Truth.assertThat(arr1.removeLast()).isEqualTo(3);

        arr1.removeLast();
        Truth.assertThat(arr1.toList()).containsExactly(1);

        arr1.removeLast();
        Truth.assertThat(arr1.toList()).containsExactly();

        Truth.assertThat(arr1.removeLast()).isEqualTo(null);
    }

    @Test
    void resizeUpTest(){
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        for (int i = 1; i <= 9; i++){
            arr1.addLast(i);
        }
        Truth.assertThat(arr1.toList()).containsExactly(1,2,3,4,5,6,7,8,9);
    }

    @Test
    void resizeDownTest(){
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        for (int i = 1; i <= 9; i++){
            arr1.addLast(i);
        }
        for (int i = 1; i <= 5; i++){
            arr1.removeLast();
        }
        Truth.assertThat(arr1.toList()).containsExactly(1,2,3,4);
    }

    @Test
    void circularBufferTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();

        for (int i = 1; i <= 8; i++) { // Fill up to initial capacity
            arr.addLast(i);
        }
        arr.removeFirst(); // Make space for wraparound
        arr.addLast(9);

        Truth.assertThat(arr.toList()).containsExactly(2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void removeFromEmptyDequeTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        // Ensure the deque is empty
        Truth.assertThat(arr1.isEmpty()).isTrue();
        Truth.assertThat(arr1.size()).isEqualTo(0);

        // Try removing from an empty deque
        Truth.assertThat(arr1.removeFirst()).isNull();
        Truth.assertThat(arr1.removeLast()).isNull();

        // Ensure size remains 0 after attempting to remove
        Truth.assertThat(arr1.size()).isEqualTo(0);
        Truth.assertThat(arr1.isEmpty()).isTrue();
    }

    @Test
    void resizeAndAccessTest() {
        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();

        // Step 1: Fill up the deque to trigger resizing up
        for (int i = 1; i <= 16; i++) {
            arr1.addLast(i);
        }
        Truth.assertThat(arr1.size()).isEqualTo(16);
        Truth.assertThat(arr1.get(0)).isEqualTo(1);
        Truth.assertThat(arr1.get(15)).isEqualTo(16);

        // Step 2: Remove elements to trigger resizing down
        for (int i = 1; i <= 12; i++) {
            arr1.removeFirst();
        }
        Truth.assertThat(arr1.size()).isEqualTo(4);
        Truth.assertThat(arr1.get(0)).isEqualTo(13);
        Truth.assertThat(arr1.get(3)).isEqualTo(16);

        // Step 3: Ensure further operations work correctly
        arr1.addFirst(100);
        arr1.addLast(200);
        Truth.assertThat(arr1.get(0)).isEqualTo(100);
        Truth.assertThat(arr1.get(5)).isEqualTo(200);
    }
}

