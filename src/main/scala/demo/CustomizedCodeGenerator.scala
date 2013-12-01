package demo 
import scala.slick.meta.Model
import scala.slick.util.StringExtensions

class CustomizedCodeGenerator(metaModel: Model) extends scala.slick.meta.codegen.SourceCodeGenerator(metaModel){
  def generate = {
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
      "src/main/scala/",
      "demo",
      "Tables.scala"
    )
  }
  
  // override generator responsible for tables
  override def Table = new Table(_){
    // customize TableQuery val name
    override def tableValueName = super.tableValueName.uncapitalize
    // customize case class name
    override def entityClassName = (meta.name.table) match {
      case "COFFEES" => "Coffee"
      case "SUPPLIERS" => "Supplier"
      case _ => super.entityClassName
    }
    // add some parents to selected Table classes
    override def tableClassParents = tableClassName match {
      case "Coffees" | "CofInventory" => Seq("HasSuppliers")
      case _ => Seq()
    }
    override def tableClassBody = super.tableClassBody ++ ( tableClassName match{
      case "Coffees" => Seq(Seq("def revenue = price.asColumnOf[Double] * sales.asColumnOf[Double]"))
      case _ => Seq()
    })
    // override generator responsible for columns
    override def Column = new Column(_){
      // customize column method names
      override def name = (table.meta.name.table,this.meta.name) match {
        case ("COFFEES","COF_NAME") => "name"
        case ("SUPPLIERS","SUP_ID") => "id"
        case ("SUPPLIERS","SUP_NAME") => "name"
        case ("COF_INVENTORY","QUAN") => "quantity"
        case _ => super.name
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
    val fkt = referencingTable.tableClassName
    val pkt = referencedTable.tableClassName
    val columns = referencingColumns.map(_.name) zip referencedColumns.map(_.name)
    s"""implicit def autojoin${name.capitalize} = (left:${fkt},right:${pkt}) => """ +
    columns.map{
      case (lcol,rcol) => "left."+lcol + " === " + "right."+rcol
    }.mkString(" && ") + "\n" +
    s"""implicit def autojoin${name.capitalize}Reverse = (left:${pkt},right:${fkt}) => """ +
    columns.map(_.swap).map{
      case (lcol,rcol) => "left."+lcol + " === " + "right."+rcol
    }.mkString(" && ")
  })
}
