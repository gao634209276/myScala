package com.chapter11

/**
  * 第11章 Scala的层级
  *
  * 11.1 Scala的类层级
  * 1、每个类都继承自Any这个超类，根类Any有两个子类AnyVal和 AnyRef。
  *    AnyVal是所有内建值类的父类，AnyRef是所有引用类的父类。
  *    Scala还在底端定义了Null和Nothing。Null是每个引用类(AnyRef)的子类。
  *    Noting在Scala的最底端，是任何类型的子类型。
  * 2、Any类中定义了如下方法：
  *    1) final def == (that: Any): Boolean
  *    2) final def != (that: Any): Boolean
  *    3) def equals(that: Any): Boolean
  *    4) hashCode: Int
  *    5) toString: String
  * 3、内建值类(AnyVal)有9个：
  *    Byte、Short、Char、Int、Long、Float、Double、Boolean和Unit
  *    前8个都对应Java的基本类型。
  *    Scala这些类的实例都写成字面量。所以不能使用new创建这些对象的实例。
  *    Unit对应Java中的void类型，它只有一个实例值,写成()
  * 4、AnyRef是所有引用类的基类。Scala被设计成可以同时工作在Java和.Net平台上。
  *    在Java平台上AnyRef是java.lang.Object的别名；
  *    在.Net平台上AnyRef是System.Object的别名。
  *    Scala中，Scala类与Java类的不同在于，它们还继承自一个ScalaObject的特别的
  *    记号特质。ScalaObject只包含一个方法，名为$tag,在内部使用以加速模式匹配
  *
  * 11.2 原始类型是如何实现的
  * 1、Scala中相等操作==被设计成：对值类型来说，就是自然的(数学或布尔)相等。
  *    对于引用类型，==被视为继承自Object的equals方法的别名。
  *    在比较引用类型时，Scala里的eq方法与java中的==一致。eq的反义词称为ne
  *
  * 11.3 底层类型
  * 1、 Null类是null引用对象的类型，它是每个引用类(继承自AnyRef的类)的子类。
  * 2、Nothing类型在Scala的类层级的最底端；它是任何其他类型的子类型。
  *    然而根本没有这个类型的任何值。Nothing的一个用处是标明不正常的终止。
  *
  */
object Chapter11 {
  def fun1() {
    val x = new String("abc")
    val y = new String("abc")
    println( x == y) // true
    println( x eq y) // false
    println( x ne y) // true

    val i = "abc"
    val j = "abc"
    println( i == j) // true
    println( i eq j) // true
    println( i ne j) // false

    println( x == i) // true
    println( x eq i) // false
    println( x ne i) // true
  }
  //演示Nothing类的作用
  def error(message: String): Nothing =
    throw new RuntimeException(message)

  def divide(x: Int, y: Int) =
    if(y != 0) x/y
    else error("can't divide by zero")

  def fun2() {
    divide(1,0)
  }

  def main(args: Array[String]): Unit = {
    fun2
  }
}
