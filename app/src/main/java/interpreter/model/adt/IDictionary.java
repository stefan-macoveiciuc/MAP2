package interpreter.model.adt;

import java.util.Map;

public interface IDictionary<K, V> {
    void put(K key, V value);
    V get(K key);
    boolean containsKey(K key);
    void remove(K key);
    Map<K,V> getContent();
}
