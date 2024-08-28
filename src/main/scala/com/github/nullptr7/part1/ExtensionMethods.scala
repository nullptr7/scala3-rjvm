package com.github.nullptr7.part1

import scala.languageFeature.postfixOps

object ExtensionMethods:

  /** implicit vals/arguments -> given/using
    * implicit classes -> extension methods
    */
  implicit class MyRichInteger(number: Int):
    def isEven: Boolean = number % 2 == 0
    def isOdd:  Boolean = !isEven

  // Since implicit was overused alot, Scala 3 came up with extension methods
  // to differentiate between implicit vals/arguments and implicit classes

  extension (number: Int)
    def isEvenV2: Boolean = number % 2 == 0
    def isOddV2:  Boolean = !isEvenV2

  // similar to implicit classes we can also have generics types for extentions too

  extension [A](list: List[A]) def ends: (A, A) = (list.head, list.last)

  /*
        reason 1: make APIs more expressive
        reason 2: enhance CERTAIN types with new methods BUT NOT others (majorily used for type classes)
   */
  // e.g.

  trait Semigroup[A]:
    def combine(x: A, y: A): A

  extension [A](list: List[A])
    def combineAll(
        using
        semigroup: Semigroup[A]
      ): A =
      list.reduce(semigroup.combine)

  given intSemigroup: Semigroup[Int] with
    override def combine(x: Int, y: Int): Int = x + y

  // grouping extensions
  object GroupedExtensions:
    extension [A](list: List[A])
      def ends: (A, A) = (list.head, list.last)

      def combineAll(
          using
          semigroup: Semigroup[A]
        ): A =
        list.reduce(semigroup.combine)

    end extension // if the extension clause is long
  end GroupedExtensions

  /** Exercises:
    *   1. Replace an impicit class with an extension clause
    *   2. Add Extension method for a binary tree
    *       - map(f: A => B): Tree[B]
    *       - forAll(predicate: A => Bolean): Boolean
    *       - sum => sum of all the elements of a INTEGER tree
    */

  // Exercise 1
  implicit class PrimeChecker(n: Int):
    def isPrime: Boolean =
      def isPrimeHelper(potentialDivisor: Int): Boolean =
        if (potentialDivisor > n / 2) true
        else if (n % potentialDivisor == 0) false
        else isPrimeHelper(potentialDivisor + 1)
      end isPrimeHelper

      assert(n >= 0)
      if n == 0 || n == 1 then false
      else isPrimeHelper(2)
    end isPrime

  end PrimeChecker

  // Solution of Exercise 1
  extension (n: Int)
    def isPrime: Boolean =
      def isPrimeHelper(potentialDivisor: Int): Boolean =
        if (potentialDivisor > n / 2) true
        else if (n % potentialDivisor == 0) false
        else isPrimeHelper(potentialDivisor + 1)
      end isPrimeHelper

      assert(n >= 0)
      if n == 0 || n == 1 then false
      else isPrimeHelper(2)
    end isPrime

  end extension

  // Exercise 2
  sealed abstract class Tree[A]
  object Tree:
    extension [A](aTree: Tree[A])
      def map[B](f: A => B): Tree[B] = aTree match
        case Leaf(value)         => Leaf(f(value))
        case Branch(left, right) => Branch(left.map(f), right.map(f))

      def forAll(predicate: A => Boolean): Boolean = aTree match
        case Leaf(value)         => predicate(value)
        case Branch(left, right) => left.forAll(predicate) && right.forAll(predicate)

    end extension

  end Tree

  extension (tree: Tree[Int])
    def sum: Int =
      tree match
        case Leaf(value)         => value
        case Branch(left, right) => left.sum + right.sum
    end sum

  end extension

  case class Leaf[A](value: A)                        extends Tree[A]
  case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  def main(args: Array[String]): Unit =

    val isEven  = 2.isEven
    val isEven2 = 2.isEvenV2

    val aList     = List(1, 2, 3, 4)
    val firstLast = aList.ends

    // this works since we alreaud have semigroup for Integers
    val sum = aList.combineAll

    // but if we try doing to do so for other type where we do not have a semigroup

    val aStringList = List("black", "white")

    // this will not combine since we have not defined semigroup of String
    // val sumStr = aStringList.combineAll

    println(s"2003 is prime - ${2003.isPrime}")
    println(s"2001 is prime - ${2001.isPrime}")

    val aTree = Branch(Branch(Leaf(2), Leaf(3)), Leaf(9))

    println(s"current - ${aTree}")

    println(s"on - aTree.map(_ * 2) - ${aTree.map(_ * 2)}")
    println(s"on aTree.forAll(_ > 0) - ${aTree.forAll(_ > 0)}")
    println(s"on aTree.forAll(_ > 9) - ${aTree.forAll(_ > 9)}")
    println(s"aTree.sum - ${aTree.sum}")

    println("Hello")
