package chapter1.array

/**
  * Created by hadoop on 16-10-2.
  */
object ArrayMath {
	//定义一个整型数组
	val intArr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
	//求和
	intArr.sum
	//求最大值
	intArr.max

	import scala.collection.mutable.ArrayBuffer

	ArrayBuffer("Hello", "Hell", "Hey", "Happy").max
	//求最小值
	intArr.min
	//toString()方法
	intArr.toString()
	//mkString()方法
	intArr.mkString(",")
	intArr.mkString("<", ",", ">")
}
