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
 * Is able to find and create objects of type Registereddevice.
 *
 */
public abstract class RegistereddeviceFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Registereddevice object.
    *
    * @return Registereddevice the created object
    */
    public static Registereddevice create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Registereddevice object = new RegistereddeviceImpl();
        RegistereddeviceState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Registereddevice object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Registereddevice findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Registereddevice object = (Registereddevice) sess.load(RegistereddeviceImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Registereddevice");
        return q.list();
    }

    /**
     *
     * Finds Registereddevice object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Registereddevice as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
