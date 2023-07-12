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
 * Is able to find and create objects of type Platform.
 *
 */
public abstract class PlatformFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Platform object.
    *
    * @return Platform the created object
    */
    public static Platform create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Platform object = new PlatformImpl();
        PlatformState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Platform object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Platform findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Platform object = (Platform) sess.load(PlatformImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Platform");
        return q.list();
    }

    /**
     *
     * Finds Platform object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Platform as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
