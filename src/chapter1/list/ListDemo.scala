package chapter1.list

/**
  * Created by hadoop on 16-10-2.
  */
object ListDemo {

	// List类型定义及List的特点
	object ListDemo {
		//字符串类型List
		val fruit = List("Apple", "Banana", "Orange")
		//前一个语句与下面语句等同
		val fruit1 = List.apply("Apple", "Banana", "Orange")

		//数值类型List
		val nums = List(1, 2, 3, 4, 5)
		//nums: List[Int] = List(1, 2, 3, 4, 5)

		//多重List，List的子元素为List
		val diagMatrix = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
		//diagMatrix: List[List[Int]] = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))

		//遍历List
		for (i <- nums) println("List Element: " + i)

		//1.List一但创建，其值不能被改变
		//2.List具有递归结构（Recursive Structure),例如链表结构
		var listStr: List[Object] = List("This", "Is", "Covariant", "Example")
		//空的List，其类型为Nothing,Nothing在Scala的继承层次中的最低层
		//即Nothing是任何Scala其它类型如String,Object等的子类
		var listStr2 = List()
		var listStr3: List[String] = List()

		//2 List常用构造方法
		//采用::及Nil进行列表构建
		val nums2 = 1 :: (2 :: (3 :: (4 :: Nil)))
		//List[Int] = List(1, 2, 3, 4)

		//由于::操作符的优先级是从右往左的，因此上一条语句等同于下面这条语句
		val nums3 = 1 :: 2 :: 3 :: 4 :: Nil
		// List[Int] = List(1, 2, 3, 4)
	}


}
