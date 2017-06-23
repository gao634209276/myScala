package com.chapter16

/**
  * 第16章 列表
  */
object Chapter16 {
  /**
    * 16.1 列表字面量
    * 列表与数组非常相似，不过有两点重要的差别：
    * 1、列表是不可变的。也就是说，不能通过赋值改变列表元素。
    * 2、列表具有递归结构(例如：linked list)，而数组是连续的。
    *
    */
  def fun1() {
    val fruit = List("apple", "orange", "pears")
    val nums = List(1, 2, 3, 4)
    val diag3 =
      List(
        List(1, 0, 0),
        List(0, 1, 0),
        List(0, 0, 0)
      )
  }

  /**
    * 16.2 List类型
    * 1、就像数组一样，列表是同质的：列表的所有元素都具有
    *    相同的类型。元素类型为T的列表写成List[T]。
    * 2、Scala里的列表类型是协变的。这意味着对于每一对类型S和T来说，
    *    如果S是T的子类型，那么List[S]也是List[T]的子类型。
    * 3、注意空列表的类型是List[Noting]
    */

  /**
    * 16.3 构造列表
    * 1、所有的构造列表都是由两个基础块Nil和::构造出来的
    */
  def fun2() {
    val fruit = "apples" :: "oranges" :: "pears" :: Nil
    val nums = 1 :: 2 :: 3 :: 4 :: Nil
    val diag3 = (1 :: 0 :: 0 :: Nil) ::
      (0 :: 1 :: 0 :: Nil) ::
      (0 :: 0 :: 1 :: Nil) :: Nil
    println(fruit)
    println(nums)
    println(diag3)
  }

  /**
    * 16.4 列表的基本操作
    * head  返回列表的第一个元素
    * tail  返回除第一个之外所有元素组成的列表
    * isEmpty 如果列表为空，则返回真
    */
  //插入排序算法，将列表升序排列
  def sort(list: List[Int]):List[Int] =  {
    if(list.isEmpty) Nil
    else insert(list.head, sort(list.tail))
  }

  def insert(x: Int, list: List[Int]): List[Int] = {
    if(list.isEmpty || x <= list.head) x::list
    else list.head :: (insert(x,list.tail))
  }

  /**
    * 列表模式
    */
  def fun3() {
    val fruit = "apples" :: "oranges" :: "pears" :: Nil
    //只有知道List的个数时才能这么写
    val List(a, b, c) = fruit
    // 打印出：apples oranges pears
    println(a + " " +b + " " +c)

    val a1 :: a2 :: a3  = fruit
    //打印出：apples oranges List(pears)
    println(a1 + " " + a2 + " " +a3)
  }

  //用模式匹配改写插入排序算法
  def sort2(xs: List[Int]): List[Int] = xs match {
    case Nil => Nil
    case y :: ys => insert(y,sort2(ys))
  }
  def insert2(x: Int, xs: List[Int]): List[Int] = xs match {
    case List()  => List()
    case y :: ys =>
      if(x <= y ) x :: xs
      else y :: insert2(x, ys)
  }

  /**
    * 16.6 List类的一阶方法
    * 一阶方法是指不以函数作为入参的方法
    * 1、连接列表。连接操作写作“:::”
    * 2、分治原则
    *    列表中的很多算法首先是用模式匹配把输入列表拆分为更简单的样本。这是
    *    "分治原则"里所说的“分”;然后根据每个样本构建结果。如果结果是非空列表，
    *    那么一块块部件将通过同样的递归遍历算法构建出来。这就是原则中所说的“治”
    * 3、计算列表长度：length方法
   　 *        为了计算列表长度，需要遍历整个列表，这比起数组的length方法要费事很多。
   　 * 　　所以判断列表是否为空应该使用 list.isEmpty 方法，而不要用 list.length == 0 来判断
    * 4、访问列表的尾部：init 方法和 last 方法
   　 * 　　last返回列表的最后一个元素，init返回除了列表最后一个元素以外的余下的列表。
    　* 　　head返回列表的第一个元素，tail返回除了列表第一个元素以外余下的列表。
    * 5、反转列表：reverse方法
    * 6、前缀与后缀：drop、take 和 splitAt
    　* 　　list.take(n):返回列表的前n个元素，如果n大于list.length则返回整个列表。
    *    list.drop(n):返回列表中除了前n个元素之外的元素。
    * 7、元素选择apply方法和indices方法
    *   apply 方法获取列表中某个索引的值
    *   indices 方法有效索引值组成的列表
    * 8、zip 方法，zip操作可以把两个列表组成一个对偶列表
    * 9、显示列表：toString方法、mkString方法和addString方法
    *   addString将构建好的字符串添加到StringBuilder上
    * 10、转换列表：elements、toArray、copyToArray
    *   List转Array使用List类的toArray方法;
    *   Array转List使用Array类的toList方法;
    *   把；列表元素复制到目标数组的一段连续空间用CopyToArray方法。
    * 11、归并排序比插入排序更为高效
    */

  //1、连接列表
  def fun4() {
    val list = List(1,2) ::: List(3,4,5)
    val list2 = List(1,2) :: List(3,4,5)

    println(list)  //List(1, 2, 3, 4, 5)
    println(list2) //List(List(1, 2), 3, 4, 5)
  }

  //2、分治原则
  //用append实现连接操作:::
  def append[T](ll: List[T], rl: List[T]): List[T] = ll match{
    case Nil => rl
    case a :: ax => a :: append(ax,rl)
  }
  def fun5() {
    val list = List(1,2) ::: List(3,4,5) ::: List(6)
    val list2 = append(List(1,2),append(List(3,4,5),List(6)))
    println(list)  //List(1, 2, 3, 4, 5, 6)
    println(list2) //List(1, 2, 3, 4, 5, 6)
  }


