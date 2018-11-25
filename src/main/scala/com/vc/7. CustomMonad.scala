package com.vc

/**
  * We can define a Monad for a custom type by providing implementation of three methods
  * flatmap, pure & tailRecM
  **/
object CustomMonad {

  def main(args: Array[String]): Unit = {
    import cats.Monad
    import scala.annotation.tailrec

    val optionMonad = new Monad[Option] {
      def flatMap[A, B](opt: Option[A])(fn: A => Option[B]): Option[B] = opt flatMap fn

      def pure[A](opt: A): Option[A] = Some(opt)

      @tailrec
      def tailRecM[A, B](a: A)(fn: A => Option[Either[A, B]]): Option[B] =
        fn(a) match {
          case None           => None
          case Some(Left(a1)) => tailRecM(a1)(fn)
          case Some(Right(b)) => Some(b)
        }
    }
  }

}
