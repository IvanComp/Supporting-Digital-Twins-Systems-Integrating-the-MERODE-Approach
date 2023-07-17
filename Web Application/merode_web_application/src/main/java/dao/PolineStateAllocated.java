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
 *    discriminator-value="PolineStateAllocated"
 */
public class PolineStateAllocated extends PolineState {

    public static PolineStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PolineStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (PolineStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new PolineStateAllocated();
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
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "36";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PolineState.mecrpoline...passed");
    }

	
	
    public void mecrpoline (org.hibernate.Session sess, Poline object) throws org.hibernate.HibernateException {
        PolineStateExists state = PolineStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 34 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 41");
        object.setState(state);
    }

	
}
