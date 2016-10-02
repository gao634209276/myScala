package chapter2

/**
  * 辅助构造函数
  * 前面讲了，如果禁用掉了主构建器，则必须使用辅助构造函数来创建对象。
  * 辅助构造函数具有两个特点：
  * （1）辅助构建器的名称为this，java中的辅助构造函数与类名相同，
  * 这常常会导致修改类名时出现不少问题，scala语言避免了这样的问题；
  * （2）调用辅助构造函数时，必须先调用主构造函数或其它已经定义好的构造函数。
  *
  * 首先看一下只有辅助构造函数的Person类
  */
//只有辅助构造函数的类
class SecondaryConstructor {
	//类成员
	private var name: String = null
	private var age: Int = 18
	private var sex: Int = 0

	//辅助构造器
	def this(name: String) {
		this()
		this.name = name
	}

	def this(name: String, age: Int) {
		this(name)
		this.age = age
	}

	def this(name: String, age: Int, sex: Int) {
		this(name, age)
		this.sex = sex
	}
}

//字节码文件
/*
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private java.lang.String name;
  private int age;
  private int sex;
  private java.lang.String name();
  private void name_$eq(java.lang.String);
  private int age();
  private void age_$eq(int);
  private int sex();
  private void sex_$eq(int);
  public cn.scala.xtwy.Person();
  public cn.scala.xtwy.Person(java.lang.String);
  public cn.scala.xtwy.Person(java.lang.String, int);
  public cn.scala.xtwy.Person(java.lang.String, int, int);
}
 */
//在定义辅助构造函数时，需要注意构造函数的顺序

class Person15 {
	//类成员
	private var name: String = null
	private var age: Int = 18
	private var sex: Int = 0

	//辅助构造器
	def this(name: String, age: Int, sex: Int) {
		this()
		//this(name,age)//此处会发生编译错误，这是因为def this(name:String,age:Int)没有被定义
		this.sex = sex
	}

	def this(name: String) {
		this()
		this.name = name
	}

	def this(name: String, age: Int) {
		this(name)
		this.age = age
	}
}

/**
  * 带主构造函数、辅助构造函数的Person类
  */
//具有主构建函数和辅助构建函数的Person类
class Person16(var name: String, var age: Int) {
	//类成员
	private var sex: Int = 0

	//辅助构造器
	def this(name: String, age: Int, sex: Int) {
		this(name, age)
		this.sex = sex
	}
}

/*
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private java.lang.String name;
  private int age;
  private int sex;
  public java.lang.String name();
  public void name_$eq(java.lang.String);
  public int age();
  public void age_$eq(int);
  private int sex();
  private void sex_$eq(int);
  public cn.scala.xtwy.Person(java.lang.String, int);
  public cn.scala.xtwy.Person(java.lang.String, int, int);
}
 */
/**
  * 在主构造函数小节当中我们提到，有时候可能会禁用掉主构造函数，此时只能通过辅助构造函数来创建对象
  */
//禁用主构造函数
class Person17 private(var name: String, var age: Int) {
	//类成员
	private var sex: Int = 0

	//辅助构造器
	def this(name: String, age: Int, sex: Int) {
		this(name, age)
		this.sex = sex
	}
}

//其字节码文件内容如下
/*
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private java.lang.String name;
  private int age;
  private int sex;
  public java.lang.String name();
  public void name_$eq(java.lang.String);
  public int age();
  public void age_$eq(int);
  private int sex();
  private void sex_$eq(int);
  private cn.scala.xtwy.Person(java.lang.String, int);
  public cn.scala.xtwy.Person(java.lang.String, int, int);
}
 */