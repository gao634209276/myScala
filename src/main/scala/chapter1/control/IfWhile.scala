package chapter1.control

/**
  * Created by hadoop on 16-10-2.
  */
object IfWhile {
	//if 的使用：
	val x = if ("hello" == "hell") 1 else 0

	//while 的使用：
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

	//do while的使用
	var line = ""
	do {
		line = readLine()
		println("Read: " + line)
	} while (line != "")

}
