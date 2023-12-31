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

import java.io.IOException;

import org.hsqldb.lib.InOutUtil;

/**
 * HTTP protocol session proxy implementation. Uses the updated HSQLDB HTTP
 * sub protocol.
 *
 * @author fredt@users
 * @version 1.7.2
 * @since 1.7.2
 */
public class HTTPClientConnection extends HSQLClientConnection {

    static final String ENCODING = "8859_1";

    public HTTPClientConnection(String host, int port, String path,
                                String database, boolean isTLS, String user,
                                String password) throws HsqlException {
        super(host, port, path, database, isTLS, user, password);
    }

    protected void initConnection(String host, int port,
                                  boolean isTLS) throws HsqlException {}

    public synchronized Result execute(Result r) throws HsqlException {

        super.openConnection(host, port, isTLS);

        Result result = super.execute(r);

        super.closeConnection();

        return result;
    }

    protected void write(Result r) throws IOException, HsqlException {

        rowOut.reset();
        r.write(rowOut);
        dataOutput.write("POST ".getBytes(ENCODING));
        dataOutput.write(path.getBytes(ENCODING));
        dataOutput.write(" HTTP/1.0\r\n".getBytes(ENCODING));
        dataOutput.write(
            "Content-Type: application/octet-stream\r\n".getBytes(ENCODING));
        dataOutput.write(("Content-Length: " + rowOut.size()
                          + "\r\n").getBytes(ENCODING));
        dataOutput.write("\r\n".getBytes(ENCODING));
        dataOutput.write(rowOut.getOutputStream().getBuffer(), 0,
                         rowOut.getOutputStream().size());
        dataOutput.flush();
    }

    protected Result read() throws IOException, HsqlException {

        // fredt - for WebServer 4 lines should be skipped
        // for Servlet, number of lines depends on Servlet container
        // stop skipping after the blank line
        rowOut.reset();

        for (;;) {
            int count = InOutUtil.readLine(dataInput, rowOut);

            if (count <= 2) {
                break;
            }
        }

        //
        Result resultIn = Result.read(rowIn, dataInput);

        return resultIn;
    }
}
