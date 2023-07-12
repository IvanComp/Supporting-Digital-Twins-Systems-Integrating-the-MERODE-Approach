/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */
 
package ui.tabs.lists;

import driver.MerodeMainEventHandlerGui;


import dao.MerodeException;
import dao.MerodeLogger;
import handlers.MerodeMainEventHandler;

import javax.swing.*;
import ui.tabs.operations.create.DeviceresultmecrdeviceresultWindow;
import ui.tabs.tables.Deviceresult_Table;
import ui.tabs.tables.renderers.ObjectTableRowRenderer;
import validation.ErrorValidator;

import java.awt.Component;
import java.awt.event.*;
import java.util.Collection;

/**
 * A Swing frame that has
 * - buttons to create, modify and destroy an object
 * - an object table
 */
@SuppressWarnings("serial")
public class DeviceresultListWindow extends ObjectListWindow {
    public DeviceresultListWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler) {
        super (main_frame, main_handler, "Deviceresult");
    }

    public DeviceresultListWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, 
    		String id) {
        super (main_frame, main_handler, "Deviceresult", id);
    }
    
    public DeviceresultListWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, Collection<?> col) {
        super (main_frame, main_handler, "Deviceresult", col);
	}

	protected void completeMethodPane() {
        JButton b_mecrdeviceresult = new JButton("mecrdeviceresult");
        b_mecrdeviceresult.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                execute_mecrdeviceresult();
            }
        });
        c_methods_p.add (b_mecrdeviceresult);  


        JButton b_meenddeviceresult = new JButton("meenddeviceresult");
        b_meenddeviceresult.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                execute_meenddeviceresult();
            }
        });
        e_methods_p.add (b_meenddeviceresult);  


        c_methods_p.add (Box.createVerticalGlue());
        m_methods_p.add (Box.createVerticalGlue());
        e_methods_p.add (Box.createVerticalGlue());
    }

    private void execute_mecrdeviceresult() {
        setVisible(false);

        DeviceresultmecrdeviceresultWindow w_mecrdeviceresult = new DeviceresultmecrdeviceresultWindow (main_frame, main_handler, this);
        w_mecrdeviceresult.pack();
        //w_mecrdeviceresult.setSize(300,w_mecrdeviceresult.getHeight());
        w_mecrdeviceresult.setVisible(true);
    }
    

    private void execute_meenddeviceresult() {
        try {
            if (table.getSelectedRow()>=0) {
            		            Object object = table.objects.get(table.getSelectedRow());
	            if (!object.getClass().equals(dao.DeviceresultImpl.class)){
                    	// FIXME means a subtype and for the moment we just warn and return
                        JOptionPane.showMessageDialog(null,"Subtypes can be handled in subtypes' tabs.","Warning",JOptionPane.WARNING_MESSAGE);
                } else {
		            if (JOptionPane.showConfirmDialog(null,"Are you sure you want to end this object?","Question",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		                main_handler.meenddeviceresult(((dao.Deviceresult) object).getId());
						main_frame.refreshTables();
						System.out.println("--------------------------");
		                try {
		                    System.out.println(main_handler.flushLog());
		                } catch (Exception e) {
		                    System.out.println(e);
		                }
					
		            }
	            }
            } else {
            	ErrorValidator.processErrors(messages.MessageProperties.NO_ROW_SELECTED);
            }
        } catch (MerodeException me) {
        	ErrorValidator.processExceptions(me);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

     
    protected void constructTable() {
        table = new Deviceresult_Table(main_handler);
        addTableListeners();
    }

    protected void constructTable(String id) {
        table = new Deviceresult_Table(main_handler, id);
        addTableListeners();
    }

	protected void constructTable(Collection<?> col) {
		table = new Deviceresult_Table(main_handler, col);
		addTableListeners();
		
	}
	
	private void addTableListeners() {
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				dao.Deviceresult object = (dao.Deviceresult)table.objects.get(row);
				Component[] m_event_buttons = m_methods_p.getComponents();
				Component[] e_event_buttons = e_methods_p.getComponents();
				if (!"".equals(object.getMandatoryInconsistency())) {
					for (int a = 0; a < m_event_buttons.length; a++) {
						m_event_buttons[a].setEnabled(false);
					}
					for (int a = 0; a < e_event_buttons.length; a++) {
						e_event_buttons[a].setEnabled(false);
					}

					if (col == ObjectTableRowRenderer.COLUMN_ID) {
						ImageIcon icon = new ImageIcon(this.getClass()
								.getResource("teacher.png"));

						JOptionPane
								.showOptionDialog(
										null,
										"<html><b>WARNING: Constraint violation</b><br><br>"
												+ "The object is locked due to missing mandatory dependent(s).<br>"
												+ "To unlock it you need to supply the mandatory dependent(s):<br><br>"
												+ "<font color='red'><b>"
												+ object.getMandatoryInconsistency()
												+ "</font><br><br><font color='red'><b></html>",
										"Mandatory Constraint Violation",
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.WARNING_MESSAGE, icon,
										null, null);
						MerodeLogger.logln("Constraint violation missing mandatory objects: " + object.getMandatoryInconsistency());
					}
				} else {
					for (int a = 0; a < m_event_buttons.length; a++) {
						m_event_buttons[a].setEnabled(true);
					}
					for (int a = 0; a < e_event_buttons.length; a++) {
						e_event_buttons[a].setEnabled(true);
					}
				}
			}
		});
	}
	

}
