package session3;

import session1.SessionWatermelon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: cyz
 * Date: 2019/8/3
 * Description:
 */
public class NewWatermelonPipline {
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
        Function<Object,CommonWatermelon> function = obj -> {
            int quaity = 0;
            if (obj instanceof AppleWatermelon){
                quaity = ((AppleWatermelon) obj).appleQuantity;
            }
            else if (obj instanceof BananaWatermelon){
                quaity = ((BananaWatermelon) obj).bananaQuantity;
            }
            return new CommonWatermelon(quaity);
        };
        Stream.concat(bananaWatermelons.stream(),appleWatermelons.stream()).
                forEach(obj -> commonWatermelons.add(function.apply(obj)));
        return commonWatermelons;
    }
    public static List<CommonWatermelon> filterWatermelons(List<CommonWatermelon> commonWatermelons){
        //使用 Predicate 将西瓜中质量小0和质量大于50的瓜挑出来，丢掉。
        Predicate<CommonWatermelon> bigger = commonWatermelon -> commonWatermelon.quantity >= 0;
        Predicate<CommonWatermelon> less = commonWatermelon -> commonWatermelon.quantity <= 50;
        return commonWatermelons.stream()
                .filter(bigger.and(less))
                .collect(Collectors.toList());
    }
    public static void writeWatermelonReport(List<CommonWatermelon> filterWatermelons){
        //使用 Consumer 创建出5个检查人员，每个检查人员都会检查每个西瓜，
        // 使用 System.out.println("X 号检察员检查第 N 个西瓜，质量为 Y 完毕")。该过程使用多线程完成。
        //也就是说我们会创建出 5 * N 个线程，待所有检查人员检查完成后
        // （使用 CountDownlatch 来确认所有线程都执行完成了），观察所有的检验报告。
        CountDownLatch countDownLatch = new CountDownLatch(5 * filterWatermelons.size());
        List<Consumer<Integer>> consumerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            consumerList.add((index) ->
                            System.out.println(finalI + " 号检察员检查第 "
                            + index
                            + " 个西瓜，质量为 "
                            + filterWatermelons.get(index).quantity
                            + " 完毕")
                    );
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,
                10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));

        consumerList.forEach(consumer -> {
            for (int i = 0; i < filterWatermelons.size(); i++) {
                final int finalI = i;
                threadPoolExecutor.submit(() -> {
                    consumer.accept(finalI);
                    countDownLatch.countDown();
                });
            }
        });
        //如果没有全部报告都写完，阻塞在这里不允许返回。
        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        threadPoolExecutor.shutdownNow();
    }
    public static void sendoutWatermelons(List<CommonWatermelon> commonWatermelons){
        List<CommonWatermelon> list = commonWatermelons.stream()
                .sorted(Comparator.comparing(w -> w.quantity))
                .collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0){
                stringBuilder.append(",");
            }
            stringBuilder.append(list.get(i).quantity);
        }
        stringBuilder.append("]");
        System.out.println("所有西瓜"+stringBuilder.toString());
    }

    public static void countingWatermelons(List<CommonWatermelon> commonWatermelons){
        int res = commonWatermelons.stream().map(commonWatermelon -> commonWatermelon.quantity)
                .reduce((a,b)-> a + b).get();
        System.out.println("这批西瓜一共有：" + res);
    }
}
