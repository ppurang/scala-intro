package org.purang.intro

import org.purang.intro.deeper.{Organization, Person, PersonException}

import scalaz._
import Scalaz._

object Main {

  def main(args: Array[String]): Unit = {
    try {
      val laura = Person("Laura", 18, Maybe.just("Ferari"))
      val lauraG = Person("Laura", 18, Maybe.just("Scooter"))
      val anil = Person("Anil", 17, Maybe.just("McLaren"))

      println(s"laura =/= anil ${laura =/= anil}")
      println(s"laura === anil ${laura === anil}")
      println(s"laura === lauraG ${laura === lauraG}")

      val pm = Organization(List(laura))
      val eng = Organization(List(anil))

      pm === eng
      throw PersonException("woha!")

    } catch {
      case PersonException(m)  =>
        println(m)

      case e: Throwable =>
        println("oops")

    }
  }

}


