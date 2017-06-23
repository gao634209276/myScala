package chapter3

/**
  * 排序
  */
object SortDemo {

	def main(args: Array[String]): Unit = {
		val lst = List(2, 1, 3, 4, 5, 10, 6)
		val newLst = insertSort(lst, Nil)
		println(newLst._2)
	}


	/*递归插入,依次将旧链表的首元素用插入排序法插入新链表

	*当旧链表为空时，返回(nil,新链表)的元组。

	*/
	def insertSort(from: List[Int], to: List[Int]): (List[Int], List[Int]) = from match {
		case Nil => (from, to)
		case head :: tail => insertSort(tail, insertList(to, head))
	}


	/** 将一个元素用插入排序法插入链表，返回插入新元素后的链表 */
	def insertList(lst: List[Int], e: Int): List[Int] = lst match {
		case Nil => List(e)
		case _ => lst.span {
			_ < e
		} match {
			case (x, y) => (x :+ e) ++ y
		}
	}

}
