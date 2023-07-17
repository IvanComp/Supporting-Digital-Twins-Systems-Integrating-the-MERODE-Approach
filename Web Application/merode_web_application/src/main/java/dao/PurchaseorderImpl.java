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
 *    discriminator-value="PurchaseorderImpl"
 */
public class PurchaseorderImpl extends Purchaseorder {

	private String livingDependents = "";

	// constructor
    public PurchaseorderImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Purchaseorder ...

	//--- o/c ---  
    public void mecrpurchaseorder(java.lang.String Name) {
        MerodeLogger.logln("Executing Purchaseorder.mecrpurchaseorder (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendpurchaseorder() {
        MerodeLogger.logln("Executing Purchaseorder.meendpurchaseorder() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrpoline() {
        MerodeLogger.logln("Executing Purchaseorder.mecrpoline() (A/M)");
    }

		//--- o/dpnd ---
    public void meendpoline() {
        MerodeLogger.logln("Executing Purchaseorder.meendpoline() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrpurchaseorder() throws MerodeException {
        MerodeLogger.log("Checking Purchaseorder.mecrpurchaseorder...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendpurchaseorder() throws MerodeException {
        MerodeLogger.log("Checking Purchaseorder.meendpurchaseorder()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendpurchaseorder()\" in class Purchaseorder:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrpoline() throws MerodeException {
        MerodeLogger.log("Checking Purchaseorder.mecrpoline()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendpoline() throws MerodeException {
        MerodeLogger.log("Checking Purchaseorder.meendpoline()...");
        MerodeLogger.logln("passed");
    }

    


    // -------------------------------------------------------
    
    private boolean hasLivingDependents() {
    	Set <String> dependents = new HashSet();

        java.util.Collection col_poline = getPoline();
        if (col_poline != null){
        	if (!col_poline.isEmpty()){
		        java.util.Iterator i_poline = col_poline.iterator();
		        while (i_poline.hasNext()) {
		            dao.Poline obj_poline = (dao.Poline)i_poline.next();
		            if (!obj_poline.getState().isFinalState()){
		            	dependents.add("Poline");
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
