/**
 * https://github.com/cvogt/slick-presentation/
 * branch: javaone-2013
 */
import scala.slick.driver.H2Driver.simple._
import setup._

object FinalCodeSample extends App {
  // connection info for a throw-away, in-memory db
  val db = Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver")

  val computersWithManufacturers =
    for (
      c <- Computers;
      co <- c.manufacturer
    ) yield (c, co)
	  
  def numComputers(pattern:Column[String]) =
    for (
      (co, group) <- computersWithManufacturers.filter(_._1.name like pattern).groupBy(_._2)
    ) yield (co.name, group.length)

  // precompiled query
  def groupsCompiled =
    for (
      p <- Parameters[String];
      g <- numComputers(p).sortBy(_._2)
    ) yield g

  db.withTransaction { implicit session =>
    initDb
    groupsCompiled("%Mac%").foreach(println)
    groupsCompiled("%Mac%").foreach(println)
  }
}