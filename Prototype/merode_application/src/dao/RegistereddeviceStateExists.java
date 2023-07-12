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
 *    discriminator-value="RegistereddeviceStateExists"
 */
public class RegistereddeviceStateExists extends RegistereddeviceState {

    public static RegistereddeviceStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    RegistereddeviceStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (RegistereddeviceStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new RegistereddeviceStateExists();
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
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "31";
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

		
    public void check_mecrdeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.mecrdeviceusage...passed");
    }

		
    public void check_meenddeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.meenddeviceusage...passed");
    }

		
    public void check_mesetready() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.mesetready...passed");
    }

		
    public void check_devicedeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking RegistereddeviceState.devicedeployment...passed");
    }

	
	
    public void meendregistereddevice (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateEnded state = RegistereddeviceStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 11");
        object.setState(state);
    }

	
    public void mecrdeviceusage (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateExists state = RegistereddeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 20");
        object.setState(state);
    }

	
    public void meenddeviceusage (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateExists state = RegistereddeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 24");
        object.setState(state);
    }

	
    public void mesetready (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateExists state = RegistereddeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 26");
        object.setState(state);
    }

	
    public void devicedeployment (org.hibernate.Session sess, Registereddevice object) throws org.hibernate.HibernateException {
        RegistereddeviceStateDeployed state = RegistereddeviceStateDeployed.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 1 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 14");
        object.setState(state);
    }

	
}
