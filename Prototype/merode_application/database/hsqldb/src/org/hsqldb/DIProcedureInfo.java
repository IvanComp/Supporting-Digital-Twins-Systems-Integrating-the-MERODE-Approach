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

import java.io.Serializable;
import java.lang.reflect.Method;

import org.hsqldb.lib.HashMap;
import org.hsqldb.lib.HsqlArrayList;
import org.hsqldb.resources.BundleHandler;
import org.hsqldb.store.ValuePool;
import org.hsqldb.types.Binary;

/**@todo fredt - move Trace.doAssert() literals to Trace*/

/**
 * Provides information about HSQLDB SQL-invoked routines and SQL functions. <p>
 *
 * In particular, this class provides information about Java Methods in a form
 * compatible with presentation via the related HSQLDB system tables,
 * SYSTEM_PROCEDURES and SYSTEM_PROCEDURECOLUMNS, involved in the production of
 * the DatabaseMetaData getProcedures and getProcedureColumns result sets.
 *
 * @author  boucherb@users
 * @version 1.7.2
 * @since 1.7.2
 */
final class DIProcedureInfo {

    // java.sql dependencies mostly removed
    static final String   conClsName               = "java.sql.Connection";
    static final int      procedureResultUnknown   = 0;
    static final int      procedureNoResult        = 1;
    static final int      procedureReturnsResult   = 2;
    static final int      procedureColumnUnknown   = 0;
    static final int      procedureColumnIn        = 1;
    static final int      procedureColumnInOut     = 2;
    static final int      procedureColumnResult    = 3;
    static final int      procedureColumnOut       = 4;
    static final int      procedureColumnReturn    = 5;
    static final int      procedureNoNulls         = 0;
    static final int      procedureNullable        = 1;
    static final int      procedureNullableUnknown = 2;
    private Class         clazz;
    private Class[]       colClasses;
    private int[]         colTypes;
    private int           colOffset;
    private int           colCount;
    private boolean       colsResolved;
    private String        fqn;
    private String        specificName;
    private int           hnd_remarks;
    private Method        method;
    private String        sig;
    private DINameSpace   nameSpace;
    private final HashMap typeMap = new HashMap();

    public DIProcedureInfo(DINameSpace ns) throws HsqlException {
        setNameSpace(ns);
    }

    private int colOffset() {

        if (!colsResolved) {
            resolveCols();
        }

        return colOffset;
    }

    HsqlArrayList getAliases() {
        return (HsqlArrayList) nameSpace.getInverseAliasMap().get(getFQN());
    }

    Class getColClass(int i) {
        return colClasses[i + colOffset()];
    }

    int getColCount() {

        if (!colsResolved) {
            resolveCols();
        }

        return colCount;
    }

    Integer getColDataType(int i) {
        return ValuePool.getInt(getColTypeCode(i));
    }

    Integer getColLen(int i) {

        int size;
        int type;

        type = getColTypeCode(i);

        switch (type) {

            case Types.BINARY :
            case Types.LONGVARBINARY :
            case Types.VARBINARY : {
                size = Integer.MAX_VALUE;

                break;
            }
            case Types.BIGINT :
            case Types.DOUBLE :
            case Types.DATE :
            case Types.FLOAT :
            case Types.TIME : {
                size = 8;

                break;
            }
            case Types.TIMESTAMP : {
                size = 12;

                break;
            }
            case Types.REAL :
            case Types.INTEGER : {
                size = 4;

                break;
            }
            case Types.SMALLINT : {
                size = 2;

                break;
            }
            case Types.TINYINT :
            case Types.BOOLEAN : {
                size = 1;

                break;
            }
            default :
                size = 0;
        }

        return (size == 0) ? null
                           : ValuePool.getInt(size);
    }

    String getColName(int i) {
        return CompiledStatement.PCOL_PREFIX + (i + colOffset());
    }

    Integer getColNullability(int i) {

        int cn;

        cn = getColClass(i).isPrimitive() ? procedureNoNulls
                                          : procedureNullable;

        return ValuePool.getInt(cn);
    }

    String getColRemark(int i) {

        String       key;
        StringBuffer sb;

        sb  = new StringBuffer(getSignature());
        key = sb.append('@').append(i + colOffset()).toString();

        return BundleHandler.getString(hnd_remarks, key);
    }

