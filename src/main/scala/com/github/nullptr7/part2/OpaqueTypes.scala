package com.github.nullptr7.part2

import java.awt.Graphics

object OpaqueTypes:

  // consider e.g.
  object SocialNetwork:
    // some data structures == 'domain'
    opaque type Name = String // name of a user

    // way 1 to create a Name type
    object Name:
      def apply(str: String): Name = str

    // way 2 to create a Name type
    extension (name: String) def fromName: Name = name

    extension (name: Name) def length: Int = name.length

    // opaque type can be applied to type definitions (type aliases)

    // this allows us to use 'Name' <-> String interchangeably

    def addFriend(person1: Name, person2: Name): Boolean =
      person1.length == person2.length // use the entire string API

  // outside SocialNetwork object Name and String are NOT Related

  import SocialNetwork.*

  // will not compile since we used opaque
  /*
  val name: Name = "Daniel"
   */

  // why: so we do not pollute your APIs with the API of the type which implements the type definitons
  // we do not need or want to have access to the entire string APIs
  // basically to hide the granular details and not let anyone use the underlying APIs in this case it is string APIs

  object Graphics:
    opaque type Color                = Int // in HexCodes
    opaque type ColorFilter <: Color = Int

    val Red:              Color       = 0xff000000
    val Green:            Color       = 0x00ff0000
    val Blue:             Color       = 0x0000ff00
    val halfTransperancy: ColorFilter = 0x88 // 50%

  import Graphics.*

  case class OverlayFilter(c: Color)

  val fadeLayer = OverlayFilter(halfTransperancy)

  // How can we create instances of opaque types + how to access ot their APIs

  // 1 - through companion objects (check SocialNetwork object)
  val aName: Name = Name("Foo")

  // 2 - through extension methods
  val anotherName: Name = "Bar".fromName

  // 2 - we can also expose APIs in our case it is String APIs

  val anotherNamesLength: Int = anotherName.length

  def main(args: Array[String]): Unit =
    println("Opaque types")
