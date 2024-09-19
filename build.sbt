ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

Compile / run / fork := true

ThisBuild / Compile / run / fork := true


//val Http4sVersion = "0.23.14"
//val CirceVersion = "0.14.3"
//val CirisConfig = "3.1.0"
//val LogbackVersion = "1.4.6"
//val EnumeratumVersion = "1.7.2"
//val ScalatestVersion = "3.2.15"
//val BuckyVersion = "3.1.0"
//val scalaPactVersion = "4.4.0"
//val CEVersion = "3.4.10"
//val NatchezVersion = "0.3.1"
//val otelVersion = "1.34.1"

val weaverVersion = "0.8.1"

//object weaver {
//  private lazy val org     = "com.disneystreaming"
//  private lazy val version = "0.8.1"
//
//  lazy val cats       = org %% "weaver-cats"       % version
//  lazy val scalacheck = org %% "weaver-scalacheck" % version
//  lazy val discipline = org %% "weaver-discipline" % version
//
//  lazy val test = Seq(cats, scalacheck, discipline)
//}


lazy val root = (project in file("."))
  .settings(
    name := "deutsche-tl",
    //    idePackagePrefix := Some("org.thomlink.tictactoe"),
    //      libraryDependencies ++= Seq(
    ////      "io.opentelemetry" % "opentelemetry-exporter-otlp"    % otelVersion,
    ////      "io.opentelemetry" % "opentelemetry-exporter-logging" % otelVersion,
    ////      "io.opentelemetry" % "opentelemetry-sdk-extension-autoconfigure" % otelVersion,
    ////      "io.opentelemetry.semconv" % "opentelemetry-semconv"    % "1.23.1-alpha",
    ////      "org.tpolecat"            %% "natchez-opentelemetry"    % "0.3.5",
    ////      "org.tpolecat"            %% "natchez-noop"             % "0.3.5",
    ////      "ch.qos.logback"           % "logback-classic"          % "1.4.14",
    ////      "net.logstash.logback"     % "logstash-logback-encoder" % "7.4",
    ////      "org.http4s"              %% "http4s-blaze-server"      % "0.23.16",
    ////      "org.http4s"              %% "http4s-blaze-client"      % "0.23.16",
    ////      "is.cir"                  %% "ciris"                    % "3.1.0",
    ////      "io.laserdisc"            %% "fs2-aws-sqs"              % "6.1.3",
    ////      "io.laserdisc"            %% "pure-sqs-tagless"         % "6.1.3",
    ////      "software.amazon.awssdk"   % "sts"                      % "2.25.53",
    ////      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    ////      "org.scalatest" %% "scalatest"       % ScalatestVersion % "test,it",
    ////      "org.typelevel" %% "cats-effect-testing-scalatest" % "1.4.0" % "test,it",
    //    ),

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.5.4",
      "org.typelevel" %% "cats-effect" % "3.5.4",
//      "com.com.disneystreaming" %% "weaver-scalacheck" % weaverVersion
      "com.disneystreaming" %% "weaver-scalacheck" % weaverVersion % Test,
      "com.disneystreaming" %% "weaver-cats" % weaverVersion % Test,
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    scalacOptions += "-Wnonunit-statement"
  )