    // JDBC sort-contract:
    // out return value column, then in/in out/out parameter columns
    // in formal order, then result columns in column order
    //
    // Currently, we materialize the java method return value, if
    // any, as a result column, not as an OUT return value column, so
    // it should actually appear _after_ the other procedure columns
    // in the row order returned by the JDBC getProcedureColumns() method
    int getColSequence(int i) {

        // colOffset has the side-effect of setting colCount properly
        return (i + colOffset() == 0) ? colCount
                                      : i;
    }

    int getColTypeCode(int i) {

        i += colOffset();

        return colTypes[i];
    }

    Integer getColUsage(int i) {

        switch (i + colOffset()) {

            case 0 : {

                // Currently, we materialize the java method return value, if
                // any, as a result column, not as an OUT return column
                return ValuePool.getInt(procedureColumnResult);
            }

            // todo: registration and reporting on result columns for routines
            //       that generate real" result sets
            default : {

                // We could get religious here and maybe report IN OUT
                // for newRow of before update for each row trigger methods,
                // but there's not really any added value
                return ValuePool.getInt(procedureColumnIn);
            }
        }
    }

    Class getDeclaringClass() {
        return this.clazz;
    }

    String getFQN() {

        StringBuffer sb;

        if (fqn == null) {
            sb = new StringBuffer();
            fqn = sb.append(clazz.getName()).append('.').append(
                method.getName()).toString();
        }

        return fqn;
    }

    String getSpecificName() {

        if (specificName == null) {
            specificName = clazz.getName() + "." + getSignature();
        }

        return specificName;
    }

    Integer getInputParmCount() {
        return ValuePool.getInt(method.getParameterTypes().length);
    }

    Method getMethod() {
        return this.method;
    }

    String getOrigin(String srcType) {
        return (nameSpace.isBuiltin(clazz) ? "BUILTIN "
                                           : "USER DEFINED ") + srcType;
    }

    Integer getOutputParmCount() {

        // no support for IN OUT or OUT columns yet
        return ValuePool.getInt(0);
    }

    String getRemark() {
        return BundleHandler.getString(hnd_remarks, getSignature());
    }

    Integer getResultSetCount() {
        return (method.getReturnType() == Void.TYPE) ? ValuePool.getInt(0)
                                                     : ValuePool.getInt(1);
    }

    Integer getResultType(String origin) {

        int type;

        type = !"ROUTINE".equals(origin) ? procedureResultUnknown
                                         : method.getReturnType()
                                           == Void.TYPE ? procedureNoResult
                                                        : procedureReturnsResult;

        return ValuePool.getInt(type);
    }

    String getSignature() {

        if (sig == null) {
            sig = DINameSpace.getSignature(method);
        }

        return sig;
    }

    /**
     * Retrieves the specific name of the given Method object. <p>
     *
     * @param m The Method object for which to retreive the specific name
     * @return the specific name of the specified Method object.
     */
    static String getMethodSpecificName(Method m) {

        return m == null ? null
                         : m.getDeclaringClass().getName() + '.'
                           + DINameSpace.getSignature(m);
    }

    DINameSpace getNameSpace() {
        return nameSpace;
    }

