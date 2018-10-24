package actor

import scala.actors._

/**
  * 3.
  * scala中的Actor也是构建在java线程基础之上的，
  * 前面在使用Actor时都是通过创建Actor对象，然后再调用act方法来启动actor。
  * 我们知道，java中线程的创建、销毁及线程间的切换是比较耗时的，
  * 因此实际中尽量避免频繁的线程创建、销毁和销毁。
  * Scala中提供react方法，在方法执行结束后，线程仍然被保留。
  * 下面的代码演示了react方法的使用：
  *
  */
object NameResolver extends Actor {

  import java.net.{InetAddress, UnknownHostException}

  def act() {
    react {
      //匹配主线程发来的("www.scala-lang.org", NameResolver)
      case (name: String, actor: Actor) =>
        //向actor发送解析后的IP地址信息
        //由于本例中传进来的actor就是NameResolver自身
        //也即自己给自己发送消息，并存入将消息存入邮箱
        actor ! getIp(name)
        //再次调用act方法，试图从邮箱中提取信息
        //如果邮箱中信息为空，则进入等待模式
        act()
      case "EXIT" =>
        println("Name resolver exiting.")
      // quit
      //匹配邮箱中的单个信息，本例中会匹配邮箱中的IP地址信息
      case msg =>
        println("Unhandled message: " + msg)
        act()
    }
  }

  def getIp(name: String): Option[InetAddress] = {
    try {
      Some(InetAddress.getByName(name))
    } catch {
      case _: UnknownHostException => None
    }
  }
}

object Main extends App {
  NameResolver.start()
  //主线程向NameResolver发送消息("www.scala-lang.org", NameResolver)
  NameResolver ! ("www.scala-lang.org", NameResolver)
  NameResolver ! ("wwwwww.scala-lang.org", NameResolver)

}

