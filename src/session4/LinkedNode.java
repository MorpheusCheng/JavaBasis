package session4;

/**
 * Author: cyz
 * Date: 2019/8/7
 * Description:
 */
public class LinkedNode {
    private char value;
    private LinkedNode next;
    private LinkedNode previous;

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public LinkedNode getNext() {
        return next;
    }

    public void setNext(LinkedNode next) {
        this.next = next;
    }

    public LinkedNode getPrevious() {
        return previous;
    }

    public void setPrevious(LinkedNode previous) {
        this.previous = previous;
    }
}
