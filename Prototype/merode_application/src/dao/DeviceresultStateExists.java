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
 *    discriminator-value="DeviceresultStateExists"
 */
public class DeviceresultStateExists extends DeviceresultState {

    public static DeviceresultStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceresultStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceresultStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceresultStateExists();
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
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "80";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meenddeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceresultState.meenddeviceresult...passed");
    }

	
	
    public void meenddeviceresult (org.hibernate.Session sess, Deviceresult object) throws org.hibernate.HibernateException {
        DeviceresultStateEnded state = DeviceresultStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 75 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 87");
        object.setState(state);
    }

	
}
