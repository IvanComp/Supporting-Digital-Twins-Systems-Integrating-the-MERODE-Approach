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
 * Is able to find and create objects of type Device.
 *
 */
public abstract class DeviceFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Device object.
    *
    * @return Device the created object
    */
    public static Device create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Device object = new DeviceImpl();
        DeviceState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Device object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Device findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Device object = (Device) sess.load(DeviceImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Device");
        return q.list();
    }

    /**
     *
     * Finds Device object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Device as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
