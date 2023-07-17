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
 * Is able to find and create objects of type Product.
 *
 */
public abstract class ProductFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Product object.
    *
    * @return Product the created object
    */
    public static Product create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Product object = new ProductImpl();
        ProductState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Product object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Product findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Product object = (Product) sess.load(ProductImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Product");
        return q.list();
    }

    /**
     *
     * Finds Product object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Product as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
