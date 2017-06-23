package chapter2

/**
  * 访问控制
  *
  * 在java语言中，主要通过public、private、protected及默认控制来实现包中类成员的访问控制，
  * 当定义一个类时，如果类成员不加任何访问控制符时，表示该类成员在定义该类的包中可见。
  * 在scala中没有public关键字，仅有private 和 protected访问控制符，
  * 当一个类成员不加private和protected时，它的访问权限就是public。下面逐个进行讲解：
  */
/**
  * 1 private 成员
  */
//private成员同java是一样的，所有带该关键字修饰的成员仅能在定义它的类或对象中使用，在外部是不可见的
class Student(var name: String, var age: Int) {
	private var sex: Int = 0

	//内部类
	class Course(val cName: String, val gpa: Float) {
		//可以直接访问其外部类的私有成员
		def getStudentSex(student: Student) = student.sex
	}

}

//班级类
class Class {
	//下面这条语句统计通不过，因为sex是私有的
	// def getStudentSex(student:Student)=student.sex
}

object Student {
	private var studentNo: Int = 0

	def uniqueStudentNo() = {
		studentNo += 1
		studentNo
	}

	def apply(name: String, age: Int) = new Student(name, age)

	def main(args: Array[String]): Unit = {
		println(Student.uniqueStudentNo())
		val s = new Student("john", 29)
		//直接访问伴生类Student中的私有成员
		println(s.sex)

		val s1 = Student("john", 29)
		println(s1.name)
		println(s1.age)

		//使用内部类
		val c1 = new s1.Course("Scala", 3.0f)
	}
}

/**
  * 2 protected 成员
  * 在java语言中，protected成员不但可以被该类及其子类访问，也可以被同一个包中的其它类使用，
  * 但在scala中，protected成员只能被该类及其子类访问
  */
class SuperClass {
	protected def f() = println(".....")
}

class SubClass extends SuperClass {
	f()
}

class OtherClass {
	//下面这个语句会报错
	//f()
}

/**
  * 3 无修饰符成员
  * 无修饰符的成员同java 的public，可以在任何位置进行访问
  *
  * 4 范围保护
  * 在scala中提供了更为灵活的访问控制方法，private、protected除了可以直接修饰成员外，
  * 还可以以private[X]、protected[X]的方式进行更为灵活的访问控制，
  * 这种访问控制的意思是可以将private、protected限定到X，X可以是包、类，还可以是单例对象
  */

package cn {

	class UtilsTest {
		//编译通不过，因为Utils利用private[scala]修饰，只能在scala及其子包中使用
		//Utils.toString()
	}
	package scala {

		//private[scala]限定Utils只能在scala及子包中使用
		private[scala] object Utils {
			def toString(x: String) {
				println(x)
			}

			import cn.scala.xtwy._

			def getTeacher(): Teacher = new Teacher("john")
		}
		package xtwy {

			class Teacher(var name: String) {
				def printName() = {
					Utils.toString(name)
				}
			}

		}

	}

}

object AccessControlDemo {

	import cn.scala.xtwy._

	def main(args: Array[String]): Unit = {
		//编译通不过，同UtilsTest
		//Utils.toString(new Teacher("john").name)
		new Teacher("john").printName()
	}

}

/**
  * private[this]，限定只有该类的对象才能访问，称这种成员为对象私有成员
  */
class Teacher2(var name: String) {
	private[this] def printName(tName: String = ""): Unit = {
		println(tName)
	}

	//调用private[this] printName方法
	def print(n: String) = this.printName(n)
}

object Teacher2 {
	//private[this]限定的成员，即使伴生对象Teacher也不能使用
	//def printName=new Teacher("john").printName()
}

object PrivateThisDemo {
	def main(args: Array[String]): Unit = {
		//编译不能通过
		//new Teacher("john").printName()
	}
}

/**
  * private，定义的类及伴生对象可以访问
  */
class Teacher3(var name: String) {
	private def printName(tName: String = ""): Unit = {
		println(tName)
	}

	//可以访问
	def print(n: String) = this.printName(n)
}

object Teacher3 {
	//伴生对象可以访问
	def printName = new Teacher3("john").printName()
}

object PrivateDemo {
	def main(args: Array[String]): Unit = {
		//不能访问
		//new Teacher3("john").printName()
	}
}

