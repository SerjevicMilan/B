import java.util.Iterator;
import java.util.Set;


//implementation of BSTMap
public class BSTMap<K extends Comparable<K> , V> implements Map61B<K, V>{
    private BSTNode root;
    private int size;

    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        private BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    public BSTMap () {
        root = null;
        size = 0;
    }

    //putting new val in Map
    @Override
    public void put(K key, V value) {
        if (!containsKey(key))//size does not change for existing key
            size += 1;
        root = put(key, value, root);
    }

    //finding leaf node or matching key and updating map recursively
    private BSTNode put(K key, V value, BSTNode root) {
        if (root == null) {//leaf node or empty root
            return new BSTNode(key, value);
        }
        if (key.compareTo(root.key) > 0) {
            root.right = put(key, value, root.right);
        }
        else if (key.compareTo(root.key) < 0) {
            root.left = put(key, value, root.left);
        }
        else {// matching key
            root.value = value;
        }
        return root;
    }

    @Override
    public V get(K key) {
        return get(key, root);
    }

    //matching key and returning val or returning null
    private V get(K key, BSTNode root) {
        if (root == null) {//leaf node or empty root
            return null;
        }
        if (key.compareTo(root.key) == 0) {
            return root.value;
        }

        if (key.compareTo(root.key) > 0) {
            return get(key, root.right);
        }
        else {
            return get(key,root.left);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(key, root);
    }

    //if key matched return true if leaf is reached return false
    private boolean containsKey(K key, BSTNode root) {
        if (root == null) {//leaf node or empty root
            return false;
        }
        if (key.compareTo(root.key) == 0) {
            return true;
        }

        if (key.compareTo(root.key) > 0) {
            return containsKey(key, root.right);
        }
        else {
            return containsKey(key,root.left);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
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
