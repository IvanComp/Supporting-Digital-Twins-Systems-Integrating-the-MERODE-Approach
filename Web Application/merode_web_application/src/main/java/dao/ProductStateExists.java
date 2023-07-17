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
 *    discriminator-value="ProductStateExists"
 */
public class ProductStateExists extends ProductState {

    public static ProductStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    ProductStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (ProductStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new ProductStateExists();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.ProductState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "48";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meendproduct() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProductState.meendproduct...passed");
    }

		
    public void check_mecrpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProductState.mecrpoline...passed");
    }

		
    public void check_mecrsuppliedproduct() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProductState.mecrsuppliedproduct...passed");
    }

		
    public void check_meendpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProductState.meendpoline...passed");
    }

		
    public void check_meendsuppliedproduct() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking ProductState.meendsuppliedproduct...passed");
    }

	
	
    public void meendproduct (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException {
        ProductStateEnded state = ProductStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 45 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 55");
        object.setState(state);
    }

	
    public void mecrpoline (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException {
        ProductStateExists state = ProductStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 45 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 58");
        object.setState(state);
    }

	
    public void mecrsuppliedproduct (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException {
        ProductStateExists state = ProductStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 45 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 60");
        object.setState(state);
    }

	
    public void meendpoline (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException {
        ProductStateExists state = ProductStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 45 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 62");
        object.setState(state);
    }

	
    public void meendsuppliedproduct (org.hibernate.Session sess, Product object) throws org.hibernate.HibernateException {
        ProductStateExists state = ProductStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 45 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 64");
        object.setState(state);
    }

	
}
