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
 *    discriminator-value="PolineStateEnded"
 */
public class PolineStateEnded extends PolineState {

    public static PolineStateEnded getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PolineStateEnded state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "ended");
	    if ( states != null && !states.isEmpty() )
	        state = (PolineStateEnded)states.iterator().next();
	    if ( state == null ) {
	        state = new PolineStateEnded();
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
    	return "ended";
    }

    protected java.lang.String getStateId() {
    	return "38";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return true;
    }




}
