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
 *     table="HIB_DEVICEUSAGE"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Deviceusage 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="DEVICEUSAGE_STATE_FK"
     *     class="dao.DeviceusageState"
     */
    public DeviceusageState getState () {
        return this.state;
    }
    
    public void setState (DeviceusageState state){
        this.state = state;
    }

    protected DeviceusageState state;

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
    *     column="PLATFORMDEPLOYMENT_FK"
    *     class="dao.Platformdeployment"
    */
    public dao.Platformdeployment getPlatformdeployment() {
        return this.platformdeployment;
    }
    
    public void setPlatformdeployment(dao.Platformdeployment platformdeployment){
        this.platformdeployment = platformdeployment;
    }

    private dao.Platformdeployment platformdeployment;
	  
   /**
    * 
    *
    * @hibernate.many-to-one
    *     column="REGISTEREDDEVICE_FK"
    *     class="dao.Registereddevice"
    */
    public dao.Registereddevice getRegistereddevice() {
        return this.registereddevice;
    }
    
    public void setRegistereddevice(dao.Registereddevice registereddevice){
        this.registereddevice = registereddevice;
    }

    private dao.Registereddevice registereddevice;
	  
   /**
    * 
    *
    * @hibernate.many-to-one
    *     column="PROCEDURE_FK"
    *     class="dao.Procedure"
    */
    public dao.Procedure getProcedure() {
        return this.procedure;
    }
    
    public void setProcedure(dao.Procedure procedure){
        this.procedure = procedure;
    }

    private dao.Procedure procedure;
	  
   /**
    * 
    *
    * @hibernate.many-to-one
    *     column="PROPERTY_FK"
    *     class="dao.Property"
    */
    public dao.Property getProperty() {
        return this.property;
    }
    
    public void setProperty(dao.Property property){
        this.property = property;
    }

    private dao.Property property;
	/**
     * 
     *
     * @hibernate.set
     *     role="deviceresult"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="DEVICEUSAGE_FK"
     * @hibernate.collection-one-to-many
     *     class="dao.Deviceresult"
     */
    public java.util.Collection getDeviceresult(){
        return this.deviceresult;
    }

    protected void setDeviceresult(java.util.Collection deviceresult){
        this.deviceresult = deviceresult;
    }

    private java.util.Collection deviceresult;
    public void attachDeviceresult (dao.Deviceresult object) {
        this.deviceresult.add(object);
    }
	// ---------- precondition of business methods  -----------
	// --- o/c ---
    public abstract void check_mecrdeviceusage() throws MerodeException;

	// --- o/m --- 
    public abstract void check_devicedeployment() throws MerodeException;
	// --- o/m --- 
    public abstract void check_mesetready() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meenddeviceusage() throws MerodeException;
	// --- o/e --- 
    public abstract void check_deviceundeployment() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrdeviceresult() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meenddeviceresult() throws MerodeException;

    // ---------------- business methods  ----------------------


	/**
     *  --- o/c --- 
     */
	public abstract void mecrdeviceusage(java.lang.String Name)
    	throws MerodeException;


	/**
     *  --- o/m ---
     */
	public abstract void devicedeployment(java.lang.String Name)
    	throws MerodeException;

	/**
     *  --- o/m ---
     */
	public abstract void mesetready(java.lang.String Name)
    	throws MerodeException;

	
/**
     *  --- o/e ---
     */
    public abstract void meenddeviceusage()
        throws MerodeException;
	
/**
     *  --- o/e ---
     */
    public abstract void deviceundeployment()
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
