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
 *    discriminator-value="PolineImpl"
 */
public class PolineImpl extends Poline {

	private String livingDependents = "";

	// constructor
    public PolineImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Poline ...

	//--- o/c ---  
    public void mecrpoline(java.lang.String Name) {
        MerodeLogger.logln("Executing Poline.mecrpoline (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendpoline() {
        MerodeLogger.logln("Executing Poline.meendpoline() (O/E)");
    }
	


    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrpoline() throws MerodeException {
        MerodeLogger.log("Checking Poline.mecrpoline...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendpoline() throws MerodeException {
        MerodeLogger.log("Checking Poline.meendpoline()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendpoline()\" in class Poline:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   


    


    // -------------------------------------------------------
    
    private boolean hasLivingDependents() {
    	Set <String> dependents = new HashSet();
        for (String s : dependents){
            livingDependents = "".equals(livingDependents) ? s
        			: livingDependents + ", " + s;       	
        }
        return dependents.size() > 0;
    }
 
 
    // ---------- precondition of inherited business methods  -----------
    




    // ---------------- inherited business methods  ----------------------
    



	     
}
