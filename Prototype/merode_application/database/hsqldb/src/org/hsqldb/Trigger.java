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

// fredt@users 20030727 - signature altered to support update triggers
/*

Contents of row1[] and row2[] in each type of trigger.

BEFORE INSERT
 - row1[] contains single String object = "Statement-level".

AFTER INSERT
 - row1[] contains single String object = "Statement-level".

BEFORE UPDATE
 - row1[] contains single String object = "Statement-level".

AFTER UPDATE
 - row1[] contains single String object = "Statement-level".

BEFORE DELETE
 - row1[] contains single String object = "Statement-level".

AFTER DELETE
 - row1[] contains single String object = "Statement-level".

BEFORE INSERT FOR EACH ROW
 - row2[] contains data about to be inserted and this can
be modified within the trigger such that modified data gets written to the
database.

AFTER INSERT FOR EACH ROW
 - row2[] contains data just inserted into the table.

BEFORE UPDATE FOR EACH ROW
 - row1[] contains currently stored data and not the data that is about to be
updated.

 - row2[] contains the data that is about to be updated.

AFTER UPDATE FOR EACH ROW
 - row1[] contains old stored data.
 - row2[] contains the new data.

BEFORE DELETE FOR EACH ROW
 - row1[] contains row data about to be deleted.

AFTER DELETE FOR EACH ROW
 - row1[] contains row data that has been deleted.

List compiled by Andrew Knight (quozzbat@users)
*/

/**
 * The interface an HSQLDB TRIGGER must implement. The user-supplied class that
 * implements this must have a default constructor.
 *
 * @author Peter Hudson
 * @version 1.7.2
 * @since 1.7.0
 */
public interface Trigger {

    // indexes into the triggers list
    int INSERT_AFTER      = 0;
    int DELETE_AFTER      = 1;
    int UPDATE_AFTER      = 2;
    int INSERT_BEFORE     = INSERT_AFTER + TriggerDef.NUM_TRIGGER_OPS;
    int DELETE_BEFORE     = DELETE_AFTER + TriggerDef.NUM_TRIGGER_OPS;
    int UPDATE_BEFORE     = UPDATE_AFTER + TriggerDef.NUM_TRIGGER_OPS;
    int INSERT_AFTER_ROW  = INSERT_AFTER + 2 * TriggerDef.NUM_TRIGGER_OPS;
    int DELETE_AFTER_ROW  = DELETE_AFTER + 2 * TriggerDef.NUM_TRIGGER_OPS;
    int UPDATE_AFTER_ROW  = UPDATE_AFTER + 2 * TriggerDef.NUM_TRIGGER_OPS;
    int INSERT_BEFORE_ROW = INSERT_BEFORE + 2 * TriggerDef.NUM_TRIGGER_OPS;
    int DELETE_BEFORE_ROW = DELETE_BEFORE + 2 * TriggerDef.NUM_TRIGGER_OPS;
    int UPDATE_BEFORE_ROW = UPDATE_BEFORE + 2 * TriggerDef.NUM_TRIGGER_OPS;

    /**
     * The method invoked upon each triggered action. <p>
     *
     * When UPDATE triggers are fired, oldRow contains the
     * existing values of the table row and newRow contains the
     * new values.<p>
     *
     * For INSERT triggers, oldRow is null and newRow contains the
     * table row to be inserted.
     *
     * For DELETE triggers, newRow is null and oldRow contains the
     * table row to be deleted.
     *
     * type contains the integer index id for trigger type, e.g.
     * TriggerDef.INSERT_AFTER (fredt@users)
     *
     * @param trigName the name of the trigger
     * @param tabName the name of the table upon which the
     *      triggered action is occuring
     * @param oldRow the old row
     * @param newRow the new row
     */
    void fire(int type, String trigName, String tabName, Object[] oldRow,
              Object[] newRow);
}
