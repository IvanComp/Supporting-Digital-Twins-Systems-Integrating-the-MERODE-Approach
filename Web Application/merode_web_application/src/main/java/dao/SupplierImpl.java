/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */

package dao;

import java.util.HashSet;
import java.util.Set;

/**
 * @hibernate.subclass
 *    discriminator-value="SupplierImpl"
 */
public class SupplierImpl extends Supplier {

	private String livingDependents = "";

	// constructor
    public SupplierImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Supplier ...

	//--- o/c ---  
    public void mecrsupplier(java.lang.String Name) {
        MerodeLogger.logln("Executing Supplier.mecrsupplier (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendsupplier() {
        MerodeLogger.logln("Executing Supplier.meendsupplier() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrpoline() {
        MerodeLogger.logln("Executing Supplier.mecrpoline() (A/M)");
    }

		//--- o/dpnd ---
    public void meendpoline() {
        MerodeLogger.logln("Executing Supplier.meendpoline() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrsuppliedproduct() {
        MerodeLogger.logln("Executing Supplier.mecrsuppliedproduct() (A/M)");
    }

		//--- o/dpnd ---
    public void meendsuppliedproduct() {
        MerodeLogger.logln("Executing Supplier.meendsuppliedproduct() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrpurchaseorder() {
        MerodeLogger.logln("Executing Supplier.mecrpurchaseorder() (A/M)");
    }

		//--- o/dpnd ---
    public void meendpurchaseorder() {
        MerodeLogger.logln("Executing Supplier.meendpurchaseorder() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrsupplier() throws MerodeException {
        MerodeLogger.log("Checking Supplier.mecrsupplier...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendsupplier() throws MerodeException {
        MerodeLogger.log("Checking Supplier.meendsupplier()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendsupplier()\" in class Supplier:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrpoline() throws MerodeException {
        MerodeLogger.log("Checking Supplier.mecrpoline()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendpoline() throws MerodeException {
        MerodeLogger.log("Checking Supplier.meendpoline()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrsuppliedproduct() throws MerodeException {
        MerodeLogger.log("Checking Supplier.mecrsuppliedproduct()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendsuppliedproduct() throws MerodeException {
        MerodeLogger.log("Checking Supplier.meendsuppliedproduct()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrpurchaseorder() throws MerodeException {
        MerodeLogger.log("Checking Supplier.mecrpurchaseorder()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendpurchaseorder() throws MerodeException {
        MerodeLogger.log("Checking Supplier.meendpurchaseorder()...");
        MerodeLogger.logln("passed");
    }

    


    // -------------------------------------------------------
    
    private boolean hasLivingDependents() {
    	Set <String> dependents = new HashSet();

        java.util.Collection col_suppliedproduct = getSuppliedproduct();
        if (col_suppliedproduct != null){
        	if (!col_suppliedproduct.isEmpty()){
		        java.util.Iterator i_suppliedproduct = col_suppliedproduct.iterator();
		        while (i_suppliedproduct.hasNext()) {
		            dao.Suppliedproduct obj_suppliedproduct = (dao.Suppliedproduct)i_suppliedproduct.next();
		            if (!obj_suppliedproduct.getState().isFinalState()){
		            	dependents.add("Suppliedproduct");
		            }
		        }        	
        	}
        }


        java.util.Collection col_purchaseorder = getPurchaseorder();
        if (col_purchaseorder != null){
        	if (!col_purchaseorder.isEmpty()){
		        java.util.Iterator i_purchaseorder = col_purchaseorder.iterator();
		        while (i_purchaseorder.hasNext()) {
		            dao.Purchaseorder obj_purchaseorder = (dao.Purchaseorder)i_purchaseorder.next();
		            if (!obj_purchaseorder.getState().isFinalState()){
		            	dependents.add("Purchaseorder");
		            }
		        }        	
        	}
        }

        for (String s : dependents){
            livingDependents = "".equals(livingDependents) ? s
        			: livingDependents + ", " + s;       	
        }
        return dependents.size() > 0;
    }
 
 
    // ---------- precondition of inherited business methods  -----------
    




    // ---------------- inherited business methods  ----------------------
    



	     
}
