import demo.Config._
import demo.CustomizedCodeGenerator
/* // ENABLE TO RUN CODE GENERATOR
object RunGenerator extends App{
  val db = slickProfile.simple.Database.forURL(url, driver=jdbcDriver)
  val metaModel = db.withTransaction{ implicit session =>
    slickProfile.metaModel
  }
  val codegen = new CustomizedCodeGenerator(metaModel)
  codegen.generate
}
*/