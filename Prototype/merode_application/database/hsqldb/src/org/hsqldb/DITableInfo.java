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

import java.util.Locale;

import org.hsqldb.resources.BundleHandler;
import org.hsqldb.store.ValuePool;

/**
 * Provides extended information about HSQLDB tables and their
 * columns/indices. <p>
 *
 * @author boucherb@users
 * @version 1.8.0
 * @since 1.7.2
 */
final class DITableInfo {

    // related to DatabaseMetaData
    int                bestRowTemporary   = 0;
    int                bestRowTransaction = 1;
    int                bestRowSession     = 2;
    int                bestRowUnknown     = 0;
    int                bestRowNotPseudo   = 1;
    static final short tableIndexOther    = 3;

    /** Used in buffer size and character octet length determinations. */
    private static final int HALF_MAX_INT = Integer.MAX_VALUE >>> 1;

    /** BundleHandler id for column remarks resource bundle. */
    private int hnd_column_remarks = -1;

    /** BundleHandler id for table remarks resource bundle. */
    private int hnd_table_remarks = -1;

    /** The Table object upon which this object is reporting. */
    private Table table;

    /** Provides intrinsic type infformation support. */
    private static final DITypeInfo ti = new DITypeInfo();

    /**
     * Creates a new DITableInfo object with the default Locale and reporting
     * on no table.  It is absolutely essential the a valid Table object is
     * assigned to this object, using the setTable method, before any Table,
     * Column or Index oriented value retrieval methods are called; this class
     * contains no assertions or exception handling related to a null or
     * invalid table member attribute.
     */
    DITableInfo() {

/** @todo fredt - remove from here: should be set in a database-wide context */
        setLocale(Locale.getDefault());
    }

    /**
     * Sets the Locale for table and column remarks. <p>
     *
     * @param l The Locale object
     */
    void setLocale(Locale l) {

        Locale oldLocale;

        synchronized (BundleHandler.class) {
            oldLocale = BundleHandler.getLocale();

            BundleHandler.setLocale(l);

            hnd_column_remarks =
                BundleHandler.getBundleHandle("column-remarks", null);
            hnd_table_remarks = BundleHandler.getBundleHandle("table-remarks",
                    null);

            BundleHandler.setLocale(oldLocale);
        }
    }

    /**
     * Retrieves whether the best row identifier column is
     * a pseudo column, like an Oracle ROWID. <p>
     *
     * Currently, this always returns an Integer whose value is
     * DatabaseMetaData.bestRowNotPseudo, as HSQLDB does not support
     * pseudo columns such as ROWID. <p>
     *
     * @return whether the best row identifier column is
     * a pseudo column
     */
    Integer getBRIPseudo() {
        return ValuePool.getInt(bestRowNotPseudo);
    }

    /**
     * Retrieves the scope of the best row identifier. <p>
     *
     * This implements the rules described in
     * DatabaseInformationMain.SYSTEM_BESTROWIDENTIFIER. <p>
     *
     * @return the scope of the best row identifier
     */
    Integer getBRIScope() {
        return (table.isWritable()) ? ValuePool.getInt(bestRowTemporary)
                                    : ValuePool.getInt(bestRowSession);
    }

    /**
     * Retrieves, if definitely known, the transfer size for values of the
     * specified column, in bytes. <p>
     *
     * @param i zero-based column index
     * @return the transfer size for values of the
     * specified column, in bytes
     */
    Integer getColBufLen(int i) {

        int    size;
        int    type;
        Column column;

        column = table.getColumn(i);
        type   = column.getDIType();

        switch (type) {

            case Types.CHAR :
            case Types.CLOB :
            case Types.LONGVARCHAR :
            case Types.VARCHAR : {
                size = column.getSize();

                if (size == 0) {}
                else if (size > HALF_MAX_INT) {
                    size = 0;
                } else {
                    size = 2 * size;
                }

                break;
            }
            case Types.BINARY :
            case Types.BLOB :
            case Types.LONGVARBINARY :
            case Types.VARBINARY : {
                size = column.getSize();

                break;
            }
            case Types.BIGINT :
            case Types.DOUBLE :
            case Types.FLOAT :
            case Types.DATE :
            case Types.REAL :
            case Types.TIME : {
                size = 8;

                break;
            }
            case Types.TIMESTAMP : {
                size = 12;

                break;
            }
            case Types.INTEGER :
            case Types.SMALLINT :
            case Types.TINYINT : {
                size = 4;

                break;
            }
            case Types.BOOLEAN : {
                size = 1;

                break;
            }
            default : {
                size = 0;

                break;
            }
        }

        return (size > 0) ? ValuePool.getInt(size)
                          : null;
    }

