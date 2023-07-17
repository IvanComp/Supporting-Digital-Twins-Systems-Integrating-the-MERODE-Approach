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
 *     table="HIB_SUPPLIEDPRODUCT"
 * @hibernate.discriminator
 *     column="class"
 * 
 *
 */

public abstract class Suppliedproduct 
    implements java.io.Serializable {
    
    // --------------- state --------------------------

    /**
     * @hibernate.many-to-one
     *     column="SUPPLIEDPRODUCT_STATE_FK"
     *     class="dao.SuppliedproductState"
     */
    public SuppliedproductState getState () {
        return this.state;
    }
    
    public void setState (SuppliedproductState state){
        this.state = state;
    }

    protected SuppliedproductState state;

    // --------------- attributes ---------------------
    private java.lang.String price;
    /**
     * 
     *
     * @hibernate.property
     *     column="PRICE"
     *     type="java.lang.String"
     *
     * @hibernate.column
     *     name="PRICE"
     *     sql-type="VARCHAR(256)"
     */
    public java.lang.String getPrice(){
        return this.price;
    }

    public void setPrice(java.lang.String price){
        this.price = price;
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
    *     column="PRODUCT_FK"
    *     class="dao.Product"
    */
    public dao.Product getProduct() {
        return this.product;
    }
    
    public void setProduct(dao.Product product){
        this.product = product;
    }

    private dao.Product product;
	  
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
     *     column="SUPPLIEDPRODUCT_FK"
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
    public abstract void check_mecrsuppliedproduct() throws MerodeException;
	// --- o/e --- 
    public abstract void check_meendsuppliedproduct() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_mecrpoline() throws MerodeException;
	// --- o/dpnds --- 
    public abstract void check_meendpoline() throws MerodeException;

    // ---------------- business methods  ----------------------


	/**
     *  --- o/c --- 
     */
	public abstract void mecrsuppliedproduct(java.lang.String Price)
    	throws MerodeException;


	
/**
     *  --- o/e ---
     */
    public abstract void meendsuppliedproduct()
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

		for (String s : inconsistentDpnds){
			commaSeparated += 
				"".equals(commaSeparated) ?
					s : "," + s ;
		}

		return commaSeparated;
	}

}
