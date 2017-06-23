本节主要内容

	高阶函数简介
	Scala中的常用高阶函数
	SAM转换
	函数柯里化
	部分应用函数

1. 高阶函数简介

高阶函数主要有两种：
	一种是将一个函数当做另外一个函数的参数（即函数参数）；
	另外一种是返回值是函数的函数。
这两种在本教程的第五节 函数与闭包中已经有所涉及，这里简单地回顾一下：
（1）函数参数
	//函数参数，即传入另一个函数的参数是函数
	//((Int)=>String)=>String
	scala> def convertIntToString(f:(Int)=>String)=f(4)
	convertIntToString: (f: Int => String)String

	scala> convertIntToString((x:Int)=>x+" s")
	res32: String = 4 s

（2）返回值是函数的函数
	//高阶函数可以产生新的函数，即我们讲的函数返回值是一个函数
	//(Double)=>((Double)=>Double)
	scala>  def multiplyBy(factor:Double)=(x:Double)=>factor*x
	multiplyBy: (factor: Double)Double => Double

	scala> val x=multiplyBy(10)
	x: Double => Double = <function1>

	scala> x(50)
	res33: Double = 500.0

Scala中的高阶函数可以说是无处不在，这点可以在Scala中的API文档中得到验证，
下图给出的是Array数组的需要函数作为参数的API：

	例如flatMap方法，下面是其API的详细内容：
	def flatMap[B](f: (A) ⇒ GenTraversableOnce[B]): Array[B]
	[use case]
	Builds a new collection by applying a function to all elements of this array and using the elements of the resulting collections.

	//下面的代码给出了该函数的用法
	For example:
	def getWords(lines: Seq[String]): Seq[String] = lines flatMap (line => line split "\\W+")
	The type of the resulting collection is guided by the static type of array. This might cause unexpected results sometimes. For example:
	// lettersOf will return a Seq[Char] of likely repeated letters, instead of a Set
	def lettersOf(words: Seq[String]) = words flatMap (word => word.toSet)
	// lettersOf will return a Set[Char], not a Seq
	def lettersOf(words: Seq[String]) = words.toSet flatMap (word => word.toSeq)
	// xs will be a an Iterable[Int]
	val xs = Map("a" -> List(11,111), "b" -> List(22,222)).flatMap(_._2)
	// ys will be a Map[Int, Int]
	val ys = Map("a" -> List(1 -> 11,1 -> 111), "b" -> List(2 -> 22,2 -> 222)).flatMap(_._2)

	//下面几行对该函数的参数进行了说明
	B : the element type of the returned collection.
	//指明f是函数，该函数传入的参数类型是A,返回类型是GenTraversableOnce[B]
	f : the function to apply to each element.
	returns : a new array resulting from applying the given collection-valued function f to each element of this array and concatenating the results.

2. Scala中的常用高阶函数 See