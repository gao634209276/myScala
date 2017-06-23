package com.chapter10
/**
  * 10.11 组合和继承
  */
class LineElement2(s: String) extends Element {
  //LineElements2 与 Array是组合关系，与Element是继承关系
  val contents = Array(s)
  override val width = s.length
  override val height = 1
}