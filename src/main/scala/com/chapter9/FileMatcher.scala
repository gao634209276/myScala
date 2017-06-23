/**
  * 第9章 控制抽象
  * 高阶函数不仅能帮助减少代码重复，还能使客户代码更简洁
  */
package com.chapter9

/**
  * 9.1 减少代码重复
  */
//未优化的代码
object FileMatcher {

  private def filesHere = (new java.io.File(".")).listFiles

  def filesEnding(query: String) =
    for (file <- filesHere; if file.getName.endsWith(query))
      yield file

  def filesContaining(query: String) =
    for (file <- filesHere; if file.getName.contains(query))
      yield file

  def filesRegex(query: String) =
    for (file <- filesHere; if file.getName.matches(query))
      yield file

}
