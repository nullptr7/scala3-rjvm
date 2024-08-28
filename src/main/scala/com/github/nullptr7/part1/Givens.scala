object Givens:
  private case class Person(name: String, age: Int)

  private val people = List(
    Person("Daniel", 99),
    Person("Alice", 23),
    Person("Master Yoda", 908),
  )

  // Scala 2
//   implicit val personOrdering: Ordering[Person] = new Ordering[Person] {

//     override def compare(x: Person, y: Person): Int =
//       x.name.compareTo(y.name)
//   }

//   given personOrdering: Ordering[Person] with
//     override def compare(x: Person, y: Person): Int =
//         x.name.compareTo(y.name)

// alternative way

  private given personOrdering: Ordering[Person] = new Ordering[Person]:
    override def compare(x: Person, y: Person): Int =
      x.name.compareTo(y.name)

  private def aMethodWithOrdering(
      person:   List[Person]
    )(implicit
      ordering: Ordering[Person]
    ) = person.sorted

  private def aMethodWithOrdering2(
      person:   List[Person]
    )(using
      ordering: Ordering[Person]
    ) = person.sorted

  /** Synthesize new implicit values
    */
  /*
  implicit private def optionOrdering[T](
      implicit
      ordering: Ordering[T]
    ): Ordering[Option[T]] =
    new Ordering[Option[T]]:
      override def compare(x: Option[T], y: Option[T]): Int =
        (x, y) match
          case (None, None)       => 0
          case (None, _)          => -1
          case (_, None)          => 1
          case (Some(a), Some(b)) => ordering.compare(a, b)
      end compare
  end optionOrdering
   */
  // In Scala3 way
  private given optionOrderingV2[T](
      using
      ordering: Ordering[T]
    ): Ordering[Option[T]] with
    override def compare(x: Option[T], y: Option[T]): Int =
      (x, y) match
        case (None, None)       => 0
        case (None, _)          => -1
        case (_, None)          => 1
        case (Some(a), Some(b)) => ordering.compare(a, b)

  private def methodWithImplicitInt(
      implicit
      value: Int
    ): Int = value * 10

  private def methodWithUsingInt(
      using
      value: Int
    ): Int = value * 10

  // with implicit value scala 2 style

  implicit val aNumber: Int = 10

  println(methodWithImplicitInt)
  println(methodWithUsingInt)

  println(methodWithImplicitInt(100))    // legal
  println(methodWithUsingInt(using 100)) // in Scala 3 we write like this to explicitly give some values unline scala2

  private object PersonGives:
    given ageOrdering: Ordering[Person] with
      override def compare(x: Person, y: Person): Int =
        y.age - x.age

  def main(args: Array[String]): Unit = {

    // 1 - import the explicit given
    import PersonGives.ageOrdering

    // 2 - import a given for a certain type
    import PersonGives.given Ordering[Person]

    // 3 - import all givens
    import PersonGives.given

    // when import is done like below
    import PersonGives.* // this will NOT import the givens!

    /** implicitly
      */

    // this is how implicitly works
    def aMethodWithImplicitArg[T](implicit instance: T): T = instance

    // in Scala 3 we have summon[T] and works similar to below

    def aMethodWithGivenArg[T](using instance: T): T = instance

    ???
  }