/**
  * 下面给出的是访问规则表
  *
  * 无任何修饰符 	任何地方都可以使用
  * private[scala] 	在定义的类中可以访问，在scala包及子包中可以访问
  * private[this] 	只能在定义的类中访问，即使伴生对象也不能访问团
  * private 	在定义的的类及伴生对象中可以访问，其它地方不能访问
  * protected[scala] 	在定义的类及子类中可以访问，在scala包及子包中可以访问，
  * protected[this] 	只能在定义的类及子类中访问，即使伴生对象也不能访问
  * protected 	在定义的类及子类中访问，伴生对象可以访问，其它地方不能访问
  */
/**
  * 包对象
  * 包对象主要用于将常量、工具函数，使用时直接通过包名引用
  */
//下面的代码给出了包对象的定义
//利用package关键字定义单例对象
package object Math {
	val PI = 3.141529
	val THETA = 2.0
	val SIGMA = 1.9
}

class Coputation {
	def computeArea(r: Double) = Math.PI * r * r
}

/*
上述代码编译后会生成下列文件：package$.class,package.class
对应字节码文件如下：
javap -private package.class
Compiled from "Math.scala"
public final class cn.scala.xtwy.Math.package {
  public static double SIGMA();
  public static double THETA();
  public static double PI();
}

javap -private package$.class
Compiled from "Math.scala"
public final class cn.scala.xtwy.Math.package$ {
  public static final cn.scala.xtwy.Math.package$ MODULE$;
  private final double PI;
  private final double THETA;
  private final double SIGMA;
  public static {};
  public double PI();
  public double THETA();
  public double SIGMA();
  private cn.scala.xtwy.Math.package$();
}
 */
//不难看出，它为我们的包对象Math创建了一个文件夹，然后创建了两个类，通过单例的方式实现方法调用。
/**
  * import高级特性
  *
  * 1 隐式引入
  * 在集合那一讲，我们提到，如果不引入任何包，scala会默认引入java.lang._
  * scala._
  * Predef._
  * 包中或对象中所有的类和方法，称这种引入会隐式引入
  *
  * 2 重命名
  * scala中允许对引入的类或方法进行重命名，
  * 如果我们需要在程序中同时使用java.util.HashMap及scala.collection.mutable.HashMap时，
  * 可以利用重命名的方法消除命名冲突的问题，虽然也可以采用包名前缀的方式使用，但代码不够简洁
  *
  */
//将java.util.HashMap重命名为JavaHashMap
import java.util.{HashMap => JavaHashMap}
import scala.collection.mutable.HashMap

object RenameUsage {
	def main(args: Array[String]): Unit = {
		val javaHashMap = new JavaHashMap[String, String]()
		javaHashMap.put("Spark", "excellent")
		javaHashMap.put("MapReduce", "good")
		for (key <- javaHashMap.keySet().toArray) {
			println(key + ":" + javaHashMap.get(key))
		}

		val scalaHashMap = new HashMap[String, String]
		scalaHashMap.put("Spark", "excellent")
		scalaHashMap.put("MapReduce", "good")
		scalaHashMap.foreach(e => {
			val (k, v) = e
			println(k + ":" + v)
		})
	}
}

/**
  * 3 类隐藏
  */
//通过HashMap=> _，这样类便被隐藏起来了
import java.util.{HashMap => _, _}
import scala.collection.mutable.HashMap

object RenameUsage2 {
	def main(args: Array[String]): Unit = {

		//这样的话,HashMap便无歧义地指向scala.collection.mutable.HashMap
		val scalaHashMap = new HashMap[String, String]
		scalaHashMap.put("Spark", "excellent")
		scalaHashMap.put("MapReduce", "good")
		scalaHashMap.foreach(e => {
			val (k, v) = e
			println(k + ":" + v)
		})
	}
}

/**
  * 内部类
  *
  * 前面我们提到过内部类，我们看到内部类可以像类的其它成员一样访问外部类的私有成员，
  * 本小节将对内部类进行更加详细的介绍。
  * 要点1：外部类不能访问内部类的成员域，但内部类可以直接访问外部类成员域，哪怕这个成员域是private私有的
  */
class OuterClass {
	//即使定义为 private[this] var x:Int=0，也是可行的
	private var x: Int = 0

	//def getInnerY=y，外部类不能直接访问内部类的成员域
	class InnerClass {
		private var y: Int = 0

		//内部的类可以直接访问外部类的成员变量和成员方法,注意外部类的成员变量是private
		def getOuterX = x
	}

}

object InnerDemo {
	def main(args: Array[String]): Unit = {
		val o = new OuterClass
		//创建内部类对象，通过o.InnerClass方式，InnerClass就像是OuterClass的成员变量一样
		val i = new o.InnerClass
		println(i.getOuterX)
	}
}

//从上述代码可以看出，内部类除了是一个类之外，与外部类的成员没有任何区别，它可以与外部类的成员域一样被使用
