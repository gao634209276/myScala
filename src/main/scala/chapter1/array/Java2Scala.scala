package chapter1.array

import scala.collection.JavaConverters._

object Java2Scala {

  val list = java.util.Arrays.asList("hello", "world")

  def main(args: Array[String]): Unit = {
    val ls = list.asScala
    print()

  }

}
