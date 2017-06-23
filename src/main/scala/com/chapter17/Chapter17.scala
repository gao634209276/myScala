package com.chapter17

/**
  * 第17章 集合类型
  */
object Chapter17 {
  /**
    * 17.1 集合库概览
    * 1、Iterable是可变和不可变序列(Seq)、集(Set)以及映射(Map)的超特质。
    * 2、序列是有序的集合，例如数组和列表。集可以通过==方法确定对每个对象最多只包含一个。
    *    映射则包含了键值映射关系的集合。
    * 3、Iterable包含十几个有用的具体方法，elements是Iterable唯一的抽象方法。
    * 4、Iterable与Iterator之间的差异在于特质Iterable指代的是可以被枚举的类型(如集合类型)，
    *    而特质Iterator是用来执行枚举操作的机制。
    * 5、Iterator只能用一次，用完之后如果还想用，需要重新调用elements方法获得新的Iterator。
    * 6、Iterator提供的具体方法都使用了next和hasNext抽象方法的实现。
    */

  /**
    * 17.2 序列
    * 序列是继承自特质Seq的类。
    * 1、列表(List)
    * 列表可以支持在列表头部快速添加和删除，但是不能提供对任意索引值的快速访问，因为这种实现
    * 需要线性枚举整个列表
    * 2、列表缓存(ListBuffer)
    * ListBuffer是可变对象，它可以更高效地通过添加元素的方式构建列表。
    * 元素的添加使用+=操作符，前缀使用+:操作符
    * 3、数组(Array)
    * 数组保存一组元素序列并可以用基于零的索引高效访问(无论获取还是添加)处于任意位置的元素。
    * 4、数组缓存(ArrayBuffer)
    * ArrayBuffer与数组类似，只是额外还允许你在序列的开始或结束的地方添加和删除元素
    * 5、队列(Queue)
    * 如果要先进先出序列可以使用Queue,Scala提供了可变和不可变的Queue
    * 6、栈
    * 如果需要后进先出的序列，可以使用Stack。同样分为可变和不可变。
    * 元素的推入使用push、弹出使用pop、获取栈顶元素而不移除用top
    * 7、字符串(轻RichString隐式转换)
    */
  //1、列表
  def fun1() {
    val colors = List("red", "blue", "green")
    println( colors.head )
    println( colors.tail )
    //向结尾添加对象
    //val colors2 = ("orange" ::  colors.reverse ).reverse
    val colors3 =  List("red", "blue", "green") ::: List("orange")
    //println(colors2)
    println(colors3)
    // colors += "white" //error
  }

  //2、列表缓存
  def fun2() {
    import scala.collection.mutable.ListBuffer
    val buf = ListBuffer[Int]()
    buf += 1
    buf += 2
    val buf2 = 3 +: buf
    println(buf)     //ListBuffer(1, 2)
    println(buf2)    //ListBuffer(3, 1, 2)
  }

  //3、数组
  //4、数组缓存
  def fun4() {
    import scala.collection.mutable.ArrayBuffer
    val buf = new ArrayBuffer[Int]()
    buf += 12
    buf += 15
    val buf2 = 18 +: buf
    println(buf)           //ArrayBuffer(12, 15)
    println(buf2)          //ArrayBuffer(18, 12, 15)
  }

  //5、队列
  def fun5() {
    import scala.collection.{immutable, mutable}
    val mutQue = mutable.Queue[Int]()
    mutQue += 1
    mutQue += 2
    mutQue += 3
    mutQue.enqueue(4)
    println(mutQue) //Queue(1, 2, 3, 4)
    mutQue.dequeue()
    println(mutQue) //Queue(2, 3, 4)
    val immutQue = immutable.Queue[Int](1,2,3)
    immutQue.enqueue(4)
    immutQue.enqueue(List(5,6))
    println(immutQue) //Queue(1, 2, 3)
    immutQue.dequeue
    println(immutQue) //Queue(1, 2, 3)

  }

