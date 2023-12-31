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


package org.hsqldb.persist;

import java.util.Enumeration;

import org.hsqldb.Database;
import org.hsqldb.DatabaseURL;
import org.hsqldb.HsqlException;
import org.hsqldb.Trace;
import org.hsqldb.lib.HashMap;
import org.hsqldb.lib.HashSet;
import org.hsqldb.lib.Iterator;
import org.hsqldb.lib.Set;
import org.hsqldb.lib.SimpleLog;
import org.hsqldb.lib.StringUtil;
import org.hsqldb.lib.java.JavaSystem;
import org.hsqldb.store.ValuePool;

/**
 * Manages a .properties file for a database.
 *
 * @author fredt@users
 * @version 1.8.0
 * @since 1.7.0
 */
public class HsqlDatabaseProperties extends HsqlProperties {

    private static String hsqldb_method_class_names =
        "hsqldb.method_class_names";
    private static HashSet accessibleJavaMethodNames;

    static {
        try {
            String prop = System.getProperty(hsqldb_method_class_names);

            if (prop != null) {
                accessibleJavaMethodNames = new HashSet();

                String[] names = StringUtil.split(prop, ";");

                for (int i = 0; i < names.length; i++) {
                    accessibleJavaMethodNames.add(names[i]);
                }
            }
        } catch (Exception e) {}
    }

    /**
     * If the system property "hsqldb.method_class_names" is not set, then
     * static methods of all available Java classes can be accessed as functions
     * in HSQLDB. If the property is set, then only the list of semicolon
     * seperated method names becomes accessible. An empty property value means
     * no class is accessible.<p>
     *
     * A property value that ends with .* is treated as a wild card and allows
     * access to all classe or method names formed by substitution of the
     * asterisk.<p>
     *
     * All methods of org.hsqldb.Library and java.lang.Math are always
     * accessible.
     *
     *
     */
    public static boolean supportsJavaMethod(String name) {

        if (accessibleJavaMethodNames == null) {
            return true;
        }

        if (name.startsWith("org.hsqldb.Library.")) {
            return true;
        }

        if (name.startsWith("java.lang.Math.")) {
            return true;
        }

        if (accessibleJavaMethodNames.contains(name)) {
            return true;
        }

        Iterator it = accessibleJavaMethodNames.iterator();

        while (it.hasNext()) {
            String className = (String) it.next();
            int    limit     = className.lastIndexOf(".*");

            if (limit < 1) {
                continue;
            }

            if (name.startsWith(className.substring(0, limit + 1))) {
                return true;
            }
        }

        return false;
    }

    // column number mappings
    public static final int indexName         = 0;
    public static final int indexAccess       = 1;
    public static final int indexClass        = 2;
    public static final int indexIsRange      = 3;
    public static final int indexDefaultValue = 4;
    public static final int indexRangeLow     = 5;
    public static final int indexRangeHigh    = 6;
    public static final int indexValues       = 7;
    public static final int indexLimit        = 8;

    // accessibility
    private static final int SET_PROPERTY  = 0;
    private static final int SQL_PROPERTY  = 1;
    private static final int FILE_PROPERTY = 2;

    // db files modified
    public static final int     FILES_NOT_MODIFIED = 0;
    public static final int     FILES_MODIFIED     = 1;
    public static final int     FILES_NEW          = 2;
    private static final String MODIFIED_NO        = "no";
    private static final String MODIFIED_YES       = "yes";
    private static final String MODIFIED_NEW       = "no-new-files";

    // allowed property metadata
    private static HashMap meta = new HashMap();

    // versions
    public static final String VERSION_STRING_1_7_0     = "1.7.0";
    public static final String VERSION_STRING_1_8_0     = "1.8.0";
    public static final String FIRST_COMPATIBLE_VERSION = "1.8.0";
    public static final String THIS_VERSION             = "1.8.0";
    public static final String THIS_FULL_VERSION        = "1.8.0.10";
    public static final String THIS_CACHE_VERSION       = "1.7.0";
    public static final String PRODUCT_NAME = "HSQL Database Engine";
    public static final int    MAJOR                    = 1,
                               MINOR                    = 8,
                               REVISION                 = 0;

