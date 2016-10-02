package chapter3

/**
  * Scala I/O操作简介
  * Scala 写文件
  * Scala 读文件
  * Scala 网络I/O
  * 正则表达式简介
  * Scala正则表达式实战
  */
/**
  * 1. Scala I/O操作简介
  * I/O操作是一门编程语言中的重要内容，
  * 在Scala中，它更多的是调用Java中的I/O类或者通过对java中的I/O类进行相应的封装来实现I/O操作。
  * 在上一节内容中我们已经用到了I/O操作：
  * PrintWriter使用的是java.io.PrintWriter类
  * val fileOutput=new PrintWriter(fileName:String)
  * fileOutput.println("#")
  * fileOutput.flush()
  * scala自身关于I/O的内容比较少，下图给出的是scala I/O相关的类
  * 相比于java语言中的I/O类，scala语言中的I/O相关类数量就显得少得多，
  * 而这其中最常用的只有Source这个类，因此要学好scala I/O操作，必须对java中的I/O相关类有个比较深入的了解。
  */

/**
  * 2. Scala 写文件
  *
  * Scala进行文件写操作，直接用的都是java中的I/O类，例如
  */

import java.io._

//可以看出 scala中的文件写操作与java中的I/O操作并没有什么区别，这说明了scala可以与java进行互操作。
object ScalaFileWriter {
	def main(args: Array[String]): Unit = {
		val fileWriter = new FileWriter("file.txt")
		fileWriter.write("scala file writer")
		fileWriter.flush()
		fileWriter.close()
	}
}

/**
  * 3. 读文件
  *
  * scala中读文件可以直接使用java中的I/O类，也可以用scala中的Source对象，
  * 该对象对java中的I/O进行了封装，使用更简便更灵活，
  * 下面的代码给出了如果读取文件并将文件内容格式化输出：
  */

import scala.io._

object ScalaFileReader {
	def main(args: Array[String]): Unit = {
		//读取文件
		val file = Source.fromFile("D:\\scala\\doc\\api\\package.html")
		//返回Iterator[String]
		val lines = file.getLines()
		//打印内容
		for (i <- lines) println(i)
		//关闭文件
		file.close()
	}
}

/**
  * 4. 网络I/O
  *
  * scala中的网络I/O操作可以通过Source对象中的fromURL方法来实现，也可以使用原生的JAVA 网络I/O操作进行，
  * 下面的代码演示的是scala中的网络I/O读取百度首页内容：
  */

import java.net.URL

import scala.io.Source.fromURL

object NetworkIO {
	def main(args: Array[String]): Unit = {
		print(fromURL(new URL("http://www.baidu.com")).mkString)
	}
}