    void setNameSpace(DINameSpace ns) throws HsqlException {

        nameSpace = ns;

        Class   c;
        Integer type;

        // can only speed up test significantly for java.lang.Object,
        // final classes, primitive types and hierachy parents.
        // Must still check later if assignable from candidate classes, where
        // hierarchy parent is not final.
        //ARRAY
        try {
            c = nameSpace.classForName("org.hsqldb.jdbc.jdbcArray");

            typeMap.put(c, ValuePool.getInt(Types.ARRAY));
        } catch (Exception e) {}

        // BIGINT
        type = ValuePool.getInt(Types.BIGINT);

        typeMap.put(Long.TYPE, type);
        typeMap.put(Long.class, type);

        // BOOLEAN
        type = ValuePool.getInt(Types.BOOLEAN);

        typeMap.put(Boolean.TYPE, type);
        typeMap.put(Boolean.class, type);

        // BLOB
        type = ValuePool.getInt(Types.BLOB);

        try {
            c = nameSpace.classForName("org.hsqldb.jdbc.jdbcBlob");

            typeMap.put(c, type);
        } catch (Exception e) {}

        // CHAR
        type = ValuePool.getInt(Types.CHAR);

        typeMap.put(Character.TYPE, type);
        typeMap.put(Character.class, type);
        typeMap.put(Character[].class, type);
        typeMap.put(char[].class, type);

        // CLOB
        type = ValuePool.getInt(Types.CLOB);

        try {
            c = nameSpace.classForName("org.hsqldb.jdbc.jdbcClob");

            typeMap.put(c, type);
        } catch (Exception e) {}

        // DATALINK
        type = ValuePool.getInt(Types.DATALINK);

        typeMap.put(java.net.URL.class, type);

        // DATE
        type = ValuePool.getInt(Types.DATE);

        typeMap.put(java.util.Date.class, type);
        typeMap.put(java.sql.Date.class, type);

        // DECIMAL
        type = ValuePool.getInt(Types.DECIMAL);

        try {
            c = nameSpace.classForName("java.math.BigDecimal");

            typeMap.put(c, type);
        } catch (Exception e) {}

        // DISTINCT
        try {
            c = nameSpace.classForName("org.hsqldb.jdbc.jdbcDistinct");

            typeMap.put(c, ValuePool.getInt(Types.DISTINCT));
        } catch (Exception e) {}

        // DOUBLE
        type = ValuePool.getInt(Types.DOUBLE);

        typeMap.put(Double.TYPE, type);
        typeMap.put(Double.class, type);

        // FLOAT : Not actually a legal IN parameter type yet
        type = ValuePool.getInt(Types.FLOAT);

        typeMap.put(Float.TYPE, type);
        typeMap.put(Float.class, type);

        // INTEGER
        type = ValuePool.getInt(Types.INTEGER);

        typeMap.put(Integer.TYPE, type);
        typeMap.put(Integer.class, type);

        // JAVA_OBJECT
        type = ValuePool.getInt(Types.JAVA_OBJECT);

        typeMap.put(Object.class, type);

        // LONGVARBINARY
        type = ValuePool.getInt(Types.LONGVARBINARY);

        typeMap.put(byte[].class, type);
        typeMap.put(Binary.class, type);

        // LONGVARCHAR
        type = ValuePool.getInt(Types.LONGVARCHAR);

        typeMap.put(String.class, type);

        // NULL
        type = ValuePool.getInt(Types.NULL);

        typeMap.put(Void.TYPE, type);
        typeMap.put(Void.class, type);

        // REF
        type = ValuePool.getInt(Types.REF);

        try {
            c = nameSpace.classForName("org.hsqldb.jdbc.jdbcRef");

            typeMap.put(c, type);
        } catch (Exception e) {}

        // SMALLINT : Not actually a legal IN parameter type yet
        type = ValuePool.getInt(Types.SMALLINT);

        typeMap.put(Short.TYPE, type);
        typeMap.put(Short.class, type);

        // STRUCT :
        type = ValuePool.getInt(Types.STRUCT);

        try {
            c = nameSpace.classForName("org.hsqldb.jdbc.jdbcStruct");

            typeMap.put(c, type);
        } catch (Exception e) {}

        // TIME
        type = ValuePool.getInt(Types.TIME);

        typeMap.put(java.sql.Time.class, type);

        // TIMESTAMP
        type = ValuePool.getInt(Types.TIMESTAMP);

        typeMap.put(java.sql.Timestamp.class, type);

        // TINYINT : Not actually a legal IN parameter type yet
        type = ValuePool.getInt(Types.TINYINT);

        typeMap.put(Byte.TYPE, type);
        typeMap.put(Byte.class, type);

        // XML : Not actually a legal IN parameter type yet
        type = ValuePool.getInt(Types.XML);

        try {
            c = nameSpace.classForName("org.w3c.dom.Document");

            typeMap.put(c, type);

            c = nameSpace.classForName("org.w3c.dom.DocumentFragment");

            typeMap.put(c, type);
        } catch (Exception e) {}
    }

