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
 *    discriminator-value="ProductImpl"
 */
public class ProductImpl extends Product {

	private String livingDependents = "";

	// constructor
    public ProductImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Product ...

	//--- o/c ---  
    public void mecrproduct(java.lang.String Name) {
        MerodeLogger.logln("Executing Product.mecrproduct (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendproduct() {
        MerodeLogger.logln("Executing Product.meendproduct() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrpoline() {
        MerodeLogger.logln("Executing Product.mecrpoline() (A/M)");
    }

		//--- o/dpnd ---
    public void meendpoline() {
        MerodeLogger.logln("Executing Product.meendpoline() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrsuppliedproduct() {
        MerodeLogger.logln("Executing Product.mecrsuppliedproduct() (A/M)");
    }

		//--- o/dpnd ---
    public void meendsuppliedproduct() {
        MerodeLogger.logln("Executing Product.meendsuppliedproduct() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrproduct() throws MerodeException {
        MerodeLogger.log("Checking Product.mecrproduct...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendproduct() throws MerodeException {
        MerodeLogger.log("Checking Product.meendproduct()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendproduct()\" in class Product:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrpoline() throws MerodeException {
        MerodeLogger.log("Checking Product.mecrpoline()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendpoline() throws MerodeException {
        MerodeLogger.log("Checking Product.meendpoline()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrsuppliedproduct() throws MerodeException {
        MerodeLogger.log("Checking Product.mecrsuppliedproduct()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendsuppliedproduct() throws MerodeException {
        MerodeLogger.log("Checking Product.meendsuppliedproduct()...");
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

        for (String s : dependents){
            livingDependents = "".equals(livingDependents) ? s
        			: livingDependents + ", " + s;       	
        }
        return dependents.size() > 0;
    }
 
 
    // ---------- precondition of inherited business methods  -----------
    




    // ---------------- inherited business methods  ----------------------
    



	     
}
