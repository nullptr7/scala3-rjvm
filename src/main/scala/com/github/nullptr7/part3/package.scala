package com.github.nullptr7

package object part3:
  val aPackageLevelValue    = "Scala 3"
  def aPackageLevelMethod() = println("this comes from a package object")

  class PackageLevelClass(data: String)
  trait PackageLevelTrait
  object PackageLevelObject
  type PackageObjectList[A] = List[A]
