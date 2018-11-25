package com.vc

/**
  * StateMonad allows us to pass additional state around as part of a computation
  *
  **/
object StateMonad {

  def main(args: Array[String]): Unit = {
    import cats.data.State

    val step1 = State[Int, String] { num =>
      val ans = num + 1
      (ans, s"Result of step1: $ans")
    }

    val step2 = State[Int, String] { num =>
      val ans = num * 2
      (ans, s"Result of step2: $ans")
    }

    val both = for {
      a <- step1
      b <- step2
    } yield (a, b)

    val (state, result) = both.run(20).value
    println(s"State: $state Result: $result") //State: 42 Result: (Result of step1: 21,Result of step2: 42)

  }

}
