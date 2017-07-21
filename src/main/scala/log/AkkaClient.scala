package log

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.ActorSystem
import akka.routing.RoundRobinRouter
import akka.event.Logging

/**
  * Akka: Slf4j + Logback
  */
object AkkaClient extends App {

  sealed trait AkkaTestMsg

  case object Start

  case object Stop

  class Client extends Actor {

    val log = Logging(context.system, this)

    def receive = {
      case Start => println("start")
        log.info("good start!")
        self ! Stop
      case Stop => println("stop")
        log.info("good stop!")
        context.system.shutdown
    }
  }

  val system = ActorSystem("system")
  val client = system.actorOf(Props[Client], "client")
  val log = Logging(system, client)

  log.info("ok")
  client ! Start
}
