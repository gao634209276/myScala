package chapter3

import java.io.File

import scala.io.Source

/**
  * 隐式转换简介
  * 隐式转换函数
  * 隐式转换规则
  * 隐式参数
  */
/**
  * 1. 隐式转换简介
  *
  * 在scala语言当中，隐式转换是一项强大的程序语言功能，
  * 它不仅能够简化程序设计，也能够使程序具有很强的灵活性。
  * 要想更进一步地掌握scala语言，了解其隐式转换的作用与原理是很有必要的，
  * 否则很难得以应手地处理日常开发中的问题。
  */
/*
在scala语言中，隐式转换是无处不在的，只不过scala语言为我们隐藏了相应的细节，
例如scala中的类继承层次结构中：
http://blog.csdn.net/lovehuangjiaju/article/details/47264655
它们存在固有的隐式转换，不需要人工进行干预，例如Float在必要情况下自动转换为Double类型
在前一讲的视图界定中我们也提到，视图界定可以跨越类层次结构进行，它背后的实现原理就是隐式转换，
例如Int类型会视图界定中会自动转换成RichInt,而RichInt实现了Comparable接口，
当然这里面的隐式转换也是scala语言为我们设计好的

Int--(implicitly)-->RichInt-->AnyVal,ScalaNumberProxy[Int],RangedProxy[Int]
本节将对隐式转换中的隐式转换函数、隐式转换规则、隐式参数进行介绍，使大家明白如何自己实现隐式转换操作。
 */
/**
  * 2. 隐式转换函数
  */
/*
下列赋值如果没有隐式转换的话会报错：
scala> val x:Int=3.5
<console>:7: error: type mismatch;
 found   : Double(3.5)
 required: Int
       val x:Int=3.5
                 ^
添加隐式转换函数后可以实现Double类型到Int类型的赋值

 */
//添加隐式转换函数后可以实现Double类型到Int类型的赋值
object ImplicitDemo {
	//定义了一个隐式函数double2Int，将输入的参数
	//从Double类型转换到Int类型
	implicit def double2Int(x: Double) = x.toInt

	//定义完隐式转换后，便可以直接将Double类型赋值给Int类型
	val x: Int = 3.5
	/*
	隐式函数的名称对结构没有影响，即implicit def double2Int(x:Double)=x.toInt
	函数可以是任何名字，只不能采用source2Target这种方式函数的意思比较明确，
	阅读代码的人可以见名知义，增加代码的可读性。
	 */
}

/**
  * 隐式转换功能十分强大，可以快速地扩展现有类库的功能，
  * 例如下面的代码：
  */
//RichFile类中定义了Read方法
class RichFile(val file: File) {
	def read = Source.fromFile(file).getLines().mkString
}

object ImplicitFunction extends App {
	implicit def double2Int(x: Double) = x.toInt

	var x: Int = 3.5

	//隐式函数将java.io.File隐式转换为RichFile类
	implicit def file2RichFile(file: File) = new RichFile(file)

	val f = new File("file.log").read
	println(f)
}

/**
  * 3. 隐式转换规则
  */
/*
隐式转换可以定义在目标文件当中，例如
implicit def double2Int(x:Double)=x.toInt
var x:Int=3.5
隐式转换函数与目标代码在同一个文件当中，
也可以将隐式转换集中放置在某个包中，在使用进直接将该包引入即可，
例如：
 */
//在chapter3包中定义了子包implicitConversion
//然后在object ImplicitConversion中定义所有的引式转换方法
package implicitConversion {

	object ImplicitConversion1 {
		implicit def double2Int(x: Double) = x.toInt

		implicit def file2RichFile(file: File) = new RichFile(file)
	}

}

object ImplicitFunction1 extends App {
	//在使用时引入所有的隐式方法
	import chapter3.implicitConversion.ImplicitConversion1._

	var x: Int = 3.5
	val f = new File("file.log").read
	println(f)
}

/**
  * 这种方式在scala语言中比较常见，在前面我们也提到，
  * scala会默认帮我们引用Predef对象中所有的方法,Predef中定义了很多隐式转换函数,
  * 下面是Predef的部分隐式转换源码：
  */
/*
scala> :implicits -v
/* 78 implicit members imported from scala.Predef */
  /* 48 inherited from scala.Predef */
  implicit def any2ArrowAssoc[A](x: A): ArrowAssoc[A]
  implicit def any2Ensuring[A](x: A): Ensuring[A]
  implicit def any2stringadd(x: Any): runtime.StringAdd
  implicit def any2stringfmt(x: Any): runtime.StringFormat
  implicit def boolean2BooleanConflict(x: Boolean): Object
  implicit def byte2ByteConflict(x: Byte): Object
  implicit def char2CharacterConflict(x: Char): Object
  implicit def double2DoubleConflict(x: Double): Object
  implicit def float2FloatConflict(x: Float): Object
  implicit def int2IntegerConflict(x: Int): Object
  implicit def long2LongConflict(x: Long): Object
  implicit def short2ShortConflict(x: Short): Object

  那什么时候会发生隐式转换呢？主要有以下几种情况：
