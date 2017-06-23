package chapter3

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JButton

/**
  * 2. Scala中的常用高阶函数
  */
object HigherOrderDef {
	// * 1 map函数
	//所有集合类型都存在map函数，例如Array的map函数的API具有如下形式：
	//def map[B](f: (A) ⇒ B): Array[B]
	def testMap(): Unit = {


		//这里面采用的是匿名函数的形式，字符串*n得到的是重复的n个字符串，这是scala中String操作的一个特点
		Array("spark", "hive", "hadoop").map((x: String) => x * 2)

		//在函数与闭包那一小节，我们提到，上面的代码还可以简化
		//省略匿名函数参数类型
		Array("spark", "hive", "hadoop").map((x) => x * 2)

		//单个参数，还可以省去括号
		Array("spark", "hive", "hadoop").map(x => x * 2)

		//参数在右边只出现一次的话，还可以用占位符的表示方式
		Array("spark", "hive", "hadoop").map(_ * 2)

		//List类型：
		val list = List("Spark" -> 1, "hive" -> 2, "hadoop" -> 2)
		//写法1
		list.map(x => x._1)
		//写法2
		list.map(_._1)
		list.map(_._2)

		//Map类型：
		//写法1
		Map("spark" -> 1, "hive" -> 2, "hadoop" -> 3).map(_._1)
		Map("spark" -> 1, "hive" -> 2, "hadoop" -> 3).map(_._2)
		//写法2
		Map("spark" -> 1, "hive" -> 2, "hadoop" -> 3).map(x => x._2)
		Map("spark" -> 1, "hive" -> 2, "hadoop" -> 3).map(x => x._1)
	}

	// * 2 flatMap函数
	def testFlatMap(): Unit = {
		//写法1
		List(List(1, 2, 3), List(2, 3, 4)).flatMap(x => x)
		//写法2
		List(List(1, 2, 3), List(2, 3, 4)).flatMap(x => x.map(y => y))
	}

	// * 3 filter函数
	def testFilter(): Unit = {
		Array(1, 2, 4, 3, 5).filter(_ > 3)
		List("List", "Set", "Array").filter(_.length > 3)
		Map("List" -> 3, "Set" -> 5, "Array" -> 7).filter(_._2 > 3)
	}

	// * 4 reduce函数
	def testReduce(): Unit = {
		//写法1
		Array(1, 2, 4, 3, 5).reduce(_ + _)

		List("Spark", "Hive", "Hadoop").reduce(_ + _)

		//写法2
		Array(1, 2, 4, 3, 5).reduce((x: Int, y: Int) => {
			println(x, y);
			x + y
		})
		Array(1, 2, 4, 3, 5).reduceLeft((x: Int, y: Int) => {
			println(x, y);
			x + y
		})
		Array(1, 2, 4, 3, 5).reduceRight((x: Int, y: Int) => {
			println(x, y);
			x + y
		})
	}

	//  * 5 fold函数
	def testFold(): Unit = {
		Array(1, 2, 4, 3, 5).foldLeft(0)((x: Int, y: Int) => {
			println(x, y);
			x + y
		})
		Array(1, 2, 4, 3, 5).foldRight(0)((x: Int, y: Int) => {
			println(x, y);
			x + y
		})
		Array(1, 2, 4, 3, 5).foldLeft(0)(_ + _)
		Array(1, 2, 4, 3, 5).foldRight(10)(_ + _)

		// /:相当于foldLeft
		(0 /: Array(1, 2, 4, 3, 5)) (_ + _)

		(0 /: Array(1, 2, 4, 3, 5)) ((x: Int, y: Int) => {
			println(x, y);
			x + y
		})
	}

	// * 6 scan函数
	def testScan(): Unit = {
		//从左扫描，每步的结果都保存起来，执行完成后生成数组
		Array(1, 2, 4, 3, 5).scanLeft(0)((x: Int, y: Int) => {
			println(x, y);
			x + y
		})
		//从右扫描，每步的结果都保存起来，执行完成后生成数组
		Array(1, 2, 4, 3, 5).scanRight(0)((x: Int, y: Int) => {
			println(x, y);
			x + y
		})
	}
}

/**
  * 3. SAM转换
  *
  * 在Java的GUI编程中，在设置某个按钮的监听器的时候，我们常常会使用下面的代码
  * （利用scala进行代码开发）：
  */
object SAMDemo {
	var counter = 0
	val button = new JButton("click")
	button.addActionListener(new ActionListener {
		override def actionPerformed(event: ActionEvent) {
			counter += 1
		}
	})

