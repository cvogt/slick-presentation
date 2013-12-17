import sbt._
import Keys._
import Tests._

/** This implements a staged sbt build, where codegenProject compiles the
 *  customized code generator and mainProject runs it to generate sources,
 *  which it then compiles together with its own sources.
 */
object stagedBuild extends Build {
  /** main project containing main source code depending on slick and codegen project */
  lazy val mainProject = Project(
    id="root",
    base=file("."),
    settings = sharedSettings ++ Seq(
      slick <<= slickCodeGenTask, // register manual sbt command
      sourceGenerators in Compile <+= slickCodeGenTask // register automatic source generator
    )
  ).dependsOn( slickProject, codegenProject )
  /** codegen project containing the customized code generator */
  lazy val codegenProject = Project(
    id="codegen",
    base=file("codegen"),
    settings = sharedSettings
  ).dependsOn( slickProject )
                                      // unreleased Slick version, which can replaced by RC1, once it's out
  lazy val slickProject = ProjectRef( uri("git://github.com/slick/slick.git#d12055c08e70d22d0cf73e175f70f42c497a3ea1"), "slick")
                       // ProjectRef( file("../slick"), "slick")
  
  // shared sbt config between main project and codegen project
  val sharedSettings = Project.defaultSettings ++ Seq(
    scalaVersion := "2.10.3",
    libraryDependencies ++= List(
//        "com.typesafe.slick" %% "slick" % "2.0.0-RC1",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.h2database" % "h2" % "1.3.166"
    )
  )

  // code generation task that calls the customized code generator
  lazy val slick = TaskKey[Seq[File]]("gen-tables")
  lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
    val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
    toError(r.run("demo.CustomizedCodeGenerator", cp.files, Array(outputDir), s.log))
    val fname = outputDir + "/demo/Tables.scala"
    println(fname)
    Seq(file(fname))
  }
}