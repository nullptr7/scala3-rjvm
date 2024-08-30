package com.github.nullptr7.part2

object MatchTypes:

  // Rarely used but good to know.
  /*
    Let's say we need to create a library that extract last constitutents of a given data structure

    for e.g.
    // Bit Ints -> last digit of type Int
    // String -> last char
    // List -> last element
   */
  def lastDigitOf(number: BigInt): Int =
    (number % 10).toInt

  def lastCharOf(string: String): Char =
    if string.isEmpty then throw new NoSuchElementException
    else string.charAt(string.length() - 1)

  def lastElementOfList[T](list: List[T]): T =
    if list.isEmpty then throw new NoSuchElementException
    else list.last

  // Scala 2 - we cannot unfortunately do this.

  // Scala 3 - yes

  type ConstituentPartOf[T] =
    T match
      case BigInt  => Int
      case String  => Char
      case List[t] => t

  val aDigit:          ConstituentPartOf[BigInt]    = 2   // okay
  val aChar:           ConstituentPartOf[String]    = 'a' // okay
  val anElementOfList: ConstituentPartOf[List[Int]] = 42  // okay

  // Generic method
  def lastComponentOf[T](biggerValueOfTypeT: T): ConstituentPartOf[T] =
    biggerValueOfTypeT match
      case b: BigInt  => (b % 10).toInt
      case s: String  =>
        if s.isEmpty then throw new NoSuchElementException else s.charAt(s.length - 1)
      case l: List[_] =>
        if l.isEmpty then throw new NoSuchElementException
        else l.last

  val lastDigit   = lastComponentOf(BigInt(23456674)) // o/p - 4
  val lastChar    = lastComponentOf("scala")          // o/p - 'a'
  val lastElement = lastComponentOf(List(1, 2, 3))    // o/p - 3

  /** Why do we even use this kind of feature i.e. 'MATCH TYPES'
    *
    * We would usually want to use if we have methods that are returning potentially unrelated types which are dependent on the input types.
    * This can be checked at compile time hence it can be of great help for a developer
    *
    * QUESTION??
    * Why is this different from OOP
    *
    * def returnLastConstituentOf(thing: Any): ConstituentPart = thing match ...
    * The above is Java style, and it does work but here 'Any' is a wider context and it means there will be no help from Compiler whatsoever
    * As everything falls to Any
    *
    * Why is this different from generics?
    * the above is more subtle.
    * for e.g.
    */

  def lastElementOfListGeneric[A](list: List[A]): A = list.last

  /** The limitation we see above is, the return type has a direct relationship to the input type
    * meaning, if the input is list[A] then the output will be of type A.
    *
    * Whereas in the method [[lastComponentOf]] it does not have any relationship meaning, BigInt as input
    * returns Int as output and so on.
    * Basically, in the method [[lastComponentOf]] the relationship between the input type and the return type is loose. However,
    * the compiler still knows what will be type and give us better feedback as it is coverd by the type level pattern matching
    * [[ConstituentPartOf]] which will be impossible to create using regular generics
    */

  // recursion of match types
  type LowestLevelPartOf[T] =
    T match
      case List[t] => LowestLevelPartOf[t]
      case _       => T

  val lastElementOfNestedList: LowestLevelPartOf[List[List[List[Int]]]] = 2

  /** The compiler is smart enough to know of any cyclic dependencies like below.
    */
  /*
  type AnnoyingMatchType[T] =
    T match
        case _ => AnnoyingMatchType[T]
   */

  /** Compiler will also detect infinite recursion for e.g. below
    */

  type InfiniteRecursiveType[T] =
    T match
      case Int => InfiniteRecursiveType[T]

  def aNaiveMethod[T]: InfiniteRecursiveType[T] = ???

  // val anIllegal: Int = aNaiveMethod[Int] //this will give compiler error

  /** Current limitation:
    *        The matchType in scala works only like how [[lastComponentOf]] is written, where we have to do patter match and so on.
    *        Anything beyond that is not supported for e.g. [[accumulateValue]] we cannot deduce as compiler will lose track of the type and result in compiler error
    *        Hence '???'
    */

  /* Below gives compiler error even for the fact we know that this is BigInt only
  def accumulateValue[T](accumulate: T, smallerValue: ConstituentPartOf[T]): T =
    accumulate match
        case b: BigInt => b + smallerValue
   */

  def accumulateValue[T](accumulate: T, smallerValue: ConstituentPartOf[T]): T =
    ???

  def main(args: Array[String]): Unit =
    println("Hello")
