package session3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Author: cyz
 * Date: 2019/7/27
 * Description: 熟练掌握各种Java Lambda技巧
 */
public class WatermelonPipline {
    public static class BananaWatermelon{
        int bananaQuantity;
        public BananaWatermelon(int bananaQuantity){
            this.bananaQuantity = bananaQuantity;
        }
    }

    public static class AppleWatermelon{
        int appleQuantity;
        public AppleWatermelon(int appleQuantity){
            this.appleQuantity = appleQuantity;
        }
    }
    public static class CommonWatermelon{
        int quantity;
        public CommonWatermelon(int quantity){
            this.quantity = quantity;
        }
    }

    public static void main(String[] args) {
        int[] bananaWatermelonArray = {-1,0,5,60};
        List<BananaWatermelon> bananaWatermelons = new ArrayList<>();
        for (int i : bananaWatermelonArray) {
            bananaWatermelons.add(new BananaWatermelon(i));
        }
        int[] appleWatermelonArray = {-1,0,5,60};
        List<AppleWatermelon> appleWatermelons = new ArrayList<>();
        for (int i : appleWatermelonArray) {
            appleWatermelons.add(new AppleWatermelon(i));
        }

        List<CommonWatermelon> commonWatermelons = mergeWatermelons(bananaWatermelons, appleWatermelons);

        List<CommonWatermelon> filteredWatermenlon = filterWatermelons(commonWatermelons);

        writeWatermelonReport(filteredWatermenlon);

        sendoutWatermelons(filteredWatermenlon);

        countingWatermelons(filteredWatermenlon);

    }

    public static List<CommonWatermelon> mergeWatermelons(List<BananaWatermelon> bananaWatermelons,
                                                          List<AppleWatermelon> appleWatermelons){
        //把两种西瓜使用stream遍历，然后Function转换为同一种西瓜
        List<CommonWatermelon> commonWatermelons = new ArrayList<>();
        bananaWatermelons.forEach(bananaWatermelon -> commonWatermelons.add(new CommonWatermelon(bananaWatermelon.bananaQuantity)));
        appleWatermelons.forEach(appleWatermelon -> commonWatermelons.add(new CommonWatermelon(appleWatermelon.appleQuantity)));
        return commonWatermelons;
    }
    public static List<CommonWatermelon> filterWatermelons(List<CommonWatermelon> commonWatermelons){
        //使用 Predicate 将西瓜中质量小0和质量大于50的瓜挑出来，丢掉。
        List<CommonWatermelon> newCommonWatermelon = new ArrayList<>();
        Predicate<CommonWatermelon> predicate = commonWatermelon -> commonWatermelon.quantity >= 0 && commonWatermelon.quantity <= 50;
        for (CommonWatermelon commonWatermelon: commonWatermelons
        ) {
            if (predicate.test(commonWatermelon)){
                newCommonWatermelon.add(commonWatermelon);
            }
        }
        return newCommonWatermelon;
    }
    public static void writeWatermelonReport(List<CommonWatermelon> filterWatermelons){
        //使用 Consumer 创建出5个检查人员，每个检查人员都会检查每个西瓜，
        // 使用 System.out.println("X 号检察员检查第 N 个西瓜，质量为 Y 完毕")。该过程使用多线程完成。
        //也就是说我们会创建出 5 * N 个线程，待所有检查人员检查完成后
        // （使用 CountDownlatch 来确认所有线程都执行完成了），观察所有的检验报告。
        CountDownLatch countDownLatch = new CountDownLatch(5 * filterWatermelons.size());
        ExecutorService excutor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 5; i++) {
            for (CommonWatermelon j : filterWatermelons) {
                final int x = i;
                final CommonWatermelon y = j;
                Runnable r = () -> {
                    Consumer<CommonWatermelon> commonWatermelonConsumer = commonWatermelon ->{
                        countDownLatch.countDown();
                        System.out.println(x + " 号检察员检查第 " + y.quantity + "个西瓜，质量为 Y 完毕");
                    };
                    commonWatermelonConsumer.accept(y);
                };
                excutor.execute(r);
            }
        }
        //如果没有全部报告都写完，阻塞在这里不允许返回。
        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        excutor.shutdownNow();
    }
    public static void sendoutWatermelons(List<CommonWatermelon> commonWatermelons){
        Stream<Integer> stream = commonWatermelons.stream().map(commonWatermelon -> commonWatermelon.quantity);
        Integer[] streamArray = stream.toArray(size -> new Integer[size]);
        System.out.println(Arrays.toString(streamArray));
    }

    public static void countingWatermelons(List<CommonWatermelon> commonWatermelons){
        int res = commonWatermelons.stream().map(commonWatermelon -> commonWatermelon.quantity)
                .reduce((a,b)-> a + b).get();
        System.out.println("这批西瓜一共有：" + res);
    }
}
