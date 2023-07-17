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
 *     table="HIB_SUPPLIER"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Supplier 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="SUPPLIER_STATE_FK"
     *     class="dao.SupplierState"
     */
    public SupplierState getState () {
        return this.state;
    }
    
    public void setState (SupplierState state){
        this.state = state;
    }

    protected SupplierState state;

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
     *     role="suppliedproduct"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="SUPPLIER_FK"
     * @hibernate.collection-one-to-many
     *     class="dao.Suppliedproduct"
     */
    public java.util.Collection getSuppliedproduct(){
        return this.suppliedproduct;
    }

    protected void setSuppliedproduct(java.util.Collection suppliedproduct){
        this.suppliedproduct = suppliedproduct;
    }

    private java.util.Collection suppliedproduct;
    public void attachSuppliedproduct (dao.Suppliedproduct object) {
        this.suppliedproduct.add(object);
    }
	/**
     * 
     *
     * @hibernate.set
     *     role="purchaseorder"
     *     lazy="false"
     * @hibernate.collection-key
     *     column="SUPPLIER_FK"
     * @hibernate.collection-one-to-many
     *     class="dao.Purchaseorder"
     */
    public java.util.Collection getPurchaseorder(){
        return this.purchaseorder;
    }

    protected void setPurchaseorder(java.util.Collection purchaseorder){
        this.purchaseorder = purchaseorder;
    }

    private java.util.Collection purchaseorder;
    public void attachPurchaseorder (dao.Purchaseorder object) {
        this.purchaseorder.add(object);
    }
	// ---------- precondition of business methods  -----------
	// --- o/c ---
    public abstract void check_mecrsupplier() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meendsupplier() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrpoline() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendpoline() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrsuppliedproduct() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendsuppliedproduct() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrpurchaseorder() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendpurchaseorder() throws MerodeException;

    // ---------------- business methods  ----------------------


	/**
     *  --- o/c --- 
     */
	public abstract void mecrsupplier(java.lang.String Name)
    	throws MerodeException;


	
/**
     *  --- o/e ---
     */
    public abstract void meendsupplier()
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
    * --- o/dpnds ---
    */
    public abstract void mecrsuppliedproduct()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void meendsuppliedproduct()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void mecrpurchaseorder()
        throws MerodeException;	

		
   /**
    * --- o/dpnds ---
    */
    public abstract void meendpurchaseorder()
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
