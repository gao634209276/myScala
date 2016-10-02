package chapter3

import java.io.File

import scala.io.Source

/**
  * 隐式参数中的隐式转换
  * 函数中隐式参数使用概要
  * 隐式转换问题梳理
  */
/**
  * 1. 隐式参数中的隐式转换
  *
  * 前一讲中，我们提到函数中如果存在隐式参数，
  * 在使用该函数的时候如果不给定对应的参数，则编译器会自动帮我们搜索相应的隐式值，并将该隐式值作为函数的参数，
  * 这里面其实没有涉及到隐式转换，本节将演示如何利用隐式参数进行隐式转换，
  * 下面的代码给定的是一个普通的比较函数
  */
object ImplicitParameter1 extends App {
	//这里面如果泛型T没有具体指定，它不能直接使用.编译通过

	//类型变量界定和视图界定指定其上界为Ordered[T]，才可以通过编译
	def compare[T <: Ordered[T]](first: T, second: T) = {
		//<符号进行比较
		if (first < second)
			first
		else
			second
	}
}

//还有一种解决方案就是通过隐式参数的隐式转换来实现，代码如下：
object ImplicitParameter2 extends App {
	//下面代码中的(implicit order:T=>Ordered[T])
	//给函数compare指定了一个隐式参数
	//该隐式参数是一个隐式转换
	def compare[T](first: T, second: T)(implicit order: T => Ordered[T]) = {
		if (first > second)
			first
		else
			second
	}

	println(compare("A", "B"))
}

/**
  * 2. 函数中隐式参数使用概要
  */

//要点1：在定义函数时，如果函数没有柯里化，implicit关键字会作用于所有参数，例如：
/*
implicit关键字在下面的函数中只能出现一次
它作用于两个参数x,y，也即x,y都是隐式参数
	def sum(implicit x: Int, y: Int) = x + y

下面的函数不合法，函数如果没有柯里化，不能期望
implicit关键字会作用于其中一个参数
	def sum(x: Int, implicit y: Int) = x + y
	def sum(implicit x: Int, implicit y: Int) = x + y
另外，值得注意的是，def maxFunc(implicit x: Int, y: Int) = x + y
在使用时，也只能指定一个隐式值，即指定的隐式值分别会对应函数中的参数（这里是x,y），
代码如下：
	def sum(implicit x: Int, y: Int) = x + y
	//只能指定一个隐式值
	//例如下面下定义的x会自动对应maxFunc中的
	//参数x,y即x=3,y=3，从而得到的结果是6
	implicit val x:Int=3
	//不能定义两个隐式值
	//implicit val y:Int=4
	println(sum)
 */

//要点2：要想使用implicit只作用于某个函数参数，则需要将函数进行柯里化，如：

/*
def sum(x: Int)(implicit y:Int)=x+y
值得注意的是，下面这种两种带隐式参数的函数也是不合法的
	def sum(x: Int)(implicit y:Int)(d:Int)=x+y+d
	def sum(x: Int)(implicit y:Int)(implicit d:Int)=x+y+d
 */

//要点3: 匿名函数不能使用隐式参数，例如：
/*
val sum2=(implicit x:Int)=>x+1
 */

///要点4: 如何函数带有隐式参数，则不能使用其偏函数，例如：
/*
	def sum(x: Int)(implicit y:Int)=x+y
	//不能定义sum的偏函数，因为它带有隐式参数
	//could not find implicit value for
	//parameter y: Int
	//not enough arguments for method sum:
	// (implicit y: Int)Int. Unspecified value parameter y.
	def sum2=sum _
 */
/**
  * 3. 隐式转换问题梳理
  */
//1 多次隐式转换问题
// 在上一讲中我们提到，隐式转换从源类型到目标类型不会多次进行，
// 也即源类型到目标类型的转换只会进行一次
class RichFile2(val file: File) {
	def read = Source.fromFile(file).getLines().mkString
}

//RichFileAnother类，里面定义了read2方法
class RichFileAnother2(val file: RichFile2) {
	def read2 = file.read
}

//隐式转换不会多次进行，下面的语句会报错
//不能期望会发生File到RichFile，然后RifchFile到
//RichFileAnthoer的转换
/*
val f=new File("file.log").read2
println(f)
 */
//注意这里指的是源类型到目标类型的转换只会进行一次，并不是说不存在多次隐式转换，
// 在一般的方法调用过程中可能会出现多次隐式转换，例如：
class ClassAa {
	override def toString() = "This is Class A"
}

class ClassBb {
	override def toString() = "This is Class B"
}

class ClassCc {
	override def toString() = "This is  ClassC"

	def printC(c: ClassCc) = println(c)
}

class ClassDd

object ImplicitWhole extends App {
	implicit def B2C(b: ClassBb) = {
		println("B2C")
		new ClassCc
	}

	implicit def D2C(d: ClassDd) = {
		println("D2C")
		new ClassCc
	}

	//下面的代码会进行两次隐式转换
	//因为ClassD中并没有printC方法
	//因为它会隐式转换为ClassC（这是第一次,D2C）
	//然后调用printC方法
	//但是printC方法只接受ClassC类型的参数
	//然而传入的参数类型是ClassB
	//类型不匹配，从而又发生了一次隐式转地换(这是第二次,B2C）
	//从而最终实现了方法的调用
	new ClassDd().printC(new ClassBb)
}

/**
  * 还有一种情况也会发生多次隐式转换，如果给函数定义了隐式参数，
  * 在实际执行过程中可能会发生多次隐式转换，代码如下：
  */
object Main extends App {

	class PrintOps() {
		def print(implicit i: Int) = println(i);
	}

	implicit def str2PrintOps(s: String) = {
		println("str2PrintOps")
		new PrintOps
	}

	implicit def str2int(implicit s: String): Int = {
		println("str2int")
		Integer.parseInt(s)
	}

	implicit def getString = {
		println("getString")
		"123"
	}

	//下面的代码会发生三次隐式转换
	//首先编译器发现String类型是没有print方法的
	//尝试隐式转换，利用str2PrintOps方法将String
	//转换成PrintOps（第一次）
	//然后调用print方法，但print方法接受整型的隐式参数
	//此时编译器会搜索隐式值，但程序里面没有给定，此时
	//编译器会尝试调用 str2int方法进行隐式转换，但该方法
	//又接受一个implicit String类型参数，编译器又会尝试
	//查找一个对应的隐式值，此时又没有，因此编译器会尝试调用
	//getString方法对应的字符串（这是第二次隐式转换，
	//获取一个字符串，从无到有的过程）
	//得到该字符串后，再调用str2int方法将String类型字符串
	//转换成Int类型（这是第三次隐式转换）
	"a".print
}

/*
上面这个例子来源于：爱国者的博客，感谢该作者的无私奉献

2 要不要用隐式转换的问题

从上述代码中可以看到，隐式转换功能很强大，
但同时也带来了程序复杂性性问题，在一个程序中如果大量运用隐式转换，
特别是涉及到多次隐式转换时，会使代码理解起来变得比较困难，
那到底要不要用隐式转换呢？下面给出我自己开发实践中的部分总结，供大家参考：
	1 即使你能轻松驾驭scala语言中的隐式转换，能不用隐式转换就尽量不用
	2 如果一定要用，在涉及多次隐式转换时，必须要说服自己这样做的合理性
	3 如果只是炫耀自己的scala语言能力，请大胆使用
 */