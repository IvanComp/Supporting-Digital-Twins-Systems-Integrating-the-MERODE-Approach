/* Copyright (c) 2001-2008, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR dao;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.hsqldb;

import java.util.Vector;

import org.hsqldb.lib.FileUtil;
import org.hsqldb.lib.HashMap;
import org.hsqldb.lib.HashSet;
import org.hsqldb.lib.HsqlTimer;
import org.hsqldb.lib.IntKeyHashMap;
import org.hsqldb.lib.Iterator;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.store.ValuePool;

/**
 * Handles initial attempts to connect to HSQLDB databases within the JVM
 * (or a classloader within the JVM). Opens the database if it is not open
 * or connects to it if it is already open. This allows the same database to
 * be used by different instances of Server and by direct connections.<p>
 *
 * Maintains a map of Server instances and notifies each server when its
 * database has shut down.<p>
 *
 * Maintains a reference to the timer used for file locks and logging.<p>
 *
 * @author fredt@users
 * @version 1.8.0
 * @since 1.7.2
 */
public class DatabaseManager {

    // Database and Server registry

    /** provides unique ID's for the Databases currently in registry */
    private static int dbIDCounter;

    /** name to Database mapping for mem: databases */
    static final HashMap memDatabaseMap = new HashMap();

    /** File to Database mapping for file: databases */
    static final HashMap fileDatabaseMap = new HashMap();

    /** File to Database mapping for res: databases */
    static final HashMap resDatabaseMap = new HashMap();

    /** id number to Database for Databases currently in registry */
    static final IntKeyHashMap databaseIDMap = new IntKeyHashMap();

    /**
     * Returns a vector containing the URI (type + path) for all the databases.
     */
    public static Vector getDatabaseURIs() {

        Vector   v  = new Vector();
        Iterator it = databaseIDMap.values().iterator();

        while (it.hasNext()) {
            Database db = (Database) it.next();

            v.addElement(db.getURI());
        }

        return v;
    }

    /**
     * Closes all the databases using the given mode.<p>
     *
     * CLOSEMODE_IMMEDIATELY = -1;
     * CLOSEMODE_NORMAL      = 0;
     * CLOSEMODE_COMPACT     = 1;
     * CLOSEMODE_SCRIPT      = 2;
     */
    public static void closeDatabases(int mode) {

        Iterator it = databaseIDMap.values().iterator();

        while (it.hasNext()) {
            Database db = (Database) it.next();

            try {
                db.close(mode);
            } catch (HsqlException e) {}
        }
    }

    /**
     * Used by server to open a new session
     */
    static Session newSession(int dbID, String user,
                              String password) throws HsqlException {

        Database db = (Database) databaseIDMap.get(dbID);

        return db == null ? null
                          : db.connect(user, password);
    }

    /**
     * Used by in-process connections and by Servlet
     */

// loosecannon1@users 1.7.2 patch properties on the JDBC URL
    public static Session newSession(String type, String path, String user,
                                     String password,
                                     HsqlProperties props)
                                     throws HsqlException {

        Database db = getDatabase(type, path, props);

        return db == null ? null
                          : db.connect(user, password);
    }

    /**
     * Returns an existing session. Used with repeat HTTP connections
     * belonging to the same JDBC Conenction / HSQL Session pair.
     */
    static Session getSession(int dbId, int sessionId) {

        Database db = (Database) databaseIDMap.get(dbId);

        return db == null ? null
                          : db.sessionManager.getSession(sessionId);
    }

    /**
     * Used by server to open or create a database
     */

// loosecannon1@users 1.7.2 patch properties on the JDBC URL
    static int getDatabase(String type, String path, Server server,
                           HsqlProperties props) throws HsqlException {

        Database db = getDatabase(type, path, props);

        registerServer(server, db);

        return db.databaseID;
    }

