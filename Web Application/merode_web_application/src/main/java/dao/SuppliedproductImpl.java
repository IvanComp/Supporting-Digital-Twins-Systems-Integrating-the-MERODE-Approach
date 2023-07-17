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
 *    discriminator-value="SuppliedproductImpl"
 */
public class SuppliedproductImpl extends Suppliedproduct {

	private String livingDependents = "";

	// constructor
    public SuppliedproductImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Suppliedproduct ...

	//--- o/c ---  
    public void mecrsuppliedproduct(java.lang.String Price) {
        MerodeLogger.logln("Executing Suppliedproduct.mecrsuppliedproduct (O/C)");
        setPrice (Price);
    }


		//--- o/e ---
	public void meendsuppliedproduct() {
        MerodeLogger.logln("Executing Suppliedproduct.meendsuppliedproduct() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrpoline() {
        MerodeLogger.logln("Executing Suppliedproduct.mecrpoline() (A/M)");
    }

		//--- o/dpnd ---
    public void meendpoline() {
        MerodeLogger.logln("Executing Suppliedproduct.meendpoline() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrsuppliedproduct() throws MerodeException {
        MerodeLogger.log("Checking Suppliedproduct.mecrsuppliedproduct...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendsuppliedproduct() throws MerodeException {
        MerodeLogger.log("Checking Suppliedproduct.meendsuppliedproduct()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendsuppliedproduct()\" in class Suppliedproduct:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrpoline() throws MerodeException {
        MerodeLogger.log("Checking Suppliedproduct.mecrpoline()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendpoline() throws MerodeException {
        MerodeLogger.log("Checking Suppliedproduct.meendpoline()...");
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
