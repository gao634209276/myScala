package chapter2

/**
  * 伴生对象/伴生类
  *
  * 在前面单例对象的基础之上，我们在object Student所在的文件内定义了一个class Student，
  * 此时object Student被称为class Student的伴生对象，
  * 而class Student被称为object Student的伴生类：
  */
class StudentAssociated(var name: String, age: Int)

object StudentAssociated {
	private var studentNo: Int = 0;

	def uniqueStudentNo() = {
		studentNo += 1
		studentNo
	}

	def main(args: Array[String]): Unit = {
		println(StudentAssociated.uniqueStudentNo())
	}
}

//生成的字节码文件如下：
/*
D:\ScalaWorkspace\ScalaChapter06_2\bin\cn\scala\xtwy>javap -private Student
警告: 二进制文件Student包含cn.scala.xtwy.Student
Compiled from "Student.scala"
public class cn.scala.xtwy.Student {
  private java.lang.String name;
  private int age;
  public static void main(java.lang.String[]);
  public static int uniqueStudentNo();
  public java.lang.String name();
  public void name_$eq(java.lang.String);
  public int age();
  public void age_$eq(int);
  public cn.scala.xtwy.Student(java.lang.String, int);
}

D:\ScalaWorkspace\ScalaChapter06_2\bin\cn\scala\xtwy>javap -private Student$
警告: 二进制文件Student$包含cn.scala.xtwy.Student$
Compiled from "Student.scala"
public final class cn.scala.xtwy.Student$ {
  public static final cn.scala.xtwy.Student$ MODULE$;
  private int studentNo;
  public static {};
  private int studentNo();
  private void studentNo_$eq(int);
  public int uniqueStudentNo();
  public void main(java.lang.String[]);
  private cn.scala.xtwy.Student$();
}
 */
/**
  * 从上面的代码中不难看出，其实伴生对象与伴生类本质上是不同的两个类，
  * 只不过伴生类与伴生对象之间可以相互访问到对主的成员包括私有的成员变量或方法
  * 例如：
  */
//
class StudentAssociated2(var name: String, var age: Int) {
	private var sex: Int = 0

	//直接访问伴生对象的私有成员
	def printCompanionObject() = println(StudentAssociated2.studentNo)
}

object StudentAssociated2 {
	private var studentNo: Int = 0;

	def uniqueStudentNo() = {
		studentNo += 1
		studentNo
	}

	def main(args: Array[String]): Unit = {
		println(StudentAssociated2.uniqueStudentNo())
		val s = new StudentAssociated2("john", 29)
		//直接访问伴生类Student中的私有成员
		println(s.sex)
	}
}