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
 * Is able to find and create objects of type Suppliedproduct.
 *
 */
public abstract class SuppliedproductFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Suppliedproduct object.
    *
    * @return Suppliedproduct the created object
    */
    public static Suppliedproduct create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Suppliedproduct object = new SuppliedproductImpl();
        SuppliedproductState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Suppliedproduct object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Suppliedproduct findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Suppliedproduct object = (Suppliedproduct) sess.load(SuppliedproductImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Suppliedproduct");
        return q.list();
    }

    /**
     *
     * Finds Suppliedproduct object(s) using a query.
     *
     */
    public static java.util.Collection findByPrice (org.hibernate.Session sess, java.lang.String Price)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Suppliedproduct as c where c.Price = ?");
    	q.setString (0, Price);
        return q.list();
    }
}
