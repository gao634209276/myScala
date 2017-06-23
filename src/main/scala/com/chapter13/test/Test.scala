package com.chapter13.test

//易于访问Fruit
//易于访问Fruits的所有成员
//与上句意思一样，只是写法不同


//易于访问chapter13的所有成员
//import com.chapter13._

//只引用对象Fruits的Apple和Orange成员
//import com.chapter13.Fruits.{Apple, Orange}

//只引用对象Fruits的Apple和Orange成员。但是对Apple对象重命名为MyApple
//Apple对象可以用Fruits.Apple 或者 MyApple进行访问
//import com.chapter13.Fruits.{Apple => MyApple, Orange}

//引用对象Fruits的所有成员，只是将Apple对象重命名为 MyApple
//import com.chapter13.Fruits.{Apple => MyApple, _}

//引用对象Fruits的所有成员，Pear除外
//import com.chapter13.Fruits.{Pear => _, _}


//易于访问Fruit

//易于访问Fruits的所有成员
//import com.chapter13.Fruits._

//与上句意思一样，只是写法不同
//import com.chapter13.Fruits.{_}
/**
  * 13.2 引用
  * 1、用import进行引用
  * 2、Scala的按需引用使用"_"而不是"*"
  * 3、Scala的灵活引用，有以下三点:
  *    1) 引用可以出现在任何地方。
  *    2) 可以指的是(单例或正统的)对象及包
  *    3) 可以重命名或隐藏一些被引用的成员
  *
  *13.3 隐式引用
  * import java.lang._
  * import scala._
  * import Predef._
  *
  * 13.4 访问修饰符
  * Scala大体上遵守Java对访问修饰符的对待方式，但也有些重要的差异：
  * 1、Scala只有私有成员(private)、保护成员(protected)和公开成员(没有标识符)
  * 2、在Scala中不允许外部类访问内部类的私有成员，而Java中可以
  * 3、在Scala中保护成员(protected修饰)子类中可以被访问，同一个包下不能被访问。
  */

class Outer {
  class Inner {
    private def f() {println("f")}
    def g() {println("g")}
    class InnerMost() {
      f() // ok
    }
  }
  (new Inner).g() // ok
  //(new Inner).f() //error 编译不过:Scala中不允许外部类访问内部类的私有成员
}

object TestFruit {
  /*def showFruit(fruit: Fruit) {
    println(name + "s are " + color)
  }*/

}
  class Super {
    protected def f() {println("f")}
  }
  class Sub extends Super {
    f() //ok Scala中保护成员(protected修饰)子类中可以被访问
  }

class Other {
    //(new super).f() //error 编译不过： Scala中保护成员(protected修饰)同一个包下不能被访问
  }


