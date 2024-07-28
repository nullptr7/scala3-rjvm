package com.github.nullptr7.part1

import scala.annotation.tailrec

object Syntax:
  @main
  def main(): Unit =
    val ifScala2Expression = if (2 > 3) "greater than 3" else "less than 3"    // scala2
    val ifScala3Expression = if 2 > 3 then "greater than 3" else "less than 3" // scala3

    // multiline ifs

    val ifExpression_v3 =
      if (2 > 3) {
        val result = "bigger"
        result
      }
      else {
        val result = "smaller"
        result
      }

    // scala3 braceless
    val ifExpression_v4 =
      if 2 > 3 then
        val result = "bigger"
        result
      else
        val result = "smaller"
        result

    val whileExpression: Unit = while (2 > 3) {
      println("bigger")
      println("much bigger")
    }

    // scala 3 braceless
    val whileExpressionScala3: Unit =
      while 2 > 3 do
        println("bigger")
        println("much bigger")

    // for
    val forComprehension: List[String] =
      for {
        num  <- List(1, 2, 3)
        char <- List('a', 'b')
      } yield s"$num $char"

    val forComprehension_v2: List[String] =
      for
        num  <- List(4, 2, 3)
        char <- List('a', 'b')
      yield s"$num $char"
    // match

    val meaningOfLife = 42
    val aPatterMatch  = meaningOfLife match {
      case 1 => "the one"
      case 2 => "double or nothing"
      case _ => "something else"
    }

    // Scala 3 braceless
    val aPatterMatch_v2 =
      meaningOfLife match
        case 1 => "another one"
        case 2 => "double or nothing"
        case _ => "something else"

    // try-catch
    val tryCatch =
      try {
        println("".charAt(2))
        println("ahead")
      }
      catch {
        case e: Exception =>
          println("error here")
      }

    val tryCatchV2 =
      try
        println("".charAt(2))
        println("ahead1")
      catch
        case nullPtr: NullPointerException => println(s"This will never reach here ${nullPtr.getMessage}")
        case e:       Exception            => println(e.getMessage)

    def isPrime(n: Int): Boolean =
      @tailrec def aux(potentialDivisor: Int): Boolean =
        if potentialDivisor > n / 2 then true
        else if n % potentialDivisor == 0 then false
        else aux(potentialDivisor + 1)
      aux(2)
    end isPrime // optional token usually used for large code blocks
    // can be applied to if, while, match, for, methods, classes etc. but it is optional.

    class Animal:
      def eat(): Unit =
        println("I am eating")
      end eat // optional token

      def grow(): Unit =
        println("I am growing")
      end grow // optional token

    end Animal // optional token

    println(ifScala2Expression)
    println(ifScala3Expression)
