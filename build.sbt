name := """activator-akka-http-elasticsearch"""

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.4",
  "org.json4s" %% "json4s-native" % "3.5.0",
  "org.elasticsearch" % "elasticsearch" % "2.4.4",
  "ch.qos.logback" % "logback-classic" % "1.2.1"
)