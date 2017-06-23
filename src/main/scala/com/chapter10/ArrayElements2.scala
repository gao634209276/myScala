package com.chapter10

/**
  * 10.6 定义参数化字段
  *
  */
//同时定义同名的参数和字段的一种简写方式
class ArrayElements2(val contents: Array[String]) extends Element {

}

//再来一个例子
class Cat {
  val dangerous = false
}

//Tiger和Tiger2的意思是一样的,Tiger类中没有其他内容，省略了{}
class Tiger(override val dangerous: Boolean, private var age: Int) extends Cat

class Tiger2(p1: Boolean, p2: Int) extends Cat {
  override val dangerous = p1;
  private var age = p2;
}