package com.chapter9

/**
  * Created by noah on 17-6-23.
  */
//减少代码重复,根据上面代码进行优化
object FileMatcher2 {

  private def filesHere = (new java.io.File(".")).listFiles

  def filesMatching(querying: String, matcher: (String, String) => Boolean) = {
    for (file <- filesHere; if matcher(file.getName, querying))
      yield file
  }

  //filesEnding 和 filesEnding2 表达的意思是一样的
  def filesEnding(query: String) =
  //这边 _是绑定变量，没有自由变量，所以没有采用闭包
    filesMatching(query, _.endsWith(_))

  def filesEnding2(query: String) =
    filesMatching(query, (fileName: String, query: String) => fileName.endsWith(query))

  def filesContaining(query: String) =
    filesMatching(query, _ contains _)

  def filesRegex(query: String) =
    filesMatching(query, _ matches _)

}