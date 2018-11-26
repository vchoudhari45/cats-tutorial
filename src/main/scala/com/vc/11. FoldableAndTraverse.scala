package com.vc

import scala.concurrent.{Await, Future}

/**
  * Foldable abstracts foldLeft and foldRight into a type class
  *
  **/
object Foldable_ {
  def main(args: Array[String]): Unit = {
    import cats.Foldable
    import cats.instances.option._ // for Foldable

    val maybeInt = Some(123)
    val res1 = Foldable[Option].foldLeft(maybeInt, 10)(_ * _)
    println(res1)

    //foldLeft & foldRight from cats are not stack safe
    def res2 = (1 to 100000).toStream
    //val res2 = bigData.foldRight(0L)(_ + _) //stackoverflow exception


    //Traverse
    val hostUptime = List(10, 10, 20)
    import scala.concurrent.ExecutionContext.Implicits.global

    //Below method gives Future of Int given a Int
    def getUptime(hostUptime: Int): Future[Int] = Future(hostUptime)

    //Future.traverse takes a list of Int and apply the getUptime function and return the composite Future
    val allUptimes: Future[List[Int]] = Future.traverse(hostUptime)(getUptime)

    import scala.concurrent.duration._
    val res3 = Await.result(allUptimes, 1.second)
    println(res3) //List(10, 10, 20)

  }
}
