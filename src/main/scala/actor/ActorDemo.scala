package actor

import scala.actors.Actor

/**
  * 1.混入Actor特质，然后实现act方法,
  * 如同java中的Runnable接口一样,各线程的run方法是并发执行的,
  * Actor中的act方法也是并发执行的
  */
class ActorDemo extends Actor {
  //实现 act()方法
  def act() {
    while (true) {
      //receive从邮箱中获取一条消息
      //然后传递给它的参数
      //该参数是一个偏函数
      receive {
        case "actorDemo" => println("receive....ActorDemo")
      }
    }
  }
}

object ActorDemo extends App {
  val actor = new ActorDemo
  //启动创建的actor
  actor.start()
  //主线程发送消息给actor
  actor ! "actorDemo"
}
