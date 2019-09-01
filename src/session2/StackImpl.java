package session2;

/**
 * Author: cyz
 * Date: 2019/7/20
 * Description: 实现一个栈
 */
public class StackImpl implements Stack {
    private int[] stackElem;
    private int top;
    public StackImpl(int maxSize){
        stackElem = new int[maxSize];
        top = 0;
    }
    public StackImpl(){
        stackElem = new int[10];
        top = 0;
    }
    @Override
    public boolean push(int value) {
        if (this.stackElem.length == top){
            int[] newArray = new int[this.stackElem.length * 2];
            System.arraycopy(stackElem,0,newArray,0,stackElem.length);
            this.stackElem = newArray;
            this.push(value);
            return true;
        }
        else{
            stackElem[top++] = value;
            return true;
        }
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public int peak() {
        if(isEmpty()){
            throw new RuntimeException("Stack is empty when peak!");
        }
        return stackElem[top - 1];
    }

    @Override
    public int pop() {
        if(isEmpty()){
            throw new RuntimeException("Stack is empty when pop!");
        }
        return stackElem[--top];
    }

    @Override
    public int size() {
        return top;
    }
}
