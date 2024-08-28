package com.github.nullptr7.part1

object MinorChanges:

  /*
        Minor changes
   */

  // import everything
  import scala.concurrent.*

  // aliases in scala 2
  import java.util.{ List => JList }

  // aliases in Scala 3
  import java.util.{ List as JList, Map as JMap }

  // import everything BUT something
  import java.util.{ List as _ /* <-- import to be ignored */, Comparator as _ /*<-- imports to be ignored*/, * }

  /** varargs
    */

  val aList   = List(1, 2, 3, 4)
  val anArray = Array(aList.head, aList(1), aList(2), aList(3))

  // Scala 2
  val anArrayV2 = Array(aList: _*)

  // Scala 3
  val anArrayV3 = Array(aList*) // consistent with vararg pattern match

  /** Trait constructor arguments possible in Scala 3
    */

  trait Person(name: String) // legal now

  // Most of the features are same as abstract class but
  // the a child trait or class cannot pass direct arguments as below
  // trait Kid extends Person("Alice")
  // this was restricted to solve the diamond problem
  /* Below is a diamond problem
  trait JPerson extends Person("Alice")
  trait APerson extends Person("Bob")
  trait Kid extends JPerson with APerson
   */


  /* 
    "Universal Constructors", == apply methods everywhere
   */ 

  class Pet(name: String)

  val aPet = Pet("Cat")  // totally allowed now in Scala3
  val aPet1 = new Pet("Dog") // scala 2 style
  def main(args: Array[String]): Unit =
    println("Minor changes")
