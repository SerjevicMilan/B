package deque;
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
        if (x == null) {
            throw new NullPointerException("Variable cannot be null");
        }

        Node firstN = new Node(x);
        firstN.next = sentinal.next;
        firstN.prev = sentinal;
        sentinal.next.prev = firstN;
        sentinal.next = firstN;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (x == null) {
            throw new NullPointerException("Variable cannot be null");
        }

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
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(size == 0)
            return null;
        Node first = sentinal.next;
        sentinal.next = first.next;
        first.next.prev = sentinal;
        size--;
        return first.value;
    }

    @Override
    public T removeLast() {
        if(size == 0)
            return null;
        Node last = sentinal.prev;
        sentinal.prev = last.prev;
        last.prev.next = sentinal;
        size--;
        return last.value;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size)
            return null;
        Node start = sentinal.next;
        while (index > 0) {
            start = start.next;
            index--;
        }
        return start.value;
    }

    @Override
    public T getRecursive(int index) {
        if(index < 0 || index >= size)
            return null;
        return getRecursive(sentinal.next, index);
    }

    private T getRecursive(Node deq, int index) {
        if (index == 0) {
            return deq.value;
        }
        return getRecursive(deq.next, index - 1);
    }

}