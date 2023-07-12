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

import ui.tabs.tables.Featureofinterest_Table;
import ui.tabs.tables.Deviceusage_Table;

import java.awt.*;
import java.util.Collection;

/**
 * A Swing frame that has
 * - an attribute table
 * - tables referencing to master and dependent object sets
 * - a button OK
 */
@SuppressWarnings("serial")
public class PropertyViewWindow extends ObjectViewPanel {

    public PropertyViewWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, dao.Property obj) {
        super (main_frame, main_handler, "Property", obj);
        setTitledBorder(obj);
    }
    public PropertyViewWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, String id) throws Exception {
    	super (main_frame, main_handler, "Property", main_handler.searchPropertyById(id));
		setTitledBorder(main_handler.searchPropertyById(id));
	}
	public void setTitledBorder(dao.Property obj){
    	setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Property : " + obj.getName()),
                	BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    protected void constructMasterTables (){

 		constructfeatureofinterestTable();
    }
    
    protected void constructDependentTables (){
		constructdeviceusageTable();
    }
    
    protected void completeAttributesPane() {
    	
		String headers[] = {"<html><b>Attribute</b></html>", "<html><b>Value</b></html>"};
		Object[][] data = { { "<html><font color='blue'>GeneratedID</font></html>", ((dao.Property)object).getId() },
	            			{ "<html><font color='blue'>Name</font></html>", ((dao.Property)object).getName()},
	            			{ "<html><font color='blue'>state</font></html>", ((dao.Property)object).getState().getName() }, 
	            			{ "<html><font color='blue'>finalized</font></html>", ((dao.Property)object).getState().isFinalState() }, 
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
    
    private Featureofinterest_Table t_featureofinterest;	
    protected void constructfeatureofinterestTable() {
    	t_featureofinterest = new Featureofinterest_Table (main_handler, ((dao.Featureofinterest)getFeatureofinterestObject()).getId());
    	//temporarily set refresh for each table
    	//t_featureofinterest.refresh();
    	
    	t_featureofinterest.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_featureofinterest = new JScrollPane(t_featureofinterest);

        JLabel lbl = new JLabel("Featureofinterest", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        masters_p.add (left);
        
        masters_p.add (jsp_featureofinterest);

    }
    
    private Object getFeatureofinterestObject() {
        return ((dao.Property)object).getFeatureofinterest();
    }


    private Deviceusage_Table t_deviceusage;
    protected void constructdeviceusageTable() {
    	t_deviceusage = new Deviceusage_Table (main_handler, getDeviceusageObjects());
     	//temporarily set refresh for each table
    	//t_deviceusage.refresh();
    	   
    	t_deviceusage.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_deviceusage = new JScrollPane(t_deviceusage);

        JLabel lbl = new JLabel("Deviceusage", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        dependents_p.add (left);
        
        dependents_p.add (jsp_deviceusage);
    }

    private Collection<?> getDeviceusageObjects() {
        return ((dao.Property)object).getDeviceusage();
    }
    


}



