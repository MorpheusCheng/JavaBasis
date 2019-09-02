package session2;

/**
 * Author: cyz
 * Date: 2019/7/21
 * Description:
 */
public class TestForStack {
    public static void main(String[] args) {
        Stack stack1 = new StackImpl(1);
        Stack stack2 = new StackWithLogImpl(1);
        stack1.push(1);
        stack1.push(2);
        stack2.push(3);
        stack2.push(4);

    }
}
