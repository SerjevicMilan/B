import deque.*;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;



/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }
    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    /** Test for empty deque buy adding and removing elements */
    public void isEmptyTest() {
        Deque61B<Integer> deq1 = new LinkedListDeque61B<>();

        deq1.addLast(0);   // [0]
        deq1.addLast(1);   // [0, 1]
        deq1.addFirst(-1); // [-1, 0, 1]
        deq1.addLast(2);   // [-1, 0, 1, 2]
        deq1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(deq1.isEmpty()).isFalse();

        deq1.removeFirst();
        deq1.removeLast();

        assertThat(deq1.isEmpty()).isFalse();

        deq1.removeLast();
        deq1.removeFirst();
        deq1.removeFirst();

        assertThat(deq1.isEmpty()).isTrue();

        deq1.removeLast();

        assertThat(deq1.isEmpty()).isTrue();

        Deque61B<Integer> deq2 = new LinkedListDeque61B<>();

        assertThat(deq2.isEmpty()).isTrue();

        //needs more tests later
    }
    @Test
    public void sizeTest() {
        Deque61B<Integer> deq1 = new LinkedListDeque61B<>();

        deq1.addLast(0);   // [0]
        deq1.addLast(1);   // [0, 1]
        deq1.addFirst(-1); // [-1, 0, 1]
        deq1.addLast(2);   // [-1, 0, 1, 2]
        deq1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(deq1.size()).isEqualTo(5);

        deq1.removeFirst();
        deq1.removeLast();

        assertThat(deq1.size()).isEqualTo(3);

        deq1.removeLast();
        deq1.removeFirst();
        deq1.removeFirst();

        assertThat(deq1.size()).isEqualTo(0);

        deq1.removeLast();

        assertThat(deq1.size()).isEqualTo(0);

        Deque61B<Integer> deq2 = new LinkedListDeque61B<>();

        assertThat(deq2.size()).isEqualTo(0);

        //needs more tests later
    }

    @Test
    public void getTest() {
        Deque61B<Integer> deq1 = new LinkedListDeque61B<>();

        deq1.addLast(0);   // [0]
        deq1.addLast(1);   // [0, 1]
        deq1.addFirst(-1); // [-1, 0, 1]
        deq1.addLast(2);   // [-1, 0, 1, 2]
        deq1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(deq1.get(0)).isEqualTo(-2);
        assertThat(deq1.get(4)).isEqualTo(2);
        assertThat(deq1.get(-1)).isEqualTo(null);
        assertThat(deq1.get(5)).isEqualTo(null);

        Deque61B<Integer> deq2 = new LinkedListDeque61B<>();

        assertThat(deq2.get(0)).isEqualTo(null);

        //needs more tests later
    }

    @Test
    public void getRecursiveTest() {
        Deque61B<Integer> deq1 = new LinkedListDeque61B<>();

        deq1.addLast(0);   // [0]
        deq1.addLast(1);   // [0, 1]
        deq1.addFirst(-1); // [-1, 0, 1]
        deq1.addLast(2);   // [-1, 0, 1, 2]
        deq1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(deq1.getRecursive(0)).isEqualTo(-2);
        assertThat(deq1.getRecursive(4)).isEqualTo(2);
        assertThat(deq1.getRecursive(-1)).isEqualTo(null);
        assertThat(deq1.getRecursive(5)).isEqualTo(null);

        Deque61B<Integer> deq2 = new LinkedListDeque61B<>();

        assertThat(deq2.getRecursive(0)).isEqualTo(null);

        //needs more tests later
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        //filing deq with elements
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        //checking return value and deq state
        assertThat(lld1.removeFirst()).isEqualTo(-2);
        assertThat(lld1.toList()).containsExactly(-1, 0, 1, 2).inOrder();

        /* chek for remove from empty deq */
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addLast(0);   // [0]

        assertThat(lld2.removeFirst()).isEqualTo(0);
        assertThat(lld2.removeFirst()).isEqualTo(null);
        assertThat(lld2.toList()).containsExactly().inOrder();
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        //filing deq with elements
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        //checking return value and deq state
        assertThat(lld1.removeLast()).isEqualTo(2);
        assertThat(lld1.toList()).containsExactly(-2,-1, 0, 1).inOrder();

        /* chek for remove from empty deq */
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addLast(0);   // [0]

        assertThat(lld2.removeLast()).isEqualTo(0);
        assertThat(lld2.removeLast()).isEqualTo(null);
        assertThat(lld2.toList()).containsExactly().inOrder();
    }

    @Test
    public void removeLastFirstTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        //filing deq with elements
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        //checking return value and deq state
        assertThat(lld1.removeLast()).isEqualTo(2);
        assertThat(lld1.toList()).containsExactly(-2,-1, 0, 1).inOrder();

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(-1, 0, 1).inOrder();

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly().inOrder();

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly().inOrder();

        /* chek for remove from empty deq */
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addLast(0);   // [0]

        assertThat(lld2.removeLast()).isEqualTo(0);
        assertThat(lld2.removeLast()).isEqualTo(null);
        assertThat(lld2.toList()).containsExactly().inOrder();
    }

    @Test
    public void recursiveGetLargeTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        for (int i = 0; i < 500; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.getRecursive(499)).isEqualTo(499);
        assertThat(lld1.getRecursive(500)).isEqualTo(null);
    }

    @Test
    public void reverseOrderTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("third");
        lld1.addFirst("second");
        lld1.addFirst("first");
        assertThat(lld1.removeLast()).isEqualTo("third");
        assertThat(lld1.removeLast()).isEqualTo("second");
        assertThat(lld1.removeLast()).isEqualTo("first");
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void addNullElementTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        try {
            lld1.addFirst(null);
            assertWithMessage("Deque should not accept null elements").fail();
        } catch (NullPointerException e) {
            assertThat(true).isTrue(); // Pass if exception is thrown
        }
    }

    @Test
    public void largeNumberOfElementsTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        for (int i = 0; i < 1000; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.size()).isEqualTo(1000);
        assertThat(lld1.get(999)).isEqualTo(999);
        for (int i = 0; i < 1000; i++) {
            lld1.removeFirst();
        }
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void multipleRemovalsFromEmptyDeque() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void stressTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        for (int i = 0; i < 10000; i++) {
            lld1.addFirst(i);
            assertThat(lld1.removeFirst()).isEqualTo(i);
        }
        assertThat(lld1.isEmpty()).isTrue();
    }


}

