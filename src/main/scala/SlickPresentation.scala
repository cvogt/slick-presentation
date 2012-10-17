import scala.slick.driver.H2Driver.simple._

object SlickPresentation extends App{  
  // define classes
  case class Person( id:Int, name:String, age:Int )
  case class Task( id:Int, title:String, personId:Int )

  // define tables
  object Persons extends Table[Person]("person"){
    def id = column[Int]("id", O.PrimaryKey)
    def name = column[String]("name")
    def age = column[Int]("age")
    def * = id ~ name ~ age <> (Person, Person.unapply _)
    def above(age:Int) = for( p <- Persons; if p.age > age ) yield p
  }

  object Tasks extends Table[Task]("task"){
    def id = column[Int]("id", O.PrimaryKey)
    def title = column[String]("title")
    def personId = column[Int]("person_id")
    def * = id ~ title ~ personId <> (Task.apply _, Task.unapply _) 
  }
 
  // create a session
  implicit val session = Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver").createSession()
  session.withTransaction{
    // generate ddl and create table in-memory
    Persons.ddl.create
    Tasks.ddl.create
   
    // insert data
    Persons.insertAll(
      Person(1, "Chris", 21 ),
      Person(2, "Stefan", 23 ),
      Person(3, "Martin", 25 ),
      Person(4, "Eugene", 21 )
    )
    Tasks.insertAll(
      Task(1,"Task 1",1),
      Task(2,"Task 2",2),
      Task(3,"Task 3",2),
      Task(4,"Task 4",3),
      Task(5,"Task 5",4),
      Task(6,"Task 6",3)      
    )
  }
    
  session.withTransaction{
    // query people above 21 with their tasks
    val somePeople = Persons.above(21).sortBy(_.name)
    val theirTasks = (for( t <- Tasks; if t.personId in somePeople.map(_.id) ) yield t)
    val theirTasksResults = theirTasks.list

    val results = somePeople.list.map( p =>
      p.name + ": " + theirTasksResults.filter(_.personId == p.id).map(_.title).mkString(", ")
    ).mkString("\n")
    
    // print results (using Scala 2.10 string interpolation)
    print(
s"""


=== result ===
${results}

=== generated sql queries ===
somePeople: ${somePeople.selectStatement}

theirTasks: ${theirTasks.selectStatement}



"""
    )
  }
  session.close()
}