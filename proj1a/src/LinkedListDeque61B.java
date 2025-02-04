import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private Node sentinal;
    private int size = 0;

    private class Node{
        private Node prev;
        private T value;
        private Node next;

        public Node(T value) {
            this.prev = null;
            this.value = value;
            this.next = null;
        }
    }

    public LinkedListDeque61B() {
        this.sentinal = new Node(null);  // Initialize sentinel with null value
        this.sentinal.next = this.sentinal;
        this.sentinal.prev = this.sentinal;
    }

    @Override
    public void addFirst(T x) {
        Node firstN = new Node(x);
        firstN.next = sentinal.next;
        firstN.prev = sentinal;
        sentinal.next.prev = firstN;
        sentinal.next = firstN;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node lastN = new Node(x);
        lastN.prev = sentinal.prev;
        lastN.next = sentinal;
        sentinal.prev.next = lastN;
        sentinal.prev = lastN;
        size++;
    }

    @Override
    public List<T> toList() {
        Node deq = sentinal.next;
        List<T> list = new ArrayList<>(size);
        while (deq != sentinal) {
            list.add(deq.value);
            deq = deq.next;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}