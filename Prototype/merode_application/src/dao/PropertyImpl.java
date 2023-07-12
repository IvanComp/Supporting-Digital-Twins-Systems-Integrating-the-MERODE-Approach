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
 *    discriminator-value="PropertyImpl"
 */
public class PropertyImpl extends Property {

	private String livingDependents = "";

	// constructor
    public PropertyImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Property ...

	//--- o/c ---  
    public void mecrproperty(java.lang.String Name) {
        MerodeLogger.logln("Executing Property.mecrproperty (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendproperty() {
        MerodeLogger.logln("Executing Property.meendproperty() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrdeviceresult() {
        MerodeLogger.logln("Executing Property.mecrdeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceresult() {
        MerodeLogger.logln("Executing Property.meenddeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrdeviceusage() {
        MerodeLogger.logln("Executing Property.mecrdeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceusage() {
        MerodeLogger.logln("Executing Property.meenddeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void deviceundeployment() {
        MerodeLogger.logln("Executing Property.deviceundeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void devicedeployment() {
        MerodeLogger.logln("Executing Property.devicedeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void mesetready() {
        MerodeLogger.logln("Executing Property.mesetready() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrproperty() throws MerodeException {
        MerodeLogger.log("Checking Property.mecrproperty...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendproperty() throws MerodeException {
        MerodeLogger.log("Checking Property.meendproperty()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendproperty()\" in class Property:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrdeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Property.mecrdeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Property.meenddeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrdeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Property.mecrdeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Property.meenddeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_deviceundeployment() throws MerodeException {
        MerodeLogger.log("Checking Property.deviceundeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_devicedeployment() throws MerodeException {
        MerodeLogger.log("Checking Property.devicedeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mesetready() throws MerodeException {
        MerodeLogger.log("Checking Property.mesetready()...");
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
