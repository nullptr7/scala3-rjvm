package com.github.nullptr7.part2

object Exports:
  class PhysicsCalculator:
    val SPEED_OF_LIGHT = 299792458
    def computeEnergy(mass: Double) = mass * SPEED_OF_LIGHT * SPEED_OF_LIGHT

  object ScienceApp:
    val physicsCalculator = new PhysicsCalculator

    // Scala 3 - export clause: create alias for field/method to use locally
    export physicsCalculator.computeEnergy

    def fusionReactor(): Unit =
      println(computeEnergy(0.001))

  // This works as well
  println(ScienceApp.computeEnergy(0.001))

  // This works as well when we bring via import all
  import ScienceApp.*
  println(ScienceApp.computeEnergy(0.001))

  def main(args: Array[String]): Unit =
    println("Hello")
