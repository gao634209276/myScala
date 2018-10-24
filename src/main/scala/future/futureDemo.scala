package future

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

// scala中的Future和Promise都是非阻塞的执行，既可以通过回调函数获取结果，也可以通过阻塞的方法串行获取结果
object futureDemo extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  // 一个Future会持有一个值，虽然这个值在将来某个时间点才可用
  // 如果计算未完成，那么这个Future就未完成。
  // 如果计算完成(得到结果或者异常),这个Future就完成了。
  // Future只能被指派一次，一旦Future给定了一个值或异常，它的结果不能修改。
  val f: Future[Int] = Future {
    Thread.sleep(100) //模拟某个耗时操作 比如网络请求
    println("haha")
    10
  }
  Thread.sleep(1000)

  // 异步方法获取结果（回调）
  // scala提供了onSuccess/onFailure/onComplete等回调函数
  f.onComplete({
    case Success(i) => println(i)
    case Failure(e) => e.printStackTrace()
  })
  // 以上代码采用偏函数形式，可以注册多个回调，即:
  f.onComplete(result => result match {
    case Success(i) => println(i + 20)
    case Failure(e) => e.printStackTrace()
  })


  // 同步方法获取结果
  // 通过Await.result可以同步获取结果，或者超时或者异常。
  // Await.ready等待计算完成，不返回结果。
  val r = Await.result(f, Duration.Inf) //Await.result(f,1 seconds)

}
