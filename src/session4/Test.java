package session4;

/**
 * Author: cyz
 * Date: 2019/8/31
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        LinedString s1 = new LinedString(new char[]{'1','a','c','b','c','b','c'});
        System.out.println(s1.charAt(1));
        System.out.println(s1.indexOf(new char[]{'c','b','c'}));
        System.out.println(s1.subString(1,3));
        System.out.println(s1.reverse());
        System.out.println(LinedString.valueOf(-100).toString());

    }
}
