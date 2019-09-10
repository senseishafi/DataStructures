

import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collections;

public class BalancedMap<K extends Comparable<K>, V> implements IMap<K, V> {

    protected TreeMap<K,V> treeMap;

    public BalancedMap() {
         treeMap = new TreeMap<K,V>();

    }

    public BalancedMap(IMap<K,V> iMap) {
        treeMap = new TreeMap<K,V>();
        for(K key: iMap.keyset()) {
            treeMap.put(key, iMap.getValue(key));

        }
    }

    @Override
    public boolean contains(K key) {
        return treeMap.containsKey(key);
    }

    @Override
    public boolean add(K key, V value) {
        if(treeMap.containsKey(key)) {
            return false;
        }
        treeMap.put(key, value);
        return true;
    }

    @Override
    public V delete(K key) {
        return treeMap.remove(key);
    }

    @Override
    public V getValue(K key) {
        return treeMap.get(key);
    }

    @Override
    public K getKey(V value) {
        for(Map.Entry<K,V> entry: treeMap.entrySet()) {
            if(entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Iterable<K> getKeys(V value) {
       TreeMap<K,V> correctMap = new TreeMap<K, V>();
       for(Map.Entry<K,V> entry : treeMap.entrySet()) {
           if(entry.getValue().equals(value)) {
               correctMap.put(entry.getKey(),value);
           }
       }
       return correctMap.keySet();
    }

    @Override
    public int size() {
        return treeMap.size();
    }

    @Override
    public boolean isEmpty() {
        return treeMap.isEmpty();
    }

    @Override
    public void clear() {
        treeMap.clear();
    }

    @Override
    public Iterable<K> keyset() {
        return treeMap.keySet();
    }

    @Override
    public Iterable<V> values() {
        return treeMap.values();
    }
}
