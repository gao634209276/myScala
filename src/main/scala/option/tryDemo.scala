package option


import scala.util.{Success, Try}

object tryDemo extends App {

  // Success
  val z: Try[Int] = Try(27)
  val x: Try[Int] = Success(27)

  // Failure
  val y: Try[Int] = Try(throw new NullPointerException("null"))

}
