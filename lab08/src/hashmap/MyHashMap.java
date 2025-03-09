package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }

        @Override
        public boolean equals(Object key) {
            if (key == null || this.key.getClass() != key.getClass())
                return false;
            K k = (K) key;
            if (this.key.equals(k))
                return true;
            return false;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int Capacity = 16;
    private double loadFactor = 0.75;
    private int size = 0;

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[Capacity];
    }

    public MyHashMap(int initialCapacity) {
        Capacity = initialCapacity;
        buckets = new Collection[initialCapacity];
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        Capacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[initialCapacity];
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
            return new LinkedList<>();
    }
    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void put(K key, V value) {
        if ((double)  size / Capacity >= loadFactor)
            resize();
        Node n = createNode(key, value);
        put(reduce(key), n);
    }

    private void put(int pos, Node n) {
        if (buckets[pos] == null) {
            buckets[pos] = createBucket();
        }
        Collection<Node> bucket = buckets[pos];

        for (Node node : bucket) {
            if(node.equals(n.key)) {
                node.value = n.value;
                return;
            }
        }
        size += 1;
        bucket.add(n);
    }

    private Node createNode(K key, V value) {
        return new Node(key ,value);
    }

    private int reduce(K key) {
        int hash = key.hashCode();
        return Math.floorMod(hash, Capacity);
    }

    @Override
    public V get(K key) {
        if (!containsKey(key))
            return null;
        int pos = reduce(key);
        Collection <Node> bucket = buckets[pos];

        for (Node node : bucket) {
            if (node.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int pos = reduce(key);
        Collection<Node> bucket = buckets[pos];
        if (bucket == null)
            return false;

        for (Node node : bucket) {
            if (node.equals(key))
                return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private Collection[] createBuckets() {
        return new Collection[Capacity];
    }

    @Override
    public void clear() {
        buckets = createBuckets();
        size = 0;
    }

    private void resize() {
        Capacity *= 2;
        Collection[] oldBuckets = buckets;
        buckets = createBuckets();
        for (int i = 0; i < size; i++) {
            Collection<Node> bucket = oldBuckets[i];
            if (bucket == null)
                continue;
            for (Node node : bucket) {
                size -= 1;
                put(node.key, node.value);
            }
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
