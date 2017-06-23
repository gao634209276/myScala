
package com.chapter10.factory

/**
  * 10.13 定义工厂对象
  * 1、工厂对象包含了构建其他对象的方法。客户可以使用这些工厂
  * 方法构造对象，而不是直接使用new构造对象。
  * 2、工厂对象的好处有：
  * 1)将对象的创建集中化并且隐藏对象实际代表的类的细节。
  * 2)可以让客户更容易理解你的库因为暴露的细节更少。
  * 3)给你更多的机会在不破还客户代码的前提下改变库的实现。
  *
  * 3、为构建工厂方法的首要任务是选择工厂方法应该放在何处。
  * 一个直接的方法是创建Element类的伴生对象并把它作为布局元素的工厂方法。
  * 使用这种方式，你要唯一暴露给客户的是Element的类/对象组合，隐藏子类
  * ArrayElement和LineElement
  */
abstract class Element {

  def contents: Array[String]

  def height: Int = contents.length

  def width: Int = if (height == 0) 0 else contents(0).length

  def above(that: Element): Element = {
    Element.elem(this.contents ++ that.contents)
  }

  def beside(that: Element): Element = {
    Element.elem(
      for (
        (line1, line2) <- this.contents zip that.contents
      ) yield line1 + line2
    )
  }

  override def toString = contents mkString "\n"
}

object Element {

  //用私有类隐藏实现
  private class LineElement(s: String) extends Element {
    val contents = Array(s)
    override val width = s.length
    override val height = 1
  }

  private class ArrayElement(cons: Array[String]) extends Element {
    def contents: Array[String] = cons;
  }

  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)

  def elem(line: String): Element =
    new LineElement(line)
}