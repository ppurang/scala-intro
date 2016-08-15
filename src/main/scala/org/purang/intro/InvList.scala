package org.purang.intro

import scala.annotation.tailrec
import scalaz.{IList, Maybe, \/}
import scalaz.syntax.either._

sealed trait InvListBoundsViolation

//perhaps showing size would be helpful
final case class IndexOutOfBounds(i: Int
                                  //, size: Int
                                 ) extends InvListBoundsViolation

final case class IllegalIndex(i: Int
                              //, size: Int
                             ) extends InvListBoundsViolation

sealed abstract class InvList[A] {
  def head: Maybe[A]
  def tail: InvList[A] //no need for maybe as Nil is always guaranteed
  def isEmpty : Boolean = head.isEmpty

  def nth(n: Int) : \/[InvListBoundsViolation, A] = {
    if(n < 0) {
      IllegalIndex(n).left
    } else {
      def loop(i: Int, xs: InvList[A]) : \/[InvListBoundsViolation, A]  = if(xs.isEmpty && i >= 0) {
        IndexOutOfBounds(n).left
      } else {
        xs match {
          case Cons(x, xss) => i match {
            case 0 => x.right
            case _ => loop(i-1, xss)
          }
          case Nil() => IndexOutOfBounds(n).left
        }
      }
      loop(n, this)
    }
  }


}

object InvList {
  private[this] val nil = Nil()

  //the following is unfortunately needed as invariant list means Nil[Nothing] can't be used where Nil[Int] is needed/expected
  //but we know what we are doing so it is ok
  def empty[A]: InvList[A] =
    nil.asInstanceOf[InvList[A]]

  def fromList[A]: List[A] => InvList[A] = _.foldRight[InvList[A]](Nil[A]())((x:A, y:InvList[A]) => Cons(x, y))

  def toList[A]: InvList[A] => List[A] = invList => {
    def loop(from: InvList[A], to: List[A]) : List[A] = from match {
      case Cons(x, xs) => loop(xs, to :+ x)
      case Nil() => to
    }
    loop(invList, List[A]())
  }
}

final case class Cons[A](hd: A, tail: InvList[A]) extends InvList[A] {
  override val head: Maybe[A] = Maybe.just(hd)
}

final case class Nil[A]() extends InvList[A] {
  override val head: Maybe[A] = Maybe.empty

  override val tail: InvList[A] = InvList.empty
}

object InvListTest {

  def main(args: Array[String]): Unit = {
    val l: InvList[Int] = Cons(1, InvList.empty)
  }
}
