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
 *     table="HIB_PURCHASEORDER"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Purchaseorder 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="PURCHASEORDER_STATE_FK"
     *     class="dao.PurchaseorderState"
     */
    public PurchaseorderState getState () {
        return this.state;
    }
    
    public void setState (PurchaseorderState state){
        this.state = state;
    }

    protected PurchaseorderState state;

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
    *     column="SUPPLIER_FK"
    *     class="dao.Supplier"
    */
    public dao.Supplier getSupplier() {
        return this.supplier;
    }
    
    public void setSupplier(dao.Supplier supplier){
        this.supplier = supplier;
    }

    private dao.Supplier supplier;
	/**
     * 
     *
     * @hibernate.set
     *     role="poline"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="PURCHASEORDER_FK"
     * @hibernate.collection-one-to-many
     *     class="dao.Poline"
     */
    public java.util.Collection getPoline(){
        return this.poline;
    }

    protected void setPoline(java.util.Collection poline){
        this.poline = poline;
    }

    private java.util.Collection poline;
    public void attachPoline (dao.Poline object) {
        this.poline.add(object);
    }
	// ---------- precondition of business methods  -----------
	// --- o/c ---
    public abstract void check_mecrpurchaseorder() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meendpurchaseorder() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrpoline() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendpoline() throws MerodeException;

    // ---------------- business methods  ----------------------


	/**
     *  --- o/c --- 
     */
	public abstract void mecrpurchaseorder(java.lang.String Name)
    	throws MerodeException;


	
/**
     *  --- o/e ---
     */
    public abstract void meendpurchaseorder()
        throws MerodeException;
	
		
   /**
    * --- o/dpnds ---
    */
    public abstract void mecrpoline()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
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
		// ==== checking poline objects ===
		if (getPoline() != null){
			it = getPoline().iterator();
			boolean poline_consistent = false;
			while (it.hasNext()) {
				dao.Poline poline = (dao.Poline) it.next();
				if (!poline.getState().isFinalState()) {
					poline_consistent = true;
				}
			}
			if (! poline_consistent){
				inconsistentDpnds.add("POLINE");
			}
		}
		// ==========================

		for (String s : inconsistentDpnds){
			commaSeparated += 
				"".equals(commaSeparated) ?
					s : "," + s ;
		}

		return commaSeparated;
	}

}