  //6、栈
  def fun6() {
    import scala.collection.mutable.Stack
    val stack = new Stack[Int]()
    stack.push(1, 2, 3)
    println(stack)
    stack.push(4)       //Stack(3, 2, 1)
    println(stack)      //Stack(3, 2, 1)
    println(stack.top)  //4
    stack.pop()
    println(stack)      //Stack(3, 2, 1)
  }

  //7、字符串(轻RichString隐式转换)
  def fun7() {
    //字符串s调用了exits方法。而String类本身并没有定义“exists”的方法.因此Scala编译器
    //会把s隐式转换为含有这个方法的RichString类。exists方法把字符串看作Seq[Char]
    def hasUpperCase(s: String) = s.exists { _.isUpper }
    println(hasUpperCase("Hello"))  //true
    println(hasUpperCase("hello"))  //false
  }

  /**
    * 17.3 集和映射
    * 1、使用集
    * 集的关键特性是它可以使用对象的==操作检查，确保任何时候每个对象只在集中保留
    * 最多一个副本。
    * 2、使用映射
    * 映射可以用来把值与集合中的元素联系起来。映射的使用与数组接近，只是你可以用任何
    * 类型的键，而不仅是使用从0开始计数的整数做索引。
    * 请注意，在创建映射的时候，必须指定两个类型。第一个类型是用来定义映射的键，第二个
    * 用来定义值。
    * 3、有序的集和映射
    * Scala的集合库提供了SortedSet和SortedMap特质。这两个特质分别由TreeSet和TreeMap
    * 实现它们能有序地保存元素(TreeSet)和键(TreeMap)。这些类只有不可变类型的版本。
    * 4、同步集和映射
    * scala.collection.mutable.SynchronizedMap已经过时，改用java.util.concurrent.ConcurrentHashMap
    * (Synchronization via traits is deprecated as it is inherently unreliable.
    * Consider java.util.concurrent.ConcurrentHashMap as an alternative)
    */
  //使用集
  def fun8() {
    //创建不可变集
    val nums = Set(1, 2, 3)
    println(nums)           //Set(1, 2, 3)
    //添加元素
    println(nums + 5)       //Set(1, 2, 3, 5)
    //删除元素
    println(nums - 3)       //Set(1, 2)
    //添加多个元素
    println(nums ++ List(4, 5))  //Set(5, 1, 2, 3, 4)
    //删除多个元素
    println(nums -- List(1, 2))  //Set(3)
    //获得交接
    println(nums & Set(2, 3, 5, 7)) //Set(2, 3)
    println(nums.size)  // 3
    println(nums.contains(3)) //true

    //可变集
    import scala.collection.mutable
    val text = "See Spot run.Run,Spot,Run!"
    val wordsArray = text.split("[ !,. ]+")
    //创建空可变集
    val wordsSet = mutable.Set.empty[String]
    for(word <- wordsArray) {
      wordsSet += word.toLowerCase()
    }
    println(wordsSet)  //Set(see, run, spot)
    wordsSet += "the"
    println(wordsSet)  //Set(see, the, run, spot)
    wordsSet -= "the"
    println(wordsSet)  //Set(see, run, spot)
    wordsSet ++= List("do", "re", "mi")
    println(wordsSet)  //Set(re, see, do, mi, run, spot)
    wordsSet --= List("do", "re")
    println(wordsSet)  //Set(see, mi, run, spot)
    //清空集
    wordsSet.clear()
    println(wordsSet)  //Set()
  }

