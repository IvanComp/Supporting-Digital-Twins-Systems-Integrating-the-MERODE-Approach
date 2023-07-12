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
 *     table="HIB_DEVICERESULT"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Deviceresult 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="DEVICERESULT_STATE_FK"
     *     class="dao.DeviceresultState"
     */
    public DeviceresultState getState () {
        return this.state;
    }
    
    public void setState (DeviceresultState state){
        this.state = state;
    }

    protected DeviceresultState state;

    // --------------- attributes ---------------------
    private java.lang.String time;
    /**
     * 
     *
     * @hibernate.property
     *     column="TIME"
     *     type="java.lang.String"
     *
     * @hibernate.column
     *     name="TIME"
     *     sql-type="VARCHAR(256)"
     */
    public java.lang.String getTime(){
        return this.time;
    }

    public void setTime(java.lang.String time){
        this.time = time;
    }
    private java.lang.String value;
    /**
     * 
     *
     * @hibernate.property
     *     column="VALUE"
     *     type="java.lang.String"
     *
     * @hibernate.column
     *     name="VALUE"
     *     sql-type="VARCHAR(256)"
     */
    public java.lang.String getValue(){
        return this.value;
    }

    public void setValue(java.lang.String value){
        this.value = value;
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
    *     column="DEVICEUSAGE_FK"
    *     class="dao.Deviceusage"
    */
    public dao.Deviceusage getDeviceusage() {
        return this.deviceusage;
    }
    
    public void setDeviceusage(dao.Deviceusage deviceusage){
        this.deviceusage = deviceusage;
    }

    private dao.Deviceusage deviceusage;
	// ---------- precondition of business methods  -----------
	// --- o/c ---
    public abstract void check_mecrdeviceresult() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meenddeviceresult() throws MerodeException;

    // ---------------- business methods  ----------------------


	/**
     *  --- o/c --- 
     */
	public abstract void mecrdeviceresult( java.lang.String Time,
		java.lang.String Value)
    	throws MerodeException;


	
/**
     *  --- o/e ---
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
