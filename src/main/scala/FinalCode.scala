/**
 * https://github.com/cvogt/slick-presentation/
 * branch: 2013/sug-berlin
 */
import demo.Tables._
import scala.slick.driver.H2Driver.simple._

object FinalCode {
  // connection info for a throw-away, in-memory db
  val url = "jdbc:h2:mem:test;INIT=runscript from 'src/main/sql/create.sql'"
  val db = Database.forURL(url, driver = "org.h2.Driver")

  // join as a method extension
  implicit class CompaniesExtensions(val companies: Query[Companies, CompaniesRow]) extends AnyVal{
    def withComputers(computers: Query[Computers, ComputersRow] = Computers) = for (
      co <- companies;
      c <- computers;
      if co.id === c.manufacturerId
    ) yield (co, c)
  }

  // re-usable query component
  def groups = (pattern: Column[String]) =>
    (for (
      (co, cs) <- Companies.withComputers(Computers.filter(_.name like pattern)).groupBy(_._1)
    ) yield (co.name, cs.length))
      .sortBy(_._2)

  // precompiled query
  def groupsCompiled = Compiled{ groups }

  db.withTransaction { implicit session =>
    groupsCompiled("%Mac%").foreach(println)
    groupsCompiled("%Mac%").foreach(println)
  }
}