  //使用映射
  def fun9() {
    import scala.collection.mutable
    //创建一个key为String，value为Int的Map
    val map = mutable.Map.empty[String, Int]
    map("hello") = 1
    map("world") = 2
    println(map)          //Map(world -> 2, hello -> 1)
    map("world") = 10
    println(map)          //Map(world -> 10, hello -> 1)
    //读取映射
    println(map("world")) //10
    println(map.contains("Hi")) //false

    val text = "See Spot run.Run,Spot,Run!"
    def countWords(text: String) = {
      val words = text.split("[ !,. ]+")
      val map = mutable.Map.empty[String, Int]
      for(word <- words) {
        var lower = word.toLowerCase()
        if(map.contains(lower))
          map(lower) = map(lower) + 1
        else
          map(lower) = 1
      }
      map
    }
    println(countWords(text)) //Map(spot -> 2, see -> 1, run -> 3)

    //映射的常见操作
    //创建不可变映射
    val nums = Map("i"->1, "ii"->2)
    println(nums + ("vi"->6))  //Map(i -> 1, ii -> 2, vi -> 6)
    println(nums - ("i"))      //Map(ii -> 2)
    println(nums ++ List("iii"->3, "v"->5)) //Map(i -> 1, ii -> 2, iii -> 3, v -> 5)
    println(nums -- List("iii", "ii"))      //Map(i -> 1)
    println(nums.keys)         //Set(i, ii)
    println(nums.keySet)       //Set(i, ii)
    nums.keysIterator.foreach { x => print(x + " ") }  //i ii
    println()
    println(nums.isEmpty) //false
  }

  //有序的集和映射
  def fun10() {
    import scala.collection.immutable.TreeSet
    val ts = TreeSet(9, 3, 1, 8, 0, 2, 7, 4, 6, 5)
    println(ts)  //TreeSet(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    val cs = TreeSet('f', 'u', 'a') //TreeSet(a, f, u)
    println(cs)

    import scala.collection.immutable.TreeMap
    val tm = TreeMap("2" -> 1, "1" -> 2, "12" ->2)
    println(tm) //Map(1 -> 2, 12 -> 2, 2 -> 1)
    val tm2 = TreeMap(2 -> "hello", 1 -> "tom", 12 ->"Jreey")
    println(tm2)//Map(1 -> tom, 2 -> hello, 12 -> Jreey)
  }

  /**
    * 17.4 可变(mutable)集合 vs. 不可变(immutable)集合
    * 有些问题使用可变集合较好，有些问题使用不可变集合较好。如果拿不定主意可以
    * 首先使用不可变集合，之后实在需要再改成可变集合。
    */

  /**
    * 17.5 初始化集合
    * 1、数组与列表之间的转换
    * 略
    * 2、集和映射的可变与不可变互转
    * 可变集或映射转换成不可变类型，或反向转换可以这么做：
    * 想把当前使用的可变集合转换成不可变集合，可以先创建空不可变集合，然后
    * 把可变集合的元素用 ++ 操作符添加进去。
    */
  def fun11() {
    import scala.collection.{immutable, mutable}
    //将不可变集转为可变集
    val treeSet = immutable.TreeSet("blue", "green", "red")
    val mutaSet = mutable.Set.empty[String] ++ treeSet
    println(mutaSet)  //Set(red, blue, green)

    //可变映射转为不可映射
    val mutaMap = mutable.Map.empty[String, Int]
    mutaMap("Hello") = 1;
    mutaMap("world") = 2;
    val immutaMap = immutable.Map.empty ++ mutaMap
  }

  /**
    * 17.6 元组
    * 元组可以保存不同类型的对象
    * 由于元组可以组合不同类型的对象，所以它不能继承自Iterable
    * 元组常用来返回方法的多个值
    */
  def longestWord(words: Array[String]) = {
    var tuple = (0, words(0))
    val wordTail = words.tail
    for(i <- 1 until words.length) {
      if(words(i).length > tuple._2.length)
        tuple = (i,words(i))
    }
    tuple
  }

  def fun14() {
    val ws = Array("hello" , "kitty","C罗", "tomyyy" ,"t") //"C罗".length 是 2
    println(longestWord(ws))

  }
  def main(args: Array[String]): Unit = {
  }
}
