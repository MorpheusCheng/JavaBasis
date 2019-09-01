package session4;

/**
 * Author: cyz
 * Date: 2019/8/7
 * Description: 实现容器为链表的LinedString
 */
public class LinedString implements StringInterface {
    private LinkedNode head;

    public LinedString(LinkedNode head){
        this.head = head;
    }
    public LinedString(char[] chars){
        if (chars == null) {
            this.head = null;
        }
        LinkedNode newhead = new LinkedNode();
        newhead.setNext(null);
        LinkedNode cur = newhead;
        for (int i = 0 ; i < chars.length ; i++){
            LinkedNode newNode = new LinkedNode();
            newNode.setValue(chars[i]);
            newNode.setPrevious(cur);
            cur.setNext(newNode);
            cur = cur.getNext();
        }
        cur.setNext(null);
        this.head = newhead.getNext();
    }

    /**
     * 根据数字返回对应的字符串
     * @param number
     * @return
     */
    public static StringInterface valueOf(Integer number) throws NumberFormatException{
        int flag = 0;
        LinkedNode newhead = new LinkedNode();
        newhead.setNext(null);
        newhead.setPrevious(null);
        if (number < 0){
            flag = 1;
            number = - number;
        }else if (number == 0){
            newhead.setValue('0');
            return new LinedString(newhead);
        }
        while (number != 0){
            int i = number % 10;
            LinkedNode newNode = new LinkedNode();
            newNode.setValue((char)(i + '0'));
            newNode.setNext(newhead.getNext());
            newNode.setPrevious(newhead);
            newhead.setNext(newNode);
            number /= 10;
        }
        if (flag == 0){
            newhead = newhead.getNext();
            newhead.setPrevious(null);
            return new LinedString(newhead);
        }
        else {
            newhead.setValue('-');
            return new LinedString(newhead);
        }
    }
    @Override
    public int length(){
        LinkedNode cur = head;
        int i = 0;
        while (cur.getNext() != null){
            cur = cur.getNext();
            i++;
        }
        return i;
    }

    @Override
    public char charAt(int position) {
        LinkedNode cur = head;
        if (position > length() || position < 0){
            throw new StringIndexOutOfBoundsException("超过字符串长度");
        }
        while (position != 0){
            cur = cur.getNext();
            position--;
        }
        return cur.getValue();
    }
    @Override
    public int indexOf(char[] target){
        int dLength = target.length;
        int dIndex = 0 , sIndex = 0;
        LinkedNode sCur = head;
        int[] next = getNextArray(target);

        while (sCur.getNext() != null && dIndex < dLength){
            if (dIndex == -1 || sCur.getValue() == target[dIndex]){
                sCur = sCur.getNext();
                sIndex++;
                dIndex++;
            }
            else {
                dIndex = next[dIndex];
            }
        }
        if (dIndex == dLength){
            return sIndex - dLength;
        }
        return -1;

    }
    private int[] getNextArray(char[] destStr){
        int[] nextArr = new int[destStr.length];
        nextArr[0] = -1;
        int k = -1,j = 0;
        while (j < destStr.length - 1){
            if (k == -1 || (destStr[j] == destStr[k])){
                ++k;
                ++j;
                nextArr[j] = k;
            }
            else {
                k = nextArr[k];
            }
        }
        return  nextArr;
    }
    @Override
    public StringInterface subString(int start , int end){
        if (start > end){
            throw new RuntimeException("start > end 输入格式错误");
        }
        if (start < 0){
            throw new StringIndexOutOfBoundsException("start < 0");
        }
        if (end > this.length() - 1){
            throw new StringIndexOutOfBoundsException("end > length");
        }
        LinkedNode cur = head;
        int newLength = end - start;
        while (start != 0){
            cur = cur.getNext();
            start--;
        }
        LinkedNode newhead = new LinkedNode();
        LinkedNode newcur = newhead;
        while (newLength != 0){
            LinkedNode newNode = new LinkedNode();
            newNode.setValue(cur.getValue());
            newNode.setPrevious(newcur);
            newcur.setNext(newNode);
            newcur = newcur.getNext();
            cur = cur.getNext();
            newLength--;
        }
        newcur.setNext(null);
        return new LinedString(newhead.getNext());
    }
    @Override
    public StringInterface reverse(){
        if (head == null || head.getNext() == null){
            return this;
        }
        LinkedNode preNode = null;
        LinkedNode curNode = head;
        LinkedNode nextNode = null;

        while (curNode != null){
            nextNode = curNode.getNext();
            curNode.setNext(preNode);
            curNode.setPrevious(nextNode);
            preNode = curNode;
            curNode = nextNode;
        }
        head = preNode;
        return this;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        LinkedNode cur = head;
        while (cur != null){
            sb.append(cur.getValue());
            cur = cur.getNext();
        }
        return sb.toString();
    }
}
