package future

import scala.concurrent.Future

object futureCombine extends App {

  val firstValFuture = Future[Int] {
    1
  }

  // 使用map方法可以组合Future：
  // flapMap方法也可以组合Future，map方法和flatMap方法唯一的区别是flatMap内部需要返回Future，而map不是
  val secondFuture = firstValFuture.map { num =>
    // val secondFuture = firstValFuture.flatMap { num =>
    println("firstFuture: " + num)
    num + "111"
  }

  secondFuture.onSuccess {
    case result => println(result)
  }
}