    /**
     * This has to be improved once a threading model is in place.
     * Current behaviour:
     *
     * Attempts to connect to different databases do not block. Two db's can
     * open simultaneously.
     *
     * Attempts to connect to a db while it is opening or closing will block
     * until the db is open or closed. At this point the db state is either
     * DATABASE_ONLINE (after db.open() has returned) which allows a new
     * connection to be made, or the state is DATABASE_SHUTDOWN which means
     * the db can be reopened for the new connection).
     *
     */

// loosecannon1@users 1.7.2 patch properties on the JDBC URL
    static Database getDatabase(String type, String path,
                                HsqlProperties props) throws HsqlException {

        // If the (type, path) pair does not correspond to a registered
        // instance, then getDatabaseObject() returns a newly constructed
        // and registered Database instance.
        // The database state will be DATABASE_SHUTDOWN,
        // which means that the switch below will attempt to
        // open the database instance.
        Database db = getDatabaseObject(type, path, props);

        synchronized (db) {
            switch (db.getState()) {

                case Database.DATABASE_ONLINE :
                    break;

                case Database.DATABASE_SHUTDOWN :

                    // if the database was shutdown while this attempt
                    // was waiting, add the database back to the registry
                    if (lookupDatabaseObject(type, path) == null) {
                        addDatabaseObject(type, path, db);
                    }

                    db.open();
                    break;

                // This state will currently not be reached as Database.Close() is
                // called while a lock is held on the database.
                // If we remove the lock from this method and a database is
                // being shutdown by a thread and in the meantime another thread
                // attempts to connect to the db. The threads could belong to
                // different server instances or be in-process.
                case Database.DATABASE_CLOSING :

                // this case will not be reached as the state is set and
                // cleared within the db.open() call above, which is called
                // from this synchronized block
                // it is here simply as a placeholder for future development
                case Database.DATABASE_OPENING :
                    throw Trace.error(Trace.LOCK_FILE_ACQUISITION_FAILURE,
                                      Trace.DatabaseManager_getDatabase);
            }
        }

        return db;
    }

// loosecannon1@users 1.7.2 patch properties on the JDBC URL
    private static synchronized Database getDatabaseObject(String type,
            String path, HsqlProperties props) throws HsqlException {

        Database db;
        String   key = path;
        HashMap  databaseMap;

        if (type == DatabaseURL.S_FILE) {
            databaseMap = fileDatabaseMap;
            key         = filePathToKey(path);
        } else if (type == DatabaseURL.S_RES) {
            databaseMap = resDatabaseMap;
        } else if (type == DatabaseURL.S_MEM) {
            databaseMap = memDatabaseMap;
        } else {
            throw Trace.runtimeError(Trace.UNSUPPORTED_INTERNAL_OPERATION,
                                     "DatabaseManager.getDatabaseObject");
        }

        db = (Database) databaseMap.get(key);

        if (db == null) {
            db            = new Database(type, path, type + key, props);
            db.databaseID = dbIDCounter;

            databaseIDMap.put(dbIDCounter, db);

            dbIDCounter++;

            databaseMap.put(key, db);
        }

        return db;
    }

    /**
     * Looks up database of a given type and path in the registry. Returns
     * null if there is none.
     */
    private static synchronized Database lookupDatabaseObject(String type,
            String path) throws HsqlException {

        Object  key = path;
        HashMap databaseMap;

        if (type == DatabaseURL.S_FILE) {
            databaseMap = fileDatabaseMap;
            key         = filePathToKey(path);
        } else if (type == DatabaseURL.S_RES) {
            databaseMap = resDatabaseMap;
        } else if (type == DatabaseURL.S_MEM) {
            databaseMap = memDatabaseMap;
        } else {
            throw (Trace.runtimeError(
                Trace.UNSUPPORTED_INTERNAL_OPERATION,
                "DatabaseManager.lookupDatabaseObject()"));
        }

        return (Database) databaseMap.get(key);
    }

    /**
     * Adds a database to the registry. Returns
     * null if there is none.
     */
    private static synchronized void addDatabaseObject(String type,
            String path, Database db) throws HsqlException {

        Object  key = path;
        HashMap databaseMap;

        if (type == DatabaseURL.S_FILE) {
            databaseMap = fileDatabaseMap;
            key         = filePathToKey(path);
        } else if (type == DatabaseURL.S_RES) {
            databaseMap = resDatabaseMap;
        } else if (type == DatabaseURL.S_MEM) {
            databaseMap = memDatabaseMap;
        } else {
            throw Trace.runtimeError(Trace.UNSUPPORTED_INTERNAL_OPERATION,
                                     "DatabaseManager.addDatabaseObject()");
        }

        databaseIDMap.put(db.databaseID, db);
        databaseMap.put(key, db);
    }

