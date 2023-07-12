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
 *    discriminator-value="DeviceresultStateAllocated"
 */
public class DeviceresultStateAllocated extends DeviceresultState {

    public static DeviceresultStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceresultStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceresultStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceresultStateAllocated();
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
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "79";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrdeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceresultState.mecrdeviceresult...passed");
    }

	
	
    public void mecrdeviceresult (org.hibernate.Session sess, Deviceresult object) throws org.hibernate.HibernateException {
        DeviceresultStateExists state = DeviceresultStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 75 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 84");
        object.setState(state);
    }

	
}
