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


package org.hsqldb.rowio;

import org.hsqldb.CachedRow;
import org.hsqldb.Table;
import org.hsqldb.lib.HashMappedList;
import org.hsqldb.lib.HsqlByteArrayOutputStream;

/**
 * Public interface for writing the data for a database row.
 *
 * @author sqlbob@users (RMP)
 * @author fredt@users
 * @version 1.7.2
 * @since 1.7.0
 */
public interface RowOutputInterface {

    void writeEnd();

    void writeSize(int size);

    void writeType(int type);

    void writeString(String value);

    void writeShortData(short i);

    void writeIntData(int i);

    void writeIntData(int i, int position);

    void writeLongData(long i);

    void writeRow(Object[] data, Table t);

    void writeData(Object[] data, Table t);

    void writeData(int l, int[] types, Object[] data, HashMappedList cols,
                   int[] primarykeys);

    // independent of the this object, calls only a static method
    int getSize(CachedRow row);

    // returns the underlying HsqlByteArrayOutputStream
    HsqlByteArrayOutputStream getOutputStream();

    // resets the byte[] buffer, ready for processing new row
    void reset();

    // returns the current size
    int size();
}
