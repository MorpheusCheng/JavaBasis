package session2;

/**
 * Author: cyz
 * Date: 2019/7/20
 * Description:
 */
public interface Stack {
    /**
     * 入栈
     * @return boolean
     */
    boolean push(int value);

    /**
     * 出栈
     * @return int
     */
    int pop();

    /**
     * 查看栈顶元素
     */
    int peak();

    /**
     * 栈的大小
     */
    int size();

    /**
     * 栈是否为空
     * @return
     */
    boolean isEmpty();

}
