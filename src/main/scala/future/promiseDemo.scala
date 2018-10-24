package future

import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success, Try}

/**
  * 除了通过Future.apply创建Future对象，还可以使用Promise.future。
  * 如果说Future是一个只读的，值还没计算的占位符。
  * 那么Promise就是一个可写的，单次指派的容器。
  * Promise可以通过调用success代表Future成功完成,failure方法抛出异常。
  * 或者更抽象的complete
  */
object promiseDemo extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  // import scala.concurrent.duration._
  val p = Promise[Int]
  val f = p.future
  val producer = Future {
    p complete Try {
      100
    }
  }

  val consumer = Future {
    f onComplete {
      case Success(i) => println(i)
      case Failure(e) => e.printStackTrace()
    }
  }
  Thread.sleep(1000)
}
