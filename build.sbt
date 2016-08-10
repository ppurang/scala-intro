scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.4",
  "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
)

fork in (Test,run) := true

cancelable in GlobalScope := true