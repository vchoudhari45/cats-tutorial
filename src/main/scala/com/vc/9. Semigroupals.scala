package com.vc

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Semigroupal is type that allows us to combine the context
  *
  * trait Semigroupal[F[_]] {
  *   def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
  * }
  *
  **/
object Semigroupals {
  def main(args: Array[String]): Unit = {
    import cats.Semigroupal
    import cats.instances.option._
    import cats.syntax.option._

    //Joining two contexts
    val res1 = Semigroupal[Option].product(123.some, "abc".some)
    println(res1) //Some((123,abc))

    //Joining three or more contexts
    val res2 = Semigroupal.tuple3(1.some, 2.some, 3.some)
    println(res2) //Some((1,2,3))

    val res3 = Semigroupal.tuple3(Option(1), Option(2), Option.empty[Int])
    println(res3) //None

    val map2Res1 = Semigroupal.map2(123.some, "abc".some)((x, y) => x.toString + y)
    println(map2Res1) //123abc

    val map2Res2 = Semigroupal.map2(123.some, Option.empty[String])((x, y) => x.toString + y)
    println(map2Res2) //None

    import cats.instances.future._
    import scala.concurrent.ExecutionContext.Implicits.global
    val semigroupalOfFuture = Semigroupal[Future].product(Future("abc"), Future(123))
    println(Await.result(semigroupalOfFuture, Duration.Inf)) //(abc,123)

    val iMap2Res1 = Semigroupal.imap2(123.some, "abc".some)((x, y) => s"${x.toString}  $y")(z => (z.split( " ")(0).toInt, ""))
    println(iMap2Res1) //Some(123  abc)

    //Apply Syntax
    import cats.syntax.apply._
    val tupledSemigroupal = (123.some, "abc".some).tupled
    println(tupledSemigroupal) //Some((123,abc))

    //mapN
    val mapNRes = (123.some, "abc".some).mapN((x, y) => x + y)
    println(mapNRes) //Some(123abc)

    //custom monoid with imapN
    import cats.Monoid
    import cats.instances.long._     // for Monoid
    import cats.instances.string._  // for Monoid
    import cats.syntax.apply._
    import cats.instances.invariant._ // for  catsSemigroupalForMonoid: InvariantSemigroupal[Monoid]
    import cats.syntax.semigroup._ // for |+|

    case class User(id:Long, name: String)
    val tupleToUser: (Long, String) => User = User.apply _
    val userToTuple: User => (Long, String) = user => (user.id, user.name)

    implicit val userMonoid: Monoid[User] = (
      Monoid[Long],
      Monoid[String],
    ).imapN(tupleToUser)(userToTuple)

    val resMonoid = User(123l, "abc") |+| User(456, "xyz")
    println(resMonoid) //User(579,abcxyz)

    import cats.instances.list._
    val semigroupalOnList = Semigroupal[List].product(List(1, 2), List(3, 4))
    println(semigroupalOnList) //semigroupalOnList: List[(Int, Int)] = List((1,3), (1,4), (2,3), (2,4))

  }
}