    /**
     * system properties supported by HSQLDB
     */
    public static final String system_lockfile_poll_retries_property =
        "hsqldb.lockfile_poll_retries";
    public static final String system_max_char_or_varchar_display_size =
        "hsqldb.max_char_or_varchar_display_size";

    //
    public static final String  db_version  = "version";
    private static final String db_readonly = "readonly";
    private static final String db_modified = "modified";

    //
    private static final String runtime_gc_interval = "runtime.gc_interval";
    public static final String  hsqldb_applog       = "hsqldb.applog";
    public static final String hsqldb_cache_file_scale =
        "hsqldb.cache_file_scale";
    public static final String hsqldb_cache_free_count_scale =
        "hsqldb.cache_free_count_scale";
    public static final String hsqldb_cache_scale = "hsqldb.cache_scale";
    public static final String hsqldb_cache_size_scale =
        "hsqldb.cache_size_scale";
    public static final String  hsqldb_cache_version = "hsqldb.cache_version";
    private static final String hsqldb_catalogs      = "hsqldb.catalogs";
    public static final String hsqldb_compatible_version =
        "hsqldb.compatible_version";
    public static final String hsqldb_default_table_type =
        "hsqldb.default_table_type";
    public static final String hsqldb_defrag_limit = "hsqldb.defrag_limit";
    private static final String hsqldb_files_readonly =
        "hsqldb.files_readonly";
    public static final String hsqldb_lock_file     = "hsqldb.lock_file";
    public static final String hsqldb_log_size      = "hsqldb.log_size";
    public static final String hsqldb_nio_data_file = "hsqldb.nio_data_file";
    public static final String hsqldb_max_nio_scale = "hsqldb.max_nio_scale";
    public static final String hsqldb_raf_buffer_scale =
        "hsqldb.raf_buffer_scale";
    private static final String hsqldb_original_version =
        "hsqldb.original_version";
    public static final String hsqldb_script_format = "hsqldb.script_format";

    //
    private static final String sql_compare_in_locale =
        "sql.compare_in_locale";
    private static final String sql_enforce_strict_size =
        "sql.enforce_strict_size";
    public static final String sql_tx_no_multi_write =
        "sql.tx_no_multi_rewrite";

    //
    public static final String textdb_cache_scale = "textdb.cache_scale";
    public static final String textdb_cache_size_scale =
        "textdb.cache_size_scale";
    public static final String textdb_all_quoted = "textdb.all_quoted";
    public static final String textdb_allow_full_path =
        "textdb.allow_full_path";
    public static final String textdb_encoding     = "textdb.encoding";
    public static final String textdb_ignore_first = "textdb.ignore_first";
    public static final String textdb_quoted       = "textdb.quoted";
    public static final String textdb_fs           = "textdb.fs";
    public static final String textdb_vs           = "textdb.vs";
    public static final String textdb_lvs          = "textdb.lvs";

