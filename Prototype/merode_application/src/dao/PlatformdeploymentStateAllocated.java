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
 *    discriminator-value="PlatformdeploymentStateAllocated"
 */
public class PlatformdeploymentStateAllocated extends PlatformdeploymentState {

    public static PlatformdeploymentStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PlatformdeploymentStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (PlatformdeploymentStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new PlatformdeploymentStateAllocated();
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
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "272";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrplatformdeployment() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PlatformdeploymentState.mecrplatformdeployment...passed");
    }

	
	
    public void mecrplatformdeployment (org.hibernate.Session sess, Platformdeployment object) throws org.hibernate.HibernateException {
        PlatformdeploymentStateExists state = PlatformdeploymentStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 270 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 277");
        object.setState(state);
    }

	
}
