package session5;

/**
 * Author: cyz
 * Date: 2019/9/1
 * Description: 实现一个去重统计的BloomFilter
 */
public class BananaBloomFilter<K> implements CountingInterface<K>{
    private static final int DEFAULT_CAPACITY = 1 << 25;
    private static final int[] seeds = new int[]{5,7,11,13,31,37,61};
    private BananaBitMap bananaBitMap;
    private SimpleHash[] func = new SimpleHash[seeds.length];
    private int size;
    public BananaBloomFilter(){
        this(DEFAULT_CAPACITY);
    }
    public BananaBloomFilter(int capacity){
        this.bananaBitMap = new BananaBitMap(capacity);
        this.size = 0;
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(capacity,seeds[i]);
        }
    }
    @Override
    public void add(K key){
        if (contains(key)){
            return;
        }
        for (SimpleHash f : func){
            bananaBitMap.add(f.hash(key));
        }
        size++;
    }

    @Override
    public int size(){
        return size;
    }

    public boolean contains(K key){
        if (key == null){
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func){
            ret = ret && bananaBitMap.contains(f.hash(key));
        }
        return ret;
    }

    public static class SimpleHash<K>{
        private int capacity;
        private int seed;
        public SimpleHash(int capacity,int seed){
            this.capacity = capacity;
            this.seed = seed;
        }
        public int hash(K key){
            int result = 0;
            String value = key.toString();
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result += seed * result + value.charAt(i);
            }
            return (capacity - 1) & result;
        }
    }

    public static void main(String[] args) {
        BananaBloomFilter bananaBloomFilter = new BananaBloomFilter(1 << 25);
        bananaBloomFilter.add("a");
        bananaBloomFilter.add("b");
        bananaBloomFilter.add("c");
        bananaBloomFilter.add("d");
        System.out.println(bananaBloomFilter.size());
        bananaBloomFilter.add("d");
        System.out.println(bananaBloomFilter.contains("a"));
    }
}