	/**
	  * 上面代码在addActionListener方法中定义了一个实现了ActionListener接口的匿名内部类，代码中
	  * new ActionListener {override def actionPerformed(event: ActionEvent)...
	  * 这部分称为样板代码，即在任何实现该接口的类中都需要这样用，重复性较高，
	  * 由于ActionListener接口只有一个actionPerformed方法，它被称为simple abstract method(SAM)
	  * SAM转换是指只给addActionListener方法传递一个参数
	  * button.addActionListener((event:ActionEvent)=>counter+=1)
	  * //并提供一个隐式转换，我们后面会具体讲隐式转换
	  * implict def makeAction(action:(event:ActionEvent)=>Unit){
	  * new ActionListener{
	  * override def actionPerformed(event:ActionEvent){action(event)}
	  * }
	  * 这样的话，在进行GUI编程的时候，可以省略非常多的样板代码，使代码更简洁。
	  */
}

/**
  * 4. 函数柯里化
  * 在函数与闭包那一节中，我们定义了下面这样的一个函数
  */
object CurryDemo {

	//mutiplyBy这个函数的返回值是一个函数
	//该函数的输入是Doulbe，返回值也是Double
	def multiplyBy(factor: Double) = (x: Double) => factor * x

	//返回的函数作为值函数赋值给变量
	val x = multiplyBy(10)
	//变量x现在可以直接当函数使用
	x(50)

	//上述代码可以像这样使用：
	def multiplyBy2(factor: Double) = (x: Double) => factor * x

	//这是高阶函数调用的另外一种形式
	multiplyBy2(10)(50)

	//那函数柯里化(curry）是怎么样的呢？其实就是将multiplyBy函数定义成如下形式
	def multiplyBy3(factor: Double)(x: Double) = x * factor

	//即通过(factor:Double)(x:Double)定义函数参数，该函数的调用方式如下：
	//柯里化的函数调用方式
	multiplyBy2(10)(50)
	//但此时它不能像def multiplyBy(factor:Double)=(x:Double)=>factor*x函数一样，可以输入单个参数进行调用
	//multiplyBy2(10)
	//错误提示函数multiplyBy缺少参数，如果要这么做的话，需要将其定义为偏函数
	//multiplyBy2(10) _
}

/**
  * 5. 部分应用函数
  *
  * 在数组那一节中，我们讲到,Scala中的数组可以通过foreach方法将其内容打印出来，
  * 代码如下：
  * Array("Hadoop","Hive","Spark")foreach(x=>println(x))
  * 上面的代码等价于下面的代码
  * def print(x:String)=println(x)
  * Array("Hadoop","Hive","Spark")foreach(print)
  *
  * 那什么是部分应用函数呢，所谓部分应用函数就是指，
  * 当函数有多个参数，而在我们使用该函数时我们不想提供所有参数（假设函数有3个函数），只提供0~2个参数，
  * 此时得到的函数便是部分应用函数，定义上述print函数的部分应用函数代码如下：
  */
object PartDef {
	//定义print的部分应用函数
	val p = print _
	Array("Hadoop", "Hive", "Spark") foreach (p)
	Array("Hadoop", "Hive", "Spark") foreach (print _)

	// 在上面的简化输出代码中，下划线_并不是占位符的作用，而是作为部分应用函数的定义符。
	// 前面我演示了一个参数的函数部分应用函数的定义方式，
	// 现在我们定义一个多个输入参数的函数，代码如下：

	//定义一个求和函数
	def sum(x: Int, y: Int, z: Int) = x + y + z

	//不指定任何参数的部分应用函数
	val s1 = sum _
	s1(1, 2, 3)
	//res91: Int = 6

	//指定两个参数的部分应用函数
	val s2 = sum(1, _: Int, 3)
	s2(2)
	//res92: Int = 6

	//指定一个参数的部分应用函数
	val s3 = sum(1, _: Int, _: Int)
	s3(2, 3)

	//res93: Int = 6
	/**
	  * 在函数柯里化那部分，我们提到柯里化的multiplyBy函数输入单个参数，
	  * 它并不会像没有柯里化的函数那样返回一个函数，而是会报错，
	  * 如果需要其返回函数的话，需要定义其部分应用函数，
	  * 代码如下：
	  */
	//定义multiplyBy函数的部分应用函数,它返回的是一个函数
	def multiplyBy(factor: Double)(x: Double) = x * factor

	val m = multiplyBy(10) _
	m(50)
	//res94: Double = 500.0
}