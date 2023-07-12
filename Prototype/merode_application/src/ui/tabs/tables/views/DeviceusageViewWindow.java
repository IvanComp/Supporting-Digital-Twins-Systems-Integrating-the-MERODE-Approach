/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */
 
package ui.tabs.tables.views;

import driver.MerodeMainEventHandlerGui;


import handlers.MerodeMainEventHandler;

import javax.swing.*;

import ui.tabs.tables.Platformdeployment_Table;
import ui.tabs.tables.Registereddevice_Table;
import ui.tabs.tables.Procedure_Table;
import ui.tabs.tables.Property_Table;
import ui.tabs.tables.Deviceresult_Table;

import java.awt.*;
import java.util.Collection;

/**
 * A Swing frame that has
 * - an attribute table
 * - tables referencing to master and dependent object sets
 * - a button OK
 */
@SuppressWarnings("serial")
public class DeviceusageViewWindow extends ObjectViewPanel {

    public DeviceusageViewWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, dao.Deviceusage obj) {
        super (main_frame, main_handler, "Deviceusage", obj);
        setTitledBorder(obj);
    }
    public DeviceusageViewWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, String id) throws Exception {
    	super (main_frame, main_handler, "Deviceusage", main_handler.searchDeviceusageById(id));
		setTitledBorder(main_handler.searchDeviceusageById(id));
	}
	public void setTitledBorder(dao.Deviceusage obj){
    	setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Deviceusage : " + obj.getName()),
                	BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    protected void constructMasterTables (){

 		constructplatformdeploymentTable();

 		constructregistereddeviceTable();

 		constructprocedureTable();

 		constructpropertyTable();
    }
    
    protected void constructDependentTables (){
		constructdeviceresultTable();
    }
    
    protected void completeAttributesPane() {
    	
		String headers[] = {"<html><b>Attribute</b></html>", "<html><b>Value</b></html>"};
		Object[][] data = { { "<html><font color='blue'>GeneratedID</font></html>", ((dao.Deviceusage)object).getId() },
	            			{ "<html><font color='blue'>Name</font></html>", ((dao.Deviceusage)object).getName()},
	            			{ "<html><font color='blue'>state</font></html>", ((dao.Deviceusage)object).getState().getName() }, 
	            			{ "<html><font color='blue'>finalized</font></html>", ((dao.Deviceusage)object).getState().isFinalState() }, 
	            		  };
	    
	    JTable jt = new JTable( data, headers );
	    JScrollPane sp = new JScrollPane( jt );
	    sp.setPreferredSize(new Dimension(300, 100));
	    jt.setForeground(Color.BLACK);
	    jt.setBackground(Color.LIGHT_GRAY);
	    jt.setEnabled(false);
	    //jt.setTableHeader(null);
	    //sp.setColumnHeaderView(null);
	    attributes_p.add( sp );
	    
        attributes_p.add (Box.createVerticalGlue());

    }
    
    private Platformdeployment_Table t_platformdeployment;	
    protected void constructplatformdeploymentTable() {
    	t_platformdeployment = new Platformdeployment_Table (main_handler, ((dao.Platformdeployment)getPlatformdeploymentObject()).getId());
    	//temporarily set refresh for each table
    	//t_platformdeployment.refresh();
    	
    	t_platformdeployment.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_platformdeployment = new JScrollPane(t_platformdeployment);

        JLabel lbl = new JLabel("Platformdeployment", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        masters_p.add (left);
        
        masters_p.add (jsp_platformdeployment);

    }
    
    private Object getPlatformdeploymentObject() {
        return ((dao.Deviceusage)object).getPlatformdeployment();
    }

    private Registereddevice_Table t_registereddevice;	
    protected void constructregistereddeviceTable() {
    	t_registereddevice = new Registereddevice_Table (main_handler, ((dao.Registereddevice)getRegistereddeviceObject()).getId());
    	//temporarily set refresh for each table
    	//t_registereddevice.refresh();
    	
    	t_registereddevice.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_registereddevice = new JScrollPane(t_registereddevice);

        JLabel lbl = new JLabel("Registereddevice", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        masters_p.add (left);
        
        masters_p.add (jsp_registereddevice);

    }
    
    private Object getRegistereddeviceObject() {
        return ((dao.Deviceusage)object).getRegistereddevice();
    }

    private Procedure_Table t_procedure;	
    protected void constructprocedureTable() {
    	t_procedure = new Procedure_Table (main_handler, ((dao.Procedure)getProcedureObject()).getId());
    	//temporarily set refresh for each table
    	//t_procedure.refresh();
    	
    	t_procedure.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_procedure = new JScrollPane(t_procedure);

        JLabel lbl = new JLabel("Procedure", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        masters_p.add (left);
        
        masters_p.add (jsp_procedure);

    }
    
    private Object getProcedureObject() {
        return ((dao.Deviceusage)object).getProcedure();
    }

    private Property_Table t_property;	
    protected void constructpropertyTable() {
    	t_property = new Property_Table (main_handler, ((dao.Property)getPropertyObject()).getId());
    	//temporarily set refresh for each table
    	//t_property.refresh();
    	
    	t_property.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_property = new JScrollPane(t_property);

        JLabel lbl = new JLabel("Property", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        masters_p.add (left);
        
        masters_p.add (jsp_property);

    }
    
    private Object getPropertyObject() {
        return ((dao.Deviceusage)object).getProperty();
    }


    private Deviceresult_Table t_deviceresult;
    protected void constructdeviceresultTable() {
    	t_deviceresult = new Deviceresult_Table (main_handler, getDeviceresultObjects());
     	//temporarily set refresh for each table
    	//t_deviceresult.refresh();
    	   
    	t_deviceresult.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_deviceresult = new JScrollPane(t_deviceresult);

        JLabel lbl = new JLabel("Deviceresult", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        dependents_p.add (left);
        
        dependents_p.add (jsp_deviceresult);
    }

    private Collection<?> getDeviceresultObjects() {
        return ((dao.Deviceusage)object).getDeviceresult();
    }
    


}



