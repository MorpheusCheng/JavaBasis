package session5;

import java.util.HashMap;

/**
 * Author: cyz
 * Date: 2019/9/3
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        String name = "123";
        HashMap hashMap = new HashMap();
        System.out.println(gethash(name));
        System.out.println(name.hashCode());
        System.out.println(name.hashCode() >>> 16);
    }
    public static int gethash(String name){
        int h;
        return (name == null) ? 0 : (h = name.hashCode()) ^ (h >>> 16);
    }
    public void test(){
        BananaHashMap<String,String> map = new BananaHashMap<>(2,1);
        System.out.println(map.size());
        map.put("A","A");
        System.out.println(map.size());
        System.out.println(map.get("A"));
        map.put("A","B");
        map.put("B","BC");
        map.put("C","C");
        map.put("D","D");
        System.out.println(map.size());
        System.out.println(map.get("A"));
        try {
            map.remove("A");
            //System.out.println(map.containsKey("A"));
            map.remove("A");
            map.remove("B");
            map.remove("C");
            map.remove("D");
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        System.out.println(map.get("A"));
        System.out.println(map.size());
    }
}
