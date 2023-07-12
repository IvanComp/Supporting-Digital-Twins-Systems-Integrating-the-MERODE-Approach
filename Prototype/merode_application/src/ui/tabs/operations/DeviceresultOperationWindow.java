/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */
 
package ui.tabs.operations;


import handlers.MerodeMainEventHandler;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.*;
import javax.swing.border.*;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import ui.utils.SpringUtilities;
import ui.tabs.lists.ObjectListWindow;
import ui.tabs.tables.ObjectTable;


import com.toedter.calendar.JDateChooser;

import driver.MerodeMainEventHandlerGui;



/**
 * Abstract superclass for windows to create or modify objects. It has:
 * - One textfield for each attribute
 * - One object table per master object
 * - One button with the name of the operation
 */
public abstract class DeviceresultOperationWindow extends JFrame {
    protected MerodeMainEventHandlerGui main_frame;
    protected MerodeMainEventHandler main_handler;
    protected ObjectListWindow list_frame;
    protected String button_name;
    
    protected ObjectTable table;
    
    public DeviceresultOperationWindow (MerodeMainEventHandlerGui main_f, 
    				MerodeMainEventHandler main_h, 
    					ObjectListWindow list_f, 
    						String b_name) {
        super("Deviceresult: " + b_name);
        main_frame = main_f;
        main_handler = main_h;
        list_frame = list_f;
        button_name = b_name;
    
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim.width / 2, (dim.height / 3) * 2);
		
		// this centers the frame on the screen
		setLocationRelativeTo(null);
		
        //getContentPane().setLayout (new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    	getContentPane().setLayout (new MigLayout());
    }
    
    protected void buildFrame(Container p) {
        constructAttributePane(p);
        constructdeviceusageTable(p);
		constructButtonPane(p);
    }

    protected abstract void constructdeviceusageTable(Container p);

    private void constructButtonPane(Container p) {
        JPanel button_p = new JPanel();
        button_p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //button_p.setLayout (new BoxLayout(button_p, BoxLayout.X_AXIS));
    
        //button_p.add (Box.createHorizontalGlue());
    
        JButton create_b = new JButton(button_name);

        create_b.addActionListener ( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                System.out.println("--------------------------");
                executeMethod();
                try {
                    System.out.println(main_handler.flushLog());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        button_p.add (create_b);
    
        //button_p.add (Box.createHorizontalGlue());
        
        getRootPane().setDefaultButton(create_b);
        
        JButton cancel_b = new JButton("Cancel");
        cancel_b.addActionListener ( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                list_frame.setVisible(true);
                dispose();
            }
        });
        button_p.add (cancel_b);
    
        //button_p.add (Box.createHorizontalGlue());
    
        p.add (button_p, "align center");
    }

    protected abstract void executeMethod();

	protected JFormattedTextField t_Time;
    protected JTextField t_Value;

    private void constructAttributePane(Container p) {
        JPanel att_p = new JPanel (new SpringLayout());
        

        JLabel l_Time = new JLabel("Time [Date/Time]: ", JLabel.TRAILING);
        att_p.add(l_Time);

        l_Time.setLabelFor(t_Time);
        
//        final JDateChooser dc_1 = new JDateChooser();
//        ((JTextField) dc_1.getDateEditor()).setVisible(false);
//        final DateFormat df_1 = new SimpleDateFormat("yyyy-MM-dd");
//        
//		dc_1.addPropertyChangeListener(new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent evt) {
//				try {
//					if (evt.getPropertyName().equals("date")) {
//				        t_Time.setText(df_1.format(dc_1.getDate()));
//					}
//				} catch (Exception e) {
//				}
//			}
//
//		});
		//JPanel date_attr_and_calendar_picker_Panel = new JPanel();
		//date_attr_and_calendar_picker_Panel.add(t_Time);
		//date_attr_and_calendar_picker_Panel.add(dc_1);
		//att_p.add(date_attr_and_calendar_picker_Panel);

        UIManager.put("JXDatePicker.arrowIcon", new ImageIcon(DeviceresultOperationWindow.class.getResource("calendar.png")));
        final JXDatePicker jxdc_1 = new JXDatePicker();
        //jxdc_1.getEditor().setVisible(false);
        jxdc_1.setPreferredSize(new Dimension(260, 22));
        jxdc_1.getMonthView().setBoxPaddingX(this.getWidth() > 500 ? 12 : 2);
       
        final DateFormat df_1 = new SimpleDateFormat("yyyy-MM-dd");
        t_Time = new JFormattedTextField(df_1);
        jxdc_1.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					if (evt.getPropertyName().equals("date")) {
				        t_Time.setText(df_1.format(jxdc_1.getDate()));
					}
				} catch (Exception e) {
				}
			}

		});
		jxdc_1.setEditor(t_Time);
        att_p.add(jxdc_1);

        JLabel l_Value = new JLabel("Value [String]: ", JLabel.TRAILING);
        att_p.add(l_Value);

        t_Value = new JTextField(15);
        t_Value.setMaximumSize(new Dimension(400, 40));
        l_Value.setLabelFor(t_Value);
        
        att_p.add(t_Value);
        
        SpringUtilities.makeCompactGrid(att_p,
                                        2, 2,  //rows, cols
                                        6, 6,  //initX, initY
                                        6, 6); //xPad, yPad
        p.add (att_p, "align center, wrap");
    }
}
