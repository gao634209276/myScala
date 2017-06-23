package chapter1.set

/**
  * Created by hadoop on 16-10-2.
  */
object MapDemo {
	//直接初始化
	// ->操作符，左边是key,右边是value
	val studentInfo = Map("john" -> 21, "stephen" -> 22, "lucy" -> 20)
	//studentInfo: scala.collection.immutable.Map[String,Int] = Map(john -> 21, stephen -> 22, lucy -> 20)

	//immutable不可变，它不具有以下操作
	//studentInfo.clear()

	//创建可变的Map
	val studentInfoMutable = scala.collection.mutable.Map("john" -> 21, "stephen" -> 22, "lucy" -> 20)
	//studentInfoMutable: scala.collection.mutable.Map[String,Int] = Map(john -> 21, lucy -> 20, stephen -> 22)
	//mutable Map可变，比如可以将其内容清空
	studentInfoMutable.clear()

	//studentInfoMutable
	//res3: scala.collection.mutable.Map[String,Int] = Map()

	//遍历操作1
	for (i <- studentInfoMutable) println(i)

	//遍历操作2
	studentInfoMutable.foreach(e => {
		val (k, v) = e;
		println(k + ":" + v)
	})
	//遍历操作3
	studentInfoMutable.foreach(e => println(e._1 + ":" + e._2))

	//定义一个空的Map
	val xMap = new scala.collection.mutable.HashMap[String, Int]()
	//xMap: scala.collection.mutable.HashMap[String,Int] = Map()

	//往里面填充值
	xMap.put("spark", 1)
	//res12: Option[Int] = None

	//xMap
	//res13: scala.collection.mutable.HashMap[String,Int] = Map(spark -> 1)

	//判断是否包含spark字符串
	xMap.contains("spark")
	//res14: Boolean = true

	//-> 初始化Map，也可以通过 ("spark",1)这种方式实现(元组的形式）
	val xMap1 = scala.collection.mutable.Map(("spark", 1), ("hive", 1))
	//xMap: scala.collection.mutable.Map[String,Int] = Map(spark -> 1, hive -> 1)

	//"spark" -> 1
	//res18: (String, Int) = (spark,1)

	//获取元素
	xMap.get("spark")
	//res19: Option[Int] = Some(1)

	xMap.get("SparkSQL")
	//res20: Option[Int] = None
}
