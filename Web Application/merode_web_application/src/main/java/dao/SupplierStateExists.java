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
 *    discriminator-value="SupplierStateExists"
 */
public class SupplierStateExists extends SupplierState {

    public static SupplierStateExists getObject (org.hibernate.Session sess) throws org.hibernate.HibernateException {
	    SupplierStateExists state = null;
	    // Search in database
	    java.util.Collection states = findStateByName (sess, "exists");
	    if ( states != null && !states.isEmpty() )
	        state = (SupplierStateExists)states.iterator().next();
	    if ( state == null ) {
	        state = new SupplierStateExists();
	        // Save in database
	        sess.save (state);
	    }
	    return state;
    }

    private static java.util.Collection findStateByName (org.hibernate.Session sess, java.lang.String statename)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.SupplierState as c where c.name = ?");
    	q.setString (0, statename);
        return q.list();
    }
    
    protected java.lang.String getStateName() {
    	return "exists";
    }

    protected java.lang.String getStateId() {
    	return "68";
    }

    public boolean isInitialState() {
    	return false;
    }

    public boolean isFinalState() {
    	return false;
    }


		
    public void check_meendsupplier() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SupplierState.meendsupplier...passed");
    }

		
    public void check_mecrpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SupplierState.mecrpoline...passed");
    }

		
    public void check_mecrpurchaseorder() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SupplierState.mecrpurchaseorder...passed");
    }

		
    public void check_mecrsuppliedproduct() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SupplierState.mecrsuppliedproduct...passed");
    }

		
    public void check_meendpoline() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SupplierState.meendpoline...passed");
    }

		
    public void check_meendpurchaseorder() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SupplierState.meendpurchaseorder...passed");
    }

		
    public void check_meendsuppliedproduct() {
        // No exception is thrown anymore...
        MerodeLogger.logln ("Checking SupplierState.meendsuppliedproduct...passed");
    }

	
	
    public void meendsupplier (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateEnded state = SupplierStateEnded.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 65 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 75");
        object.setState(state);
    }

	
    public void mecrpoline (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateExists state = SupplierStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 65 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 78");
        object.setState(state);
    }

	
    public void mecrpurchaseorder (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateExists state = SupplierStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 65 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 82");
        object.setState(state);
    }

	
    public void mecrsuppliedproduct (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateExists state = SupplierStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 65 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 84");
        object.setState(state);
    }

	
    public void meendpoline (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateExists state = SupplierStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 65 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 86");
        object.setState(state);
    }

	
    public void meendpurchaseorder (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateExists state = SupplierStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 65 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 90");
        object.setState(state);
    }

	
    public void meendsuppliedproduct (org.hibernate.Session sess, Supplier object) throws org.hibernate.HibernateException {
        SupplierStateExists state = SupplierStateExists.getObject(sess);
        MerodeLogger.logln ("[" + Instant.now() + "]" + " Object " + object.getId() + " of object type 65 changed from state " + getStateId() + " to state " + state.getStateId() + " using method 92");
        object.setState(state);
    }

	
}
