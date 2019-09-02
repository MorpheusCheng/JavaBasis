package session2;

/**
 * Author: cyz
 * Date: 2019/7/21
 * Description: 实现一个带日志的栈
 */
public class StackWithLogImpl extends StackImpl {
    public StackWithLogImpl(int maxsize){
        super(maxsize);
    }
    public StackWithLogImpl(){
        super();
    }
    @Override
    public int size() {
        System.out.println("start size");
        int size = super.size();
        System.out.println("end size");
        return size;
    }

    @Override
    public int pop() {
        System.out.println("start pop");
        int pop =  super.pop();
        System.out.println("end pop");
        return pop;
    }

    @Override
    public int peak() {
        System.out.println("start peak");
        int peak =  super.peak();
        System.out.println("end peak");
        return peak;
    }

    @Override
    public boolean isEmpty() {
        System.out.println("start isEmpty");
        boolean isempty =  super.isEmpty();
        System.out.println("end isEmpty");
        return isempty;
    }

    @Override
    public boolean push(int value) {
        System.out.println("start push");
        boolean push =  super.push(value);
        System.out.println("end push");
        return push;
    }
}
