package chapter3
package implicitc

import java.io.{BufferedReader, File, FileReader}


/**
  * Created by tangfei on 2016/11/4.
  */
class Implicit() {
	def test:Unit = {
		println("Implicit")
	}
}

class A{
}

object Implicit {

	//隐式转换
	implicit def a2Implicit2(a:A) = new Implicit()

	//隐式类
	implicit class Files(file: File) {
		def lines: Array[String] = {
			val fileReader: FileReader = new FileReader(file)
			val reader = new BufferedReader(fileReader)
			try {
				var lines = Array[String]()
				var line = reader.readLine()

				while (line != null) {
					lines = lines :+ line
					line = reader.readLine()
				}
				lines
			} finally {
				fileReader.close()
				reader.close()
			}
		}
	}

	//隐式参数
	def implicitMethod(implicit name:String):Unit = {
		println(name)
	}

	def main(args: Array[String]) {
		//隐士转换
		val a = new A
		a.test

		//隐式类
		val file: File = new File(getClass.getResource("/").getPath+"file.txt")
		file.lines foreach  println

		//隐式参数
		implicit val name = "hark"
		implicitMethod
		implicitMethod("bbb")

	}


}

