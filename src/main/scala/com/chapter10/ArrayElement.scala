
package com.chapter10
/**
  * 10.4 扩展类
  * 1、使用extends进行扩展
  * 2、extends使得子类继承超类的所有非私有成员
  * 3、如果省略extends，Scala中的类默认继承自scala.AnyRef,
  *    相当于Java中的java.lang.Object
  *
  * 10.5 重写方法和字段
  * 1、Scala里的字段和方法属于相同命名空间。这让字段可以重新无参方法。
  * 2、Scala里禁止同一个类中用同样的名称定义字段和方法。尽管Java允许这么做。
  * 3、Java有四个命名空间：字段、方法、类型和包；
  *    Scala两个命名空间：值(字段、方法、包、单例对象)和 类型(类和特质名)
  *
  * 10.10 定义final变量
  *    如果想确保一个成员不被子类重写，可以通过添加final修饰符来实现；
  *    如果想该类没有子类，只要在class前加上final即可。
  */
class ArrayElement(cons: Array[String]) extends Element {
  def contents:Array[String] = cons

  //用字段重写无参方法
  //val contents:Array[String] = cons;

  //添加final后子类不能重写该方法
  final def demo() {
    println("ArrayElement")
  }

}
