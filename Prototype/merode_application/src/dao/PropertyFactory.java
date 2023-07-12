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
 * Is able to find and create objects of type Property.
 *
 */
public abstract class PropertyFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Property object.
    *
    * @return Property the created object
    */
    public static Property create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Property object = new PropertyImpl();
        PropertyState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Property object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Property findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Property object = (Property) sess.load(PropertyImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Property");
        return q.list();
    }

    /**
     *
     * Finds Property object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Property as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
