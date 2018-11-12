package com.vc

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

}
