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


package org.hsqldb.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* $Id: SqlFile.java,v 1.170 2008/03/15 13:53:46 fredt Exp $ */

/**
 * Encapsulation of a sql text file like 'myscript.sql'.
 * The ultimate goal is to run the execute() method to feed the SQL
 * commands within the file to a jdbc connection.
 *
 * Some implementation comments and variable names use keywords based
 * on the following definitions.  <UL>
 * <LI> COMMAND = Statement || SpecialCommand || BufferCommand
 * Statement = SQL statement like "SQL Statement;"
 * SpecialCommand =  Special Command like "\x arg..."
 * BufferCommand =  Editing/buffer command like ":s/this/that/"
 *
 * When entering SQL statements, you are always "appending" to the
 * "immediate" command (not the "buffer", which is a different thing).
 * All you can do to the immediate command is append new lines to it,
 * execute it, or save it to buffer.
 * When you are entering a buffer edit command like ":s/this/that/",
 * your immediate command is the buffer-edit-command.  The buffer
 * is the command string that you are editing.
 * The buffer usually contains either an exact copy of the last command
 * executed or sent to buffer by entering a blank line,
 * but BUFFER commands can change the contents of the buffer.
 *
 * In general, the special commands mirror those of Postgresql's psql,
 * but SqlFile handles command editing much different from Postgresql
 * because of Java's lack of support for raw tty I/O.
 * The \p special command, in particular, is very different from psql's.
 *
 * Buffer commands are unique to SQLFile.  The ":" commands allow
 * you to edit the buffer and to execute the buffer.
 *
 * \d commands are very poorly supported for Mysql because
 * (a) Mysql lacks most of the most basic JDBC support elements, and
 * the most basic role and schema features, and
 * (b) to access the Mysql data dictionay, one must change the database
 * instance (to do that would require work to restore the original state
 * and could have disastrous effects upon transactions).
 *
 * To make changes to this class less destructive to external callers,
 * the input parameters should be moved to setters (probably JavaBean
 * setters would be best) instead of constructor args and System
 * Properties.
 *
 * The process*() methods, other than processBuffHist() ALWAYS execute
 * on "buffer", and expect it to contain the method specific prefix
 * (if any).
 *
 * @version $Revision: 1.170 $
 * @author Blaine Simpson unsaved@users
 */

public class SqlFile {
    private static final int DEFAULT_HISTORY_SIZE = 40;
    private File             file;
    private boolean          interactive;
    private String           primaryPrompt    = "sql> ";
    private String           rawPrompt      = null;
    private String           contPrompt       = "  +> ";
    private Connection       curConn          = null;
    private boolean          htmlMode         = false;
    private Map              userVars; // Always a non-null map set in cons.
    private List             history          = null;
    private int              rawMode          = RAW_FALSE;
    private String           nullRepToken     = null;
    private String           dsvTargetFile    = null;
    private String           dsvTargetTable   = null;
    private String           dsvConstCols     = null;
    private String           dsvRejectFile    = null;
    private String           dsvRejectReport  = null;
    public static String     LS = System.getProperty("line.separator");
    private int              maxHistoryLength = 1;
    private SqltoolRB        rb               = null;
    private String           magicPrefix      = null;
    // For append editing, this is automatically prefixed to what the
    // user enters.

    private static final int RAW_FALSE = 0; // Raw mode off
    private static final int RAW_EMPTY = 1; // Raw mode on, but no raw input yet
    private static final int RAW_DATA  = 2; // Raw mode on and we have input

    /**
     * N.b. javax.util.regex Optional capture groups (...)? are completely
     * unpredictable wrt whether you get a null capture group vs. no capture.
     * Must always check count!
     */
    private static Pattern   specialPattern =
            Pattern.compile("\\s*\\\\(\\S+)(?:\\s+(.*\\S))?\\s*");
    private static Pattern   plPattern  =
            Pattern.compile("\\s*\\*\\s*(.*\\S)?\\s*");
    private static Pattern   foreachPattern =
            Pattern.compile("\\s*\\*\\s*foreach\\s+(\\S+)\\s*\\(([^)]*)\\)\\s*");
    private static Pattern   ifwhilePattern =
            Pattern.compile("\\s*\\*\\s*\\S+\\s*\\(([^)]*)\\)\\s*");
    private static Pattern   varsetPattern =
            Pattern.compile("\\s*\\*\\s*(\\S+)\\s*([=_~])\\s*(?:(.*\\S)\\s*)?");
    private static Pattern   substitutionPattern =
            Pattern.compile("(\\S)(.+?)\\1(.*?)\\1(.+)?\\s*");
            // Note that this pattern does not include the leading ":s".
    private static Pattern   slashHistoryPattern =
            Pattern.compile("\\s*/([^/]+)/\\s*(\\S.*)?");
    private static Pattern   historyPattern =
            Pattern.compile("\\s*(-?\\d+)?\\s*(\\S.*)?");
            // Note that this pattern does not include the leading ":".
    private static Pattern wincmdPattern = null;

    static {
        if (System.getProperty("os.name").startsWith("Windows")) {
            wincmdPattern = Pattern.compile("([^\"]+)?(\"[^\"]*\")?");
        }
    }
    // This can throw a runtime exception, but since the pattern
    // Strings are constant, one test run of the program will tell
    // if the patterns are good.

    /**
     * Encapsulate updating local variables which depend upon PL variables.
     *
     * Right now this is called whenever the user variable map is changed.
     * It would be more efficient to do it JIT by keeping track of when
     * the vars may be "dirty" by a variable map change, and having all
     * methods that use the settings call a conditional updater, but that
     * is less reliable since there is no way to guarantee that the vars
     * are not used without checking.
     */
    private void updateUserSettings() {
        dsvSkipPrefix = SqlFile.convertEscapes(
                (String) userVars.get("*DSV_SKIP_PREFIX"));
        if (dsvSkipPrefix == null) {
            dsvSkipPrefix = DEFAULT_SKIP_PREFIX;
        }
        dsvSkipCols = (String) userVars.get("*DSV_SKIP_COLS");
        dsvColDelim =
            SqlFile.convertEscapes((String) userVars.get("*DSV_COL_DELIM"));
        if (dsvColDelim == null) {
            dsvColDelim =
                SqlFile.convertEscapes((String) userVars.get("*CSV_COL_DELIM"));
        }
        if (dsvColDelim == null) {
            dsvColDelim = DEFAULT_COL_DELIM;
        }

        dsvRowDelim =
            SqlFile.convertEscapes((String) userVars.get("*DSV_ROW_DELIM"));
        if (dsvRowDelim == null) {
            dsvRowDelim =
                SqlFile.convertEscapes((String) userVars.get("*CSV_ROW_DELIM"));
        }
        if (dsvRowDelim == null) {
            dsvRowDelim = DEFAULT_ROW_DELIM;
        }

        dsvTargetFile = (String) userVars.get("*DSV_TARGET_FILE");
        if (dsvTargetFile == null) {
            dsvTargetFile = (String) userVars.get("*CSV_FILEPATH");
        }
        dsvTargetTable = (String) userVars.get("*DSV_TARGET_TABLE");
        if (dsvTargetTable == null) {
            dsvTargetTable = (String) userVars.get("*CSV_TABLENAME");
            // This just for legacy variable name.
        }

        dsvConstCols = (String) userVars.get("*DSV_CONST_COLS");
        dsvRejectFile = (String) userVars.get("*DSV_REJECT_FILE");
        dsvRejectReport = (String) userVars.get("*DSV_REJECT_REPORT");

        nullRepToken = (String) userVars.get("*NULL_REP_TOKEN");
        if (nullRepToken == null) {
            nullRepToken = (String) userVars.get("*CSV_NULL_REP");
        }
        if (nullRepToken == null) {
            nullRepToken = DEFAULT_NULL_REP;
        }
    }

    /**
     * Private class to "share" a variable among a family of SqlFile
     * instances.
     */
    private static class BooleanBucket {
        BooleanBucket() {}
        private boolean bPriv = false;

        public void set(boolean bIn) {
            bPriv = bIn;
        }

        public boolean get() {
            return bPriv;
        }
    }

    BooleanBucket possiblyUncommitteds = new BooleanBucket();
    // This is an imperfect solution since when user runs SQL they could
    // be running DDL or a commit or rollback statement.  All we know is,
    // they MAY run some DML that needs to be committed.

    private static final String DIVIDER =
        "-----------------------------------------------------------------"
        + "-----------------------------------------------------------------";
    // Needs to be at least as wide as the widest field or header displayed.
    private static final String SPACES =
        "                                                                 "
        + "                                                                 ";
    // Needs to be at least as wide as the widest field or header displayed.
    private static String revnum = null;

    static {
        revnum = "354";
    }

    private String DSV_OPTIONS_TEXT = null;
    private String D_OPTIONS_TEXT = null;
    private String RAW_LEADIN_MSG = null;

    /**
     * Interpret lines of input file as SQL Statements, Comments,
     * Special Commands, and Buffer Commands.
     * Most Special Commands and many Buffer commands are only for
     * interactive use.
     *
     * @param inFile  inFile of null means to read stdin.
     * @param inInteractive  If true, prompts are printed, the interactive
     *                       Special commands are enabled, and
     *                       continueOnError defaults to true.
     * @throws IOException  If can't open specified SQL file.
     */
    public SqlFile(File inFile, boolean inInteractive, Map inVars)
            throws IOException {
        // Set up ResourceBundle first, so that any other errors may be
        // reported with localized messages.
        try {
            rb = new SqltoolRB();
            rb.validate();
            rb.setMissingPosValueBehavior(
                    ValidatingResourceBundle.NOOP_BEHAVIOR);
            rb.setMissingPropertyBehavior(
                    ValidatingResourceBundle.NOOP_BEHAVIOR);
        } catch (RuntimeException re) {
            System.err.println("Failed to initialize resource bundle");
            throw re;
        }
        rawPrompt = rb.getString(SqltoolRB.RAWMODE_PROMPT) + "> ";
        DSV_OPTIONS_TEXT = rb.getString(SqltoolRB.DSV_OPTIONS);
        D_OPTIONS_TEXT = rb.getString(SqltoolRB.D_OPTIONS);
        RAW_LEADIN_MSG = rb.getString(SqltoolRB.RAW_LEADIN);
        DSV_X_SYNTAX_MSG = rb.getString(SqltoolRB.DSV_X_SYNTAX);
        DSV_M_SYNTAX_MSG = rb.getString(SqltoolRB.DSV_M_SYNTAX);
        nobufferYetString = rb.getString(SqltoolRB.NOBUFFER_YET);

        file        = inFile;
        interactive = inInteractive;
        userVars    = inVars;
        if (userVars == null) {
            userVars = new HashMap();
        }
        updateUserSettings();

        if (file != null &&!file.canRead()) {
            throw new IOException(rb.getString(SqltoolRB.SQLFILE_READFAIL,
                    file.toString()));
        }
        if (interactive) {
            history = new ArrayList();
            String histLenString = System.getProperty("sqltool.historyLength");
            if (histLenString != null) try {
                maxHistoryLength = Integer.parseInt(histLenString);
            } catch (Exception e) {
            } else {
                maxHistoryLength = DEFAULT_HISTORY_SIZE;
            }
        }
    }

    /**
     * Constructor for reading stdin instead of a file for commands.
     *
     * @see #SqlFile(File,boolean)
     */
    public SqlFile(boolean inInteractive, Map inVars) throws IOException {
        this(null, inInteractive, inVars);
    }

    /**
     * Process all the commands on stdin.
     *
     * @param conn The JDBC connection to use for SQL Commands.
     * @see #execute(Connection,PrintStream,PrintStream,boolean)
     */
    public void execute(Connection conn,
                        Boolean coeOverride)
                        throws SqlToolError, SQLException {
        execute(conn, System.out, System.err, coeOverride);
    }

    /**
     * Process all the commands on stdin.
     *
     * @param conn The JDBC connection to use for SQL Commands.
     * @see #execute(Connection,PrintStream,PrintStream,boolean)
     */
    public void execute(Connection conn,
                        boolean coeOverride)
                        throws SqlToolError, SQLException {
        execute(conn, System.out, System.err, new Boolean(coeOverride));
    }

    // So we can tell how to handle quit and break commands.
    public boolean      recursed     = false;
    private String      lastSqlStatement   = null;
    private int         curLinenum   = -1;
    private PrintStream psStd        = null;
    private PrintStream psErr        = null;
    private PrintWriter pwQuery      = null;
    private PrintWriter pwDsv        = null;
    StringBuffer        immCmdSB     = new StringBuffer();
    private boolean             continueOnError = false;
    /*
     * This is reset upon each execute() invocation (to true if interactive,
     * false otherwise).
     */
    private static final String DEFAULT_CHARSET = null;
    // Change to Charset.defaultCharset().name(); once we can use Java 1.5!
    private BufferedReader      br              = null;
    private String              charset         = null;
    private String              buffer          = null;

