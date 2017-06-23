package chapter1.list

/**
  * Created by hadoop on 16-10-2.
  */
//List伴生对象方法
object ListObject {
	//apply方法
	List.apply(1, 2, 3)
	//res139: List[Int] = List(1, 2, 3)

	//range方法，构建某一值范围内的List
	List.range(2, 6)
	//res140: List[Int] = List(2, 3, 4, 5)

	//步长为2
	List.range(2, 6, 2)
	//res141: List[Int] = List(2, 4)

	//步长为-1
	List.range(2, 6, -1)
	//res142: List[Int] = List()

	List.range(6, 2, -1)
	//res143: List[Int] = List(6, 5, 4, 3)

	//构建相同元素的List
	val res144 = List.make(5, "hey")
	//res144: List[String] = List(hey, hey, hey, hey, hey)

	//unzip方法
	//List.unzip(res145)
	//res146: (List[Int], List[Char]) = (List(1, 2, 3, 4),List(1, 2, 3, 4))

	//list.flatten，将列表平滑成第一个无素
	val xss = List(List('a', 'b'), List('c'), List('d', 'e'))
	//xss: List[List[Char]] = List(List(a, b), List(c), List(d, e))
	xss.flatten
	//res147: List[Char] = List(a, b, c, d, e)

	//列表连接
	List.concat(List('a', 'b'), List('c'))
	//res148: List[Char] = List(a, b, c)
}