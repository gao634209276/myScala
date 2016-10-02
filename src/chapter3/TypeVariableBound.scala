package chapter3

/**
  * 类型变量界定(Type Variable Bound)
  * 视图界定（View Bound)
  * 上界（Upper Bound）与下界（Lower Bound）
  */

/**
  * 1. 类型变量界定(Type Variable Bound)
  *
  * 类型变量界定是指在泛型的基础上，对泛型的范围进行进一步的界定，从而缩下泛型的具体范围，
  * 例如：
  */
//下面的类编译通不过,因为泛型T在编译的时候不能确定其具体类型
//即并不是所有的类中都存在compareTo方法
/*
class TypeVariableBound {
	def compare[T](first:T,second:T)={
		if (first.compareTo(second)>0)
			first
		else
			second
	}
}

object TypeVariableBound{
	def main(args: Array[String]): Unit = {
		val tvb=new TypeVariableBound
		println(tvb.compare("A", "B"))
	}
}
 */
/**
  * 如果在使TypeVariableBound类编译通过，此时可以利用类型变量界定对泛型T进行界定，
  * 指明所有的泛型T都实现了Comparable接口，
  * 代码如下：
  */
class TypeVariableBound {
	//采用<:进行类型变量界定
	//该语法的意思是泛型T必须是实现了Comparable接口的类型
	def compare[T <: Comparable[T]](first: T, second: T) = {
		if (first.compareTo(second) > 0)
			first
		else
			second
	}
}

object TypeVariableBound {
	def main(args: Array[String]): Unit = {
		val tvb = new TypeVariableBound
		//由于String类型实现了Comparable接口,下面这种使用方式是合法的
		println(tvb.compare("A", "B"))
	}
}


/**
  * 从上述代码可以看到，
  * compare方法中如果输入的类型处于Comparable类对应继承层次结构中，则是合法的，否则的话编译会报错，
  * 例如：
  */
//定义一个case class类Person
case class Person30(var name: String, var age: Int)

/*
object TypeVariableBound2 {
	def main(args: Array[String]): Unit = {
		val tvb = new TypeVariableBound
		println(tvb.compare("A", "B"))
		//下面这一行代码会报错，这是因为Person类并没有实现Comparable接口
		println(tvb.compare(Person30("stephen", 19), Person30("john", 20)))
	}
}
 */


/**
  * 如果需要输入Person类也合法的话，则Person类要实现Comparable接口，
  * 代码如下：
  */
case class Person31(var name: String, var age: Int) extends Comparable[Person31] {
	def compareTo(o: Person31): Int = {
		if (this.age > o.age) 1
		else if (this.age == o.age) 0
		else -1
	}
}

/*

class TypeVariableBound2 {
	def compare[T <: Comparable[T]](first: T, second: T) = {
		if (first.compareTo(second) > 0)
			first
		else
			second
	}
}
 */

object TypeVariableBound2 {
	def main(args: Array[String]): Unit = {
		val tvb = new TypeVariableBound
		println(tvb.compare("A", "B"))
		//此时下面这条语句是合法的，因为Person类实现了Comparable接口
		println(tvb.compare(Person31("stephen", 19), Person31("john", 20)))
	}
}

/**
  * 上面的类型变量界定都是作用于方法Compare上，
  * 类型变量界定除了作用于方法上外，还可以对类中的泛型进行范围限定，
  * 例如：
  */
//定义Student类为case class，且泛型T的类型变量界定为AnyVal
//在创建类时，所有处于AnyVal类继承层次结构的类都是合法的
//如Int、Double等值类型
case class Student11[S, T <: AnyVal](var name: S, var hight: T)

object TypeVariableBound3 {
	def main(args: Array[String]): Unit = {
		//下面这条语句是不合法的，因为String类型不属于AnyVal类层次结构
		// val S1=Student("john","170")
		//下面这两条语句都是合法的，因为Int,Long类型都是AnyVal
		val S2 = Student11("john", 170.0)
		val S3 = Student11("john", 170L)
	}
}

/**
  * 从上面的例子中不难看出，类型变量界定可以对方法和类中的泛型进行范围界定，
  * 这种界定建立在类继承层次结构的基础之上，通过<:符号将泛型的范围进行一步减少。
  */

/**
  * 2. 视图界定（View Bound)
  *
  */
/*
上一节将的类型变量界定建立在类继承层次结构的基础上，但有时候这种限定不能满足实际要求，
如果希望跨越类继承层次结构时，可以使用视图界定来实现的，其后面的原理是通过隐式转换
（我们在下一讲中会详细讲解什么是隐式转换）来实现。
视图界定利用<%符号来实现，在上一节中提到：
//使用的是类型变量界定
case class Student[T,S <: Comparable[S]](var name:T,var height:S)
object ViewBound extends App{

  val s= Student("john","170")
  //下面这条语句不合法，这是因为
  //Int类型没有实现Comparable接口
  val s2= Student("john",170)
}
上面这个问题可以通过视图界定来解决，代码如下：
*/
/**
  * 上面这个问题可以通过视图界定来解决，代码如下：
  */
