package session5;

/**
 * Author: cyz
 * Date: 2019/9/1
 * Description:
 */
public abstract class BananaMap<K,V> {

    /**
     * Map 元素个数
     * @return
     */
    abstract int size();



    /**
     * 是否为空
     * @return
     */

    abstract boolean isEmpty();


    /**
     * 是否存在某个key
     * @param key
     * @return
     */
    abstract boolean containsKey(K key);


    /**
     * 根据 key 取得值
     * @param key
     * @return
     */
    abstract V get(K key);


    /**
     * 存储 key-value 对
     * @param key
     * @return
     */
    abstract V put(K key, V value);

    /**
     * 移除某个值
     * @param key
     * @return
     */
    abstract V remove(K key);




}
