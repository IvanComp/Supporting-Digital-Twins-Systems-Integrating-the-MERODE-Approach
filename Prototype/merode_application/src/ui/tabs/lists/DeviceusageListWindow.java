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
import ui.tabs.operations.create.DeviceusagemecrdeviceusageWindow;
import ui.tabs.operations.modify.DeviceusagedevicedeploymentWindow;
import ui.tabs.operations.modify.DeviceusagemesetreadyWindow;
import ui.tabs.tables.Deviceusage_Table;
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
public class DeviceusageListWindow extends ObjectListWindow {
    public DeviceusageListWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler) {
        super (main_frame, main_handler, "Deviceusage");
    }

    public DeviceusageListWindow (MerodeMainEventHandlerGui main_frame, 
    		MerodeMainEventHandler main_handler, 
    		String id) {
        super (main_frame, main_handler, "Deviceusage", id);
    }
    
    public DeviceusageListWindow(MerodeMainEventHandlerGui main_frame,
			MerodeMainEventHandler main_handler, Collection<?> col) {
        super (main_frame, main_handler, "Deviceusage", col);
	}

	protected void completeMethodPane() {
        JButton b_mecrdeviceusage = new JButton("mecrdeviceusage");
        b_mecrdeviceusage.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                execute_mecrdeviceusage();
            }
        });
        c_methods_p.add (b_mecrdeviceusage);  

        JButton b_devicedeployment = new JButton("devicedeployment");
        b_devicedeployment.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                execute_devicedeployment();
            }
        });
        m_methods_p.add (b_devicedeployment);  
        JButton b_mesetready = new JButton("mesetready");
        b_mesetready.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                execute_mesetready();
            }
        });
        m_methods_p.add (b_mesetready);  

        JButton b_meenddeviceusage = new JButton("meenddeviceusage");
        b_meenddeviceusage.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                execute_meenddeviceusage();
            }
        });
        e_methods_p.add (b_meenddeviceusage);  
        JButton b_deviceundeployment = new JButton("deviceundeployment");
        b_deviceundeployment.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                execute_deviceundeployment();
            }
        });
        e_methods_p.add (b_deviceundeployment);  


        c_methods_p.add (Box.createVerticalGlue());
        m_methods_p.add (Box.createVerticalGlue());
        e_methods_p.add (Box.createVerticalGlue());
    }

    private void execute_mecrdeviceusage() {
        setVisible(false);

        DeviceusagemecrdeviceusageWindow w_mecrdeviceusage = new DeviceusagemecrdeviceusageWindow (main_frame, main_handler, this);
        w_mecrdeviceusage.pack();
        //w_mecrdeviceusage.setSize(300,w_mecrdeviceusage.getHeight());
        w_mecrdeviceusage.setVisible(true);
    }
    
    private void execute_devicedeployment() {
    	if (table.getSelectedRow()>=0) {
    	    Object object = table.objects.get(table.getSelectedRow());
    	    if (!object.getClass().equals(dao.DeviceusageImpl.class)){
                // FIXME means a subtype and for the moment we just warn and return
                JOptionPane.showMessageDialog(null,"Subtypes can be handled in subtypes' tabs.","Warning",JOptionPane.WARNING_MESSAGE);
            } else {
	            setVisible(false);
	        
	            object = (dao.Deviceusage)table.objects.get(table.getSelectedRow());
	
	            DeviceusagedevicedeploymentWindow w_devicedeployment = new DeviceusagedevicedeploymentWindow (main_frame, main_handler, this, (dao.Deviceusage)object);
	            w_devicedeployment.pack();
	            //w_devicedeployment.setSize(300,w_pay.getHeight());
	            //w_devicedeployment.create_b.requestFocusInWindow();
	            w_devicedeployment.setVisible(true);
         	}
        } else {
        	ErrorValidator.processErrors(messages.MessageProperties.NO_ROW_SELECTED);  
        }
     }

    private void execute_mesetready() {
    	if (table.getSelectedRow()>=0) {
    	    Object object = table.objects.get(table.getSelectedRow());
    	    if (!object.getClass().equals(dao.DeviceusageImpl.class)){
                // FIXME means a subtype and for the moment we just warn and return
                JOptionPane.showMessageDialog(null,"Subtypes can be handled in subtypes' tabs.","Warning",JOptionPane.WARNING_MESSAGE);
            } else {
	            setVisible(false);
	        
	            object = (dao.Deviceusage)table.objects.get(table.getSelectedRow());
	
	            DeviceusagemesetreadyWindow w_mesetready = new DeviceusagemesetreadyWindow (main_frame, main_handler, this, (dao.Deviceusage)object);
	            w_mesetready.pack();
	            //w_mesetready.setSize(300,w_pay.getHeight());
	            //w_mesetready.create_b.requestFocusInWindow();
	            w_mesetready.setVisible(true);
         	}
        } else {
        	ErrorValidator.processErrors(messages.MessageProperties.NO_ROW_SELECTED);  
        }
     }


    private void execute_meenddeviceusage() {
        try {
            if (table.getSelectedRow()>=0) {
            		            Object object = table.objects.get(table.getSelectedRow());
	            if (!object.getClass().equals(dao.DeviceusageImpl.class)){
                    	// FIXME means a subtype and for the moment we just warn and return
                        JOptionPane.showMessageDialog(null,"Subtypes can be handled in subtypes' tabs.","Warning",JOptionPane.WARNING_MESSAGE);
                } else {
		            if (JOptionPane.showConfirmDialog(null,"Are you sure you want to end this object?","Question",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		                main_handler.meenddeviceusage(((dao.Deviceusage) object).getId());
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

    private void execute_deviceundeployment() {
        try {
            if (table.getSelectedRow()>=0) {
            		            Object object = table.objects.get(table.getSelectedRow());
	            if (!object.getClass().equals(dao.DeviceusageImpl.class)){
                    	// FIXME means a subtype and for the moment we just warn and return
                        JOptionPane.showMessageDialog(null,"Subtypes can be handled in subtypes' tabs.","Warning",JOptionPane.WARNING_MESSAGE);
                } else {
		            if (JOptionPane.showConfirmDialog(null,"Are you sure you want to end this object?","Question",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		                main_handler.deviceundeployment(((dao.Deviceusage) object).getId());
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
        table = new Deviceusage_Table(main_handler);
        addTableListeners();
    }

    protected void constructTable(String id) {
        table = new Deviceusage_Table(main_handler, id);
        addTableListeners();
    }

	protected void constructTable(Collection<?> col) {
		table = new Deviceusage_Table(main_handler, col);
		addTableListeners();
		
	}
	
	private void addTableListeners() {
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				dao.Deviceusage object = (dao.Deviceusage)table.objects.get(row);
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
