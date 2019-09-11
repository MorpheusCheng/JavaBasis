package session5;


/**
 * 去重统计的 BitMap
 *
 */
public class BananaBitMap implements CountingInterface<Integer>{
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
        if (contains(key)){
            return;
        }
        bits[getIndex(key)] |= 1 << getPosition(key);
        size++;
    }
    public int getIndex(int num){
        return num >> capacity;
    }
    public int getPosition(int num){
        return num & 0x07;
    }
    public boolean contains(int key){
        return (bits[getIndex(key)] & 1 << getPosition(key)) != 0;
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
        bitMap.add(20);
        System.out.println(bitMap.size());
        System.out.println(bitMap.contains(20));
        System.out.println(bitMap.contains(65));
    }

}
