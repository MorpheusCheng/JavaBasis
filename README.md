# JavaBasis
java基础练习
## session1
熟练掌握 Java 基础语法。时间:1周。
### 目标 ：实现一个卖西瓜的摊摊函数
```java
public class SessionWatermelon {
	public static void main(String[] args) {
	}

	public static int[] sell(int[] buyNum){
		return null;
	}

	private static int sell0(int buyNum){
		return 0;
	}
}
```
逻辑：
1. 如果没顾客，那就不卖
2. 来了N个顾客，要按顺序卖，不卖的客户也要告诉他不卖（卖0）。
3. 单个卖的逻辑在 sell0 实现。
4. 如果超过50个，只卖50个。
5. 如果需求小于0个，sell0 抛出异常，在 sell 捕获异常并登记为不卖（卖0）。
6. 在控制台打印出最终这批卖出去多少个。术语：xxx总共卖出去N个。
7. 实现自己的打印数组函数。格式: `[1,2,3,4,5]`;
## session2
熟练掌握 Java 封装继承多态思想，时间一周
### 目标：实现一个栈，和实现一个带日志输出的栈
要求：
1. 使用int[] 存储
2. 如果栈满了，扩容2倍。
3. 如果栈为空 pop 和 peak 抛异常。
ps: 带日志输出的栈：在所有操作的前面打出日志，以push为例子，开始push之前 System.out.println("start push"); push 结束的时候 System.out.println("end push");
```java
public interface Stack {

	/**
	 * 入栈
	 * @return boolean
	 */
	boolean push(int value);

	/**
	 * 出栈
	 * @return int
	 */
	int pop();

	/**
	 * 查看栈顶元素
	 */

	int peak();

	/**
	 * 栈的大小
	 */

	int size();

	/**
	 * 栈是否为空
	 * @return
	 */
	boolean isEmpty();

}
```
## session3 
熟练掌握各种 Java Lambda 技巧
### 目标
实现对于西瓜的流水线处理，最终产出合格的西瓜。
1. 把两种西瓜使用 stream 遍历，然后 Function 转换为同一种西瓜。
2. 使用 Predicate 将西瓜中质量小于0和质量大于50的瓜挑出来，丢掉。
3. 使用 Consumer 创建出5个检查人员，每个检查人员都会检查每个西瓜，使用 System.out.println("X 号检察员检查第 N 个西瓜，质量为 Y 完毕")。该过程使用多线程完成。 也就是说我们会创建出 50 个线程，待所有检查人员检查完成后（使用 CountDownlatch 来确认所有线程都执行完成了），观察所有的检验报告。
4. 把现在还剩下的西瓜质量按顺序打印出来，格式 [1，3，4，5，6]。
5. 使用 reduce 计算一下，最终这批西瓜总计有多重，并打印出来
## session4
熟练掌握 JDK 基础类 String、字符串匹配算法、数字转字符串、链表翻转算法
### 目标
1. 实现 StringInterface 接口，实现一个容器为数组的 ArrayString。
2. 实现 StringInterface 接口，实现一个容器为链表的 LinedString。
3. 实现字符串匹配算法 indexOf。
4. 实现数字转字符串的算法。
5. 实现字符串首尾翻转字符串算法。
6. 描述为什么在 JDK 中使用数组而不是链表来实现字符串。
7.描述为什么 String 是一个不可变的类，以及是怎么实现不可变的。
## session5
熟练掌握 Hash 原理。
### 学习资料：
- https://www.jianshu.com/p/bba9b61b80e7 什么是 Hash 函数
- https://www.cnblogs.com/williamjie/p/9377028.html 解决 Hash 碰撞的方案
- https://www.cnblogs.com/dijia478/p/8006713.html JDK 1.7中 HashMap 的实现原理
### 主要关注内容
1. Hash 函数的原理以及使用
2. Hash 碰撞的解决方案。
3. HashMap 的实现原理
### 目标
1. 实现一个基于 链地址法 的HashMap
2. 实现一个 BitMap 和一个 BloomFilter 进行统计（附加题）
### 扩展阅读
- https://www.cnblogs.com/netxsky/articles/10422126.html BitMap 算法
- https://www.cnblogs.com/z941030/p/9218356.html BloomFilter 算法
