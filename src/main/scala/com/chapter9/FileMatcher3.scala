package com.chapter9

//对上面代码进一步优化
object FileMatcher3 {

  private def filesHere = (new java.io.File(".")).listFiles

  def filesMatching(matcher: (String) => Boolean) = {
    for (file <- filesHere; if matcher(file.getName))
      yield file
  }

  //filesEnding 和 filesEnding2 表达的意思是一样的
  def filesEnding(query: String) =
  //这边 _是绑定变量，query是自由变量，采用了闭包
    filesMatching(_.endsWith(query))

  def filesContaining(query: String) =
    filesMatching(_.contains(query))

  def filesRegex(query: String) =
    filesMatching(_.matches(query))

}