    /**
     * Retrieves the declared size, in bytes, for character-valued
     * columns. <p>
     *
     * If the size cannot be represented in the range [0,Integer.MAX_VALUE],
     * this returns null. <p>
     *
     * @param i zero-based column index
     * @return the size, in bytes, for character-valued columns
     */
    Integer getColCharOctLen(int i) {

        int    size;
        int    type;
        Column column;

        column = table.getColumn(i);
        type   = column.getDIType();

        switch (type) {

            case Types.CHAR :
            case Types.CLOB :
            case Types.LONGVARCHAR :
            case Types.VARCHAR : {
                size = column.getSize();

                if (size == 0) {}
                else if (size > HALF_MAX_INT) {
                    size = 0;
                } else {
                    size = 2 * size;
                }

                break;
            }
            default : {
                size = 0;

                break;
            }
        }

        return (size == 0) ? null
                           : ValuePool.getInt(size);
    }

    /**
     * Retrieves the SQL data type code for the specified column. <p>
     *
     * @param i zero-based column index
     * @return the SQL data type code for the specified column
     */
    Integer getColDataType(int i) {
        return ValuePool.getInt(table.getColumn(i).getDIType());
    }

    /**
     * Retrieves the SQL data type name for the specified column. <p>
     *
     * @param i zero-based column index
     * @return the SQL data type name for the specified column
     */
    String getColDataTypeName(int i) {

        Column column = table.getColumn(i);

        ti.setTypeCode(column.getDIType());
        ti.setTypeSub(column.getDITypeSub());

        return ti.getTypeName();
    }

    /**
     * Retrieves the HSQLDB data subtype code for the specified column. <p>
     *
     * @param i zero-based column index
     * @return the HSQLDB data subtype code for the specified column
     */
    Integer getColDataTypeSub(int i) {
        return ValuePool.getInt(table.getColumn(i).getDITypeSub());
    }

    /**
     * Retrieves the declared default value expression for the column. <p>
     *
     * @param i zero-based column index
     * @return the declared default value expression for the column
     */
    String getColDefault(int i) {
        return table.getColumn(i).getDefaultDDL();
    }

    /**
     * Retrieves whether the specified column is the identity column for
     * the table. <p>
     *
     * @param i zero-based column index
     * @return whether the specified column is the identity column for
     * the table.
     */
    Boolean getColIsIdentity(int i) {
        return ValuePool.getBoolean(table.getColumn(i).isIdentity());
    }

    /**
     * Retrieves whether the specified column is nullable. <p>
     *
     * If the column is nullable, "YES" is retrieved, else "NO". <p>
     *
     * @param i zero-based column index
     * @return the nullability of the specified column
     */
    String getColIsNullable(int i) {

        Column column = table.getColumn(i);

        return (column.isNullable() &&!column.isIdentity()) ? "YES"
                                                            : "NO";
    }

    /**
     * Retrieves the simple name of the specified column. <p>
     *
     * @param i zero-based column index
     * @return the simple name of the specified column.
     */
    String getColName(int i) {
        return table.getColumn(i).columnName.name;
    }

    /**
     * Retrieves the specified column's nullablility. <p>
     *
     * @param i zero-based column index
     * @return the specified column's nullablilit
     */
    Integer getColNullability(int i) {

        Column column = table.getColumn(i);

        return (column.isNullable() &&!column.isIdentity())
               ? ValuePool.getInt(DITypeInfo.columnNullable)
               : ValuePool.getInt(DITypeInfo.columnNoNulls);
    }

    /**
     * Retrieves the number base that should be used to interpret the
     * specified column's numeric precision, as reported by getColSize(int).
     *
     * @param i zero-based column index
     * @return the number base that should be used to
     *    interpret the column's numeric precision,
     *    as reported by getColSize(int).
     */
    Integer getColPrecRadix(int i) {

        ti.setTypeCode(table.getColumn(i).getDIType());

        return ti.getNumPrecRadix();
    }

