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
 * Is able to find and create objects of type Poline.
 *
 */
public abstract class PolineFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Poline object.
    *
    * @return Poline the created object
    */
    public static Poline create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Poline object = new PolineImpl();
        PolineState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Poline object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Poline findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Poline object = (Poline) sess.load(PolineImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Poline");
        return q.list();
    }

    /**
     *
     * Finds Poline object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Poline as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
