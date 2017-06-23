package com.chapter5

/**
  * 第5章：基本类型和操作
  *
  * 知识要点：
  * 5.1 基本类型
  * 1、一些基本类型
  * 整数类型：Byte、Short、Int、Long 和 Char.位数分别是8、16、32、64、16(无符号Unicode)
  * 数类型：Float、Double和整数类型
  * String
  * Boolean
  * 2、除了String属于包java.lang下面，其余基本类型都在包scala下
  * 3、推荐使用首字母大写的基本类型。如Int，不推荐使用int
  *
  * 5.2 字面量
  * 1、整数字面量：Byte、Short、Int、Long
  * 2、浮点数字面量：Float、Double
  * 3、字符字面量：Char
  * 4、字符串字面量：String
  * 5、符号字面量：Symbol
  * 6、布尔型字面量：Boolean
  *
  * 5.3 操作符和方法
  * 1、Scala里任何方法都是操作符。
  * 到底是方法还是操作符取决于你怎么使用它：
  * 如s.indexOf("e")中indexOf就是方法；s indexOf "e" 中indexOf就是操作符。
  *
  * 5.4 数学运算、5.5关系和逻辑操作、5.6位操作符与java基本相同，跳过阅读
  *
  * 5.5 对象相等性
  * 1、如果想比较两个对象是否相等，可以使用 == 或它的反义 !=
  * 2、Scala中 ==在比较不同的对象时也可能产生true，只要比较的两者内容相同，并且equals方法是基于内容编写的
  * 3、Scala的==和java的有什么区别？
  *    Java中==可以比较原始类型也可以比较引用类型。对于原始类型，java的==比较的是值是否相等，
  *    与Scala一致。而对于引用类型Java的==比较的是引用的相等性。即两个变量是否指向JVM中的同一对象。
  *    Scala也提供了这种机制，用的是eq
  *
  * 5.6 操作符的优先级和关联性
  * 操作符的关联性决定了操作符的分组方式。Scala里操作符的关联性取决于它的最后一个字符。
  * 任何':'字符结尾的方法由它的右操作数调用，并把左操作数传入。与其他字符结尾的方法与之相反。
  * 因此 a ::: b 相当于 b.:::(a);a * b 相当于 a.*(b)
  */

object Chapter5 {
  def fun1():Unit = {
    var a = "Hello World"
    //下面两句相等。a indexOf "e"  等价于 a.indexOf("e")
    println(a indexOf "e")
    println(a.indexOf("e"))
  }

  def main(args: Array[String]): Unit = {
    fun1()
  }
}
