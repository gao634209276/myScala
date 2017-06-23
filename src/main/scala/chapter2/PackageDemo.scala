/**
  * 包的作用与定义
  * 将代码组织到chapter2.demo包中
  * package chapter2.demo
  * 上述包也可通过下面的包定义方式进行：
  * package chapter2{
  * package demo{
  * class Teacher{
  * }
  * }
  * }
  * 在程序的任何地方都可以通过chapter2.demo.Teacher来使用Teacher这个类
  * 将代码放在默认包下面，但编码后的字节码文件被自动组织到chapter2.demo文件夹当中
  * 也就是说:我们可以在任何地方进行包中类的定义，scala帮助我们进行自动文件组织
  */

/**
  * 包的作用域与引入（import）的使用方法
  *
  * 下面的代码给出了包的作用域和引入的使用方法
  */
package chapter2 {
	package demo {

		class Teacher4(var name: String) {
			//演示包的访问规则
			//内层包可以访问外层包中定义的类或对象，无需引入
			def printName() = {
				Utils.toString(name)
			}
		}

	}

	//在包chapter2下创建了一个Utils单例
	object Utils {
		def toString(x: String) {
			println(x)
		}

		//外层包无法直接访问内层包，下面这一行代码编译通不过
		//def getTeacher():Teacher=new Teacher("john")
		//如果一定要使用的话，可以引入包
		import chapter2.demo.Teacher4

		def getTeacher(): Teacher4 = new Teacher4("john")
	}

}

object PackageDemo {
	//scala允许在任何地方进行包的引入，_的意思是引入该包下的所有类和对象
	import chapter2.demo._
	import chapter2._

	def main(args: Array[String]): Unit = {
		Utils.toString(new Teacher4("john").name)
		new Teacher4("john").printName()
	}
}
