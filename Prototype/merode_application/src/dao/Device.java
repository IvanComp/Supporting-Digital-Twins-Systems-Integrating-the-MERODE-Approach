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
 *     table="HIB_DEVICE"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Device 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="DEVICE_STATE_FK"
     *     class="dao.DeviceState"
     */
    public DeviceState getState () {
        return this.state;
    }
    
    public void setState (DeviceState state){
        this.state = state;
    }

    protected DeviceState state;

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
     *     role="registereddevice"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="DEVICE_FK"
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
	/**
     * 
     *
     * @hibernate.set
     *     role="procedure"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="DEVICE_FK"
     * @hibernate.collection-one-to-many
     *     class="dao.Procedure"
     */
    public java.util.Collection getProcedure(){
        return this.procedure;
    }

    protected void setProcedure(java.util.Collection procedure){
        this.procedure = procedure;
    }

    private java.util.Collection procedure;
    public void attachProcedure (dao.Procedure object) {
        this.procedure.add(object);
    }
	// ---------- precondition of business methods  -----------
	// --- o/c ---
    public abstract void check_mecrdevice() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meenddevice() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrdeviceresult() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meenddeviceresult() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrregistereddevice() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendregistereddevice() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrprocedure() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendprocedure() throws MerodeException;
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
	public abstract void mecrdevice(java.lang.String Name)
    	throws MerodeException;


	
/**
     *  --- o/e ---
     */
    public abstract void meenddevice()
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
    public abstract void mecrprocedure()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void meendprocedure()
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
