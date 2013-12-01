package demo
// AUTO-GENERATED
import scala.slick.driver.JdbcProfile
trait Tables extends Interfaces{
  val profile: JdbcProfile
  import profile.simple._
  import scala.slick.meta.ForeignKeyAction
  import scala.slick.jdbc.{GetResult => GR}
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  
  /** Entity class storing rows of table coffees */
  case class Coffee(name: String, supId: Int, price: BigDecimal, sales: Int, total: Int)
  /** GetResult implicit for fetching Coffee objects using plain SQL queries */
  implicit def GetCoffees(implicit e0: GR[String], e1: GR[Int], e2: GR[BigDecimal]) = GR{r => Coffee.tupled((r.<<[String], r.<<[Int], r.<<[BigDecimal], r.<<[Int], r.<<[Int])) }
  /** Table description of table COFFEES. Objects of this class serves as prototypes for rows in queries. */
  class Coffees(tag: Tag) extends Table[Coffee](tag,"COFFEES") with HasSuppliers {
    def * = (name, supId, price, sales, total) <> (Coffee.tupled, Coffee.unapply)
    def ? = (name.?, supId.?, price.?, sales.?, total.?).shaped.<>({case (name, supId, price, sales, total) => name.map(_ =>Coffee.tupled((name.get, supId.get, price.get, sales.get, total.get)))}, (_:Any) => ???)
    
    /** Database column COF_NAME PrimaryKey */
    val name = column[String]("COF_NAME", O.PrimaryKey)
    /** Database column SUP_ID  */
    val supId = column[Int]("SUP_ID")
    /** Database column PRICE  */
    val price = column[BigDecimal]("PRICE")
    /** Database column SALES  */
    val sales = column[Int]("SALES")
    /** Database column TOTAL  */
    val total = column[Int]("TOTAL")
    
    /** Foreign key Some(CONSTRAINT_63) referencing suppliers */
    val constraint63 = foreignKey("CONSTRAINT_63", supId, suppliers)(t => t.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
    
    def revenue = price.asColumnOf[Double] * sales.asColumnOf[Double]
  }
  /** Collection-like TableQuery object for table coffees */
  lazy val coffees = TableQuery[Coffees]
  
  /** Entity class storing rows of table coffeeDescriptions */
  case class CoffeeDescriptionsRow(cofName: String, cofDesc: java.sql.Blob)
  /** GetResult implicit for fetching CoffeeDescriptionsRow objects using plain SQL queries */
  implicit def GetCoffeeDescriptions(implicit e0: GR[String], e1: GR[java.sql.Blob]) = GR{r => CoffeeDescriptionsRow.tupled((r.<<[String], r.<<[java.sql.Blob])) }
  /** Table description of table COFFEE_DESCRIPTIONS. Objects of this class serves as prototypes for rows in queries. */
  class CoffeeDescriptions(tag: Tag) extends Table[CoffeeDescriptionsRow](tag,"COFFEE_DESCRIPTIONS") {
    def * = (cofName, cofDesc) <> (CoffeeDescriptionsRow.tupled, CoffeeDescriptionsRow.unapply)
    def ? = (cofName.?, cofDesc.?).shaped.<>({case (cofName, cofDesc) => cofName.map(_ =>CoffeeDescriptionsRow.tupled((cofName.get, cofDesc.get)))}, (_:Any) => ???)
    
    /** Database column COF_NAME PrimaryKey */
    val cofName = column[String]("COF_NAME", O.PrimaryKey)
    /** Database column COF_DESC  */
    val cofDesc = column[java.sql.Blob]("COF_DESC")
    
