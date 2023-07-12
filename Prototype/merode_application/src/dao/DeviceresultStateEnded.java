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
 *    discriminator-value="DeviceresultStateEnded"
 */
public class DeviceresultStateEnded extends DeviceresultState {

    public static DeviceresultStateEnded getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceresultStateEnded state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "ended");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceresultStateEnded)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceresultStateEnded();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.DeviceresultState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "ended";
    }

    protected java.lang.String getStateId() {
    	return "81";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return true;
    }




}
