name := "api-example"

version := "0.1"

scalaVersion := "2.12.6"

val tapirVersion = "0.14.3"
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % tapirVersion
libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-akka-http" % tapirVersion
libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc" % "3.4.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"



//Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.4" % Test
