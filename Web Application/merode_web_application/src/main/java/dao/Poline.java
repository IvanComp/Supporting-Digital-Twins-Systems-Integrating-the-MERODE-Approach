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
 *     table="HIB_POLINE"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Poline 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="POLINE_STATE_FK"
     *     class="dao.PolineState"
     */
    public PolineState getState () {
        return this.state;
    }
    
    public void setState (PolineState state){
        this.state = state;
    }

    protected PolineState state;

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
    *     column="SUPPLIEDPRODUCT_FK"
    *     class="dao.Suppliedproduct"
    */
    public dao.Suppliedproduct getSuppliedproduct() {
        return this.suppliedproduct;
    }
    
    public void setSuppliedproduct(dao.Suppliedproduct suppliedproduct){
        this.suppliedproduct = suppliedproduct;
    }

    private dao.Suppliedproduct suppliedproduct;
	  
   /**
    * 
    *
    * @hibernate.many-to-one
    *     column="PURCHASEORDER_FK"
    *     class="dao.Purchaseorder"
    */
    public dao.Purchaseorder getPurchaseorder() {
        return this.purchaseorder;
    }
    
    public void setPurchaseorder(dao.Purchaseorder purchaseorder){
        this.purchaseorder = purchaseorder;
    }

    private dao.Purchaseorder purchaseorder;
	// ---------- precondition of business methods  -----------
	// --- o/c ---
    public abstract void check_mecrpoline() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meendpoline() throws MerodeException;

    // ---------------- business methods  ----------------------


	/**
     *  --- o/c --- 
     */
	public abstract void mecrpoline(java.lang.String Name)
    	throws MerodeException;


	
/**
     *  --- o/e ---
     */
    public abstract void meendpoline()
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
