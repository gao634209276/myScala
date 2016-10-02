package chapter3

/**
  * 模式匹配的类型
  * for控制结构中的模式匹配
  * option类型模式匹配
  */
/**
  * 1. 模式的类型
  *
  * 1 常量模式
  */
object ConstantPattern {
	def main(args: Array[String]): Unit = {
		//注意，下面定义的是一个函数
		//函数的返回值利用的是模式匹配后的结果作为其返回值
		//还需要注意的是函数定义在main方法中
		//也即scala语言可以在一个函数中定义另外一个函数
		def patternShow(x: Any) = x match {
			case 5 => "five"
			case true => "true"
			case "test" => "String"
			case null => "null"
			case Nil => "empty list"
			case _ => "Other constant"
		}
		println(patternShow(5))
	}
}

/**
  * 2 变量模式
  */
object VariablePattern {
	def main(args: Array[String]): Unit = {
		def patternShow(x: Any) = x match {
			case 5 => "five"
			//所有不是值为5的都会匹配变量y
			//例如"xxx"，则函数的返回结果就是"xxx"
			case y => y
		}
		println(patternShow("xxx"))
	}
}

/**
  * 3 构造器模式
  */
//构造器模式必须将类定义为case class
case class Person29(name: String, age: Int)

object ConstructorPattern {
	def main(args: Array[String]): Unit = {
		val p = new Person29("摇摆少年梦", 27)
		def constructorPattern(p: Person29) = p match {
			case Person29(name, age) => "Person"
			case _ => "Other"
		}
	}
}

/**
  * 4 序列（Sequence)模式
  * 序列模式指的是像Array、List这样的序列集合进行模式匹配
  */
object SequencePattern {
	def main(args: Array[String]): Unit = {
		val p = List("spark", "hive", "SparkSQL")
		def sequencePattern(p: List[String]) = p match {
			//只需要匹配第二个元素
			case List(_, second, _*) => second
			case _ => "Other"
		}
		println(sequencePattern(p))
	}
}

/**
  * 5 元组模式
  */
//匹配某个元组内容
object TuplePattern {
	def main(args: Array[String]): Unit = {
		val t = ("spark", "hive", "SparkSQL")
		def tuplePattern(t: Any) = t match {
			case (one, _, _) => one
			case _ => "Other"
		}
		println(tuplePattern(t))
	}
}

/**
  * 6 类型模式
  */
//匹配传入参数的类型
object TypePattern {
	def main(args: Array[String]): Unit = {

		def tuplePattern(t: Any) = t match {
			case t: String => "String"
			case t: Int => "Integer"
			case t: Double => "Double"
		}
		println(tuplePattern(5.0))
	}

	//上述代码如果不用模式匹配的话，要实现相同的功能，可以通过下列代码实现：
	def tuplePattern2(t: Any) = {
		if (t.isInstanceOf[String]) "String"
		else if (t.isInstanceOf[Int]) "Int"
		else if (t.isInstanceOf[Double]) "Double"
		else if (t.isInstanceOf[Map[_, _]]) "MAP"
	}
}

/**
  * 7 变量绑定模式
  */
object VariableBindingPattern {
	def main(args: Array[String]): Unit = {
		var t = List(List(1, 2, 3), List(2, 3, 4))
		def variableBindingPattern(t: Any) = t match {
			//变量绑定，采用变量名（这里是e)
			//与@符号，如果后面的模式匹配成功，则将
			//整体匹配结果作为返回
			case List(_, e@List(_, _, _)) => e
			case _ => Nil
		}

		println(variableBindingPattern(t))
	}
}

//编译执行后的输出结果为  List(2, 3, 4)

/**
  * 2. for控制结构中的模式匹配
  */

object PatternInForLoop {
	def main(args: Array[String]): Unit = {
		val m = Map("china" -> "beijing", "dwarf japan" -> "tokyo", "Aerican" -> "DC Washington")
		//利用for循环对Map进行模式匹配输出，
		for ((nation, capital) <- m)
			println(nation + ": " + capital)
	}
}

//正则表达式中的模式匹配：
object RegexMatch {
	def main(args: Array[String]): Unit = {
		val ipRegex = "(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)".r
		for (ipRegex(one, two, three, four) <- ipRegex.findAllIn("192.168.1.1")) {
			println("IP子段1:" + one)
			println("IP子段2:" + two)
			println("IP子段3:" + three)
			println("IP子段4:" + four)
		}
	}
}

/**
  * 3. Option类型模式匹配
  * 在前面的课程内容中，我们曾经提到过Option类型，Option类型有两个子类，
  * 分别是Some和None（单例对象），本小节将从模式匹配的角度对Option类进行重新思考。
  */
