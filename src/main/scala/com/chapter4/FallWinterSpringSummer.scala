package com.chapter4

/**
  * 4.5 Application(App) 特质
  *
  * 1、使用方法是 extends Application(App)代之以main方法。把想要执行的代码写入花括号
  * 2、优点：比main方法简便。缺点：1)不能使用args 2)只适用于单线程和相对简单的程序
  */
object FallWinterSpringSummer extends App {
  for(season <- List("fall","winter","spring"))
    println(season + ":" + CheckSumAccumulator.calculate(season) )
}