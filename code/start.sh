#!/bin/sh

javac -cp "desmoj-2.5.1e-bin.jar" *.java

java -cp $CLASSPATH:desmoj-2.5.1e-bin.jar LaunchSimulation

firefox LaunchSimulation.html