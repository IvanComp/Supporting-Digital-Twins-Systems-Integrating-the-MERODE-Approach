/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */



package ui.tabs.operations.modify;

import handlers.MerodeMainEventHandler;

import javax.swing.*;
import java.awt.*;

import ui.tabs.lists.ObjectListWindow;
import ui.tabs.operations.DeviceusageOperationWindow;

import ui.tabs.tables.Platformdeployment_Table;
import ui.tabs.tables.Registereddevice_Table;
import ui.tabs.tables.Procedure_Table;
import ui.tabs.tables.Property_Table;

import validation.ErrorValidator;


import java.lang.reflect.Field;
import java.text.*;

import com.toedter.calendar.JDateChooser;

import dao.MerodeException;
import driver.MerodeMainEventHandlerGui;


/**
 * Windows to modify objects. They have:
 * - One textfield for each attribute; all fields contain the current value
 * - One object table per master object; the master object is shown and can
 *   not be changed
 * - One button to execute the operation
 */
public class DeviceusagedevicedeploymentWindow extends DeviceusageOperationWindow {
    private dao.Deviceusage object;

    public DeviceusagedevicedeploymentWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, 
    			ObjectListWindow list_frame, 
    				dao.Deviceusage obj) {
        super(main_frame, main_handler, list_frame, "devicedeployment");
        object = obj;
        buildFrame(getContentPane());
        fillTextFields();
    }
  
    protected void constructplatformdeploymentTable(Container p) {
        Platformdeployment_Table t_platformdeployment = new Platformdeployment_Table(main_handler, object.getPlatformdeployment().getId());
    
        t_platformdeployment.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_platformdeployment = new JScrollPane(t_platformdeployment);

        JLabel lbl = new JLabel("Platformdeployment", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
        left.add( lbl );
        p.add (left, "wrap" );
        p.add (jsp_platformdeployment, "wrap" );
    }
    protected void constructregistereddeviceTable(Container p) {
        Registereddevice_Table t_registereddevice = new Registereddevice_Table(main_handler, object.getRegistereddevice().getId());
    
        t_registereddevice.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_registereddevice = new JScrollPane(t_registereddevice);

        JLabel lbl = new JLabel("Registereddevice", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
        left.add( lbl );
        p.add (left, "wrap" );
        p.add (jsp_registereddevice, "wrap" );
    }
    protected void constructprocedureTable(Container p) {
        Procedure_Table t_procedure = new Procedure_Table(main_handler, object.getProcedure().getId());
    
        t_procedure.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_procedure = new JScrollPane(t_procedure);

        JLabel lbl = new JLabel("Procedure", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
        left.add( lbl );
        p.add (left, "wrap" );
        p.add (jsp_procedure, "wrap" );
    }
    protected void constructpropertyTable(Container p) {
        Property_Table t_property = new Property_Table(main_handler, object.getProperty().getId());
    
        t_property.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_property = new JScrollPane(t_property);

        JLabel lbl = new JLabel("Property", JLabel.LEFT);
        JPanel left = new JPanel( new FlowLayout(FlowLayout.LEFT) );
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
        left.add( lbl );
        p.add (left, "wrap" );
        p.add (jsp_property, "wrap" );
    }
    
    private void fillTextFields() {
        t_Name.setText (object.getName());

    }

    protected void executeMethod() {
        try {
            boolean hasErrors = false;
            String errors = "";
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
                main_handler.devicedeployment (object.getId(), 
                							t_Name.getText());
        
                //list_frame.table.refresh();
                main_frame.refreshTables();
		
                dispose();
                list_frame.setVisible(true);
            }
        } catch (MerodeException me) {
        	ErrorValidator.processExceptions(me);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
