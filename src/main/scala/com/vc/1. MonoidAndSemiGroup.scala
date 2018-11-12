package com.vc

import cats.Monoid

/**
  * Monoid: Used for combining types
  * SemiGroup: Used for combining types but when we can't define empty value for a type
  * Holds Associative Law & Identity Law
  *
  * trait Monoid[A] {
  *   def combine(x: A, y: A): A
  *   def empty: A
  * }
  **/
object MonoidAndSemiGroup extends App {

  import cats.instances.string._
  val stringUsingMonoidInstances = Monoid[String].combine("Hello ", "Normal ")
  println(stringUsingMonoidInstances)

  import cats.instances.option._
  import cats.instances.string._
  val optionUsingMonoidInstances = Monoid[Option[String]].combineAll(List(Some("Hello "), None, Some("Option "), Some("Monoid")))
  println(optionUsingMonoidInstances)

  import cats.syntax.semigroup._
  import cats.instances.string._
  val stringUsingSemiGroupSyntax = "Hello " |+| "Normal " |+| "SemiGroupSyntax"
  println(stringUsingSemiGroupSyntax)

  import cats.syntax.semigroup._
  import cats.instances.string._
  import cats.instances.option._
  val optionUsingSemiGroupSyntax = Option("Hello ") |+| Option("Option ") |+| Option("SemiGroupSyntax") |+| None
  println(optionUsingSemiGroupSyntax)

}
