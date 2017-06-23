package chapter1.array

object ArrayDemo {
	def main(args: Array[String]) {

		//定义一个长度为10的数值数组
		val numberArray = new Array[Int](10)
		//定义一个长度为10的String类型数组
		val strArray = new Array[String](10)
		//可以看出：复杂对象类型在数组定义时被初始化为null，数值型被初始化为0
		//数组元素赋值
		strArray(0) = "First Element"
		//需要注意的是，val strArray=new Array[String](10)
		//这意味着strArray不能被改变，但数组内容是可以改变的

		//另一种定长数组定义方式
		//这种调用方式其实是调用其apply方法进行数组创建操作
		val strArray2 = Array("First", "Second")

		//要使用ArrayBuffer，先要引入scala.collection.mutable.ArrayBuffer
		import scala.collection.mutable.ArrayBuffer
		//创建String类型ArrayBuffer数组缓冲
		val strArrayVar = ArrayBuffer[String]()
		//+=意思是在尾部添加元素
		strArrayVar += "Hello"
		//+=后面还可以跟多个元素的集合
		//注意操作后的返回值
		strArrayVar +=("World", "Programmer")
		//显示完整数组内容
		//strArrayVar
		//++=用于向数组中追加内容，++=右侧可以是任何集合
		//追加Array数组
		strArrayVar ++= Array("Wllcome", "To", "XueTuWuYou")
		//追加List
		strArrayVar ++= List("Wellcome", "To", "XueTuWuYou")
		//删除末尾n个元素
		strArrayVar.trimEnd(3)

		//创建整型数组缓冲
		var intArrayVar = ArrayBuffer(1, 1, 2)
		//在数组索引为0的位置插入元素6
		intArrayVar.insert(0, 6)

		//在数组索引为0的位置插入元素7,8,9
		intArrayVar.insert(0, 7, 8, 9)
		//从索引0开始，删除4个元素
		intArrayVar.remove(0, 4)
		//转成定长数组
		val resl = intArrayVar.toArray
	}

}
