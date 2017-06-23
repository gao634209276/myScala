package chapter1.control

import java.io.File

/**
  * Created by hadoop on 16-10-2.
  */
object MultFor {
  val filesHere: Array[File] = new java.io.File("src/main/scala/chapter1/control").listFiles

  def fileLines(file: java.io.File): List[String] =
    scala.io.Source.fromFile(file).getLines.toList

  //多重循环的实现：
  def grep(pattern: String): Unit =
    for (
      file <- filesHere if file.getName.endsWith(".scala");
      line <- fileLines(file) if line.trim.matches(pattern)
    )
      println(file + ": " + line.trim)

  //等同于for嵌套
  def grep2(pattern: String): Unit =
    for (file <- filesHere if file.getName.endsWith(".scala"))
      for (line <- fileLines(file) if line.trim.matches(pattern))
        println(file + ": " + line.trim)


  def main(args: Array[String]) {
    grep(".*gcd.*")
    println("----------------------------")
    grep2(".*gcd.*")
  }
}
