package chapter1.function

/**
  * 闭包(Closure）
  */
object Closure {

	//(x:Int)=>x+more,这里面的more是一个自由变量（Free Variable）,more是一个没有给定含义的不定变量
	//而x则的类型确定、值在函数调用的时候被赋值，称这种变量为绑定变量（Bound Variable）
	var more = 1
	//more: Int = 1

	val fun = (x: Int) => x + more
	//fun: Int => Int = <function1>

	fun(10)
	//res1: Int = 11

	more = 10

	fun(10)
	//res2: Int = 20

	//像这种运行时确定more类型及值的函数称为闭包,more是个自由变量，在运行时其值和类型得以确定
	//这是一个由开放(free)到封闭的过程，因此称为闭包

	val someNumbers = List(-11, -10, -5, 0, 5, 10)
	//someNumbers: List[Int] = List(-11, -10, -5, 0, 5, 10)
	var sum = 0
	someNumbers.foreach(sum += _)
	sum
	//res8: Int = -11

	someNumbers.foreach(sum += _)
	sum

	//res10: Int = -22

	//下列函数也是一种闭包，因为在运行时其值才得以确定
	def multiplyBy(factor: Double) = (x: Double) => factor * x
}
