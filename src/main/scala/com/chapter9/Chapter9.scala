package com.chapter9

import java.io.{File, PrintWriter}

/**
  * Created by noah on 17-6-23.
  */
object Chapter9 {
  /**
    * 9.2 简化客户代码
    */
  //判断传入的值中是否有小于0的
  //未使用高阶函数
  def containsNeg(nums: List[Int]): Boolean = {
    var exists = false
    for (num <- nums)
      if (num < 0)
        exists = true
    exists
  }

  //使用高阶函数exits
  def containsNeg2(nums: List[Int]): Boolean = nums.exists(_ < 0)

  //判断List中是否含有奇数
  def containsOdd(nums: List[Int]) = nums.exists { _ % 2 == 1 }

  /**
    * 9.3 柯里化
    *
    */
  def fun1() {
    //未被柯里化的函数
    def plainOldSum(x: Int, y: Int) = x + y

    //柯里化的函数
    def curriedSum(x: Int)(y: Int) = x + y

    println(plainOldSum(1, 2))
    //实际上接连调用了两个传统函数。第一个函数调用带单个的名为x的Int参数，
    //并返回第二个函数的函数值。第二个函数带Int参数y。
    println(curriedSum(1)(2))
  }

  /**
    * 9.4 编写新的控制结构
    * 1、在拥有头等函数的语言中，即使语言的语法是固定的，你也可以有效地制作新的控制结构。
    * 所有你需要做的就是创建带函数做参数的方法。
    * 2、任何时候，当你发现你的代码中多个地方有重复的控制模式时，就应该考虑把它实现为一个
    * 新的控制结构。
    * 3、Scala的任何方法调用，如果确定只传入一个参数，就能可选地使用花括号替代小括号包围参数
    */

  def fun2() {
    def twice(op1: Double => Double, x: Double) = op1(op1(x))
    println(twice(_ + 1,5))
  }

  //打开一个资源，对它进行操作，然后关闭资源。可以用如下方法将其捕获并放入控制抽象
  def withPrintWriter(file: File, op: PrintWriter => Unit) {
    val writer = new PrintWriter(file)
    try {
      //把writer借给函数op，这是“借贷模式”
      op(writer)
    } finally {
      writer.close()
    }
  }

  //使用上面定义的方法
  def fun3() {
    val file =  new File("D:\\study\\scalaWorkSpace\\Scala\\test.scala")
    withPrintWriter (file, writer => writer.println(new java.util.Date));
  }


  //对上面的withPrintWriter进行柯里化
  def withPrintWriter2(file: File)(op: PrintWriter => Unit) {
    val writer = new PrintWriter(file)
    try {
      //把writer借给函数op，这是“借贷模式”
      op(writer)
    } finally {
      writer.close()
    }
  }

  def fun4() = {
    val file = new File("D:\\study\\scalaWorkSpace\\Scala\\test.scala")
    //采用柯里化就可以在多个参数的时候使用花括号了，这样的写法更加赏心悦目。
    withPrintWriter2(file) {
      writer => writer.println(new java.util.Date)
    }
  }

  /**
    * 9.5 传名参数
    */
  var assertionsEnabled = true

  def byNameAssert(predicate: => Boolean) =
    if(assertionsEnabled && !predicate)
      throw new AssertionError

  def fun5() {
    byNameAssert(5 > 4)
  }

  def main(args: Array[String]): Unit = {
    fun5()
  }

}