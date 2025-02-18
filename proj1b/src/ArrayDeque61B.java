import java.util.ArrayList;
import java.util.Iterator;
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

    @Override
    public Iterator<T> iterator() {
        return new ADIterator();
    }

    private class ADIterator<T> implements Iterator<T> {
        private int pos = 0;

        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            T reItem =(T) get(pos);
            pos++;
            return reItem;
        }
    }

    //add first update next position and size
    @Override
    public void addFirst(T x) {
        if(x == null)
            return;

        if(size == items.length)
            resizeUp();
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

        if(size == items.length)
            resizeUp();
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
        if(size == 0)
            return null;

        T reVal = items[Math.floorMod(nextFirst + 1, items.length)];
        items[Math.floorMod(nextFirst + 1, items.length)] = null;
        nextFirst++;
        size--;

        if (items.length > 8 && (double) size / items.length <= 0.25)
            resizeDown();

        return reVal;
    }

    @Override
    public T removeLast() {
        if(size == 0)
            return null;

        T reVal = items[Math.floorMod(nextLast - 1, items.length)];
        items[Math.floorMod(nextLast - 1, items.length)] = null;
        nextLast--;
        size--;

        if (items.length > 8 && (double) size / items.length <= 0.25)
            resizeDown();

        return reVal;
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
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private void resizeUp() {
        T[] newArr =  (T[]) new Object[size * 2];
        for(int i = 0; i < size; i++) {
            newArr[i] = get(i);
        }
        items = newArr;
        nextFirst = -1;
        nextLast = size;
    }

    private void resizeDown() {
        T[] newArr =  (T[]) new Object[items.length / 2];
        for(int i = 0; i < size; i++) {
            newArr[i] = get(i);
        }
        items = newArr;
        nextFirst = -1;
        nextLast = size;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof ArrayDeque61B otherObj) {
            if (this.size != otherObj.size()) {
                return false;
            }
            for (T x : this) {
                for (int i = 0; i < otherObj.size(); i++){
                    if (x == otherObj.get(i)) {
                        break;
                    }
                    if (i == otherObj.size() - 1)
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toList().toString();
    }
}