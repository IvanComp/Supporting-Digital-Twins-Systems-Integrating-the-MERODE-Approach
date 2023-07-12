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
 *    discriminator-value="PlatformdeploymentStateExists"
 */
public class PlatformdeploymentStateExists extends PlatformdeploymentState {

    public static PlatformdeploymentStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PlatformdeploymentStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (PlatformdeploymentStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new PlatformdeploymentStateExists();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.PlatformdeploymentState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "273";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meendplatformdeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.meendplatformdeployment...passed");
    }

		
    public void check_devicedeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.devicedeployment...passed");
    }

		
    public void check_deviceundeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.deviceundeployment...passed");
    }

		
    public void check_mecrdeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.mecrdeviceresult...passed");
    }

		
    public void check_mecrdeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.mecrdeviceusage...passed");
    }

		
    public void check_meenddeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.meenddeviceresult...passed");
    }

		
    public void check_meenddeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.meenddeviceusage...passed");
    }

		
    public void check_mesetready() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.mesetready...passed");
    }

	
	
    public void meendplatformdeployment (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateEnded state = PlatformdeploymentStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 280");
        object.setState(state);
    }

	
    public void devicedeployment (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateExists state = PlatformdeploymentStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 283");
        object.setState(state);
    }

	
    public void deviceundeployment (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateExists state = PlatformdeploymentStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 285");
        object.setState(state);
    }

	
    public void mecrdeviceresult (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateExists state = PlatformdeploymentStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 287");
        object.setState(state);
    }

	
    public void mecrdeviceusage (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateExists state = PlatformdeploymentStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 289");
        object.setState(state);
    }

	
    public void meenddeviceresult (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateExists state = PlatformdeploymentStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 291");
        object.setState(state);
    }

	
    public void meenddeviceusage (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateExists state = PlatformdeploymentStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 293");
        object.setState(state);
    }

	
    public void mesetready (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateExists state = PlatformdeploymentStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 295");
        object.setState(state);
    }

	
}
