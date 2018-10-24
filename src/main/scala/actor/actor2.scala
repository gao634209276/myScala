package actor

import scala.actors.Actor._

object actor2 {

  case class Speak(line: String)

  case class Gesture(bodyPart: String, action: String)

  case class NegotiateNewContract()

  case class ThatsAWrap()

  // receive方法的单次执行问题, 该方法只会执行第一次 tell 处理
  def receiveOnce(): Unit = {
    val badActor =
      actor {
        //这里receive方法只会匹配一次便结束
        receive {
          case NegotiateNewContract =>
            System.out.println("I won't do it for less than $1 million!")
          case Speak(line) =>
            System.out.println(line)
          case Gesture(bodyPart, action) =>
            System.out.println("(" + action + "s " + bodyPart + ")")
          case _ =>
            System.out.println("Huh? I'll be in my trailer.")
        }
      }
    //receive方法只处理下面这条语句发送的消息
    badActor ! NegotiateNewContract
    //下面其余的消息不会被处理
    badActor ! Speak("Do ya feel lucky, punk?")
    badActor ! Gesture("face", "grimaces")
    badActor ! Speak("Well, do ya?")
  }

  // 通过while循环接受多次
  def receiveMore() = {
    def ct = "Thread " + Thread.currentThread().getName() + ": "
    val badActor =
      actor {
        var done = false
        //while循环
        while (!done) {
          receive {
            case NegotiateNewContract =>
              System.out.println("I won't do it for less than $1 million!")
            case Speak(line) =>
              System.out.println(line)
            case Gesture(bodyPart, action) =>
              // 执行结果可以看到,Actor最终的实现仍然是线程，只不过它提供的编程模型与java中的编程模型不一样而已。
            System.out.println(ct + "(" + action + "s " + bodyPart + ")")
            case ThatsAWrap =>
              System.out.println("Great cast party, everybody! See ya!")
              done = true
            case _ =>
              System.out.println("Huh? I'll be in my trailer.")
          }
        }
      }
    //下面所有的消息都能被处理
    badActor ! NegotiateNewContract
    badActor ! Speak("Do ya feel lucky, punk?")
    //  利用!?发送同步消息，等待返回值
    badActor !? Gesture("face", "grimaces")
    System.out.println("Sending....")
    badActor !? Speak("Well, do ya?")
    System.out.println("Sending....")
    //消息发送后，receive方法执行完毕
    badActor ! ThatsAWrap
  }


  def main(args: Array[String]): Unit = {

  }
}
