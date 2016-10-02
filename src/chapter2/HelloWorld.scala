package chapter2

//scala应用程序同样采用main方法作为应用程序的入口
object HelloWorld {
	def main(args: Array[String]): Unit = {
		println("Hello World")
	}

	def gcdLoop(x: Long, y: Long): Long = {
		var a = x
		var b = y
		while (a != 0) {
			val temp = a
			a = b % a
			b = temp
		}
		b
	}

	var line = ""
	do {
		line = readLine()
		println("Read: " + line)
	} while (line != "")

}