    static {

        // string defaults for protected props
        meta.put(db_version, getMeta(db_version, FILE_PROPERTY, null));
        meta.put(hsqldb_compatible_version,
                 getMeta(hsqldb_compatible_version, FILE_PROPERTY, null));
        meta.put(hsqldb_cache_version,
                 getMeta(hsqldb_cache_version, FILE_PROPERTY, null));
        meta.put(hsqldb_original_version,
                 getMeta(hsqldb_original_version, FILE_PROPERTY, null));
        meta.put(db_modified, getMeta(db_modified, FILE_PROPERTY, null));

        // string defaults for user defined props
        meta.put(hsqldb_default_table_type,
                 getMeta(hsqldb_default_table_type, SET_PROPERTY, "memory"));
        meta.put(textdb_fs, getMeta(textdb_fs, SET_PROPERTY, ","));
        meta.put(textdb_vs, getMeta(textdb_vs, SET_PROPERTY, null));
        meta.put(textdb_lvs, getMeta(textdb_lvs, SET_PROPERTY, null));
        meta.put(textdb_encoding,
                 getMeta(textdb_encoding, SET_PROPERTY, null));

        // boolean defaults for protected props
        meta.put(db_readonly, getMeta(db_readonly, FILE_PROPERTY, false));
        meta.put(hsqldb_files_readonly,
                 getMeta(hsqldb_files_readonly, FILE_PROPERTY, false));
        meta.put(textdb_allow_full_path,
                 getMeta(textdb_allow_full_path, FILE_PROPERTY, false));

        // boolean defaults for user defined props
        meta.put(hsqldb_lock_file,
                 getMeta(hsqldb_lock_file, SET_PROPERTY, true));
        meta.put(hsqldb_nio_data_file,
                 getMeta(hsqldb_nio_data_file, SET_PROPERTY, false));
        meta.put(hsqldb_catalogs,
                 getMeta(hsqldb_catalogs, SET_PROPERTY, false));
        meta.put(sql_enforce_strict_size,
                 getMeta(sql_enforce_strict_size, SET_PROPERTY, false));
        meta.put(sql_tx_no_multi_write,
                 getMeta(sql_tx_no_multi_write, SET_PROPERTY, false));
        meta.put(textdb_quoted, getMeta(textdb_quoted, SET_PROPERTY, false));
        meta.put(textdb_all_quoted,
                 getMeta(textdb_all_quoted, SET_PROPERTY, false));
        meta.put(textdb_ignore_first,
                 getMeta(textdb_ignore_first, SET_PROPERTY, false));

        // integral defaults for user-defined set props
        meta.put(hsqldb_applog,
                 getMeta(hsqldb_applog, SET_PROPERTY, 0, new byte[] {
            0, 1, 2
        }));
        meta.put(hsqldb_cache_file_scale,
                 getMeta(hsqldb_cache_file_scale, SET_PROPERTY, 1, new byte[] {
            1, 8
        }));
        meta.put(hsqldb_script_format,
                 getMeta(hsqldb_script_format, SET_PROPERTY, 0, new byte[] {
            0, 1, 3
        }));

        // integral defaults for proteced range props
        meta.put(hsqldb_log_size,
                 getMeta(hsqldb_log_size, SQL_PROPERTY, 0, 0, 16000));
        meta.put(hsqldb_defrag_limit,
                 getMeta(hsqldb_defrag_limit, SQL_PROPERTY, 200, 0, 16000));

        // integral defaults for user defined range props
        meta.put(runtime_gc_interval,
                 getMeta(runtime_gc_interval, SET_PROPERTY, 0, 0, 1000000));
        meta.put(hsqldb_cache_free_count_scale,
                 getMeta(hsqldb_cache_free_count_scale, SET_PROPERTY, 9, 6,
                         12));
        meta.put(hsqldb_cache_scale,
                 getMeta(hsqldb_cache_scale, SET_PROPERTY, 14, 8, 18));
        meta.put(hsqldb_cache_size_scale,
                 getMeta(hsqldb_cache_size_scale, SET_PROPERTY, 10, 6, 20));
        meta.put(hsqldb_max_nio_scale,
                 getMeta(hsqldb_max_nio_scale, SET_PROPERTY, 28, 24, 31));
        meta.put(hsqldb_raf_buffer_scale,
                 getMeta(hsqldb_raf_buffer_scale, SET_PROPERTY, 12, 8, 13));
        meta.put(textdb_cache_scale,
                 getMeta(textdb_cache_scale, SET_PROPERTY, 10, 8, 16));
        meta.put(textdb_cache_size_scale,
                 getMeta(textdb_cache_size_scale, SET_PROPERTY, 10, 6, 20));
    }

