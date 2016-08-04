package org.purang.intro.deeper

import scalaz.{Equal, Maybe}


case class PersonException(msg: String) extends Exception

case class Person(name: String,
                  age: Int,
                  car: Maybe[String] = Maybe.empty
                 )
object Person {
  implicit val eq = Equal.equalA[Person]
}

case class Organization(people: List[Person])
object Organization {
  implicit val eq = Equal.equalA[Organization]
}