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
 * Is able to find and create objects of type Supplier.
 *
 */
public abstract class SupplierFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Supplier object.
    *
    * @return Supplier the created object
    */
    public static Supplier create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Supplier object = new SupplierImpl();
        SupplierState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Supplier object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Supplier findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Supplier object = (Supplier) sess.load(SupplierImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Supplier");
        return q.list();
    }

    /**
     *
     * Finds Supplier object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Supplier as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