    /**
     * Process all the commands in the file (or stdin) associated with
     * "this" object.
     * Run SQL in the file through the given database connection.
     *
     * This is synchronized so that I can use object variables to keep
     * track of current line number, command, connection, i/o streams, etc.
     *
     * Sets encoding character set to that specified with System Property
     * 'sqlfile.charset'.  Defaults to "US-ASCII".
     *
     * @param conn The JDBC connection to use for SQL Commands.
     * @throws SQLExceptions thrown by JDBC driver.
     *                       Only possible if in "\c false" mode.
     * @throws SqlToolError  all other errors.
     *               This includes including QuitNow, BreakException,
     *               ContinueException for recursive calls only.
     */
    public synchronized void execute(Connection conn, PrintStream stdIn,
                                     PrintStream errIn,
                                     Boolean coeOverride)
                                     throws SqlToolError,
                                         SQLException {
        psStd      = stdIn;
        psErr      = errIn;
        curConn    = conn;
        curLinenum = -1;

        String  inputLine;
        String  trimmedInput;
        String  deTerminated;
        boolean inComment = false;    // Gobbling up a comment
        int     postCommentIndex;
        boolean rollbackUncoms = true;

        continueOnError = (coeOverride == null) ? interactive
                                                : coeOverride.booleanValue();

        if (userVars.size() > 0) {
            plMode = true;
        }

        String specifiedCharSet = System.getProperty("sqlfile.charset");

        charset = ((specifiedCharSet == null) ? DEFAULT_CHARSET
                                              : specifiedCharSet);

        try {
            br = new BufferedReader((charset == null)
                    ?  (new InputStreamReader((file == null)
                            ? System.in : (new FileInputStream(file))))
                    :  (new InputStreamReader(((file == null)
                            ? System.in : (new FileInputStream(file))),
                                    charset)));
            // Replace with just "(new FileInputStream(file), charset)"
            // once use defaultCharset from Java 1.5 in charset init. above.
            curLinenum = 0;

            if (interactive) {
                stdprintln(rb.getString(SqltoolRB.SQLFILE_BANNER, revnum));
            }

            while (true) {
                if (interactive && magicPrefix == null) {
                    psStd.print((immCmdSB.length() > 0 || rawMode == RAW_DATA)
                            ? contPrompt : ((rawMode == RAW_FALSE)
                                    ? primaryPrompt : rawPrompt));
                }

                inputLine = br.readLine();
                if (magicPrefix != null) {
                    inputLine = magicPrefix + inputLine;
                    magicPrefix = null;
                }

                if (inputLine == null) {
                    /*
                     * This is because interactive EOD on some OSes doesn't
                     * send a line-break, resulting in no linebreak at all
                     * after the SqlFile prompt or whatever happens to be
                     * on their screen.
                     */
                    if (interactive) {
                        psStd.println();
                    }

                    break;
                }

                curLinenum++;

                if (inComment) {
                    postCommentIndex = inputLine.indexOf("*/") + 2;

                    if (postCommentIndex > 1) {
                        // I see no reason to leave comments in history.
                        inputLine = inputLine.substring(postCommentIndex);

                        // Empty the buffer.  The non-comment remainder of
                        // this line is either the beginning of a new SQL
                        // or Special command, or an empty line.
                        immCmdSB.setLength(0);

                        inComment = false;
                    } else {
                        // Just completely ignore the input line.
                        continue;
                    }
                }

                trimmedInput = inputLine.trim();

                try {
                    if (rawMode != RAW_FALSE) {
                        boolean rawExecute = inputLine.equals(".;");
                        if (rawExecute || inputLine.equals(":.")) {
                            if (rawMode == RAW_EMPTY) {
                                rawMode = RAW_FALSE;
                                throw new SqlToolError(
                                        rb.getString(SqltoolRB.RAW_EMPTY));
                            }
                            rawMode = RAW_FALSE;

                            setBuf(immCmdSB.toString());
                            immCmdSB.setLength(0);

                            if (rawExecute) {
                                historize();
                                processSQL();
                            } else if (interactive) {
                                stdprintln(rb.getString(
                                            SqltoolRB.RAW_MOVEDTOBUFFER));
                            }
                        } else {
                            if (rawMode == RAW_DATA) {
                                immCmdSB.append('\n');
                            }
                            rawMode = RAW_DATA;

                            if (inputLine.length() > 0) {
                                immCmdSB.append(inputLine);
                            }
                        }

                        continue;
                    }

                    if (immCmdSB.length() == 0) {
                    // NEW Immediate Command (i.e., not appending).
                        if (trimmedInput.startsWith("/*")) {
                            postCommentIndex = trimmedInput.indexOf("*/", 2)
                                               + 2;

                            if (postCommentIndex > 1) {
                                // I see no reason to leave comments in
                                // history.
                                inputLine = inputLine.substring(
                                    postCommentIndex + inputLine.length()
                                    - trimmedInput.length());
                                trimmedInput = inputLine.trim();
                            } else {
                                // Just so we get continuation lines:
                                immCmdSB.append("COMMENT");

                                inComment = true;

                                continue;
                            }
                        }

                        if (trimmedInput.length() == 0) {
                            // This is just to filter out useless newlines at
                            // beginning of commands.
                            continue;
                        }

                        if ((trimmedInput.charAt(0) == '*'
                                && (trimmedInput.length() < 2
                                    || trimmedInput.charAt(1) != '{'))
                                || trimmedInput.charAt(0) == '\\') {
                            setBuf(trimmedInput);
                            processFromBuffer();
                            continue;
                        }

                        if (trimmedInput.charAt(0) == ':' && interactive) {
                            processBuffHist(trimmedInput.substring(1));
                            continue;
                        }

                        String ucased = trimmedInput.toUpperCase();

                        if (ucased.startsWith("DECLARE")
                                || ucased.startsWith("BEGIN")) {
                            rawMode = RAW_EMPTY;

                            immCmdSB.append(inputLine);

                            if (interactive) {
                                stdprintln(RAW_LEADIN_MSG);
                            }

                            continue;
                        }
                    }

                    if (trimmedInput.length() == 0 && interactive &&!inComment) {
                        // Blank lines delimit commands ONLY IN INTERACTIVE
                        // MODE!
                        setBuf(immCmdSB.toString());
                        immCmdSB.setLength(0);
                        stdprintln(rb.getString(SqltoolRB.INPUT_MOVEDTOBUFFER));
                        continue;
                    }

                    deTerminated = SqlFile.deTerminated(inputLine);

                    // A null terminal line (i.e., /\s*;\s*$/) is never useful.
                    if (!trimmedInput.equals(";")) {
                        if (immCmdSB.length() > 0) {
                            immCmdSB.append('\n');
                        }

                        immCmdSB.append((deTerminated == null) ? inputLine
                                                                   : deTerminated);
                    }

                    if (deTerminated == null) {
                        continue;
                    }

                    // If we reach here, then immCmdSB contains a complete
                    // SQL command.

                    if (immCmdSB.toString().trim().length() == 0) {
                        immCmdSB.setLength(0);
                        throw new SqlToolError(rb.getString(
                                    SqltoolRB.SQLSTATEMENT_EMPTY));
                        // There is nothing inherently wrong with issuing
                        // an empty command, like to test DB server health.
                        // But, this check effectively catches many syntax
                        // errors early, and the DB check can be done by
                        // sending a comment like "// comment".
                    }

                    setBuf(immCmdSB.toString());
                    immCmdSB.setLength(0);
                    historize();
                    processSQL();
                } catch (BadSpecial bs) {
                    // BadSpecials ALWAYS have non-null getMessage().
                    errprintln(rb.getString(SqltoolRB.ERRORAT,
                            new String[] {
                                ((file == null) ? "stdin" : file.toString()),
                                Integer.toString(curLinenum),
                                inputLine,
                                bs.getMessage(),
                            }
                    ));
                    Throwable cause = bs.getCause();
                    if (cause != null) {
                        errprintln(rb.getString(SqltoolRB.CAUSEREPORT,
                                cause.toString()));

                    }

                    if (!continueOnError) {
                        throw new SqlToolError(bs);
                    }
                } catch (SQLException se) {
                    errprintln("SQL " + rb.getString(SqltoolRB.ERRORAT,
                            new String[] {
                                ((file == null) ? "stdin" : file.toString()),
                                Integer.toString(curLinenum),
                                lastSqlStatement,
                                se.getMessage(),
                            }));
                    // It's possible that we could have
                    // SQLException.getMessage() == null, but if so, I think
                    // it reasonsable to show "null".  That's a DB inadequacy.

                    if (!continueOnError) {
                        throw se;
                    }
                } catch (BreakException be) {
                    String msg = be.getMessage();

                    if (recursed) {
                        rollbackUncoms = false;
                        // Recursion level will exit by rethrowing the BE.
                        // We set rollbackUncoms to false because only the
                        // top level should detect break errors and
                        // possibly roll back.
                    } else if (msg == null || msg.equals("file")) {
                        break;
                    } else {
                        errprintln(rb.getString(SqltoolRB.BREAK_UNSATISFIED,
                                msg));
                    }

                    if (recursed ||!continueOnError) {
                        throw be;
                    }
                } catch (ContinueException ce) {
                    String msg = ce.getMessage();

                    if (recursed) {
                        rollbackUncoms = false;
                    } else {
                        errprintln(rb.getString(SqltoolRB.CONTINUE_UNSATISFIED,
                                msg));
                    }

                    if (recursed ||!continueOnError) {
                        throw ce;
                    }
                } catch (QuitNow qn) {
                    throw qn;
                } catch (SqlToolError ste) {
                    errprint(rb.getString(SqltoolRB.ERRORAT,
                            new String[] {
                                ((file == null) ? "stdin" : file.toString()),
                                Integer.toString(curLinenum),
                                inputLine,
                                ((ste.getMessage() == null)
                                        ? "" : ste.getMessage())
                            }
                    ));
                    if (ste.getMessage() != null) errprintln("");
                    Throwable cause = ste.getCause();
                    if (cause != null) {
                        errprintln(rb.getString(SqltoolRB.CAUSEREPORT,
                                cause.toString()));
                    }
                    if (!continueOnError) {
                        throw ste;
                    }
                }

                immCmdSB.setLength(0);
            }

            if (inComment || immCmdSB.length() != 0) {
                errprintln(rb.getString(SqltoolRB.INPUT_UNTERMINATED,
                        immCmdSB.toString()));
                throw new SqlToolError(rb.getString(
                        SqltoolRB.INPUT_UNTERMINATED, immCmdSB.toString()));
            }

            rollbackUncoms = false;
            // Exiting gracefully, so don't roll back.
        } catch (IOException ioe) {
            throw new SqlToolError(rb.getString(
                    SqltoolRB.PRIMARYINPUT_ACCESSFAIL), ioe);
        } catch (QuitNow qn) {
            if (recursed) {
                throw qn;
                // Will rollback if conditions otherwise require.
                // Otherwise top level will decide based upon qn.getMessage().
            }
            rollbackUncoms = (qn.getMessage() != null);

            if (rollbackUncoms) {
                errprintln(rb.getString(SqltoolRB.ABORTING, qn.getMessage()));
                throw new SqlToolError(qn.getMessage());
            }

            return;
        } finally {
            closeQueryOutputStream();

            if (fetchingVar != null) {
                errprintln(rb.getString(SqltoolRB.PLVAR_SET_INCOMPLETE,
                        fetchingVar));
                rollbackUncoms = true;
            }

            if (br != null) try {
                br.close();
            } catch (IOException ioe) {
                throw new SqlToolError(rb.getString(
                        SqltoolRB.INPUTREADER_CLOSEFAIL), ioe);
            }

            if (rollbackUncoms && possiblyUncommitteds.get()) {
                errprintln(rb.getString(SqltoolRB.ROLLINGBACK));
                curConn.rollback();
                possiblyUncommitteds.set(false);
            }
        }
    }

    /**
     * Returns a copy of given string without a terminating semicolon.
     * If there is no terminating semicolon, null is returned.
     *
     * @param inString Base String, which will not be modified (because
     *                 a "copy" will be returned).
     * @returns Null if inString contains no terminating semi-colon.
     */
    private static String deTerminated(String inString) {
        int index = inString.lastIndexOf(';');

        if (index < 0) {
            return null;
        }

        for (int i = index + 1; i < inString.length(); i++) {
            if (!Character.isWhitespace(inString.charAt(i))) {
                return null;
            }
        }

        return inString.substring(0, index);
    }

    /**
     * Utility nested Exception class for internal use only.
     *
     * Do not instantiate with null message.
     */
    static private class BadSpecial extends AppendableException {
        static final long serialVersionUID = 7162440064026570590L;

        BadSpecial(String s) {
            super(s);
            if (s == null)
                throw new RuntimeException(
                        "Must construct BadSpecials with non-null message");
        }
        BadSpecial(String s, Throwable t) {
            super(s, t);
            if (s == null)
                throw new RuntimeException(
                        "Must construct BadSpecials with non-null message");
        }
    }

    /**
     * Utility nested Exception class for internal use.
     * This must extend SqlToolError because it has to percolate up from
     * recursions of SqlTool.execute(), yet SqlTool.execute() is public.
     * Therefore, external users have no reason to specifically handle
     * QuitNow.
     */
    private class QuitNow extends SqlToolError {
        static final long serialVersionUID = 1811094258670900488L;

        public QuitNow(String s) {
            super(s);
        }

        public QuitNow() {
            super();
        }
    }

    /**
     * Utility nested Exception class for internal use.
     * This must extend SqlToolError because it has to percolate up from
     * recursions of SqlTool.execute(), yet SqlTool.execute() is public.
     * Therefore, external users have no reason to specifically handle
     * BreakException.
     */
    private class BreakException extends SqlToolError {
        static final long serialVersionUID = 351150072817675994L;

        public BreakException() {
            super();
        }

        public BreakException(String s) {
            super(s);
        }
    }

    /**
     * Utility nested Exception class for internal use.
     * This must extend SqlToolError because it has to percolate up from
     * recursions of SqlTool.execute(), yet SqlTool.execute() is public.
     * Therefore, external users have no reason to specifically handle
     * ContinueException.
     */
    private class ContinueException extends SqlToolError {
        static final long serialVersionUID = 5064604160827106014L;

        public ContinueException() {
            super();
        }

        public ContinueException(String s) {
            super(s);
        }
    }

    /**
     * Utility nested Exception class for internal use only.
     */
    private class BadSubst extends Exception {
        static final long serialVersionUID = 7325933736897253269L;

        BadSubst(String s) {
            super(s);
        }
    }

    /**
     * Utility nested Exception class for internal use only.
     */
    private class RowError extends AppendableException {
        static final long serialVersionUID = 754346434606022750L;

        RowError(String s) {
            super(s);
        }

        RowError(Throwable t) {
            this(null, t);
        }

        RowError(String s, Throwable t) {
            super(s, t);
        }
    }

    /**
     * Execute processSql/processPL/processSpecial from buffer.
     */
    public void processFromBuffer()
            throws BadSpecial, SQLException, SqlToolError {
        historize();
        if (buffer.charAt(0) == '*' && (buffer.length() < 2
                || buffer.charAt(1) != '{')) {
            // Test above just means commands starting with *, EXCEPT
            // for commands beginning with *{.
            processPL(buffer);
            return;
        }

        if (buffer.charAt(0) == '\\') {
            processSpecial(buffer);
            return;
        }
        processSQL();
    }

    /**
     * Process a Buffer/History Command.
     *
     * Due to the nature of the goal here, we don't trim() "other" like
     * we do for other kinds of commands.
     *
     * @param inString Complete command, less the leading ':' character.
     * @throws SQLException  thrown by JDBC driver.
     * @throws BadSpecial    special-command-specific errors.
     * @throws SqlToolError  all other errors.
     */
    private void processBuffHist(String inString)
    throws BadSpecial, SQLException, SqlToolError {
        if (inString.length() < 1) {
            throw new BadSpecial(rb.getString(SqltoolRB.BUFHIST_UNSPECIFIED));
        }

        // First handle the simple cases where user may not specify a
        // command number.
        char commandChar = inString.charAt(0);
        String other       = inString.substring(1);
        if (other.trim().length() == 0) {
            other = null;
        }
        switch (commandChar) {
            case 'l' :
            case 'b' :
                enforce1charBH(other, 'l');
                if (buffer == null) {
                    stdprintln(nobufferYetString);
                } else {
                    stdprintln(rb.getString(SqltoolRB.EDITBUFFER_CONTENTS,
                            buffer));
                }

                return;

            case 'h' :
                enforce1charBH(other, 'h');
                showHistory();

                return;

            case '?' :
                stdprintln(rb.getString(SqltoolRB.BUFFER_HELP));

                return;
        }

        Integer histNum = null;
        Matcher hm = slashHistoryPattern.matcher(inString);
        if (hm.matches()) {
            histNum = historySearch(hm.group(1));
            if (histNum == null) {
                stdprintln(rb.getString(SqltoolRB.SUBSTITUTION_NOMATCH));
                return;
            }
        } else {
            hm = historyPattern.matcher(inString);
            if (!hm.matches()) {
                throw new BadSpecial(rb.getString(SqltoolRB.EDIT_MALFORMAT));
                // Empirically, I find that his pattern always captures two
                // groups.  Unfortunately, there's no way to guarantee that :( .
            }
            histNum = ((hm.group(1) == null || hm.group(1).length() < 1)
                    ? null : new Integer(hm.group(1)));
        }
        if (hm.groupCount() != 2) {
            throw new BadSpecial(rb.getString(SqltoolRB.EDIT_MALFORMAT));
            // Empirically, I find that his pattern always captures two
            // groups.  Unfortunately, there's no way to guarantee that :( .
        }
        commandChar = ((hm.group(2) == null || hm.group(2).length() < 1)
                ? '\0' : hm.group(2).charAt(0));
        other = ((commandChar == '\0') ? null : hm.group(2).substring(1));
        if (other != null && other.length() < 1) other = null;
        String targetCommand = ((histNum == null)
                ? null : commandFromHistory(histNum.intValue()));
        // Every command below depends upon buffer content.

        switch (commandChar) {
            case '\0' :  // Special token set above.  Just history recall.
                setBuf(targetCommand);
                stdprintln(rb.getString(SqltoolRB.BUFFER_RESTORED, buffer));
                return;

            case ';' :
                enforce1charBH(other, ';');

                if (targetCommand != null) setBuf(targetCommand);
                if (buffer == null) throw new BadSpecial(
                        rb.getString(SqltoolRB.NOBUFFER_YET));
                stdprintln(rb.getString(SqltoolRB.BUFFER_EXECUTING, buffer));
                processFromBuffer();

                return;

            case 'a' :
                if (targetCommand == null) targetCommand = buffer;
                if (targetCommand == null) throw new BadSpecial(
                        rb.getString(SqltoolRB.NOBUFFER_YET));
                immCmdSB.append(targetCommand);

                if (other != null) {
                    String deTerminated = SqlFile.deTerminated(other);

                    if (!other.equals(";")) {
                        immCmdSB.append(((deTerminated == null)
                                ? other : deTerminated));
                    }

                    if (deTerminated != null) {
                        // If we reach here, then immCmdSB contains a
                        // complete command.

                        setBuf(immCmdSB.toString());
                        immCmdSB.setLength(0);
                        stdprintln(rb.getString(SqltoolRB.BUFFER_EXECUTING,
                                buffer));
                        processFromBuffer();

                        return;
                    }
                }

                magicPrefix = immCmdSB.toString();
                immCmdSB.setLength(0);
                if (interactive) stdprint(magicPrefix);

                return;

            case 'w' :
                if (targetCommand == null) targetCommand = buffer;
                if (targetCommand == null) throw new BadSpecial(
                        rb.getString(SqltoolRB.NOBUFFER_YET));
                if (other == null) {
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.DESTFILE_DEMAND));
                }
                String targetFile = dereference(other.trim(), false);
                // Dereference and trim the target file name
                // This is the only case where we dereference a : command.

                PrintWriter pw = null;
                try {
                    pw = new PrintWriter((charset == null)
                            ?  (new OutputStreamWriter(
                                    new FileOutputStream(targetFile, true)))
                            :  (new OutputStreamWriter(
                                    new FileOutputStream(targetFile, true),
                                            charset))
                            // Appendmode so can append to an SQL script.
                    );
                    // Replace with just "(new FileOutputStream(file), charset)"
                    // once use defaultCharset from Java 1.5 in charset init.
                    // above.

                    pw.print(targetCommand);
                    if (!targetCommand.matches("\\s*[*:\\\\].*")) pw.print(';');
                    pw.println();
                    pw.flush();
                } catch (Exception e) {
                    throw new BadSpecial(rb.getString(SqltoolRB.FILE_APPENDFAIL,
                            targetFile), e);
                } finally {
                    if (pw != null) pw.close();
                }

                return;

            case 's' :
                boolean modeExecute = false;
                boolean modeGlobal = false;
                if (targetCommand == null) targetCommand = buffer;
                if (targetCommand == null) throw new BadSpecial(
                        rb.getString(SqltoolRB.NOBUFFER_YET));

                try {
                    if (other == null || other.length() < 3) {
                        throw new BadSubst(rb.getString(
                                SqltoolRB.SUBSTITUTION_MALFORMAT));
                    }
                    Matcher m = substitutionPattern.matcher(other);
                    if (!m.matches()) {
                        throw new BadSubst(rb.getString(
                                SqltoolRB.SUBSTITUTION_MALFORMAT));
                    }

                    // Note that this pattern does not include the leading :.
                    if (m.groupCount() < 3 || m.groupCount() > 4) {
                        // Assertion failed
                        throw new RuntimeException(
                                "Matched substitution pattern, but "
                                + "captured " + m.groupCount() + " groups");
                    }
                    String optionGroup = (
                            (m.groupCount() > 3 && m.group(4) != null)
                            ? (new String(m.group(4))) : null);

                    if (optionGroup != null) {
                        if (optionGroup.indexOf(';') > -1) {
                            modeExecute = true;
                            optionGroup = optionGroup.replaceFirst(";", "");
                        }
                        if (optionGroup.indexOf('g') > -1) {
                            modeGlobal = true;
                            optionGroup = optionGroup.replaceFirst("g", "");
                        }
                    }

                    Matcher bufferMatcher = Pattern.compile("(?s"
                            + ((optionGroup == null) ? "" : optionGroup)
                            + ')' + m.group(2)).matcher(targetCommand);
                    String newBuffer = (modeGlobal
                            ? bufferMatcher.replaceAll(m.group(3))
                            : bufferMatcher.replaceFirst(m.group(3)));
                    if (newBuffer.equals(targetCommand)) {
                        stdprintln(rb.getString(
                                SqltoolRB.SUBSTITUTION_NOMATCH));
                        return;
                    }

                    setBuf(newBuffer);
                    stdprintln(rb.getString((modeExecute
                            ? SqltoolRB.BUFFER_EXECUTING
                            : SqltoolRB.EDITBUFFER_CONTENTS), buffer));
                } catch (PatternSyntaxException pse) {
                    throw new BadSpecial(
                            rb.getString(SqltoolRB.SUBSTITUTION_SYNTAX), pse);
                } catch (BadSubst badswitch) {
                    throw new BadSpecial(
                            rb.getString(SqltoolRB.SUBSTITUTION_SYNTAX));
                }

                if (modeExecute) {
                    immCmdSB.setLength(0);
                    processFromBuffer();
                }

