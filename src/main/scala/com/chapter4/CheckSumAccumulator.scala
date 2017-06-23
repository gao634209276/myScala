package com.chapter4

import scala.collection.mutable.WeakHashMap

/**
  * 4.1节 类、字段 和方法; 4.2节 分号推断； 4.3节 Singleton对象
  *
  * 类的相关知识点
  * 1、类的字段和方法统称为成员。
  * 2、类用class，单例对象用object，方法用def
  * 3、字段的另一种说法是实例变量
  * 4、在java中写public的地方，在Scala里什么都不用写。public是Scala的默认访问级别。
  * 5、Scala里方法参数的一个重要特征是它们都是val，不是var，
  * 如果你想在方法里面给参数赋值，结果是编译失败。
  * 6、Scala中方法中的return有时候可以去掉。
  * 如果没有发现任何显示的返回语句。Scala方法将返回方法中最后一次计算得到的值。
  * 方法的推荐风格是尽量避免使用返回语句，尤其是多条返回语句。
  * Scala鼓励你分层简化方法，将一个方法简化成多个更小的方法。
  * 内容决定形式。如果确实需要，也可以编写多条返回语句。
  * 7、方法中如果只有单条语句，可以去掉花括号。
  * 8、在scala里，分号是可选的。可加可不加。但是一行中有多个语句，分号必须要加。
  * 9、串接类似于+这样的中缀操作符的时候，Scala通常的风格是把操作符放在行尾而不是行头
  */
class CheckSumAccumulator {
  private var sum = 0

  //默认public级别
  //该方法执行的目的就是为了它的副作用
  def add(b: Byte): Unit = {
    //b = 1 //编译不过，因为b是val
    sum += b
  }

  //上面的方法可以改写成：
  //但是注意：如果去掉等号，那么方法的结果类型必定是Unit
  def add2(b: Byte) {
    sum += b
  }

  def getSum(): Int = {
    sum;
  }

  //上面的方法可以改写成：
  def getSum2(): Int = sum

  //也可改写成：
  def getSum3() = sum

  //但是不能写成下面的形式。因为 “ = ” 去掉，必定返回Unit类型
  def getSum4() {
    sum
  }
}

/**
  * 1、Scala没有静态成员，而代之以定义单例对象。用object关键字。下面是一个单例对象。
  * 2、当单例对象与某个类同名时，单例对象被称为这个类的伴生对象。这个类被称为单例对象的伴生类。
  * 3、类和它的伴生对象必须定义在同一个源文件中。
  * 4、类和它的伴生对象可以互相访问其私有成员。
  * 5、单例对象扩展了父类，并可以混入特质
  * 6、单例对象不能带参数，而类可以。因为单例对象不使用new实例化，所以没机会传给它实力。
  * 7、单例对象在第一次被访问的时候才会被初始化。
  * 8、不与伴生类共享名称的单例对象称为独立对象。可以用于工具类或Scala应用入口点等。
  */
object CheckSumAccumulator {
  //使用scala.collection.mutable.WeakHashMap的好处是：当内存稀缺时，缓存里的条目会被垃圾回收，释放内存。
  private val cache = WeakHashMap[String, Int]();

  def calculate(s: String): Int = {
    WeakHashMap
    if (cache.contains(s)) {
      cache(s)
    } else {
      val csa = new CheckSumAccumulator
      for (c <- s) {
        csa.add(c.toByte)
      }
      //直接访问伴生类的私有成员，不需要调用getSum()方法
      cache += (s -> csa.sum)
      csa.sum
    }
  }

  def main(args: Array[String]): Unit = {
    println(calculate("Hello world"))
  }
}


