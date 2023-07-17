cd "`dirname "$0"`"
java -cp hsqldb.jar org.hsqldb.server.Server --database.0 file:mydb --dbname.0 xdb