import scala.slick.driver.H2Driver.simple._

object SlickPresentation extends App{  
  // define classes
  
  
  // define tables
  
  
  // create a session
  implicit val session = Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver").createSession()
  session.withTransaction{
    // generate ddl and create table in-memory
    
   
    // insert data
    

  }
    
  session.withTransaction{
    // query people above 21 with their tasks
    

    // print results (using Scala 2.10 string interpolation)
    print(
s"""


=== result ===


=== generated sql queries ===




"""
    )
  }
  session.close()
}