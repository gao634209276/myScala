package log

import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

/**
  * Slf4j + Logback
  * Scala-logging + Logback
  */

object ScalaLogTest extends App {
  val logger = Logger(LoggerFactory.getLogger("name"))
  logger.debug("This is very convenient ;-)")
}


