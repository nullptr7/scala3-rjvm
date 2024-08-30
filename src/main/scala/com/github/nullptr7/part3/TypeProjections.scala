package com.github.nullptr7.part3

object TypeProjections:
  class Outer:
    class Inner

  val o1: Outer = new Outer
  val o2: Outer = new Outer

  // object of inner type depends instance of outer type.
  // hence o1.Inner cannot be interchanged to o2.Inner
  val i1: o1.Inner = new o1.Inner
  val i2: o2.Inner = new o2.Inner

  // In scala 3 we can also do

  val i4: Outer#Inner = new o1.Inner
  val i5: Outer#Inner = new o2.Inner
  //       ^^^ type projections

  // Below requirement works in Scala 2 but not in Scala 3
  /*
        trait ItemLike {
            type Key
        }

        trait Item[K] extends ItemLike {
            type Key = K
        }

        class StringItem extends Item[String]
        class IntItem extends Item[Int]

        def get[ItemType <: ItemLike](key: ItemType#Key): ItemType = ???
                                            ^^^ DOES NOT WORK IN SCALA 3
        get[IntItem](42) // an IntItem with 42
        get[StringItem]("Scala") // a StringItem with 42
        get[StringItem](42) // should not compile
   */

  /* 
    In Scala 3 we ONLY create type projects of a CONCRETE Class, as compared to above implementation
    ItemType <: ItemLike is an abstract class in itself.
   */  

  def main(args: Array[String]): Unit =
    println("Hello")
