package com.vc

/**
  * Validated(Semigroupal)
  *
  * Validated complements Either nicely but Whether to use Either or Validated as a default is determined by context
  * In application code, we typically find areas that favour accumulating semantics, we use validated (For e.g. Form Validation where we need to accumulate all the errors)
  * and areas that favour fail-fast semantics, we use either
  *
  **/
object Validated_ {
  def main(args: Array[String]): Unit = {

    import cats.data.Validated

    //Apply method
    val res1 = Validated.Valid(123)
    println(res1) //Valid(123)

    val res2 = Validated.Invalid(List("Badness"))
    println(res2) //Invalid(List(Badness))

    //Smart Constructors
    val res3 = Validated.valid[List[String], Int](123)
    println(res3) //Valid(123)

    val res4 = Validated.invalid[List[String], Int](List("Badness"))
    println(res4) //Invalid(List(Badness))

    //Using syntax
    import cats.syntax.validated._
    val res5 = 123.valid[List[String]]
    println(res5) //Valid(123)

    val res6 = List("Badness").invalid[Int]
    println(res6) //Invalid(List(Badness))

    //With helper methods
    val res7 = Validated.catchOnly[NumberFormatException]("foo".toInt)
    println(res7) //Invalid(java.lang.NumberFormatException: For input string: "foo")

    //Combining instances of Validated
    import cats.Semigroupal
    import cats.instances.string._ // for Semigroup
    type AllErrorsOr[A] = Validated[String, A]
    val res8 = Semigroupal[AllErrorsOr].product(
      "Error 1".invalid[Int],
      "Error 2".invalid[Int]
    )
    println(res8) //Invalid(Error 1Error 2)

    import cats.Semigroupal
    import cats.instances.vector._ // for Semigroup
    type AllErrorsInVectorOr[A] = Validated[Vector[String], A]
    val res9 = Semigroupal[AllErrorsInVectorOr].product(
      Vector("Error1").invalid[Int],
      Vector("Error1").invalid[Int],
    )
    println(res9) //Invalid(Vector(Error 1, Error 2))

  }
}
