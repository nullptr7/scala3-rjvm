package com.github.nullptr7.part1

object ImplicitConversions:
  case class Person(name: String):
    def greet: String = s"Hey, my name is $name, Scala rocks!"


  // In Scala 2 we can replace a string to Person automatically like below

  // below is an implicit conversion like we do in Scala 2
/*   implicit def string2Person(string: String): Person = Person(string)

  val daniel: Person = "Daniel"

  val greetMessage: String = "Daniel".greet */

  // such kind of implicit conversions are discouraged since it is very difficult to comprehend about the nature of the types
  // debugging in large code bases becomes difficult

  // In Scala 3: we add implicit conversions explicitly
  // step 1: import the implicit conversion support

  import scala.language.implicitConversions

  // step2: defined a given value of type 'Conversion'

  given string2Person: Conversion[String, Person] with
    override def apply(x: String): Person = Person(x)

  // 1 - use methods of the converted type
  "Daniel".greet

  val person: Person = "Daniel"

  def sayHiTo(person: Person): Unit =
    println(s"Hi ${person.name}")

  sayHiTo("Alice")  

  def main(arg: Array[String]): Unit =
    println("hello")