/*
下面给出的是Option类在scala语言中的类层次结构：
http://blog.csdn.net/lovehuangjiaju/article/details/47187639
Option[A]-->Product,Serializable,None,Some[A]
--implicity-->Iterable[A]
Option类其实是一个sealed class
//Option类的部分源码
sealed abstract class Option[+A] extends Product with Serializable {
  self =>
	// Returns true if the option is $none, false otherwise.
	def isEmpty: Boolean

	//Returns true if the option is an instance of $some, false otherwise.
	def isDefined: Boolean = !isEmpty

下面给出的分别是Some及None的源码：
// Class `Some[A]` represents existing values of type
final case class Some[+A](x: A) extends Option[A] {
	def isEmpty = false
	def get = x
}
//This case object represents non-existent values.
case object None extends Option[Nothing] {
	def isEmpty = true
	def get = throw new NoSuchElementException("None.get")
}
下面的代码演示了其如何应用到模式匹配中:
 */

object OptionDemo extends App {
	val m = Map("hive" -> 2, "spark" -> 3, "Spark MLlib" -> 4)

	def mapPattern(t: String) = m.get(t) match {
		case Some(x) => println(x); x
		case None => println("None"); -1
	}

	println(mapPattern("Hive"))
}

//输出结果为：
//None
//-1
/**
  * 前面我们看到：
  * None是一个case object，它同Some一样都extends Option类，
  * 只不过Some是case class，对于case class我们已经很熟悉了，
  * 那case object它又是怎么样的呢？
  * 假设我们定义了以下类：
  */

//下面的类主要用于模拟Option,Some,None三个类或对象之间的关系
sealed abstract class Abs

case class CaseClass(name: String, age: Int) extends Abs

case object CaseObject extends Abs {
}

/*
上述代码编译后，生成的字节码文件如下：
2015/08/01  21:26               515 A.class
2015/08/01  21:26             1,809 B$.class
2015/08/01  21:26             4,320 B.class
2015/08/01  21:26             1,722 CaseObject$.class
2015/08/01  21:26             1,490 CaseObject.class
单从编译后生成的类来看，它们之间似乎实现方式都一样，那到底是什么样的呢？
class A的反编译后的代码如下：
javap -private A.class
public abstract class cn.scala.xtwy.A {
  public cn.scala.xtwy.A();
}
case class B对应的字节码文件反编译后如下：

public class cn.scala.xtwy.B extends cn.scala.xtwy.A implements scala.Product,sc
ala.Serializable {
  private final java.lang.String name;
  private final int age;
  public static scala.Function1<scala.Tuple2<java.lang.String, java.lang.Object>
, cn.scala.xtwy.B> tupled();
  public static scala.Function1<java.lang.String, scala.Function1<java.lang.Obje
ct, cn.scala.xtwy.B>> curried();
  public java.lang.String name();
  public int age();
  public cn.scala.xtwy.B copy(java.lang.String, int);
  public java.lang.String copy$default$1();
  public int copy$default$2();
  public java.lang.String productPrefix();
  public int productArity();
  public java.lang.Object productElement(int);
  public scala.collection.Iterator<java.lang.Object> productIterator();
  public boolean canEqual(java.lang.Object);
  public int hashCode();
  public java.lang.String toString();
  public boolean equals(java.lang.Object);
  public cn.scala.xtwy.B(java.lang.String, int);
}
//自动生成的伴生对像类
public final class cn.scala.xtwy.B$ extends scala.runtime.AbstractFunction2<java
.lang.String, java.lang.Object, cn.scala.xtwy.B> implements scala.Serializable {

  public static final cn.scala.xtwy.B$ MODULE$;
  public static {};
  public final java.lang.String toString();
  public cn.scala.xtwy.B apply(java.lang.String, int);
  public scala.Option<scala.Tuple2<java.lang.String, java.lang.Object>> unapply(
cn.scala.xtwy.B);
  private java.lang.Object readResolve();
  public java.lang.Object apply(java.lang.Object, java.lang.Object);
  private cn.scala.xtwy.B$();
}

case object CaseObject对应的反编译后的内容：
javap -private CaseObject.class
public final class cn.scala.xtwy.CaseObject {
  public static java.lang.String toString();
  public static int hashCode();
  public static boolean canEqual(java.lang.Object);
  public static scala.collection.Iterator<java.lang.Object> productIterator();
  public static java.lang.Object productElement(int);
  public static int productArity();
  public static java.lang.String productPrefix();
}

javap -private CaseObject$.class
public final class cn.scala.xtwy.CaseObject$ extends cn.scala.xtwy.A implements
scala.Product,scala.Serializable {
  public static final cn.scala.xtwy.CaseObject$ MODULE$;
  public static {};
  public java.lang.String productPrefix();
  public int productArity();
  public java.lang.Object productElement(int);
  public scala.collection.Iterator<java.lang.Object> productIterator();
  public boolean canEqual(java.lang.Object);
  public int hashCode();
  public java.lang.String toString();
  private java.lang.Object readResolve();
  private cn.scala.xtwy.CaseObject$();
}
 */
/**
  * 对比上述代码不难看出，case object与case class所不同的是，
  * case object对应反编译后的CaseObject$.class中不存在apply、unapply方法，
  * 这是因为None不需要创建对象及进行内容提取，
  * 从这个角度讲，它被定义为case object是十分合理的。
  */
