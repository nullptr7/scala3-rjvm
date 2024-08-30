package com.github.nullptr7.part2

object TypeLambdas:

  /** Kinds = types of types
    * - Int, String, Person = Level 0 = value - level kinds (concrete types)
    *
    * - List, Option, Future, Map = Level 1 kind ("generics")
    *
    * - Monad, Functor = Level 2 ("generics of generics")
    */
  val aNumber: Int       = 42            // level 0
  val aList:   List[Int] = List(1, 2, 3) // level 1 as we call it list of Ints
//              ^^ type constructor: Need to pass a type argument => Level 0 type

  type MyList[A] = List[A]
  type MyListV2  = [A] =>> List[A] // type lambda, same as above MyList[A]

  val aListV2: MyListV2[Int] = List(1, 2, 3) // okay

  type MyMap[K, V] = Map[K, V]
  type MyMapV2     = [K, V] =>> Map[K, V]

  // This is fairly useful in higher kinded types
  class Functor[F[_]]
  //    ^^ type constructor: need to pass a level-1 type to obtain a level-0 type

  val functorOption = new Functor[Option]

  type MyFunctor[F[_]] = Functor[F]
  type MyFunctorV2     = [F[_]] =>> Functor[F]

  // why do we need type lambdas

  // generalizations or simplifications of types

  type MySpecialMap = [A] =>> Map[String, A]

  val anAddressBook: MySpecialMap[String] = Map() // consice

  // another - to reduce the number of arguments of complex types

  // Example: ZIO
  class ZIO[R, E, A]

  // In many scenarios we really do not need R, E and just want to use A.
  // For e.g. if we want to integrate cats-effect with ZIO

  trait Monad[M[_]]:
    def flatMap[A, B](ma: M[A])(transformation: A => M[B]): M[B]

  /** We create [[ZIOMonad]] with only two type arguments R & E and keep 'A' the value type that has to be wrapped with a affect i.e. Monad
    */
  class ZIOMonad[R, E] extends Monad[[A] =>> ZIO[R, E, A]]:

    // Notice the signature it is 'exactly' what we need
    override def flatMap[A, B](ma: ZIO[R, E, A])(transformation: A => ZIO[R, E, B]): ZIO[R, E, B] = new ZIO

  /** Exercise:
    *        Define a Monad datatype for Either
    */

  val aLeft:  Either[String, Int] = Left("Scala")
  val aRight: Either[String, Int] = Right(42)

  // Implementation
  class EitherMonad[E] extends Monad[[R] =>> Either[E, R]]:
    override def flatMap[A, B](ma: Either[E, A])(transformation: A => Either[E, B]): Either[E, B] =
      ma match
        case Left(error)  => Left(error)
        case Right(value) => transformation(value)

  def main(args: Array[String]): Unit =
    println("hello")