    /**
     * Retrieves the remarks, if any, recorded against the specified
     * column. <p>
     *
     * @param i zero-based column index
     * @return the remarks recorded against the specified column.
     */
    String getColRemarks(int i) {

        String key;

        if (table.getTableType() != Table.SYSTEM_TABLE) {
            return null;
        }

        key = getName() + "_" + getColName(i);

        return BundleHandler.getString(hnd_column_remarks, key);
    }

    /**
     * Retrieves the declared (but not currently enforced) or implicit fixed
     * number of digits to the right of the decimal point for exact numeric
     * types.
     *
     * If the column's type precludes scale declaration, null is returned.
     *
     * @param i zero-based column index
     * @return the fixed number of digits to the right of the decimal point
     * for exact numeric types.
     */
    Integer getColScale(int i) {

        Column column;
        int    type;

        column = table.getColumn(i);
        type   = column.getDIType();

        return Types.acceptsScaleCreateParam(type)
               ? ValuePool.getInt(column.getScale())
               : null;
    }

    /**
     * Retrieves null (not implemented). <p>
     *
     * @param i zero-based column index
     * @return null (not implemented)
     */
    String getColScopeCat(int i) {
        return null;
    }

    /**
     * Retrieves null (not implemented). <p>
     *
     * @param i zero-based column index
     * @return null (not implemented)
     */
    String getColScopeSchem(int i) {
        return null;
    }

    /**
     * Retrieves null (not implemented). <p>
     *
     * @param i zero-based column index
     * @return null (not implemented)
     */
    String getColScopeTable(int i) {
        return null;
    }

    /**
     * Retrieves either the declared or maximum length/precision for
     * the specified column, if its type allows a precision/length
     * declaration, else null. <p>
     *
     * @param i zero-based column index
     * @return the declared or maximum length/precision for
     *    the specified column
     */
    Integer getColSize(int i) {

        Column column;
        int    type;
        int    size;

        column = table.getColumn(i);
        type   = column.getDIType();

        if (!Types.acceptsPrecisionCreateParam(type)) {
            return null;
        }

        size = column.getSize();

        if (size > 0) {
            return ValuePool.getInt(size);
        } else {
            ti.setTypeCode(type);

            return ti.getPrecision();
        }
    }

    /**
     * Retrieves the SQL CLI data type code for the specified column. <p>
     *
     * @param i zero-based column index
     * @return the SQL CLI data type code for the specified column
     */
    Integer getColSqlDataType(int i) {

        ti.setTypeCode(table.getColumn(i).getDIType());

        return ti.getSqlDataType();
    }

    /**
     * Retrieves the SQL CLI datetime subtype for the specified column. <p>
     *
     * @param i zero-based column index
     * @return the SQL CLI datetime subtype for the specified column
     */
    Integer getColSqlDateTimeSub(int i) {

        ti.setTypeCode(table.getColumn(i).getDIType());

        return ti.getSqlDateTimeSub();
    }

    /**
     * Retrieves the full data source descriptor for [TEMP] TEXT tables. <p>
     *
     * @return the full data source descriptor
     */
    String getDataSource() {
        return table.getDataSource();
    }

    /**
     * Retrieves the HSQLDB-specific type of the table. <p>
     *
     * @return the HSQLDB-specific type of the table
     */
    String getHsqlType() {

        switch (table.getTableType()) {

            case Table.MEMORY_TABLE :
            case Table.TEMP_TABLE :
            case Table.SYSTEM_TABLE :
                return "MEMORY";

            case Table.CACHED_TABLE :
                return "CACHED";

            case Table.TEMP_TEXT_TABLE :
            case Table.TEXT_TABLE :
                return "TEXT";

            case Table.VIEW :
            default :
                return null;
        }
    }

    /**
     * Retrieves null (not implemented). <p>
     *
     * @param i zero-based index specifier
     * @return null (not implemented)
     */
    Integer getIndexCardinality(int i) {
        return null;
    }

    /**
     * Retrieves the sort-direction for the specified column in the
     * specified index. <p>
     *
     * @param i zero-based index specifier
     * @param columnPosition zero-based ordinal position of column in index
     * @return the sort-direction for the specified column in the
     * specified index
     */
    String getIndexColDirection(int i, int columnPosition) {

        // so far, hsqldb only supports completely ascending indexes
        return "A";
    }

    /**
     * Retrieves an array map from the zero-based ordinal positions of the
     * columns in the specfied Index to the zero-based ordinal positions of
     * the same columns in the table. <p>
     *
     * @param i zero-based index specifier
     * @return an array map from the zero-based ordinal positions of
     *    the columns in the specfied Index to the zero-based
     *    ordinal positions of the same columns in the table
     */
    int[] getIndexColumns(int i) {
        return table.getIndex(i).getColumns();
    }

