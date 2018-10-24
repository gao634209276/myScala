package option

import java.io.IOException

object eitherDemo extends App {

  // 一个方法在传不同参数时得到不同返回值，返回值是两个不相关的类型，分别是Left、Right
  // 惯例中一般认为 Left 包含错误或无效值。 Right 包含正确或有效值
  def readfile(): Either[IOException, String] = try {
    Right("hello")
  } catch {
    case e: IOException => Left(e)
  }

  println(
    readfile() match {
      case Right(msg) => msg
      case Left(e) => e.getMessage
    })


}
