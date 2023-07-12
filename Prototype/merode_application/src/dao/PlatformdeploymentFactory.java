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
 * Is able to find and create objects of type Platformdeployment.
 *
 */
public abstract class PlatformdeploymentFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Platformdeployment object.
    *
    * @return Platformdeployment the created object
    */
    public static Platformdeployment create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Platformdeployment object = new PlatformdeploymentImpl();
        PlatformdeploymentState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Platformdeployment object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Platformdeployment findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Platformdeployment object = (Platformdeployment) sess.load(PlatformdeploymentImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Platformdeployment");
        return q.list();
    }

    /**
     *
     * Finds Platformdeployment object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Platformdeployment as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
