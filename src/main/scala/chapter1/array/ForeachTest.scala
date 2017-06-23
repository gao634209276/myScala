package chapter1.array

/**
  * Created by hadoop on 16-10-2.
  */
object ForeachTest {

	import scala.collection.mutable.ArrayBuffer

	var intArrayVar = ArrayBuffer(1, 1, 2)

	def main(args: Array[String]) {

		for (i <- 0 until intArrayVar.length) println("Array Element: " + intArrayVar(i))
		//数组方式（推荐使用）
		for (i <- intArrayVar) println("Array Element: " + i)
		//步长为2
		for (i <- 0 until(intArrayVar.length, 2)) println("Array Element: " + intArrayVar(i))
		//倒序输出
		for (i <- (0 until intArrayVar.length).reverse) println("Array Element: " +
			intArrayVar(i))
	}
}
