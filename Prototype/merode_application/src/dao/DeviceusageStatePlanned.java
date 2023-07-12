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
 *    discriminator-value="DeviceusageStatePlanned"
 */
public class DeviceusageStatePlanned extends DeviceusageState {

    public static DeviceusageStatePlanned getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceusageStatePlanned state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "planned");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceusageStatePlanned)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceusageStatePlanned();
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
    	return "planned";
    }

    protected java.lang.String getStateId() {
    	return "253";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meenddeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceusageState.meenddeviceusage...passed");
    }

		
    public void check_mesetready() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceusageState.mesetready...passed");
    }

	
	
    public void meenddeviceusage (org.hibernate.Session sess, Deviceusage object) throws org.hibernate.HibernateException {
        DeviceusageStateEnded state = DeviceusageStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 210 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 241");
        object.setState(state);
    }

	
    public void mesetready (org.hibernate.Session sess, Deviceusage object) throws org.hibernate.HibernateException {
        DeviceusageStateReady state = DeviceusageStateReady.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 210 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 246");
        object.setState(state);
    }

	
}
