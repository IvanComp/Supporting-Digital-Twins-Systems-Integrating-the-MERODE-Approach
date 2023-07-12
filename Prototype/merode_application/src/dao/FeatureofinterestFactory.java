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
 * Is able to find and create objects of type Featureofinterest.
 *
 */
public abstract class FeatureofinterestFactory {

   // ---------------- create method --------------------
   
   /**
    * Creates a(n) Featureofinterest object.
    *
    * @return Featureofinterest the created object
    */
    public static Featureofinterest create(org.hibernate.Session session) throws org.hibernate.HibernateException {
        Featureofinterest object = new FeatureofinterestImpl();
        FeatureofinterestState.setInitialState(session, object);
        return object;
    }


    // ---------------- finder methods  ----------------------

    /**
     *
     * Finds Featureofinterest object by its primary key.
     * In Hibernate, this is just a call to load().
     *
     */
    public static Featureofinterest findByPrimaryKey (org.hibernate.Session sess, java.lang.String id)
        throws org.hibernate.HibernateException {
        Featureofinterest object = (Featureofinterest) sess.load(FeatureofinterestImpl.class, id);
        return object;
    }

    public static java.util.Collection getAllObjects (org.hibernate.Session sess)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Featureofinterest");
        return q.list();
    }

    /**
     *
     * Finds Featureofinterest object(s) using a query.
     *
     */
    public static java.util.Collection findByName (org.hibernate.Session sess, java.lang.String Name)
        throws org.hibernate.HibernateException {
    
        org.hibernate.Query q = sess.createQuery("from dao.Featureofinterest as c where c.Name = ?");
    	q.setString (0, Name);
        return q.list();
    }
}