  def fun6() {
    val list = List("a", "b", "c", "d", "e")
    println(list.length)     //5
    println(list.head)       //a
    println(list.last)       //e
    println(list.tail)       //List(b, c, d, e)
    println(list.init)       //List(a, b, c, d)
    println(list.reverse)    //List(e, d, c, b, a)
    println(list.take(2))    //List(a, b)
    println(list.drop(2))    //List(c, d, e)
    println(list.splitAt(2)) //(List(a, b),List(c, d, e))

    val list2 = List("a","b",null,"d")
    println(list2.apply(2)) //null
    println(list2(2))       //null
    println(list2(3))       //d
    println(list2(3))       //d
    println(list2.indices)  //Range(0, 1, 2, 3)

    val intList = List(1, 2 , 3)
    val charList = List('a', 'b', 'c', 'd')
    val zipList = intList zip charList
    println(zipList)         //返回List[(Int, Char)]型：List((1,a), (2,b), (3,c))

    println(list.toString)
    println(list.mkString("@"))     //List(a, b, c, d, e)
    val strb = new StringBuilder()  //a@b@c@d@e
    val strb2 = list.addString(strb,"{",";","}")
    println(strb2)                  //{a;b;c;d;e}

    val arry = Array("0","0","0","0","0","0","0","0","0","0","0")
    val myArry = list.toArray
    println(list.toArray)   //[Ljava.lang.String;@d1cfd52
    println(myArry.toList)  //List(a, b, c, d, e)
    println(arry.toList)    //List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    list.copyToArray(arry,3)
    arry.foreach { x => print(x + " ") } //0 0 0 a b c d e 0 0 0
  }

  /**
    * List类的高阶方法
    * 列表间映射：map、flatMap和foreach
    * 列表过滤：filter、partition、find、takeWhile、dropWhile和span
    * 列表论断：forall和exists (以下进度问题，测试略)
    * 折叠列表：/：和：\
    * 列表排序: sort
    *
    */
  //列表间映射：map、flatMap、foreach
  def fun7() {
    println( List(1,2,3) map { x => x + 1 } ) //List(2, 3, 4)
    var words = List("give","me","a","apple")
    println( words map (_.length) ) //List(4, 2, 1, 5)
    println( words map (_.toList) ) //List(List(g, i, v, e), List(m, e), List(a), List(a, p, p, l, e))
    println( words map (_.toList.reverse) ) //List(List(e, v, i, g), List(e, m), List(a), List(e, l, p, p, a))
    println( words map (_.toList.reverse.mkString) ) //List(evig, em, a, elppa)

    //println( words flatMap (_.length) )                //List(4, 2, 1, 5)
    println( words flatMap (_.toList) )                  //List(g, i, v, e, m, e, a, a, p, p, l, e)
    println( words flatMap (_.toList.reverse) )          //List(e, v, i, g, e, m, a, e, l, p, p, a)
    println( words flatMap (_.toList.reverse.mkString) ) //List(e, v, i, g, e, m, a, e, l, p, p, a)

    //val v = List.range(1, 5).flatMap { x => ??? }
    println( List.range(1, 5) ) //List(1, 2, 3, 4)
    println( List.range(1, 5).map { i => List.range(1, i) } )      //List(List(), List(1), List(1, 2), List(1, 2, 3))
    println( List.range(1, 5).flatMap { i => List.range(1, i) } )  //List(1, 1, 2, 1, 2, 3)

    //构建1 <= j < i = 5的(i, j)对偶
    println( List.range(1, 5).flatMap { i => List.range(1, i).map { j => (i, j) } } ) //List((2,1), (3,1), (3,2), (4,1), (4,2), (4,3))
    println( for(i <- 1 until 5; j <- 1 until i) yield(i, j) )                        //Vector((2,1), (3,1), (3,2), (4,1), (4,2), (4,3))
    println( for(i <- List.range(1, 5); j <- List.range(1, i)) yield(i, j) )          //List((2,1), (3,1), (3,2), (4,1), (4,2), (4,3))

    //foreach
    var sum = 0
    List(1,2,3,4).foreach {sum += _ }
    println(sum) //10

  }

  //列表过滤：filter、partition、find、takeWhile、dropWhile和span
  def fun8() {
    val list = List(1, 2, 3, 4, 5, 6, 7)
    println(list.filter { _ % 2 == 0 })  //List(2, 4)
    //list partition p 等价于 (list filter p, list filter (!p(_)))
    println(list.partition { _ % 2 == 0  })  //(List(2, 4, 6),List(1, 3, 5, 7))
    println(list.find { _ % 2 == 0 })  //Some(2)

    val list2 = List(1, 2, 3, -4, 5, 6, 7)
    println(list2.takeWhile { _ > 0 })  //List(1, 2, 3)
    println(list2.dropWhile { _ > 0 })  //List(-4, 5, 6, 7)
    //list span p 等价于 ()
    println(list2.span { _ > 0 })  //(List(1, 2, 3),List(-4, 5, 6, 7))

    /**
      * 16.8 List对象的方法 (以下进度问题，测试略)
      * 1、通过元素创建列表：List.apply
      * 2、创建数值范围:List.range
      * 3、创建统一的列表:List.make
      * 4、解除啮合列表：List.unzip
      * 5、链接列表：List.flatten、List.concat
      * 6、映射及测试配对列表：List.map2、List.forall2、List.exists2
      */

  }
  def main(args: Array[String]): Unit = {
    fun8
  }
}
