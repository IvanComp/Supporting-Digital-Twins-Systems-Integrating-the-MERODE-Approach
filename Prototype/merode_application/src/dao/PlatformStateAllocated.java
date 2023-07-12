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
 *    discriminator-value="PlatformStateAllocated"
 */
public class PlatformStateAllocated extends PlatformState {

    public static PlatformStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PlatformStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (PlatformStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new PlatformStateAllocated();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.PlatformState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "138";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrplatform() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformState.mecrplatform...passed");
    }

	
	
    public void mecrplatform (org.hibernate.Session sess, Platform object) throws org.hibernate.HibernateException {
        PlatformStateExists state = PlatformStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 136 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 143");
        object.setState(state);
    }

	
}
