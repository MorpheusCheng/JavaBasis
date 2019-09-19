package session7;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Author: cyz
 * Date: 2019/9/19
 * Description:
 */
public class JVM {
    /**
     * 整个内存区大小
     */
    private Integer Xmx = 50;

    /**
     * young 区的大小
     */

    private Integer Xmn = 10;


    /**
     *  持久代大小
     */
    private Integer XXPermSize = 10;


    public JVM(Integer Xmx , Integer Xmn , Integer XXPermSize){
        this.Xmn = Xmn;
        this.Xmx = Xmx;
        this.XXPermSize = XXPermSize;

        YOUNG_GEN_SPACE = new ArrayList<>(Xmn);
        OLD_GEN_SPACE = new ArrayList<>(Xmx - Xmn);
        PERM_GEN_SPACE = new ArrayList<>(XXPermSize);

        this.eden_size = Xmn * 6 / 10;
        this.survivor_size = Xmn - eden_size;
        this.from = eden_size;
        this.to = from + survivor_size / 2;
    }


    /**
     * 这里需要自己划分 Eden 和 Survivor 的大小，千万不要触发扩容
     */
    private List<JMemory> YOUNG_GEN_SPACE ;


    /**
     * 老年代，千万不要触发扩容
     */
    private  List<JMemory> OLD_GEN_SPACE;

    /**
     * 持久代(JVM 内存)，JDK 1.8 后为 MetaSpace(物理内存)，千万不要触发扩容
     */
    private  List<JMemory> PERM_GEN_SPACE;

    //指向survivor区域的两个指针 from 和 to 区域
    private int from = 6;

    private int to = 8;

    private int eden_size = 6;

    private int survivor_size = 4;


    private static final ConcurrentMap<String,Integer> GC_ROOT = Maps.<String,Integer> newConcurrentMap();

    public  void addObject(String name , Object value){
        gc();
        if (GC_ROOT.containsKey(name)){
            GC_ROOT.put(name,GC_ROOT.get(name) + 1);
        }else {
            GC_ROOT.put(name,1);
            JMemory newJMemory = new JMemory();
            newJMemory.setObject(value);
            newJMemory.setGcCount(0);
            newJMemory.setMark(false);
            YOUNG_GEN_SPACE.set(getEmptySpace(YOUNG_GEN_SPACE,eden_size),newJMemory);
        }

    }

    public void removeObject(String name){
        gc();
        if (!GC_ROOT.containsKey(name)){
            throw new RuntimeException("没有此对象: "+name);
        }else {
            GC_ROOT.put(name,0);
        }
    }


    public  void addPermObject(String name , Object value){
        int index = getEmptySpace(PERM_GEN_SPACE,PERM_GEN_SPACE.size());
        if(index == -1){
            gc();
        }
        GC_ROOT.put(name,1);
        JMemory newJMermory = new JMemory();
        newJMermory.setObject(value);
        newJMermory.setGcCount(0);
        newJMermory.setMark(false);
        PERM_GEN_SPACE.set(index,newJMermory);
    }

    public  void removePermObject(String name){
        gc();
        if (!GC_ROOT.containsKey(name)){
            throw new RuntimeException("没有此对象：" + name);
        }else {
            GC_ROOT.put(name,0);
        }

    }


    /**
     * 这里自己实现 gc 算法，比如什么时候应该进行代的晋升，什么时候进行young gc，什么时候进行full gc，什么时候应该crash。
     * 核心逻辑：
     * 新对象优先放 Eden 区，然后经过一定轮数晋升到 Survivor 区域。Survivor 区域分为两个区域循环利用。
     * 对象 gc 经过一定轮数后，从 Survivor 区晋升到 Old 区。
     * 如果 old 区放不下了，进行full gc。
     * 如果 永久代放不下了，进行full gc。
     * PS : 本作业不要求多线程回收，每次回收就 stop the world 好了，暂时不要太纠结这块的性能
     *
     */
    private  void gc(){
        if (isEdenFull()){
            //执行minor gc
            for (int i = 0; i < eden_size ; i++) {
                if (YOUNG_GEN_SPACE.get(i).isMark()){
                    YOUNG_GEN_SPACE.set(i,null);
                }else {
                    YOUNG_GEN_SPACE.get(i).setGcCount(YOUNG_GEN_SPACE.get(i).getGcCount() + 1);
                }
            }
            return;
        }


    }


    private  void checkMemory(){
        if(YOUNG_GEN_SPACE.size() > Xmn){
            throw new OutOfMemoryError("OutOfMemory Error,YoungGen full");
        }

        if(OLD_GEN_SPACE.size() > Xmx - Xmn){
            throw new OutOfMemoryError("OutOfMemory Error,OldGen full ");
        }

        if(PERM_GEN_SPACE.size() > XXPermSize){
            throw new OutOfMemoryError("OutOfMemory Error,PermGen full ");
        }

    }

    private boolean isEdenFull(){
        for (int i = 0 ; i < eden_size ; i++){
            if (YOUNG_GEN_SPACE.get(i) == null){
                return false;
            }
        }
        return true;
    }
    private boolean isOldGenFull(){
        for (int i = 0; i < OLD_GEN_SPACE.size(); i++) {
            if (OLD_GEN_SPACE.get(i) == null){
                return false;
            }
        }
        return true;
    }

    private int getEmptySpace(List<JMemory> space,int size){
        for (int i = 0; i < size; i++) {
            if (space.get(i) == null){
                return i;
            }
        }
        return -1;
    }

}
