ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

Compile / run / fork := true

ThisBuild / Compile / run / fork := true

val weaverVersion = "0.8.1"

lazy val root = (project in file("."))
  .settings(
    name := "deutsche-tl",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.5.4",
      "org.typelevel" %% "cats-effect" % "3.5.4",
      "com.disneystreaming" %% "weaver-scalacheck" % weaverVersion % Test,
      "com.disneystreaming" %% "weaver-cats" % weaverVersion % Test,
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions += "-Wnonunit-statement"
  )
