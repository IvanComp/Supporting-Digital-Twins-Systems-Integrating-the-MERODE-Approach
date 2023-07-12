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

import ui.tabs.tables.Platform_Table;
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
public class PlatformdeploymentViewWindow extends ObjectViewPanel {

    public PlatformdeploymentViewWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, dao.Platformdeployment obj) {
        super (main_frame, main_handler, "Platformdeployment", obj);
        setTitledBorder(obj);
    }
    public PlatformdeploymentViewWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, String id) throws Exception {
    	super (main_frame, main_handler, "Platformdeployment", main_handler.searchPlatformdeploymentById(id));
		setTitledBorder(main_handler.searchPlatformdeploymentById(id));
	}
	public void setTitledBorder(dao.Platformdeployment obj){
    	setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Platformdeployment : " + obj.getName()),
                	BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    protected void constructMasterTables (){

 		constructplatformTable();

 		constructfeatureofinterestTable();
    }
    
    protected void constructDependentTables (){
		constructdeviceusageTable();
    }
    
    protected void completeAttributesPane() {
    	
		String headers[] = {"<html><b>Attribute</b></html>", "<html><b>Value</b></html>"};
		Object[][] data = { { "<html><font color='blue'>GeneratedID</font></html>", ((dao.Platformdeployment)object).getId() },
	            			{ "<html><font color='blue'>Name</font></html>", ((dao.Platformdeployment)object).getName()},
	            			{ "<html><font color='blue'>state</font></html>", ((dao.Platformdeployment)object).getState().getName() }, 
	            			{ "<html><font color='blue'>finalized</font></html>", ((dao.Platformdeployment)object).getState().isFinalState() }, 
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
    
    private Platform_Table t_platform;	
    protected void constructplatformTable() {
    	t_platform = new Platform_Table (main_handler, ((dao.Platform)getPlatformObject()).getId());
    	//temporarily set refresh for each table
    	//t_platform.refresh();
    	
    	t_platform.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_platform = new JScrollPane(t_platform);

        JLabel lbl = new JLabel("Platform", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        masters_p.add (left);
        
        masters_p.add (jsp_platform);

    }
    
    private Object getPlatformObject() {
        return ((dao.Platformdeployment)object).getPlatform();
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
        return ((dao.Platformdeployment)object).getFeatureofinterest();
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
        return ((dao.Platformdeployment)object).getDeviceusage();
    }
    


}



