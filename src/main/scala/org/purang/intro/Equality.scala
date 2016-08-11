package org.purang.intro

/**
  * Type classes 101
  *
  * https://en.wikipedia.org/wiki/Type_class
  *
  * http://danielwestheide.com/blog/2013/02/06/the-neophytes-guide-to-scala-part-12-type-classes.html
  * verbose: http://blog.jaceklaskowski.pl/2015/05/15/ad-hoc-polymorphism-in-scala-with-type-classes.html
  * advanced: https://twitter.github.io/scala_school/advanced-types.html#viewbounds
  *
  * haskell: http://learnyouahaskell.com/types-and-typeclasses#typeclasses-101
  *
  *
  */

/**
  * TypeClass capturing equality
  */
abstract class Equality[A] { // is slightly better than Trait for performance and we don't expect mixin
  def eq(a: A, b: A) : Boolean
}

object Equality {
  def apply[A: Equality] = implicitly[Equality[A]]

  def lift[A](f: (A, A) => Boolean) : Equality[A] = new Equality[A] { def eq(a: A, b: A) = f(a,b) }

  def equal[A] : Equality[A] = lift(_ == _)

  implicit val ints: Equality[Int] = equal[Int]
  implicit val strings: Equality[String] = equal[String]
  implicit val doubles: Equality[Double] = equal[Double]
  implicit def list[A]: Equality[List[A]] = equal[List[A]]
}

/**
  * These operation(s) could be lifted into already existing types to allow ease of use
  * implicitly[Equality].equalz(a,b) turns to a === b
  */
final class EqualityOps[A: Equality](val self: A) {
   def ===(other: => A): Boolean = Equality[A].eq(self, other)
}

object EqualityOps {
  implicit def toEqualOps[A: Equality](a: A) : EqualityOps[A] = new EqualityOps[A](a)
}

case class Vehicle(typ: String, value: Int)
object Vehicle {
  implicit val eq: Equality[Vehicle] = Equality.equal
}

object TestEquality {

  def main(args: Array[String]): Unit = {
    import Equality._
    import EqualityOps._

    println(1 === 1)
    //println("1" === 1)
    println(List() === List())
    println(List(1) === List(1))
    //println(List("1") === List(1))

    println("1" === "1")

    println(Vehicle("a", 1000) === Vehicle("a", 1000))
    println("this one -> " + (Vehicle("a", 1000) === Vehicle("a", 10001)))

    println(Vehicle("a", 1000) === Vehicle("b", 1000))

  }
}
