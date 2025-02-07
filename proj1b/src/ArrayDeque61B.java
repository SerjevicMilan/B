import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T>{
   private T[] items;
   private int size;
   private int nextFirst;
   private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    //add first update next position and size
    @Override
    public void addFirst(T x) {
        if(x == null)
            return;
        //add resize if size == items.length

        items[Math.floorMod(nextFirst, items.length)] = x;
        nextFirst--;
        size++;
    }

    //add last update next position and size
    @Override
    public void addLast(T x) {
        if(x == null)
            return;

        //add resize if size == items.length

        items[Math.floorMod(nextLast, items.length)] = x;
        nextLast++;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>(size);
        for(int i = nextFirst + 1; i < nextLast; i++) {
            list.add(items[Math.floorMod(i, items.length)]);// replace later with get
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    //starting point "nextFirst + 1" + index and mod it so you can circle in array
    @Override
    public T get(int index) {
        if(index < 0 || index >= size)
            return null;
        return items[Math.floorMod(index + nextFirst + 1, items.length)];
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}