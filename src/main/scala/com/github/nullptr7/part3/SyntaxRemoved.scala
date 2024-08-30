package com.github.nullptr7.part3

object SyntaxRemoved:

  /*
    Scala 2
   */
  /*
    do-while instructions
    This was supported in Scala 2 but NOT in scala 3
    var i = 0
    do {
        println(i)
        i += 1
    } while(i < 10)
   */
  // Methods returning Unit

  /*
    This use to work in Scala 2 where notice we do not have "=" sign, this still used to work for Unit return types
    This DOES NOT work in Scala 3
    def sayHi(): Unit {}
   */

  // limit 22
  // In scala 2 - we are limited to writing 22 or less arguments for Functions, Case Classes, tuples 

  // parameterless syntax

  def aParameterlessMethod = 42
  def aMethodWithEmptyArgList() = 42

  // In Scala 2, you can call both with the parameterless syntax

  val meaningOfLife = aParameterlessMethod // okay

  // This was allowed in scala 2 to call '()' irrespective if we have defined as parameterless or empty arguments
//   val meaningOfLifeParameterLess = aParameterlessMethod()

  // Below used to work in Scala 2 but not we have to add '()' to call the method with 'empty arguments'
  // val meaningOfLifeV2 = aMethodWithEmptyArgList
  val meaningOfLifeV2 = aMethodWithEmptyArgList()


  // Uninitialized vars
  // In Scala 2
  var toAssignLater: Int = _ //okay in Scala 2 but will be phased out in future Scala 3 versions

  toAssignLater = 87 //ok

  // Scala 3 recommeded style
  import scala.compiletime.uninitialized

  var toBeSetLater: Int = uninitialized

  toBeSetLater = 87

  def main(args: Array[String]): Unit =
    println("Hello")
