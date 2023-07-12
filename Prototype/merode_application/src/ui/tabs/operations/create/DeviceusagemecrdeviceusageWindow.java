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
import ui.tabs.operations.DeviceusageOperationWindow;
import ui.tabs.tables.Platformdeployment_Table;
import ui.tabs.tables.Registereddevice_Table;
import ui.tabs.tables.Procedure_Table;
import ui.tabs.tables.Property_Table;

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
public class DeviceusagemecrdeviceusageWindow extends DeviceusageOperationWindow {
    
    public DeviceusagemecrdeviceusageWindow (MerodeMainEventHandlerGui main_frame, MerodeMainEventHandler main_handler, ObjectListWindow list_frame) {
        super(main_frame, main_handler, list_frame, "mecrdeviceusage");
        buildFrame(getContentPane());
    }

	
    private Platformdeployment_Table t_platformdeployment;
    
    protected void constructplatformdeploymentTable(Container p) {
        t_platformdeployment = new Platformdeployment_Table (main_handler);
    
        t_platformdeployment.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_platformdeployment = new JScrollPane(t_platformdeployment);
       
        JLabel lbl = new JLabel("Platformdeployment", JLabel.LEFT);
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

        p.add (lbl, "wrap" );
        p.add (jsp_platformdeployment, "wrap" );
    }
	
    private Registereddevice_Table t_registereddevice;
    
    protected void constructregistereddeviceTable(Container p) {
        t_registereddevice = new Registereddevice_Table (main_handler);
    
        t_registereddevice.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_registereddevice = new JScrollPane(t_registereddevice);
       
        JLabel lbl = new JLabel("Registereddevice", JLabel.LEFT);
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

        p.add (lbl, "wrap" );
        p.add (jsp_registereddevice, "wrap" );
    }
	
    private Procedure_Table t_procedure;
    
    protected void constructprocedureTable(Container p) {
        t_procedure = new Procedure_Table (main_handler);
    
        t_procedure.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_procedure = new JScrollPane(t_procedure);
       
        JLabel lbl = new JLabel("Procedure", JLabel.LEFT);
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

        p.add (lbl, "wrap" );
        p.add (jsp_procedure, "wrap" );
    }
	
    private Property_Table t_property;
    
    protected void constructpropertyTable(Container p) {
        t_property = new Property_Table (main_handler);
    
        t_property.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_property = new JScrollPane(t_property);
       
        JLabel lbl = new JLabel("Property", JLabel.LEFT);
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

        p.add (lbl, "wrap" );
        p.add (jsp_property, "wrap" );
    }

    protected void executeMethod() {
        try {
            boolean hasErrors = false;
            String errors = "";
			if (t_platformdeployment.getSelectedRow()<0) {
                hasErrors = true;
                errors = errors + "Please select master: Platformdeployment\n";
            }
			if (t_registereddevice.getSelectedRow()<0) {
                hasErrors = true;
                errors = errors + "Please select master: Registereddevice\n";
            }
			if (t_procedure.getSelectedRow()<0) {
                hasErrors = true;
                errors = errors + "Please select master: Procedure\n";
            }
			if (t_property.getSelectedRow()<0) {
                hasErrors = true;
                errors = errors + "Please select master: Property\n";
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
                boolean success = main_handler.mecrdeviceusage (
                	((dao.Platformdeployment)t_platformdeployment.objects.get(t_platformdeployment.getSelectedRow())).getId(), 
                	((dao.Registereddevice)t_registereddevice.objects.get(t_registereddevice.getSelectedRow())).getId(), 
                	((dao.Procedure)t_procedure.objects.get(t_procedure.getSelectedRow())).getId(), 
                	((dao.Property)t_property.objects.get(t_property.getSelectedRow())).getId(), 
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