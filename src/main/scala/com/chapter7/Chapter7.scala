package com.chapter7

/**
  * 第7章 内建控制结构
  *
  * Scala内建的控制结构屈指可数，仅有if、while、for、try、match和函数调用。
  * 如此之少的理由是，Scala从语法层面支持函数字面量。
  */
object Chapter7 {
  /**
    * 7.1 If表达式
    * 尽可能寻找使用val的机会它让代码即容易阅读又方便重构。
    */

  //指令式风格
  def fun1(args: Array[String]):Unit = {
    var filename = "default.txt"
    if (!args.isEmpty)
      filename = args(0)
    println(filename)
  }

  //函数式风格
  def fun2(args: Array[String]):Unit = {
    val filename =
      if(!args.isEmpty) args(0) else "default.txt"
    println(filename)
  }

  //或者如下
  def fun3(args: Array[String]):Unit = {
    println(if(!args.isEmpty) args(0) else "default.txt")
  }

  /**
    * 7.2 While 循环
    * 要点：
    * 1、由于while循环不产生值，因此它经常被纯函数式语言所舍弃。纯函数语言只有表达式，没有循环。
    * 2、如果你想编码重复执行某进程，直到某种状态改变的算法，那么while循环可以直接表达；
    * 如果使用函数式风格编写的话，大概要用递归实现。
    * 3、while循环经常和var结对出现。建议对while循环要审慎使用,如同质疑对var的使用那样。
    */
  //一个while循环的例子
  def gdcLoop(x: Long, y: Long): Long = {
    var a = x;
    var b = y;
    while (a != 0) {
      val temp = a
      a = b % a
      b = temp
    }
    b
  }

  //上面的例子用函数式风格编程如下：
  def gcd(x: Long, y: Long): Long =
    if(y == 0) x else gcd(y, x % y)

  //一个do-while循环的例子
  def fun4(): Unit = {
    var line = ""
    do {
      line = readLine();
      println("Read: " + line)
    } while (line != "")
  }

  /**
    * 7.3 For表达式
    * 1、枚举集合类
    *    1) for能做的最简单的事就是把集合中所有的元素都枚举一遍
    *    2) for表达式对任何种类的集合类都有效
    *    3) to操作符包含枚举的集合的上边限，unitl不包含上边限
    * 2、过滤
    *    有时并不想枚举集合的全部元素，而只想过滤出某个子集。这可以通过在
    *    for表达示的括号中添加过滤器，即if子句。需要的话可以添加多个if子句,
    *    如果for后面用的是小括号，那么多个if子句之间必须用";"隔开;
    *    如果for后面的小括号替换成大括号{},就可以省略if子句之间的";"
    * 3、嵌套枚举
    *    如果加入多个 <- 就得到了嵌套循环
    * 4、流间变量绑定
    * 5、创建新集合
    *    1)可以创建一个值去记住每一次迭代，只要在for表达式前加上yield
    *    2)加上yield后for表达式每次执行的时候都会产生一个新值。当for表单式完成
    *      的时候，结果将是包含了所有产生值得集合对象。
    *    3)for-yield表达式的语法是：for (子句) yield {循环体}
    *    4)yield保存的是循环体中的最后一句 (个人见解，不一定正确)
    */

  def filesHere = (new java.io.File(".")).listFiles
  //枚举集合类
  //枚举当前目录下的文件名
  def fun5(): Unit = {
    for(filename <- filesHere)
      println(filename)
  }

  //用to操作符打印出1~4
  def fun6(): Unit = {
    for(i <- 1 to 4)
      println(i)
  }

  //用until操作符只打印出1~3
  def fun7(): Unit = {
    for(i <- 1 until 4)
      println(i)
  }

  def fun8(): Unit = {
    //以下的遍历方式在Scala中不常见
    for(i <- 0 to filesHere.length - 1)
      println(filesHere(i))
    //以下常见
    for(i <- 0 until filesHere.length)
      println(filesHere(i))
  }

  //过滤
  //采用if进行过滤的一个例子
  def fun9(): Unit = {
    for(file <- filesHere if file.getName.endsWith(".scala"))
      println(file)
  }

  //或者不采用if进行过滤
  def fun10(): Unit = {
    for(file <- filesHere )
      if (file.getName.endsWith(".scala"))
        println(file)
  }

  //多个if子句进行过滤的一个例子
  def fun11(): Unit = {
    for(
      file <- filesHere
      if file.getName.endsWith(".scala");
      if file.isFile()
    ) println(file)
  }

  //嵌套循环
  def fileLines(file: java.io.File)  =
    scala.io.Source.fromFile(file).getLines().toList

  //注意：这里for之后用了{} 并且省略了if之间的;
  def grep(pattern: String) =
    for {
      file <- filesHere
      if file.getName.endsWith(".scala") //此处;省略
      line <- fileLines(file)
      if line.trim.matches(pattern)
    } println(file + ":" + line.trim)

  def fun12(): Unit = {
    grep(".*fun.*")
  }

  //流间变量绑定
  //以下line.trim只计算一遍
  def grep2(pattern: String) =
  for {
    file <- filesHere
    if file.getName.endsWith(".scala") //此处;省略
    line <- fileLines(file)
    trimmed = line.trim //绑定的变量被当作val引入和使用，但是不带val
    if trimmed.matches(pattern)
  } println(file + ":" + trimmed)

  //创造新集合
  def scalaFile():Array[java.io.File] = {
    for(
      file <- filesHere
      if file.getName.endsWith(".scala")
    ) yield {
      println(file)
      //other codes ...
      file //每一次循环都会将这个file保存起来，yield保存的是循环体中的最后一句
    }


  }

