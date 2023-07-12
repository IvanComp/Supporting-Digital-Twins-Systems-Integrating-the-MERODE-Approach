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
 *    discriminator-value="DeviceresultImpl"
 */
public class DeviceresultImpl extends Deviceresult {

	private String livingDependents = "";

	// constructor
    public DeviceresultImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Deviceresult ...

	//--- o/c ---  
    public void mecrdeviceresult( java.lang.String Time,
		java.lang.String Value) {
        MerodeLogger.logln("Executing Deviceresult.mecrdeviceresult (O/C)");
        setTime (Time);
        setValue (Value);
    }


		//--- o/e ---
	public void meenddeviceresult() {
        MerodeLogger.logln("Executing Deviceresult.meenddeviceresult() (O/E)");
    }
	


    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrdeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Deviceresult.mecrdeviceresult...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meenddeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Deviceresult.meenddeviceresult()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meenddeviceresult()\" in class Deviceresult:\nObject has living dependents " + livingDependents);
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
