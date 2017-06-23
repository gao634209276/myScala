package com.chapter12

import com.chapter6.Rational

import scala.collection.mutable.ArrayBuffer

/**
  * 12.1特质是如何工作的
  * 1、特质的定义除了使用关键字trait外，与类定义无异。
  * 2、特质通过extends或with关键字混入到类中
  * 3、使用extends关键字混入特质后，该类隐式地继承了特质的超类。
  * 4、特质就像带有具体方法的Java接口。但它还有更多的功能，例如可以声明字段和维持状态值。
  * 5、特质和类的两个不同点：
  *    1)特质不能有任何“类”参数，即传递给类的主构造器的参数。
  *    2)super在类中是静态绑定的，在特质中是动态绑定的。
  */
object Chapter12 {
  trait Philosophical {
    def philosophize() {
      println("I consume memory,therefore I am !")
    }
  }

  //使用extends混入特质，这样Frog的超类其实就是Philosophical的超类
  class Frog extends Philosophical {
    override def toString = "green"
    override def philosophize() {
      println("It ain't easy being " + toString)
    }
  }

  //定义一个Animal类
  class Animal

  //使用with混入特质，现在dog继承自Animal而不是Philosophical的超类
  class Frog1 extends Animal with Philosophical {

  }

  trait HasLegs

  //混入多个特质
  class Frog2 extends Animal with Philosophical with HasLegs {
    override def toString = "green"
  }

  def fun1() {
    val frog = new Frog()
    frog.philosophize()
  }

  def fun2() {
    //变量phil是特质Philosophical，所以可以被初始化为任何混入了Philosophical特质的类的对象。
    val phil: Philosophical = new Frog()
    println(phil)
    phil.philosophize()
  }
  /**
    * 12.2 瘦接口对阵胖接口
    * 在Scala中添加具体方法是一次性的劳动。只要在特质中实现一次方法，而不再
    * 需要在每个混入特质的方法中重新实现它。
    * 要使用特质丰富接口，只要简单地定义一个具有少量抽象方法的特质--特质接口的
    * 瘦部分--和潜在的大量的具体方法，所有的都实现在抽象方法之上。然后就可以把
    * 丰富了的特质混入到类中，实现接口的瘦部分。并且最终获得具有全部胖接口内容的类
    */

  /**
    * 12.3样例：长方形对象
    */

  /*
  class Point(a: Int, b: Int) { //Point的详细写法
    val x = a
    val y = b
  }
  */
  class Point(val x: Int, val y: Int)

  trait Rectangular {
    //声明了topLeft和bottomRight方法(两个未实现的方法)
    def topLeft: Point
    def bottomRight: Point

    //定义了left、right和width方法
    def left = topLeft.x
    def right = bottomRight.x
    def width = right - left
  }

  //Component类混入特质Rectangular，并获得它提供的所有方法
  //这里去掉abstract会报错，因为未实现Rectangular中的topLeft和bottomLeft方法
  abstract class Component extends Rectangular {
    //其他方法...
  }

  class Rectange(val topLeft: Point, val bottomRight: Point) extends Rectangular {
    //其他方法...
  }

  def fun3() {
    val rect = new Rectange(new Point(1,2), new Point(3,4))
    println(rect)
  }

  /**
    * 12.4 Ordered特质
    * 1、Ordered特质定义了四个比较操作符：> >= < <=
    *    在比较两个排序对象时，混入Ordered特质，实现compare方法就能用它们来比较对象了。
    * 2、定义compare方法来比较方法的调用者this和当作方法参数传入的对象。如果这两个对象相等，
    *    应该返回0；this小于参数，返回负数；this大于参数，返回正数
    * 3、Ordered并没有为你定义equals方法，因为它无法做到。所以还要自己定义这个方法。
    */
  class Rati(m: Int, d: Int) extends Rational(m, d) with Ordered[Rati] {
    def compare(that: Rati) =
      this.mole * that.denom - that.denom * this.mole
  }

  def fun4() {
    val x = new Rati(1, 2)
    val y = new Rati(3, 4)
    val z = new Rati(2, 4)
    println(x < y) //false
    println(x <= y) //true
  }

  /**
    * 12.5 特质用来做可堆叠的改变
    * 1、特质可以为类提供可堆叠的改变。特质可以通过堆叠来改变类的方法。
    */
  //定义一个抽象的队列
  abstract class IntQueue {
    def get() :Int
    def put(x: Int)
  }

  //使用ArrayBuffer实现BasicIntQueue
  class BasicIntQueue extends IntQueue {
    //scala.collection.mutable.ArrayBuffer
    private val buf = new ArrayBuffer[Int]
    def get() = buf.remove(0)
    def put(x:Int) {
      buf += x
    }
    override def toString = buf.toString()
  }

  /*
   *注意特质Doubling有两件有趣的事情：
   * 1)定义了超类InitQueue。这个定义意味着该特质只能混入扩展了InitQueue的类
   * 2)在声明为抽象的方法中使用了super调用。因为特质是动态绑定的，所以super调用
   * 会在混入了另一个类或特质后，有了具体的方法再被调用。普通的类不能这样使用super
   */
  //Doubling的作用是当整数被放入队列的时候对其加倍
  trait Doubling extends IntQueue {
    //abstract override 的组合只有在特质成员的定义中才被认可。在实现可堆叠改动的特质中经常用到
    //它告诉编译器你的目的。意味着特质必须被混入某个具有期待方法的具体类中
    abstract override def put(x:Int) {
      super.put(2 * x)
    }
  }

  trait Incrementing extends IntQueue {
    abstract override def put(x:Int) {
      super.put(x + 1)
    }
  }

  trait Filtering extends IntQueue {
    abstract override def put(x:Int) {
      if(x >= 0)
        super.put(x)
    }
  }

  def fun5() {
    println(1)
    //混入的次序非常重要，粗略地说，越靠近右边的特质越先起作用
    val queue = new BasicIntQueue with Incrementing with Filtering
    queue.put(0)
    println(queue)
    queue.put(2)
    println(queue)
    queue.put(-1)
    println(queue)
    queue.get()
    println(queue)
    queue.get()
    println(queue)
  }

  /**
    * 12.6 为什么不是多重继承？
    * 略
    */

  /**
    * 12.7 特质，用还是不用？
    * 使用特质还是抽象类，没有固定规律，以下列出几条可供考虑的规则：
    * 1、如果行为不会被重用，那么就把它做成具体类。具体类没有可重用的行为。
    * 2、如果要在多个不相关的类中重用，就做成特质。只有特质可以混入到不同的类层级中。
    * 3、如果你希望从Java代码中继承它，就使用抽象类。
    * 4、如果你计划以编译后的方式发布它，就使用抽象类，并且希望外部组织能够写一些继承自它
    *   的类，你应该更倾向于使用抽象类。
    * 5、如果效率非常重要，则倾向于使用类。
    */
  def main(args: Array[String]): Unit = {
    fun5
  }

}
