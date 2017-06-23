package chapter2

/**
  * 在前几节中我们提到，通过利用apply方法可以直接利用类名创建对象，
  * 例如前面在讲集合的时候，可以通过val intList=List(1,2,3)这种方式创建初始化一个列表对象，
  * 其实它相当于调用val intList=List.apply(1,2,3)，只不过val intList=List(1,2,3)这种创建方式更简洁一点，
  * 但我们必须明确的是这种创建方式仍然避免不了new，它后面的实现机制仍然是new的方式，只不过我们自己在使用的时候可以省去new的操作。
  * 下面就让我们来自己实现apply方法，代码如下：
  */
//定义Student类，该类称为伴生类，因为在同一个源文件里面，我们还定义了object Student
class StudentApply(var name: String, var age: Int) {
	private var sex: Int = 0

	//直接访问伴生对象的私有成员
	def printCompanionObject() = println(StudentApply.studentNo)

}

//伴生对象
object StudentApply {
	private var studentNo: Int = 0;

	def uniqueStudentNo() = {
		studentNo += 1
		studentNo
	}

	//定义自己的apply方法
	def apply(name: String, age: Int) = new StudentApply(name, age)

	def main(args: Array[String]): Unit = {
		println(StudentApply.uniqueStudentNo())
		val s = new StudentApply("john", 29)
		//直接访问伴生类Student中的私有成员
		println(s.sex)

		//直接利用类名进行对象的创建，这种方式实际上是调用前面的apply方法进行实现，这种方式的好处是避免了自己手动new去创建对象
		val s1 = StudentApply("john", 29)
		println(s1.name)
		println(s1.age)
	}
}