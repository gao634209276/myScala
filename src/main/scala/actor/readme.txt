https://blog.csdn.net/lovehuangjiaju/article/details/47623177

Scala很好地解决了java并发编程的问题，要在scala中进行并发编程，有以下几种途径可以实现：
1 actor消息模型、akka actor并发模型。

2 Thread、Runnable

3 java.util.concurennt

4 第三方开源并发框架如Netty，Mina

在上述四种途径当中，利用 actor消息模型、akka actor并发模型是scala并发编程的首先，
本节主要介绍actor消息模型，akka actor并发模型我们将放在后面的章节中介绍。

Actor有下列几种状态：

初始状态（New），Actor对象被创建，但还没有启动即没有执行start方法时的状态
执行状态（Runnable），正在执行时的状态
挂起状态（Suspended），在react方法中等待时的状态
时间点挂起状态（TimedSuspended），挂起状态的一种特殊形式，reactWithin方法中的等待时的状态
阻塞状态（Blocked），在receive方法中阻塞等待时的状态
时间点阻塞状态（TimedBlocked），在receiveWithin方法中阻塞等待时的状态
结束状态（Terminated），执行完成后被销毁