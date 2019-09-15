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
## session6
熟练掌握TCP原理
### 学习资料
- https://blog.csdn.net/qq_31869107/article/details/81327494 TCP握手与挥手详解
- https://blog.csdn.net/yao5hed/article/details/81046945 TCP 滑动窗口
- https://blog.csdn.net/m0_37962600/article/details/79993310 TCP 中的拥塞控制算法
- https://blog.csdn.net/occupy8/article/details/48174307 TCP 中的 Nagel 算法
- https://www.cnblogs.com/steven520213/p/8005258.html TCP 与 UDP 的区别与联系
### 主要关注内容
TCP 基础链接握手原理。 TCP 滑动窗口以及拥塞控制算法。 TCP 中 Nagel 算法。
### 实现
请不要太过关注 TCP 协议各个字节各个头部的含义，比如第2个字节到第8个字节代表什么，这里请暂时不要关注这些细节。

主要注重 TCP 协议的流程，算法。
1. 画出属于自己的握手原理图，描述为什么什么是 TIME_WAIT，有什么策略。
2. 用伪代码写出 Nagel 算法，描述清楚流程即可。
3. 描述滑动窗口、拥塞控制 的基础机制，以及这样设计能带来什么好处。
4. 描述清楚 TCP 与 UDP 的区别与联系，设计一个 TCP 与 UDP 混合使用的技术方案。
5. 实现简单的 TCP 三次握手四次挥手示例代码 见 TCPClient TCPServer TCPStatus。
## session7
熟练掌握JVM原理
### 学习资料
https://blog.csdn.net/zhang_jiayuan/article/details/82083163 https://www.cnblogs.com/rinack/p/9888692.html
### 主要关注内容
JVM内存分布 Class加载机制 JVM运行时 GC算法
### 作业
实现一个JVM 的 gc 流程，JVM 为虚拟机类，JMemory 为虚拟的虚拟机内的内存定义模型。
- 这里自己实现 gc 算法，比如什么时候应该进行代的晋升，什么时候进行young gc，什么时候进行full gc，什么时候应该crash。
- 核心逻辑：
- 新对象优先放 Eden 区，然后经过一定轮数晋升到 Survivor 区域。Survivor 区域分为两个区域循环利用。
- 对象 gc 经过一定轮数后，从 Survivor 区晋升到 Old 区。
- 如果 old 区放不下了，进行full gc。
- 如果 永久代放不下了，进行full gc。
- ps:本作业不要求多线程回收，每次回收就 stop the world 好了，暂时不要太纠结这块的性能
### 目标
掌握JVM 的内存分布，以及基础的 GC 算法。