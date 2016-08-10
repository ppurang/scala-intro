package org.purang.intro

import scala.annotation.tailrec
import scalaz._, Scalaz._


object Sum {

  def sum[A](lower: A, upper: A,
             f: A => A,
             //the following support notions of additivity/combining two values, next value, comparison etc.
             op: A => A => A, //associative binary operation
             zero: A, //unit for the op defined above
             next: A => A, //successor  .. needed for range
             continue: A => A => Boolean //continuation
            ): A = {

    //tailcalls, trampolines, continuations etc.
    // easy: http://blog.richdougherty.com/2009/04/tail-calls-tailrec-and-trampolines.html
    // http://www.scala-lang.org/api/current/index.html#scala.util.control.TailCalls$
    // hard: http://blog.higher-order.com/assets/trampolines.pdf
    //http://stackoverflow.com/questions/3114142/what-is-the-scala-annotation-to-ensure-a-tail-recursive-function-is-optimized
    @tailrec def loop(lower: A, acc: A): A = {
      if (continue(lower)(upper)) {
        val acc0: A = op(acc)(f(lower))
        loop(next(lower), acc0)
      } else {
        acc
      }
    }

    loop(lower, zero)
  }

  //this is strictly more powerful than sum above i.e.
  // it subsumes all that is possible with the sum above
  // and more
  def sumII[A, B](lower: A, upper: A,
                  f: A => B,
                  //the following support notions of additivity/combining two values, next value, comparison etc.
                  op: B => B => B, //associative binary operation
                  zero: B, //unit for the op defined above
                  next: A => A, //successor  .. needed for range
                  continue: A => A => Boolean //continuation
                 ): B = {

    //tailcalls, trampolines, continuations etc.
    // easy: http://blog.richdougherty.com/2009/04/tail-calls-tailrec-and-trampolines.html
    // http://www.scala-lang.org/api/current/index.html#scala.util.control.TailCalls$
    // hard: http://blog.higher-order.com/assets/trampolines.pdf
    //http://stackoverflow.com/questions/3114142/what-is-the-scala-annotation-to-ensure-a-tail-recursive-function-is-optimized
    @tailrec def loop(lower: A, acc: B): B = {
      if (continue(lower)(upper)) {
        loop(next(lower), op(acc)(f(lower)))
      } else {
        acc
      }
    }

    loop(lower, zero)
  }

  //the following shows you the named parts of this abstraction of folding over a list like container and combining the values
  //  Monoid captures zero and op
  //  Foldable captures folding over a recursive/iterative structure
  //  foldMap captures mapping and folding at the same time
  def sumAbstraction[A, B: Monoid, L[_] : Foldable](l: L[A], f: A => B): B = {
    Foldable[L].foldMap(l)(f)
  }

}


object TestSum {

  def main(args: Array[String]): Unit = {
    val negCase: Int = Sum.sum[Int](
      -10,
      10,
      x => x,
      x => y => x + y,
      0,
      i => i + 1,
      x => y => x <= y
    )

    val negCaseII: Int = Sum.sumII[Int, Int](
      -10,
      10,
      x => x,
      x => y => x + y,
      0,
      i => i + 1,
      x => y => x <= y
    )
    val negCaseControl: Int = (-10 to 10).map(x => x).sum

    val posCase: Int = Sum.sum[Int](
      0,
      10,
      x => x,
      x => y => x + y,
      0,
      i => i + 1,
      x => y => x <= y
    )

    val posCaseII: Int = Sum.sum[Int](
      0,
      10,
      x => x,
      x => y => x + y,
      0,
      i => i + 1,
      x => y => x <= y
    )
    val posCaseControl: Int = (0 to 10).map(x => x).sum

    assert(negCase === negCaseControl, s"negative case - $negCase === $negCaseControl")
    assert(negCaseII === negCaseControl, s"negative case II - $negCase === $negCaseControl")
    assert(posCase === posCaseControl, s"positive case - $posCase === $posCaseControl")
    assert(posCaseII === posCaseControl, s"positive case II - $posCase === $posCaseControl")





    assert(Sum.sumAbstraction((-10 to 10).toList, (x: Int) => x) === negCaseControl, s"negative case - $negCase === $negCaseControl")
    assert(Sum.sumAbstraction((0 to 10).toList, (x: Int) => x) === posCaseControl, s"positive case - $posCase === $posCaseControl")


  }
}

