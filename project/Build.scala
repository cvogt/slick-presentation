import sbt._
import Keys._
import Tests._

object myBuild extends Build {
  lazy val mainProject = Project(
    id="root",
    base=file("."),
    settings = Project.defaultSettings ++ Seq(
      scalaVersion := "2.10.3",
      libraryDependencies ++= List(
  //        "com.typesafe.slick" %% "slick" % "2.0.0-RC1",
        "org.slf4j" % "slf4j-nop" % "1.6.4",
        "com.h2database" % "h2" % "1.3.166"
      ),
      slick <<= slickCodeGenTask, // register manual sbt command
      sourceGenerators in Compile <+= slickCodeGenTask // register automatic source generator
    )
  ).dependsOn( slickProject )

  lazy val slickProject = ProjectRef( uri("git://github.com/slick/slick.git#d12055c08e70d22d0cf73e175f70f42c497a3ea1"), "slick")
                       // ProjectRef( file("../slick"), "slick")
  
  // code generation task
  lazy val slick = TaskKey[Seq[File]]("gen-tables")
  lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
    val outputDir = (dir / "slick").getPath// place generated files in sbt's managed sources folder
    val url = "jdbc:h2:mem:test;INIT=runscript from 'src/main/sql/create.sql'"
    val jdbcDriver = "org.h2.Driver"
    val slickDriver = "scala.slick.driver.H2Driver"
    val pkg = "demo"
    toError(r.run("scala.slick.model.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
    val fname = outputDir + "/demo/Tables.scala"
    Seq(file(fname))
  }
}