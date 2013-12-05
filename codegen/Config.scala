package demo
object Config{
  // connection info for a throw-away, in-memory db, which is freshly initialized on every run
  val initScripts = Seq("drop-tables.sql","create-tables.sql","populate-tables.sql")
  val url = "jdbc:h2:mem:test;INIT="+initScripts.map("runscript from 'src/sql/"+_+"'").mkString("\\;")
  val jdbcDriver =  "org.h2.Driver"
  val slickProfile = scala.slick.driver.H2Driver
}
