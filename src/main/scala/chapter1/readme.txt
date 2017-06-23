 Option,None,Some类型
 Option、None、Some是scala中定义的类型，它们在scala语言中十分常用，
 因此这三个类型非学重要。
 None、Some是Option的子类，它主要解决值为null的问题，
 在Java语言中，对于定义好的HashMap，如果get方法中传入的键不存在，方法会返回null，
 在编写代码的时候对于null的这种情况通常需要特殊处理，然而在实际中经常会忘记，
 因此它很容易引起 NullPointerException异常。
 在Scala语言中通过Option、None、Some这三个类来避免这样的问题，
 这样做有几个好处，首先是代码可读性更强，
 当看到Option时，我们自然而然就知道它的值是可选的，然后变量是Option，
 比如Option[String]的时候，直接使用String的话，编译直接通不过。