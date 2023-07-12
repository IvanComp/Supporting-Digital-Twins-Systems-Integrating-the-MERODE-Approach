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
 *    discriminator-value="ProcedureStateAllocated"
 */
public class ProcedureStateAllocated extends ProcedureState {

    public static ProcedureStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    ProcedureStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (ProcedureStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new ProcedureStateAllocated();
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
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "51";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrprocedure() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProcedureState.mecrprocedure...passed");
    }

	
	
    public void mecrprocedure (org.hibernate.Session sess, Procedure object) throws org.hibernate.HibernateException {
        ProcedureStateExists state = ProcedureStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 49 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 56");
        object.setState(state);
    }

	
}