  /**
    * 7.4 使用try表达式处理异常
    * 1、抛出异常时，Scala中的方法参数列表后面不需要加throws。
    * 2、fun13中，如果异常是FileNotFoundException，那么第一个case子句将
    *    被执行；如果IOException,那么第二个子句被执行。如果都不是，将抛出异常。
    * 3、如果想让某些代码无论如何都执行，可以放在finally子句中。
    *    通常避免在finally子句中加上return，因为它可能会让产生的异常信息丢失。
    */
  //抛出异常，方法后面不需要带throws
  def fun12(n: Int): Int = {
    if(n == 0)
      throw new RuntimeException("参数不能为0")
    else
      n
  }

  //捕获异常
  def fun13(path: String) = {
    try {
      //throw new RuntimeException
      val file = new java.io.FileReader(path);
      //处理并关闭文件
    } catch {
      //处理错误
      case ex: java.io.FileNotFoundException =>
        println("java.io.FileNotFoundException：")
        println("找不到文件")
      case ex: java.io.IOException =>
        println("java.io.IOException：")
        println("其他IO错误")
    }
  }

  //finally 子句
  def fun14(path: String) = {
    val file = new java.io.FileReader(path);
    try {
      //使用文件
    } finally {
      file.close() //确保关闭文件
    }
  }
  //返回的值为1
  def fun15(): Int = {
    try {
      1
    } finally {
      2
    }
  }

  //返回的值为2
  def fun16(): Int = {
    try {
      return 1
    } finally {
      return 2
    }
  }

  //通常避免在finally子句中加上return,因为它会使异常丢失。
  def fun17(i: Int): Int = {
    try {
      if( i==0 )
        throw new RuntimeException()
      i
    } finally {
      println("关闭文件")
      //return 2 //如果加上了这句，就算抛出了异常，最后也会返回2，异常丢失了
    }
  }

  /**
    * 7.5匹配(match)表达式
    * 1、Scala的match表达式类似于其他语言中的switch语句。
    * 2、Scala的match和Java的switch的区别：
    *    1) 任何类型的常量或者其他什么东西都能当做Scala里做比较用的样本（case）。
    *    而不只是Java的case语句里的整数类型和枚举常量。
    *    2) Scala中的case后面没有break。Scala中break是隐藏的。不允许从上一个备选项
    *    落到下一个备选项里面去的情况发生。通常这可以使代码更简短，并且避免一些错误产生的根源。
    *    3) 与Java相比，最显著的差别或许是match表达式也能产生值
    */
  def fun18(food: String): Unit = {
    food match {
      case "salt" => println("papper")
      case "chips" => println("salsa")
      case "eggs" => println("bacon")
      case _ => println("huh?") // "_"相当于Java中的default
    }
  }

  //显然fun19与fun18相比，更加简洁，并且解开了两个原本应该分离的关注点：先选择食物，再打印它
  def fun19(food: String): Unit = {
    val friend = food match {
      case "salt" => "papper"
      case "chips" => "salsa"
      case "eggs" => "bacon"
      case _ => "huh?" // "_"相当于Java中的default
    }
    println(friend)
  }


  /**
    * 7.6 不再使用break和continue
    * 1、Scala中去掉了break和continue
    * 2、break和continue并不是编程中所必需的。用if和布尔变量可以替换continue和break
    */
  //查找以".scala"结尾，但不以"_"开头的字符串
  /*
   * //在java中...
   * int i = 0;
   * boolean foundInt = false;
   * while (i < args.length) {
   *   if(args[i].startsWith("_")) {
   *     i += 1;
   *     continue;
   *   }
   *   if(args[i].endsWith(".scala")) {
   *     foundIt = true;
   *     break;
   *   }
   *   i += 1;
   *}
   */

  //在scala中，使用指令式风格编程
  def fun20(): Int = {
    val args = Array("_xx.scala","hh.java","right.scala")
    var i = 0;
    var foundIt = false;
    while (i < args.length && !foundIt) {
      var arg = args(i)
      if(!arg.startsWith("_")){
        if(arg.endsWith(".scala")) {
          foundIt = true;
        }
      }
      i = i + 1
    }
    i-1
  }

  //在scala中，使用函数式风格编程
  def fun21(): Int = {
    val args = Array("_xx.scala","hh.java","right.scala")
    def searchFrom(i:Int): Int = {
      if(i >= args.length)
        -1
      else if(args(i).startsWith("_"))
        searchFrom(i+1)
      else if(args(i).endsWith(".scala"))
        i
      else
        searchFrom(i+1)
    }
    searchFrom(0)
  }

  /**
    * 7.7 变量范围
    * 1、Scala的范围规则和Java相比，只有一个不同，就是Scala允许在嵌套范围内定义同名变量。
    */
  def fun22(): Unit = {
    val a = 1; //这边必须加分号
    {
      val a = 2
      println(a)
    }
    println(a)
  }

  /**
    * 7.8 重构指令式风格的代码
    */
  //下面的程序打印出乘法表
  //以序列形式返回一行乘法表
  def makeRowSeq(row: Int) = {
    for(col <- 1 to 10) yield {
      val prod = (row * col).toString
      val padding = " " * (4 - prod.length)
      padding + prod
    }
  }

  //以字符串形式返还一行乘法表
  def makeRow(row: Int) = makeRowSeq(row).mkString

  //以字符串形式返回一行乘法表
  def multiTable() = {
    val tableSeq =
      for(row <- 1 to 10) yield makeRow(row)
    tableSeq.mkString("\n")
  }
  //程序测试的入口
  def main(args: Array[String]): Unit = {

  }
}
