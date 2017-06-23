package chapter2

/**
  * 4 Traits简介
  * scala和java语言一样，采用了很强的限制策略，避免了多种继承的问题。
  * 在java语言中，只允许继承一个超类，该类可以实现多个接口，但java接口有其自身的局限性：
  * 接口中只能包括抽象方法，不能包含字段、具体方法。
  * Scala语言利用Trait解决了该问题，
  * 在scala的trait中，它不但可以包括抽象方法还可以包含字段和具体方法。
  * trait的示例如下：
  */
//trait定义演示
trait TraitsDemo {
	//定义一个抽象方法，注意不需要加abstract
	//加了abstract反而会报错
	def delete(id: String): Boolean

	def add(o: Any): Boolean

	def update(o: Any): Int

	def query(id: String): List[Any]
}

//生成的字节码文件反编译后的结果：
/*
Compiled from "Dao.scala"
public interface cn.scala.xtwy.DAO {
  public abstract boolean delete(java.lang.String);
  public abstract boolean add(java.lang.Object);
  public abstract int update(java.lang.Object);
  public abstract scala.collection.immutable.List<java.lang.Object> query(java.l
ang.String);
}
 */
/**
  * 下面的代码演示了如果使用trait
  */
trait MysqlDAO {
	def add(o: Any): Boolean

	def update(o: Any): Int

	def query(id: String): List[Any]
}

class DaoImpl extends MysqlDAO {
	def add(o: Any): Boolean = true

	def update(o: Any): Int = 1

	def query(id: String): List[Any] = List(1, 2, 3)
}

/**
  * 如果有多个trait的话：
  */
trait MysqlDAO2 {
	var recodeMount: Long = 15000000000000L

	def add(o: Any): Boolean

	def update(o: Any): Int

	def query(id: String): List[Any]
}

class DaoImpl2 extends MysqlDAO2 with Cloneable {
	def add(o: Any): Boolean = true

	def update(o: Any): Int = 1

	def query(id: String): List[Any] = List(1, 2, 3)
}

/**
  * 5 Traits几种不同使用方式
  *
  * 1 当做java接口使用的trait，如
  */
//trait定义演示
trait DAO1 {
	//定义一个抽象方法，注意不需要加abstract
	//加了abstract反而会报错
	def delete(id: String): Boolean

	def add(o: Any): Boolean

	def update(o: Any): Int

	def query(id: String): List[Any]
}

/**
  * 2 带具体实现的trait
  */

trait DAO2 {
	//delete方法有具体实现
	def delete(id: String): Boolean = {
		println("delete implementation")
		true
	}

	def add(o: Any): Boolean

	def update(o: Any): Int

	def query(id: String): List[Any]
}

//这里定义的特质将生成两个字节码文件：
/*
2015/07/25  22:20               575 DAO$class.class
2015/07/25  22:20               898 DAO.class
               2 个文件          1,473 字节
               2 个目录 175,333,232,640 可用字节
D:\ScalaWorkspace\ScalaChapter10\bin\cn\scala\xtwy>javap -private DAO$class.class
Compiled from "Dao.scala"
public abstract class cn.scala.xtwy.DAO$class {
  public static boolean delete(cn.scala.xtwy.DAO, java.lang.String);
  public static void $init$(cn.scala.xtwy.DAO);
}

D:\ScalaWorkspace\ScalaChapter10\bin\cn\scala\xtwy>javap -private DAO.class
Compiled from "Dao.scala"
public abstract class cn.scala.xtwy.DAO$class {
  public static boolean delete(cn.scala.xtwy.DAO, java.lang.String);
  public static void $init$(cn.scala.xtwy.DAO);
}
 */
//从字节码文件可以看出，带有具体实现的trait是通过java中的抽象类来实现的。
/**
  * 3 带抽象字段的trait
  */
trait DAO3 {
	var recodeMount: Long

	def delete(id: String): Boolean = {
		println("delete implementation")
		true
	}

	def add(o: Any): Boolean

	def update(o: Any): Int

	def query(id: String): List[Any]
}

/**
  * 4 具体字段的trait
  */

trait DAO4 {
	var recodeMount: Long = 15000000000000L

	def delete(id: String): Boolean = {
		println("delete implementation")
		true
	}

	def add(o: Any): Boolean

	def update(o: Any): Int

	def query(id: String): List[Any]
}

