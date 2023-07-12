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
 *    discriminator-value="DeviceusageStateAllocated"
 */
public class DeviceusageStateAllocated extends DeviceusageState {

    public static DeviceusageStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceusageStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceusageStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceusageStateAllocated();
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
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "252";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrdeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceusageState.mecrdeviceusage...passed");
    }

	
	
    public void mecrdeviceusage (org.hibernate.Session sess, Deviceusage object) throws org.hibernate.HibernateException {
        DeviceusageStatePlanned state = DeviceusageStatePlanned.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 210 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 236");
        object.setState(state);
    }

	
}
