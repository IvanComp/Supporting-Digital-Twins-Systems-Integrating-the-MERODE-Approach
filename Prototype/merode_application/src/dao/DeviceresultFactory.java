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
 * Is able to find and create objects of type Deviceresult.
 *
 */
public abstract class DeviceresultFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Deviceresult object.
    *
    * @return Deviceresult the created object
    */
    public static Deviceresult create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Deviceresult object = new DeviceresultImpl();
        DeviceresultState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Deviceresult object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Deviceresult findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Deviceresult object = (Deviceresult) sess.load(DeviceresultImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Deviceresult");
        return q.list();
    }

    /**
     *
     * Finds Deviceresult object(s) using a query.
     *
     */
    public static java.util.Collection findByTime (org.hibernate.Session sess, java.lang.String Time)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Deviceresult as c where c.Time = ?");
    	q.setString (0, Time);
        return q.list();
    }
    /**
     *
     * Finds Deviceresult object(s) using a query.
     *
     */
    public static java.util.Collection findByValue (org.hibernate.Session sess, java.lang.String Value)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Deviceresult as c where c.Value = ?");
    	q.setString (0, Value);
        return q.list();
    }
}
