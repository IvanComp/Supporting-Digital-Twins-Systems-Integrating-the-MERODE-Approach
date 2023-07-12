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
public class DeviceresultViewWindow extends ObjectViewPanel {

    public DeviceresultViewWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, dao.Deviceresult obj) {
        super (main_frame, main_handler, "Deviceresult", obj);
        setTitledBorder(obj);
    }
    public DeviceresultViewWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, String id) throws Exception {
    	super (main_frame, main_handler, "Deviceresult", main_handler.searchDeviceresultById(id));
		setTitledBorder(main_handler.searchDeviceresultById(id));
	}
	public void setTitledBorder(dao.Deviceresult obj){
    	setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Deviceresult : " + obj.getTime()),
                	BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    protected void constructMasterTables (){

 		constructdeviceusageTable();
    }
    
    protected void constructDependentTables (){
    }
    
    protected void completeAttributesPane() {
    	
		String headers[] = {"<html><b>Attribute</b></html>", "<html><b>Value</b></html>"};
		Object[][] data = { { "<html><font color='blue'>GeneratedID</font></html>", ((dao.Deviceresult)object).getId() },
	            			{ "<html><font color='blue'>Time</font></html>", ((dao.Deviceresult)object).getTime()},
	            			{ "<html><font color='blue'>Value</font></html>", ((dao.Deviceresult)object).getValue()},
	            			{ "<html><font color='blue'>state</font></html>", ((dao.Deviceresult)object).getState().getName() }, 
	            			{ "<html><font color='blue'>finalized</font></html>", ((dao.Deviceresult)object).getState().isFinalState() }, 
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
    
    private Deviceusage_Table t_deviceusage;	
    protected void constructdeviceusageTable() {
    	t_deviceusage = new Deviceusage_Table (main_handler, ((dao.Deviceusage)getDeviceusageObject()).getId());
    	//temporarily set refresh for each table
    	//t_deviceusage.refresh();
    	
    	t_deviceusage.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_deviceusage = new JScrollPane(t_deviceusage);

        JLabel lbl = new JLabel("Deviceusage", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() ^ Font.BOLD));
        left.add( lbl );
        masters_p.add (left);
        
        masters_p.add (jsp_deviceusage);

    }
    
    private Object getDeviceusageObject() {
        return ((dao.Deviceresult)object).getDeviceusage();
    }




}



