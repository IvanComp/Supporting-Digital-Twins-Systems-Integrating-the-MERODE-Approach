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
 *    discriminator-value="ProcedureImpl"
 */
public class ProcedureImpl extends Procedure {

	private String livingDependents = "";

	// constructor
    public ProcedureImpl() {
    }

    // ---------------- business methods  ----------------------
    // concrete business methods that were declared
    // abstract in class Procedure ...

	//--- o/c ---  
    public void mecrprocedure(java.lang.String Name) {
        MerodeLogger.logln("Executing Procedure.mecrprocedure (O/C)");
        setName (Name);
    }


		//--- o/e ---
	public void meendprocedure() {
        MerodeLogger.logln("Executing Procedure.meendprocedure() (O/E)");
    }
	
		//--- o/dpnd ---
    public void mecrdeviceresult() {
        MerodeLogger.logln("Executing Procedure.mecrdeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceresult() {
        MerodeLogger.logln("Executing Procedure.meenddeviceresult() (A/M)");
    }

		//--- o/dpnd ---
    public void mecrdeviceusage() {
        MerodeLogger.logln("Executing Procedure.mecrdeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void meenddeviceusage() {
        MerodeLogger.logln("Executing Procedure.meenddeviceusage() (A/M)");
    }

		//--- o/dpnd ---
    public void deviceundeployment() {
        MerodeLogger.logln("Executing Procedure.deviceundeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void devicedeployment() {
        MerodeLogger.logln("Executing Procedure.devicedeployment() (A/M)");
    }

		//--- o/dpnd ---
    public void mesetready() {
        MerodeLogger.logln("Executing Procedure.mesetready() (A/M)");
    }

	

    // ---------- precondition of business methods  -----------
    
	//--- o/e --- 
    public void check_mecrprocedure() throws MerodeException {
        MerodeLogger.log("Checking Procedure.mecrprocedure...");
        MerodeLogger.logln("passed");
    }

	//--- o/e ---
    public void check_meendprocedure() throws MerodeException {
        MerodeLogger.log("Checking Procedure.meendprocedure()...");

        if(hasLivingDependents()){
            throw new MerodeException ("Precondition violation in ending method \"meendprocedure()\" in class Procedure:\nObject has living dependents " + livingDependents);
        }
        MerodeLogger.logln("passed");
    }
   

	//--- o/dpnds ---
    public void check_mecrdeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Procedure.mecrdeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceresult() throws MerodeException {
        MerodeLogger.log("Checking Procedure.meenddeviceresult()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mecrdeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Procedure.mecrdeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_meenddeviceusage() throws MerodeException {
        MerodeLogger.log("Checking Procedure.meenddeviceusage()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_deviceundeployment() throws MerodeException {
        MerodeLogger.log("Checking Procedure.deviceundeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_devicedeployment() throws MerodeException {
        MerodeLogger.log("Checking Procedure.devicedeployment()...");
        MerodeLogger.logln("passed");
    }
	//--- o/dpnds ---
    public void check_mesetready() throws MerodeException {
        MerodeLogger.log("Checking Procedure.mesetready()...");
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
