package log

import org.apache.log4j.Logger

/**
  *
  * Log4j should conf log4j.properties
  * Created by noah on 17-7-21.
  */
object Log4jTest {
  def main(args: Array[String]): Unit = {
    val log = Logger.getLogger("Log4jTest")
    log.info("Log4j Logger ")

  }
}