//利用<%符号对泛型S进行限定
//它的意思是S可以是Comparable类继承层次结构
//中实现了Comparable接口的类
//也可以是能够经过隐式转换得到的类,该类实现了
//Comparable接口

case class Student12[T, S <% Comparable[S]](var name: T, var height: S)

object ViewBound extends App {
	val s = Student12("john", "170")
	//下面这条语句在视图界定中是合法的
	//因为Int类型此时会隐工转换为
	//RichInt类，而RichInt类属于Comparable、
	//继承层次结构
	val s2 = Student12("john", 170)
}

/*
查看Scala API文档可以看到
Int--(implicitly)-->RichInt-->AnyVal,ScalaNumberProxy[Int],RangedProxy[Int]
Int类会隐式转换成RichInt类，RichInt并不是直接实现Comparable口，
而是通过ScalaNumberProxy类将Comparable中的方法继承过来：
OrderedProxy[T]-->Ordered[T],Typed[T],RichBoolean,ScalaNumberProxy[T]--(implicitly)-->math.Ordered[OrderedProxy[T]]
ScalaNumberProxy混入了OrderedProxy，而OrderedProxy又混入了Ordered
14 classes/traits--(implicitly)-->Ordered[A]--(implicitly)-->Ordered[Ordered[A]]
Deadline,Duration,OrderedProxy[T],StringLike[Repr],Value-->Ordered[A]-->Comparable[A]
trait Ordered混入了Comparable接口

可以看到，视图界定比类型变量界定的限制要宽松一点，
它不但可以是类继承层次结构中的类，也可以跨越类继承层次结构，
这后台的实现方式是通过隐式转换来进行的。
 */
/**
  * 3. 上界（Upper Bound）与下界（Lower Bound）
  */
/*
下类代码其实是类型参数中经常提到的上界，这是因为它限定了继承层次结构中最顶层的类，
例如T <: AnyVal表示泛型T的类型的最顶层类是AnyVal，
所有输入是AnyVal的子类都是合法的，其它的都是非法的，因为被称为上界，有点像x<=3这样的数学比较。

case class Student[S,T <: AnyVal](var name:S,var hight:T)
除了上界之外，还有个非常重要的内容就是下界，下界通过>:符号来标识，
代码如下：
 */
class Pair1[T](val first: T, val second: T) {
	//下界通过[R >: T]的意思是
	//泛型R的类型必须是T的超类
	def replaceFirst[R >: T](newFirst: R) = new Pair1[R](newFirst, second)

	override def toString() = first + "---" + second
}

//Book类
class Book(val name: String) {
	override def toString() = "name--" + name
}

//Book子类Ebook
class Ebook(name: String) extends Book(name)

//Book子类Pbook
class Pbook(name: String) extends Book(name)

//Pbook子类,WeridBook
class WeirdBook(name: String) extends Pbook(name)

object LowerBound extends App {

	val first = new Ebook("hello")
	val second = new Pbook("paper book")


	val p1 = new Pair1(first, second)
	println(p1)
	//scala> val p1 = new Pair1(first,second)
	//p1: Pair1[Book] = name--hello---name--paper book
	//Ebook,Pbook，最终得到的类是Pair1[Book]

	val newFirst = new Book("generic pBook")
	val p2 = p1.replaceFirst(newFirst)
	//p2: Pair1[Book] = name--generic pBook---name--paper book
	println(p2)

	val weirdFirst: WeirdBook = new WeirdBook("generic pBook")
	val p3 = p1.replaceFirst(weirdFirst)
	//p3: Pair1[Book] = name--generic pBook---name--paper book

	val p4 = new Pair1(second, second)
	//p4: Pair1[Pbook] = name--paper book---name--paper book
	println(p4)
	val thirdBook = new Book("Super Books")
	val p5 = p4.replaceFirst(thirdBook)
	println(p5)

	//下面这条语句会报错
	//type mismatch; found : cn.scala.xtwy.lowerbound.Pair1[cn.scala.xtwy.lowerbound.Pbook] required: cn.scala.xtwy.lowerbound.Pbook
	//val p6: Pbook = p4.replaceFirst(weirdFirst)
}

/*
通过上述代码发现，如果newFirst的类型刚好是T的基类，R就直接是newFirst的类型。
如果newFirst的类型不是T的基类，那R就会是T和newFirst的类型的共同基类。
当限定返回变量类型时，例如val p6:Pbook=p4.replaceFirst(weirdFirst)，
由于p4为Pair1[Pbook]，也即T为Pbook类型，
而replaceFirst(weirdFirst)中的weirdFirst为Pbook的子类，违反了R>:T的下界限定，从而编译出错。
从这里我们可以看到，下界的作用主要是保证类型安全
 */