This are code examples for the [Oct 15](http://www.meetup.com/Bay-Area-Scala-Enthusiasts/events/80269142/)
and [Oct 17](http://www.meetup.com/Bay-Area-Scala-Enthusiasts/events/80268902/)
presentations to Bay Area Scala Enthusiasts accompanying the
[Slick](http://slick.typesafe.com) presentation query library for Scala.


## To Run ##

Install SBT 0.12, available from
http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html

Make sure `sbt` is in your PATH.

You then need to clone this project

    git clone git://github.com/cvogt/slick-presentation

Change into the slick-presentation folder.

Run the code example using SBT

    $ sbt run

Or, start sbt and run the code example from within sbt

    $ sbt
    > run

## Requirements ##
You can edit the example using your favorite editor or to get proper code completion you can use:

* Eclipse 3.7 (Juno), available from
http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/junosr1

* The Scala-IDE plugin, available from this Eclipse update site:
http://download.scala-ide.org/releases-210/milestone/site

## Eclipse ##
To edit the project in eclipse you can generate the required eclipse project files like this:

    $ sbt eclipse

After that you can import the project into eclipse.
