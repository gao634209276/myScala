package chapter2

import java.io.{IOException, PrintWriter}

/**
  * trait构造顺序
  * trait与类的比较
  * 提前定义与懒加载
  * trait扩展类
  * self type
  */

/**
  * 1 trait构造顺序
  * 在前一讲当中我们提到，对于不存在具体实现及字段的trait，它最终生成的字节码文件反编译后是等同于Java中的接口，
  * 而对于存在具体实现及字段的trait，其字节码文件反编译后得到的java中的抽象类，它有着scala语言自己的实现方式。
  * 因此，对于trait它也有自己的构造器，trait的构造器由字段的初始化和其它trait体中的语句构成，
  * 下面是其代码演示：
  */
trait TraitLogger {
	println("Logger")

	def log(msg: String): Unit
}

trait FileLogger extends TraitLogger {
	println("FilgeLogger")
	val fileOutput = new PrintWriter("file.log")
	fileOutput.println("#")

	def log(msg: String): Unit = {
		fileOutput.print(msg)
		fileOutput.flush()
	}
}

object TraitLoggerDemo {
	def main(args: Array[String]): Unit = {
		//匿名类
		new FileLogger {
		}.log("trat demo")
	}
}

//打印输出内容为：
//Logger
//FilgeLogger
//创建文件file.log，内容为
//#
//trat demo

/**
  * 通过上述不难发现，在创建匿名类对象时，先调用的是Logger类的构造器，然后调用的是FileLogger的构造器。
  * 实际上构造器是按以下顺序执行的：
  * 1. 如果有超类，则先调用超类的构造器
  * 2. 如果有父trait，它会按照继承层次先调用父trait的构造器
  * 如果有多个父trait，则按顺序从左到右执行
  * 4. 所有父类构造器和父trait被构造完之后，才会构造本类
  */
class Person24

class Student6 extends Person24 with FileLogger with Cloneable

//上述构造器的执行顺序为:
//1 首先调用父类Person的构造器
//2 调用父trait Logger的构造器
//3 再调用trait FileLogger构造器，再然后调用Cloneable的构造器
//4 最后才调用Student的构造器

/**
  * 2 trait与类的比较
  *
  * 通过前一小节，可以看到，trait有自己的构造器，它是无参构造器，不能定义trait带参数的构造器，
  * 即：不能定义trait带参数的构造器
  * trait FileLogger(msg:String)
  * 除此之外 ，trait与普通的scala类并没有其它区别，
  * 在前一讲中我们提到，trait中可以有具体的、抽象的字段，也可以有具体的、抽象的方法，
  * 即使trait中没有抽象的方法也是合理的，如:
  */
//FileLogger里面没有抽象的方法
trait FileLogger2 extends TraitLogger {
	println("FilgeLogger")
	val fileOutput = new PrintWriter("file.log")
	fileOutput.println("#")

	def log(msg: String): Unit = {
		fileOutput.print(msg)
		fileOutput.flush()
	}
}

/**
  * 3. 提前定义与懒加载
  *
  * 前面的FileLogger中的文件名被写死为”file.log”，程序不具有通用性，
  * 这边对前面的FileLogger进行改造，把文件名写成参数形式，代码如下：
  */

import java.io.PrintWriter

trait Logger {
	def log(msg: String): Unit
}

trait FileLogger3 extends Logger {
	//增加了抽象成员变量
	val fileName: String
	//将抽象成员变量作为PrintWriter参数
	val fileOutput = new PrintWriter(fileName: String)
	fileOutput.println("#")

	def log(msg: String): Unit = {
		fileOutput.print(msg)
		fileOutput.flush()
	}
}

/**
  * 这样的设计会存在一个问题，
  * 虽然子类可以对fileName抽象成员变量进行重写，编译也能通过，
  * 但实际执行时会出空指针异常，完全代码如下：
  */
class Person25

class Student7 extends Person25 with FileLogger {
	//Student类对FileLogger中的抽象字段进行重写
	val fileName = "file.log"
}

object TraitDemo {
	def main(args: Array[String]): Unit = {
		new Student7().log("trait demo")
	}
}