    /**
     * Retrieves the simple name of the specified Index. <p>
     *
     * @param i zero-based index specifier
     * @return the simple name of the specified Index
     */
    String getIndexName(int i) {
        return table.getIndex(i).getName().name;
    }

    /**
     * Retrieves null (not implemented). <p>
     *
     * @param i zero-based index specifier
     * @return null (not implemented)
     */
    Integer getIndexRowCardinality(int i) {
        return null;
    }

    /**
     * Retrieves the DatabaseMetaData type code of the specified Index. <p>
     *
     * @param i zero-based index specifier
     * @return the DatabaseMetaData type code of the specified Index
     */
    Integer getIndexType(int i) {
        return ValuePool.getInt(tableIndexOther);
    }

    /**
     * Retrieves the number of visible columns in the specified Index.  That
     * is, this retrieves one less than the physical number of columns if the
     * table maintains an internal primary index on an internal identity
     * column, as is the case when the table has no declared primary key or
     * identity column. <p>
     *
     * @param i zero-based index specifier
     * @return the number of visible columns in the specified Index
     */
    int getIndexVisibleColumns(int i) {
        return table.getIndex(i).getVisibleColumns();
    }

    /**
     * Retrieves the simple name of the table. <p>
     *
     * @return the simple name of the table
     */
    String getName() {
        return table.getName().name;
    }

    /**
     * Retrieves the value of the next automatically assigned identity. <p>
     *
     * Be aware that this is not necessarily the value that will be assigned
     * to the identity column during the next insert or update.  This value is
     * used if NULL is either implicitly or explicity assigned. <p>
     *
     * @return the value of the next automatically assigned identity
     */
    Long getNextIdentity() {

        Index pi;

        if (table.identityColumn < 0) {
            return null;
        }

        return ValuePool.getLong(table.identitySequence.peek());
    }

    /**
     * Retrieves the remarks (if any) recorded against the Table. <p>
     *
     * @return the remarks recorded against the Table
     */
    String getRemark() {

        return (table.getTableType() == Table.SYSTEM_TABLE)
               ? BundleHandler.getString(hnd_table_remarks, getName())
               : null;
    }

    /**
     * Retrieves the standard JDBC type of the table. <p>
     *
     * "TABLE" for user-defined tables, "VIEW" for user-defined views,
     * and so on.
     *
     * @return the standard JDBC type of the table
     */
    String getStandardType() {

        switch (table.getTableType()) {

            case Table.VIEW :
                return "VIEW";

            case Table.TEMP_TABLE :
            case Table.TEMP_TEXT_TABLE :
                return "GLOBAL TEMPORARY";

            case Table.SYSTEM_TABLE :
                return "SYSTEM TABLE";

            default :
                return "TABLE";
        }
    }

    /**
     * Retrieves the Table object on which this object is currently
     * reporting. <p>
     *
     * @return the Table object on which this object
     *    is currently reporting
     */
    Table getTable() {
        return this.table;
    }

    /**
     * Retrieves, for [TEMP] TEXT tables, whether the table's data source
     * descriptor requests descending read semantics.  That is, when this
     * value is true, it indicate that the text file is to be read from
     * the bottom up. <p>
     *
     * @return whether the table's data source
     *    descriptor requests descending
     *    read semantics
     */
    Boolean isDataSourceDescending() {
        return ValuePool.getBoolean(table.isDescDataSource());
    }

    /**
     * Retrieves whether the specified Index is non-unique. <p>
     *
     * @param i zero-based index specifier
     * @return whether the specified Index is non-unique
     */
    Boolean isIndexNonUnique(int i) {
        return ValuePool.getBoolean(!table.getIndex(i).isUnique());
    }

    /**
     * Retrieves whether the table is in data read-only mode.  This value does
     * not reflect the various read-only modes of the database or the
     * read-only mode of the connection. <p>
     *
     * @return whether the table is in data read-only mode
     */
    Boolean isReadOnly() {
        return ValuePool.getBoolean(table.isDataReadOnly());
    }

    /**
     * Assigns the Table object on which this object is to report. <p>
     *
     * @param table the Table object on which this object is to report
     */
    void setTable(Table table) {
        this.table = table;
    }
}
