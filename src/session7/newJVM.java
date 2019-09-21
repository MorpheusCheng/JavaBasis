package session7;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Author: cyz
 * Date: 2019/9/21
 * Description:
 */
public class newJVM {
    //整个内存区的大小
    private Integer Xmx = 20;
    //年轻代的大小  分为Eden区和两个survivor区
    private Integer Xmn = 10;
    private Integer EdenSize = 6;
    private Integer SurvivorSize = 2;
    private Integer from = EdenSize;
    private Integer to = EdenSize + SurvivorSize;

    //永久代的大小
    private Integer XXPermSize = 10;

    private List<JMemory> YOUNG_GEN_SPACE;
    private List<JMemory> OLD_GEN_SPACE;
    private List<JMemory> PERM_GEN_SPACE;

    private static final ConcurrentMap<String,Integer> GC_ROOT = Maps.<String,Integer> newConcurrentMap();

    public newJVM(){
        YOUNG_GEN_SPACE = new ArrayList<>(Xmn);
        OLD_GEN_SPACE = new ArrayList<>(Xmx - Xmn);
        PERM_GEN_SPACE = new ArrayList<>(XXPermSize);

        init();
    }
    public newJVM(Integer Xmx, Integer Xmn , Integer XXPermSize){
        this.Xmn = Xmn;
        this.Xmx = Xmx;
        this.XXPermSize = XXPermSize;

        YOUNG_GEN_SPACE = new ArrayList<>(Xmn);
        OLD_GEN_SPACE = new ArrayList<>(Xmx - Xmn);
        PERM_GEN_SPACE = new ArrayList<>(XXPermSize);

        init();
    }

    private void init(){
        for (int i = 0; i < Xmn; i++) {
            YOUNG_GEN_SPACE.add(null);
        }
    }

    public void addObject(String name , Object value){
        gc();
        if (GC_ROOT.containsKey(name)){
            GC_ROOT.put(name, GC_ROOT.get(name) + 1);
        }else {
            System.out.println("add object : " + name);
            GC_ROOT.put(name,1);
            JMemory element = new JMemory();
            element.setGcCount(0);
            element.setMark(false);
            element.setObject(value);
            element.setName(name);
            int index = getEmptySpace(YOUNG_GEN_SPACE,0,EdenSize);
            YOUNG_GEN_SPACE.set(index,element);
        }
    }

    public void removeObject(String name){
        gc();
        if (GC_ROOT.containsKey(name)){
            GC_ROOT.put(name,GC_ROOT.get(name) - 1);
        }else {
            System.out.println("not found object: " + name);
        }
    }

    public void addPermObject(String name,Object value){
        gc();
        if (GC_ROOT.containsKey(name)){
            GC_ROOT.put(name, GC_ROOT.get(name) + 1);
        }else {
            System.out.println("add perm object : " + name);
            GC_ROOT.put(name,1);
            JMemory element = new JMemory();
            element.setGcCount(0);
            element.setMark(false);
            element.setObject(value);
            element.setName(name);
            PERM_GEN_SPACE.add(element);
        }
    }

    public void removePermObject(String name){
        gc();
        if (GC_ROOT.containsKey(name)){
            GC_ROOT.put(name,GC_ROOT.get(name) - 1);
        }else {
            System.out.println("not perm found object: " + name);
        }
    }

    private void gc(){
        if (getEmptySpace(YOUNG_GEN_SPACE,0,EdenSize) == -1){
            System.out.println("执行一次minor gc");
            MinorGc();
        }else if (OLD_GEN_SPACE.size() > Xmx - Xmn){
            System.out.println("执行一次full gc");
            FullGc();
        }
    }

