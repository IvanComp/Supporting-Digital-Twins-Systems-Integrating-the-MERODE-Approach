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
 *    discriminator-value="PolineStateExists"
 */
public class PolineStateExists extends PolineState {

    public static PolineStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PolineStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (PolineStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new PolineStateExists();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.PolineState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "37";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meendpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PolineState.meendpoline...passed");
    }

	
	
    public void meendpoline (org.hibernate.Session sess, Poline object) throws org.hibernate.HibernateException {
        PolineStateEnded state = PolineStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 34 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 44");
        object.setState(state);
    }

	
}
