package com.github.nullptr7.part2

import scala.annotation.targetName

object InfixNotations:
  case class Person(name: String) {
    infix /*<-- Scala 3 only */ def likes(movie: String): String = s"$name likes $movie"

    // Usually infix notations are done mostly for alphanumeric methods for e.g.
    @targetName("amazedBy") // should be alphanumeric token
    infix def !!(observation: String): String = s"Wow!! $observation"

  }

  // in Scala 2
  val person = Person("Alice")

  person.likes("Forest Gump")

  person likes "Forest Gump"

  // Scala 3 - we explicit with `infix` modifier
  // We add 'infix' modifier

  // infix is a SOFT modifier (not mandatory + does not collide with name)

  // extension methods can also be infix

  extension (person: Person) infix def enjoys(musicGenre: String): String = s"${person.name} listens to $musicGenre"

  // this uses inix notation with extension method
  person enjoys "Classical Music"

  /*
    Since Scala is interoperable with Java, Java however, cannot call '!!' method as it does not allow
    So we have a concept as Target name - rename "operators" as Java-legal names
    by using annotation as "@targetName"

    Benefit
    + new method name can be called from Java
    - new method name cannot be called from Scala and should use the original name i.e. '!!' in our example
   */

  // Infix types

  infix /* since we know of the fact that this will be used as infix it is better we give with infix as its prefix*/ trait <:>[A, B]
  type Ints  = <:>[Int, Int] // can also be writtne as below
  type Ints2 = Int <:> Int
  type IntAndStringType = Int <:> String

  def main(args: Array[String]): Unit =
    println("Hello InfixNotations")
