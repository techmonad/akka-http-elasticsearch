name := """activator-akka-http-elasticsearch"""

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.4",
  "org.json4s" %% "json4s-native" % "3.5.0",
  "org.elasticsearch" % "elasticsearch" % "2.4.4",
  "ch.qos.logback" % "logback-classic" % "1.2.1",
  "org.scalatest" % "scalatest_2.11" % "3.0.1" % "test",
  "net.java.dev.jna" % "jna" % "4.2.1" % "test",
  "org.apache.commons" % "commons-io" % "1.3.2" % "test"
)