package com.chapter10

/** 10.1 略
  * 10.2 抽象类
  * 1、抽象类由abstract修饰符修饰
  * 2、Scala的抽象方法的声明中不需要也不允许有abstract修饰符
  *
  * 10.3 定义无参方法
  * 1、def height(): Int 称为空括号方法； def height: Int 称为无参方法。
  *    推荐的用法是：无论何时，只要方法中没有参数并且方法仅能通过读取所包含对象的
  *    属性去访问可变状态(特指方法不能改变可变状态)，就使用无参方法。
  * 2、下面用无参方法和属性定义height和width从客户的观点看是完全相同的。
  *    唯一的差别是访问字段比调用方法略快，因为字段值在初始化时被预计算。
  *    而方法调用在每次调用时都要计算。特别地，如果类的字段编程了访问方法，
  *    只要访问方法是纯的，即不产生副作用也不依赖可变状态，那么客户代码就
  *    不应该关心是用哪种方式实现的。
  * 3、Scala在遇到混合了无参数和空括号方法的情况时很自由。特别地，你可以用
  *    空括号方法重写无参数方法，反之亦然。
  * 4、Scala在函数调用时可以省略所有的空括号。但是以下情况建议加上空括号：
  *    例如，如果方法执行了I/O，或写入可重新赋值的变量（var），或读取不是调用者
  *    字段的var，总之只要使用了可变对象，都应添加空括号。
  * 5、Scala鼓励将不带参数，并且没有副作用的方法定义为无参数方法的风格。即省略空括号。
  *    但是永远不要定义没有空括号的带副作用的方法。
  */
//abstract修饰符说明类可能有未实现的方法，所以不能被实例化
abstract class Element {
  //contents被声明为没有实现的方法。换句话说，这个方法是Element类的抽象成员。
  //Scala的抽象方法的声明中不需要也不允许有abstract修饰符
  def contents:Array[String]

  //注意： def height(): Int 称为空括号方法； def height: Int 称为无参方法。
  def height: Int = contents.length
  //height 也可以由属性来实现，只要将def改成val即可
  //val height: Int = contents.length
  //width 方法返回第一行的长度
  def width: Int = if(height == 0) 0 else contents(0).length
  //height 也可以由属性来实现，只要将def改成val即可
  //val width: Int = if(height == 0) 0 else contents(0).length


  // ++ 操作符连接两个数组
  def above(that: Element): Element = {
    new ArrayElement(this.contents ++ that.contents)
  }

  def beside(that: Element): Element = {
    new ArrayElement(
      for(
      //zip操作符转换成一个二元对数组(称为Tuple2)
      //Array(1, 2, 3) zip Array("a", "b") 将生成Array((1,"a")(2,"b"))
      //如果两个数组中的一个比另一个长，zip将舍弃多余元素，上面舍弃了3
        (line1, line2) <- this.contents zip that.contents
      )yield line1 + line2
    )
  }

  //注意：toString没有带空参数列表。这遵循了统一访问原则的建议，因为toString是一个不带任何参数的纯方法
  override def toString = contents mkString "\n"

}