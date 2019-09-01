package session4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: cyz
 * Date: 2019/8/7
 * Description:
 */
public class ArrayString implements StringInterface {
    final char[] chars;

    public ArrayString(char value[]){
        this.chars = Arrays.copyOf(value,value.length);
    }

    public ArrayString(ArrayString arrayString){
        this.chars = arrayString.chars;
    }

    /**
     * 根据数字返回对应的字符串
     * @param number
     * @return
     */
    public static StringInterface valueOf(Integer number) throws NumberFormatException{
        int flag = 0;
        List<Integer> values = new ArrayList<Integer>();
        if (number < 0){
            flag = 1;
            number = - number;
        }else if (number == 0){
            return new ArrayString(new char[]{'0'});
        }
        while (number != 0){
            int i = number % 10;
            values.add(i);
            number /= 10;
        }
        char[] valueChar = new char[values.size() + (flag == 0 ? 0 : 1)];
        if (flag == 0){
            for (int i = 0; i < valueChar.length; i++) {
                valueChar[i] = (char)(values.get(valueChar.length - i - 1) + '0');
            }
        }else {
            valueChar[0] = '-';
            for (int i = 1 ; i < valueChar.length ; i++){
                valueChar[i] = (char)(values.get(valueChar.length - i) + '0');
            }
        }
        return new ArrayString(valueChar);
    }

    @Override
    public int length(){
        return chars.length;
    }

    @Override
    public char charAt(int position){
        if (position >= this.length() || position < 0){
            throw new StringIndexOutOfBoundsException("超过字符串长度");
        }
        return chars[position];
    }
    /**
     * 根据target 获取第一个匹配的字符串的位置，如果没找到，返回 -1
     * @param target
     * @return
     */
    @Override
    public char indexOf(char[] target){

        return 0;
    }

    @Override
    public StringInterface subString(int start,int end){
        if (start > end){
            throw new RuntimeException("start > end 输入格式错误");
        }
        if (start < 0){
            throw new StringIndexOutOfBoundsException("start < 0");
        }
        if (end > this.length() - 1){
            throw new StringIndexOutOfBoundsException("end > length");
        }
        char[] newChars = Arrays.copyOfRange(chars,start,end);
        return new ArrayString(newChars);
    }

    @Override
    public StringInterface reverse(){
        char[] newChar = new char[this.length()];
        int i = 0;
        while (i < this.length()){
            newChar[i] = this.charAt(this.length() - i - 1);
            i++;
        }
        return new ArrayString(newChar);
    }
}
