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
 *    discriminator-value="RegistereddeviceImpl"
 */
public class RegistereddeviceImpl extends Registereddevice {

	private String livingDependents = "";

	// constructor
    public RegistereddeviceImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Registereddevice ...

	//--- o/c ---  
    public void mecrregistereddevice(java.lang.String Name) {
        MerodeLogger.logln("Executing Registereddevice.mecrregistereddevice (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendregistereddevice() {
        MerodeLogger.logln("Executing Registereddevice.meendregistereddevice() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrdeviceresult() {
        MerodeLogger.logln("Executing Registereddevice.mecrdeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceresult() {
        MerodeLogger.logln("Executing Registereddevice.meenddeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrdeviceusage() {
        MerodeLogger.logln("Executing Registereddevice.mecrdeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceusage() {
        MerodeLogger.logln("Executing Registereddevice.meenddeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void deviceundeployment() {
        MerodeLogger.logln("Executing Registereddevice.deviceundeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void devicedeployment() {
        MerodeLogger.logln("Executing Registereddevice.devicedeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void mesetready() {
        MerodeLogger.logln("Executing Registereddevice.mesetready() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrregistereddevice() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.mecrregistereddevice...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendregistereddevice() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.meendregistereddevice()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendregistereddevice()\" in class Registereddevice:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrdeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.mecrdeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.meenddeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrdeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.mecrdeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.meenddeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_deviceundeployment() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.deviceundeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_devicedeployment() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.devicedeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mesetready() throws MerodeException {
        MerodeLogger.log("Checking Registereddevice.mesetready()...");
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
