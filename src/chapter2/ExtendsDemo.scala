package chapter2

/**
  * 1 类的继承
  */

//Person类
class Person18(name: String, age: Int) {
}

//Student继承Person类
class StudentExtends(name: String, age: Int, var studentNo: String) extends Person18(name, age) {
}

object ExtendsDemo {
	def main(args: Array[String]): Unit = {
		val student = new StudentExtends("john", 18, "1024")
	}
}

/**
  * 等同于下面的Java代码
  * //Person类
  * class Person{
  * private String name;
  * private int age;
  * public Person(String name,int age){
  * this.name=name;
  * this.age=age;
  * }
  * }
  * //Student继承Person类
  * class Student extends Person{
  * private String studentNo;
  * public Student(string name,int age,String studentNo){
  * super(name,age);
  * this.sutdentNo=studentNo;
  * }
  * }
  */

/**
  * 2. 构造函数执行顺序
  * 下面的代码演示scala在继承的时候，构造函数的执行顺序
  */
//Person类
class Person19(name: String, age: Int) {
	println("Constructing Person")
}

//Student继承Person类
class Student2(name: String, age: Int, var studentNo: String) extends Person19(name, age) {
	println("Constructing Student")
}

object ExtendsDemo2 {
	def main(args: Array[String]): Unit = {
		//下面的语句执行时会打印下列内容
		//Constructing Person
		//Constructing Student
		//也就是说，构造Student这前，首先会调用Person的主构造方法
		val student = new Student2("john", 18, "1024")
	}
}

/**
  * 等同于下面的java代码
  * //Person类
  * class Person{
  * private String name;
  * private int age;
  * public Person(String name,int age){
  *
  * this.name=name;
  * this.age=age;
  * System.out.println("Constructing Person");
  * }
  * }
  *
  * //Student继承Person类
  * class Student extends Person{
  * private String studentNo;
  * public Student(string name,int age,String studentNo){
  * super(name,age);
  * this.sutdentNo=studentNo;
  * System.out.println("Constructing Student");
  * }
  * }
  */

/**
  * 3. 方法重写
  *
  * 方法重写指的是当子类继承父类的时候，
  * 从父类继承过来的方法不能满足子类的需要，子类希望有自己的实现，
  * 这时需要对父类的方法进行重写，方法重写是实现多态和动态绑定的关键。
  *
  * scala中的方法重写同java一样，也是利用override关键字标识重写父类的算法。
  * 下面的代码演示了方法重写如何实现
  */
class Person20(name: String, age: Int) {
	//println("Constructing Person")
	def walk(): Unit = println("walk like a normal person")
}

//Student继承Person类
class Student3(name: String, age: Int, var studentNo: String) extends Person20(name, age) {
	//println("Constructing Student")
	override def walk(): Unit = {
		super.walk() //调用父类的walk方法
		println("walk like a elegant swan") //增加了自己的实现
	}
}

object ExtendsDemo3 {
	def main(args: Array[String]): Unit = {
		val s = new Student3("john", 18, "1024")
		s.walk()
	}
}

/**
  * 不得不提的是，如果父类是抽象类，则override关键字可以不加，
  * 这是因为如果继承的父类是抽象类（假设抽象类为AbstractClass，子类为SubClass），
  * 在SubClass类中，AbstractClass对应的抽象方法如果没有实现的话，那SubClass也必须定义为抽象类，
  * 否则的话必须要有方法的实现，这样的话，加不加override关键字都是可以的。
  * 下面是一个实例代码：
  */
//抽象的Person类
abstract class Person21(name: String, age: Int) {
	def walk(): Unit
}

//Student继承抽象Person类
class Student4(name: String, age: Int, var studentNo: String) extends Person21(name, age) {
	//重写抽象类中的walk方法，可以不加override关键字
	def walk(): Unit = {
		println("walk like a elegant swan")
	}
}

object ExtendsDemo4 {
	def main(args: Array[String]): Unit = {
		val s = new Student4("john", 18, "1024")
		s.walk()
	}
}

/**
  * 4. 匿名类
  * 当某个类在程序中只使用一次时，可以将类定义为匿名类，匿名类的定义如下：
  */
//抽象的Person类
abstract class Person22(name: String, age: Int) {
	def walk(): Unit
}


