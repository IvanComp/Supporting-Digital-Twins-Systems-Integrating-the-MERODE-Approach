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
import ui.tabs.operations.PropertyOperationWindow;
import ui.tabs.tables.Featureofinterest_Table;

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
public class PropertymecrpropertyWindow extends PropertyOperationWindow {
    
    public PropertymecrpropertyWindow (MerodeMainEventHandlerGui main_frame, MerodeMainEventHandler main_handler, ObjectListWindow list_frame) {
        super(main_frame, main_handler, list_frame, "mecrproperty");
        buildFrame(getContentPane());
    }

	
    private Featureofinterest_Table t_featureofinterest;
    
    protected void constructfeatureofinterestTable(Container p) {
        t_featureofinterest = new Featureofinterest_Table (main_handler);
    
        t_featureofinterest.setPreferredScrollableViewportSize(new Dimension(560, 120));
        JScrollPane jsp_featureofinterest = new JScrollPane(t_featureofinterest);
       
        JLabel lbl = new JLabel("Featureofinterest", JLabel.LEFT);
        lbl.setForeground(new Color(145, 33, 158));
        Font f = lbl.getFont();
        lbl.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));

        p.add (lbl, "wrap" );
        p.add (jsp_featureofinterest, "wrap" );
    }

    protected void executeMethod() {
        try {
            boolean hasErrors = false;
            String errors = "";
			if (t_featureofinterest.getSelectedRow()<0) {
                hasErrors = true;
                errors = errors + "Please select master: Featureofinterest\n";
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
                boolean success = main_handler.mecrproperty (
                	((dao.Featureofinterest)t_featureofinterest.objects.get(t_featureofinterest.getSelectedRow())).getId(), 
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