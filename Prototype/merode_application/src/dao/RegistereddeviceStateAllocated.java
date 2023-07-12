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
 *    discriminator-value="RegistereddeviceStateAllocated"
 */
public class RegistereddeviceStateAllocated extends RegistereddeviceState {

    public static RegistereddeviceStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    RegistereddeviceStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (RegistereddeviceStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new RegistereddeviceStateAllocated();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.RegistereddeviceState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "28";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrregistereddevice() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.mecrregistereddevice...passed");
    }

	
	
    public void mecrregistereddevice (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateExists state = RegistereddeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 8");
        object.setState(state);
    }

	
}
