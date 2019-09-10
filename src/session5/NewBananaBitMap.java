package session5;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * Author: cyz
 * Date: 2019/9/10
 * Description:
 */
public class NewBananaBitMap<K> implements CountingInterface<K> {
    //使用int[]存储一个单元有4字节，32bit
    private int[] filter;
    private int size;
    private DigestInterface digestInterface;

    private static final Integer DEFAULT_CAPACITY = 1000000;

    public NewBananaBitMap(){
        init(DEFAULT_CAPACITY);
    }
    public NewBananaBitMap(int capacity){
        init(capacity);
    }
    private void init(int capacity){
        this.filter = new int[capacity];
        this.size = 0;
        this.digestInterface = new DigestMD5();
    }
    //position 1 - 2^31 整型的范围
    private int getBit(int position){
        return 1 << position;
    }
    private boolean containsBit0(int position , int target){
        return (getBit(position) & target) == getBit(position);
    }
    private void setBit(int position){
        int index = position / 32;
        int pos = position % 32;
        //获取对应位置的32bit的内容
        int target = filter[index];
        if (containsBit0(pos,target)){
            return;
        }
        target += getBit(position);
        filter[index] = target;
    }
    private boolean containsBit(int position){
        int index = position / 32;
        int pos = position % 32;
        int target = filter[index];
        return containsBit0(pos,target);
    }
    @Override
    public void add(K key){
        String keyString = key.toString();
        int digest = digestInterface.digest(keyString);
        int position = digest % (32 * filter.length);
        if (!containsBit(position)){
            setBit(position);
            size++;
        }
    }
    @Override
    public int size(){
        return size;
    }

    private class DigestMD5 implements DigestInterface{
        @Override
        public int digest(String key){
            return Math.abs(encryptMD5(key));
        }
        private int encryptMD5(String data){
            byte[] bytes = null;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                bytes = messageDigest.digest(data.getBytes());
                return new String(bytes).hashCode();
            }catch (GeneralSecurityException e){

            }
            return 0;
        }
    }
    private interface DigestInterface{
        int digest(String key);
    }
}
