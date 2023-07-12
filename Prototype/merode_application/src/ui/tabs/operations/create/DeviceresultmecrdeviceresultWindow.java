/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */

package ui.tabs.operations.create;

import handlers.MerodeMainEventHandler;

import javax.swing.*;

import validation.ErrorValidator;

import java.awt.*;

import ui.tabs.lists.ObjectListWindow;
import ui.tabs.operations.DeviceresultOperationWindow;
import ui.tabs.tables.Deviceusage_Table;

import java.lang.reflect.Field;
import java.text.*;

import com.toedter.calendar.JDateChooser;

import dao.MerodeException;
import driver.MerodeMainEventHandlerGui;


/**
 * Windows to create objects. They have:
 * - One textfield for each attribute
 * - One object table per master object
 * - One button to execute the operation
 */
@SuppressWarnings("serial")
public class DeviceresultmecrdeviceresultWindow extends DeviceresultOperationWindow {
    
    public DeviceresultmecrdeviceresultWindow (MerodeMainEventHandlerGui main_frame, MerodeMainEventHandler main_handler, ObjectListWindow list_frame) {
        super(main_frame, main_handler, list_frame, "mecrdeviceresult");
        buildFrame(getContentPane());
    }

	
    private Deviceusage_Table t_deviceusage;
    
    protected void constructdeviceusageTable(Container p) {
        t_deviceusage = new Deviceusage_Table (main_handler);
    
        t_deviceusage.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_deviceusage = new JScrollPane(t_deviceusage);
       
        JLabel lbl = new JLabel("Deviceusage", JLabel.LEFT);
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

        p.add (lbl, "wrap" );
        p.add (jsp_deviceusage, "wrap" );
    }

    protected void executeMethod() {
        try {
            boolean hasErrors = false;
            String errors = "";
			if (t_deviceusage.getSelectedRow()<0) {
                hasErrors = true;
                errors = errors + "Please select master: Deviceusage\n";
            }

            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            if (t_Time.getText().trim().length()==0) {
                hasErrors = true;
                errors = errors + "Please fill in attribute: Time\n";
            }
             else {
            	try {
                	df.parse(t_Time.getText());        		
            	}  	catch(ParseException e)
            	{
            		//not Time input
                    hasErrors = true;
                    errors = errors + "Please provide a valid DATE/TIME input, e.g. 2000-01-01, or choose with a date picker: Time\n";
            	}
	         }
 
            if (t_Value.getText().trim().length()==0) {
                hasErrors = true;
                errors = errors + "Please fill in attribute: Value\n";
            }
 
            if (hasErrors) {
                Field[] privateMembers = this.getClass().getDeclaredFields();
                String objName = this.getTitle().substring(0, this.getTitle().lastIndexOf(":")).toUpperCase();
            	ErrorValidator.processErrors(errors, privateMembers, objName);
            } else {
                boolean success = main_handler.mecrdeviceresult (
                	((dao.Deviceusage)t_deviceusage.objects.get(t_deviceusage.getSelectedRow())).getId(), 
                 t_Time.getText(),t_Value.getText());
                if (success){
                    //list_frame.table.refresh();
                    main_frame.refreshTables();
                    
                    dispose();
                    list_frame.setVisible(true);              	
                }
            }
        } catch (MerodeException me) {
        	ErrorValidator.processExceptions(me);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}