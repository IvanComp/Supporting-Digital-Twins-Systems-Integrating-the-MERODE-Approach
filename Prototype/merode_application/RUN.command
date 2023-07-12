#!/bin/sh

cd "$(dirname "$0")"
chmod u+rwx RUN.command

THE_CLASSPATH=

THE_CLASSPATH=${THE_CLASSPATH}:.
THE_CLASSPATH=${THE_CLASSPATH}:./bin
for i in `ls ./lib/*.jar`
  do
  THE_CLASSPATH=${THE_CLASSPATH}:${i}
done
for i in `ls ./lib/hibernate3-jars/*.jar`
  do
  THE_CLASSPATH=${THE_CLASSPATH}:${i}
done
THE_CLASSPATH=${THE_CLASSPATH}:./database/hsqldb/lib/hsqldb.jar


java -jar ./lib/ecj-4.2.jar -classpath ".:${THE_CLASSPATH}" -1.6 -nowarn -noExit -d ./bin ./src

java -classpath ".:${THE_CLASSPATH}" driver.MerodeMainEventHandlerGui

chmod u+rwx RUN.command