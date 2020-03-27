#!/bin/sh
cd game-engine || exit
rm -rf target/
rm -f game-engine.jar
./build.sh
cd ../game-runner || exit
mvn clean package
cd ../reference-bot/java || exit
mvn clean package
cd ../../starter-bots/java || exit
mvn clean package
cd ../../ || exit