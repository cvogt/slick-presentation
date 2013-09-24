/**
 * https://github.com/cvogt/slick-presentation/
 * branch: javaone-2013
 */
import scala.slick.driver.H2Driver.simple._
import setup._

object LiveDemo extends App {
  // connection info for a throw-away, in-memory db
  val db = Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver")

  db.withTransaction { implicit session =>
    initDb

  }
}