package session7;

import java.util.Random;

/**
 * Author: cyz
 * Date: 2019/9/19
 * Description:
 */
public class TestJVM {

    public static void main(String[] args){

        //JVM jvm = new JVM(20,10,10);
        newJVM jvm = new newJVM();
        Random random = new Random();

        int objectSize = 100;
        for(int i = 0 ; i < objectSize ; i++){
            jvm.removeObject(getNewName(random.nextInt(10)));
            jvm.addObject(getNewName(i), new Object());
        }


        for(int i = 0 ; i < objectSize ; i++){
            jvm.removePermObject(getPermName(random.nextInt(10)));
            jvm.addPermObject(getPermName(i), new Object());
        }


    }


    private static String getNewName(int i){
        return "NEW_"+i;
    }

    private static String getPermName(int i){
        return "PERM_"+i;
    }
}
