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
 *    discriminator-value="PlatformImpl"
 */
public class PlatformImpl extends Platform {

	private String livingDependents = "";

	// constructor
    public PlatformImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Platform ...

	//--- o/c ---  
    public void mecrplatform(java.lang.String Name) {
        MerodeLogger.logln("Executing Platform.mecrplatform (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendplatform() {
        MerodeLogger.logln("Executing Platform.meendplatform() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrplatformdeployment() {
        MerodeLogger.logln("Executing Platform.mecrplatformdeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void meendplatformdeployment() {
        MerodeLogger.logln("Executing Platform.meendplatformdeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrdeviceresult() {
        MerodeLogger.logln("Executing Platform.mecrdeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceresult() {
        MerodeLogger.logln("Executing Platform.meenddeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrregistereddevice() {
        MerodeLogger.logln("Executing Platform.mecrregistereddevice() (A/M)");
    }

		//--- o/dpnd ---
    public void meendregistereddevice() {
        MerodeLogger.logln("Executing Platform.meendregistereddevice() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrdeviceusage() {
        MerodeLogger.logln("Executing Platform.mecrdeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceusage() {
        MerodeLogger.logln("Executing Platform.meenddeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void deviceundeployment() {
        MerodeLogger.logln("Executing Platform.deviceundeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void devicedeployment() {
        MerodeLogger.logln("Executing Platform.devicedeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void mesetready() {
        MerodeLogger.logln("Executing Platform.mesetready() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrplatform() throws MerodeException {
        MerodeLogger.log("Checking Platform.mecrplatform...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendplatform() throws MerodeException {
        MerodeLogger.log("Checking Platform.meendplatform()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendplatform()\" in class Platform:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrplatformdeployment() throws MerodeException {
        MerodeLogger.log("Checking Platform.mecrplatformdeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendplatformdeployment() throws MerodeException {
        MerodeLogger.log("Checking Platform.meendplatformdeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrdeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Platform.mecrdeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Platform.meenddeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrregistereddevice() throws MerodeException {
        MerodeLogger.log("Checking Platform.mecrregistereddevice()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendregistereddevice() throws MerodeException {
        MerodeLogger.log("Checking Platform.meendregistereddevice()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrdeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Platform.mecrdeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Platform.meenddeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_deviceundeployment() throws MerodeException {
        MerodeLogger.log("Checking Platform.deviceundeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_devicedeployment() throws MerodeException {
        MerodeLogger.log("Checking Platform.devicedeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mesetready() throws MerodeException {
        MerodeLogger.log("Checking Platform.mesetready()...");
        MerodeLogger.logln("passed");
    }

    


    // -------------------------------------------------------
    
    private boolean hasLivingDependents() {
    	Set <String> dependents = new HashSet();

        java.util.Collection col_platformdeployment = getPlatformdeployment();
        if (col_platformdeployment != null){
        	if (!col_platformdeployment.isEmpty()){
		        java.util.Iterator i_platformdeployment = col_platformdeployment.iterator();
		        while (i_platformdeployment.hasNext()) {
		            dao.Platformdeployment obj_platformdeployment = (dao.Platformdeployment)i_platformdeployment.next();
		            if (!obj_platformdeployment.getState().isFinalState()){
		            	dependents.add("Platformdeployment");
		            }
		        }        	
        	}
        }


        java.util.Collection col_registereddevice = getRegistereddevice();
        if (col_registereddevice != null){
        	if (!col_registereddevice.isEmpty()){
		        java.util.Iterator i_registereddevice = col_registereddevice.iterator();
		        while (i_registereddevice.hasNext()) {
		            dao.Registereddevice obj_registereddevice = (dao.Registereddevice)i_registereddevice.next();
		            if (!obj_registereddevice.getState().isFinalState()){
		            	dependents.add("Registereddevice");
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
