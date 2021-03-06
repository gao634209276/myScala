package chapter1.set

/**
  * Set（集）是一种不存在重复元素的集合，它与数学上定义的集合是对应的
  */
object SetDemo {

	val numsSet = Set(3.0, 5)

	//numsSet: scala.collection.mutable.Set[Double] = Set(5.0, 3.0)
	//向集中添加一个元素，同前一讲中的列表和数组不一样的是
	//Set在插入元素时并不保元素的顺序
	//默认情况下，Set的实现方式是HashSet实现方式，
	//集中的元素通过HashCode值进行组织
	def main(args: Array[String]) {

		numsSet + 6
		//res20: scala.collection.mutable.Set[Double] = Set(5.0, 6.0, 3.0)

		//遍历集
		for (i <- numsSet) println(i)

		//如果对插入的顺序有着严格的要求，则采用scala.collection.mutalbe.LinkedHashSet来实现
		val linkedHashSet = scala.collection.mutable.LinkedHashSet(3.0, 5)
		//linkedHashSet: scala.collection.mutable.LinkedHashSet[Double] = Set(3.0, 5.0)

		linkedHashSet + 6
		//res26: scala.collection.mutable.LinkedHashSet[Double] = Set(3.0, 5.0, 6.0)  numsSet + 6
		for (i <- linkedHashSet) println(i)
	}
}
