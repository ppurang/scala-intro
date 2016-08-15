package org.purang.intro

import org.scalacheck.Properties
import org.scalacheck.Prop.{forAll, BooleanOperators}
import org.scalacheck._

import scalaz._, Scalaz._

object InvListSpec extends Properties("InvList") {

  implicit val positiveInteger = Gen.choose(0,50)

  implicit def smallInvLists[A: Gen]: Gen[List[A]] = Gen.listOf(implicitly[Gen[A]])

  property("nth should be same as for a comparable list") = forAll(positiveInteger, smallInvLists) { (b:Int, a: List[Int]) => {
    val size = a.length
    println(a.length)
    (b >= 0 && b < size) ==> {

      Maybe.just(a(b)) === InvList.fromList(a).nth(b).fold(x => Maybe.empty, Maybe.just)
    }
  }
  }

}
