package chapter2

/**
  * 单例对象
  * 在某些应用场景下，我们可能不需要创建对象，而是想直接调用方法，
  * 但是Scala语言并不支持静态成员，Scala通过单例对象来解决该问题。
  */
//单例对象的创建方式如下
object StudentSinglton {
	private var studentNo: Int = 0;

	def uniqueStudentNo() = {
		studentNo += 1
		studentNo
	}

	def main(args: Array[String]): Unit = {
		println(StudentSinglton.uniqueStudentNo())
	}
}

/*
利用javap命令查看字节码文件内容有：
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
  private cn.scala.xtwy.Student$();
}

D:\ScalaWorkspace\ScalaChapter06_2\bin\cn\scala\xtwy>javap -private Student
警告: 二进制文件Student包含cn.scala.xtwy.Student
Compiled from "Student.scala"
public final class cn.scala.xtwy.Student {
  public static void main(java.lang.String[]);
  public static int uniqueStudentNo();
}*/
/**
  * 不难看出，object Student最终生成了两个类，分别是Student与Student，它们都是final类型的，而且Student的构造方法是私有的，通过静态成员域 public static final cn.scala.xtwy.Student$ MODULE$; 对Student$进行引用，这其实是Java语言中单例实现方式。
  * 单例对象的使用方式同Java语言类引用静态成员是一样的。
  */