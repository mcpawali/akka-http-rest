import sbt.Keys.scalaVersion

name := "akka-http-rest"

version := "1.0"

scalaVersion := "2.12.8"

val akka              = "2.3.12"
val akkaStreamV       = "2.4.4"
val scalaTestVer      = "2.2.2"
val mockito           = "1.10.8"
val postgresqlVersion = "9.4-1201-jdbc41"
val slickVersion      = "3.1.1"
val json4sVersion     = "3.4.0"

val common_settings = Defaults.coreDefaultSettings ++
  Seq(
    organization := "com.cpawali",
    scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.5.24",
      "com.typesafe.akka" %% "akka-http" % "10.1.12",
      "com.typesafe.akka" %% "akka-stream" % "2.6.1",
      "com.typesafe.slick" %% "slick" % "3.3.2",
      "org.postgresql" % "postgresql" % "9.4.1211",
      "com.zaxxer" % "HikariCP" % "2.4.3",
      "org.apache.commons" % "commons-email" % "1.3.3",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
      "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
      "org.json4s" %% "json4s-jackson" % "3.6.9"
    )
  )
lazy val `core`        = project.in(file("core")).withId("core").settings(common_settings)
lazy val `domain`           = project.in(file("domain")).withId("domain").settings(common_settings).dependsOn(`core`)
lazy val `akka-http-rest` = project.in(file(".")).withId("akka-http-rest").settings(common_settings).aggregate(`core`, `domain`)
