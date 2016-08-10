package org.purang.intro

import org.scalacheck.Properties
import org.scalacheck.Prop.{forAll, BooleanOperators}
import org.scalacheck._

import scalaz._, Scalaz._

object SumSpecification extends Properties("Sum") {

  val smallInteger = Gen.choose(-10000,10000)

  property("sum") = forAll(smallInteger, smallInteger) { (a: Int, b: Int) =>
    a < b ==> {
      val sum = Sum.sum[Int](
        a,
        b,
        x => x,
        x => y => x + y,
        0,
        i => i+1,
        x => y => x <= y
      )
    sum === (a to b).map(x => x).sum}
  }

}
