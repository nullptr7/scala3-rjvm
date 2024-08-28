package com.github.nullptr7.part1

object NotGivens:

  // some library code
  def processList[A, B](la: List[A], lb: List[B]): List[(A, B)] =
    for
      a <- la
      b <- lb
    yield (a, b)
  end processList

  // requirement: be able to call processList ONLY IF the types A & H are different

  val combinedList = processList(List(1, 2, 3), List("black", "white"))

  val combinedNumbers = processList(List(1, 2, 3), List(4, 5)) // want this to give a compiler error since but the arguments are of same time.

  class <:>[A, B]
  // make the compiler create a <:>[A, A]

  given equalType[A]: <:>[A, A] with {}

  object DifferentTypes:
    import scala.util.NotGiven

    // if a compiler cannot find a given T => then the compiler will generate a NotGiven[T]
    def processListWithDifferentType[A, B](
        la: List[A],
        lb: List[B],
      )(using
        NotGiven[A <:> B]
      ): List[(A, B)] =
      processList(la, lb)

  def main(arg: Array[String]): Unit =
    println("Hello world")

    import DifferentTypes.*
    val combinedList = processListWithDifferentType(List(1, 2, 3), List("black", "white"))

    // This will give compilation error and hence we are forcing this method to provide list of different data types
    // It also does not work for type aliaes

    type type1 = List[Int]
    type type2 = List[Int]
    val aListOfType1: type1 = List(1, 2, 3)
    val aListOfType2: type2 = List(4, 5, 6)

    // processListWithDifferentType(aListOfType1, aListOfType2)

    // val combinedNumbers = processListWithDifferentType(List(1,2,3), List(3,4,5))

  end main

end NotGivens
