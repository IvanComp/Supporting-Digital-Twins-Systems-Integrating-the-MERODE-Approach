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
 *    discriminator-value="SuppliedproductStateEnded"
 */
public class SuppliedproductStateEnded extends SuppliedproductState {

    public static SuppliedproductStateEnded getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    SuppliedproductStateEnded state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "ended");
	    if ( states != null && !states.isEmpty() )
	        state = (SuppliedproductStateEnded)states.iterator().next();
	    if ( state == null ) {
	        state = new SuppliedproductStateEnded();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.SuppliedproductState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "ended";
    }

    protected java.lang.String getStateId() {
    	return "6";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return true;
    }




}
