package chapter1.set

/**
  * 元组则是不同类型值的聚集
  */
object TupleDemo {
	//元组的定义
	("hello", "china", "beijing")
	//res23: (String, String, String) = (hello,china,beijing)

	("hello", "china", 1)
	//res24: (String, String, Int) = (hello,china,1)

	var tuple = ("Hello", "China", 1)
	//tuple: (String, String, Int) = (Hello,China,1)

	//访问元组内容
	tuple._1
	//res25: String = Hello

	tuple._2
	//res26: String = China

	tuple._3
	//res27: Int = 1

	//通过模式匹配获取元组内容
	val (first, second, third) = tuple
	//first: String = Hello
	//second: String = China
	//third: Int = 1
}
