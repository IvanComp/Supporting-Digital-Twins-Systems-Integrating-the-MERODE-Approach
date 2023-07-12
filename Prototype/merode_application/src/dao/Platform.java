/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */

package dao;

import java.util.HashSet;
import java.util.Iterator;



/**
 * 
 *
 * @hibernate.class
 *     table="HIB_PLATFORM"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Platform 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="PLATFORM_STATE_FK"
     *     class="dao.PlatformState"
     */
    public PlatformState getState () {
        return this.state;
    }
    
    public void setState (PlatformState state){
        this.state = state;
    }

    protected PlatformState state;

    // --------------- attributes ---------------------
    private java.lang.String name;
    /**
     * 
     *
     * @hibernate.property
     *     column="NAME"
     *     type="java.lang.String"
     *
     * @hibernate.column
     *     name="NAME"
     *     sql-type="VARCHAR(256)"
     */
    public java.lang.String getName(){
        return this.name;
    }

    public void setName(java.lang.String name){
        this.name = name;
    }
    private java.lang.String id;

    /**
     * 
     *
     * @hibernate.id
     *     generator-class="uuid.hex"
     *     column="ID"
     *     type="java.lang.String"
     *
     * @hibernate.column
     *     name="ID"
     *     sql-type="VARCHAR(256)"
     */
    public java.lang.String getId()
    {
        return this.id;
    }

    public void setId(java.lang.String id){
        this.id = id;
    }


    // ------------- relations ------------------

/**
     * 
     *
     * @hibernate.set
     *     role="platformdeployment"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="PLATFORM_FK"
     * @hibernate.collection-one-to-many
     *     class="dao.Platformdeployment"
     */
    public java.util.Collection getPlatformdeployment(){
        return this.platformdeployment;
    }

    protected void setPlatformdeployment(java.util.Collection platformdeployment){
        this.platformdeployment = platformdeployment;
    }

    private java.util.Collection platformdeployment;
    public void attachPlatformdeployment (dao.Platformdeployment object) {
        this.platformdeployment.add(object);
    }
	/**
     * 
     *
     * @hibernate.set
     *     role="registereddevice"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="PLATFORM_FK"
     * @hibernate.collection-one-to-many
     *     class="dao.Registereddevice"
     */
    public java.util.Collection getRegistereddevice(){
        return this.registereddevice;
    }

    protected void setRegistereddevice(java.util.Collection registereddevice){
        this.registereddevice = registereddevice;
    }

    private java.util.Collection registereddevice;
    public void attachRegistereddevice (dao.Registereddevice object) {
        this.registereddevice.add(object);
    }
	// ---------- precondition of business methods  -----------
	// --- o/c ---
    public abstract void check_mecrplatform() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meendplatform() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrplatformdeployment() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendplatformdeployment() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrdeviceresult() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meenddeviceresult() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrregistereddevice() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendregistereddevice() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrdeviceusage() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meenddeviceusage() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_deviceundeployment() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_devicedeployment() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mesetready() throws MerodeException;

    // ---------------- business methods  ----------------------


	/**
     *  --- o/c --- 
     */
	public abstract void mecrplatform(java.lang.String Name)
    	throws MerodeException;


	
/**
     *  --- o/e ---
     */
    public abstract void meendplatform()
        throws MerodeException;
	
		
   /**
    * --- o/dpnds ---
    */
    public abstract void mecrplatformdeployment()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void meendplatformdeployment()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void mecrdeviceresult()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void meenddeviceresult()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void mecrregistereddevice()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void meendregistereddevice()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void mecrdeviceusage()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void meenddeviceusage()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void deviceundeployment()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void devicedeployment()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void mesetready()
        throws MerodeException;	

	
    /**
     * checking if an object is consistent 
     * against mandatory relationship(s)
     */
    public String getMandatoryInconsistency() {
		java.util.Set<String> inconsistentDpnds = new HashSet();
		String commaSeparated = "";
		Iterator it;
		//boolean isConsistent = false;

		for (String s : inconsistentDpnds){
			commaSeparated += 
				"".equals(commaSeparated) ?
					s : "," + s ;
		}

		return commaSeparated;
	}

}
