package actor

//成员balance状态一旦被赋值，便不能更改
//因而它也是线程安全的
class Person(val age: Integer) {
  def getAge() = age
}

object Person {
  //创建新的对象来实现对象状态修改
  def increment(person: Person): Person = {
    new Person(person.getAge() + 1)
  }
}
