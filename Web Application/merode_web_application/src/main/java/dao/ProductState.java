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
 *     table="HIB_PRODUCT_STATE"
 * @hibernate.discriminator
 *     column="class"
 */
public abstract class ProductState 
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
    public static void setInitialState (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException {
        ProductStateAllocated state = ProductStateAllocated.getObject(sess);
        object.setState(state);
    }
    // Control operations to check whether an event may occur.
    // By default an exception is thrown; it is taken away in
    // a subclass for all appropriate methods.
    public void check_mecrproduct() throws MerodeException {
        MerodeLogger.logln ("Checking ProductState.mecrproduct...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 99 with name MEcrProduct for object type 45 named Product in state " + getStateId() + " called " + getStateName());
    }
    public void check_meendproduct() throws MerodeException {
        MerodeLogger.logln ("Checking ProductState.meendproduct...");
        throw new MerodeException ("[" + Instant.now() + "]" + " Can't execute event 100 with name MEendProduct for object type 45 named Product in state " + getStateId() + " called " + getStateName());
    }
    public void check_mecrpoline() throws MerodeException {
        MerodeLogger.logln ("Checking ProductState.mecrpoline...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 97 with name MEcrPOLine for object type 45 named Product in state " + getStateId() + " called " + getStateName());
    }
    public void check_meendpoline() throws MerodeException {
        MerodeLogger.logln ("Checking ProductState.meendpoline...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 98 with name MEendPOLine for object type 45 named Product in state " + getStateId() + " called " + getStateName());
    }
    public void check_mecrsuppliedproduct() throws MerodeException {
        MerodeLogger.logln ("Checking ProductState.mecrsuppliedproduct...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 93 with name MEcrSuppliedProduct for object type 45 named Product in state " + getStateId() + " called " + getStateName());
    }
    public void check_meendsuppliedproduct() throws MerodeException {
        MerodeLogger.logln ("Checking ProductState.meendsuppliedproduct...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 94 with name MEendSuppliedProduct for object type 45 named Product in state " + getStateId() + " called " + getStateName());
    }

    // Put the object in the correct state.
    public void mecrproduct (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException{
	}
    public void meendproduct (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException{
    }
    public void mecrpoline (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException{
    }
    public void meendpoline (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException{
    }
    public void mecrsuppliedproduct (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException{
    }
    public void meendsuppliedproduct (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException{
    }


}
