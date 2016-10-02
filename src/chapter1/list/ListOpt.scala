package chapter1.list

/**
  * Created by hadoop on 16-10-2.
  */
object ListOpt extends App{
	val nums = 1 :: 2 :: 3 :: 4 :: Nil
	//List常用操作
	//判断是否为空
	nums.isEmpty
	//res108: Boolean = false

	//取第一个无素
	nums.head
	//res109: Int = 1

	//取除第一个元素外剩余的元素，返回的是列表
	nums.tail
	// List[Int] = List(2, 3, 4)

	//取列表第二个元素
	nums.tail.head

	// Int = 2

	//插入排序算法实现
	def isort(xs: List[Int]): List[Int] =
		if (xs.isEmpty) Nil
		else insert(xs.head, isort(xs.tail))

	def insert(x: Int, xs: List[Int]): List[Int] =
		if (xs.isEmpty || x <= xs.head) x :: xs
		else xs.head :: insert(x, xs.tail)

	//List连接操作
	List(1, 2, 3) ::: List(4, 5, 6)
	//res116: List[Int] = List(1, 2, 3, 4, 5, 6)

	//取除最后一个元素外的元素，返回的是列表
	nums.init
	//res117: List[Int] = List(1, 2, 3)

	//取列表最后一个元素
	nums.last
	//res118: Int = 4

	//列表元素倒置
	nums.reverse
	//res119: List[Int] = List(4, 3, 2, 1)

	//一些好玩的方法调用
	nums.reverse.reverse == nums
	//res120: Boolean = true

	nums.reverse.init
	//res121: List[Int] = List(4, 3, 2)

	nums.tail.reverse
	//res122: List[Int] = List(4, 3, 2)

	//丢弃前n个元素
	nums drop 3
	//res123: List[Int] = List(4)

	nums drop 1
	//res124: List[Int] = List(2, 3, 4)

	//获取前n个元素
	nums take 1
	//res125: List[Int] = List(1)

	nums.take(3)
	//res126: List[Int] = List(1, 2, 3)

	//将列表进行分割
	nums.splitAt(2)
	//res127: (List[Int], List[Int]) = (List(1, 2),List(3, 4))

	//前一个操作与下列语句等同
	(nums.take(2), nums.drop(2))
	//res128: (List[Int], List[Int]) = (List(1, 2),List(3, 4))

	//Zip操作
	val nums2 = List(1, 2, 3, 4)
	//nums: List[Int] = List(1, 2, 3, 4)

	val chars = List('1', '2', '3', '4')
	//chars: List[Char] = List(1, 2, 3, 4)

	//返回的是List类型的元组(Tuple）
	nums2 zip chars
	//res130: List[(Int, Char)] = List((1,1), (2,2), (3,3), (4,4))

	//List toString方法
	nums2.toString
	//res131: String = List(1, 2, 3, 4)

	//List mkString方法
	nums2.mkString
	//res132: String = 1234

	//转换成数组
	nums2.toArray
	//res134: Array[Int] = Array(1, 2, 3, 4)
	val res = List('1', '2', '3', '4').map(_.toString)
	res.foreach(print)
}

