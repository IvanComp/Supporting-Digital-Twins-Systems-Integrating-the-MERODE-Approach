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
 *    discriminator-value="PlatformdeploymentImpl"
 */
public class PlatformdeploymentImpl extends Platformdeployment {

	private String livingDependents = "";

	// constructor
    public PlatformdeploymentImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Platformdeployment ...

	//--- o/c ---  
    public void mecrplatformdeployment(java.lang.String Name) {
        MerodeLogger.logln("Executing Platformdeployment.mecrplatformdeployment (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendplatformdeployment() {
        MerodeLogger.logln("Executing Platformdeployment.meendplatformdeployment() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrdeviceresult() {
        MerodeLogger.logln("Executing Platformdeployment.mecrdeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceresult() {
        MerodeLogger.logln("Executing Platformdeployment.meenddeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrdeviceusage() {
        MerodeLogger.logln("Executing Platformdeployment.mecrdeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceusage() {
        MerodeLogger.logln("Executing Platformdeployment.meenddeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void deviceundeployment() {
        MerodeLogger.logln("Executing Platformdeployment.deviceundeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void devicedeployment() {
        MerodeLogger.logln("Executing Platformdeployment.devicedeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void mesetready() {
        MerodeLogger.logln("Executing Platformdeployment.mesetready() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrplatformdeployment() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.mecrplatformdeployment...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendplatformdeployment() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.meendplatformdeployment()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendplatformdeployment()\" in class Platformdeployment:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrdeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.mecrdeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.meenddeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrdeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.mecrdeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.meenddeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_deviceundeployment() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.deviceundeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_devicedeployment() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.devicedeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mesetready() throws MerodeException {
        MerodeLogger.log("Checking Platformdeployment.mesetready()...");
        MerodeLogger.logln("passed");
    }

    


    // -------------------------------------------------------
    
    private boolean hasLivingDependents() {
    	Set <String> dependents = new HashSet();

        java.util.Collection col_deviceusage = getDeviceusage();
        if (col_deviceusage != null){
        	if (!col_deviceusage.isEmpty()){
		        java.util.Iterator i_deviceusage = col_deviceusage.iterator();
		        while (i_deviceusage.hasNext()) {
		            dao.Deviceusage obj_deviceusage = (dao.Deviceusage)i_deviceusage.next();
		            if (!obj_deviceusage.getState().isFinalState()){
		            	dependents.add("Deviceusage");
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
