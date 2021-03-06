# find-class-versions
**This utility will only run on Java7 or later.**

Simple java command line utility to report on the version of the .class files within .jar files beneath a given directory.

The utility takes 2 arguments.

The 1st argument is the name of the directory beneath which the utility will scan for .jar files.

The 2nd (optional) argument is the minimum java class version that should be reported. If this argument is missing, then all versions will 
be reported.

A typical command line might look like:

`java -jar find-class-versions-1.0.0.jar /home/username/dev 50`

The above will report the java class version of all jar files found beneath the /home/username/dev directory.

Note that a .jar file *may* have more than 1 version of .class files within it. This utility will report all versions found, one per line.

## Motivation

Suppose you're working on a legacy project that is required to target an older JVM. In your development environment, you use the latest
JVM, but for actual deployment, your target JVM is old. How do you easily identify the problem jar files that get bundled
into your project, where a problem jar file is any that includes .class files that target a newer JVM than the one to which you will deploy?

This tool makes it easy to identify those problem .jar files.
