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
 *    discriminator-value="PropertyStateAllocated"
 */
public class PropertyStateAllocated extends PropertyState {

    public static PropertyStateAllocated getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    PropertyStateAllocated state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "allocated");
	    if ( states != null && !states.isEmpty() )
	        state = (PropertyStateAllocated)states.iterator().next();
	    if ( state == null ) {
	        state = new PropertyStateAllocated();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.PropertyState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "allocated";
    }

    protected java.lang.String getStateId() {
    	return "186";
    }

    public boolean isInitialState() {
    	return true;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_mecrproperty() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking PropertyState.mecrproperty...passed");
    }

	
	
    public void mecrproperty (org.hibernate.Session sess, Property object) throws org.hibernate.HibernateException {
        PropertyStateExists state = PropertyStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 184 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 191");
        object.setState(state);
    }

	
}
