package process

import java.io.File

import scala.sys.process._

object ProcessDemo extends App {

  //  blocks until it exits, and returns the exit code.  Standard output and error are sent to the console.
  "ls".!

  //Process("ls").lines

  // blocks until it exits, and returns the output as a String.
  // Standard error is sent to the provided ProcessLogger.
  // If the exit code is non-zero, an exception is thrown.
  val res = Seq("ls", "src").!!
  println(res)

  // 把两个ProcessBuilder组合起来
  // 1 pipeline方式，类似于unix的管道操作，使用 #| 方法
  "ls ." #| "grep src" ! //返回int

  "ls ." #| "grep src" !! // 返回str


  //2 按顺序执行，第一部分成功后第二部分才执行，没有传递结果功能，使用 ### 方法
  "ls ." ### "sleep 5s" ### "ls ~/"

  //3 根据第一部分执行完返回状态码，决定第二部分是否执行，使用 #&& 或 #|| 方法
  "ls ." #&& "exit 1" #|| "ls ~/"

  // 重定向输出
  "ls /" #> "grep App" #>> new File("app.text") !

  // 启动一个进程
  // run: 最通用的方法，立即返回 scala.sys.process.Process，于此同时命令异步执行
  println(("ls /Applications" ### "sleep 4s" ### "ls /Users").run)

  // ！ 会阻塞执行知道任务完毕退出，返回退出状态码

  // !! 也会阻塞执行流直到命令执行完毕退出，与!，会返回String输出结果。

  // line 立即返回，有点像run，不同的是，返回的是Stream[String]，当去去stream中的数据时，有可能阻塞直到值可获得。
  // 当执行的命令返回非0状态码，说明命令执行错误，lineStream会抛出异常,需要额外处理。
  // 对此，加强版的方法为line_!。


}
