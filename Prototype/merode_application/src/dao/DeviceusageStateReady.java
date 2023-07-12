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
 *    discriminator-value="DeviceusageStateReady"
 */
public class DeviceusageStateReady extends DeviceusageState {

    public static DeviceusageStateReady getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceusageStateReady state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "ready");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceusageStateReady)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceusageStateReady();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.DeviceusageState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "ready";
    }

    protected java.lang.String getStateId() {
    	return "256";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_devicedeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceusageState.devicedeployment...passed");
    }

	
	
    public void devicedeployment (org.hibernate.Session sess, Deviceusage object) throws org.hibernate.HibernateException {
        DeviceusageStateStarted state = DeviceusageStateStarted.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 210 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 244");
        object.setState(state);
    }

	
}
