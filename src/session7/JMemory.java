package session7;

/**
 * Author: cyz
 * Date: 2019/9/19
 * Description:
 */
public class JMemory {
    private int gcCount;
    private boolean isMark;
    private Object object;
    private String name;

    public int getGcCount(){
        return gcCount;
    }

    public void setGcCount(int gcCount) {
        this.gcCount = gcCount;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isMark() {
        return isMark;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
