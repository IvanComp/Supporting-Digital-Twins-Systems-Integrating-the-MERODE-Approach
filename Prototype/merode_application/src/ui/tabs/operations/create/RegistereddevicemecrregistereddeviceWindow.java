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
import ui.tabs.operations.RegistereddeviceOperationWindow;
import ui.tabs.tables.Platform_Table;
import ui.tabs.tables.Device_Table;

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
public class RegistereddevicemecrregistereddeviceWindow extends RegistereddeviceOperationWindow {
    
    public RegistereddevicemecrregistereddeviceWindow (MerodeMainEventHandlerGui main_frame, MerodeMainEventHandler main_handler, ObjectListWindow list_frame) {
        super(main_frame, main_handler, list_frame, "mecrregistereddevice");
        buildFrame(getContentPane());
    }

	
    private Platform_Table t_platform;
    
    protected void constructplatformTable(Container p) {
        t_platform = new Platform_Table (main_handler);
    
        t_platform.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_platform = new JScrollPane(t_platform);
       
        JLabel lbl = new JLabel("Platform", JLabel.LEFT);
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

        p.add (lbl, "wrap" );
        p.add (jsp_platform, "wrap" );
    }
	
    private Device_Table t_device;
    
    protected void constructdeviceTable(Container p) {
        t_device = new Device_Table (main_handler);
    
        t_device.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_device = new JScrollPane(t_device);
       
        JLabel lbl = new JLabel("Device", JLabel.LEFT);
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

        p.add (lbl, "wrap" );
        p.add (jsp_device, "wrap" );
    }

    protected void executeMethod() {
        try {
            boolean hasErrors = false;
            String errors = "";
			if (t_platform.getSelectedRow()<0) {
                hasErrors = true;
                errors = errors + "Please select master: Platform\n";
            }
			if (t_device.getSelectedRow()<0) {
                hasErrors = true;
                errors = errors + "Please select master: Device\n";
            }

            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            if (t_Name.getText().trim().length()==0) {
                hasErrors = true;
                errors = errors + "Please fill in attribute: Name\n";
            }
 
            if (hasErrors) {
                Field[] privateMembers = this.getClass().getDeclaredFields();
                String objName = this.getTitle().substring(0, this.getTitle().lastIndexOf(":")).toUpperCase();
            	ErrorValidator.processErrors(errors, privateMembers, objName);
            } else {
                boolean success = main_handler.mecrregistereddevice (
                	((dao.Platform)t_platform.objects.get(t_platform.getSelectedRow())).getId(), 
                	((dao.Device)t_device.objects.get(t_device.getSelectedRow())).getId(), 
                t_Name.getText());
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