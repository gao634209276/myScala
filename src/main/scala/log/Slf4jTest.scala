package log

import org.slf4j.LoggerFactory

/**
  * Created by noah on 17-7-21.
  */
object Slf4jTest {
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger("Slf4jTest")
    logger.info("slf4j Logger ")
  }
}

