/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */

package dao;
import java.time.*;

import validation.ErrorValidator;
/**
 * @hibernate.class
 *     table="HIB_POLINE_STATE"
 * @hibernate.discriminator
 *     column="class"
 */
public abstract class PolineState 
	implements java.io.Serializable {

    /**
     * @hibernate.id
     *     generator-class="uuid.hex"
     *     column="ID"
     *     type="java.lang.String"
     *
     * @hibernate.column
     *     name="ID"
     *     sql-type="VARCHAR(256)"
     */
    public java.lang.String getId()
    {
        return this.id;
    }

    public void setId(java.lang.String id)
    {
        this.id = id;
    }

    private java.lang.String id;

    /**
     * @hibernate.property
     *     column="NAME"
     *     type="java.lang.String"
     *
     * @hibernate.column
     *     name="NAME"
     *     sql-type="VARCHAR(256)"
     */
    public java.lang.String getName()
    {
        return getStateName();
    }

    protected abstract java.lang.String getStateId();

    public void setName(java.lang.String name)
    {
    // do nothing
    }

    protected abstract java.lang.String getStateName();
    
    public abstract boolean isInitialState();
    public abstract boolean isFinalState();

    // Put the object in the "allocated" state, i.e. the actual initial state
    public static void setInitialState (org.hibernate.Session sess, Poline object) throws org.hibernate.HibernateException {
        PolineStateAllocated state = PolineStateAllocated.getObject(sess);
        object.setState(state);
    }
    // Control operations to check whether an event may occur.
    // By default an exception is thrown; it is taken away in
    // a subclass for all appropriate methods.
    public void check_mecrpoline() throws MerodeException {
        MerodeLogger.logln ("Checking PolineState.mecrpoline...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 97 with name MEcrPOLine for object type 34 named Poline in state " + getStateId() + " called " + getStateName());
    }
    public void check_meendpoline() throws MerodeException {
        MerodeLogger.logln ("Checking PolineState.meendpoline...");
        throw new MerodeException ("[" + Instant.now() + "]" + " Can't execute event 98 with name MEendPOLine for object type 34 named Poline in state " + getStateId() + " called " + getStateName());
    }

    // Put the object in the correct state.
    public void mecrpoline (org.hibernate.Session sess, Poline object) throws org.hibernate.HibernateException{
	}
    public void meendpoline (org.hibernate.Session sess, Poline object) throws org.hibernate.HibernateException{
    }


}
