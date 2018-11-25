package com.vc

import cats.data.Writer

/**
  * Writer Monad
  * Carries a log along with computation.
  * For e.g. Recording sequences of steps in multithreaded computation where standard logging can result in interleaved msg
  * It helps us to identify which message come from which computation
  **/
object WriterMonad {
  def main(args: Array[String]): Unit = {
    import cats.syntax.writer._
    import cats.instances.vector._
    import cats.syntax.applicative._

    val res1 = Writer(Vector("Process Started", "Process Ended"), 123)
    println(res1) //WriterT((Vector(Process Started, Process Ended),123))

    //Writer by specifying only log
    val res2 = Vector("Process Started", "Process Ended").tell
    println(res2) //WriterT((Vector(Process Started, Process Ended),())

    //Writer by specifying only result
    type Logged[A] = Writer[Vector[String], A]
    val res3= 123.pure[Logged]
    println(res3) //WriterT((Vector(),123))

    //Writer by syntax
    val res4 = 123.writer(Vector("Process Started", "Process Ended"))
    println(res4) //WriterT((Vector(Process Started, Process Ended),123))

    //Extract log
    val logs = res4.written
    println(logs) //Vector(Process Started, Process Ended)

    //Extract value
    val value = res4.value
    println(value) //123

    //Extract both log & value
    val (log, result) = res4.run
    println(s"$log $result") //Vector(Process Started, Process Ended) 123

  }
}
