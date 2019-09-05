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
    private int threshold;
    private int size;
    private float loadFactor;
    static class Entry<K,V> {
        private K key;
        private V value;
        private Entry<K,V> next;
        public Entry(K key,V value){
            this.key = key;
            this.value = value;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setNext(Entry next) {
            this.next = next;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entry getNext() {
            return next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
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
        this.table = new Entry[capacity];
        threshold = (int) (capacity * loadFactor);
        loadFactor = loadFactor;
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
        if (get(key) == null){
            return false;
        }
        return true;
    }

    private static final int hash(Object key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    @Override
    V get(K key) {
        int hash = hash(key);
        int index = hash % table.length;
        Entry<K,V> cur = table[index];
        if (cur == null){
            return null;
        }
        while (cur != null){
            if (cur.getKey() == key || key.equals(cur.getKey())){
                return cur.getValue();
            }
            cur = cur.getNext();
        }
        return null;
    }

    @Override
    boolean put(K key, V value) {
        int index = hash(key) % table.length;
        for (Entry<K,V> e = table[index]; e != null ; e = e.getNext()){
            if (e.getKey() == key || key.equals(e.getKey())){
                e.setValue(value);
                return true;
            }
        }
        size++;
        resize();
        int newIndex = hash(key) % table.length;
        Entry<K,V> entry = new Entry<>(key,value);
        entry.setNext(table[newIndex]);
        table[newIndex] = entry;
        return false;
    }
    public void resize(){
        if (size > threshold){
            Entry[] oldtable = table;
            if (oldtable.length == MAXIMUM_CAPACITY){
                threshold = Integer.MAX_VALUE;
                return;
            }
            Entry[] newtable = new Entry[2 * oldtable.length];
            for (Entry<K,V> e : table){
                while (null != e){
                    Entry<K,V> next = e.getNext();
                    int index = hash(e.getKey()) % newtable.length;
                    //采用头插法插入新的表
                    e.setNext(newtable[index]);
                    newtable[index] = e;
                    e = next;
                }
            }
            table = newtable;
            threshold = (int) Math.min(newtable.length * loadFactor,MAXIMUM_CAPACITY);
        }
    }

    @Override
    V remove(K key) {
        int index = hash(key) % table.length;
        Entry<K, V> pre = table[index];
        if (pre == null){
            throw new RuntimeException("key is not exist!");
        }
        if (key.equals(pre.getKey()) || key == pre.getKey()) {
            V value = pre.getValue();
            table[index] = pre.getNext();
            size--;
            return value;
        }
        while (pre.getNext() != null) {
            if (pre.getNext().getKey() == key || key.equals(pre.getNext().getKey())) {
                V value = (V) pre.getNext().getValue();
                pre = pre.getNext().getNext();
                size--;
                return value;
            }
            pre = pre.getNext();
        }
        throw new RuntimeException("key is not exist!");
    }
}