    /** Foreign key Some(CONSTRAINT_EC) referencing coffees */
    val constraintEc = foreignKey("CONSTRAINT_EC", cofName, coffees)(t => t.name, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table coffeeDescriptions */
  lazy val coffeeDescriptions = TableQuery[CoffeeDescriptions]
  
  /** Entity class storing rows of table coffeeHouses */
  case class CoffeeHousesRow(storeId: Int, city: Option[String], coffee: Int, merch: Int, total: Int)
  /** GetResult implicit for fetching CoffeeHousesRow objects using plain SQL queries */
  implicit def GetCoffeeHouses(implicit e0: GR[Int], e1: GR[String]) = GR{r => CoffeeHousesRow.tupled((r.<<[Int], r.<<?[String], r.<<[Int], r.<<[Int], r.<<[Int])) }
  /** Table description of table COFFEE_HOUSES. Objects of this class serves as prototypes for rows in queries. */
  class CoffeeHouses(tag: Tag) extends Table[CoffeeHousesRow](tag,"COFFEE_HOUSES") {
    def * = (storeId, city, coffee, merch, total) <> (CoffeeHousesRow.tupled, CoffeeHousesRow.unapply)
    def ? = (storeId.?, city, coffee.?, merch.?, total.?).shaped.<>({case (storeId, city, coffee, merch, total) => storeId.map(_ =>CoffeeHousesRow.tupled((storeId.get, city, coffee.get, merch.get, total.get)))}, (_:Any) => ???)
    
    /** Database column STORE_ID PrimaryKey */
    val storeId = column[Int]("STORE_ID", O.PrimaryKey)
    /** Database column CITY  */
    val city = column[Option[String]]("CITY")
    /** Database column COFFEE  */
    val coffee = column[Int]("COFFEE")
    /** Database column MERCH  */
    val merch = column[Int]("MERCH")
    /** Database column TOTAL  */
    val total = column[Int]("TOTAL")
  }
  /** Collection-like TableQuery object for table coffeeHouses */
  lazy val coffeeHouses = TableQuery[CoffeeHouses]
  
  /** Entity class storing rows of table cofInventory */
  case class CofInventoryRow(warehouseId: Int, cofName: String, supId: Int, quantity: Int, dateVal: Option[java.sql.Timestamp])
  /** GetResult implicit for fetching CofInventoryRow objects using plain SQL queries */
  implicit def GetCofInventory(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]) = GR{r => CofInventoryRow.tupled((r.<<[Int], r.<<[String], r.<<[Int], r.<<[Int], r.<<?[java.sql.Timestamp])) }
  /** Table description of table COF_INVENTORY. Objects of this class serves as prototypes for rows in queries. */
  class CofInventory(tag: Tag) extends Table[CofInventoryRow](tag,"COF_INVENTORY") with HasSuppliers {
    def * = (warehouseId, cofName, supId, quantity, dateVal) <> (CofInventoryRow.tupled, CofInventoryRow.unapply)
    def ? = (warehouseId.?, cofName.?, supId.?, quantity.?, dateVal).shaped.<>({case (warehouseId, cofName, supId, quantity, dateVal) => warehouseId.map(_ =>CofInventoryRow.tupled((warehouseId.get, cofName.get, supId.get, quantity.get, dateVal)))}, (_:Any) => ???)
    
    /** Database column WAREHOUSE_ID  */
    val warehouseId = column[Int]("WAREHOUSE_ID")
    /** Database column COF_NAME  */
    val cofName = column[String]("COF_NAME")
    /** Database column SUP_ID  */
    val supId = column[Int]("SUP_ID")
    /** Database column QUAN  */
    val quantity = column[Int]("QUAN")
    /** Database column DATE_VAL  */
    val dateVal = column[Option[java.sql.Timestamp]]("DATE_VAL")
    
