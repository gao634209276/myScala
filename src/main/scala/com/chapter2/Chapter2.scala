package com.chapter2

/**
  * Created by noah on 17-6-23.
  */
object Chapter2 {
  //函数定义
  def max(x: Int, y: Int): Int = {
    if (x > y)
      x
    else
      y
  }

  def max2(x: Int, y: Int) = if (x > y) x else y

  def greeting() = println("Hello world");

  //用While做循环，用if做判断。
  //注意必须把while和if的boolean表达式放在括号里
  //下面函数的编码风格属于指令式编程
  def fun1() = {
    var i = 0
    while (i < 10) {
      if (i != 0)
        print(" " + i)
      else
        print(i)
      //error：i++
      i += 1
    }
    println()
  }

  //用foreach和for做枚举
  //下面是函数的编码风格属于函数式编程
  //语法：括号、命名参数列表、右箭头、函数体
  def fun2(args: Array[String]): Unit = {
    //scala解释器推断出arg是String类型，因为调用foreach数组的元素的类型是String
    args.foreach { arg => println(arg) }
    //更明确的写法
    args.foreach { (arg: String) => println(arg) }
    //{} 替换成 () 也是可以的
    args.foreach((arg: String) => println(arg))

  }

  //指令式for循环，为了引导你使用函数式编程，scala只有一个指令式for循环
  def fun3(args: Array[String]): Unit = {
    for (arg <- args) {
      println(arg)
    }
  }

  def main(args: Array[String]): Unit = {
    var greetings = new Array[String](3)
    greetings(0) = "Hello"
    greetings(1) = ","
    greetings(2) = "world!"
    fun3(greetings)
    //println(max2(3,2))
  }

}
