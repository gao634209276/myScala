package com.chapter15

/**
  * 第15章 样本类和模式匹配
  * 样本类：在用来做模式匹配的类前加一个case
  */
object Chapter15 {

  /**
    * 15.1 简单例子
    *
    */
  abstract class Expr

  //样本类
  case class Var(name: String) extends Expr

  case class Number(num: Double) extends Expr

  case class UnOp(operator: String, arg: Expr) extends Expr

  case class BinOp(operator: String,
                   left: Expr, right: Expr) extends Expr

  /*
   * 样本类
   * 带有case修饰符的类被成为样本类。
   * 作用是：
   * 1、添加与类名一致的工厂方法。
   *    例如可以使用Var("x")来构造Var对象，替代较长的new Var("x")
   * 2、样本类参数列表中的参数隐式地获得了Val前缀
   * 3、编译器为该类添加了toString、hashCode和equals的“自然”实现
   * 4、样本类最大的好处是支持模式匹配
   *
   */
  def fun1() {
    //省略了new
    val v = Var("x")
    val op = BinOp("+", Number(1.0), Var("x"))
    println(v.name + op.left)
    println(v)
    println(op)
    println(v.eq(op.right)) //false
    println(v == op.right) //true
  }

  /*
   * 模式匹配
   * 一个模式匹配包含了一系列备选项，每个都开始于关键字case。每个备选项都包含了
   * 一个模式及一到多个表达式，它们将在模式匹配过程中被计算。箭头符号 =>隔开了
   * 模式和表达式。
   */
  def simplifyTop(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) => e //双重负号
    case BinOp("+", e, Number(0)) => e //加0
    case BinOp("*", e, Number(0)) => Number(0) //乘0
    case BinOp("*", e, Number(1)) => e //乘1
    case _ => expr //这句是必须的，否则没有模式匹配时会报MatchError异常
  }

  /*
   *match与switch的比较:
   * 有三点不同牢记在心：
   * 1) match是Scala表达式，它始终以值作为结果；
   * 2) Scala的表达式永远不会掉到下一个备选项；
   * 3) 如果没有匹配到模式会抛出MatchError异常，确保所有情况都考虑到了。
   */

  /**
    * 15.2 模式的种类
    * 1、通配模式
    * 通配模式(_)匹配任意对象,可以用来忽略对象中你不关心的部分
    * 2、常量模式
    * 常量模式仅可匹配自身。
    * 任何字面量都可以用作常量。例如：5、true、"Hello"
    * 任何的val或单例对象也可以用作常量。例如：单例对象Nil
    * 3、变量模式
    * 变量模式类似于通配模式，可以匹配任何对象。不同的地方在于Scala
    * 把变量绑定在匹配的对象上。因此之后可以使用这个变量操作对象。
    * 还有一点注意：用小写字母开始的名字被当作模式变量；所有其他的引用当作常量
    * 4、构造器模式
    * 5、序列模式
    * 匹配如List或Array这样的序列类型。可以指定模式内任意数量的元素
    * 6、元组模式
    * 还可以匹配元组。类似(a, b, c)这样的模式可以匹配任意的3元组
    * 7、类型模式
    * 8、变量绑定
    * 除了变量模式外还可以对任何其他模式添加变量。只要在之前加上 “变量名 @” 即可
    */
  //1、通配模式
  def describe1(expr: Any): Any = {
    expr match {
      case BinOp(op, left, right) =>
        println("this is BinOp")
        expr
      case _ => expr
    }
  }

  //2、常量模式
  def describe2(x: Any) = x match {
    case 5 => "five" //常量模式
    case true => "truth" //常量模式
    case "hello" => "hi!" //常量模式
    case Nil => "the empty List" //常量模式
    case _ => "something other" //通配模式
  }

  //3、变量模式
  def describe3(x: Any) = x match {
    case 5 => "five" //常量模式
    case other => "这个是变量模式哦：" + other //变量模式
    //case _ => "something other" //上面使用了变量模式,就永远到达不了这句了
  }

  //变量还是常量？Scala规定：在匹配模式中用小写字母开始的名字被当作模式变量；所有其他的引用当作常量
  def describe4(e: Any) = e match {
    //case PI => "Pi = " + PI  //编译不过
    case Math.PI => "Pi = " + Math.PI //编译通过： 常量模式
    case other => "not Pi"
  }

  //4、构造器模式
  def describe5(expr: Expr): Expr = expr match {
    case BinOp("*", e, Number(1)) => e //构造器模式
    case _ => expr //这句是必须的，否则没有模式匹配时会报MatchError异常
  }

  //序列模式
  def describe7(e: Any) = e match {
    case List(0, _, _) => println("found it")
    case List(0, 2, _*) => println("found it2") //匹配无数个
    case _ =>
  }

  //元组模式
  def describe8(e: Any) = e match {
    case (a, b, c) => println("matched:" + a + b + c)
    case _ =>
  }

  //类型模式
  def generalSize(x: Any) = x match {
    case s: String => s.length
    case m: Map[_, _] => m.size //表示key-value 是任意类型
    //和上面句一样，value值限定为String没有任何效果，因为有“类型擦除”
    //case m: Map[_, String] => m.size

    //擦除规则的唯一例外是数组，它被做了特殊处理，所以Array没有“类型擦除”效果
    case a: Array[Int] => println("a:" + a.length) //当类型是 Int时执行这句
    case a2: Array[_] => println("a2:" + a2.length) //当类型不是Int时，执行这句
    case _ =>
  }

  //变量绑定
  def describe9(expr: Any) = expr match {
    //将模式匹配中的UnOp("-",e)取名为myname，然后在表达式中就可以使用myname来替代UnOp("-",e)了
    case UnOp("-", myname@UnOp("-", e)) => myname
    case tuple@(a, b, c) => tuple._1
    case _ => expr //这句是必须的，否则没有模式匹配时会报MatchError异常
  }

  /**
    * 15.3 模式守卫
    *
    */
  def describe10(e: Any) = e match {
    //下面这句编译不过，因为匹配模式的参数中第2个和第3个参数都是x。
    //但是如果定义为x和y，那么不能做到仅仅匹配第2个参数和第3个参数相等的情况(不想等也被匹配了)
    //case BinOp("+", x, x) => BinOp("*", x, Number(2))
    //可以这样做：
    case BinOp("+", x, y) if x == y => BinOp("*", x, Number(2)) //匹配x和y相等
    case n: Int if n > 0 => n //匹配正整数
    case s: String if s(0) == 'a' => s //匹配以 'a'开头的字符串
    case _ =>
  }

  /**
    * 15.4 模式重叠
    */
  def describe11(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) => e //双重负号
    //下面三个语句模式重叠，从上往下进行匹配
    case BinOp("+", e, Number(0)) => e //加0
    case BinOp("*", e, Number(0)) => Number(0) //乘0
    case BinOp("*", e, Number(1)) => e //乘1
    case _ => expr //这句是必须的，否则没有模式匹配时会报MatchError异常
  }

  /**
    * 15.5 封闭类
    * 让样本类的超类被封闭(sealed).封闭类除了类定义所在的文件之外不能再添加新的子类。
    */

  sealed abstract class Expr2

  //样本类
  case class Var2(name: String) extends Expr2

  case class Number2(num: Double) extends Expr2

  case class UnOp2(operator: String, arg: Expr2) extends Expr2

  case class BinOp2(operator: String,
                    left: Expr2, right: Expr2) extends Expr2

  //因为Expr2用了sealed，所以如果匹配模式中没有完全定义Expr2的子类编译器就会提醒
  //match may not be exhaustive. It would fail on the following inputs: BinOp2(_, _, _), UnOp2(_, _)
  def describe12(e: Expr2): String = e match {
    case Number2(_) => "a number"
    case Var2(_) => "a variable"
  }

  //如果让这些警告去掉可以添加@unchecked注解
  def describe13(e: Expr2): String = (e: @unchecked) match {
    case Number2(_) => "a number"
    case Var2(_) => "a variable"
  }

  /**
    * 15.6 Option类型
    * Scala为可选值定义了一个名为Option的标准类型。这种值可以有两种形式。
    * 可以是Some(x)的形式，其中x是实际值，或者也可以是None对象，代表缺失值。
    * Scala集合类的某些标准操作会产生可选值。例如 Scala的Map的get方法会在发现了
    * 指定键的情况下产生Some(value),在没有找到指定键的时候产生None。
    */


  //分离可选值，通常使用模式匹配
  def show(x: Option[String]) = x match {
    case Some(s) => s // Some是一个样本类：final case class Some[+A](x: A) extends Option[A] {
    case None => "?" //None是一个样本类：case object None extends Option[Nothing]
  }

  def fun2() {
    val captials = Map("France" -> "Paris", "Japan" -> "Tokyo")
    println(captials.get("France")) // 打印出： Some(Paris)
    println(show(captials.get("France"))) // 打印出：Paris
    println(captials.get("China")) // 打印出： None
    println(show(captials.get("China"))) // 打印出：?
  }

  /**
    * 15.7、模式无处不在
    */
  //1、模式在变量定义中
  def fun3() {
    val myTuple = (123, "abc")
    //使用模式拆分元组，并把其中的每个值分配给变量
    val (number, string) = myTuple
    println(number + " " + string)
  }

  //2、用作偏函数的样本序列
  //略

  //3、for表达式里的模式
  def fun4() {
    val captials = Map("France" -> "Paris", "Japan" -> "Tokyo")
    //for 表达式从captials映射中获得所有的键/值对。
    //每一对都匹配于模式(country, city),并定义了两个变量country和city
    for ((country, city) <- captials) {
      println(country + " " + city)
    }
  }

  def main(args: Array[String]): Unit = {
    fun3()
  }

}
