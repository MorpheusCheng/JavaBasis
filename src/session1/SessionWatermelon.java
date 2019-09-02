package session1;

/**
 * Author: cyz
 * Date: 2019/7/20
 * Description: 实现一个卖西瓜的摊摊函数
 */
public class SessionWatermelon {
    public static void main(String[] args) {
        int[] buyNum = null;
        int[] myArr = sell(buyNum);
        printArray(myArr);
    }
    public static int[] sell(int buyNum[]){
        if (buyNum == null){
            System.out.println("输入数组为空，不符合要求");
            return new int[]{};
        }
        int[] allSell = new int[buyNum.length];
        for (int i = 0 ; i < buyNum.length ; i++){
            try {
                allSell[i] = sell0(buyNum[i]);
            }catch (Exception e){
                allSell[i] = 0;
                e.printStackTrace();
            }
        }
        int sum = 0;
        for (int num: allSell
        ) {
            sum += num;
        }
        System.out.println("总共卖出："+sum);
        return allSell;
    }
    public static int sell0(int buyNum) throws Exception{
        if (buyNum < 0 ){
            throw new Exception("buyNum can't be less than 0");
        }
        else if (buyNum > 50){
            return 50;
        }
        else {
            return buyNum;
        }
    }
    public static void printArray(int[] myArray){
        StringBuilder arrayString = new StringBuilder();
        arrayString.append("[");
        if (myArray.length == 1){
            arrayString.append(myArray[0]);
        }
        else if (myArray.length > 1){
            for (int i = 0; i < myArray.length - 1; i++) {
                arrayString.append(myArray[i]);
                arrayString.append(",");
            }
            arrayString.append(myArray[myArray.length - 1]);
        }
        arrayString.append("]");
        System.out.println(arrayString);
    }
}
