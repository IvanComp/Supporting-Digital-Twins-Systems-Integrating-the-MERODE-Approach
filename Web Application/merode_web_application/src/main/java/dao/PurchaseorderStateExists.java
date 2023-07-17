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
 *    discriminator-value="PurchaseorderStateExists"
 */
public class PurchaseorderStateExists extends PurchaseorderState {

    public static PurchaseorderStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PurchaseorderStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (PurchaseorderStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new PurchaseorderStateExists();
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
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "21";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meendpurchaseorder() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PurchaseorderState.meendpurchaseorder...passed");
    }

		
    public void check_mecrpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PurchaseorderState.mecrpoline...passed");
    }

		
    public void check_meendpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PurchaseorderState.meendpoline...passed");
    }

	
	
    public void meendpurchaseorder (org.hibernate.Session sess, Purchaseorder object) throws org.hibernate.HibernateException {
        PurchaseorderStateEnded state = PurchaseorderStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 18 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 28");
        object.setState(state);
    }

	
    public void mecrpoline (org.hibernate.Session sess, Purchaseorder object) throws org.hibernate.HibernateException {
        PurchaseorderStateExists state = PurchaseorderStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 18 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 31");
        object.setState(state);
    }

	
    public void meendpoline (org.hibernate.Session sess, Purchaseorder object) throws org.hibernate.HibernateException {
        PurchaseorderStateExists state = PurchaseorderStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 18 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 33");
        object.setState(state);
    }

	
}
