This is a code example of using the Slick database query library for Scala.

http://slick.typesafe.com

The example was used at

http://www.meetup.com/Bay-Area-Scala-Enthusiasts/events/80269142/
http://www.meetup.com/Bay-Area-Scala-Enthusiasts/events/80268902/

To get it running you need

SBT 0.12
http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html

Make sure sbt is in your PATH.

You then need to clone this project
git clone git://github.com/cvogt/slick-presentation

Run the code example from SBT
$ sbt run

Or, start sbt and run the code example from within sbt
$ sbt
> run

You can edit the example using your favorite editor or to get proper code completion you can use

Eclipse 3.7
http://www.eclipse.org/downloads/packages/eclipse-classic-372/indigosr2

The Scala-IDE plugin
http://download.scala-ide.org/releases-210/milestone/site

Create a new Eclipse project in the project folder and add the slick jar to the build path. You can find it in the ivy cache. Under Windows it is located in you user directory under %HOMEDRIVE%%HOMEPATH%\.ivy2\cache\com.typesafe\slick_2.10.0-M7\jars\slick_2.10.0-M7-0.11.1.jar
Under Mac and Linux it is located in you user directory under ~.ivy2/cache/com.typesafe/slick_2.10.0-M7/jars/slick_2.10.0-M7-0.11.1.jar
