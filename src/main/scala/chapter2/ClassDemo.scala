package chapter2


/**
  * 类定义、创建对象
  */
//采用关键字class定义
class ClassDemo {
	//类成员必须初始化，否则会报错
	//这里定义的是一个公有成员
	var name: String = null
}

//Person类在编译后会生成Person.class文件
//利用javap -prviate Person命令查看字节码文件内容，可以看得到以下内容
/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private java.lang.String name;
  public java.lang.String name();
  public void name_$eq(java.lang.String);
  public cn.scala.xtwy.Person();
}
 */
/** 从字节码文件内容可以看到：
  * 虽然我们只在Person类中定义了一个类成员（域）name，类型为String，
  * 但Scala会默认帮我们生成name()与name_=（）及构造函数Person()。
  * 其中name()对应Java中的getter方法，name_=()对应java中的setter方法（由于JVM中不允许出现=，所以用$eq代替。
  * 值得注意的是定义的是公有成员，但生成的字节码中却是以私有的方式实现的，生成的getter、setter方法是公有的
  * 因此，可以直接new操作创建Person对象
  *
  * 默认已经有构建函数，所以可以直接new
  * val p=new Person()
  * 直接调用getter和setter方法
  * p.name_=("john")
  * p.name
  * 直接修改，但其实调用的是p.name_=("jonh")
  * p.name="jonh"
  *
  * 你也可以定义自己的getter和setter方法
  */
class Person2 {
	//定义私有成员
	private var privateName: String = null;

	//getter方法
	def name = privateName

	//setter方法
	def name_=(name: String) {
		this.privateName = name
	}

}

/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private java.lang.String privateName;
  private java.lang.String privateName();
  private void privateName_$eq(java.lang.String);
  public java.lang.String name();
  public void name_$eq(java.lang.String);
  public cn.scala.xtwy.Person();
}
 */
/**
  * 从生成的字节码中可以看出：
  * （1）定义成私有成员，其getter、setter方法也是私有的；
  * （2）直接能访问的是我们自己定义的getter、setter方法。
  * 下面给出的是调用方式
  *
  * val p=new Person()
  * p.name
  * 直接赋值法
  * p.name="john"
  *
  * 从代码执行产生的结果，我们可以知道：
  * 通过p.name=“john”这种方式进行赋值，调用者并不需要知道是其通过方法调用还是字段访问来进行操作的，
  * 这便是著名的统一访问原则
  *
  * 如果类的成员域是val类型的变量，则只会生成getter方法
  */
class Person3 {
	//类成员必须初始化，否则会报错
	//这里定义的是一个val公有成员
	val name: String = "john"
}

/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private final java.lang.String name;
  public java.lang.String name();
  public cn.scala.xtwy.Person();
}
 */
/**
  * 从字节码文件中可以看出：val变量对应的是java中的final类型变量，只生成了getter方法
  *
  * 如果将成员域定义为private[this]，则不会生成getter、setter方法
  */
class Person4 {
	//类成员必须初始化，否则会报错
	//private[this]修饰
	private[this] var name: String = "john"
}

/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private java.lang.String name;
  public cn.scala.xtwy.Person();
}
 */
/**
  * 在java语言当中，在定义JavaBean的时候生成的都是setXxx()、getXxx()方法，
  * 但scala语言生成的getter方法和setter方法并不是这样的，
  * 如果也需要程序自动会生成getter方法和setter方法，则需要引入 scala.reflect.BeanProperty
  * 然后采用注解的方式修饰变量
  */
class Person5 {
	//类成员必须初始化，否则会报错
	//@BeanProperty用于生成getXxx,setXxx方法
	import scala.beans.BeanProperty

	@BeanProperty var name: String = "john"
}

/*
警告: 二进制文件Person包含cn.scala.xtwy.Person
Compiled from "Person.scala"
public class cn.scala.xtwy.Person {
  private java.lang.String name;
  public java.lang.String name();
  public void name_$eq(java.lang.String);
  public void setName(java.lang.String);
  public java.lang.String getName();
  public cn.scala.xtwy.Person();
}
 */
/**
  * get.set方法规则:
  * val/var name
  * http://blog.csdn.net/lovehuangjiaju/article/details/47009607
  */

