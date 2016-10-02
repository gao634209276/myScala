package chapter3

/**
  * 下面给出最为常用的几种正则表达式的操作来领略正则表达式的魅力及它在scala中是如何使用的。
  */
//1 匹配邮箱：
object RegexMatch1 {
	def main(args: Array[String]): Unit = {
		val sparkRegex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$".r
		for (matchString <- sparkRegex.findAllIn("zhouzhihubeyond@sina.com")) {
			println(matchString)
		}
	}
}

//2 匹配网址：
object RegexMatch2 {
	def main(args: Array[String]): Unit = {
		val sparkRegex = "^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\s*)?$".r
		for (matchString <- sparkRegex.findAllIn("http://www.xuetuwuyou.com")) {
			println(matchString)
		}
	}
}

//3 匹配手机号码：
object RegexMatch3 {
	def main(args: Array[String]): Unit = {
		val sparkRegex = "(86)*0*13\\d{9}".r
		for (matchString <- sparkRegex.findAllIn("13887888888")) {
			println(matchString)
		}
	}
}

//4 匹配IP地址：
object RegexMatch4 {
	def main(args: Array[String]): Unit = {
		val sparkRegex = "(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)".r
		for (matchString <- sparkRegex.findAllIn("192.168.1.1")) {
			println(matchString)
		}
	}
}

/**
  * 在scala中，有一个非常非常强大的功能，那就是提取器（Extractor），
  * 在后面我们会专门拿一讲来讲解scala的提取器，本节只是简单演示一下提取器是如何与scala中的正则表达式一直使用的。
  *
  * 5 提取匹配的IP地址子段：
  */
object RegexMatch5 {
	def main(args: Array[String]): Unit = {
		val ipRegex = "(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)".r
		for (ipRegex(one, two, three, four) <- ipRegex.findAllIn("192.168.1.1")) {
			println("IP子段1:" + one)
			println("IP子段2:" + two)
			println("IP子段3:" + three)
			println("IP子段4:" + four)
		}
	}
}

//提取邮箱中的用户名：
object RegexMatch6 {
	def main(args: Array[String]): Unit = {
		val sparkRegex = "^([\\w-]+(\\.[\\w-]+)*)@[\\w-]+(\\.[\\w-]+)+$".r
		for (sparkRegex(domainName, _*) <- sparkRegex.findAllIn("zhouzhihubeyond@sina.com")) {
			println(domainName)
		}
	}
}