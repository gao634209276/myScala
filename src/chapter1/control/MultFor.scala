package chapter1.control

/**
  * Created by hadoop on 16-10-2.
  */
object MultFor {
	val filesHere = (new java.io.File("src/chapter1/control")).listFiles

	def fileLines(file: java.io.File) =
		scala.io.Source.fromFile(file).getLines.toList

	//多重循环的实现：
	def grep(pattern: String) =
		for (
			file <- filesHere if file.getName.endsWith(".scala");
			line <- fileLines(file) if line.trim.matches(pattern)
		)
			println(file + ": " + line.trim)

	//等同于for嵌套
	def grep2(pattern: String) =
		for (
			file <- filesHere if file.getName.endsWith(".scala")
		) for (
			line <- fileLines(file) if line.trim.matches(pattern)
		) println(file + ": " + line.trim)


	def main(args: Array[String]) {
		grep(".*gcd.*")
		println("----------------------------")
		grep2(".*gcd.*")
	}
}
