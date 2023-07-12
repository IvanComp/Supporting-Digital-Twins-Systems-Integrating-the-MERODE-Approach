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
import ui.tabs.tables.Platformdeployment_Table;

import java.awt.*;
import java.util.Collection;

/**
 * A Swing frame that has
 * - an attribute table
 * - tables referencing to master and dependent object sets
 * - a button OK
 */
@SuppressWarnings("serial")
public class PlatformViewWindow extends ObjectViewPanel {

    public PlatformViewWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, dao.Platform obj) {
        super (main_frame, main_handler, "Platform", obj);
        setTitledBorder(obj);
    }
    public PlatformViewWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, String id) throws Exception {
    	super (main_frame, main_handler, "Platform", main_handler.searchPlatformById(id));
		setTitledBorder(main_handler.searchPlatformById(id));
	}
	public void setTitledBorder(dao.Platform obj){
    	setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Platform : " + obj.getName()),
                	BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    protected void constructMasterTables (){
    }
    
    protected void constructDependentTables (){
		constructplatformdeploymentTable();
		constructregistereddeviceTable();
    }
    
    protected void completeAttributesPane() {
    	
		String headers[] = {"<html><b>Attribute</b></html>", "<html><b>Value</b></html>"};
		Object[][] data = { { "<html><font color='blue'>GeneratedID</font></html>", ((dao.Platform)object).getId() },
	            			{ "<html><font color='blue'>Name</font></html>", ((dao.Platform)object).getName()},
	            			{ "<html><font color='blue'>state</font></html>", ((dao.Platform)object).getState().getName() }, 
	            			{ "<html><font color='blue'>finalized</font></html>", ((dao.Platform)object).getState().isFinalState() }, 
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
    	t_platformdeployment = new Platformdeployment_Table (main_handler, getPlatformdeploymentObjects());
     	//temporarily set refresh for each table
    	//t_platformdeployment.refresh();
    	   
    	t_platformdeployment.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_platformdeployment = new JScrollPane(t_platformdeployment);

        JLabel lbl = new JLabel("Platformdeployment", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        dependents_p.add (left);
        
        dependents_p.add (jsp_platformdeployment);
    }

    private Collection<?> getPlatformdeploymentObjects() {
        return ((dao.Platform)object).getPlatformdeployment();
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
        return ((dao.Platform)object).getRegistereddevice();
    }
    


}



