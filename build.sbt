name := "trafficLightSimulator"

version := "1.0"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.4"
)

libraryDependencies += "org.specs2" %% "specs2-core" % "4.0.0" % "test"
libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.5.6" % "test"



resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions in Test ++= Seq("-Yrangepos")