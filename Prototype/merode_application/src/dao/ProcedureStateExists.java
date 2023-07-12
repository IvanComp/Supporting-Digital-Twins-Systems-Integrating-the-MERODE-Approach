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
 *    discriminator-value="ProcedureStateExists"
 */
public class ProcedureStateExists extends ProcedureState {

    public static ProcedureStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    ProcedureStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (ProcedureStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new ProcedureStateExists();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.ProcedureState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "52";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meendprocedure() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.meendprocedure...passed");
    }

		
    public void check_devicedeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.devicedeployment...passed");
    }

		
    public void check_deviceundeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.deviceundeployment...passed");
    }

		
    public void check_mecrdeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.mecrdeviceresult...passed");
    }

		
    public void check_mecrdeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.mecrdeviceusage...passed");
    }

		
    public void check_meenddeviceresult() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.meenddeviceresult...passed");
    }

		
    public void check_meenddeviceusage() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.meenddeviceusage...passed");
    }

		
    public void check_mesetready() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.mesetready...passed");
    }

	
	
    public void meendprocedure (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateEnded state = ProcedureStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 59");
        object.setState(state);
    }

	
    public void devicedeployment (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateExists state = ProcedureStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 62");
        object.setState(state);
    }

	
    public void deviceundeployment (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateExists state = ProcedureStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 64");
        object.setState(state);
    }

	
    public void mecrdeviceresult (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateExists state = ProcedureStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 66");
        object.setState(state);
    }

	
    public void mecrdeviceusage (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateExists state = ProcedureStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 68");
        object.setState(state);
    }

	
    public void meenddeviceresult (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateExists state = ProcedureStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 70");
        object.setState(state);
    }

	
    public void meenddeviceusage (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateExists state = ProcedureStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 72");
        object.setState(state);
    }

	
    public void mesetready (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateExists state = ProcedureStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 74");
        object.setState(state);
    }

	
}