object ExtendsDemo5 {
	def main(args: Array[String]): Unit = {
		//下面的代码定义了一个匿名类，并且进行了实例化
		//直接new Person("john",18)，后面跟的是类的内容
		//我们知道，Person是一个抽象类，它是不能被实例化的
		//这里能够直接new操作是因为我们扩展了Person类，只不
		//过这个类是匿名的，只能使用一次而已
		val s = new Person22("john", 18) {
			override def walk() = {
				println("Walk like a normal Person")
			}
		}
		s.walk()
	}
}

/**
  * 5 多态与动态绑定
  * “多态”(Polymorphic)也叫“动态绑定”(Dynamic Binding),
  * “迟绑定”(Late Binding)指“在执行期间（而非编译期间）判断所引用对象的实际类型，根据其实际类型调用其相应的方法。”
  * 即指子类的引用可以赋给父类，程序在运行时根据实际类型调用对应的方法
  * 下面的代码演示了scala中的多态与动态绑定：
  */
//抽象Person类
abstract class Person23(var name: String, var age: Int) {

	def walk(): Unit

	//talkTo方法，参数为Person类型
	def talkTo(p: Person23): Unit
}

class Student5(name: String, age: Int) extends Person23(name, age) {
	private var studentNo: Int = 0

	def walk() = println("walk like a elegant swan")

	//重写父类的talkTo方法
	def talkTo(p: Person23) = {
		println("talkTo() method in Student")
		println(this.name + " is talking to " + p.name)
	}
}

class Teacher5(name: String, age: Int) extends Person23(name, age) {
	private var teacherNo: Int = 0

	def walk() = println("walk like a elegant swan")

	//重写父类的talkTo方法
	def talkTo(p: Person23) = {
		println("talkTo() method in Teacher")
		println(this.name + " is talking to " + p.name)
	}
}

object ExtendsDemo6 {
	def main(args: Array[String]): Unit = {

		//下面的两行代码演示了多态的使用
		//Person类的引用可以指向Person类的任何子类
		val p1: Person23 = new Teacher5("albert", 38)
		val p2: Person23 = new Student5("john", 38)

		//下面的两行代码演示了动态绑定
		//talkTo方法参数类型为Person类型
		//p1.talkTo(p2)传入的实际类型是Student
		//p2.talkTo(p1)传入的实际类型是Teacher
		//程序会根据实际类型调用对应的不同子类中的talkTo()方法
		p1.talkTo(p2)
		p2.talkTo(p1)
	}
}

/**
  * 6. 组合与继承的使用
  * 一 继承
  *
  * 继承是Is a 的关系，比如说Student继承Person,则说明Student is a Person。继承的优点是子类可以重写父类的方法来方便地实现对父类的扩展。
  * 继承的缺点有以下几点：
  * 1 父类的内部细节对子类是可见的。
  * 2 子类从父类继承的方法在编译时就确定下来了，所以无法在运行期间改变从父类继承的方法的行为。
  * 3 如果对父类的方法做了修改的话（比如增加了一个参数），则子类的方法必须做出相应的修改。所以说子类与父类是一种高耦合，违背了面向对象思想。
  *
  * 二 组合
  *
  * 组合也就是设计类的时候把要组合的类的对象加入到该类中作为自己的成员变量。组合的优点：
  * 1 当前对象只能通过所包含的那个对象去调用其方法，所以所包含的对象的内部细节对当前对象时不可见的。
  * 2 当前对象与包含的对象是一个低耦合关系，如果修改包含对象的类中代码不需要修改当前对象类的代码。
  * 3 当前对象可以在运行时动态的绑定所包含的对象。可以通过set方法给所包含对象赋值。
  * 组合的缺点：
  * 1 容易产生过多的对象。
  * 2 为了能组合多个对象，必须仔细对接口进行定义。
  * 由此可见，组合比继承更具灵活性和稳定性，所以在设计的时候优先使用组合。只有当下列条件满足时才考虑使用继承：
  * 1 子类是一种特殊的类型，而不只是父类的一个角色
  * 2 子类的实例不需要变成另一个类的对象
  * 3 子类扩展，而不是覆盖或者使父类的功能失效
  *
  */