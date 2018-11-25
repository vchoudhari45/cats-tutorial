package com.vc

/**
  * Reader Monads
  * Allows us to sequence the operations that depends on some input.
  *
  * If we have a number of opera􏰀ons that all depend on some external configuration,
  * we can chain them together using a Reader to produce one large opera􏰀on that accepts the configuration
  * as a parameter and runs our program in the order specified.
  **/
object ReaderMonad {
  def main(args: Array[String]): Unit = {

    //DbReader for a Reader that consumes a Db as input
    import cats.data.Reader
    case class Db(userNames: Map[Int, String])
    type DbReader[A] = Reader[Db, A]

    //Below method returns DbReader and assumes that Db class object will be available during execution
    def findUserName(userId: Int): DbReader[Option[String]] = Reader(db => db.userNames.get(userId))

    val db = Db(Map(1 -> "ABC", 2 -> "XYZ"))

    //Db object is passed using run method like below
    val res1 = findUserName(1).run(db)
    println(res1)
  }
}
