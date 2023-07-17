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
 *    discriminator-value="PurchaseorderStateAllocated"
 */
public class PurchaseorderStateAllocated extends PurchaseorderState {

    public static PurchaseorderStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PurchaseorderStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (PurchaseorderStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new PurchaseorderStateAllocated();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.PurchaseorderState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "20";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrpurchaseorder() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PurchaseorderState.mecrpurchaseorder...passed");
    }

	
	
    public void mecrpurchaseorder (org.hibernate.Session sess, Purchaseorder object) throws org.hibernate.HibernateException {
        PurchaseorderStateExists state = PurchaseorderStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 18 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 25");
        object.setState(state);
    }

	
}
