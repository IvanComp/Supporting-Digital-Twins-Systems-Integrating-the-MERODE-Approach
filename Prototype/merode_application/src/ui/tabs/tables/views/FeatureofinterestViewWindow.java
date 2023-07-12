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

import ui.tabs.tables.Property_Table;
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
public class FeatureofinterestViewWindow extends ObjectViewPanel {

    public FeatureofinterestViewWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, dao.Featureofinterest obj) {
        super (main_frame, main_handler, "Featureofinterest", obj);
        setTitledBorder(obj);
    }
    public FeatureofinterestViewWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, String id) throws Exception {
    	super (main_frame, main_handler, "Featureofinterest", main_handler.searchFeatureofinterestById(id));
		setTitledBorder(main_handler.searchFeatureofinterestById(id));
	}
	public void setTitledBorder(dao.Featureofinterest obj){
    	setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Featureofinterest : " + obj.getName()),
                	BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    protected void constructMasterTables (){
    }
    
    protected void constructDependentTables (){
		constructplatformdeploymentTable();
		constructpropertyTable();
    }
    
    protected void completeAttributesPane() {
    	
		String headers[] = {"<html><b>Attribute</b></html>", "<html><b>Value</b></html>"};
		Object[][] data = { { "<html><font color='blue'>GeneratedID</font></html>", ((dao.Featureofinterest)object).getId() },
	            			{ "<html><font color='blue'>Name</font></html>", ((dao.Featureofinterest)object).getName()},
	            			{ "<html><font color='blue'>state</font></html>", ((dao.Featureofinterest)object).getState().getName() }, 
	            			{ "<html><font color='blue'>finalized</font></html>", ((dao.Featureofinterest)object).getState().isFinalState() }, 
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
        return ((dao.Featureofinterest)object).getPlatformdeployment();
    }
    
    private Property_Table t_property;
    protected void constructpropertyTable() {
    	t_property = new Property_Table (main_handler, getPropertyObjects());
     	//temporarily set refresh for each table
    	//t_property.refresh();
    	   
    	t_property.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_property = new JScrollPane(t_property);

        JLabel lbl = new JLabel("Property", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        dependents_p.add (left);
        
        dependents_p.add (jsp_property);
    }

    private Collection<?> getPropertyObjects() {
        return ((dao.Featureofinterest)object).getProperty();
    }
    


}



