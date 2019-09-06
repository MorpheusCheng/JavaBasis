package session5;

/**
 * Author: cyz
 * Date: 2019/9/1
 * Description: 自己实现一个HashMap
 */
public class BananaHashMap<K,V> extends BananaMap<K,V> {
    static final int DEFAULT_INITIAL_CAPACITY =  1 << 4;
    static final int MAXIMUM_CAPACITY = 1 << 30 ;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private float loadFactor;
    private int threshold;
    private int capacity;
    private int size;
    static class Entry<K,V> {
        final K key;
        V value;
        private Entry next;
        private Entry prev;
        public Entry(K key,V value){
            this.key = key;
            this.value = value;
        }
    }
    private Entry<K,V>[] table;
    public BananaHashMap(){
        this(DEFAULT_INITIAL_CAPACITY);
    }
    public BananaHashMap(int capacity){
        this(capacity,DEFAULT_LOAD_FACTOR);
    }
    public BananaHashMap(int capacity,float loadFactor){
        table = (Entry<K,V>[]) new Entry[capacity];
        loadFactor = loadFactor;
        threshold = (int) (capacity * loadFactor);
        capacity = capacity;
        size = 0;
    }
    @Override
    int size() {
        return size;
    }

    @Override
    boolean isEmpty() {
        return size == 0;
    }

    @Override
    boolean containsKey(K key) {
        return false;
    }

    private static final int hash(Object key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    @Override
    V get(K key) {
        return null;
    }

    @Override
    V put(K key, V value) {
        return null;
    }

    @Override
    V remove(K key) {
        return null;
    }
}