    /**
     * Removes the database from registry.
     */
    static void removeDatabase(Database database) {

        int     dbID = database.databaseID;
        String  type = database.getType();
        String  path = database.getPath();
        Object  key  = path;
        HashMap databaseMap;

        notifyServers(database);

        if (type == DatabaseURL.S_FILE) {
            databaseMap = fileDatabaseMap;

// boucherb@users 20040124 - patch 1.7.2
// Under the current contract, it's essentially impossible for an
// exception to get thrown here, because the database could not
// have been registered successfully before hand using the same
// path
//
// Eventually, we might think about storing the key with the
// database instance so as to avoid this unnecessary additional
// conversion and highly unlikely corner case handling.
            try {
                key = filePathToKey(path);
            } catch (HsqlException e) {
                Iterator it       = databaseMap.keySet().iterator();
                Object   foundKey = null;

                while (it.hasNext()) {
                    Object currentKey = it.next();

                    if (databaseMap.get(currentKey) == database) {
                        foundKey = currentKey;

                        break;
                    }
                }

                if (foundKey == null) {

                    // ??? return;
                } else {
                    key = foundKey;
                }
            }
        } else if (type == DatabaseURL.S_RES) {
            databaseMap = resDatabaseMap;
        } else if (type == DatabaseURL.S_MEM) {
            databaseMap = memDatabaseMap;
        } else {
            throw (Trace.runtimeError(
                Trace.UNSUPPORTED_INTERNAL_OPERATION,
                "DatabaseManager.lookupDatabaseObject()"));
        }

        databaseIDMap.remove(dbID);
        databaseMap.remove(key);

        if (databaseIDMap.isEmpty()) {
            ValuePool.resetPool();
        }
    }

    /**
     * Maintains a map of servers to sets of databases.
     * Servers register each of their databases.
     * When a database is shutdown, all the servers accessing it are notified.
     * The database is then removed form the sets for all servers and the
     * servers that have no other database are removed from the map.
     */
    static HashMap serverMap = new HashMap();

    /**
     * Deregisters a server completely.
     */
    static void deRegisterServer(Server server) {
        serverMap.remove(server);
    }

    /**
     * Deregisters a server as serving a given database. Not yet used.
     */
    private static void deRegisterServer(Server server, Database db) {

        Iterator it = serverMap.values().iterator();

        for (; it.hasNext(); ) {
            HashSet databases = (HashSet) it.next();

            databases.remove(db);

            if (databases.isEmpty()) {
                it.remove();
            }
        }
    }

    /**
     * Registers a server as serving a given database.
     */
    private static void registerServer(Server server, Database db) {

        if (!serverMap.containsKey(server)) {
            serverMap.put(server, new HashSet());
        }

        HashSet databases = (HashSet) serverMap.get(server);

        databases.add(db);
    }

    /**
     * Notifies all servers that serve the database that the database has been
     * shutdown.
     */
    private static void notifyServers(Database db) {

        Iterator it = serverMap.keySet().iterator();

        for (; it.hasNext(); ) {
            Server  server    = (Server) it.next();
            HashSet databases = (HashSet) serverMap.get(server);

            if (databases.contains(db)) {
                server.notify(ServerConstants.SC_DATABASE_SHUTDOWN,
                              db.databaseID);
            }
        }
    }

    static boolean isServerDB(Database db) {

        Iterator it = serverMap.keySet().iterator();

        for (; it.hasNext(); ) {
            Server  server    = (Server) it.next();
            HashSet databases = (HashSet) serverMap.get(server);

            if (databases.contains(db)) {
                return true;
            }
        }

        return false;
    }

    // Timer
    private static final HsqlTimer timer = new HsqlTimer();

    public static HsqlTimer getTimer() {
        return timer;
    }

    // converts file path to database lookup key, converting any
    // thrown exception to an HsqlException in the process
    private static String filePathToKey(String path) throws HsqlException {

        try {
            return FileUtil.getDefaultInstance().canonicalOrAbsolutePath(path);
        } catch (Exception e) {
            throw Trace.error(Trace.FILE_IO_ERROR, e.toString());
        }
    }
}
