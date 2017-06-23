package chapter1.control

/**
  * Created by hadoop on 16-10-2.
  */
object For {
	//利用if替代while控制结构
	//这些做可以减少var变量的使用，程序结构也更简单，表达能力更强
	def gcd(x: Long, y: Long): Long =
		if (y == 0) x else gcd(y, x % y)

	//在for循环结构中还可以加入if进行过滤操作
	val filesHere = (new java.io.File(".")).listFiles
	for (file <- filesHere if file.getName.endsWith(".scala"))
		println(file)
	//还可以加入多个过滤条件，用;隔开
	for (
		file <- filesHere if file.isFile;
		if file.getName.endsWith(".scala")
	) println(file)

	//多重循环的实现：
	def fileLines(file: java.io.File) =
		scala.io.Source.fromFile(file).getLines.toList

	def grep(pattern: String) =
		for (
			file <- filesHere if file.getName.endsWith(".scala");
			line <- fileLines(file) if line.trim.matches(pattern)
		) println(file + ": " + line.trim)

	grep(".*gcd.*")

	def scalaFiles =
		for {
			file <- filesHere
			if file.getName.endsWith(".scala")
		} yield file
}
