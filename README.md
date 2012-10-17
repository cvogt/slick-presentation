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

Run the code example from SBT

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

## New Projects ##
Create a new Eclipse project and add the Slick jar to the build path. You can find it in the ivy cache.
Under Windows it is located at `%HOMEDRIVE%%HOMEPATH%\.ivy2\cache\com.typesafe\slick_2.10.0-M7\jars\slick_2.10.0-M7-0.11.1.jar`
Under Mac and Linux it is located at `~.ivy2/cache/com.typesafe/slick_2.10.0-M7/jars/slick_2.10.0-M7-0.11.1.jar`
