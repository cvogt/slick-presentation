/**
 * https://github.com/cvogt/slick-presentation/
 * branch: ny-scala-2013
 */
import scala.slick.driver.H2Driver.simple._
import setup._

object NyScala2013 extends App {
  // connection info for a throw-away, in-memory db
  val db = Database.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver")

  // join as a method extension
  implicit class CompaniesExtensions(val companies: Query[Companies, Company]) extends AnyVal{
    def withComputers(computers: Query[Computers, Computer] = Computers) = for (
      co <- companies;
      c <- computers;
      if co.id === c.manufacturerId
    ) yield (co, c)
  }

  // re-usable query component
  def groups(pattern: Column[String]) =
    (for (
      (co, cs) <- Companies.withComputers(Computers.filter(_.name like pattern)).groupBy(_._1)
    ) yield (co.name, cs.length))
      .sortBy(_._2)

  // precompiled query
  def groupsCompiled = Parameters[String].flatMap(groups)

  db.withTransaction { implicit session =>
    initDb
    groupsCompiled("%Mac%").foreach(println)
    groupsCompiled("%Mac%").foreach(println)
  }
}