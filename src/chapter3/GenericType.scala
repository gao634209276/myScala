package chapter3

import java.io._

/**
  * 泛型(Generic Type）简介
  * 注解（Annotation）简介
  * 注解常用场景
  */

/**
  * 1. 泛型(Generic Type）简介
  *
  * 泛型用于指定方法或类可以接受任意类型参数，参数在实际使用时才被确定，
  * 泛型可以有效地增强程序的适用性，使用泛型可以使得类或方法具有更强的通用性。
  * 泛型的典型应用场景是集合及集合中的方法参数，可以说同Java一样，scala中泛型无处不在，
  * 具体可以查看scala的api
  */

//1 泛型类

//单个泛型参数的使用情况
class Person[T](var name: T)

class Student[T](name: T) extends Person(name)

object GenericDemo {
	def main(args: Array[String]): Unit = {
		println(new Student[String]("摇摆少年梦").name)
	}
}

/*
javap -private Person.class
Compiled from "GenericDemo.scala"
public class cn.scala.xtwy.Person<T> {
  private T name;
  public T name();
  public void name_$eq(T);
  public cn.scala.xtwy.Person(T);
}

D:\ScalaWorkspace\ScalaChapter16\bin\cn\scala\xtwy>javap -private Student.class
Compiled from "GenericDemo.scala"
public class cn.scala.xtwy.Student<T, S> extends cn.scala.xtwy.Person<T> {
  private S age;
  public S age();
  public void age_$eq(S);
  public cn.scala.xtwy.Student(T, S);
}
 */
//从上面的代码不难看出，scala泛型对应于java中的泛型，掌握了java中的泛型也就掌握了scala中的泛型
/**
  * 2. 注解（Annotation）简介
  * Annotation是一种对程序代码进行描述的结构化信息。
  * Annotation可以分布在程序的任何地方，能够注解变量、类、方法、参数等多种元素，
  * 它的主要功能有以下几种：
  * 1 自动生成scala文档
  * 2 检查程序中可能出现的语法问题
  * 3 规定程序行为
  */
/*
scala.collection.immutable.HashMap类对应部分源码：
@SerialVersionUID(2L)
class HashMap[A, +B] extends AbstractMap[A, B]
                        with Map[A, B]
                        with MapLike[A, B, HashMap[A, B]]
                        with Serializable
                        with CustomParallelizable[(A, B), ParHashMap[A, B]]
{

2 检查程序中可能出现的语法问题
//当程序使用该API时，给出相应提示，属于语法检查范畴
 @deprecated("Use the `merged` method instead.", "2.10.0")
  def merge[B1 >: B](that: HashMap[A, B1], mergef: MergeFunction[A, B1] = null): HashMap[A, B1] = merge0(that, 0, liftMerger(mergef))
3 规定程序行为
//@BeanProperty，要求程序生成相应getter,setter方法，与java命名规范一致
class Student[T,S](name:T,var age:S) extends Person(name)
{
  @BeanProperty var studentNo:String=null
}
当然，annotation还有其它功能，上面三种只是平时在编写程序时最为常用的功能
 */
//annotation具有如下语法格式：
class A

class B extends A {
	//同java一样，采用@+注解关键字对方法、变量
	//类等进行注解标识
	//下面的注解用于标识getName方法在未来会被丢弃
	//不推荐使用
	@deprecated def getName() = "Class B"
}

object AnnotationDemo {
	def main(args: Array[String]): Unit = {
		var b = new B()
		//在调用的时候，编译器出给出相应提示
		b.getName()
	}
}

/**
  * 3. 注解常用场景
  *
  * 注解的常用场景包括volatile，transient，native,SerialVersionUID,serializable5个，
  * 用于对变量或方法进行注解，其中volatile用于标识变量可能会被多个线程同时修改，它不是线程安全的；
  * transient用于标识变量是瞬时的，它不会被持久化；
  * native用于标识算法来自C或C++代码实现
  */
abstract class Ab {
	//native用于标记 cplusplusMethod为c或c++中实现的本地方法
	@native def cplusplusMethod()
}

//标记B可被序列化
//注解声明序列化版本
@SerialVersionUID(1000330L)
@serializable
class B1 extends Ab {
	//volatile注解标记变量name非线程安全
	@volatile var name: String = "B"
	//transient注解用于标记变量age不被序列化
	@transient var age: Int = 40

}

//下面举下对象序列化的例子：
/*
//下面的代码编译时不会出问题，但执行时会抛出异常
class Person{
  var name:String="zzh"
  var age:Int=0
  override def toString()="name="+name+" age="+age
}

object Serialize {
  def main(args: Array[String]): Unit = {
     val file = new File("person.out")

        val oout = new ObjectOutputStream(new FileOutputStream(file))
        val person = new Person
        oout.writeObject(person)
        oout.close()

        val oin = new ObjectInputStream(new FileInputStream(file))
        val newPerson = oin.readObject()
        oin.close();
        println(newPerson)
  }
}
Exception in thread "main" java.io.NotSerializableException: cn.scala.xtwy.serialize.Person
    at java.io.ObjectOutputStream.writeObject0(Unknown Source)
    at java.io.ObjectOutputStream.writeObject(Unknown Source)
    at cn.scala.xtwy.serialize.Serialize$.main(Serialize.scala:22)
    at cn.scala.xtwy.serialize.Serialize.main(Serialize.scala)

此时在Person类前加@serializable则可以对对象进行正常序列化
 */
//声明对象可序列化
@serializable
class Person33 {
	var name: String = "zzh"
	var age: Int = 0

	override def toString() = "name=" + name + " age=" + age
}

object Serialize {
	def main(args: Array[String]): Unit = {
		val file = new File("person.out")

		val oout = new ObjectOutputStream(new FileOutputStream(file))
		val person = new Person33
		oout.writeObject(person)
		oout.close()

		val oin = new ObjectInputStream(new FileInputStream(file))
		val newPerson = oin.readObject()
		oin.close();
		println(newPerson)
	}
}

//反序列化后的输出结果为：
//name=zzh age=0
/**
  * 如果给成员变量加@transient注解的话，则相应的成员变量不会被序列化，
  * 此时如果进行反序列化的话，对应成员变量为null，如
  */
@serializable
class Person34 {
	//@transient注解声明后，成员变量不会被充列化
	@transient var name: String = "zzh"
	var age: Int = 0

	override def toString() = "name=" + name + " age=" + age
}

object Serialize2 {
	def main(args: Array[String]): Unit = {
		val file = new File("person.out")

		val oout = new ObjectOutputStream(new FileOutputStream(file))
		val person = new Person34
		oout.writeObject(person)
		oout.close()

		val oin = new ObjectInputStream(new FileInputStream(file))
		val newPerson = oin.readObject()
		oin.close();
		println(newPerson)
	}
}

//反序列化后的输出
//name=null age=0
