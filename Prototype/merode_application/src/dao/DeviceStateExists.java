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
 *    discriminator-value="DeviceStateExists"
 */
public class DeviceStateExists extends DeviceState {

    public static DeviceStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    DeviceStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (DeviceStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new DeviceStateExists();
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
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "299";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meenddevice() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.meenddevice...passed");
    }

		
    public void check_devicedeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.devicedeployment...passed");
    }

		
    public void check_deviceundeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.deviceundeployment...passed");
    }

		
    public void check_mecrdeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.mecrdeviceresult...passed");
    }

		
    public void check_mecrdeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.mecrdeviceusage...passed");
    }

		
    public void check_mecrprocedure() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.mecrprocedure...passed");
    }

		
    public void check_mecrregistereddevice() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.mecrregistereddevice...passed");
    }

		
    public void check_meenddeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.meenddeviceresult...passed");
    }

		
    public void check_meenddeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.meenddeviceusage...passed");
    }

		
    public void check_meendprocedure() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.meendprocedure...passed");
    }

		
    public void check_meendregistereddevice() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.meendregistereddevice...passed");
    }

		
    public void check_mesetready() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking DeviceState.mesetready...passed");
    }

	
	
    public void meenddevice (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateEnded state = DeviceStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 306");
        object.setState(state);
    }

	
    public void devicedeployment (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 309");
        object.setState(state);
    }

	
    public void deviceundeployment (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 313");
        object.setState(state);
    }

	
    public void mecrdeviceresult (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 317");
        object.setState(state);
    }

	
    public void mecrdeviceusage (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 321");
        object.setState(state);
    }

	
    public void mecrprocedure (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 325");
        object.setState(state);
    }

	
    public void mecrregistereddevice (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 327");
        object.setState(state);
    }

	
    public void meenddeviceresult (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 329");
        object.setState(state);
    }

	
    public void meenddeviceusage (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 333");
        object.setState(state);
    }

	
    public void meendprocedure (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 337");
        object.setState(state);
    }

	
    public void meendregistereddevice (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 339");
        object.setState(state);
    }

	
    public void mesetready (org.hibernate.Session sess, Device object) throws org.hibernate.HibernateException {
        DeviceStateExists state = DeviceStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 296 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 341");
        object.setState(state);
    }

	
}