                return;
        }

        throw new BadSpecial(rb.getString(SqltoolRB.BUFFER_UNKNOWN,
                Character.toString(commandChar)));
    }

    private boolean doPrepare   = false;
    private String  prepareVar  = null;
    private String  dsvColDelim = null;
    private String  dsvSkipPrefix = null;
    private String  dsvRowDelim = null;
    private String  dsvSkipCols = null;
    private String  DSV_X_SYNTAX_MSG = null;
    private String  DSV_M_SYNTAX_MSG = null;
    private String  nobufferYetString = null;

    private void enforce1charSpecial(String token, char command)
            throws BadSpecial {
        if (token.length() != 1) {
            throw new BadSpecial(rb.getString(SqltoolRB.SPECIAL_EXTRACHARS,
                     Character.toString(command), token.substring(1)));
        }
    }
    private void enforce1charBH(String token, char command)
            throws BadSpecial {
        if (token != null) {
            throw new BadSpecial(rb.getString(SqltoolRB.BUFFER_EXTRACHARS,
                    Character.toString(command), token));
        }
    }

    /**
     * Process a Special Command.
     *
     * @param inString TRIMMED complete command, including the leading
     *                 '\' character.
     * @throws SQLException thrown by JDBC driver.
     * @throws BadSpecial special-command-specific errors.
     * @throws SqlToolError all other errors, plus QuitNow,
     *                      BreakException, ContinueException.
     */
    private void processSpecial(String inString)
    throws BadSpecial, QuitNow, SQLException, SqlToolError {
        if (inString.equals("\\")) {
            throw new BadSpecial(rb.getString(SqltoolRB.SPECIAL_UNSPECIFIED));
        }
        Matcher m = specialPattern.matcher(
                plMode ? dereference(inString, false) : inString);
        if (!m.matches()) {
            throw new BadSpecial(rb.getString(SqltoolRB.SPECIAL_MALFORMAT));
            // I think it's impossible to get here, since the pattern is
            // so liberal.
        }
        if (m.groupCount() < 1 || m.groupCount() > 2) {
            // Failed assertion
            throw new RuntimeException(
                    "Pattern matched, yet captured " + m.groupCount()
                    + " groups");
        }

        String arg1 = m.group(1);
        String other = ((m.groupCount() > 1) ? m.group(2) : null);

        switch (arg1.charAt(0)) {
            case 'q' :
                enforce1charSpecial(arg1, 'q');
                if (other != null) {
                    throw new QuitNow(other);
                }

                throw new QuitNow();
            case 'H' :
                enforce1charSpecial(arg1, 'H');
                htmlMode = !htmlMode;

                stdprintln(rb.getString(SqltoolRB.HTML_MODE,
                        Boolean.toString(htmlMode)));

                return;

            case 'm' :
                if (arg1.equals("m?") ||
                        (arg1.equals("m") && other != null
                                 && other.equals("?"))) {
                    stdprintln(DSV_OPTIONS_TEXT + LS + DSV_M_SYNTAX_MSG);
                    return;
                }
                if (arg1.length() != 1 || other == null) {
                    throw new BadSpecial(DSV_M_SYNTAX_MSG);
                }
                boolean noComments = other.charAt(other.length() - 1) == '*';
                String skipPrefix = null;

                if (noComments) {
                    other = other.substring(0, other.length()-1).trim();
                    if (other.length() < 1) {
                        throw new BadSpecial(DSV_M_SYNTAX_MSG);
                    }
                } else {
                    skipPrefix = dsvSkipPrefix;
                }
                int colonIndex = other.indexOf(" :");
                if (colonIndex > -1 && colonIndex < other.length() - 2) {
                    skipPrefix = other.substring(colonIndex + 2);
                    other = other.substring(0, colonIndex).trim();
                }

                importDsv(other, skipPrefix);

                return;

            case 'x' :
                if (arg1.equals("x?") ||
                        (arg1.equals("x") && other != null
                                 && other.equals("?"))) {
                    stdprintln(DSV_OPTIONS_TEXT + LS + DSV_X_SYNTAX_MSG);
                    return;
                }
                try {
                    if (arg1.length() != 1 || other == null) {
                        throw new BadSpecial(DSV_X_SYNTAX_MSG);
                    }

                    String tableName = ((other.indexOf(' ') > 0) ? null
                                                                 : other);

                    if (dsvTargetFile == null && tableName == null) {
                        throw new BadSpecial(rb.getString(
                                    SqltoolRB.DSV_TARGETFILE_DEMAND));
                    }
                    File dsvFile = new File((dsvTargetFile == null)
                                            ? (tableName + ".dsv")
                                            : dsvTargetFile);

                    pwDsv = new PrintWriter((charset == null)
                       ? (new OutputStreamWriter(new FileOutputStream(dsvFile)))
                       : (new OutputStreamWriter(new FileOutputStream(dsvFile),
                               charset)));
                    // Replace with just "(new FileOutputStream(file), charset)"
                    // once use defaultCharset from Java 1.5 in charset init.
                    // above.

                    ResultSet rs = curConn.createStatement().executeQuery(
                            (tableName == null) ? other
                                                : ("SELECT * FROM "
                                                   + tableName));
                    List colList = new ArrayList();
                    int[] incCols = null;
                    if (dsvSkipCols != null) {
                        Set skipCols = new HashSet();
                        String[] skipColsArray = dsvSkipCols.split("\\s*\\Q"
                                + dsvColDelim + "\\E\\s*");
                        for (int i = 0; i < skipColsArray.length; i++) {
                            skipCols.add(skipColsArray[i].toLowerCase());
                        }
                        ResultSetMetaData rsmd = rs.getMetaData();
                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                            if (!skipCols.remove(rsmd.getColumnName(i)
                                    .toLowerCase())) {
                                colList.add(new Integer(i));
                            }
                        }
                        if (colList.size() < 1) {
                            throw new BadSpecial(rb.getString(
                                    SqltoolRB.DSV_NOCOLSLEFT, dsvSkipCols));
                        }
                        if (skipCols.size() > 0) {
                            throw new BadSpecial(rb.getString(
                                    SqltoolRB.DSV_SKIPCOLS_MISSING,
                                        skipCols.toString()));
                        }
                        incCols = new int[colList.size()];
                        for (int i = 0; i < incCols.length; i++) {
                            incCols[i] = ((Integer) colList.get(i)).intValue();
                        }
                    }
                    displayResultSet(null, rs, incCols, null);
                    pwDsv.flush();
                    stdprintln(rb.getString(SqltoolRB.FILE_WROTECHARS,
                            Long.toString(dsvFile.length()),
                            dsvFile.toString()));
                } catch (FileNotFoundException e) {
                    throw new BadSpecial(rb.getString(SqltoolRB.FILE_WRITEFAIL,
                            other), e);
                } catch (UnsupportedEncodingException e) {
                    throw new BadSpecial(rb.getString(SqltoolRB.FILE_WRITEFAIL,
                            other), e);
                } finally {
                    // Reset all state changes
                    if (pwDsv != null) {
                        pwDsv.close();
                    }

                    pwDsv       = null;
                }

                return;

            case 'd' :
                if (arg1.equals("d?") ||
                        (arg1.equals("d") && other != null
                                 && other.equals("?"))) {
                    stdprintln(D_OPTIONS_TEXT);
                    return;
                }
                if (arg1.length() == 2) {
                    listTables(arg1.charAt(1), other);

                    return;
                }

                if (arg1.length() == 1 && other != null) try {
                    int space = other.indexOf(' ');

                    if (space < 0) {
                        describe(other, null);
                    } else {
                        describe(other.substring(0, space),
                                 other.substring(space + 1).trim());
                    }

                    return;
                } catch (SQLException se) {
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.METADATA_FETCH_FAIL), se);
                }

                throw new BadSpecial(rb.getString(SqltoolRB.SPECIAL_D_LIKE));
            case 'o' :
                enforce1charSpecial(arg1, 'o');
                if (other == null) {
                    if (pwQuery == null) {
                        throw new BadSpecial(rb.getString(
                                SqltoolRB.OUTPUTFILE_NONETOCLOSE));
                    }

                    closeQueryOutputStream();

                    return;
                }

                if (pwQuery != null) {
                    stdprintln(rb.getString(SqltoolRB.OUTPUTFILE_REOPENING));
                    closeQueryOutputStream();
                }

                try {
                    pwQuery = new PrintWriter((charset == null)
                            ? (new OutputStreamWriter(
                                    new FileOutputStream(other, true)))
                            : (new OutputStreamWriter(
                                    new FileOutputStream(other, true), charset))
                    );
                    // Replace with just "(new FileOutputStream(file), charset)"
                    // once use defaultCharset from Java 1.5 in charset init.
                    // above.

                    /* Opening in append mode, so it's possible that we will
                     * be adding superfluous <HTML> and <BODY> tags.
                     * I think that browsers can handle that */
                    pwQuery.println((htmlMode
                            ? ("<HTML>" + LS + "<!--")
                            : "#") + " " + (new java.util.Date()) + ".  "
                                    + rb.getString(SqltoolRB.OUTPUTFILE_HEADER,
                                            getClass().getName())
                                    + (htmlMode ? (" -->" + LS + LS + "<BODY>")
                                                : LS));
                    pwQuery.flush();
                } catch (Exception e) {
                    throw new BadSpecial(rb.getString(SqltoolRB.FILE_WRITEFAIL,
                            other), e);
                }

                return;

            case 'i' :
                enforce1charSpecial(arg1, 'i');
                if (other == null) {
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.SQLFILE_NAME_DEMAND));
                }

                try {
                    SqlFile sf = new SqlFile(new File(other), false, userVars);

                    sf.recursed = true;

                    // Share the possiblyUncommitted state
                    sf.possiblyUncommitteds = possiblyUncommitteds;
                    sf.plMode               = plMode;

                    sf.execute(curConn, continueOnError);
                } catch (ContinueException ce) {
                    throw ce;
                } catch (BreakException be) {
                    String beMessage = be.getMessage();

                    // Handle "file" and plain breaks (by doing nothing)
                    if (beMessage != null &&!beMessage.equals("file")) {
                        throw be;
                    }
                } catch (QuitNow qn) {
                    throw qn;
                } catch (Exception e) {
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.SQLFILE_EXECUTE_FAIL, other), e);
                }

                return;

            case 'p' :
                enforce1charSpecial(arg1, 'p');
                if (other == null) {
                    stdprintln(true);
                } else {
                    stdprintln(other, true);
                }

                return;

            case 'a' :
                enforce1charSpecial(arg1, 'a');
                if (other != null) {
                    curConn.setAutoCommit(
                        Boolean.valueOf(other).booleanValue());
                }

                stdprintln(rb.getString(SqltoolRB.A_SETTING,
                        Boolean.toString(curConn.getAutoCommit())));

                return;
            case '=' :
                enforce1charSpecial(arg1, '=');
                curConn.commit();
                possiblyUncommitteds.set(false);
                stdprintln(rb.getString(SqltoolRB.COMMITTED));

                return;

            case 'b' :
                if (arg1.length() == 1) {
                    if (other != null) {
                        throw new BadSpecial(rb.getString(
                                SqltoolRB.SPECIAL_B_MALFORMAT));
                    }
                    fetchBinary = true;

                    return;
                }

                if (arg1.charAt(1) == 'p') {
                    if (other != null) {
                        throw new BadSpecial(rb.getString(
                                SqltoolRB.SPECIAL_B_MALFORMAT));
                    }
                    doPrepare = true;

                    return;
                }

                if ((arg1.charAt(1) != 'd' && arg1.charAt(1) != 'l')
                        || other == null) {
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.SPECIAL_B_MALFORMAT));
                }

                File file = new File(other);

                try {
                    if (arg1.charAt(1) == 'd') {
                        dump(file);
                    } else {
                        binBuffer = SqlFile.loadBinary(file);
                        stdprintln(rb.getString(
                                SqltoolRB.BINARY_LOADEDBYTESINTO,
                                        binBuffer.length));
                    }
                } catch (BadSpecial bs) {
                    throw bs;
                } catch (IOException ioe) {
                    throw new BadSpecial(rb.getString(SqltoolRB.BINARY_FILEFAIL,
                            other), ioe);
                }

                return;

            case '*' :
            case 'c' :
                enforce1charSpecial(arg1, '=');
                if (other != null) {
                    // But remember that we have to abort on some I/O errors.
                    continueOnError = Boolean.valueOf(other).booleanValue();
                }

                stdprintln(rb.getString(SqltoolRB.C_SETTING,
                        Boolean.toString(continueOnError)));

                return;

            case '?' :
                stdprintln(rb.getString(SqltoolRB.SPECIAL_HELP));

                return;

            case '!' :
                // N.b. This DOES NOT HANDLE UNIX shell wildcards, since there
                // is no UNIX shell involved.
                // Doesn't make sense to incur overhead of a shell without
                // stdin capability.
                // Can't provide stdin to the executed program because
                // the forked program could gobble up program input,
                // depending on how SqlTool was invoked, nested scripts,
                // etc.

                // I'd like to execute the user's default shell if they
                // ran "\!" with no argument, but (a) there is no portable
                // way to determine the user's default or login shell; and
                // (b) shell is useless without stdin ability.
                InputStream stream;
                byte[]      ba         = new byte[1024];
                String      extCommand = ((arg1.length() == 1)
                        ? "" : arg1.substring(1))
                    + ((arg1.length() > 1 && other != null)
                       ? " " : "") + ((other == null) ? "" : other);
                if (extCommand.trim().length() < 1)
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.BANG_INCOMPLETE));

                try {
                    Runtime runtime = Runtime.getRuntime();
                    Process proc = ((wincmdPattern == null)
                            ? runtime.exec(extCommand)
                            : runtime.exec(genWinArgs(extCommand))
                    );

                    proc.getOutputStream().close();

                    int i;

                    stream = proc.getInputStream();

                    while ((i = stream.read(ba)) > 0) {
                        stdprint(new String(ba, 0, i));
                    }

                    stream.close();

                    stream = proc.getErrorStream();

                    while ((i = stream.read(ba)) > 0) {
                        errprint(new String(ba, 0, i));
                    }

                    stream.close();

                    if (proc.waitFor() != 0) {
                        throw new BadSpecial(rb.getString(
                                SqltoolRB.BANG_COMMAND_FAIL, extCommand));
                    }
                } catch (BadSpecial bs) {
                    throw bs;
                } catch (Exception e) {
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.BANG_COMMAND_FAIL, extCommand), e);
                }

                return;

            case '.' :
                enforce1charSpecial(arg1, '.');
                rawMode = RAW_EMPTY;

                if (interactive) {
                    stdprintln(RAW_LEADIN_MSG);
                }

                return;
        }

        throw new BadSpecial(rb.getString(SqltoolRB.SPECIAL_UNKNOWN,
                Character.toString(arg1.charAt(0))));
    }

    private static final char[] nonVarChars = {
        ' ', '\t', '=', '}', '\n', '\r'
    };

    /**
     * Returns index specifying 1 past end of a variable name.
     *
     * @param inString String containing a variable name
     * @param startIndex Index within inString where the variable name begins
     * @returns Index within inString, 1 past end of the variable name
     */
    static int pastName(String inString, int startIndex) {
        String workString = inString.substring(startIndex);
        int    e          = inString.length();    // Index 1 past end of var name.
        int    nonVarIndex;

        for (int i = 0; i < nonVarChars.length; i++) {
            nonVarIndex = workString.indexOf(nonVarChars[i]);

            if (nonVarIndex > -1 && nonVarIndex < e) {
                e = nonVarIndex;
            }
        }

        return startIndex + e;
    }

    /**
     * Deference *{} PL variables and ${} System Property variables.
     *
     * @throws SqlToolError
     */
    private String dereference(String inString,
                               boolean permitAlias) throws SqlToolError {
        /* TODO:  Rewrite using java.util.regex. */
        String       varName, varValue;
        StringBuffer expandBuffer = new StringBuffer(inString);
        int          b, e;    // begin and end of name.  end really 1 PAST name
        int iterations;

        if (permitAlias && inString.trim().charAt(0) == '/') {
            int slashIndex = inString.indexOf('/');

            e = SqlFile.pastName(inString.substring(slashIndex + 1), 0);

            // In this case, e is the exact length of the var name.
            if (e < 1) {
                throw new SqlToolError(rb.getString(
                        SqltoolRB.PLALIAS_MALFORMAT));
            }

            varName  = inString.substring(slashIndex + 1, slashIndex + 1 + e);
            varValue = (String) userVars.get(varName);

            if (varValue == null) {
                throw new SqlToolError(rb.getString(
                        SqltoolRB.PLVAR_UNDEFINED, varName));
            }

            expandBuffer.replace(slashIndex, slashIndex + 1 + e,
                                 (String) userVars.get(varName));
        }

        String s;
        boolean permitUnset;
        // Permit unset with:     ${:varname}
        // Prohibit unset with :  ${varnam}

        iterations = 0;
        while (true) {
            s = expandBuffer.toString();
            b = s.indexOf("${");

            if (b < 0) {
                // No more unexpanded variable uses
                break;
            }

            e = s.indexOf('}', b + 2);

            if (e == b + 2) {
                throw new SqlToolError(rb.getString(SqltoolRB.SYSPROP_EMPTY));
            }

            if (e < 0) {
                throw new SqlToolError(rb.getString(
                            SqltoolRB.SYSPROP_UNTERMINATED));
            }

            permitUnset = (s.charAt(b + 2) == ':');

            varName = s.substring(b + (permitUnset ? 3 : 2), e);
            if (iterations++ > 10000)
                throw new SqlToolError(rb.getString(SqltoolRB.VAR_INFINITE,
                        varName));

            varValue = System.getProperty(varName);
            if (varValue == null) {
                if (permitUnset) {
                    varValue = "";
                } else {
                    throw new SqlToolError(rb.getString(
                            SqltoolRB.SYSPROP_UNDEFINED, varName));
                }
            }

            expandBuffer.replace(b, e + 1, varValue);
        }

        iterations = 0;
        while (true) {
            s = expandBuffer.toString();
            b = s.indexOf("*{");

            if (b < 0) {
                // No more unexpanded variable uses
                break;
            }

            e = s.indexOf('}', b + 2);

            if (e == b + 2) {
                throw new SqlToolError(rb.getString(SqltoolRB.PLVAR_NAMEEMPTY));
            }

            if (e < 0) {
                throw new SqlToolError(rb.getString(
                            SqltoolRB.PLVAR_UNTERMINATED));
            }

            permitUnset = (s.charAt(b + 2) == ':');

            varName = s.substring(b + (permitUnset ? 3 : 2), e);
            if (iterations++ > 10000)
                throw new SqlToolError(rb.getString(SqltoolRB.VAR_INFINITE,
                        varName));
            // TODO:  Use a smarter algorithm to handle (or prohibit)
            // recursion without this clumsy detection tactic.

            varValue = (String) userVars.get(varName);
            if (varValue == null) {
                if (permitUnset) {
                    varValue = "";
                } else {
                    throw new SqlToolError(rb.getString(
                            SqltoolRB.PLVAR_UNDEFINED, varName));
                }
            }

            expandBuffer.replace(b, e + 1, varValue);
        }

        return expandBuffer.toString();
    }

    public boolean plMode = false;

    //  PL variable name currently awaiting query output.
    private String  fetchingVar = null;
    private boolean silentFetch = false;
    private boolean fetchBinary = false;

    /**
     * Process a Process Language Command.
     * Nesting not supported yet.
     *
     * @param inString Complete command, including the leading '\' character.
     * @throws BadSpecial special-command-specific errors.
     * @throws SqlToolError all other errors, plus BreakException and
     *                      ContinueException.
     */
    private void processPL(String inString) throws BadSpecial, SqlToolError {
        Matcher m = plPattern.matcher(dereference(inString, false));
        if (!m.matches()) {
            throw new BadSpecial(rb.getString(SqltoolRB.PL_MALFORMAT));
            // I think it's impossible to get here, since the pattern is
            // so liberal.
        }
        if (m.groupCount() < 1 || m.group(1) == null) {
            plMode = true;
            stdprintln(rb.getString(SqltoolRB.PL_EXPANSIONMODE, "on"));
            return;
        }

        String[] tokens = m.group(1).split("\\s+");

        if (tokens[0].charAt(0) == '?') {
            stdprintln(rb.getString(SqltoolRB.PL_HELP));

            return;
        }

        // If user runs any PL command, we turn PL mode on.
        plMode = true;

        if (tokens[0].equals("end")) {
            throw new BadSpecial(rb.getString(SqltoolRB.END_NOBLOCK));
        }

        if (tokens[0].equals("continue")) {
            if (tokens.length > 1) {
                if (tokens.length == 2 &&
                        (tokens[1].equals("foreach") ||
                         tokens[1].equals("while"))) {
                    throw new ContinueException(tokens[1]);
                }
                throw new BadSpecial(rb.getString(SqltoolRB.CONTINUE_SYNTAX));
            }

            throw new ContinueException();
        }

        if (tokens[0].equals("break")) {
            if (tokens.length > 1) {
                if (tokens.length == 2 &&
                        (tokens[1].equals("foreach") ||
                         tokens[1].equals("if") ||
                         tokens[1].equals("while") ||
                         tokens[1].equals("file"))) {
                    throw new BreakException(tokens[1]);
                }
                throw new BadSpecial(rb.getString(SqltoolRB.BREAK_SYNTAX));
            }

            throw new BreakException();
        }

        if (tokens[0].equals("list") || tokens[0].equals("listvalues")
                || tokens[0].equals("listsysprops")) {
            boolean sysProps =tokens[0].equals("listsysprops");
            String  s;
            boolean doValues = (tokens[0].equals("listvalues") || sysProps);
            // Always list System Property values.
            // They are unlikely to be very long, like PL variables may be.

            if (tokens.length == 1) {
                stdprint(formatNicely(
                        (sysProps ? System.getProperties() : userVars),
                        doValues));
            } else {
                if (doValues) {
                    stdprintln(rb.getString(SqltoolRB.PL_LIST_PARENS));
                } else {
                    stdprintln(rb.getString(SqltoolRB.PL_LIST_LENGTHS));
                }

                for (int i = 1; i < tokens.length; i++) {
                    s = (String) (sysProps ? System.getProperties() : userVars).
                            get(tokens[i]);
                    if (s == null) continue;
                    stdprintln("    " + tokens[i] + ": "
                               + (doValues ? ("(" + s + ')')
                                           : Integer.toString(s.length())));
                }
            }

            return;
        }

        if (tokens[0].equals("dump") || tokens[0].equals("load")) {
            if (tokens.length != 3) {
                throw new BadSpecial(rb.getString(
                        SqltoolRB.DUMPLOAD_MALFORMAT));
            }

            String varName = tokens[1];

            if (varName.indexOf(':') > -1) {
                throw new BadSpecial(rb.getString(SqltoolRB.PLVAR_NOCOLON));
            }
            File   file    = new File(tokens[2]);

            try {
                if (tokens[0].equals("dump")) {
                    dump(varName, file);
                } else {
                    load(varName, file, charset);
                }
            } catch (IOException ioe) {
                throw new BadSpecial(rb.getString(SqltoolRB.DUMPLOAD_FAIL,
                        varName, file.toString()), ioe);
            }

            return;
        }

        if (tokens[0].equals("prepare")) {
            if (tokens.length != 2) {
                throw new BadSpecial(rb.getString(SqltoolRB.PREPARE_MALFORMAT));
            }

            if (userVars.get(tokens[1]) == null) {
                throw new BadSpecial(rb.getString(
                    SqltoolRB.PLVAR_UNDEFINED, tokens[1]));
            }

            prepareVar = tokens[1];
            doPrepare  = true;

            return;
        }

        if (tokens[0].equals("foreach")) {
            Matcher foreachM= foreachPattern.matcher(
                    dereference(inString, false));
            if (!foreachM.matches()) {
                throw new BadSpecial(rb.getString(SqltoolRB.FOREACH_MALFORMAT));
            }
            if (foreachM.groupCount() != 2) {
                throw new RuntimeException(
                        "foreach pattern matched, but captured "
                        + foreachM.groupCount() + " groups");
            }

            String varName   = foreachM.group(1);
            if (varName.indexOf(':') > -1) {
                throw new BadSpecial(rb.getString(SqltoolRB.PLVAR_NOCOLON));
            }
            String[] values = foreachM.group(2).split("\\s+");
            File   tmpFile = null;
            String varVal;

            try {
                tmpFile = plBlockFile("foreach");
            } catch (IOException ioe) {
                throw new BadSpecial(rb.getString(SqltoolRB.PL_TEMPFILE_FAIL),
                    ioe);
            }

            String origval = (String) userVars.get(varName);


            try {
                SqlFile sf;

                for (int i = 0; i < values.length; i++) {
                    try {
                        varVal = values[i];

                        userVars.put(varName, varVal);
                        updateUserSettings();

                        sf          = new SqlFile(tmpFile, false, userVars);
                        sf.plMode   = true;
                        sf.recursed = true;

                        // Share the possiblyUncommitted state
                        sf.possiblyUncommitteds = possiblyUncommitteds;

                        sf.execute(curConn, continueOnError);
                    } catch (ContinueException ce) {
                        String ceMessage = ce.getMessage();

                        if (ceMessage != null
                                &&!ceMessage.equals("foreach")) {
                            throw ce;
                        }
                    }
                }
            } catch (BreakException be) {
                String beMessage = be.getMessage();

                // Handle "foreach" and plain breaks (by doing nothing)
                if (beMessage != null &&!beMessage.equals("foreach")) {
                    throw be;
                }
            } catch (QuitNow qn) {
                throw qn;
            } catch (Exception e) {
                throw new BadSpecial(rb.getString(SqltoolRB.PL_BLOCK_FAIL), e);
            }

            if (origval == null) {
                userVars.remove(varName);
                updateUserSettings();
            } else {
                userVars.put(varName, origval);
            }

            if (tmpFile != null &&!tmpFile.delete()) {
                throw new BadSpecial(rb.getString(
                        SqltoolRB.TEMPFILE_REMOVAL_FAIL, tmpFile.toString()));
            }

            return;
        }

        if (tokens[0].equals("if") || tokens[0].equals("while")) {
            Matcher ifwhileM= ifwhilePattern.matcher(
                    dereference(inString, false));
            if (!ifwhileM.matches()) {
                throw new BadSpecial(rb.getString(SqltoolRB.IFWHILE_MALFORMAT));
            }
            if (ifwhileM.groupCount() != 1) {
                throw new RuntimeException(
                        "if/while pattern matched, but captured "
                        + ifwhileM.groupCount() + " groups");
            }

            String[] values =
                    ifwhileM.group(1).replaceAll("!([a-zA-Z0-9*])", "! $1").
                            replaceAll("([a-zA-Z0-9*])!", "$1 !").split("\\s+");
            File tmpFile = null;

            if (tokens[0].equals("if")) {
                try {
                    tmpFile = plBlockFile("if");
                } catch (IOException ioe) {
                    throw new BadSpecial(rb.getString(SqltoolRB.PL_BLOCK_FAIL),
                            ioe);
                }

                try {
                    if (eval(values)) {
                        SqlFile sf = new SqlFile(tmpFile, false, userVars);

                        sf.plMode   = true;
                        sf.recursed = true;

                        // Share the possiblyUncommitted state
                        sf.possiblyUncommitteds = possiblyUncommitteds;

                        sf.execute(curConn, continueOnError);
                    }
                } catch (BreakException be) {
                    String beMessage = be.getMessage();

                    // Handle "if" and plain breaks (by doing nothing)
                    if (beMessage == null ||!beMessage.equals("if")) {
                        throw be;
                    }
                } catch (ContinueException ce) {
                    throw ce;
                } catch (QuitNow qn) {
                    throw qn;
                } catch (BadSpecial bs) {
                    bs.appendMessage(rb.getString(SqltoolRB.IF_MALFORMAT));
                    throw bs;
                } catch (Exception e) {
                    throw new BadSpecial(
                        rb.getString(SqltoolRB.PL_BLOCK_FAIL), e);
                }
            } else if (tokens[0].equals("while")) {
                try {
                    tmpFile = plBlockFile("while");
                } catch (IOException ioe) {
                    throw new BadSpecial(
                        rb.getString(SqltoolRB.PL_TEMPFILE_FAIL), ioe);
                }

                try {
                    SqlFile sf;

                    while (eval(values)) {
                        try {
                            sf          = new SqlFile(tmpFile, false, userVars);
                            sf.recursed = true;

                            // Share the possiblyUncommitted state
                            sf.possiblyUncommitteds = possiblyUncommitteds;
                            sf.plMode               = true;

                            sf.execute(curConn, continueOnError);
                        } catch (ContinueException ce) {
                            String ceMessage = ce.getMessage();

                            if (ceMessage != null &&!ceMessage.equals("while")) {
                                throw ce;
                            }
                        }
                    }
                } catch (BreakException be) {
                    String beMessage = be.getMessage();

                    // Handle "while" and plain breaks (by doing nothing)
                    if (beMessage != null &&!beMessage.equals("while")) {
                        throw be;
                    }
                } catch (QuitNow qn) {
                    throw qn;
                } catch (BadSpecial bs) {
                    bs.appendMessage(rb.getString(SqltoolRB.WHILE_MALFORMAT));
                    throw bs;
                } catch (Exception e) {
                    throw new BadSpecial(rb.getString(SqltoolRB.PL_BLOCK_FAIL),
                            e);
                }
            } else {
                // Assertion
                throw new RuntimeException(rb.getString(SqltoolRB.PL_UNKNOWN,
                        tokens[0]));
            }

            if (tmpFile != null &&!tmpFile.delete()) {
                throw new BadSpecial(rb.getString(
                        SqltoolRB.TEMPFILE_REMOVAL_FAIL, tmpFile.toString()));
            }

            return;
        }

        m = varsetPattern.matcher(dereference(inString, false));
        if (!m.matches()) {
            throw new BadSpecial(rb.getString(SqltoolRB.PL_UNKNOWN, tokens[0]));
        }
        if (m.groupCount() < 2 || m.groupCount() > 3) {
            // Assertion
            throw new RuntimeException("varset pattern matched but captured "
                    + m.groupCount() + " groups");
        }

        String varName  = m.group(1);

        if (varName.indexOf(':') > -1) {
            throw new BadSpecial(rb.getString(SqltoolRB.PLVAR_NOCOLON));
        }

        switch (m.group(2).charAt(0)) {
            case '_' :
                silentFetch = true;
            case '~' :
                if (m.groupCount() > 2 && m.group(3) != null) {
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.PLVAR_TILDEDASH_NOMOREARGS, m.group(3)));
                }

                userVars.remove(varName);
                updateUserSettings();

                fetchingVar = varName;

                return;

            case '=' :
                if (fetchingVar != null && fetchingVar.equals(varName)) {
                    fetchingVar = null;
                }

                if (m.groupCount() > 2 && m.group(3) != null) {
                    userVars.put(varName, m.group(3));
                } else {
                    userVars.remove(varName);
                }
                updateUserSettings();

                return;
        }

        throw new BadSpecial(rb.getString(SqltoolRB.PL_UNKNOWN, tokens[0]));
        // I think this would already be caught in the setvar block above.
    }

    /*
     * Read a PL block into a new temp file.
     *
     * WARNING!!! foreach blocks are not yet smart about comments
     * and strings.  We just look for a line beginning with a PL "end"
     * command without worrying about comments or quotes (for now).
     *
     * WARNING!!! This is very rudimentary.
     * Users give up all editing and feedback capabilities while
     * in the foreach loop.
     * A better solution would be to pass current input stream to a
     * new SqlFile.execute() with a mode whereby commands are written
     * to a separate history but not executed.
     *
     * @throws IOException
     * @throws SqlToolError
     */
    private File plBlockFile(String seeking) throws IOException, SqlToolError {
        String          s;

        // Have already read the if/while/foreach statement, so we are already
        // at nest level 1.  When we reach nestlevel 1 (read 1 net "end"
        // statement), we're at level 0 and return.
        int    nestlevel = 1;
        String curPlCommand;

        if (seeking == null
                || ((!seeking.equals("foreach")) && (!seeking.equals("if"))
                    && (!seeking.equals("while")))) {
            throw new RuntimeException(
                "Assertion failed.  Unsupported PL block type:  " + seeking);
        }

        File tmpFile = File.createTempFile("sqltool-", ".sql");
        PrintWriter pw = new PrintWriter((charset == null)
                ?  (new OutputStreamWriter(new FileOutputStream(tmpFile)))
                :  (new OutputStreamWriter(new FileOutputStream(tmpFile),
                        charset)));
        // Replace with just "(new FileOutputStream(file), charset)"
        // once use defaultCharset from Java 1.5 in charset init. above.

        try {
        pw.println("/* " + (new java.util.Date()) + ". "
                   + getClass().getName() + " PL block. */");
        pw.println();
        Matcher m;

        while (true) {
            s = br.readLine();

            if (s == null) {
                s = rb.getString(SqltoolRB.PL_BLOCK_UNTERMINATED, seeking);
                errprintln(s);
                throw new SqlToolError(s);
            }

            curLinenum++;

            m = plPattern.matcher(s);
            if (m.matches() && m.groupCount() > 0 && m.group(1) != null) {
                String[] tokens = m.group(1).split("\\s+");
                curPlCommand = tokens[0];

                // PL COMMAND of some sort.
                if (curPlCommand.equals(seeking)) {
                    nestlevel++;
                } else if (curPlCommand.equals("end")) {
                    if (tokens.length < 2) {
                        s = rb.getString(SqltoolRB.END_SYNTAX, "1");
                        errprintln(s);
                        throw new SqlToolError(s);
                    }

                    String inType = tokens[1];

                    if (inType.equals(seeking)) {
                        nestlevel--;

                        if (nestlevel < 1) {
                            break;
                        }
                    }

                    if ((!inType.equals("foreach")) && (!inType.equals("if"))
                            && (!inType.equals("while"))) {
                        s = rb.getString(SqltoolRB.END_SYNTAX, "2");
                        errprintln(s);
                        throw new SqlToolError(s);
                    }
                }
            }

            pw.println(s);
        }

        pw.flush();
        } finally {
            pw.close();
        }

        return tmpFile;
    }

    /**
     * Wrapper methods so don't need to call x(..., false) in most cases.
     */
    /* Unused.  Enable when/if need.
    private void stdprintln() {
        stdprintln(false);
    }
    */

    private void stdprint(String s) {
        stdprint(s, false);
    }

    private void stdprintln(String s) {
        stdprintln(s, false);
    }

    /**
     * Encapsulates normal output.
     *
     * Conditionally HTML-ifies output.
     */
    private void stdprintln(boolean queryOutput) {
        if (htmlMode) {
            psStd.println("<BR>");
        } else {
            psStd.println();
        }

        if (queryOutput && pwQuery != null) {
            if (htmlMode) {
                pwQuery.println("<BR>");
            } else {
                pwQuery.println();
            }

            pwQuery.flush();
        }
    }

    /**
     * Encapsulates error output.
     *
     * Conditionally HTML-ifies error output.
     */
    private void errprint(String s) {
        psErr.print(htmlMode
                    ? ("<DIV style='color:white; background: red; "
                       + "font-weight: bold'>" + s + "</DIV>")
                    : s);
    }

    /**
     * Encapsulates error output.
     *
     * Conditionally HTML-ifies error output.
     */
    private void errprintln(String s) {
        psErr.println(htmlMode
                      ? ("<DIV style='color:white; background: red; "
                         + "font-weight: bold'>" + s + "</DIV>")
                      : s);
    }

    /**
     * Encapsulates normal output.
     *
     * Conditionally HTML-ifies output.
     */
    private void stdprint(String s, boolean queryOutput) {
        psStd.print(htmlMode ? ("<P>" + s + "</P>")
                             : s);

        if (queryOutput && pwQuery != null) {
            pwQuery.print(htmlMode ? ("<P>" + s + "</P>")
                                   : s);
            pwQuery.flush();
        }
    }

    /**
     * Encapsulates normal output.
     *
     * Conditionally HTML-ifies output.
     */
    private void stdprintln(String s, boolean queryOutput) {
        psStd.println(htmlMode ? ("<P>" + s + "</P>")
                               : s);

        if (queryOutput && pwQuery != null) {
            pwQuery.println(htmlMode ? ("<P>" + s + "</P>")
                                     : s);
            pwQuery.flush();
        }
    }

    // Just because users may be used to seeing "[null]" in normal
    // SqlFile output, we use the same default value for null in DSV
    // files, but this DSV null representation can be changed to anything.
    private static final String DEFAULT_NULL_REP = "[null]";
    private static final String DEFAULT_ROW_DELIM = LS;
    private static final String DEFAULT_COL_DELIM = "|";
    private static final String DEFAULT_SKIP_PREFIX = "#";
    private static final int    DEFAULT_ELEMENT   = 0,
                                HSQLDB_ELEMENT    = 1,
                                ORACLE_ELEMENT    = 2
    ;

    // These do not specify order listed, just inclusion.
    private static final int[] listMDSchemaCols = { 1 };
    private static final int[] listMDIndexCols  = {
        2, 6, 3, 9, 4, 10, 11
    };

    /** Column numbering starting at 1. */
    private static final int[][] listMDTableCols = {
        {
            2, 3
        },    // Default
        {
            2, 3
        },    // HSQLDB
        {
            2, 3
        },    // Oracle
    };

    /**
     * SYS and SYSTEM are the only base system accounts in Oracle, however,
     * from an empirical perspective, all of these other accounts are
     * system accounts because <UL>
     * <LI> they are hidden from the casual user
     * <LI> they are created by the installer at installation-time
     * <LI> they are used automatically by the Oracle engine when the
     *      specific Oracle sub-product is used
     * <LI> the accounts should not be <I>messed with</I> by database users
     * <LI> the accounts should certainly not be used if the specific
     *      Oracle sub-product is going to be used.
     * </UL>
     *
     * General advice:  If you aren't going to use an Oracle sub-product,
     * then <B>don't install it!</B>
     * Don't blindly accept default when running OUI.
     *
     * If users also see accounts that they didn't create with names like
     * SCOTT, ADAMS, JONES, CLARK, BLAKE, OE, PM, SH, QS, QS_*, these
     * contain sample data and the schemas can safely be removed.
     */
    private static final String[] oracleSysSchemas = {
        "SYS", "SYSTEM", "OUTLN", "DBSNMP", "OUTLN", "MDSYS", "ORDSYS",
        "ORDPLUGINS", "CTXSYS", "DSSYS", "PERFSTAT", "WKPROXY", "WKSYS",
        "WMSYS", "XDB", "ANONYMOUS", "ODM", "ODM_MTR", "OLAPSYS", "TRACESVR",
        "REPADMIN"
    };

    /**
     * Lists available database tables.
     *
     * When a filter is given, we assume that there are no lower-case
     * characters in the object names (which would require "quotes" when
     * creating them).
     *
     * @throws BadSpecial usually wrap a cause (which cause is a
     *                    SQLException in some cases).
     * @throws SqlToolError passed through from other methods in this class.
     */
    private void listTables(char c, String inFilter) throws BadSpecial,
            SqlToolError {
        String   schema  = null;
        int[]    listSet = null;
        String[] types   = null;

        /** For workaround for \T for Oracle */
        String[] additionalSchemas = null;

        /** This is for specific non-getTable() queries */
        Statement statement = null;
        ResultSet rs        = null;
        String    narrower  = "";
        /*
         * Doing case-sensitive filters now, for greater portability.
        String                    filter = ((inFilter == null)
                                          ? null : inFilter.toUpperCase());
         */
        String filter = inFilter;

        try {
            DatabaseMetaData md            = curConn.getMetaData();
            String           dbProductName = md.getDatabaseProductName();

            //System.err.println("DB NAME = (" + dbProductName + ')');
            // Database-specific table filtering.

            /* 3 Types of actions:
             *    1) Special handling.  Return from the "case" block directly.
             *    2) Execute a specific query.  Set statement in the "case".
             *    3) Otherwise, set filter info for dbmd.getTable() in the
             *       "case".
             */
            types = new String[1];

            switch (c) {
                case '*' :
                    types = null;
                    break;

                case 'S' :
                    if (dbProductName.indexOf("Oracle") > -1) {
                        errprintln(rb.getString(SqltoolRB.VENDOR_ORACLE_DS));

                        types[0]          = "TABLE";
                        schema            = "SYS";
                        additionalSchemas = oracleSysSchemas;
                    } else {
                        types[0] = "SYSTEM TABLE";
                    }
                    break;

                case 's' :
                    if (dbProductName.indexOf("HSQL") > -1) {
                        //  HSQLDB does not consider Sequences as "tables",
                        //  hence we do not list them in
                        //  DatabaseMetaData.getTables().
                        if (filter != null
                                && filter.charAt(filter.length() - 1)
                                   == '.') {
                            narrower =
                                "\nWHERE sequence_schema = '"
                                + filter.substring(0, filter.length() - 1)
                                + "'";
                            filter = null;
                        }

                        statement = curConn.createStatement();

                        statement.execute(
                            "SELECT sequence_schema, sequence_name FROM "
                            + "information_schema.system_sequences"
                            + narrower);
                    } else {
                        types[0] = "SEQUENCE";
                    }
                    break;

                case 'r' :
                    if (dbProductName.indexOf("HSQL") > -1) {
                        statement = curConn.createStatement();

                        statement.execute(
                            "SELECT authorization_name FROM "
                            + "information_schema.system_authorizations\n"
                            + "WHERE authorization_type = 'ROLE'\n"
                            + "ORDER BY authorization_name");
                    } else if (dbProductName.indexOf(
                            "Adaptive Server Enterprise") > -1) {
                        // This is the basic Sybase server.  Sybase also has
                        // their "Anywhere", ASA (for embedded), and replication
                        // databases, but I don't know the Metadata strings for
                        // those.
                        statement = curConn.createStatement();

                        statement.execute(
                            "SELECT name FROM syssrvroles ORDER BY name");
                    } else if (dbProductName.indexOf(
                            "Apache Derby") > -1) {
                        throw new BadSpecial(
                            rb.getString(SqltoolRB.VENDOR_DERBY_DR));
                    } else {
                        throw new BadSpecial(
                            rb.getString(SqltoolRB.VENDOR_NOSUP_D, "r"));
                    }
                    break;

                case 'u' :
                    if (dbProductName.indexOf("HSQL") > -1) {
                        statement = curConn.createStatement();

                        statement.execute(
                            "SELECT user, admin FROM "
                            + "information_schema.system_users\n"
                            + "ORDER BY user");
                    } else if (dbProductName.indexOf("Oracle") > -1) {
                        statement = curConn.createStatement();

                        statement.execute(
                            "SELECT username, created FROM all_users "
                            + "ORDER BY username");
                    } else if (dbProductName.indexOf("PostgreSQL") > -1) {
                        statement = curConn.createStatement();

                        statement.execute(
                            "SELECT usename, usesuper FROM pg_catalog.pg_user "
                            + "ORDER BY usename");
                    } else if (dbProductName.indexOf(
                            "Adaptive Server Enterprise") > -1) {
                        // This is the basic Sybase server.  Sybase also has
                        // their "Anywhere", ASA (for embedded), and replication
                        // databases, but I don't know the Metadata strings for
                        // those.
                        statement = curConn.createStatement();

                        statement.execute(
                            "SELECT name, accdate, fullname FROM syslogins "
                            + "ORDER BY name");
                    } else if (dbProductName.indexOf(
                            "Apache Derby") > -1) {
                        throw new BadSpecial(
                            rb.getString(SqltoolRB.VENDOR_DERBY_DU));
                    } else {
                        throw new BadSpecial(
                            rb.getString(SqltoolRB.VENDOR_NOSUP_D, "u"));
                    }
                    break;

                case 'a' :
                    if (dbProductName.indexOf("HSQL") > -1) {
                        //  HSQLDB Aliases are not the same things as the
                        //  aliases listed in DatabaseMetaData.getTables().
                        if (filter != null
                                && filter.charAt(filter.length() - 1)
                                   == '.') {
                            narrower =
                                "\nWHERE alias_schem = '"
                                + filter.substring(0, filter.length() - 1)
                                + "'";
                            filter = null;
                        }

                        statement = curConn.createStatement();

                        statement.execute(
                            "SELECT alias_schem, alias FROM "
                            + "information_schema.system_aliases" + narrower);
                    } else {
                        types[0] = "ALIAS";
                    }
                    break;

                case 't' :
                    excludeSysSchemas = (dbProductName.indexOf("Oracle")
                                         > -1);
                    types[0] = "TABLE";
                    break;

                case 'v' :
                    types[0] = "VIEW";
                    break;

                case 'n' :
                    rs = md.getSchemas();

                    if (rs == null) {
                        throw new BadSpecial(
                            "Failed to get metadata from database");
                    }

                    displayResultSet(null, rs, listMDSchemaCols, filter);

                    return;

                case 'i' :

                    // Some databases require to specify table, some don't.
                    /*
                    if (filter == null) {
                        throw new BadSpecial("You must specify the index's "
                                + "table as argument to \\di");
                    }
                     */
                    String table = null;

                    if (filter != null) {
                        int dotat = filter.indexOf('.');

                        schema = ((dotat > 0) ? filter.substring(0, dotat)
                                              : null);

                        if (dotat < filter.length() - 1) {
                            // Not a schema-only specifier
                            table = ((dotat > 0) ? filter.substring(dotat + 1)
                                                 : filter);
                        }

                        filter = null;
                    }

                    // N.b. Oracle incorrectly reports the INDEX SCHEMA as
                    // the TABLE SCHEMA.  The Metadata structure seems to
                    // be designed with the assumption that the INDEX schema
                    // will be the same as the TABLE schema.
                    rs = md.getIndexInfo(null, schema, table, false, true);

                    if (rs == null) {
                        throw new BadSpecial(
                            "Failed to get metadata from database");
                    }

                    displayResultSet(null, rs, listMDIndexCols, null);

                    return;

                default :
                    throw new BadSpecial(rb.getString(
                            SqltoolRB.SPECIAL_D_UNKNOWN,
                                Character.toString(c)) + LS + D_OPTIONS_TEXT);
            }

            if (statement == null) {
                if (dbProductName.indexOf("HSQL") > -1) {
                    listSet = listMDTableCols[HSQLDB_ELEMENT];
                } else if (dbProductName.indexOf("Oracle") > -1) {
                    listSet = listMDTableCols[ORACLE_ELEMENT];
                } else {
                    listSet = listMDTableCols[DEFAULT_ELEMENT];
                }

                if (schema == null && filter != null
                        && filter.charAt(filter.length() - 1) == '.') {
                    schema = filter.substring(0, filter.length() - 1);
                    filter = null;
                }
            }

            rs = ((statement == null)
                  ? md.getTables(null, schema, null, types)
                  : statement.getResultSet());

            if (rs == null) {
                throw new BadSpecial(rb.getString(
                        SqltoolRB.METADATA_FETCH_FAIL));
            }

            displayResultSet(null, rs, listSet, filter);

            if (additionalSchemas != null) {
                for (int i = 1; i < additionalSchemas.length; i++) {
                    /*
                     * Inefficient, but we have to do each successful query
                     * twice in order to prevent calling displayResultSet
                     * for empty/non-existent schemas
                     */
                    rs = md.getTables(null, additionalSchemas[i], null,
                                      types);

                    if (rs == null) {
                        throw new BadSpecial(rb.getString(
                                SqltoolRB.METADATA_FETCH_FAILFOR,
                                        additionalSchemas[i]));
                    }

                    if (!rs.next()) {
                        continue;
                    }

                    displayResultSet(
                        null,
                        md.getTables(
                            null, additionalSchemas[i], null, types), listSet, filter);
                }
            }
        } catch (SQLException se) {
            throw new BadSpecial(rb.getString( SqltoolRB.METADATA_FETCH_FAIL),
                    se);
        } catch (NullPointerException npe) {
            throw new BadSpecial(rb.getString( SqltoolRB.METADATA_FETCH_FAIL),
                    npe);
        } finally {
            excludeSysSchemas = false;

            if (rs != null) {
                rs = null;
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {}

                statement = null;
            }
        }
    }

    private boolean excludeSysSchemas = false;

    /**
     * Process the contents of Edit Buffer as an SQL Statement
     *
     * @throws SQLException thrown by JDBC driver.
     * @throws SqlToolError all other errors.
     */
    private void processSQL() throws SQLException, SqlToolError {
        // Really don't know whether to take the network latency hit here
        // in order to check autoCommit in order to set
        // possiblyUncommitteds more accurately.
        // I'm going with "NO" for now, since autoCommit will usually be off.
        // If we do ever check autocommit, we have to keep track of the
        // autocommit state when every SQL statement is run, since I may
        // be able to have uncommitted DML, turn autocommit on, then run
        // other DDL with autocommit on.  As a result, I could be running
        // SQL commands with autotommit on but still have uncommitted mods.
        // (For all I know, the database could commit or rollback whenever
        // the autocommit option is changed, and that behavior could be
        // DB-specific).
        lastSqlStatement    = (plMode ? dereference(buffer, true)
                                      : buffer);
        Statement statement = null;

        if (doPrepare) {
            if (lastSqlStatement.indexOf('?') < 1) {
                lastSqlStatement = null;
                throw new SqlToolError(rb.getString(
                        SqltoolRB.PREPARE_DEMANDQM));
            }

            doPrepare = false;

            PreparedStatement ps = curConn.prepareStatement(lastSqlStatement);

            if (prepareVar == null) {
                if (binBuffer == null) {
                    lastSqlStatement = null;
                    throw new SqlToolError(rb.getString(
                            SqltoolRB.BINBUFFER_EMPTY));
                }

                ps.setBytes(1, binBuffer);
            } else {
                String val = (String) userVars.get(prepareVar);

                if (val == null) {
                    lastSqlStatement = null;
                    throw new SqlToolError(
                            rb.getString(SqltoolRB.PLVAR_UNDEFINED,
                                    prepareVar));
                }

                prepareVar = null;

                ps.setString(1, val);
            }

            ps.executeUpdate();

            statement = ps;
        } else {
            statement = curConn.createStatement();

            statement.execute(lastSqlStatement);
        }

        possiblyUncommitteds.set(true);

        try {
            displayResultSet(statement, statement.getResultSet(), null, null);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {}
        }
        lastSqlStatement = null;
    }

    /**
     * Display the given result set for user.
     * The last 3 params are to narrow down records and columns where
     * that can not be done with a where clause (like in metadata queries).
     *
     * @param statement The SQL Statement that the result set is for.
     *                  (This is so we can get the statement's update count.
     *                  Can be null for non-update queries.)
     * @param r         The ResultSet to display.
     * @param incCols   Optional list of which columns to include (i.e., if
     *                  given, then other columns will be skipped).
     * @param incFilter Optional case-insensitive substring.
     *                  Rows are skipped which to not contain this substring.
     * @throws SQLException thrown by JDBC driver.
     * @throws SqlToolError all other errors.
     */
    private void displayResultSet(Statement statement, ResultSet r,
                                  int[] incCols,
                                  String filter) throws SQLException,
                                  SqlToolError {
        java.sql.Timestamp ts;
        int dotAt;
        int                updateCount = (statement == null) ? -1
                                                             : statement
                                                                 .getUpdateCount();
        boolean            silent      = silentFetch;
        boolean            binary      = fetchBinary;

        silentFetch = false;
        fetchBinary = false;

        if (excludeSysSchemas) {
            stdprintln(rb.getString(SqltoolRB.VENDOR_NOSUP_SYSSCHEMAS));
        }

        switch (updateCount) {
            case -1 :
                if (r == null) {
                    stdprintln(rb.getString(SqltoolRB.NORESULT), true);

                    break;
                }

                ResultSetMetaData m        = r.getMetaData();
                int               cols     = m.getColumnCount();
                int               incCount = (incCols == null) ? cols
                                                               : incCols
                                                                   .length;
                String            val;
                List              rows        = new ArrayList();
                String[]          headerArray = null;
                String[]          fieldArray;
                int[]             maxWidth = new int[incCount];
                int               insi;
                boolean           skip;

                // STEP 1: GATHER DATA
                if (!htmlMode) {
                    for (int i = 0; i < maxWidth.length; i++) {
                        maxWidth[i] = 0;
                    }
                }

                boolean[] rightJust = new boolean[incCount];
                int[]     dataType  = new int[incCount];
                boolean[] autonulls = new boolean[incCount];

                insi        = -1;
                headerArray = new String[incCount];

                for (int i = 1; i <= cols; i++) {
                    if (incCols != null) {
                        skip = true;

                        for (int j = 0; j < incCols.length; j++) {
                            if (i == incCols[j]) {
                                skip = false;
                            }
                        }

                        if (skip) {
                            continue;
                        }
                    }

                    headerArray[++insi] = m.getColumnLabel(i);
                    dataType[insi]      = m.getColumnType(i);
                    rightJust[insi]     = false;
                    autonulls[insi]     = true;

                    switch (dataType[insi]) {
                        case java.sql.Types.BIGINT :
                        case java.sql.Types.BIT :
                        case java.sql.Types.DECIMAL :
                        case java.sql.Types.DOUBLE :
                        case java.sql.Types.FLOAT :
                        case java.sql.Types.INTEGER :
                        case java.sql.Types.NUMERIC :
                        case java.sql.Types.REAL :
                        case java.sql.Types.SMALLINT :
                        case java.sql.Types.TINYINT :
                            rightJust[insi] = true;
                            break;

                        case java.sql.Types.VARBINARY :
                        case java.sql.Types.VARCHAR :
                        case java.sql.Types.ARRAY :
                            // Guessing at how to handle ARRAY.
                        case java.sql.Types.BLOB :
                        case java.sql.Types.CLOB :
                        case java.sql.Types.LONGVARBINARY :
                        case java.sql.Types.LONGVARCHAR :
                            autonulls[insi] = false;
                            break;
                    }

                    if (htmlMode) {
                        continue;
                    }

                    if (headerArray[insi].length() > maxWidth[insi]) {
                        maxWidth[insi] = headerArray[insi].length();
                    }
                }

                boolean filteredOut;

                while (r.next()) {
                    fieldArray  = new String[incCount];
                    insi        = -1;
                    filteredOut = filter != null;

                    for (int i = 1; i <= cols; i++) {
                        // This is the only case where we can save a data
                        // read by recognizing we don't need this datum early.
                        if (incCols != null) {
                            skip = true;

                            for (int j = 0; j < incCols.length; j++) {
                                if (i == incCols[j]) {
                                    skip = false;
                                }
                            }

                            if (skip) {
                                continue;
                            }
                        }

                        // This row may still be ditched, but it is now
                        // certain that we need to increment the fieldArray
                        // index.
                        ++insi;

                        if (!SqlFile.canDisplayType(dataType[insi])) {
                            binary = true;
                        }

                        val = null;

                        if (!binary) {
                            /*
                             * The special formatting for all time-related
                             * fields is because the most popular current
                             * databases are extremely inconsistent about
                             * what resolution is returned for the same types.
                             * In my experience so far, Dates MAY have
                             * resolution down to second, but only TIMESTAMPs
                             * support sub-second res. (and always can).
                             * On top of that there is no consistency across
                             * getObject().toString().  Oracle doesn't even
                             * implement it for their custom TIMESTAMP type.
                             */
                            switch (dataType[insi]) {
                                case java.sql.Types.TIMESTAMP:
                                case java.sql.Types.DATE:
                                case java.sql.Types.TIME:
                                    ts  = r.getTimestamp(i);
                                    val = ((ts == null) ? null : ts.toString());
                                    // Following block truncates non-zero
                                    // sub-seconds from time types OTHER than
                                    // TIMESTAMP.
                                    if (dataType[insi]
                                            != java.sql.Types.TIMESTAMP
                                            && val != null) {
                                        dotAt = val.lastIndexOf('.');
                                        for (int z = dotAt + 1;
                                                z < val.length(); z++) {
                                            if (val.charAt(z) != '0') {
                                                dotAt = 0;
                                                break;
                                            }
                                        }
                                        if (dotAt > 1) {
                                            val = val.substring(0, dotAt);
                                        }
                                    }
                                    break;
                                default:
                                    val = r.getString(i);

                                    // If we tried to get a String but it
                                    // failed, try getting it with a String
                                    // Stream
                                    if (val == null) {
                                        try {
                                            val = streamToString(
                                                r.getAsciiStream(i), charset);
                                        } catch (Exception e) { }
                                    }
                            }
                        }

                        if (binary || (val == null &&!r.wasNull())) {
                            if (pwDsv != null) {
                                throw new SqlToolError(
                                        rb.getString(SqltoolRB.DSV_BINCOL));
                            }

                            // DB has a value but we either explicitly want
                            // it as binary, or we failed to get it as String.
                            try {
                                binBuffer =
                                    SqlFile.streamToBytes(r.getBinaryStream(i));
                            } catch (IOException ioe) {
                                throw new SqlToolError(
                                    "Failed to read value using stream",
                                    ioe);
                            }

                            stdprintln(rb.getString(SqltoolRB.BINBUF_WRITE,
                                       Integer.toString(binBuffer.length),
                                       headerArray[insi],
                                       SqlFile.sqlTypeToString(dataType[insi])
                                    ));

                            return;
                        }

                        if (excludeSysSchemas && val != null && i == 2) {
                            for (int z = 0; z < oracleSysSchemas.length;
                                    z++) {
                                if (val.equals(oracleSysSchemas[z])) {
                                    filteredOut = true;

                                    break;
                                }
                            }
                        }

                        userVars.put("?", ((val == null) ? nullRepToken : val));
                        if (fetchingVar != null) {
                            userVars.put(fetchingVar, userVars.get("?"));
                            updateUserSettings();

                            fetchingVar = null;
                        }

                        if (silent) {
                            return;
                        }

                        // We do not omit rows here.  We collect information
                        // so we can make the decision after all rows are
                        // read in.
                        if (filter != null
                                && (val == null
                                    || val.indexOf(filter) > -1)) {
                            filteredOut = false;
                        }

                        ///////////////////////////////
                        // A little tricky here.  fieldArray[] MUST get set.
                        if (val == null && pwDsv == null) {
                            if (dataType[insi] == java.sql.Types.VARCHAR) {
                                fieldArray[insi] = (htmlMode ? "<I>null</I>"
                                                             : nullRepToken);
                            } else {
                                fieldArray[insi] = "";
                            }
                        } else {
                            fieldArray[insi] = val;
                        }

                        ///////////////////////////////
                        if (htmlMode || pwDsv != null) {
                            continue;
                        }

                        if (fieldArray[insi].length() > maxWidth[insi]) {
                            maxWidth[insi] = fieldArray[insi].length();
                        }
                    }

                    if (!filteredOut) {
                        rows.add(fieldArray);
                    }
                }

                // STEP 2: DISPLAY DATA  (= 2a OR 2b)
                // STEP 2a (Non-DSV)
                if (pwDsv == null) {
                    condlPrintln("<TABLE border='1'>", true);

                    if (incCount > 1) {
                        condlPrint(SqlFile.htmlRow(COL_HEAD) + LS + PRE_TD, true);

                        for (int i = 0; i < headerArray.length; i++) {
                            condlPrint("<TD>" + headerArray[i] + "</TD>",
                                       true);
                            condlPrint(((i > 0) ? SqlFile.spaces(2)
                                                : "") + SqlFile.pad(
                                                    headerArray[i],
                                                    maxWidth[i],
                                                    rightJust[i],
                                                    (i < headerArray.length
                                                     - 1 || rightJust[i])), false);
                        }

                        condlPrintln(LS + PRE_TR + "</TR>", true);
                        condlPrintln("", false);

                        if (!htmlMode) {
                            for (int i = 0; i < headerArray.length; i++) {
                                condlPrint(((i > 0) ? SqlFile.spaces(2)
                                                    : "") + SqlFile.divider(
                                                        maxWidth[i]), false);
                            }

                            condlPrintln("", false);
                        }
                    }

                    for (int i = 0; i < rows.size(); i++) {
                        condlPrint(SqlFile.htmlRow(((i % 2) == 0) ? COL_EVEN
                                                          : COL_ODD) + LS
                                                          + PRE_TD, true);

                        fieldArray = (String[]) rows.get(i);

                        for (int j = 0; j < fieldArray.length; j++) {
                            condlPrint("<TD>" + fieldArray[j] + "</TD>",
                                       true);
                            condlPrint(((j > 0) ? SqlFile.spaces(2)
                                                : "") + SqlFile.pad(
                                                    fieldArray[j],
                                                    maxWidth[j],
                                                    rightJust[j],
                                                    (j < fieldArray.length
                                                     - 1 || rightJust[j])), false);
                        }

                        condlPrintln(LS + PRE_TR + "</TR>", true);
                        condlPrintln("", false);
                    }

                    condlPrintln("</TABLE>", true);

                    if (rows.size() != 1) {
                        stdprintln(LS + rb.getString(SqltoolRB.ROWS_FETCHED,
                                rows.size()), true);
                    }

                    condlPrintln("<HR>", true);

                    break;
                }

                // STEP 2b (DSV)
                if (incCount > 0) {
                    for (int i = 0; i < headerArray.length; i++) {
                        dsvSafe(headerArray[i]);
                        pwDsv.print(headerArray[i]);

                        if (i < headerArray.length - 1) {
                            pwDsv.print(dsvColDelim);
                        }
                    }

                    pwDsv.print(dsvRowDelim);
                }

                for (int i = 0; i < rows.size(); i++) {
                    fieldArray = (String[]) rows.get(i);

                    for (int j = 0; j < fieldArray.length; j++) {
                        dsvSafe(fieldArray[j]);
                        pwDsv.print((fieldArray[j] == null)
                                    ? (autonulls[j] ? ""
                                                    : nullRepToken)
                                    : fieldArray[j]);

                        if (j < fieldArray.length - 1) {
                            pwDsv.print(dsvColDelim);
                        }
                    }

                    pwDsv.print(dsvRowDelim);
                }

                stdprintln(rb.getString(SqltoolRB.ROWS_FETCHED_DSV,
                        rows.size()));
                break;

            default :
                userVars.put("?", Integer.toString(updateCount));
                if (fetchingVar != null) {
                    userVars.put(fetchingVar, userVars.get("?"));
                    updateUserSettings();
                    fetchingVar = null;
                }

                if (updateCount != 0) {
                    stdprintln((updateCount == 1)
                        ? rb.getString(SqltoolRB.ROW_UPDATE_SINGULAR)
                        : rb.getString(SqltoolRB.ROW_UPDATE_MULTIPLE,
                                updateCount));
                }
                break;
        }
    }

    private static final int    COL_HEAD = 0,
                                COL_ODD  = 1,
                                COL_EVEN = 2
    ;
    private static final String PRE_TR   = spaces(4);
    private static final String PRE_TD   = spaces(8);

    /**
     * Print a properly formatted HTML &lt;TR&gt; command for the given
     * situation.
     *
     * @param colType Column type:  COL_HEAD, COL_ODD or COL_EVEN.
     */
    private static String htmlRow(int colType) {
        switch (colType) {
            case COL_HEAD :
                return PRE_TR + "<TR style='font-weight: bold;'>";

            case COL_ODD :
                return PRE_TR
                       + "<TR style='background: #94d6ef; font: normal "
                       + "normal 10px/10px Arial, Helvitica, sans-serif;'>";

            case COL_EVEN :
                return PRE_TR
                       + "<TR style='background: silver; font: normal "
                       + "normal 10px/10px Arial, Helvitica, sans-serif;'>";
        }

        return null;
    }

    /**
     * Returns a divider of hypens of requested length.
     *
     * @param len Length of output String.
     */
    private static String divider(int len) {
        return (len > DIVIDER.length()) ? DIVIDER
                                        : DIVIDER.substring(0, len);
    }

    /**
     * Returns a String of spaces of requested length.
     *
     * @param len Length of output String.
     */
    private static String spaces(int len) {
        return (len > SPACES.length()) ? SPACES
                                       : SPACES.substring(0, len);
    }

    /**
     * Pads given input string out to requested length with space
     * characters.
     *
     * @param inString Base string.
     * @param fulllen  Output String length.
     * @param rightJustify  True to right justify, false to left justify.
     */
    private static String pad(String inString, int fulllen,
                              boolean rightJustify, boolean doPad) {
        if (!doPad) {
            return inString;
        }

        int len = fulllen - inString.length();

        if (len < 1) {
            return inString;
        }

        String pad = SqlFile.spaces(len);

        return ((rightJustify ? pad
                              : "") + inString + (rightJustify ? ""
                                                               : pad));
    }

    /**
     * Display command history.
     */
    private void showHistory() throws BadSpecial {
        if (history == null) {
            throw new BadSpecial(rb.getString(SqltoolRB.HISTORY_UNAVAILABLE));
        }
        if (history.size() < 1) {
            throw new BadSpecial(rb.getString(SqltoolRB.HISTORY_NONE));
        }
        for (int i = 0; i < history.size(); i++) {
            psStd.println("#" + (i + oldestHist) + " or "
                    + (i - history.size()) + ':');
            psStd.println((String) history.get(i));
        }
        if (buffer != null) {
            psStd.println(rb.getString(SqltoolRB.EDITBUFFER_CONTENTS, buffer));
        }

        psStd.println();
        psStd.println(rb.getString(SqltoolRB.BUFFER_INSTRUCTIONS));
    }

    /**
     * Return a Command from command history.
     */
    private String commandFromHistory(int inIndex) throws BadSpecial {
        int index = inIndex;  // Just to quiet compiler warnings.

        if (history == null) {
            throw new BadSpecial(rb.getString(SqltoolRB.HISTORY_UNAVAILABLE));
        }
        if (index == 0) {
            throw new BadSpecial(rb.getString(SqltoolRB.HISTORY_NUMBER_REQ));
        }
        if (index > 0) {
            // Positive command# given
            index -= oldestHist;
            if (index < 0) {
                throw new BadSpecial(rb.getString(SqltoolRB.HISTORY_BACKTO,
                       oldestHist));
            }
            if (index >= history.size()) {
                throw new BadSpecial(rb.getString(SqltoolRB.HISTORY_UPTO,
                       history.size() + oldestHist - 1));
            }
        } else {
            // Negative command# given
            index += history.size();
            if (index < 0) {
                throw new BadSpecial(rb.getString(SqltoolRB.HISTORY_BACK,
                       history.size()));
            }
        }
        return (String) history.get(index);
    }

    /**
     * Search Command History for a regex match.
     *
     * @returns Absolute command number, if any match.
     */
    private Integer historySearch(String findRegex) throws BadSpecial {
        if (history == null) {
            throw new BadSpecial(rb.getString(SqltoolRB.HISTORY_UNAVAILABLE));
        }
        Pattern pattern = Pattern.compile("(?ims)" + findRegex);
        // Make matching more liberal.  Users can customize search behavior
        // by using "(?-OPTIONS)" or (?OPTIONS) in their regexes.
        for (int index = history.size() - 1; index >= 0; index--)
            if (pattern.matcher((String) history.get(index)).find())
                return new Integer(index + oldestHist);
        return null;
    }

    private void setBuf(String newContent) {
        buffer = new String(newContent);
        // System.err.println("Buffer is now (" + buffer + ')');
    }

    int oldestHist = 1;

    /**
     * Add a command onto the history list.
     */
    private void historize() {
        if (history == null || buffer == null) {
            return;
        }
        if (history.size() > 0 &&
                history.get(history.size() - 1).equals(buffer)) {
            // Don't store two consecutive commands that are exactly the same.
            return;
        }
        history.add(buffer);
        if (history.size() <= maxHistoryLength) {
            return;
        }
        history.remove(0);
        oldestHist++;
    }

    /**
     * Describe the columns of specified table.
     *
     * @param tableName  Table that will be described.
     * @param filter  Substring to filter by
     */
    private void describe(String tableName,
                          String inFilter) throws SQLException {
        /*
         * Doing case-sensitive filters now, for greater portability.
        String filter = ((inFilter == null) ? null : inFilter.toUpperCase());
         */
        String    filter = inFilter;
        List      rows        = new ArrayList();
        String[]  headerArray = {
            rb.getString(SqltoolRB.DESCRIBE_TABLE_NAME),
            rb.getString(SqltoolRB.DESCRIBE_TABLE_DATATYPE),
            rb.getString(SqltoolRB.DESCRIBE_TABLE_WIDTH),
            rb.getString(SqltoolRB.DESCRIBE_TABLE_NONULLS),
        };
        String[]  fieldArray;
        int[]     maxWidth  = {
            0, 0, 0, 0
        };
        boolean[] rightJust = {
            false, false, true, false
        };

        // STEP 1: GATHER DATA
        for (int i = 0; i < headerArray.length; i++) {
            if (htmlMode) {
                continue;
            }

            if (headerArray[i].length() > maxWidth[i]) {
                maxWidth[i] = headerArray[i].length();
            }
        }

        Statement statement = curConn.createStatement();
        ResultSet r         = null;

        try {
            statement.execute("SELECT * FROM " + tableName + " WHERE 1 = 2");

            r = statement.getResultSet();

            ResultSetMetaData m    = r.getMetaData();
            int               cols = m.getColumnCount();

            for (int i = 0; i < cols; i++) {
                fieldArray    = new String[4];
                fieldArray[0] = m.getColumnName(i + 1);

                if (filter != null && fieldArray[0].indexOf(filter) < 0) {
                    continue;
                }

                fieldArray[1] = m.getColumnTypeName(i + 1);
                fieldArray[2] = Integer.toString(m.getColumnDisplaySize(i + 1));
                fieldArray[3] =
                    ((m.isNullable(i + 1) == java.sql.ResultSetMetaData.columnNullable)
                     ? (htmlMode ? "&nbsp;"
                                 : "")
                     : "*");

                rows.add(fieldArray);

                for (int j = 0; j < fieldArray.length; j++) {
                    if (fieldArray[j].length() > maxWidth[j]) {
                        maxWidth[j] = fieldArray[j].length();
                    }
                }
            }

            // STEP 2: DISPLAY DATA
            condlPrint("<TABLE border='1'>" + LS + SqlFile.htmlRow(COL_HEAD) + LS
                       + PRE_TD, true);

            for (int i = 0; i < headerArray.length; i++) {
                condlPrint("<TD>" + headerArray[i] + "</TD>", true);
                condlPrint(((i > 0) ? SqlFile.spaces(2)
                                    : "") + SqlFile.pad(headerArray[i], maxWidth[i],
                                                rightJust[i],
                                                (i < headerArray.length - 1
                                                 || rightJust[i])), false);
            }

            condlPrintln(LS + PRE_TR + "</TR>", true);
            condlPrintln("", false);

            if (!htmlMode) {
                for (int i = 0; i < headerArray.length; i++) {
                    condlPrint(((i > 0) ? SqlFile.spaces(2)
                                        : "") + SqlFile.divider(maxWidth[i]), false);
                }

                condlPrintln("", false);
            }

            for (int i = 0; i < rows.size(); i++) {
                condlPrint(SqlFile.htmlRow(((i % 2) == 0) ? COL_EVEN
                                                  : COL_ODD) + LS
                                                  + PRE_TD, true);

                fieldArray = (String[]) rows.get(i);

                for (int j = 0; j < fieldArray.length; j++) {
                    condlPrint("<TD>" + fieldArray[j] + "</TD>", true);
                    condlPrint(((j > 0) ? SqlFile.spaces(2)
                                        : "") + SqlFile.pad(
                                            fieldArray[j], maxWidth[j],
                                            rightJust[j],
                                            (j < fieldArray.length - 1
                                             || rightJust[j])), false);
                }

                condlPrintln(LS + PRE_TR + "</TR>", true);
                condlPrintln("", false);
            }

            condlPrintln(LS + "</TABLE>" + LS + "<HR>", true);
        } finally {
            try {
                if (r != null) {
                    r.close();

                    r = null;
                }

                statement.close();
            } catch (Exception e) {}
        }
    }

    private boolean eval(String[] inTokens) throws BadSpecial {
        /* TODO:  Rewrite using java.util.regex.  */
        // dereference *VARNAME variables.
        // N.b. we work with a "copy" of the tokens.
        boolean  negate = inTokens.length > 0 && inTokens[0].equals("!");
        String[] tokens = new String[negate ? (inTokens.length - 1)
                                            : inTokens.length];
        String inToken;

        for (int i = 0; i < tokens.length; i++) {
            inToken = inTokens[i + (negate ? 1 : 0)];
            if (inToken.length() > 1 && inToken.charAt(0) == '*') {
                tokens[i] = (String) userVars.get(inToken.substring(1));
            } else {
                tokens[i] = inTokens[i + (negate ? 1 : 0)];
            }

            // Unset variables permitted in expressions as long as use
            // the short *VARNAME form.
            if (tokens[i] == null) {
                tokens[i] = "";
            }
        }

        if (tokens.length == 1) {
            return (tokens[0].length() > 0 &&!tokens[0].equals("0")) ^ negate;
        }

        if (tokens.length == 3) {
            if (tokens[1].equals("==")) {
                return tokens[0].equals(tokens[2]) ^ negate;
            }

            if (tokens[1].equals("!=") || tokens[1].equals("<>")
                    || tokens[1].equals("><")) {
                return (!tokens[0].equals(tokens[2])) ^ negate;
            }

            if (tokens[1].equals(">")) {
                return (tokens[0].length() > tokens[2].length() || ((tokens[0].length() == tokens[2].length()) && tokens[0].compareTo(tokens[2]) > 0))
                       ^ negate;
            }

            if (tokens[1].equals("<")) {
                return (tokens[2].length() > tokens[0].length() || ((tokens[2].length() == tokens[0].length()) && tokens[2].compareTo(tokens[0]) > 0))
                       ^ negate;
            }
        }

        throw new BadSpecial(rb.getString(SqltoolRB.LOGICAL_UNRECOGNIZED));
    }

    private void closeQueryOutputStream() {
        if (pwQuery == null) {
            return;
        }

        if (htmlMode) {
            pwQuery.println("</BODY></HTML>");
            pwQuery.flush();
        }

        pwQuery.close();

        pwQuery = null;
    }

    /**
     * Print to psStd and possibly pwQuery iff current HTML mode matches
     * supplied printHtml.
     */
    private void condlPrintln(String s, boolean printHtml) {
        if ((printHtml &&!htmlMode) || (htmlMode &&!printHtml)) {
            return;
        }

        psStd.println(s);

        if (pwQuery != null) {
            pwQuery.println(s);
            pwQuery.flush();
        }
    }

    /**
     * Print to psStd and possibly pwQuery iff current HTML mode matches
     * supplied printHtml.
     */
    private void condlPrint(String s, boolean printHtml) {
        if ((printHtml &&!htmlMode) || (htmlMode &&!printHtml)) {
            return;
        }

        psStd.print(s);

        if (pwQuery != null) {
            pwQuery.print(s);
            pwQuery.flush();
        }
    }

    private String formatNicely(Map map, boolean withValues) {
        String       key;
        StringBuffer sb = new StringBuffer();
        Iterator     it = (new TreeMap(map)).keySet().iterator();

        if (withValues) {
            SqlFile.appendLine(sb, rb.getString(SqltoolRB.PL_LIST_PARENS));
        } else {
            SqlFile.appendLine(sb, rb.getString(SqltoolRB.PL_LIST_LENGTHS));
        }

        while (it.hasNext()) {
            key = (String) it.next();

            String s = (String) map.get(key);

            SqlFile.appendLine(sb, "    " + key + ": " + (withValues ? ("(" + s + ')')
                                                        : Integer.toString(
                                                        s.length())));
        }

        return sb.toString();
    }

    /**
     * Ascii file dump.
     *
     * dumpFile must not be null.
     */
    private void dump(String varName,
                      File dumpFile) throws IOException, BadSpecial {
        String val = (String) userVars.get(varName);

        if (val == null) {
            throw new BadSpecial(rb.getString(
                    SqltoolRB.PLVAR_UNDEFINED, varName));
        }

        OutputStreamWriter osw = ((charset == null)
                ? (new OutputStreamWriter(new FileOutputStream(dumpFile)))
                : (new OutputStreamWriter(new FileOutputStream(dumpFile),
                            charset)));
        // Replace with just "(new FileOutputStream(file), charset)"
        // once use defaultCharset from Java 1.5 in charset init. above.

        try {
            osw.write(val);

            if (val.length() > 0) {
                char lastChar = val.charAt(val.length() - 1);

                if (lastChar != '\n' && lastChar != '\r') {
                    osw.write(LS);
                }
            }

            osw.flush();
        } finally {
            osw.close();
        }

        // Since opened in overwrite mode, since we didn't exception out,
        // we can be confident that we wrote all the bytest in the file.
        stdprintln(rb.getString(SqltoolRB.FILE_WROTECHARS,
                Long.toString(dumpFile.length()), dumpFile.toString()));
    }

    byte[] binBuffer = null;

    /**
     * Binary file dump
     *
     * dumpFile must not be null.
     */
    private void dump(File dumpFile) throws IOException, BadSpecial {
        if (binBuffer == null) {
            throw new BadSpecial(rb.getString(SqltoolRB.BINBUFFER_EMPTY));
        }

        FileOutputStream fos = new FileOutputStream(dumpFile);
        int len = 0;

        try {
            fos.write(binBuffer);

            len = binBuffer.length;

            binBuffer = null;

            fos.flush();
        } finally {
            fos.close();
        }
        stdprintln(rb.getString(SqltoolRB.FILE_WROTECHARS,
                len, dumpFile.toString()));
    }

    /**
     * As the name says...
     * This method always closes the input stream.
     */
    public String streamToString(InputStream is, String cs)
            throws IOException {
        try {
            byte[] ba = null;
            int bytesread = 0;
            int retval;
            try {
                ba = new byte[is.available()];
            } catch (RuntimeException re) {
                throw new IOException(rb.getString(SqltoolRB.READ_TOOBIG));
            }
            while (bytesread < ba.length &&
                    (retval = is.read(
                            ba, bytesread, ba.length - bytesread)) > 0) {
                bytesread += retval;
            }
            if (bytesread != ba.length) {
                throw new IOException(rb.getString(SqltoolRB.READ_PARTIAL,
                            bytesread,
                            ba.length));
            }
            try {
                return (cs == null) ? (new String(ba))
                                         : (new String(ba, cs));
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException(uee);
            } catch (RuntimeException re) {
                throw new IOException(rb.getString(SqltoolRB.READ_CONVERTFAIL));
            }
        } finally {
            is.close();
        }
    }

    /**
     * Ascii file load.
     */
    private void load(String varName, File asciiFile, String cs)
            throws IOException {
        String string = streamToString(new FileInputStream(asciiFile), cs);
        userVars.put(varName, string);
        updateUserSettings();
    }

    static public byte[] streamToBytes(InputStream is) throws IOException {
        byte[]                xferBuffer = new byte[10240];
        ByteArrayOutputStream baos       = new ByteArrayOutputStream();
        int                   i;

        while ((i = is.read(xferBuffer)) > 0) {
            baos.write(xferBuffer, 0, i);
        }

        return baos.toByteArray();
    }

    /**
     * Binary file load
     */
    static public byte[] loadBinary(File binFile) throws IOException {
        byte[]                xferBuffer = new byte[10240];
        ByteArrayOutputStream baos       = new ByteArrayOutputStream();
        int                   i;
        FileInputStream       fis        = new FileInputStream(binFile);

        try {
            while ((i = fis.read(xferBuffer)) > 0) {
                baos.write(xferBuffer, 0, i);
            }
        } finally {
            fis.close();
        }

        byte[] ba = baos.toByteArray();

        return ba;
    }

    /**
     * This method is used to tell SqlFile whether this Sql Type must
     * ALWAYS be loaded to the binary buffer without displaying.
     *
     * N.b.:  If this returns "true" for a type, then the user can never
     * "see" values for these columns.
     * Therefore, if a type may-or-may-not-be displayable, better to return
     * false here and let the user choose.
     * In general, if there is a toString() operator for this Sql Type
     * then return false, since the JDBC driver should know how to make the
     * value displayable.
     *
     * The table on this page lists the most common SqlTypes, all of which
     * must implement toString():
     *     http://java.sun.com/docs/books/tutorial/jdbc/basics/retrieving.html
     *
     * @see java.sql.Types
     */
    public static boolean canDisplayType(int i) {
        /* I don't now about some of the more obscure types, like REF and
         * DATALINK */
        switch (i) {
            //case java.sql.Types.BINARY :
            case java.sql.Types.BLOB :
            case java.sql.Types.JAVA_OBJECT :

            //case java.sql.Types.LONGVARBINARY :
            //case java.sql.Types.LONGVARCHAR :
            case java.sql.Types.OTHER :
            case java.sql.Types.STRUCT :

                //case java.sql.Types.VARBINARY :
                return false;
        }

        return true;
    }

    // won't compile with JDK 1.3 without these
    private static final int JDBC3_BOOLEAN  = 16;
    private static final int JDBC3_DATALINK = 70;

    public static String sqlTypeToString(int i) {
        switch (i) {
            case java.sql.Types.ARRAY :
                return "ARRAY";

            case java.sql.Types.BIGINT :
                return "BIGINT";

            case java.sql.Types.BINARY :
                return "BINARY";

            case java.sql.Types.BIT :
                return "BIT";

            case java.sql.Types.BLOB :
                return "BLOB";

            case JDBC3_BOOLEAN :
                return "BOOLEAN";

            case java.sql.Types.CHAR :
                return "CHAR";

            case java.sql.Types.CLOB :
                return "CLOB";

            case JDBC3_DATALINK :
                return "DATALINK";

            case java.sql.Types.DATE :
                return "DATE";

            case java.sql.Types.DECIMAL :
                return "DECIMAL";

            case java.sql.Types.DISTINCT :
                return "DISTINCT";

            case java.sql.Types.DOUBLE :
                return "DOUBLE";

            case java.sql.Types.FLOAT :
                return "FLOAT";

            case java.sql.Types.INTEGER :
                return "INTEGER";

            case java.sql.Types.JAVA_OBJECT :
                return "JAVA_OBJECT";

            case java.sql.Types.LONGVARBINARY :
                return "LONGVARBINARY";

            case java.sql.Types.LONGVARCHAR :
                return "LONGVARCHAR";

            case java.sql.Types.NULL :
                return "NULL";

            case java.sql.Types.NUMERIC :
                return "NUMERIC";

            case java.sql.Types.OTHER :
                return "OTHER";

            case java.sql.Types.REAL :
                return "REAL";

            case java.sql.Types.REF :
                return "REF";

            case java.sql.Types.SMALLINT :
                return "SMALLINT";

            case java.sql.Types.STRUCT :
                return "STRUCT";

            case java.sql.Types.TIME :
                return "TIME";

            case java.sql.Types.TIMESTAMP :
                return "TIMESTAMP";

            case java.sql.Types.TINYINT :
                return "TINYINT";

            case java.sql.Types.VARBINARY :
                return "VARBINARY";

            case java.sql.Types.VARCHAR :
                return "VARCHAR";
        }

        return "Unknown type " + i;
    }

    /**
     * Validate that String is safe to display in a DSV file.
     *
     * @throws SqlToolError if validation fails.
     */
    public void dsvSafe(String s) throws SqlToolError {
        if (pwDsv == null || dsvColDelim == null || dsvRowDelim == null
                || nullRepToken == null) {
            throw new RuntimeException(
                "Assertion failed.  \n"
                + "dsvSafe called when DSV settings are incomplete");
        }

        if (s == null) {
            return;
        }

        if (s.indexOf(dsvColDelim) > 0) {
            throw new SqlToolError(rb.getString(SqltoolRB.DSV_COLDELIM_PRESENT,
                        dsvColDelim));
        }

        if (s.indexOf(dsvRowDelim) > 0) {
            throw new SqlToolError(rb.getString(SqltoolRB.DSV_ROWDELIM_PRESENT,
                        dsvRowDelim));
        }

        if (s.trim().equals(nullRepToken)) {
            // The trim() is to avoid the situation where the contents of a
            // field "looks like" the null-rep token.
            throw new SqlToolError(rb.getString(SqltoolRB.DSV_NULLREP_PRESENT,
                        nullRepToken));
        }
    }

    /**
     * Translates user-supplied escapes into the traditionaly corresponding
     * corresponding binary characters.
     *
     * Allowed sequences:
     * <UL>
     *  <LI>\0\d+   (an octal digit)
     *  <LI>\[0-9]\d*  (a decimal digit)
     *  <LI>\[Xx][0-9]{2}  (a hex digit)
     *  <LI>\n  Newline  (Ctrl-J)
     *  <LI>\r  Carriage return  (Ctrl-M)
     *  <LI>\t  Horizontal tab  (Ctrl-I)
     *  <LI>\f  Form feed  (Ctrl-L)
     * </UL>
     *
     * Java 1.4 String methods will make this into a 1 or 2 line task.
     */
    public static String convertEscapes(String inString) {
        if (inString == null) {
            return null;
        }
        return convertNumericEscapes(
                convertEscapes(convertEscapes(convertEscapes(convertEscapes(
                    convertEscapes(inString, "\\n", "\n"), "\\r", "\r"),
                "\\t", "\t"), "\\\\", "\\"),
            "\\f", "\f")
        );
    }

    /**
     * @param string  Non-null String to modify.
     */
    private static String convertNumericEscapes(String string) {
        String workString = string;
        int i = 0;

        for (char dig = '0'; dig <= '9'; dig++) {
            while ((i = workString.indexOf("\\" + dig, i)) > -1
                    && i < workString.length() - 1) {
                workString = convertNumericEscape(string, i);
            }
            while ((i = workString.indexOf("\\x" + dig, i)) > -1
                    && i < workString.length() - 1) {
                workString = convertNumericEscape(string, i);
            }
            while ((i = workString.indexOf("\\X" + dig, i)) > -1
                    && i < workString.length() - 1) {
                workString = convertNumericEscape(string, i);
            }
        }
        return workString;
    }

    /**
     * @offset  Position of the leading \.
     */
    private static String convertNumericEscape(String string, int offset) {
        int post = -1;
        int firstDigit = -1;
        int radix = -1;
        if (Character.toUpperCase(string.charAt(offset + 1)) == 'X') {
            firstDigit = offset + 2;
            radix = 16;
            post = firstDigit + 2;
            if (post > string.length()) post = string.length();
        } else {
            firstDigit = offset + 1;
            radix = (Character.toUpperCase(string.charAt(firstDigit)) == '0')
                    ? 8 : 10;
            for (post = firstDigit + 1; post < string.length()
                    && Character.isDigit(string.charAt(post)); post++) ;
        }
        return string.substring(0, offset) + ((char)
                Integer.parseInt(string.substring(firstDigit, post), radix))
                + string.substring(post);
    }

    /**
     * @param string  Non-null String to modify.
     */
    private static String convertEscapes(String string, String from, String to) {
        String workString = string;
        int i = 0;
        int fromLen = from.length();

        while ((i = workString.indexOf(from, i)) > -1
                && i < workString.length() - 1) {
            workString = workString.substring(0, i) + to
                         + workString.substring(i + fromLen);
        }
        return workString;
    }

    /**
     * Name is self-explanatory.
     *
     * If there is user demand, open file in random access mode so don't
     * need to load 2 copies of the entire file into memory.
     * This will be difficult because can't use standard Java language
     * features to search through a character array for multi-character
     * substrings.
     *
     * @throws SqlToolError  Would prefer to throw an internal exception,
     *                       but we want this method to have external
     *                       visibility.
     */
    public void importDsv(String filePath, String skipPrefix)
            throws SqlToolError {
        /* To make string comparisons, contains() methods, etc. a little
         * simpler and concise, just switch all column names to lower-case.
         * This is ok since we acknowledge up from that DSV import/export
         * assume no special characters or escaping in column names. */
        byte[] bfr  = null;
        File   file = new File(filePath);
        SortedMap constColMap = null;
        if (dsvConstCols != null) {
            // We trim col. names, but not values.  Must allow users to
            // specify values as spaces, empty string, null.
            constColMap = new TreeMap();
            String[] constPairs = dsvConstCols.split("\\Q"
                    + dsvColDelim + "\\E\\s*");
            int firstEq;
            String n;
            for (int i = 0; i < constPairs.length; i++) {
                firstEq = constPairs[i].indexOf('=');
                n = constPairs[i].substring(0, firstEq).trim().toLowerCase();
                if (n.trim().length() < 1) {
                    throw new SqlToolError(
                            rb.getString(SqltoolRB.DSV_CONSTCOLS_NULLCOL));
                }
                constColMap.put(n, constPairs[i].substring(firstEq + 1));
            }
        }
        Set skipCols = null;
        if (dsvSkipCols != null) {
            skipCols = new HashSet();
            String[] skipColsArray = dsvSkipCols.split("\\s*\\Q"
                    + dsvColDelim + "\\E\\s*");
            for (int i = 0; i < skipColsArray.length; i++) {
                skipCols.add(skipColsArray[i].toLowerCase());
            }
        }

        if (!file.canRead()) {
            throw new SqlToolError(rb.getString(SqltoolRB.FILE_READFAIL,
                    file.toString()));
        }

        try {
            bfr = new byte[(int) file.length()];
        } catch (RuntimeException re) {
            throw new SqlToolError(rb.getString(SqltoolRB.READ_TOOBIG), re);
        }

        int bytesread = 0;
        int retval;
        InputStream is = null;

        try {
            is = new FileInputStream(file);
            while (bytesread < bfr.length &&
                    (retval = is.read(bfr, bytesread, bfr.length - bytesread))
                    > 0) {
                bytesread += retval;
            }

        } catch (IOException ioe) {
            throw new SqlToolError(ioe);
        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException ioe) {
                errprintln(rb.getString(SqltoolRB.INPUTFILE_CLOSEFAIL)
                        + ": " + ioe);
            }
        }
        if (bytesread != bfr.length) {
            throw new SqlToolError(rb.getString(SqltoolRB.READ_PARTIAL,
                    bytesread, bfr.length));
        }

        String string = null;
        String dateString;

        try {
            string = ((charset == null)
                    ? (new String(bfr)) : (new String(bfr, charset)));
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        } catch (RuntimeException re) {
            throw new SqlToolError(rb.getString(SqltoolRB.READ_CONVERTFAIL),
                    re);
        }

        List     headerList = new ArrayList();
        String    tableName = dsvTargetTable;

        // N.b.  ENDs are the index of 1 PAST the current item
        int recEnd = -1000; // Recognizable value incase something goes
                            // horrifically wrong.
        int colStart;
        int colEnd;

        // First read one until we get one header line
        int lineCount = 0; // Assume a 1 line header?
        int recStart = -1;
        String trimmedLine = null;
        boolean switching = false;

        while (true) {
            recStart = (recStart < 0) ? 0 : (recEnd + dsvRowDelim.length());
            if (recStart > string.length() - 2) {
                throw new SqlToolError(rb.getString(SqltoolRB.DSV_HEADER_NONE));
            }
            recEnd = string.indexOf(dsvRowDelim, recStart);
            lineCount++; // Increment when we have line start and end

            if (recEnd < 0) {
                // Last line in file.  No data records.
                recEnd = string.length();
            }
            trimmedLine = string.substring(recStart, recEnd).trim();
            if (trimmedLine.length() < 1
                    || (skipPrefix != null
                            && trimmedLine.startsWith(skipPrefix))) {
                continue;
            }
            if (trimmedLine.startsWith("targettable=")) {
                if (tableName == null) {
                    tableName = trimmedLine.substring(
                            "targettable=".length()).trim();
                }
                continue;
            }
            if (trimmedLine.equals("headerswitch{")) {
                if (tableName == null) {
                    throw new SqlToolError(rb.getString(
                            SqltoolRB.DSV_HEADER_NOSWITCHTARG, lineCount));
                }
                switching = true;
                continue;
            }
            if (trimmedLine.equals("}")) {
                throw new SqlToolError(rb.getString(
                        SqltoolRB.DSV_HEADER_NOSWITCHMATCH, lineCount));
            }
            if (!switching) {
                break;
            }
            int colonAt = trimmedLine.indexOf(':');
            if (colonAt < 1 || colonAt == trimmedLine.length() - 1) {
                throw new SqlToolError(rb.getString(
                        SqltoolRB.DSV_HEADER_NONSWITCHED, lineCount));
            }
            String matcher = trimmedLine.substring(0, colonAt).trim();
            // Need to be sure here that tableName is not null (in
            // which case it would be determined later on by the file name).
            if (matcher.equals("*") || matcher.equalsIgnoreCase(tableName)){
                recStart = 1 + string.indexOf(':', recStart);
                break;
            }
            // Skip non-matched header line
        }

        String headerLine = string.substring(recStart, recEnd);
        colStart = recStart;
        colEnd   = -1;
        String colName;

        while (true) {
            if (colEnd == recEnd) {
                // We processed final column last time through loop
                break;
            }

            colEnd = string.indexOf(dsvColDelim, colStart);

            if (colEnd < 0 || colEnd > recEnd) {
                colEnd = recEnd;
            }

            if (colEnd - colStart < 1) {
                throw new SqlToolError(rb.getString(
                        SqltoolRB.DSV_NOCOLHEADER,
                        headerList.size() + 1, lineCount));
            }

            colName = string.substring(colStart, colEnd).trim().toLowerCase();
            headerList.add(
                (colName.equals("-")
                        || (skipCols != null
                                && skipCols.remove(colName))
                        || (constColMap != null
                                && constColMap.containsKey(colName))
                )
                ? ((String) null)
                : colName);

            colStart = colEnd + dsvColDelim.length();
        } if (skipCols != null && skipCols.size() > 0) {
            throw new SqlToolError(rb.getString(
                    SqltoolRB.DSV_SKIPCOLS_MISSING, skipCols.toString()));
        }

        boolean oneCol = false;  // At least 1 non-null column
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.get(i) != null) {
                oneCol = true;
                break;
            }
        }
        if (oneCol == false) {
            // Difficult call, but I think in any real-world situation, the
            // user will want to know if they are inserting records with no
            // data from their input file.
            throw new SqlToolError(rb.getString(SqltoolRB.DSV_NOCOLSLEFT,
                    dsvSkipCols));
        }

        int inputColHeadCount = headerList.size();

        if (constColMap != null) {
            headerList.addAll(constColMap.keySet());
        }

        String[]  headers   = (String[]) headerList.toArray(new String[0]);
        // headers contains input headers + all constCols, some of these
        // values may be nulls.

        if (tableName == null) {
            tableName = file.getName();

            int i = tableName.lastIndexOf('.');

            if (i > 0) {
                tableName = tableName.substring(0, i);
            }
        }

        StringBuffer tmpSb = new StringBuffer();
        List tmpList = new ArrayList();

        int skippers = 0;
        for (int i = 0; i < headers.length; i++) {
            if (headers[i] == null) {
                skippers++;
                continue;
            }
            if (tmpSb.length() > 0) {
                tmpSb.append(", ");
            }

            tmpSb.append(headers[i]);
            tmpList.add(headers[i]);
        }
        boolean[] autonulls = new boolean[headers.length - skippers];
        boolean[] parseDate = new boolean[autonulls.length];
        boolean[] parseBool = new boolean[autonulls.length];
        String[] insertFieldName = (String[]) tmpList.toArray(new String[] {});
        // Remember that the headers array has all columns in DSV file,
        // even skipped columns.
        // The autonulls array only has columns that we will insert into.

        StringBuffer sb = new StringBuffer("INSERT INTO " + tableName + " ("
                                           + tmpSb + ") VALUES (");
        StringBuffer typeQuerySb = new StringBuffer("SELECT " + tmpSb
            + " FROM " + tableName + " WHERE 1 = 2");

        try {
            ResultSetMetaData rsmd = curConn.createStatement().executeQuery(
                typeQuerySb.toString()).getMetaData();

            if (rsmd.getColumnCount() != autonulls.length) {
                throw new SqlToolError(rb.getString(
                        SqltoolRB.DSV_METADATA_MISMATCH));
                // Don't know if it's possible to get here.
                // If so, it's probably a SqlTool problem, not a user or
                // data problem.
                // Should be researched and either return a user-friendly
                // message or a RuntimeExceptin.
            }

            for (int i = 0; i < autonulls.length; i++) {
                autonulls[i] = true;
                parseDate[i] = false;
                parseBool[i] = false;
                switch(rsmd.getColumnType(i + 1)) {
                    case java.sql.Types.BOOLEAN:
                        parseBool[i] = true;
                        break;
                    case java.sql.Types.VARBINARY :
                    case java.sql.Types.VARCHAR :
                    case java.sql.Types.ARRAY :
                        // Guessing at how to handle ARRAY.
                    case java.sql.Types.BLOB :
                    case java.sql.Types.CLOB :
                    case java.sql.Types.LONGVARBINARY :
                    case java.sql.Types.LONGVARCHAR :
                        autonulls[i] = false;
                        // This means to preserve white space and to insert
                        // "" for "".  Otherwise we trim white space and
                        // insert null for \s*.
                        break;
                    case java.sql.Types.DATE:
                    case java.sql.Types.TIME:
                    case java.sql.Types.TIMESTAMP:
                        parseDate[i] = true;
                }
            }
        } catch (SQLException se) {
            throw new SqlToolError(rb.getString(
                    SqltoolRB.QUERY_METADATAFAIL,
                            typeQuerySb.toString()), se);
        }

        for (int i = 0; i < autonulls.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }

            sb.append('?');
        }

        // Initialize REJECT file(s)
        int rejectCount = 0;
        File rejectFile = null;
        File rejectReportFile = null;
        PrintWriter rejectWriter = null;
        PrintWriter rejectReportWriter = null;
        if (dsvRejectFile != null) try {
            rejectFile = new File(dsvRejectFile);
            rejectWriter = new PrintWriter((charset == null)
                    ? (new OutputStreamWriter(new FileOutputStream(rejectFile)))
                    : (new OutputStreamWriter(new FileOutputStream(rejectFile),
                            charset)));
                    // Replace with just "(new FileOutputStream(file), charset)"
                    // once use defaultCharset from Java 1.5 in charset init.
                    // above.
            rejectWriter.print(headerLine + dsvRowDelim);
        } catch (IOException ioe) {
            throw new SqlToolError(rb.getString(
                    SqltoolRB.DSV_REJECTFILE_SETUPFAIL, dsvRejectFile), ioe);
        }
        if (dsvRejectReport != null) try {
            rejectReportFile = new File(dsvRejectReport);
            rejectReportWriter = new PrintWriter((charset == null)
                    ? (new OutputStreamWriter(
                            new FileOutputStream(rejectReportFile)))
                    : (new OutputStreamWriter(
                            new FileOutputStream(rejectReportFile), charset)));
                    // Replace with just "(new FileOutputStream(file), charset)"
                    // once use defaultCharset from Java 1.5 in charset init.
                    // above.
            rejectReportWriter.println(rb.getString(
                    SqltoolRB.REJECTREPORT_TOP, new String[] {
                        (new java.util.Date()).toString(),
                        file.getPath(),
                        ((rejectFile == null) ? rb.getString(SqltoolRB.NONE)
                                        : rejectFile.getPath()),
                        ((rejectFile == null) ? null : rejectFile.getPath()),
                    }));
        } catch (IOException ioe) {
            throw new SqlToolError(rb.getString(
                    SqltoolRB.DSV_REJECTREPORT_SETUPFAIL, dsvRejectReport),
                            ioe);
        }

        int recCount = 0;
        int skipCount = 0;
        PreparedStatement ps = null;
        boolean importAborted = false;

        try {
            try {
                ps = curConn.prepareStatement(sb.toString() + ')');
            } catch (SQLException se) {
                throw new SqlToolError(rb.getString(
                        SqltoolRB.INSERTION_PREPAREFAIL, sb.toString()), se);
            }
            String[] dataVals = new String[autonulls.length];
            // Length is number of cols to insert INTO, not nec. # in DSV file.
            int      readColCount;
            int      storeColCount;
            String   currentFieldName = null;

            // Insert data rows 1-row-at-a-time
            while (true) try { try {
                recStart = recEnd + dsvRowDelim.length();

                if (recStart >= string.length()) {
                    break;
                }

                recEnd = string.indexOf(dsvRowDelim, recStart);
                lineCount++; // Increment when we have line start and end

                if (recEnd < 0) {
                    // Last record
                    recEnd = string.length();
                }
                trimmedLine = string.substring(recStart, recEnd).trim();
                if (trimmedLine.length() < 1) {
                    continue;  // Silently skip blank lines
                }
                if (skipPrefix != null
                        && trimmedLine.startsWith(skipPrefix)) {
                    skipCount++;
                    continue;
                }
                if (switching) {
                    if (trimmedLine.equals("}")) {
                        switching = false;
                        continue;
                    }
                    int colonAt = trimmedLine.indexOf(':');
                    if (colonAt < 1 || colonAt == trimmedLine.length() - 1) {
                        throw new SqlToolError(rb.getString(
                                SqltoolRB.DSV_HEADER_MATCHERNONHEAD,
                                        lineCount));
                    }
                    continue;
                }

                // Finally we will attempt to add a record!
                recCount++;
                // Remember that recCount counts both inserts + rejects

                colStart = recStart;
                colEnd   = -1;
                readColCount = 0;
                storeColCount = 0;

                while (true) {
                    if (colEnd == recEnd) {
                        // We processed final column last time through loop
                        break;
                    }

                    colEnd = string.indexOf(dsvColDelim, colStart);

                    if (colEnd < 0 || colEnd > recEnd) {
                        colEnd = recEnd;
                    }

                    if (readColCount == inputColHeadCount) {
                        throw new RowError(rb.getString(
                                SqltoolRB.DSV_COLCOUNT_MISMATCH,
                                        inputColHeadCount, 1 + readColCount));
                    }

                    if (headers[readColCount++] != null) {
                        dataVals[storeColCount++] =
                                string.substring(colStart, colEnd);
                    }
                    colStart             = colEnd + dsvColDelim.length();
                }
                if (readColCount < inputColHeadCount) {
                    throw new RowError(rb.getString(
                            SqltoolRB.DSV_COLCOUNT_MISMATCH,
                                    inputColHeadCount, readColCount));
                }
                /* Already checked for readColCount too high in prev. block */

                if (constColMap != null) {
                    Iterator it = constColMap.values().iterator();
                    while (it.hasNext()) {
                        dataVals[storeColCount++] = (String) it.next();
                    }
                }
                if (storeColCount != dataVals.length) {
                    throw new RowError(rb.getString(
                            SqltoolRB.DSV_INSERTCOL_MISMATCH,
                                    dataVals.length, storeColCount));
                }

                for (int i = 0; i < dataVals.length; i++) {
                    currentFieldName = insertFieldName[i];
                    if (autonulls[i]) dataVals[i] = dataVals[i].trim();
                    // N.b. WE SPECIFICALLY DO NOT HANDLE TIMES WITHOUT
                    // DATES, LIKE "3:14:00", BECAUSE, WHILE THIS MAY BE
                    // USEFUL AND EFFICIENT, IT IS NOT PORTABLE.
                    //System.err.println("ps.setString(" + i + ", "
                    //      + dataVals[i] + ')');

                    if (parseDate[i]) {
                        if ((dataVals[i].length() < 1 && autonulls[i])
                              || dataVals[i].equals(nullRepToken)) {
                            ps.setTimestamp(i + 1, null);
                        } else {
                            dateString = (dataVals[i].indexOf(':') > 0)
                                       ? dataVals[i]
                                       : (dataVals[i] + " 0:00:00");
                            // BEWARE:  This may not work for some foreign
                            // date/time formats.
                            try {
                                ps.setTimestamp(i + 1,
                                        java.sql.Timestamp.valueOf(dateString));
                            } catch (IllegalArgumentException iae) {
                                throw new RowError(rb.getString(
                                        SqltoolRB.TIME_BAD, dateString), iae);
                            }
                        }
                    } else if (parseBool[i]) {
                        if ((dataVals[i].length() < 1 && autonulls[i])
                              || dataVals[i].equals(nullRepToken)) {
                            ps.setNull(i + 1, java.sql.Types.BOOLEAN);
                        } else {
                            try {
                                ps.setBoolean(i + 1, Boolean.valueOf(
                                        dataVals[i]).booleanValue());
                                // Boolean... is equivalent to Java 4's
                                // Boolean.parseBoolean().
                            } catch (IllegalArgumentException iae) {
                                throw new RowError(rb.getString(
                                        SqltoolRB.BOOLEAN_BAD, dataVals[i]),
                                                iae);
                            }
                        }
                    } else {
                        ps.setString(
                            i + 1,
                            (((dataVals[i].length() < 1 && autonulls[i])
                              || dataVals[i].equals(nullRepToken))
                             ? null
                             : dataVals[i]));
                    }
                    currentFieldName = null;
                }

                retval = ps.executeUpdate();

                if (retval != 1) {
                    throw new RowError(rb.getString(
                            SqltoolRB.INPUTREC_MODIFIED, retval));
                }

                possiblyUncommitteds.set(true);
            } catch (SQLException se) {
                throw new RowError(null, se);
            } } catch (RowError re) {
                rejectCount++;
                if (rejectWriter != null || rejectReportWriter != null) {
                    if (rejectWriter != null) {
                        rejectWriter.print(string.substring(
                                recStart, recEnd) + dsvRowDelim);
                    }
                    if (rejectReportWriter != null) {
                        genRejectReportRecord(rejectReportWriter,
                                rejectCount, lineCount,
                                currentFieldName, re.getMessage(),
                                re.getCause());
                    }
                } else {
                    importAborted = true;
                    throw new SqlToolError(
                            rb.getString(SqltoolRB.DSV_RECIN_FAIL,
                                    lineCount, currentFieldName)
                            + ((re.getMessage() == null)
                                    ? "" : ("  " + re.getMessage())),
                            re.getCause());
                }
            }
        } finally {
            String summaryString = null;
            if (recCount > 0) {
                summaryString = rb.getString(SqltoolRB.DSV_IMPORT_SUMMARY,
                        new String[] {
                                ((skipPrefix == null)
                                          ? "" : ("'" + skipPrefix + "'-")),
                                Integer.toString(skipCount),
                                Integer.toString(rejectCount),
                                Integer.toString(recCount - rejectCount),
                                (importAborted ? "importAborted" : null)
                        });
                stdprintln(summaryString);
            }
            try {
                if (recCount > rejectCount && !curConn.getAutoCommit()) {
                    stdprintln(rb.getString(SqltoolRB.INSERTIONS_NOTCOMMITTED));
                }
            } catch (SQLException se) {
                stdprintln(rb.getString(SqltoolRB.AUTOCOMMIT_FETCHFAIL));
                stdprintln(rb.getString(SqltoolRB.INSERTIONS_NOTCOMMITTED));
                // No reason to throw here.  If use attempts to use the
                // connection for anything significant, we will throw then.
            }
            if (rejectWriter != null) {
                rejectWriter.flush();
                rejectWriter.close();
            }
            if (rejectReportWriter != null && rejectCount > 0) {
                rejectReportWriter.println(rb.getString(
                        SqltoolRB.REJECTREPORT_BOTTOM, summaryString, revnum));
                rejectReportWriter.flush();
                rejectReportWriter.close();
            }
            if (rejectCount == 0) {
                if (rejectFile != null && rejectFile.exists()
                        && !rejectFile.delete())
                    errprintln(rb.getString(SqltoolRB.DSV_REJECTFILE_PURGEFAIL,
                            rejectFile.toString()));
                if (rejectReportFile != null && !rejectReportFile.delete())
                    errprintln(rb.getString(
                            SqltoolRB.DSV_REJECTREPORT_PURGEFAIL,
                                    (rejectFile == null)
                                            ? null : rejectFile.toString()));
                // These are trivial errors.
            }
        }
    }

    public static void appendLine(StringBuffer sb, String s) {
        sb.append(s + LS);
    }

    /**
     * Does a poor-man's parse of a MSDOS command line and parses it
     * into a WIndows cmd.exe invocation to approximate.
     */
    static private String[] genWinArgs(String monolithic) {
        List list = new ArrayList();
        list.add("cmd.exe");
        list.add("/y");
        list.add("/c");
        Matcher m = wincmdPattern.matcher(monolithic);
        String[] internalTokens;
        while (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                if (m.group(i) == null) continue;
                if (m.group(i).length() > 1 && m.group(i).charAt(0) == '"') {
                    list.add(m.group(i).substring(1, m.group(i).length() - 1));
                    continue;
                }
                internalTokens = m.group(i).split("\\s+");
                for (int j = 0; j < internalTokens.length; j++)
                    list.add(internalTokens[j]);
            }
        }
        return (String[]) list.toArray(new String[] {});
    }

    private void genRejectReportRecord(PrintWriter pw, int rCount,
            int lCount, String field, String eMsg, Throwable cause) {
        pw.println(rb.getString(SqltoolRB.REJECTREPORT_ROW,
                new String[] {
                    ((rCount % 2 == 0) ? "even" : "odd") + "row",
                    Integer.toString(rCount),
                    Integer.toString(lCount),
                    ((field == null) ? "&nbsp;" : field),
                    (((eMsg == null) ? "" : eMsg)
                            + ((eMsg == null || cause == null) ? "" : "<HR/>")
                            + ((cause == null) ? "" : (
                                    (cause instanceof SQLException
                                            && cause.getMessage() != null)
                                        ? cause.getMessage()
                                        : cause.toString()
                                    )
                            )
                    )
                }));
    }
}
