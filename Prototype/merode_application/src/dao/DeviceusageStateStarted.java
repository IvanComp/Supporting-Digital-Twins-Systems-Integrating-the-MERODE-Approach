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
 *    discriminator-value="DeviceusageStateStarted"
 */
public class DeviceusageStateStarted extends DeviceusageState {

    public static DeviceusageStateStarted getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceusageStateStarted state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "started");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceusageStateStarted)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceusageStateStarted();
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
    	return "started";
    }

    protected java.lang.String getStateId() {
    	return "255";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_deviceundeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceusageState.deviceundeployment...passed");
    }

		
    public void check_mecrdeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceusageState.mecrdeviceresult...passed");
    }

		
    public void check_meenddeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceusageState.meenddeviceresult...passed");
    }

	
	
    public void deviceundeployment (org.hibernate.Session sess, Deviceusage object) throws org.hibernate.HibernateException {
        DeviceusageStateEnded state = DeviceusageStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 210 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 239");
        object.setState(state);
    }

	
    public void mecrdeviceresult (org.hibernate.Session sess, Deviceusage object) throws org.hibernate.HibernateException {
        DeviceusageStateStarted state = DeviceusageStateStarted.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 210 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 248");
        object.setState(state);
    }

	
    public void meenddeviceresult (org.hibernate.Session sess, Deviceusage object) throws org.hibernate.HibernateException {
        DeviceusageStateStarted state = DeviceusageStateStarted.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 210 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 250");
        object.setState(state);
    }

	
}
