#!/bin/zsh

VERSION=0.0.1-SNAPSHOT
JAR_PATH=./build/libs/sudoku-${VERSION}.jar

if [ ! -f $JAR_PATH ]
then
  echo "Project is not built; building..."
  ./gradlew clean build
fi

if [ -f $1 ]
then
  java -jar $JAR_PATH $1 --validate
else
  java -jar $JAR_PATH ./src/test/resources/$1 --validate
fi
