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
 *    discriminator-value="DeviceImpl"
 */
public class DeviceImpl extends Device {

	private String livingDependents = "";

	// constructor
    public DeviceImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Device ...

	//--- o/c ---  
    public void mecrdevice(java.lang.String Name) {
        MerodeLogger.logln("Executing Device.mecrdevice (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meenddevice() {
        MerodeLogger.logln("Executing Device.meenddevice() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrdeviceresult() {
        MerodeLogger.logln("Executing Device.mecrdeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceresult() {
        MerodeLogger.logln("Executing Device.meenddeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrregistereddevice() {
        MerodeLogger.logln("Executing Device.mecrregistereddevice() (A/M)");
    }

		//--- o/dpnd ---
    public void meendregistereddevice() {
        MerodeLogger.logln("Executing Device.meendregistereddevice() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrprocedure() {
        MerodeLogger.logln("Executing Device.mecrprocedure() (A/M)");
    }

		//--- o/dpnd ---
    public void meendprocedure() {
        MerodeLogger.logln("Executing Device.meendprocedure() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrdeviceusage() {
        MerodeLogger.logln("Executing Device.mecrdeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceusage() {
        MerodeLogger.logln("Executing Device.meenddeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void deviceundeployment() {
        MerodeLogger.logln("Executing Device.deviceundeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void devicedeployment() {
        MerodeLogger.logln("Executing Device.devicedeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void mesetready() {
        MerodeLogger.logln("Executing Device.mesetready() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrdevice() throws MerodeException {
        MerodeLogger.log("Checking Device.mecrdevice...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meenddevice() throws MerodeException {
        MerodeLogger.log("Checking Device.meenddevice()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meenddevice()\" in class Device:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrdeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Device.mecrdeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Device.meenddeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrregistereddevice() throws MerodeException {
        MerodeLogger.log("Checking Device.mecrregistereddevice()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendregistereddevice() throws MerodeException {
        MerodeLogger.log("Checking Device.meendregistereddevice()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrprocedure() throws MerodeException {
        MerodeLogger.log("Checking Device.mecrprocedure()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meendprocedure() throws MerodeException {
        MerodeLogger.log("Checking Device.meendprocedure()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrdeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Device.mecrdeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Device.meenddeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_deviceundeployment() throws MerodeException {
        MerodeLogger.log("Checking Device.deviceundeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_devicedeployment() throws MerodeException {
        MerodeLogger.log("Checking Device.devicedeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mesetready() throws MerodeException {
        MerodeLogger.log("Checking Device.mesetready()...");
        MerodeLogger.logln("passed");
    }

    


    // -------------------------------------------------------
    
    private boolean hasLivingDependents() {
    	Set <String> dependents = new HashSet();

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


        java.util.Collection col_procedure = getProcedure();
        if (col_procedure != null){
        	if (!col_procedure.isEmpty()){
		        java.util.Iterator i_procedure = col_procedure.iterator();
		        while (i_procedure.hasNext()) {
		            dao.Procedure obj_procedure = (dao.Procedure)i_procedure.next();
		            if (!obj_procedure.getState().isFinalState()){
		            	dependents.add("Procedure");
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
