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

import ui.tabs.tables.Registereddevice_Table;
import ui.tabs.tables.Procedure_Table;

import java.awt.*;
import java.util.Collection;

/**
 * A Swing frame that has
 * - an attribute table
 * - tables referencing to master and dependent object sets
 * - a button OK
 */
@SuppressWarnings("serial")
public class DeviceViewWindow extends ObjectViewPanel {

    public DeviceViewWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, dao.Device obj) {
        super (main_frame, main_handler, "Device", obj);
        setTitledBorder(obj);
    }
    public DeviceViewWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, String id) throws Exception {
    	super (main_frame, main_handler, "Device", main_handler.searchDeviceById(id));
		setTitledBorder(main_handler.searchDeviceById(id));
	}
	public void setTitledBorder(dao.Device obj){
    	setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Device : " + obj.getName()),
                	BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    protected void constructMasterTables (){
    }
    
    protected void constructDependentTables (){
		constructregistereddeviceTable();
		constructprocedureTable();
    }
    
    protected void completeAttributesPane() {
    	
		String headers[] = {"<html><b>Attribute</b></html>", "<html><b>Value</b></html>"};
		Object[][] data = { { "<html><font color='blue'>GeneratedID</font></html>", ((dao.Device)object).getId() },
	            			{ "<html><font color='blue'>Name</font></html>", ((dao.Device)object).getName()},
	            			{ "<html><font color='blue'>state</font></html>", ((dao.Device)object).getState().getName() }, 
	            			{ "<html><font color='blue'>finalized</font></html>", ((dao.Device)object).getState().isFinalState() }, 
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
    

    private Registereddevice_Table t_registereddevice;
    protected void constructregistereddeviceTable() {
    	t_registereddevice = new Registereddevice_Table (main_handler, getRegistereddeviceObjects());
     	//temporarily set refresh for each table
    	//t_registereddevice.refresh();
    	   
    	t_registereddevice.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_registereddevice = new JScrollPane(t_registereddevice);

        JLabel lbl = new JLabel("Registereddevice", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        dependents_p.add (left);
        
        dependents_p.add (jsp_registereddevice);
    }

    private Collection<?> getRegistereddeviceObjects() {
        return ((dao.Device)object).getRegistereddevice();
    }
    
    private Procedure_Table t_procedure;
    protected void constructprocedureTable() {
    	t_procedure = new Procedure_Table (main_handler, getProcedureObjects());
     	//temporarily set refresh for each table
    	//t_procedure.refresh();
    	   
    	t_procedure.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_procedure = new JScrollPane(t_procedure);

        JLabel lbl = new JLabel("Procedure", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        dependents_p.add (left);
        
        dependents_p.add (jsp_procedure);
    }

    private Collection<?> getProcedureObjects() {
        return ((dao.Device)object).getProcedure();
    }
    


}



