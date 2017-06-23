package com.chapter14

import org.scalatest.Suite

/**
  * Created by noah on 17-6-23.
  */
object Chapter14 {
  /**
    * 14.1 断言
    * 可以用assert或者ensuring
    */
  def testAssert() {
    val i = 1
    val j = 1
    assert(i == j)
  }

  //这里 _就是返回的结果值，这里是i
  def testEnsuring(w: Int): Int = {
    val i = w + 1
    i //最后一行返回的就是 下面的_
  } ensuring (w < _)

  /**
    * Scala里的单元测试
    */
  class ElementSuite extends Suite {
    def testUniformElement() {
      val a = 1
      assert(a == 1)
    }
  }

  def main(args: Array[String]): Unit = {
    testEnsuring(1)
  }
}
