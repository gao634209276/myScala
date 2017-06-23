package com.chapter10
/**
  * 10.7 调用超类构造器
  * 10.8 使用override修饰符
  *      Scala要求：若子类成员所有重写了父类的具体成员必须带有override修饰符；
  *      若成员实现的是同名的抽象成员时，则这个修饰符是可选的。若成员并未实现或
  *      重写基类里的成员，则禁用这个操作符。
  * 10.9 多态和绑定
  */
class LineElement(s: String) extends ArrayElement(Array(s)){
  override val width = s.length
  override val height = 1

  /*override def demo() { //超类该方法已被final修饰，编译不过
      println("ArrayElement")
    }*/
}