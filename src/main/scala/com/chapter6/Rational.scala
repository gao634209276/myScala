package com.chapter6

/**
  * 第6章 函数式对象
  *
  * Rationl类的业务：有理数的一些运算
  *
  * 知识要点：
  * 1、不可变对象的权衡：
  *    不可变对象与可变对象相比，有若干优点和一个缺点：
  *    优点：
  *    1)不可变对象不会随着时间变化而改变状态，更易理清头绪
  *    2)不可变对象可以自由地被传递。而可变对象传递给其他代码之前要创建一个副本
  *    3)不可变对象构造之后，多线程访问不会破坏对象的内部状态
  *    4)不可变对象让哈希表键值更加安全
  *    缺点：
  *    不可变对象的更新有时需要复制很大的对象表，而可变对象的更新可以在原址进行。（String 和 StringBuilder的例子）
  * 2、Java类可以有直接带参数的构造器，而Scala类可以直接带参数。
  *   Scala的写法更加简洁--类参数可以直接在类的主体中使用
  * 3、Scala用override关键字进行重写
  * 4、先决条件是传递给方法或构造器时对值的限制，是调用者必须满足的需求。
  *   一种方式是使用require方法。require方法带一个布尔型参数，require方法
  *   将正常返回，否则会报IllegaArgumentException。
  *   Require方法定义在对独立对象Predef上。
  * 5、自指向用this关键字
  * 6、有时候一个类中需要多个构造器。Scala中除主构造器之外的构造器被称为辅助构造器。
  *   用def this(...)定义辅助构造器。
  * 7、一番告诫：在设计库的时候，你应记在脑袋里的目标并不是仅仅让客户代码简洁，而是
  *   让它变得更可读。简洁性经常是可读性的重要部分，但不能简洁得过了头。设计出有助
  *   于简洁、可读、易懂的代码。
  */

class Rational(m: Int, d: Int) {
  //定义先决条件。传入的分母d不能为0
  require(d != 0)

  //分子
  val mole: Int = m

  //分母
  val denom: Int = d

  //定义辅助构造器，允许只传入分子时，分母默认是1
  def this(m: Int) = this(m, 1)

  //重新toString方法
  override def toString() = m + "/" + d

  //定义两个有理数相加的方法
  def add(that: Rational) = {
    new Rational(
      mole * that.denom + that.mole * denom,
      that.denom * denom
    )
  }

  //用‘+’这个符号作为方法名
  def +(that: Rational) = add(that)

  //重载+方法
  def +(i: Int) =
    new Rational(mole  + i * denom, denom)

  //用‘*’这个符号作为方法名，表示两个有理数相乘
  def *(that: Rational): Rational =
    new Rational(this.mole * that.mole, this.denom * that.denom)

  //重载*方法
  def *(i: Int) =
    new Rational(this.mole * i, this.denom)

  //比较
  def lessThan(that: Rational) =
    this.mole * that.denom < that.mole * this.denom
}

object Rational extends App{
  val r1 = new Rational(1, 2)
  val r2 = new Rational(2, 3)

  //这里r1 + r2 等价于 r1.+(r2) 业务上等价于 r1.add(r2)
  var result = r1 + r2
  Predef.println(result)
}