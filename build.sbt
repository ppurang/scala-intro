scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.4",
  "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
)

fork in Scope.GlobalScope := true

cancelable in Scope.GlobalScope := true

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.8.0")
addCompilerPlugin("com.milessabin" % "si2712fix-plugin_2.11.8" % "1.2.0")
//addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("oncue", "releases"),
  Resolver.bintrayRepo("scalaz", "releases"),
  "twitter" at "http://maven.twttr.com",
  "gradle-libs" at "http://gradle.artifactoryonline.com/gradle/libs"
  //"scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",   //"-Yno-predef",
  "-Yno-adapted-args",
  "-Ywarn-value-discard",
  "-Ywarn-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-nullary-override",
  "-Ywarn-numeric-widen",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials"
)

scalacOptions in Test ++= Seq("-Yrangepos")


wartremoverErrors in (Compile, compile) ++= Seq(Wart.Any, Wart.Equals, Wart.Any2StringAdd, Wart.ExplicitImplicitTypes, Wart.Option2Iterable, Wart.OptionPartial, Wart.Return, Wart.TryPartial, Wart.EitherProjectionPartial, Wart.Enumeration, Wart.ImplicitConversion, Wart.JavaConversions, Wart.LeakingSealed) //Wart.FinalCaseClass


