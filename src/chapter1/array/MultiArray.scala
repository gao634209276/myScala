package chapter1.array

/**
  * Created by hadoop on 16-10-2.
  */
object MultiArray {
	//定义2行3列数组
	var multiDimArr = Array(Array(1, 2, 3), Array(2, 3, 4))
	//获取第一行第三列元素
	multiDimArr(0)(2)
	//多维数组的遍历
	for (i <- multiDimArr) println(i.mkString(","))
}
