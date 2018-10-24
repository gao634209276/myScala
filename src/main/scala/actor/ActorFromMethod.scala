package actor

import scala.actors.Actor._

/**
  * 2.前面给的是通过extends Actor的方式来创建一个Actor类，
  * 其实scala.actors.Actor中提供了一个actor工具方法，可以非常方便地直接创建Actor对象如：
  */
object ActorFromMethod extends App {
  //通过工具方法actor直接创建Actor对象
  //创建的actor对象无需调用start方法，对象创建完成后会立即执行。
  val methodActor = actor {
    for (i <- 1 to 5)
      println("That is the question.")
    Thread.sleep(1000)
  }
}
