package com.chapter13
/**
  * 第13章 包和引用
  */
/**
  * 13.1 包
  * 1、Scala有两种方式把代码放在命名包中：
  *   1) 像Java一样，通过把package子句放在文件顶端的方式把整个文件内容放入包中
  *   2) 像是C#的命名空间，把package子句之后要放到包里的定义用花括号括起来。
  *      除此之外这种语法还能让你把文件的不同部分放在不同的包中。
  * 2、Scala的包是嵌套的。也就是说，包lucius在包com的内部。
  *    Java包，尽管是分级的，但不是嵌套的。
  */

//同一个文件嵌入不同的包
package com {
  package lucius {
    //在com.lucius包中
    class Navigator
    package tests {
      //在com.lucius.tests包中
      class NavigatorSuite
    }
  }
}

//较少缩进的嵌入包
package com.lucius2 {
  //在com.lucius2包中
  class Navigator
  package tests {
    //在com.lucius2.tests包中
    class NavigatorSuite
  }
}

//Scala的包确实是嵌套的
package com {
  package lucius3 {
    class Navigator
  }
  package lucius4 {
    class Booster {
      //不用写com.lucius3.Navigator
      val nav = new lucius3.Navigator
    }
  }
}

//访问隐匿的包名
package launch {
  class Booster3
}

package com {
  package lucius5 {
    package launch {
      class Booster1
    }
    class MissionControl {
      val booster1 = new launch.Booster1
      val booster2 = new com.launch.Booster2
      //此句在scala2.11.6中编译不通过
      //val booster3 = new _root_.launch.Booster3
    }
  }
  package launch {
    class Booster2
  }
}