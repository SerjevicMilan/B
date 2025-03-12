package wnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
extends on HashMap put so we don't replace Array but add to it
 */
public class ListHashMap<K, V> extends HashMap<K, List<V>> {


    /*
     if contains key we get array of values and add new value to it
     else we just add new key value(array of V) pair
     @param k
     key for hashMap
     @param v
     Value for hashMap(List of V)
     @return
     List of Values
     */
    @Override
    public List<V> put (K k, List <V> v) {
        if (v == null || k == null || v.contains(null))
            return null;
        if(super.containsKey(k)) {
            List<V> list = super.get(k);
            list.add(v.get(0));
            return list;
        }
        else {
            return super.put(k, v);
        }
    }

    /*
    Takes single value and turns it in array list then calls put method on it
    @param k
    key for hashMap
    @param v
    Value for hashMap(make it a single element list)
     */
    public void add(K k, V v) {
        if (k == null || v == null)
            return;
        put(k, new ArrayList<V>(List.of(v)));
    }


}
