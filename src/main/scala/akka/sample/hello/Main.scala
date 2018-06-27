package sample.hello

import akka.sample.hello.HelloWorld


object Main {
  def main(args: Array[String]): Unit = {
    akka.Main.main(Array[String](classOf[HelloWorld].getName))

  }

  def test(): Unit = {
    import akka.actor.ActorSystem
    import akka.actor.Props

    val system = ActorSystem.create("Hello")
    val a = system.actorOf(Props.create(classOf[Nothing]), "helloWorld")
    System.out.println(a.path)
  }
}