import sbt._
import Keys._
import Tests._

object build extends Build {
  lazy val mainProject = Project(
    id="root",
    base=file("."),
    settings = sharedSettings ++ Seq(
    )
  ).dependsOn(ProjectRef(uri("git://github.com/slick/slick#tmp/hlistCompileSpeedup"),"slick"))
  val sharedSettings = Project.defaultSettings ++ Seq(
    scalaVersion := "2.10.4"
  )
}
