/**
 * https://github.com/cvogt/slick-presentation/
 * branch: javaone-2013
 */

import demo.Config

object Tables extends demo.Tables{
  val profile = Config.slickProfile 
}
import Tables._
import profile.simple._

object CodeExamples extends App {
  val db = Database.forURL(Config.url, driver=Config.jdbcDriver)
  db.withTransaction { implicit session =>
    // acronyms for entity types
    type C = Coffee
    type S = Supplier
    
    
    // SLIDES

    // Recap
    (for ( c <- coffees;
          if c.sales > 999
    ) yield c.name).run

    // For-expression desugaring in Scala
    coffees
      .withFilter(_.sales > 999)
      .map(_.name)

    // Types in Slick
    (coffees:TableQuery[Coffees]).map( (c:Coffees) =>
      (c.name: Column[String])
    ): Query[Column[String],String]

    // Table extensions
    coffees.map(_.sales).update(2)
    println( coffees.map(c => c.revenue).run )

    // Query extensions
    implicit class QueryExtensions[T,E]
                    ( val q: Query[T,E] ){
      def page(no: Int, pageSize: Int = 10)
          : Query[T,E]
          = q.drop( (no-1)*pageSize ).take(pageSize)
    }

    val q5 = coffees.sortBy(_.name).page(5)
    q5.run

    val q6 = suppliers.page(5)
    q6.run

    // Query extensions by Table
    implicit class CoffeesExtensions
                      ( val q: Query[Coffees,C] ){
      def byName(name:Column[String])
          : Query[Coffees,C]
          = q.filter(_.name === name).sortBy(_.name)
    }

    val q7 = coffees.byName("ColumbianDecaf").page(5)
    q7.run
    
    // Query extensions for joins
    implicit class CoffeesExtensions2
                      ( val q: Query[Coffees,C] ){
      def withSuppliers
          (s: Query[Suppliers,S] = Tables.suppliers)
          : Query[(Coffees,Suppliers),(C,S)]
          = q.join(s).on(_.supId===_.id)
      def suppliers
          (s: Query[Suppliers, S] = Tables.suppliers)
          : Query[Suppliers, S]
          = q.withSuppliers(s).map(_._2)
    }

    val q8 = coffees.withSuppliers()
    val q9 = coffees.withSuppliers( suppliers.filter(_.city === "Henderson") )
    val q10 = coffees.withSuppliers()
    q8.run
    q9.run
    q10.run
    
    // possible but not implemented here: chaining
    // coffeeShops.coffees.suppliers.withCoffees
    
    // Query extensions by Interface

    implicit class HasSuppliersExtensions[T <: HasSuppliers,E]
                          ( val q: Query[T,E] ){
      def bySupId(id: Column[Int]): Query[T,E] = q.filter( _.supId === id )
      def withSuppliers
          (s: Query[Suppliers,S] = Tables.suppliers)
          : Query[(T,Suppliers),(E,S)]
          = q.join(s).on(_.supId===_.id)
      def suppliers
          (s: Query[Suppliers, S] = Tables.suppliers)
          : Query[Suppliers, S]
          = q.withSuppliers(s).map(_._2)
    }
    
    // available quantities of coffees
    val q11 = cofInventory.withSuppliers().map{
      case (i,s) => i.quantity.asColumnOf[String] ++ " of " ++ i.cofName ++ " at " ++ s.name
    }
    println( "Q11:" + q11.run )

    // Auto joins
    implicit class QueryExtensions2[T,E]
                    ( val q: Query[T,E] ){
      def autoJoin[T2,E2]
            ( q2:Query[T2,E2] )
            ( implicit condition: (T,T2) => Column[Boolean] )
            : Query[(T,T2),(E,E2)]
            = q.join(q2).on(condition)
    }

    import Tables.AutoJoins._
    coffees.autoJoin( suppliers ) : Query[(Coffees,Suppliers),_]
    coffees.autoJoin( suppliers ).map(_._2).autoJoin(cofInventory)

    // Auto incrementing inserts
    val supplier = new Supplier( 0, "Arabian Coffees Inc.", "", "", "", Some("") )

    // now ignores auto-increment column
    suppliers.insert( supplier )
    // includes auto-increment column
    suppliers.forceInsert( supplier )

    // Code generator for Slick code
    def dont_run_1{
      import scala.slick.model.codegen.SourceCodeGenerator
      SourceCodeGenerator.main(
        Array(
            "scala.slick.driver.H2Driver",
            "org.h2.Driver",
            "jdbc:h2:mem:test",
            "src/main/scala/", // base src folder
            "demo" // package
          )
      )
    }
    
    // Outer join limitation in Slick
    try{
        suppliers.leftJoin(coffees)
          .on(_.id === _.supId)
          .run // scala.slick.SlickException: Read NULL value ...
    } catch {
      case e:scala.slick.SlickException =>
    }

    // Outer join pattern 
    suppliers.leftJoin(coffees)
      .on(_.id === _.supId)
      .map{ case(s,c) => (s,(c.name.?,c.supId.?)) }
      .run
      .map{ case (s,c) =>
            (s,c._1.map(_ => (c._1.get,c._2.get))) }
    
    // Generated outer join helper
    suppliers.leftJoin(coffees)
      .on(_.id === _.supId)
      .map{ case(s,c) => (s,c.?) }
      .run

    def dont_run_2{
      {
        // Use generator as a library
        val model = db.withSession{ implicit session =>
          profile.model // e.g. H2Driver.model
        }
        import  scala.slick.model.codegen.SourceCodeGenerator
        val codegen = new SourceCodeGenerator(model){
          // <- customize here
        }
        codegen.writeToFile(
          profile = "scala.slick.driver.H2Driver", // default driver (not mandatory)
          folder = "src/main/scala/",
          pkg = "demo",
          container = "Tables", // default value: Tables
          fileName="Tables.scala" // default value: Tables.scala
        )
      }
      ;{
        // Adjust name mapping
        import  scala.slick.model.codegen.SourceCodeGenerator
        val codegen = new SourceCodeGenerator(profile.model){
          override def tableName = dbName => dbName.toLowerCase.toCamelCase
          override def entityName = tableName(_).dropRight(1) // Supplier, defaults to SuppliersRow
        }
      }
    }

    // Dynamic column (careful about security)
    coffees.map(c => c.name)
    coffees.map(c => c.column[String]("COF_NAME"))

    // Dynamic Queries
    implicit class QueryExtensions3[E,T<: Table[E]]
                   ( val query: Query[T,E] ){
      // apply sort keys as sortBy calls in reverse order
      def filterDynamic(filterString: String) : Query[T,E] = {
        val ExtractCondition = "([a-zA-Z0-9_]*)\\s*(=|like)\\s*([\\.a-zA-Z0-9]*)".r
        // split string into useful pieces
        val conditions = filterString.split(',').toList.map{ case ExtractCondition(key,op,value) => (key,op,value)}
        query.filter(
          table =>
          conditions.foldLeft(
            LiteralColumn(true):Column[Boolean]
          ){
            case ( conditionSoFar, (key,op,value) ) =>
              val column = table.column[String](key)
              conditionSoFar && ((op match {
                case "="    => column ===  LiteralColumn(value)
                case "like" => column like LiteralColumn("%" + value + "%")
              }):Column[Boolean])
          }:Column[Boolean]
        )
      }
        
      // apply sort keys as sortBy calls in reverse order
      def sortDynamic(sortString: String) : Query[T,E] = {
        // split string into useful pieces
        val sortKeys = sortString.split(',').toList.map( _.split('.').map(_.toUpperCase).toList )
        sortDynamicImpl(sortKeys)
      }
      private def sortDynamicImpl(sortKeys: List[Seq[String]]) : Query[T,E] = {
        sortKeys match {
          case key :: tail => 
            sortDynamicImpl( tail ).sortBy( table =>
              key match {
                case name :: "ASC" :: Nil =>  table.column[String](name).asc
                case name :: Nil =>           table.column[String](name).asc
                case name :: "DESC" :: Nil => table.column[String](name).desc
                case o => throw new Exception("invalid sorting key: "+o)
                }
              )
            case Nil => query
          }
        }
      }

    println(
      coffees.filterDynamic("COF_NAME like Decaf").list
    )

    println(
      suppliers.sortDynamic("street.desc,city.desc").list
    )
  }
}
