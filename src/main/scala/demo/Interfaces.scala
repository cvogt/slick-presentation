package demo 
import scala.slick.driver.JdbcProfile
trait Interfaces{
  val profile: JdbcProfile
  import profile.simple._
  trait HasSuppliers{
    def supId: Column[Int]
  }
}