package com.chapter18

/**
  * 第18章 有状态的对象
  */
object Chapter18 {
  /**
    * 18.2 可重新赋值的变量和属性
    * 可以对可重新赋值的变量执行两种操作：获取它的值，或者把它设为新值。
    * 在JavaBeans这样的库中好，常被封装为getter和setter方法。
    * Scala里，对象的每个非私有的var类型成员变量都隐含定义了getter和setter
    * 方法。var变量x的getter方法命名为"x",它的setter方法命名为"x_"
    */
  class Time {
    private [this] var h = 12
    private [this] var m = 0

    def hour: Int = h
    def hour_ (x: Int) {
      require(0 <= x && x < 24)
      h = x
    }

    //相当于java中getMinute
    def minute:Int = m
    //相当于java中setMinute
    def minute_ (x:Int) {
      require(0 <= x && x < 60)
      m = x
    }

  }

  def main(args: Array[String]): Unit = {
    val time = new Time()
    time.hour_(12)
    time.minute_(34)
    println(time.hour + ":" + time.minute)
  }
}
