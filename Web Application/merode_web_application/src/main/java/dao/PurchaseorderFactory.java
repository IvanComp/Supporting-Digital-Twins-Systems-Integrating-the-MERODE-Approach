/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */

package dao;

/**
 * Factory class.
 * Is able to find and create objects of type Purchaseorder.
 *
 */
public abstract class PurchaseorderFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Purchaseorder object.
    *
    * @return Purchaseorder the created object
    */
    public static Purchaseorder create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Purchaseorder object = new PurchaseorderImpl();
        PurchaseorderState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Purchaseorder object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Purchaseorder findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Purchaseorder object = (Purchaseorder) sess.load(PurchaseorderImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Purchaseorder");
        return q.list();
    }

    /**
     *
     * Finds Purchaseorder object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Purchaseorder as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