/*
上述代码在编译时不会有问题，但实际执行时会抛异常，异常如下：
Exception in thread "main" java.lang.NullPointerException
    at java.io.FileOutputStream.<init>(Unknown Source)
    at java.io.FileOutputStream.<init>(Unknown Source)
    at java.io.PrintWriter.<init>(Unknown Source)
    at cn.scala.xtwy.FileLogger$class.$init$(TraitDemo.scala:12)
    at cn.scala.xtwy.Student.<init>(TraitDemo.scala:22)
    at cn.scala.xtwy.TraitDemo$.main(TraitDemo.scala:28)
    at cn.scala.xtwy.TraitDemo.main(TraitDemo.scala)
    具体原因就是构造器的执行顺序问题，
class Student extends Person with FileLogger{
  //Student类对FileLogger中的抽象字段进行重写
  val fileName="file.log"
}
//在对Student类进行new操作的时候，它首先会
//调用Person构造器，这没有问题，然后再调用
//Logger构造器，这也没问题，但它最后调用FileLogger
//构造器的时候，它会执行下面两条语句
//增加了抽象成员变量
  val fileName:String
  //将抽象成员变量作为PrintWriter参数
  val fileOutput=new PrintWriter(fileName:String)
此时fileName没有被赋值，被初始化为null，在执行new PrintWriter(fileName:String)操作的时候便抛出空指针异常
 */
/**
  * 有几种办法可以解决前面的问题：
  * 1 提前定义
  * 提前定义是指在常规构造之前将变量初始化，完整代码如下：
  */
object TraitDemo2 {
	def main(args: Array[String]): Unit = {
		val s = new {
			//提前定义
			override val fileName = "file.log"
		} with Student7
		s.log("predifined variable ")
	}
}

/**
  * 显然，这种方式编写的代码很不优雅，也比较难理解。
  * 此时可以通过在第一讲中提到的lazy即懒加载的方式
  *
  * 2 lazy懒加载的方式
  */

trait FileLogger4 extends Logger {

	val fileName: String
	//将方法定义为lazy方式
	lazy val fileOutput = new PrintWriter(fileName: String)
	//下面这条语句不能出现，否则同样会报错
	//因此，它是FileLogger构造器里面的方法
	//在构造FileLogger的时候便会执行
	//fileOutput.println("#")

	def log(msg: String): Unit = {
		fileOutput.print(msg)
		fileOutput.flush()
	}
}

class Person26

class Student8 extends Person26 with FileLogger4 {
	val fileName = "file.log"
}

object TraitDemo3 {
	def main(args: Array[String]): Unit = {
		val s = new Student8
		s.log("predifined variable ")
	}
}

/**
  * lazy方式定义fileOutput只有当真正被使用时才被初始化，
  * 例子中，当调用 s.log(“predifined variable “)时，fileOutput才被初始化，
  * 此时fileName已经被赋值了。
  */

/**
  * 4 trait扩展类
  *
  * 在本节的第2小节部分，我们给出了trait与类之间的区别，我们现在明白，
  * trait除了不具有带参数的构造函数之外，与普通类没有任何区别，
  * 这意味着trait也可以扩展其它类
  */
//trait扩展类Exception
trait ExceptionLogger extends Exception with Logger {
	def log(msg: String): Unit = {
		println(getMessage())
	}
}

/**
  * 如果此时定义了一个类混入了ExceptionLogger ，则Exception自动地成为这个类的超类，代码如下：
  */
//类UnprintedException扩展自ExceptionLogger
//注意用的是extends
//此时ExceptionLogger父类Exception自动成为
//UnprintedException的父类
class UnprintedException extends ExceptionLogger {
	override def log(msg: String): Unit = {
		println("")
	}
}

/**
  * 当UnprintedException扩展的类或混入的特质具有相同的父类时，scala会自动地消除冲突，例如：
  */
//IOException具有父类Exception
//ExceptionLogger也具有父类Exception
//scala会使UnprintedException只有一个父类Exception
class UnprintedException2 extends IOException with ExceptionLogger {
	override def log(msg: String): Unit = {
		println("")
	}
}

/**
  * 5 self type
  * 下面的代码演示了什么是self type即自身类型
  */
class A {
	//下面 self =>  定义了this的别名，它是self type的一种特殊形式
	//这里的self并不是关键字，可以是任何名称
	self =>
	val x = 2

	//可以用self.x作为this.x使用
	def foo = self.x + this.x
}

/**
  * 下面给出了内部类中使用场景
  */
class OuterClass2 {
	outer =>
	//定义了一个外部类别名
	val v1 = "here"

	class InnerClass2 {
		// 用outer表示外部类，相当于OuterClass.this
		println(outer.v1)
	}

}

/**
  * 而下面的代码则定义了自身类型self type，它不是前面别名的用途，
  */
trait X {
}

class B {
	//self:X => 要求B在实例化时或定义B的子类时
	//必须混入指定的X类型，这个X类型也可以指定为当前类型
	self: X =>
}

/**
  * 自身类型的存在相当于让当前类变得“抽象”了，它假设当前对象(this)也符合指定的类型，
  * 因为自身类型 this:X =>的存在，当前类构造实例时需要同时满足X类型，
  * 下面给出自身类型的使用代码：
  */
//类C扩展B的时候必须混入trait X
//否则的话会报错
class C extends B with X {
	def foo() = println("self type demo")
}

object SelfTypeDemo extends App {
	println(new C().foo)
}