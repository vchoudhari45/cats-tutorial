package com.vc

/**
  * MonadTransform allows us to wrap stacks of monads to produce new monads
  *
  * OptionT
  * EitherT
  * ReaderT
  * WriterT
  * StateT
  * IdT
  **/
object MonadTransform {

  def main(args: Array[String]): Unit = {
    import cats.data.OptionT
    type ErrorOr[A] = Either[String, A]

    //Below Left Or Right of Some type A, outermost monad is applied first in this case Option is applied to type A first
    type ErrorOrOption[A] = OptionT[ErrorOr, A]

    import cats.syntax.applicative._
    import cats.instances.either._


    //Right side value
    val res1 = 10.pure[ErrorOrOption]
    println(res1) //OptionT(Right(Some(10)))

    val res2 = "abc".pure[ErrorOrOption]
    println(res2) //OptionT(Right(Some(abc)))
  }

}
