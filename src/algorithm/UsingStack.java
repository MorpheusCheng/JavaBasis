package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Author: cyz
 * Date: 2019/9/26
 * Description: 使用Stack实现中缀表达式转后缀 然后利用后缀表达式计算值
 */
public class UsingStack {
    /**
     * 将输入中缀表达式转化为后缀表达式
     * @param input 中缀表达式
     * @return 后缀表达式
     */
    public static String[] middleToLast(String[] input){
        Stack<String> stack = new Stack<String>();
        List<String> res = new ArrayList<String>(input.length);
        for(String e : input){
            if (e.length() > 1 || (e.charAt(0) >= 48 && e.charAt(0) <= 57 ) ){
                res.add(e);
            }else if (stack.isEmpty() && (e.charAt(0) < 48 || e.charAt(0) > 57 )){
                stack.push(e);
            }else if (e.charAt(0) == '('){
                stack.push(e);
            }else if (e.charAt(0) == ')'){
                while (!stack.isEmpty() && stack.peek().charAt(0) != '('){
                    res.add(stack.pop());
                }
                if (!stack.isEmpty()){
                    stack.pop();
                }
            }else if(e.charAt(0) == '+' || e.charAt(0) == '-'){
                while (!stack.isEmpty() && (stack.peek().charAt(0) == '+' ||
                        stack.peek().charAt(0) == '-' ||
                        stack.peek().charAt(0) == '*' ||
                        stack.peek().charAt(0) == '/')){
                    res.add(stack.pop());
                }
                stack.push(e);
            }else if (e.charAt(0) == '*' || e.charAt(0) == '/'){
                while (!stack.isEmpty() && (stack.peek().charAt(0) == '*' || stack.peek().charAt(0) == '/')){
                    res.add(stack.pop());
                }
                stack.push(e);
            }
        }
        while (!stack.isEmpty()){
            res.add(stack.pop());
        }
        return res.toArray(new String[input.length]);
    }

    /**
     * 将输入后缀表达式计算结果
     * @param input ： 输入后缀表达式
     * @return 计算结果
     */
    public static int computeLast(String[] input){
        Stack<Integer> stack = new Stack<>();
        for (String e : input){
            if (e == null){
                break;
            }
            if (e.length() > 1 || (e.charAt(0) >= 48 && e.charAt(0) <= 57)){
                stack.push(Integer.parseInt(e));
            }else if (e.charAt(0) == '+'){
                int re1 = stack.pop();
                int re2 = stack.pop();
                stack.push(re2 + re1);
            }else if (e.charAt(0) == '-'){
                int re1 = stack.pop();
                int re2 = stack.pop();
                stack.push(re2 - re1);
            }else if (e.charAt(0) == '*'){
                int re1 = stack.pop();
                int re2 = stack.pop();
                stack.push(re2 * re1);
            }else if (e.charAt(0) == '/'){
                int re1 = stack.pop();
                int re2 = stack.pop();
                stack.push(re2 / re1);
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        String[] input = {"9","+","(","3","-","1",")","*","3","+","10","/","2"};
        String[] res1 = middleToLast(input);
        System.out.println(Arrays.toString(res1));
        System.out.println("结果："+computeLast(res1));
    }
}
