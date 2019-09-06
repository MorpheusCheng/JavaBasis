package session5;

/**
 * 去重统计的 BitMap
 * @param <Integer>
 */
public class BananaBitMap<Integer> implements CountingInterface<Integer>{
    private byte[] bits;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 8;
    public BananaBitMap(){
        this(DEFAULT_CAPACITY);
    }
    public BananaBitMap(int capacity){
        this.capacity = capacity;
        this.bits = new byte[getIndex(capacity) + 1];
        this.size = 0;
    }
    @Override
    public void add(Integer key) {
        if ((int)key > capacity){
            throw new ArrayIndexOutOfBoundsException("key超过bitmap范围");
        }
        if (contains(key)){
            return;
        }
        bits[getIndex((int)key)] |= 1 << getPosition((int)key);
        size++;
    }
    public int getIndex(int num){
        return num >> 3;
    }
    public int getPosition(int num){
        return num & 0x07;
    }
    public boolean contains(Integer key){
        return (bits[getIndex((int)key)] & 1 << getPosition((int)key)) != 0;
    }
    @Override
    public int size() {
        return size;
    }
    //test
    public static void main(String[] args) {
        BananaBitMap bitMap = new BananaBitMap(10);
        bitMap.add(1);
        bitMap.add(7);
        System.out.println(bitMap.size());
        System.out.println(bitMap.contains(7));
        bitMap.add(10);
    }

}
