package demo 
import scala.slick.model.Model
import scala.slick.driver.H2Driver
import Config._

object CustomizedCodeGenerator{
  def main(args: Array[String]) = {
    val db = H2Driver.simple.Database.forURL(url,driver=jdbcDriver)
    val model = db.withSession(H2Driver.createModel(_))
    val codegen = new scala.slick.model.codegen.SourceCodeGenerator(model){
      def writeToFile = {
        // package up code with Interface
        val packageCode = s"""
package demo
// AUTO-GENERATED
import scala.slick.driver.JdbcProfile
trait Tables extends Interfaces{
  val profile: JdbcProfile
  import profile.simple._
  ${this.indent(this.code)}
}
        """.trim()

        this.writeStringToFile(
          packageCode,
          args(0),
          "demo",
          "Tables.scala"
        )
      }

      // customize scala entity name (case class, etc.)
      override def entityName = dbTableName => dbTableName match {
        case "COFFEES" => "Coffee"
        case "SUPPLIERS" => "Supplier"
        case _ => super.entityName(dbTableName)
      }
      
      // override generator responsible for tables
      override def Table = new Table(_){
        table =>
        // add some parents to selected Table classes
        override def TableClass = new TableClass{
          override def parents = name match {
            case "Coffees" | "CofInventory" => Seq("HasSuppliers")
            case _ => Seq()
          }
          override def body = super.body ++ ( name match{
            case "Coffees" => Seq(Seq("def revenue = price.asColumnOf[Double] * sales.asColumnOf[Double]"))
            case _ => Seq()
          })
        }
        // customize table value name
        override def TableValue = new TableValue{
          override def rawName = super.rawName.uncapitalize
        }
        // override generator responsible for columns
        override def Column = new Column(_){
          // customize column method names
          override def rawName = (table.model.name.table,this.model.name) match {
            case ("COFFEES","COF_NAME") => "name"
            case ("SUPPLIERS","SUP_ID") => "id"
            case ("SUPPLIERS","SUP_NAME") => "name"
            case ("COF_INVENTORY","QUAN") => "quantity"
            case _ => super.rawName
          }
        }
      }
      // Generate auto-join conditions 1
      // append autojoin conditions to generated code
      override def code = {
        super.code + "\n\n" + s"""
/** implicit join conditions for auto joins */
object AutoJoins{
  ${indent(joins.mkString("\n"))}
}
        """.trim()
      }
      // Generate auto-join conditions 2
      // assemble autojoin conditions
      val joins = tables.flatMap( _.foreignKeys.map{ foreignKey =>
        import foreignKey._
        val fkt = referencingTable.TableClass.name
        val pkt = referencedTable.TableClass.name
        val columns = referencingColumns.map(_.name) zip referencedColumns.map(_.name)
        s"""implicit def autojoin${fkt}${name.capitalize} = (left:${fkt},right:${pkt}) => """ +
        columns.map{
          case (lcol,rcol) => "left."+lcol + " === " + "right."+rcol
        }.mkString(" && ") + "\n" +
        s"""implicit def autojoin${fkt}${name.capitalize}Reverse = (left:${pkt},right:${fkt}) => """ +
        columns.map(_.swap).map{
          case (lcol,rcol) => "left."+lcol + " === " + "right."+rcol
        }.mkString(" && ")
      })
    }
    codegen.writeToFile
  }
}