package com.chapter3

import scala.io.Source

/**
  * Created by noah on 17-6-23.
  */
object Chapter3 {
  /****************使用类型参数化数组(Array)************/
  //注意：当同时用类型和值参数化实例的时候，应该首先写方括号和类型参数，然后再写圆括号和值参数
  def fun1(): Unit = {
    //数组也是类的实例
    //注意：这并不是scala里创建和初始化数组的推荐方式
    val greetStrings = new Array[String](3)
    //下面这句代码等价于上面那句
    //val greetString：Array[String] = new Array[String](3)

    //任何对于对象的值参数应用将被转化成对apply方法的调用，当然前提必须定义过apply方法。
    //所以greetings(i) 被转化为 greetStrings.apply(i)
    greetStrings(0) = "Hello"
    greetStrings(1) = ","
    greetStrings(2) = "World"

    //这里 0 to 2 被转化成 (0).to(2)
    for(i <- 0 to 2) {
      println(greetStrings(i))
    }
  }

  //更简便地创建和初始化数组
  def fun2(): Unit = {
    //编译器根据传递的之类型推断Array[String]
    val numNames = Array("zero","one","two")
    //与上面等价，apply方法可以有不定个数的参数
    val numNames2 = Array.apply("zero","one","two")
  }

  /****************使用列表(List)******************/
  //方法没有副作用是函数式风格编程的重要理念。计算并返回值应该是方法唯一的目的。
  //这样做的好处之一是方法之间耦合度降低，便于重用。
  //好处之二是方法的参数和返回值都经过类型检查器的检查，可以较容易地根据类型错误推断出隐含的逻辑错误。
  //scala.List和Array一样只能保存同类对象。并且List本身是不可变的，就像是java中的String.
  //scala.List这是为了函数式编程风格而设计的
  def fun3(): Unit = {
    val oneTwo = List(1, 2)
    val threeFour = List(3, 4)
    // :::方法实现叠加功能
    val oneTwoThreeFour = oneTwo ::: threeFour
    //打印出：List(1, 2, 3, 4)
    println(oneTwoThreeFour)

    //::发音为“cons”
    val oneTwoThree = 1 :: List(2, 3)
    //打印出：List(1, 2, 3)
    println(oneTwoThree)
    //error : oneTwoFour = 2 :: 3 :: 4 ,Nil是空列表的简写，需要以Nil结尾定义空列表。
    //打印出：List(2, 3, 4)
    val oneTwoFour = 2 :: 3 :: 4 :: Nil
  }

  /****************使用元组(Tuple)***************/
  //元组和列表都是容器，元组和列表都是不可变的，但是元组可以保存不同对象，列表只能保存相同对象。
  def fun4(): Unit = {
    //下面那句相当于 val pair = Tuple3[Int, Char, String](1,'a',"Hello")
    val pair = (1,'a',"Hello")
    //用._基于1的索引进行访问
    println(pair._1)
    println(pair._2)
    println(pair._3)

  }

  /*********************使用集Set*******************/
  def fun5():Unit = {
    //不可变集
    //不加包名，默认是不可变的Set
    var immutableSet = Set("Hello", "Kitty")
    //等价
    //var immutableSet = scala.collection.immutable.Set("Hello", "Kitty");
    //这里重新创建了一个包含"Tom"的immutableSet
    immutableSet += "Tom";

    //可变集
    var mutableSet = scala.collection.mutable.Set("Hello", "Kitty");
    mutableSet += "Tom";

    //使用可变的HashSet，  要引入
    //import scala.collection.mutable.HashSet
    //使用不可变的HashSet，要引入
    //import scala.collection.immutable.HashSet
  }

  /****************使用Map************************/
  def fun6():Unit = {

    var inmutMap = Map(1 -> "Hello", 2 -> "world");
    println(inmutMap)

    var inmutHashMap = scala.collection.immutable.HashMap(1 -> "Hello", 2 -> "world");
    //imutHashMap += (3 -> "China") //error!
    println(inmutHashMap)

    var mutHashMap = scala.collection.mutable.HashMap("key1" -> "Hello", 2 -> "world");
    mutHashMap += (3 -> "China")
    //打印出：Map(2 -> world, key1 -> Hello, 3 -> China)
    println(mutHashMap)
    //打印出：None
    println(mutHashMap.get(1))
    //打印出：Some(Hello)
    println(mutHashMap.get("key1"))

  }

  /***************学习识别函数式编程风格**************/
  //如果含有var，可能是指令式编程；如果仅仅只有val，可能是函数式编程
  //向函数式编程转变的方式之一就是不用任何var编程
  //函数式风格更简洁，更不容易犯错
  //完全没有副作用和var才是真正函数式风格的例子
  //mkString 将各个arg用\n拼接起来
  //没有副作用的好处之一有助于程序更容易测试
  def formatArgs(args: Array[String]) = args.mkString("\n")

  /**
    * scala程序员的平衡感:
    * 崇尚val，不可变对象和没有副作用的方法。
    * 首先想到它们，只有在特定需要并加以权衡之后才选择var，可变对象和副作用的方法。
    */

  /****************从文件读取文本行******************/
  def fun7():Unit = {
    //需要import scala.io.Source
    for(line <- Source.fromFile("D:\\study\\words.txt").getLines)
      println(line)

    //用Source.fromFile("D:\\study\\words.txt").getLines().toList 转化成List
    val list = Source.fromFile("D:\\study\\words.txt").getLines().toList;
  }

  def main(args: Array[String]): Unit = {
    fun7()
  }
}
