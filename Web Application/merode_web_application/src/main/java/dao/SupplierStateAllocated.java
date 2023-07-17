/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */
package dao;
import java.time.*;


/**
 * @hibernate.subclass
 *    discriminator-value="SupplierStateAllocated"
 */
public class SupplierStateAllocated extends SupplierState {

    public static SupplierStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    SupplierStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (SupplierStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new SupplierStateAllocated();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.SupplierState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "67";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrsupplier() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SupplierState.mecrsupplier...passed");
    }

	
	
    public void mecrsupplier (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateExists state = SupplierStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 65 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 72");
        object.setState(state);
    }

	
}
