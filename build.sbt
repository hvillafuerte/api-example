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