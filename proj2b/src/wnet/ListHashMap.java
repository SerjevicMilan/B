package wnet;

import java.util.HashMap;

/*
extends on HashMap put so we don't replace Array but add to it
 */
public class ListHashMap<K, V> extends HashMap<K, V> {


    // if contains key we get array of values and add new value to it
    @Override
    public V put (K k, V v) {
        return null;
    }
}
