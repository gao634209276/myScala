package com.chapter8
/**
  *第8章 函数和闭包
  *
  * Scala提供了许多Java中没有定义函数的方式。
  * 1、除了作为对象成员函数的方法之外，还有内嵌在函数中的函数，函数字面量和函数值。
  * 2、函数式编程风格的一个重要设计原则是：程序应该被结构成若干小的函数，每块实现
  *    一个定义完备的任务，每块都非常小。这种风格的好处是它让程序员能够把这些模块
  *    的灵活组装成更复杂的事物。每个小块应该充分简化到足以单独理解。可以用本地函数
  *    或者私有方法达到这个目的。
  */

import scala.io.Source

/**
  *8.1 方法
  *  对象中的函数被称为方法。
  */
//例子：读取文件，打印出超过指定宽度的文本行
object LongLine {

  def processFile(filename: String,  width: Int) {
    val source = Source.fromFile(filename)
    for(line <- source.getLines) {
      processLine(filename, width, line)
    }
  }

  private def processLine(filename: String,  width: Int, line: String) {
    if(line.length() > width) {
      println(filename +" : " +line)
    }
  }

  def main(args: Array[String]): Unit = {
    processFile("D:\\study\\scalaWorkSpace\\Scala\\test.scala", 50)
  }

}