1 当方法中参数的类型与实际类型不一致时，例如
def f(x:Int)=x
	//方法中输入的参数类型与实际类型不一致，此时会发生隐式转换
	//double类型会转换为Int类型，再进行方法的执行
	f(3.14)
2 当调用类中不存在的方法或成员时，会自动将对象进行隐式转换，例如：
 */

object ImplicitFunction2 extends App {
	implicit def double2Int(x: Double) = x.toInt

	var x: Int = 3.5

	//隐式函数将java.io.File隐式转换为RichFile类
	implicit def file2RichFile(file: File) = new RichFile(file)

	//File类的对象并不存在read方法，此时便会发生隐式转换
	//将File类转换成RichFile
	val f = new File("file.log").read
	println(f)
}

/**
  * 前面我们讲了什么情况下会发生隐式转换，下面我们讲一下什么时候不会发生隐式转换：
  *
  * 1 编译器可以不在隐式转换的编译通过，则不进行隐式转换，例如
  */
/*
//这里定义了隐式转换函数
	scala> implicit def double2Int(x:Double)=x.toInt
	warning: there were 1 feature warning(s); re-run with -feature for details
	double2Int: (x: Double)Int

//下面几条语句，不需要自己定义隐式转换编译就可以通过
//因此它不会发生前面定义的隐式转换
	scala> 3.0*2
	res0: Double = 6.0
	scala> 2*3.0
	res1: Double = 6.0
	scala> 2*3.7
	res2: Double = 7.4
 */
/**
  * 2 如果转换存在二义性，则不会发生隐式转换，例如
  */
/*
package implicitConversion {

	object ImplicitConversion2 {
		implicit def double2Int(x: Double) = x.toInt

		//这里定义了一个隐式转换
		implicit def file2RichFile(file: File) = new RichFile(file)

		//这里又定义了一个隐式转换，目的与前面那个相同
		implicit def file2RichFile2(file: File) = new RichFile(file)
	}

}

object ImplicitFunction extends App {

	import chapter3.implicitConversion.ImplicitConversion2._

	var x: Int = 3.5

	//下面这条语句在编译时会出错，提示信息如下：
	//type mismatch; found : java.io.File required:
	// ?{def read: ?} Note that implicit conversions
	//are not applicable because they are ambiguous:
	//both method file2RichFile in object
	//ImplicitConversion of type (file:
	//java.io.File)cn.scala.xtwy.RichFile and method
	//file2RichFile2 in object ImplicitConversion of
	//type (file: java.io.File)cn.scala.xtwy.RichFile
	//are possible conversion functions from java.io.File to ?{def read: ?}
	//value read is not a member of java.io.File

	val f = new File("file.log").read
	println(f)
}

 */

/**
  * 编译提示隐式转换存在二义性（ambiguous）
  * 3 隐式转换不会嵌套进行，例如
  */
package implicitConversion {

	object ImplicitConversion3 {
		implicit def double2Int(x: Double) = x.toInt

		implicit def file2RichFile(file: File) = new RichFile(file)

		//implicit def file2RichFile2(file:File)=new RichFile(file)
		implicit def richFile2RichFileAnother(file: RichFile) = new RichFileAnother(file)
	}

}

//RichFileAnother类，里面定义了read2方法
class RichFileAnother(val file: RichFile) {
	def read2 = file.read
}

object ImplicitFunction3 extends App {

	import chapter3.implicitConversion.ImplicitConversion3._

	var x: Int = 3.5

	//隐式转换不会多次进行，下面的语句会报错
	//不能期望会发生File到RichFile，然后RifchFile到
	//RichFileAnthoer的转换
	val f = new File("file.log").read
	println(f)
}

//理解了这些规则之后，在使用隐式转换时才能够得心应手

/**
  * 4. 隐式参数
  */
/*
在一般的函数据定义过程中，需要明确传入函数的参数，代码如下：
package cn.scala.xtwy
class Student(var name:String){
  //将Student类的信息格式化打印
  def formatStudent(outputFormat:OutputFormat)={
    outputFormat.first+" "+this.name+" "+outputFormat.second
  }
}

class OutputFormat(var first:String,val second:String)

object ImplicitParameter {
  def main(args: Array[String]): Unit = {
    val outputFormat=new OutputFormat("<<",">>")
    println(new Student("john").formatStudent(outputFormat))
  }
}
//执行结果
//<< john >>
 */
/**
  * 如果给函数定义隐式参数的话，则在使用时可以不带参数，代码如下：
  */
class Student2(var name: String) {
	//利用柯里化函数的定义方式，将函数的参数利用
	//implicit关键字标识
	//这样的话，在使用的时候可以不给出implicit对应的参数
	def formatStudent()(implicit outputFormat: OutputFormat) = {
		outputFormat.first + " " + this.name + " " + outputFormat.second
	}
}

class OutputFormat(var first: String, val second: String)

object ImplicitParameter {
	def main(args: Array[String]): Unit = {
		//程序中定义的变量outputFormat被称隐式值
		implicit val outputFormat = new OutputFormat("<<", ">>")
		//在.formatStudent()方法时，编译器会查找类型
		//为OutputFormat的隐式值,本程序中定义的隐式值
		//为outputFormat
		println(new Student2("john").formatStudent())
	}
}