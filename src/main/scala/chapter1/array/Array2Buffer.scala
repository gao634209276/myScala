package chapter1.array

/**
  * Created by hadoop on 16-10-2.
  */
object Array2Buffer {

	import scala.collection.mutable.ArrayBuffer

	var intArrayVar = ArrayBuffer(1, 1, 2)
	//生成新的数组，原数组不变
	//缓冲数据转换后产生的仍然是缓冲数组
	var intArrayVar2 = for (i <- intArrayVar) yield i * 2
	//定长数组转转后产生的仍然是定长数组，原数组不变
	var intArrayNoBuffer = Array(1, 2, 3)
	var intArrayNoBuffer2 = for (i <- intArrayNoBuffer) yield i * 2
	//加入过滤条件
	var intArrayNoBuffer3 = for (i <- intArrayNoBuffer if i >= 2) yield i * 2
}