    private void resolveCols() {

        Class   rType;
        Class[] pTypes;
        Class   clazz;
        int     ptlen;
        int     pclen;
        boolean isFPCON;

        rType         = method.getReturnType();
        pTypes        = method.getParameterTypes();
        ptlen         = pTypes.length;
        isFPCON       = ptlen > 0 && pTypes[0].getName().equals(conClsName);
        pclen         = 1 + ptlen - (isFPCON ? 1
                                             : 0);
        colClasses    = new Class[pclen];
        colTypes      = new int[pclen];
        colClasses[0] = rType;
        colTypes[0]   = typeForClass(rType);

        for (int i = isFPCON ? 1
                             : 0, idx = 1; i < ptlen; i++, idx++) {
            clazz           = pTypes[i];
            colClasses[idx] = clazz;
            colTypes[idx]   = typeForClass(clazz);
        }

        colOffset = rType == Void.TYPE ? 1
                                       : 0;
        colCount  = pclen - colOffset;
    }

    /**
     * This requires the following properties files:
     *
     * org_hsqldb_Library.properties
     * java_math.properties
     */
    void setMethod(Method m) {

        String remarkKey;

        method       = m;
        clazz        = method.getDeclaringClass();
        fqn          = null;
        specificName = null;
        sig          = null;
        colsResolved = false;
        remarkKey    = clazz.getName().replace('.', '_');
        hnd_remarks  = BundleHandler.getBundleHandle(remarkKey, null);
    }

    int typeForClass(Class c) {

        Class   to;
        Integer type = (Integer) typeMap.get(c);

        if (type != null) {
            return type.intValue();
        }

        // ARRAY (dimension 1)
        // HSQLDB does not yet support ARRAY for SQL, but
        // Trigger.fire takes Object[] row, which we report.
        // Also, it's just friendly to show what "would"
        // be required if/when we support ARRAY in a broader
        // sense
        if (c.isArray() &&!c.getComponentType().isArray()) {
            return Types.ARRAY;
        }

        try {
            to = Class.forName("java.sql.Array");

            if (to.isAssignableFrom(c)) {
                return Types.ARRAY;
            }
        } catch (Exception e) {}

        // NUMERIC
        // All java.lang.Number impls and BigDecimal have
        // already been covered by lookup in typeMap.
        // They are all final, so this is OK.
        if (Number.class.isAssignableFrom(c)) {
            return Types.NUMERIC;
        }

        // TIMESTAMP
        try {
            to = Class.forName("java.sql.Timestamp");

            if (to.isAssignableFrom(c)) {
                return Types.TIMESTAMP;
            }
        } catch (Exception e) {}

        // TIME
        try {
            to = Class.forName("java.sql.Time");

            if (to.isAssignableFrom(c)) {
                return Types.TIMESTAMP;
            }
        } catch (Exception e) {}

        // DATE
        try {
            to = Class.forName("java.sql.Date");

            if (to.isAssignableFrom(c)) {
                return Types.DATE;
            }
        } catch (Exception e) {}

        // BLOB
        try {
            to = Class.forName("java.sql.Blob");

            if (to.isAssignableFrom(c)) {
                return Types.BLOB;
            }
        } catch (Exception e) {}

        // CLOB
        try {
            to = Class.forName("java.sql.Clob");

            if (to.isAssignableFrom(c)) {
                return Types.CLOB;
            }
        } catch (Exception e) {}

        // REF
        try {
            to = Class.forName("java.sql.Ref");

            if (to.isAssignableFrom(c)) {
                return Types.REF;
            }
        } catch (Exception e) {}

        // STRUCT
        try {
            to = Class.forName("java.sql.Struct");

            if (to.isAssignableFrom(c)) {
                return Types.STRUCT;
            }
        } catch (Exception e) {}

        // LONGVARBINARY : org.hsqldb.Binary is not final
        if (Binary.class.isAssignableFrom(c)) {
            return Types.LONGVARBINARY;
        }

        // LONGVARCHAR : really OTHER at this point
        try {

            // @since JDK1.4
            to = Class.forName("java.lang.CharSequence");

            if (to.isAssignableFrom(c)) {
                return Types.LONGVARCHAR;
            }
        } catch (Exception e) {}

        // we have no standard mapping for the specified class
        // at this point...is it even storable?
        if (Serializable.class.isAssignableFrom(c)) {

            // Yes: it is storable, as an OTHER.
            return Types.OTHER;
        }

        // It may (in future, say using bean contract) or may not be storable
        // (by HSQLDB)...
        // but then it may be possible to pass to an in-process routine,
        // so be lenient and just return the most generic type.
        return Types.JAVA_OBJECT;
    }
}