    /** Foreign key Some(CONSTRAINT_5) referencing coffees */
    val constraint5 = foreignKey("CONSTRAINT_5", cofName, coffees)(t => t.name, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
    /** Foreign key Some(CONSTRAINT_57) referencing suppliers */
    val constraint57 = foreignKey("CONSTRAINT_57", supId, suppliers)(t => t.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table cofInventory */
  lazy val cofInventory = TableQuery[CofInventory]
  
  /** Entity class storing rows of table dataRepository */
  case class DataRepositoryRow(documentName: Option[String], url: Option[String])
  /** GetResult implicit for fetching DataRepositoryRow objects using plain SQL queries */
  implicit def GetDataRepository(implicit e0: GR[String]) = GR{r => DataRepositoryRow.tupled((r.<<?[String], r.<<?[String])) }
  /** Table description of table DATA_REPOSITORY. Objects of this class serves as prototypes for rows in queries. */
  class DataRepository(tag: Tag) extends Table[DataRepositoryRow](tag,"DATA_REPOSITORY") {
    def * = (documentName, url) <> (DataRepositoryRow.tupled, DataRepositoryRow.unapply)
    
    /** Database column DOCUMENT_NAME  */
    val documentName = column[Option[String]]("DOCUMENT_NAME")
    /** Database column URL  */
    val url = column[Option[String]]("URL")
  }
  /** Collection-like TableQuery object for table dataRepository */
  lazy val dataRepository = TableQuery[DataRepository]
  
  /** Entity class storing rows of table merchInventory */
  case class MerchInventoryRow(itemId: Int, itemName: Option[String], supId: Option[Int], quan: Option[Int], dateVal: Option[java.sql.Timestamp])
  /** GetResult implicit for fetching MerchInventoryRow objects using plain SQL queries */
  implicit def GetMerchInventory(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]) = GR{r => MerchInventoryRow.tupled((r.<<[Int], r.<<?[String], r.<<?[Int], r.<<?[Int], r.<<?[java.sql.Timestamp])) }
  /** Table description of table MERCH_INVENTORY. Objects of this class serves as prototypes for rows in queries. */
  class MerchInventory(tag: Tag) extends Table[MerchInventoryRow](tag,"MERCH_INVENTORY") {
    def * = (itemId, itemName, supId, quan, dateVal) <> (MerchInventoryRow.tupled, MerchInventoryRow.unapply)
    def ? = (itemId.?, itemName, supId, quan, dateVal).shaped.<>({case (itemId, itemName, supId, quan, dateVal) => itemId.map(_ =>MerchInventoryRow.tupled((itemId.get, itemName, supId, quan, dateVal)))}, (_:Any) => ???)
    
    /** Database column ITEM_ID PrimaryKey */
    val itemId = column[Int]("ITEM_ID", O.PrimaryKey)
    /** Database column ITEM_NAME  */
    val itemName = column[Option[String]]("ITEM_NAME")
    /** Database column SUP_ID  */
    val supId = column[Option[Int]]("SUP_ID")
    /** Database column QUAN  */
    val quan = column[Option[Int]]("QUAN")
    /** Database column DATE_VAL  */
    val dateVal = column[Option[java.sql.Timestamp]]("DATE_VAL")
    
    /** Foreign key Some(CONSTRAINT_678) referencing suppliers */
    val constraint678 = foreignKey("CONSTRAINT_678", supId, suppliers)(t => t.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table merchInventory */
  lazy val merchInventory = TableQuery[MerchInventory]
  
  /** Entity class storing rows of table rssFeeds */
  case class RssFeedsRow(rssName: String, rssFeedXml: java.sql.Clob)
  /** GetResult implicit for fetching RssFeedsRow objects using plain SQL queries */
  implicit def GetRssFeeds(implicit e0: GR[String], e1: GR[java.sql.Clob]) = GR{r => RssFeedsRow.tupled((r.<<[String], r.<<[java.sql.Clob])) }
  /** Table description of table RSS_FEEDS. Objects of this class serves as prototypes for rows in queries. */
  class RssFeeds(tag: Tag) extends Table[RssFeedsRow](tag,"RSS_FEEDS") {
    def * = (rssName, rssFeedXml) <> (RssFeedsRow.tupled, RssFeedsRow.unapply)
    def ? = (rssName.?, rssFeedXml.?).shaped.<>({case (rssName, rssFeedXml) => rssName.map(_ =>RssFeedsRow.tupled((rssName.get, rssFeedXml.get)))}, (_:Any) => ???)
    
    /** Database column RSS_NAME PrimaryKey */
    val rssName = column[String]("RSS_NAME", O.PrimaryKey)
    /** Database column RSS_FEED_XML  */
    val rssFeedXml = column[java.sql.Clob]("RSS_FEED_XML")
  }
  /** Collection-like TableQuery object for table rssFeeds */
  lazy val rssFeeds = TableQuery[RssFeeds]
  
  /** Entity class storing rows of table suppliers */
  case class Supplier(id: Int, name: String, street: String, city: String, state: String, zip: Option[String])
  /** GetResult implicit for fetching Supplier objects using plain SQL queries */
  implicit def GetSuppliers(implicit e0: GR[Int], e1: GR[String]) = GR{r => Supplier.tupled((r.<<[Int], r.<<[String], r.<<[String], r.<<[String], r.<<[String], r.<<?[String])) }
  /** Table description of table SUPPLIERS. Objects of this class serves as prototypes for rows in queries. */
  class Suppliers(tag: Tag) extends Table[Supplier](tag,"SUPPLIERS") {
    def * = (id, name, street, city, state, zip) <> (Supplier.tupled, Supplier.unapply)
    def ? = (id.?, name.?, street.?, city.?, state.?, zip).shaped.<>({case (id, name, street, city, state, zip) => id.map(_ =>Supplier.tupled((id.get, name.get, street.get, city.get, state.get, zip)))}, (_:Any) => ???)
    
    /** Database column SUP_ID AutoInc, PrimaryKey */
    val id = column[Int]("SUP_ID", O.AutoInc, O.PrimaryKey)
    /** Database column SUP_NAME  */
    val name = column[String]("SUP_NAME")
    /** Database column STREET  */
    val street = column[String]("STREET")
    /** Database column CITY  */
    val city = column[String]("CITY")
    /** Database column STATE  */
    val state = column[String]("STATE")
    /** Database column ZIP  */
    val zip = column[Option[String]]("ZIP")
  }
  /** Collection-like TableQuery object for table suppliers */
  lazy val suppliers = TableQuery[Suppliers]
  
  /** implicit join conditions for auto joins */
  object AutoJoins{
    implicit def autojoinConstraint63 = (left:Coffees,right:Suppliers) => left.supId === right.id
    implicit def autojoinConstraint63Reverse = (left:Suppliers,right:Coffees) => left.id === right.supId
    implicit def autojoinConstraintEc = (left:CoffeeDescriptions,right:Coffees) => left.cofName === right.name
    implicit def autojoinConstraintEcReverse = (left:Coffees,right:CoffeeDescriptions) => left.name === right.cofName
    implicit def autojoinConstraint5 = (left:CofInventory,right:Coffees) => left.cofName === right.name
    implicit def autojoinConstraint5Reverse = (left:Coffees,right:CofInventory) => left.name === right.cofName
    implicit def autojoinConstraint57 = (left:CofInventory,right:Suppliers) => left.supId === right.id
    implicit def autojoinConstraint57Reverse = (left:Suppliers,right:CofInventory) => left.id === right.supId
    implicit def autojoinConstraint678 = (left:MerchInventory,right:Suppliers) => left.supId === right.id
    implicit def autojoinConstraint678Reverse = (left:Suppliers,right:MerchInventory) => left.id === right.supId
  }
}