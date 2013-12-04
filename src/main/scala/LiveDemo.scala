/**
 * https://github.com/cvogt/slick-presentation/
 * branch: 2013/sug-berlin
 */
import demo.Tables._
import scala.slick.driver.H2Driver.simple._

object LiveDemo extends App {
  // connection info for a throw-away, in-memory db
  val url = "jdbc:h2:mem:test;INIT=runscript from 'src/main/sql/create.sql'"
  val db = Database.forURL(url, driver = "org.h2.Driver")

  db.withTransaction { implicit session =>
    
  }
}