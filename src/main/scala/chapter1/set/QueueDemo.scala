package chapter1.set

/**
  * Created by hadoop on 16-10-2.
  */
object QueueDemo {
	var queue = scala.collection.immutable.Queue(1, 2, 3)
	//出队
	queue.dequeue
	//入队
	queue.enqueue(4)
	//res40: scala.collection.immutable.Queue[Int] = Queue(1, 2, 3, 4)

	//mutable queue
	var queue2 = scala.collection.mutable.Queue(1, 2, 3, 4, 5)
	//queue: scala.collection.mutable.Queue[Int] = Queue(1, 2, 3, 4, 5)

	//入队操作
	queue2 += 5
	//res43: scala.collection.mutable.Queue[Int] = Queue(1, 2, 3, 4, 5, 5)

	//集合方式
	queue2 ++= List(6, 7, 8)
	//res45: scala.collection.mutable.Queue[Int] = Queue(1, 2, 3, 4, 5, 5, 6, 7, 8)
}
