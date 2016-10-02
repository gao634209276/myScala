package chapter2

/**
  * 利用IDE开发scala应用程序时，
  * 在运行程序时必须指定main方法作为程序的入口，
  * 例如：
  * object Student {
  * //必须定义mian方法作为程序的入口才能执行
  * def main(args: Array[String]): Unit = {
  * val s1=Student("john",29)
  * println(s1.name)
  * println(s1.age)
  * }
  * }
  * 除了这种方式之外，scala还提供了一种机制，即通过扩展App，
  * 在Scala IDE for Eclipse里是通过new->scala app方式创建的
  * 也可在代码直接指定：
  */
//扩展App后，程序可以直接运行，而不需要自己定义main方法，代码更简洁
//App其实是一种trait，它帮助我们定义了main方法。
object AppDemo extends App {
	println("App Demo")
}