    private Database database;

    public HsqlDatabaseProperties(Database db) {

        super(db.getPath(), db.getFileAccess(), db.isFilesInJar());

        database = db;

        // char padding to size and exception if data is too long
        setProperty(sql_enforce_strict_size, false);

        // removed from 1.7.2 - sql.month is always true (1-12)
        // removed from 1.7.2 - sql.strict_fk is always enforced
        // if true, requires a pre-existing unique index for foreign key
        // referenced column and returns an error if index does not exist
        // 1.61 creates a non-unique index if no index exists
        // setProperty("sql.strict_fk", false);
        // removed from 1.7.2
        // has no effect if sql_strict_fk is true, otherwise if true,
        // creates a unique index for foreign keys instead of non-unique
        // setProperty("sql.strong_fk", true);
        // the two properties below are meant for attempting to open an
        // existing database with all its files *.properties, *script and
        // *.data.
        // version of a new database
        setProperty(db_version, THIS_VERSION);

        // the earliest version that can open this database
        // this is set to 1.7.2 when the db is written to
        setProperty(hsqldb_compatible_version, FIRST_COMPATIBLE_VERSION);

        // data format of the cache file
        // this is set to 1.7.0 when a new *.data file is created
        setProperty(hsqldb_cache_version, THIS_CACHE_VERSION);

        // the version that created this database
        // once created, this won't change if db is used with a future version
        setProperty(hsqldb_original_version, THIS_VERSION);
        /*
                garbage collection with gc_interval
                Setting this value can be useful when HSQLDB is used as an
                in-process part of an application. The minimum practical
                amount is probably "10000" and the maximum "1000000"

                In some versions of Java, such as 1.3.1_02 on windows,
                when the application runs out of memory it runs the gc AND
                requests more memory from the OS. Setting this property
                forces the DB to live inside its memory budget but the
                maximum amount of memory can still be set with the
                java -Xmx argument to provide the memory needed by other
                parts of the app to do graphics and networking.

                Of course there is a speed penalty for setting the value
                too low and doing garbage collection too often.

                This was introduced as a result of tests by Karl Meissner
                (meissnersd@users)
         */

        // garbage collect per Record or Cache Row objects created
        // the default, "0" means no garbage collection is forced by
        // hsqldb (the Java Runtime will do it's own garbage collection
        // in any case).
        setProperty(runtime_gc_interval, 0);

        // this property is either 1 or 8
        setProperty(hsqldb_cache_file_scale, 1);

        // this property is between 6 - 20, default 8
        setProperty(hsqldb_cache_size_scale, 8);

        // number of rows from CACHED tables kept constantly in memory
        // the number of rows in up to 3 * (2 to the power of
        // cache_scale value).
        // reduce the default 14 (3*16K rows) if memory is limited and rows
        // are large.
        // values between 8-16 are allowed
        setProperty(hsqldb_cache_scale, 14);

        // maximum size of .log file in megabytes
        setProperty(hsqldb_log_size, 200);

        // type of logging (0 : text , 1 : binary, 3 : compressed)
        setProperty(hsqldb_script_format, 0);
        setProperty(db_readonly, false);
        setProperty(db_modified, "no-new-files");

        // initial method of data file access
        setProperty(hsqldb_nio_data_file, true);

        // set default table type to MEMORY
        setProperty(hsqldb_default_table_type, "memory");

        // the property "version" is also set to the current version
        //
        // the following properties can be set by the user as defaults for
        // text tables. the default values are shown.
        // "textdb.fs", ","
        // "textdb.vs", ",";
        // "textdb.lvs", ","
        // "textdb.ignore_first", false
        // "textdb.quoted", true
        // "textdb.all_quoted", false
        // "textdb.encoding", "ASCII"
        // "textdb.cache_scale", 10  -- allowed range 8-16
        // "textdb.cache_size_scale", 10  -- allowed range 8-20
        //
        // OOo related code
        if (db.isStoredFileAccess()) {
            setProperty(hsqldb_default_table_type, "cached");
            setProperty(hsqldb_cache_scale, 13);
            setProperty(hsqldb_log_size, 10);
            setProperty(sql_enforce_strict_size, true);
            setProperty(hsqldb_nio_data_file, false);
        }

        // OOo end
    }

