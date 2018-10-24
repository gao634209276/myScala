package option

object optionDemo1 extends App {

  // Some and None
  val name: Option[String] = Some(" name ")
  //val name: Option[String] = None

  val upper = name.map(_.trim).filter(_.length != 0).map(_.toUpperCase)

  println(upper.getOrElse("-"))

  println(upper.get)


  // Option
  val x: Option[String] = Option("name")
  val y: Option[String] = Option(null)

  // Option使用
  val username = Option("username")
  val password = Option("password")

  def login(username: String, password: String) = Option[Any] {
    Option(true)
  }

  val isLogin = for (un <- username; pwd <- password; isLogin <- login(un, pwd)) yield isLogin

  isLogin match {
    case Some(_) => println("login success")
    case None => println("login failed")
  }
}
