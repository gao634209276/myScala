package chapter2

/**
  * 类主构造器
  * 主构造器的定义与类的定义交织在一直，将构造器参数直接放在类名称之后，
  * 如下代码：
  */
//下列代码不但定义了一个类Person，还定义了主构造器，主构造器的参数为String、Int类型
class Constructor(val name: String, val age: Int)

/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private final java.lang.String name;
  private final int age;
  public java.lang.String name();
  public int age();
  public cn.scala.xtwy.Person(java.lang.String, int);
}
 */

/**
  * 不难看出：上面的代码与下列java语言编写的代码等同
  * public class Person{
  * private final String name;
  * private final int age;
  * public Person(String name,int age){
  * this.name=name;
  * this.age=age;
  * }
  * public String getName(){ return name}
  * public int getAge() {return age}
  * }
  *
  * 具体使用操作如下：
  * val p=new Person("john",29)
  * p.name
  * p.age
  *
  * 主构造器会执行类定义中的所有语句，例如
  */
//当在创建对象时，需要进行相关初始化操作时，可以将初始化语句放在类体中，同样也可以在类中添加或重写相关方法
class Person6(val name: String, val age: Int) {
	//println将作为主构建器中的一部分，在创建对象时被执行
	println("constructing Person ........")

	//重写toString()方法
	override def toString() = name + ":" + age
}

/**
  * val p=new Person("john",29)
  * constructing Person ........
  * p: Person = john:29
  *
  * 回过头来看的话，前面我们定义的Person类是一种无参主构建器
  */

//Person类具有无参主构建器
class Person7 {
	println("constructing Person....")
	val name: String = "john"
}

/**
  * val p=new Person()
  * constructing Person....
  * p: Person = Person@79895f
  *
  * 主构建器还可以使用默认参数
  */
//默认参数的主构建器
class Person8(val name: String = "", val age: Int = 18) {
	println("constructing Person ........")

	override def toString() = name + ":" + age
}

/**
  * val p=new Person
  * constructing Person ........
  * p: Person = :18
  * val p=new Person("john")
  * constructing Person ........
  * p: Person = john:18
  *
  * 主构造器中的参数还可以加访问控制符
  */
//默认参数的主构建器，参数带访问控制符号
//age变成私有成员，其getter方法是私有的，外部不能访问
class Person9(val name: String = "", private val age: Int = 18) {
	println("constructing Person ........")

	override def toString() = name + ":" + age
}

/**
  * 当主构造器的参数不用var或val修饰的时候，
  * 参数会生成类的私有val成员，并且不会产生getter和setter方法
  */
//不加变量修饰符
class Person10(name: String, age: Int) {
	println("constructing Person ........")

	override def toString() = name + ":" + age
}

/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private final java.lang.String name;
  private final int age;
  public java.lang.String toString();
  public cn.scala.xtwy.Person(java.lang.String, int);
}
 */
//与下面类定义等同
class Person11(private[this] val name: String, private[this] val age: Int) {
	println("constructing Person ........")

	override def toString() = name + ":" + age
}

/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private final java.lang.String name;
  private final int age;
  public java.lang.String toString();
  public cn.scala.xtwy.Person(java.lang.String, int);
}
 */
/**
  * 值得注意的是，将上述Person类中的toString()方法去掉，则类中无任何地方使用了主构造器的参数，
  * 此时主构造器参数不会生成类成员
  * 即将
  */
//不加变量修饰符
class Person12(name: String, age: Int) {
	println("constructing Person ........")

	override def toString() = name + ":" + age
}

/**
  * 改成：
  */
class Person13(val name: String, age: Int) {
	println("constructing Person ........")
}

/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  public cn.scala.xtwy.Person(java.lang.String, int);
}
 */
//可以看出，主构造器参数不会生成类成员
//下面图给出了Scala中主构建器参数生成类成员和方法时的规则

/**
  * 在某些情况下，可能需要禁用主构建器，代码如下：
  */
//类名后面紧跟private关键字可以将主构建器设为私有，不允许外部使用
class Person14 private(var name: String, var age: Int) {
	println("constructing Person ........")
}

//生成的字节码文件如下，可以看到其构建函数已经为private了
/*
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private java.lang.String name;
  private int age;
  public java.lang.String name();
  public void name_$eq(java.lang.String);
  public int age();
  public void age_$eq(int);
  private cn.scala.xtwy.Person(java.lang.String, int);
}
 */
/**
  * 此时不能直接这么用 val p=new Person("john",19)
  */
