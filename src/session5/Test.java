package session5;

/**
 * Author: cyz
 * Date: 2019/9/3
 * Description:
 */
public class Test {
    public static void main(String[] args) {
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
        map.remove("A");
        //System.out.println(map.containsKey("A"));
        map.remove("A");
        map.remove("B");
        map.remove("C");
        map.remove("D");
        System.out.println(map.get("A"));
        System.out.println(map.size());
    }
}
