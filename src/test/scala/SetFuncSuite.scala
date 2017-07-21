import com.typesafe.scalalogging.slf4j.Logger
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

/**
  * Created by noah on 17-7-21.
  */
class SetFuncSuite extends FunSuite {

  //差集
  test("Test difference") {
    val a = Set("a", "b", "a", "c")
    val b = Set("b", "d")
    assert(a -- b === Set("a", "c"))
  }

  //交集
  test("Test intersection") {
    val a = Set("a", "b", "a", "c")
    val b = Set("b", "d")
    assert(a.intersect(b) === Set("b"))
  }

  //并集
  test("Test union") {
    val a = Set("a", "b", "a", "c")
    val b = Set("b", "d")
    assert(a ++ b === Set("a", "b", "c", "d"))
  }


  test("myDemo") {
    val logger = Logger(LoggerFactory.getLogger("name"))
    var intArrayVar = ArrayBuffer(1, 1, 2)
    //生成新的数组，原数组不变
    //缓冲数据转换后产生的仍然是缓冲数组
    var intArrayVar2 = for (i <- intArrayVar) yield i * 2
    //定长数组转转后产生的仍然是定长数组，原数组不变
    var intArrayNoBuffer = Array(1, 2, 3)
    var intArrayNoBuffer2 = for (i <- intArrayNoBuffer) yield i * 2
    //加入过滤条件
    var intArrayNoBuffer3 = for (i <- intArrayNoBuffer if i >= 2) yield i * 2



  }
}