    /**
     * Creates file with defaults if it didn't exist.
     * Returns false if file already existed.
     */
    public boolean load() throws HsqlException {

        boolean exists;

        if (!DatabaseURL.isFileBasedDatabaseType(database.getType())) {
            return true;
        }

        try {
            exists = super.load();
        } catch (Exception e) {
            throw Trace.error(Trace.FILE_IO_ERROR, Trace.LOAD_SAVE_PROPERTIES,
                              new Object[] {
                fileName, e
            });
        }

        if (!exists) {
            return false;
        }

        filterLoadedProperties();

        String version = getProperty(hsqldb_compatible_version);

        // do not open if the database belongs to a later (future) version
        int check = version.substring(0, 5).compareTo(THIS_VERSION);

        if (check > 0) {
            throw Trace.error(Trace.WRONG_DATABASE_FILE_VERSION);
        }

        version = getProperty(db_version);

        if (version.charAt(2) == '6') {
            setProperty(hsqldb_cache_version, "1.6.0");
        }

        JavaSystem.gcFrequency = getIntegerProperty(runtime_gc_interval, 0);

        return true;
    }

    /**
     * Sets the database member variables after creating the properties object,
     * openning a properties file, or changing a property with a command
     */
    public void setDatabaseVariables() {

        if (isPropertyTrue(db_readonly)) {
            database.setReadOnly();
        }

        if (isPropertyTrue(hsqldb_files_readonly)) {
            database.setFilesReadOnly();
        }

        database.sqlEnforceStrictSize =
            isPropertyTrue(sql_enforce_strict_size);

        if (isPropertyTrue(sql_compare_in_locale)) {
            stringProps.remove(sql_compare_in_locale);
            database.collation.setCollationAsLocale();
        }

        database.txManager.setReWriteProtection(
            isPropertyTrue(sql_tx_no_multi_write));
        database.setMetaDirty(false);
    }

    public void save() throws HsqlException {

        if (!DatabaseURL.isFileBasedDatabaseType(database.getType())
                || database.isFilesReadOnly() || database.isFilesInJar()) {
            return;
        }

        try {
            super.save(fileName + ".properties" + ".new");
            fa.renameElement(fileName + ".properties" + ".new",
                             fileName + ".properties");
        } catch (Exception e) {
            database.logger.appLog.logContext(SimpleLog.LOG_ERROR, "failed");

            throw Trace.error(Trace.FILE_IO_ERROR, Trace.LOAD_SAVE_PROPERTIES,
                              new Object[] {
                fileName, e
            });
        }
    }

    void filterLoadedProperties() {

        Enumeration en = stringProps.propertyNames();

        while (en.hasMoreElements()) {
            String  key    = (String) en.nextElement();
            boolean accept = meta.containsKey(key);

            if (!accept) {
                stringProps.remove(key);
            }
        }
    }

    /**
     *  overload file database properties with any passed on URL line
     *  do not store password etc
     */
    public void setURLProperties(HsqlProperties p) {

        if (p != null) {
            for (Enumeration e = p.propertyNames(); e.hasMoreElements(); ) {
                String   propertyName = (String) e.nextElement();
                Object[] row          = (Object[]) meta.get(propertyName);

                if (row != null
                        && (db_readonly.equals(propertyName)
                            || ((Integer) row[indexAccess]).intValue()
                               == SET_PROPERTY)) {

                    // can add error checking with defaults
                    setProperty(propertyName, p.getProperty(propertyName));
                }
            }
        }
    }

