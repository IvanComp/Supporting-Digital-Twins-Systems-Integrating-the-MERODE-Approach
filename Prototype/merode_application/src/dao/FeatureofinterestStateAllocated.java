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
 *    discriminator-value="FeatureofinterestStateAllocated"
 */
public class FeatureofinterestStateAllocated extends FeatureofinterestState {

    public static FeatureofinterestStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    FeatureofinterestStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (FeatureofinterestStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new FeatureofinterestStateAllocated();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.FeatureofinterestState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "90";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrfeatureofinterest() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking FeatureofinterestState.mecrfeatureofinterest...passed");
    }

	
	
    public void mecrfeatureofinterest (org.hibernate.Session sess, Featureofinterest object) throws org.hibernate.HibernateException {
        FeatureofinterestStateExists state = FeatureofinterestStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 88 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 95");
        object.setState(state);
    }

	
}
