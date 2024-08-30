package com.github.nullptr7.part2

object Enums:
  enum Permissions:
    case READ, WRITE, EXECUTE, NONE

    // add fields/methods

    def openDocument(): Unit =
      if this == READ then println("opening document...")
      else println("reading not allowed")

  val somePermisisons: Permissions = Permissions.READ

  // enums can also take constructor args

  enum PermissionsWithBits(bits: Int):
    case READ    extends PermissionsWithBits(4)
    case WRITE   extends PermissionsWithBits(2)
    case EXECUTE extends PermissionsWithBits(1)
    case NONE    extends PermissionsWithBits(0)

  // we can have companion objects for enums too
  object PermissionsWithBits:
    def fromBits(bits: Int): PermissionsWithBits = // whatever we want to do
      PermissionsWithBits.NONE

  // some out of the box methods in enums
  val somePermissionsOrdinal = somePermisisons.ordinal

  val allPermisions = PermissionsWithBits.values // array of all permissions

  val readPermission: Permissions = Permissions.valueOf("READ")

  val permissionsWithBits = PermissionsWithBits.valueOf("READ")

  def main(args: Array[String]): Unit =
    somePermisisons.openDocument()
    println(somePermissionsOrdinal)
    println(allPermisions.mkString(","))
    println(readPermission)
    println(permissionsWithBits)
    println("Hello")
