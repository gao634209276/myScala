package chapter1.set

/**
  * Created by hadoop on 16-10-2.
  */
object StackDemo {
	//mutable Stack
	import scala.collection.mutable.Stack

	//new 创建方式
	val stack = new Stack[Int]
	//stack: scala.collection.mutable.Stack[Int] = Stack()

	//Apply创建方式
	val stack1 = Stack(1, 2, 3)
	//stack1: scala.collection.mutable.Stack[Int] = Stack(1, 2, 3)

	//出栈
	stack1.top
	//res55: Int = 1

	//入栈
	stack.push(1)
	//res57: stack.type = Stack(1)
	//入栈
	stack.push(2)
	//res58: stack.type = Stack(2, 1)
	//出栈
	stack.top
	//res59: Int = 2

	//stack
	//res60: scala.collection.mutable.Stack[Int] = Stack(2, 1)
}
