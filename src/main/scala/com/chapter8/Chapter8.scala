package com.chapter8
/**
  * 8.3 头等函数
  * 1、什么是头等函数？
  * 在计算机科学的一个编程语言中，如果它把方法作为一等公民的时候，
  * 那么这个编程语言就含有头等函数（first-class functions）。
  * 特别是，这意味着那个语言支持将函数当作参数传递给其他的函数，
  * 从那其他的函数里返回出值，并且可以将他们设定为变量，或者将他们存储在数据结构中。
  * Scala的函数是头等函数。你不仅可以定义和调用函数，还可以把它们写成匿名的函数字面量，
  * 并把它们作为值传递。函数字面量被编译进类，并在运行期间实例化为函数值。
  * 函数字面量和值的区别在于：函数字面量存在于源代码，而函数值作为对象存在于运行期。
  * 这个区别很像类（源代码）和对象（运行期）之间的关系。
  *
  */
object Chapter8 {

  // (x: Int) => x + 1 这是一个函数字面量，=> 指明这个函数把左边部分转化成右边部分. 并将函数字面量存为变量
  def fun1() {
    var increase = (x: Int) => x + 1
    println("increase:  " + increase(10))
  }

  // 如果想让函数字面量包含多条语句，用{},函数的返回值是最后一行表达式产生的值
  def fun2() {
    var increase = (x: Int) => {
      x + 1
      println("这是increase2")
      x + 2
    }
    println("increase:  " + increase(10))
  }

  //所有的集合类都有foreach方法。它以函数作为入参，并对每个元素调用该函数
  def fun3() {
    val someNumber = List(-10, 1, -3, 5, -7, 12)
    someNumber.foreach((x: Int) => println(x))
  }

  //集合类型还有filter方法
  def fun4(): List[Int] = {
    val someNumber = List(-10, 1, -3, 5, -7, 12)
    someNumber.filter((x: Int) => x > 0)
  }

  /**
    * 8.4 函数字面量的短格式
    * Scala 提供了许多方法去除冗余信息并把函数字面量写得更简短。
    * 一种让函数字面量更简短的方式是去除参数类型。
    */
  def fun6() {
    val someNumber = List(-10, 1, -3, 5, -7, 12)
    //去除了参数类型
    someNumber.foreach(x => println(x))
  }

  //集合类型还有filter方法
  def fun7(): List[Int] = {
    val someNumber = List(-10, 1, -3, 5, -7, 12)
    //去除了参数类型
    someNumber.filter(x => x > 0)
  }

  /**
    * 8.5 占位符语法
    * 如果想让函数字面量更加简洁，可以把下划线当作一个或更多参数的占位符。
    * 可以把下划线看作表达式里需要被填补的“空白”，这个“空白”在每次函数被调用的时候用函数的参数填入
    */
  def fun8() {
    val someNumber = List(-10, 1, -3, 5, -7, 12)
    //下面那句与someNumber.filter(x => x > 0)等价
    println(someNumber.filter(_ > 0))
  }

  def fun9() {
    val f = (_: Int) + (_: Int)
    // 打印出15
    println(f(5, 10))
  }

  /**
    * 8.6 部分应用函数
    */
  def fun10() {
    val someNumber = List(-10, 1, -3, 5, -7, 12)
    //以下四句的意思是一样的
    someNumber.foreach(x => println(x))
    someNumber.foreach(println(_))
    someNumber.foreach(println _)
    someNumber.foreach(println)
  }

  //偏函数
  def fun11() {
    def sum(a: Int, b: Int, c: Int) = a + b + c

    //实例化一个带3个缺失整数参数的函数值，并把这个新的函数值的索引赋给变量a
    val a = sum _
    println(a(1, 2, 3))
    val b = sum(1, _: Int, 3)
    println(b(20))
  }

  /**
    * 8.7 闭包
    * 依照函数字面量在运行时创建的函数值(对象)被称为闭包。
    * 不带自由变量的函数字面量，如(x: Int) => x + 1, 被称为封闭项
    * 任何带有自由变量的函数字面量，如如(x: Int) => x + more, 被称为开放项
    *
    */
  def fun12() {
    println("000000")
    var more = 1
    val addMore = (x: Int) => x + more //more是什么
    //打印出: 11
    println(addMore(10))
    more = 2
    //打印出: 12
    println(addMore(10))
  }

  def fun13() {
    var sum = 0
    val someNumber = List(-3, -2, -1, 1, 2, 5)
    someNumber.foreach {
      sum += _
    }
    //打印出: 2
    println(sum)
  }

  def fun14() {
    def makeIncreaser(more: Int) = (x: Int) => x + more

    val inc1 = makeIncreaser(1)
    //打印出：2
    println(inc1(1))

    val inc999 = makeIncreaser(999)
    //打印出：1000
    println(inc999(1))
  }

  /**
    *8.8 重复参数
    * 在Scala中，可以指明函数的最后一个参数是重复的，从而允许客户端传入可变长度参数列表。
    * 想要标注一个重复参数，可在参数的类型后面放一个星号。
    */
  def fun15() {
    def echo(args: String*) = {
      for (arg <- args) print(arg + " ")
      println()
    }

    echo("one")
    echo("one", "two")
    echo("one", "two", "three")
    //上面echo中args的类型的实际上是Array[String]。但是直接传递一个数组会报错！
    val arr = Array("one", "two", "three")
    //echo(arry) // 编译不通过
    //在arr后面加上: _* 就可以将数组传入可变长度参数列表。
    //这个标注告诉编译器把arr的每个元素当作参数。
    echo(arr: _*)
  }

  /**
    * 8.9 尾递归
    * 1、在函数最后一个动作调用自己的函数，被称为尾递归
    * 2、Scala检测到尾递归就用新值更新函数参数，然后把它替换成一个回到
    * 函数开头的跳转。这样它的效率就与While循环一样了。
    * 3、尾调优化限定了方法或嵌套函数必须在最后一个操作调用本身,
    * 而不是转到某个函数值或什么其他的中间函数的情况。所以Scala
    * 里，尾递归的使用局限还是很大的。
    */
  //尾递归的一个例子
  private val theNumber = 999

  private def isGoodEnough(guess: Int) = {
    if (guess == theNumber)
      true
    else
      false
  }

  private def improve(guess: Int) = {
    if (guess > theNumber)
      guess - 1
    else
      guess + 1
  }


  def approximate(guess: Int): Int = {
    println("调用一次approximate,guess是：" + guess)
    if (isGoodEnough(guess)) guess
    else approximate(improve(guess))
  }

  //尾递归函数追综
  //这个方法不是尾递归，因为最后还做了一步加1的操作
  def boom(x: Int): Int = {
    if (x == 0) throw new RuntimeException
    else boom(x - 1) + 1
  }

  //这个方法是尾递归
  def bang(x: Int): Int = {
    if (x == 0) throw new RuntimeException
    else bang(x - 1)
  }

  //尾递归的局限
  //1、互相递归的函数不能优化
  def isEvent(x: Int): Boolean =
  if (x == 0) true else isOdd(x - 1)

  def isOdd(x: Int): Boolean =
    if (x == 0) true else isEvent(x - 1)

  //2、以下函数也不能优化
  var funValue = nestedFun _

  def nestedFun(x: Int) {
    if (x != 0) {
      println(x)
      funValue(x - 1)
    }
  }

  //入口程序
  def main(args: Array[String]): Unit = {
    bang(10)
  }
}