    private void MinorGc(){
        //首先清理掉所有年轻代的垃圾
        int surCount = 0;
        for (int i = 0; i < YOUNG_GEN_SPACE.size(); i++) {
            JMemory element = YOUNG_GEN_SPACE.get(i);
            if (element != null && GC_ROOT.get(element.getName()) < 0){
                GC_ROOT.remove(element.getName());
                System.out.println("Minor gc , clean :" + element.getName());
                YOUNG_GEN_SPACE.set(i,null);
            }else if (element != null){
                surCount++;
                element.setGcCount(element.getGcCount() + 1);
            }
        }
        //如果存活对象大于survivor区的大小 全部移入到OLD区
        if (surCount > SurvivorSize){
            for (int i = 0; i < YOUNG_GEN_SPACE.size(); i++) {
                JMemory element = YOUNG_GEN_SPACE.get(i);
                if (element != null){
                    checkMemory();
                    OLD_GEN_SPACE.add(element);
                    YOUNG_GEN_SPACE.set(i , null);
                }

            }
        }else {
            for (int i = 0; i < EdenSize; i++) {
                JMemory element = YOUNG_GEN_SPACE.get(i);
                if (element != null){
                    int index = getEmptySpace(YOUNG_GEN_SPACE,to,to + SurvivorSize);
                    YOUNG_GEN_SPACE.set(index , element);
                    YOUNG_GEN_SPACE.set(i , null);
                }
            }
            for (int i = from; i < from + SurvivorSize; i++) {
                JMemory element = YOUNG_GEN_SPACE.get(i);
                //survivor区中超过一定年龄可以直接升入old区
                if (element != null && element.getGcCount() > 3){
                    checkMemory();
                    OLD_GEN_SPACE.add(element);
                    YOUNG_GEN_SPACE.set(i, null);
                }
                 else if (element != null){
                    int index = getEmptySpace(YOUNG_GEN_SPACE,to,to + SurvivorSize);
                    YOUNG_GEN_SPACE.set(index , element);
                    YOUNG_GEN_SPACE.set(i , null);
                }
            }
            //结束之后 交换from 与 to 指针
            int temp = from;
            from = to;
            to = temp;
        }
    }

    private void FullGc(){
        System.out.println("执行一次full gc");
        for (int i = 0; i < YOUNG_GEN_SPACE.size(); i++) {
            JMemory element = YOUNG_GEN_SPACE.get(i);
            if (element != null && GC_ROOT.get(element.getName()) < 0){
                System.out.println("full gc , clean : " + element.getName());
                GC_ROOT.remove(element.getName());
                YOUNG_GEN_SPACE.set(i,null);
            }
        }
        for (int i = 0 ; i < OLD_GEN_SPACE.size() ; i++){
            JMemory element = OLD_GEN_SPACE.get(i);
            if (GC_ROOT.get(element.getName()) < 0){
                System.out.println("full gc , clean : " + element.getName());
                GC_ROOT.remove(element.getName());
                OLD_GEN_SPACE.remove(i);
                i--;
            }
        }
        for (int i = 0; i < PERM_GEN_SPACE.size(); i++){
            JMemory element = PERM_GEN_SPACE.get(i);
            if (GC_ROOT.get(element.getName()) < 0){
                System.out.println("full gc , clean : " + element.getName());
                GC_ROOT.remove(element.getName());
                PERM_GEN_SPACE.remove(i);
                i--;
            }
        }

    }

    private void checkMemory(){
        if (YOUNG_GEN_SPACE.size() > Xmn){
            throw new OutOfMemoryError("outofmemory error , YoungGen Full!");
        }

        if (OLD_GEN_SPACE.size() > Xmx - Xmn){
            throw new OutOfMemoryError("outofmemory error , OldGen Full!");
        }

        if (PERM_GEN_SPACE.size() > XXPermSize){
            throw new OutOfMemoryError("outofmemory error , PermGen Full!");
        }
    }
    //主要是寻找survivor区的空余位置
    private int getEmptySpace(List<JMemory> space , int start , int end){
        for (int i = start; i < end; i++) {
            if (space.get(i) == null){
                return i;
            }
        }
        return -1;
    }


}
