import sbt._
import Keys._
import Tests._

object qqBuild extends Build {
  lazy val aProject = Project(
    id="root",
    base=file("."),
    settings = Project.defaultSettings ++ Seq(
      scalaVersion := "2.10.3",
      libraryDependencies ++= List(
//        "com.typesafe.slick" %% "slick" % "2.0.0-M2",
//        "org.slf4j" % "slf4j-nop" % "1.6.4",
        "com.h2database" % "h2" % "1.3.166"
      )
    )
  ).dependsOn(
    // depends on unreleased Slick commit, can replaced by RC1, once it's out
    ProjectRef( uri("git://github.com/slick/slick.git#2c8f3a7cf311c9779aa778503014e3bfcbf3c2a1"), "slick")
    //ProjectRef( file("../slick"), "slick")
  )
}