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
 *     table="HIB_REGISTEREDDEVICE"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Registereddevice 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="REGISTEREDDEVICE_STATE_FK"
     *     class="dao.RegistereddeviceState"
     */
    public RegistereddeviceState getState () {
        return this.state;
    }
    
    public void setState (RegistereddeviceState state){
        this.state = state;
    }

    protected RegistereddeviceState state;

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
    * @hibernate.many-to-one
    *     column="PLATFORM_FK"
    *     class="dao.Platform"
    */
    public dao.Platform getPlatform() {
        return this.platform;
    }
    
    public void setPlatform(dao.Platform platform){
        this.platform = platform;
    }

    private dao.Platform platform;
	  
   /**
    * 
    *
    * @hibernate.many-to-one
    *     column="DEVICE_FK"
    *     class="dao.Device"
    */
    public dao.Device getDevice() {
        return this.device;
    }
    
    public void setDevice(dao.Device device){
        this.device = device;
    }

    private dao.Device device;
	/**
     * 
     *
     * @hibernate.set
     *     role="deviceusage"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="REGISTEREDDEVICE_FK"
     * @hibernate.collection-one-to-many
     *     class="dao.Deviceusage"
     */
    public java.util.Collection getDeviceusage(){
        return this.deviceusage;
    }

    protected void setDeviceusage(java.util.Collection deviceusage){
        this.deviceusage = deviceusage;
    }

    private java.util.Collection deviceusage;
    public void attachDeviceusage (dao.Deviceusage object) {
        this.deviceusage.add(object);
    }
	// ---------- precondition of business methods  -----------
	// --- o/c ---
    public abstract void check_mecrregistereddevice() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meendregistereddevice() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrdeviceresult() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meenddeviceresult() throws MerodeException;
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
	public abstract void mecrregistereddevice(java.lang.String Name)
    	throws MerodeException;


	
/**
     *  --- o/e ---
     */
    public abstract void meendregistereddevice()
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
