package chapter1.function

/**
  * Created by hadoop on 16-10-2.
  */
object FunctionDemo {
	def main(args: Array[String]) {

		val increase = (x: Int) => x + 1
		println(increase(10))

		//前面的语句等同于
		def increaseAnother(x: Int) = x + 1

		println(increaseAnother(10))
		//多个语句则使用{}
		val increase2 = (x: Int) => {
			println("Xue")
			println("Tu")
			println("Wu")
			println("You")
			x + 1
		}
		println(increase2(10))
		//数组的map方法中调用（写法1）
		println(Array(1, 2, 3, 4).map(increase).mkString(","))

		//匿名函数写法（写法2）
		println(Array(1, 2, 3, 4).map((x: Int) => x + 1).mkString(","))

		//函数进一步简化

		//花括方式（写法3）
		Array(1, 2, 3, 4).map { (x: Int) => x + 1 }.mkString(",")
		//res25: String = 2,3,4,5

		//省略.的方式（写法4)
		Array(1, 2, 3, 4) map { (x: Int) => x + 1 } mkString (",")
		//res26: String = 2,3,4,5

		//参数类型推断写法（写法5）
		Array(1, 2, 3, 4) map { (x) => x + 1 } mkString (",")
		//res27: String = 2,3,4,5

		//函数只有一个参数的话，可以省略()（写法6）
		Array(1, 2, 3, 4) map { x => x + 1 } mkString (",")
		//res28: String = 2,3,4,5

		//如果参数右边只出现一次，则可以进一步简化（写法7）
		Array(1, 2, 3, 4) map {
			_ + 1
		} mkString (",")
		//res29: String = 2,3,4,5

		//值函数简化方式
		//val fun0=1+_，该定义方式不合法，因为无法进行类型推断

		//值函数简化方式（正确方式）
		val fun1 = 1 + (_: Double)
		//un1: Double => Double = <function1>

		fun1(999)
		//es30: Double = 1000.0

		//值函数简化方式（正确方式2）
		val fun2: (Double) => Double = 1 + _
		//fun2: Double => Double = <function1>

		fun2(200)

		//res31: Double = 201.0
		//函数参数(高阶函数）
		//((Int)=>String)=>String
		def convertIntToString(f: (Int) => String) = f(4)

		//convertIntToString: (f: Int => String)String

		convertIntToString((x: Int) => x + " s")

		//res32: String = 4 s

		//高阶函数可以产生新的函数
		//(Double)=>((Double)=>Double)
		def multiplyBy(factor: Double) = (x: Double) => factor * x

		//multiplyBy: (factor: Double)Double => Double

		val x = multiplyBy(10)
		//x: Double => Double = <function1>

		x(50)
		//res33: Double = 500.0
	}
}
