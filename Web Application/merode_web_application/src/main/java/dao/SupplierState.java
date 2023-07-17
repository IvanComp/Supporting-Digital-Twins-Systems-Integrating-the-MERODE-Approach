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
 *     table="HIB_SUPPLIER_STATE"
 * @hibernate.discriminator
 *     column="class"
 */
public abstract class SupplierState 
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
    public static void setInitialState (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateAllocated state = SupplierStateAllocated.getObject(sess);
        object.setState(state);
    }
    // Control operations to check whether an event may occur.
    // By default an exception is thrown; it is taken away in
    // a subclass for all appropriate methods.
    public void check_mecrsupplier() throws MerodeException {
        MerodeLogger.logln ("Checking SupplierState.mecrsupplier...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 101 with name MEcrSupplier for object type 65 named Supplier in state " + getStateId() + " called " + getStateName());
    }
    public void check_meendsupplier() throws MerodeException {
        MerodeLogger.logln ("Checking SupplierState.meendsupplier...");
        throw new MerodeException ("[" + Instant.now() + "]" + " Can't execute event 102 with name MEendSupplier for object type 65 named Supplier in state " + getStateId() + " called " + getStateName());
    }
    public void check_mecrpoline() throws MerodeException {
        MerodeLogger.logln ("Checking SupplierState.mecrpoline...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 97 with name MEcrPOLine for object type 65 named Supplier in state " + getStateId() + " called " + getStateName());
    }
    public void check_meendpoline() throws MerodeException {
        MerodeLogger.logln ("Checking SupplierState.meendpoline...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 98 with name MEendPOLine for object type 65 named Supplier in state " + getStateId() + " called " + getStateName());
    }
    public void check_mecrsuppliedproduct() throws MerodeException {
        MerodeLogger.logln ("Checking SupplierState.mecrsuppliedproduct...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 93 with name MEcrSuppliedProduct for object type 65 named Supplier in state " + getStateId() + " called " + getStateName());
    }
    public void check_meendsuppliedproduct() throws MerodeException {
        MerodeLogger.logln ("Checking SupplierState.meendsuppliedproduct...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 94 with name MEendSuppliedProduct for object type 65 named Supplier in state " + getStateId() + " called " + getStateName());
    }
    public void check_mecrpurchaseorder() throws MerodeException {
        MerodeLogger.logln ("Checking SupplierState.mecrpurchaseorder...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 95 with name MEcrPurchaseOrder for object type 65 named Supplier in state " + getStateId() + " called " + getStateName());
    }
    public void check_meendpurchaseorder() throws MerodeException {
        MerodeLogger.logln ("Checking SupplierState.meendpurchaseorder...");
        throw new MerodeException("[" + Instant.now() + "]" + " Can't execute event 96 with name MEendPurchaseOrder for object type 65 named Supplier in state " + getStateId() + " called " + getStateName());
    }

    // Put the object in the correct state.
    public void mecrsupplier (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException{
	}
    public void meendsupplier (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException{
    }
    public void mecrpoline (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException{
    }
    public void meendpoline (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException{
    }
    public void mecrsuppliedproduct (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException{
    }
    public void meendsuppliedproduct (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException{
    }
    public void mecrpurchaseorder (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException{
    }
    public void meendpurchaseorder (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException{
    }


}
