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
 * Is able to find and create objects of type Procedure.
 *
 */
public abstract class ProcedureFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Procedure object.
    *
    * @return Procedure the created object
    */
    public static Procedure create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Procedure object = new ProcedureImpl();
        ProcedureState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Procedure object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Procedure findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Procedure object = (Procedure) sess.load(ProcedureImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Procedure");
        return q.list();
    }

    /**
     *
     * Finds Procedure object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Procedure as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
