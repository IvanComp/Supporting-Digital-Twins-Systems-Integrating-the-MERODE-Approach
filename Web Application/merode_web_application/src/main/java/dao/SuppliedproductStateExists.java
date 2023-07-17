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
 *    discriminator-value="SuppliedproductStateExists"
 */
public class SuppliedproductStateExists extends SuppliedproductState {

    public static SuppliedproductStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    SuppliedproductStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (SuppliedproductStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new SuppliedproductStateExists();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.SuppliedproductState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "5";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meendsuppliedproduct() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SuppliedproductState.meendsuppliedproduct...passed");
    }

		
    public void check_mecrpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SuppliedproductState.mecrpoline...passed");
    }

		
    public void check_meendpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SuppliedproductState.meendpoline...passed");
    }

	
	
    public void meendsuppliedproduct (org.hibernate.Session sess, Suppliedproduct object) throws org.hibernate.HibernateException {
        SuppliedproductStateEnded state = SuppliedproductStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 12");
        object.setState(state);
    }

	
    public void mecrpoline (org.hibernate.Session sess, Suppliedproduct object) throws org.hibernate.HibernateException {
        SuppliedproductStateExists state = SuppliedproductStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 15");
        object.setState(state);
    }

	
    public void meendpoline (org.hibernate.Session sess, Suppliedproduct object) throws org.hibernate.HibernateException {
        SuppliedproductStateExists state = SuppliedproductStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 17");
        object.setState(state);
    }

	
}