    public Set getUserDefinedPropertyData() {

        Set      set = new HashSet();
        Iterator it  = meta.values().iterator();

        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();

            if (((Integer) row[indexAccess]).intValue() == SET_PROPERTY) {
                set.add(row);
            }
        }

        return set;
    }

    public boolean isUserDefinedProperty(String key) {

        Object[] row = (Object[]) meta.get(key);

        return row != null
               && ((Integer) row[indexAccess]).intValue() == SET_PROPERTY;
    }

    public boolean isBoolean(String key) {

        Object[] row = (Object[]) meta.get(key);

        return row != null && row[indexClass].equals("boolean")
               && ((Integer) row[indexAccess]).intValue() == SET_PROPERTY;
    }

    public boolean isIntegral(String key) {

        Object[] row = (Object[]) meta.get(key);

        return row != null && row[indexClass].equals("int")
               && ((Integer) row[indexAccess]).intValue() == SET_PROPERTY;
    }

    public boolean isString(String key) {

        Object[] row = (Object[]) meta.get(key);

        return row != null && row[indexClass].equals("java.lang.String")
               && ((Integer) row[indexAccess]).intValue() == SET_PROPERTY;
    }

    public String setDatabaseProperty(String key,
                                      String value) throws HsqlException {

        Object[] row = (Object[]) meta.get(key);

        // can check bounds here
        value = super.setProperty(key, value);

        return value;
    }

    public int getDefaultWriteDelay() {

        // OOo related code
        if (database.isStoredFileAccess()) {
            return 2000;
        }

        // OOo end
        return 10000;
    }

    public void setDBModified(int mode) throws HsqlException {

        String value = MODIFIED_NO;

        if (mode == FILES_MODIFIED) {
            value = MODIFIED_YES;
        } else if (mode == FILES_NEW) {
            value = MODIFIED_NEW;
        }

        setProperty(db_modified, value);
        save();
    }

    public int getDBModified() throws HsqlException {

        String value = getProperty("modified");

        if (MODIFIED_YES.equals(value)) {
            return FILES_MODIFIED;
        } else if (MODIFIED_NEW.equals(value)) {
            return FILES_NEW;
        }

        return FILES_NOT_MODIFIED;
    }

    private static Object[] getMeta(String name, int accessLevel,
                                    String defaultValue) {

        Object[] row = new Object[indexLimit];

        row[indexName]         = name;
        row[indexAccess]       = ValuePool.getInt(accessLevel);
        row[indexClass]        = "java.lang.String";
        row[indexDefaultValue] = defaultValue;

        return row;
    }

    private static Object[] getMeta(String name, int accessLevel,
                                    boolean defaultValue) {

        Object[] row = new Object[indexLimit];

        row[indexName]         = name;
        row[indexAccess]       = ValuePool.getInt(accessLevel);
        row[indexClass]        = "boolean";
        row[indexDefaultValue] = defaultValue ? Boolean.TRUE
                                              : Boolean.FALSE;

        return row;
    }

    private static Object[] getMeta(String name, int accessLevel,
                                    int defaultValue, byte[] values) {

        Object[] row = new Object[indexLimit];

        row[indexName]         = name;
        row[indexAccess]       = ValuePool.getInt(accessLevel);
        row[indexClass]        = "int";
        row[indexDefaultValue] = ValuePool.getInt(defaultValue);
        row[indexValues]       = values;

        return row;
    }

    private static Object[] getMeta(String name, int accessLevel,
                                    int defaultValue, int rangeLow,
                                    int rangeHigh) {

        Object[] row = new Object[indexLimit];

        row[indexName]         = name;
        row[indexAccess]       = ValuePool.getInt(accessLevel);
        row[indexClass]        = "int";
        row[indexDefaultValue] = ValuePool.getInt(defaultValue);
        row[indexIsRange]      = Boolean.TRUE;
        row[indexRangeLow]     = ValuePool.getInt(rangeLow);
        row[indexRangeHigh]    = ValuePool.getInt(rangeHigh);

        return row;
    }
}
