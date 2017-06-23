package com.chapter8

import scala.io.Source


/**
  * 8.2 本地函数
  * 1、本地函数就是定义在函数内部的函数。
  * 2、本地函数可以访问外部函数的参数
  */
//改写上一个例子
object LongLine2 {

  def processFile(filename: String, width: Int) {
    val source = Source.fromFile(filename)
    for (line <- source.getLines) {
      processLine(line)
    }

    def processLine(line: String) {
      if (line.length() > width) {
        println(filename + " : " + line)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    processFile("D:\\study\\scalaWorkSpace\\Scala\\test.scala", 50)
  }

}