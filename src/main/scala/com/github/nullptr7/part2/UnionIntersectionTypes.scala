package com.github.nullptr7.part2

object UnionIntersectionTypes:

  // union types
  val truthOr42: Boolean | Int = 42

  def ambivalentMethod(arg: String | Int): String =
    arg match
      case _: Int    => "a string"
      case _: String => "a number"

  // this opens a lot of possibilities but be cautious with type inference requirement for e.g.
  val stringOrInt = if 42 > 0 then "a string" else 42 // takes the lowest ancestor which in our case is ANY

  // but this will work if we explicitly provide the return type
  val stringOrInt2: String | Int = if 42 > 0 then "a string" else 42

  // opens the door for flow typing - explicit null checking

  type Maybe[T] = T | Null

  def handleMaybe(someValue: Maybe[String]): Int =
    /*
        Here the scala 3 compiler knows it is exactly a String and NOT null

        This is called flow typing, because compiler can identify or narrow down the value as the code progresses

        currently it is restricted to null checking only
     */
    if someValue != null then someValue.length // this was not possible in Scala 2
    else -1

  // this kind of flow typing does not work
  /*
  type ErrorOr[T] = T | "error"

  def handleResource(arg: ErrorOr[Int]): Unit =
    if arg != "error" then println(arg + 1) // this is not possible in scala 3
    else println("error")
   */

  // Intersection types
  class Animal
  trait Carnivore
  class Crocodile extends Animal with Carnivore

  val carnivoreAnimal: Animal & Carnivore = new Crocodile

  // Intersection types can pose some problem and we should be careful abount it.
  // for e.g. diamond problem

  trait Gadget:
    def use(): Unit

  trait Camera extends Gadget:
    def takePicture():  Unit = println("smile!")
    override def use(): Unit = println("snap!")

  trait Phone extends Gadget:
    def makePhoneCall(): Unit = println("dialling...")

    override def use(): Unit = println("ring ring...")

  def useSmartDevice(cp: Camera & Phone): Unit =
    cp.takePicture()   // called from camera
    cp.makePhoneCall() // called from phone
    cp.use()           // ??? depends on how camera and phone are inherited (order) - trait linearization
    // which trait ends up later in the inheritance chain will be used

  class SmartPhone      extends Phone with Camera // use() will be "snap!"
  class CameraWithPhone extends Camera with Phone // use() will be "ring ring..."

  // intersection types + coveriance
  trait HostConfig:
    def getHostName: String

  trait HostController:
    def get: Option[HostConfig]

  trait PortConfig:
    def getPortNumber: Int

  trait PortController:
    def get: Option[PortConfig]

  def getConfigs(controller: HostController & PortController): Option[HostConfig & PortConfig] =
    controller.get // this is good since now we can get both 'getHostName' & 'getPortNumber'

  def main(args: Array[String]): Unit =

    val aNumberDecription = ambivalentMethod(55)
    val aStringDecription = ambivalentMethod("String")

    println(aNumberDecription)
    println(aStringDecription)
    println("=" * 50)

    useSmartDevice(new SmartPhone)
    useSmartDevice(new CameraWithPhone)
    println("=" * 50)
