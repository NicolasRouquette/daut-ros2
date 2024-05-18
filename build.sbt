
import scala.sys.process._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.2"

lazy val pythonLdFlags = {
  try {
    val withoutEmbed = "python3-config --ldflags".!!
    if (withoutEmbed.contains("-lpython")) {
      withoutEmbed.split(' ').map(_.trim).filter(_.nonEmpty).toSeq
    } else {
      val withEmbed = "python3-config --ldflags --embed".!!
      withEmbed.split(' ').map(_.trim).filter(_.nonEmpty).toSeq
    }
  } catch {
    case _: Exception => Seq.empty[String]
  }
}

lazy val pythonLibsDir = {
  pythonLdFlags.find(_.startsWith("-L")).map(_.drop(2)).getOrElse("")
}

val daut = "org.havelund" %% "daut" % "0.2"
val scalapy = "dev.scalapy" %% "scalapy-core" % "0.5.3"

lazy val root = (project in file("."))
  .settings( organization := "org.havelund"
    , name := "daut-ros2"
    , libraryDependencies ++= Seq(daut, scalapy)
    , Compile / fork := true
    , Compile / javaOptions += s"-Djna.library.path=$pythonLibsDir"
  )
