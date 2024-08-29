package com.github.nullptr7.part2

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

object NewFunctionFeatures:

  /** Generics in functions
    */
  // scala 2 only had generic METHODS
  def processOption[A](option: Option[A]): String =
    option match {
      case None        => "[]"
      case Some(value) => s"[$value]"
    }

  // In Scala 3 we CAN add generics to function values
  val processOptionFunc: [A] => Option[A] => String = // <- syntax for func signature
    [A] =>
      (option: Option[A]) =>
        option match // <- syntax for func implementation
          case Some(value) => s"[$value]"
          case None        => "[]"

  /** Context Functions - functions with using clauses or implicit arguments
    */

  // In scala2 only defs(methods) can take implicit arguments

  // in Scala 2, only METHODS can have context args
  def methodWithoutContextArg(nonContextArg: Int)(nonContextArg2: String): String =
    ???

  def methodWithContextArg(
      contextArg:  Int
    )(using
      contextArg2: String
    ): String =
    ???

  // in Scala 3, also available for function values
  // mainly useful for eta-exapansion like below
  val functionWithoutContextArg: Int => String => String = methodWithoutContextArg

  // now the above works in Scala2 because it was all fine with non implicit functions,
  // The moment we add implicit arguments the scala 2 compiler breaks if we did above.
  // val functionWithContextArg = methodWithContextArg // gives compiler error

  // In scala 3, eta-expansion works with context args
  val functionWithContextArg: Int => String ?=> String = methodWithContextArg
  //                                  ^^ this argument is given

  // require given instances at the call site instead of definition
  // for e.g. in Scala 2
//   given ec: ExecutionContext = ???   // we need this to work in Scala 2
//   val incrementAsync: Int => Future[Int] = x => Future(x * 1000)

  // In scala 3 we can actually use context function like below
  val incrementAsync: ExecutionContext ?=> Int => Future[Int] = x => Future(x * 1000)
  // The above code perfectly compiles

  // later, in other part of code
  given ec: ExecutionContext = ???
  List(1, 2, 3).map(incrementAsync) // I require the EC at the call site and not at the function site

  def aFutureComputation(incrementValue: Int): ExecutionContext ?=> Future[Int] =
    Future(incrementValue + 10)

  aFutureComputation(10)  

  /** Parameter untupling
    */

  val tuples = List((1, 2), (2, 3), (3, 4))

  // below ONLY works in Scala 3
  tuples.map((a, b) => a + b) // was not possible in scala 2
  // bit heavy on the compiler side

  // In Scala 2 we usually would have done like below
  tuples.map { // partial function, Still supported in Scala 3
    case (a, b) => a + b
  }

  def main(args: Array[String]): Unit =

    println(processOptionFunc(Some(1)))         // ok because this is Option[Int]
    println(processOptionFunc(Some("Scala 3"))) // ok because this is Option[String]
    println(processOptionFunc(None))            // ok

    println("Hello new functions")
