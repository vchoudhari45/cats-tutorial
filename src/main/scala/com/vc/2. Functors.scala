package com.vc

import cats.Functor
import scala.concurrent.Future
import scala.language.higherKinds

/**
  * Functors: Abstraction to represent sequences of operations
  * Monads & Applicative
  *
  * import scala.language.higherKinds
  * trait Functor[F[_]] {
  *   def map[A, B](fa: F[A])(f: A => B): F[B]
  * }
  *
  * Composition Law
  **/
object Functors extends App {

  import cats.instances.future._
  import scala.concurrent.ExecutionContext.Implicits.global
  //Can be rewritten as Future("Hello ").map(_ + "Future Functor") but then scala uses in-build map method
  val futureMapUsingFunctor = Functor[Future].map(Future("Hello "))(_ + "Future Functor")
  println(futureMapUsingFunctor)

  import cats.instances.list._
  //Can be rewritten as List(2, 4, 6).map(_ * 2) but then scala uses in-build map method
  val listMapUsingFunctor = Functor[List].map(List(2, 4, 6))(_ * 2)
  println(listMapUsingFunctor)

  import cats.instances.option._
  //Can be rewritten as Some(1).map(_ * 2) but then scala uses in-build map method
  val optionMapUsingFunctor = Functor[Option].map(Some(1))(_ * 2)
  println(optionMapUsingFunctor)

  //lift a function
  val func = (a: Int) => a + 1
  val liftedFunc = Functor[List].lift(func)
  println(liftedFunc(List(1, 2, 3)))

  //compose
  import cats.instances.list._
  import cats.instances.option._
  val listOption = List(Some(1), None, Some(2))
  val listOptionMapUsingFunctor = Functor[List].compose[Option]
  println(listOptionMapUsingFunctor.map(listOption)(_ * 2))

}
