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
 *    discriminator-value="DeviceStateAllocated"
 */
public class DeviceStateAllocated extends DeviceState {

    public static DeviceStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceStateAllocated();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.DeviceState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "298";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrdevice() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.mecrdevice...passed");
    }

	
	
    public void mecrdevice (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 303");
        object.setState(state);
    }

	
}
