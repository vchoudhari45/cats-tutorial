package com.vc

import cats.{Eval, Monad}

import scala.util.Try

/**
  * Monad is mechanism for sequencing computation but also takes into consideration intermediate complication
  * For e.g. intermediate Option Type
  * Every monad is also a functor
  *
  * trait Monad[F[_]] {
  *   def pure[A](value: A): F[A]
  *   def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]
  * }
  *
  * Left Identity, Right Identity & Associativity
  **/
object Monads extends App {

  import cats.instances.option._
  val opt1 = Monad[Option].pure(2)
  println(opt1)

  import cats.instances.option._
  val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
  println(opt2)

  import cats.instances.option._
  val opt3 = Monad[Option].map(opt1)(a => a + 1)
  println(opt3)

  import cats.instances.list._
  val list1 = Monad[List].flatMap(List(1, 2, 3))(a => List(a * 10))
  println(list1.mkString(", "))

  // Won't compile it will if a was of type monad and a can be converted to monad by cats.Id
  // val a = 3
  // val b = 4
  // val c = for {
  //   x <- a
  //   y <- b
  // } yield x + y
  // println(c)

  import cats.syntax.functor._
  import cats.syntax.flatMap._
  import cats.Id
  val a = Monad[Id].pure(3)
  val b = Monad[Id].pure(4)
  val c = for {
    x <- a
    y <- b
  } yield x + y
  println(c)

  val either1: Either[Exception, Int] = Right(10)
  val either2: Either[Exception, Int] = Left(new Exception("some exception"))

  //Instead of specifying type definition we can cats either constructor as below
  import cats.syntax.either._
  val either3 = 10.asRight[Exception]

  val res = for {
    a <- either1
    b <- either2
  } yield a + b

  println(res)

  //catchOnly & catchNonFatal
  val either4 = Either.catchOnly[NumberFormatException]("foo".toInt)
  println(s"either4 $either4")

  //Below will throw an NumberFormatException to console
  //val either5 = Either.catchOnly[NullPointerException]("foo".toInt)

  val either6 = Either.catchNonFatal(sys.error("NonFatal Error"))
  println(s"either6 $either6")

  val either7 = Either.fromTry(Try("Foo".toInt))
  println(s"either7 $either7")

  val either8 = Either.fromTry(Try("3".toInt))
  println(s"either8 $either8")

  val either9 = Either.fromOption(Some(4), new Exception("No value specified"))
  println(s"either9 $either9")

  //Ensure
  val either10 = (-1).asRight[Exception].ensure(new Exception("Must be non-negative"))(_ > 0)
  println(either10)

  //recover & recoverWith
  val either11 = either10.recover{
    case e: Exception => 1
  }
  println(either11)

  val either12 = either10.recoverWith {
    case e: Exception => Left(new Exception("Ok recovering with new exception"))
  }
  println(either12)

  val either13 = either12.leftMap(_.getMessage)
  println(either13)

  val either14 = either12.bimap(_.getMessage, _ * 1)
  println(either14)

  val either15 = either12.swap
  println(either15)


  /**
    *   scala       cats
    *   val         Now         eager, memoized
    *   lazy val    Later       lazy, memoized
    *   def         Always      lazy, not memoized
    **/

    val x = { math.random() }                   //Always same value on accessing x
    lazy val xLazy = { math.random() }         //Always same value on accessing xLazy but evaluated on first access
    def xDef = { math.random() }      //Always different value


    //Eval.defer makes recursive method stack safe
    def factorial(n: BigInt): BigInt =
      if(n == 1) n else n * factorial(n - 1)

    def factorialEval(n: BigInt): Eval[BigInt] =
      if(n == 1) Eval.now(n)
      else Eval.defer(factorialEval(n - 1).map(_ * n))


}
