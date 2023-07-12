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
 *    discriminator-value="RegistereddeviceStateDeployed"
 */
public class RegistereddeviceStateDeployed extends RegistereddeviceState {

    public static RegistereddeviceStateDeployed getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    RegistereddeviceStateDeployed state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "deployed");
	    if ( states != null && !states.isEmpty() )
	        state = (RegistereddeviceStateDeployed)states.iterator().next();
	    if ( state == null ) {
	        state = new RegistereddeviceStateDeployed();
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
    	return "deployed";
    }

    protected java.lang.String getStateId() {
    	return "30";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meendregistereddevice() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.meendregistereddevice...passed");
    }

		
    public void check_deviceundeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.deviceundeployment...passed");
    }

		
    public void check_mecrdeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.mecrdeviceresult...passed");
    }

		
    public void check_meenddeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.meenddeviceresult...passed");
    }

	
	
    public void meendregistereddevice (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateEnded state = RegistereddeviceStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 11");
        object.setState(state);
    }

	
    public void deviceundeployment (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateExists state = RegistereddeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 16");
        object.setState(state);
    }

	
    public void mecrdeviceresult (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateDeployed state = RegistereddeviceStateDeployed.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 18");
        object.setState(state);
    }

	
    public void meenddeviceresult (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateDeployed state = RegistereddeviceStateDeployed.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 22");
        object.setState(state);
    }

	
}
