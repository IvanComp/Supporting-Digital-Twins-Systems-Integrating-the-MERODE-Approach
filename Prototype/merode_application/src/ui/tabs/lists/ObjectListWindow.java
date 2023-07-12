/**
 * Attention: Generated source! Do not modify by hand!
 */
package ui.tabs.lists;



import handlers.MerodeMainEventHandler;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import ui.tabs.tables.ObjectTable;

import driver.MerodeMainEventHandlerGui;

import java.awt.event.*;
import java.awt.*;
import java.util.Collection;




/**
 * Abstract superclass for a frame that has
 * - buttons to create, modify and destroy an object
 * - an object table
 * - an Ok button
 */
@SuppressWarnings("serial")
public abstract class ObjectListWindow extends JPanel {
    protected MerodeMainEventHandlerGui main_frame;
    protected MerodeMainEventHandler main_handler;
    @SuppressWarnings("rawtypes")
	protected java.util.Collection obj_collection;
    
    protected JPanel top_p;
    protected JPanel c_methods_p;
    protected JPanel m_methods_p;
    protected JPanel e_methods_p;
    
	public ObjectTable table;
	
	MigLayout migLayout = new MigLayout(
			"", // Layout Constraints
			"10[fill,grow]10", // Column constraints
			"5[shrink]5[shrink]"); // Row constraints
	
	int prefferedHight = 
		main_frame.BUTTONS_NUMBER * main_frame.BUTTON_HEIGHT < 140 
			? 140 : main_frame.BUTTONS_NUMBER * main_frame.BUTTON_HEIGHT;
    
    public ObjectListWindow(
    		MerodeMainEventHandlerGui main_f, 
    		MerodeMainEventHandler main_h, 
    		String title, String id) {
       // super(title);
        main_frame = main_f;
        main_handler = main_h;
    
        //setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));

		setLayout(migLayout);
        constructTopPane(this);
        
        constructMethodPane (this);
        completeMethodPane ();
        
        constructTable (id);
        completeTable (this);
    
        constructOkButton(this);
    }

    public ObjectListWindow(
    		MerodeMainEventHandlerGui main_f, 
    		MerodeMainEventHandler main_h, String title) {
        //super(title + " list");
        main_frame = main_f;
        main_handler = main_h;
    
        //setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(migLayout);
    
        constructTopPane(this);
        
        constructMethodPane (this);
        completeMethodPane ();
        
        constructTable ();
        completeTable (this);
    
        //constructOkButton(this);
    }
    
	public ObjectListWindow(MerodeMainEventHandlerGui main_f,
			MerodeMainEventHandler main_h, 
			String title, 
			Collection<?> col ) {
       // super(title + " list");
        main_frame = main_f;
        main_handler = main_h;
        obj_collection = col;
   
        //setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(migLayout);
    
        constructTopPane(this);
        
        constructMethodPane (this);
        completeMethodPane ();
        
        constructTable (col);
        completeTable (this);
    
        constructOkButton(this);
	}

	protected abstract void completeMethodPane ();
    
    protected abstract void constructTable ();
    protected abstract void constructTable (String id);
    protected abstract void constructTable (java.util.Collection<?> col);
    
    private void constructOkButton(Container p) {
        JPanel button_p = new JPanel();
        button_p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        button_p.setLayout (new BoxLayout(button_p, BoxLayout.X_AXIS));
    
        button_p.add (Box.createHorizontalGlue());
        final JButton ok_b = new JButton("Ok");
        ok_b.addActionListener ( new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                //dispose();
            	ok_b.getRootPane().getParent().setVisible(false);
            }
        });
        button_p.add (ok_b);
        button_p.add (Box.createHorizontalGlue());
    
        p.add (button_p);
    }

    private void constructTopPane(Container p) {
        top_p = new JPanel();
       /* top_p.setBorder(BorderFactory.createCompoundBorder(BorderFactory
            .createTitledBorder("Manage"), BorderFactory
            .createEmptyBorder(5, 5, 5, 5)));*/

        top_p.setLayout (new BoxLayout(top_p, BoxLayout.X_AXIS));
        p.add (top_p, BorderLayout.NORTH);
    }
    
    private void constructMethodPane(Container p) {
    
        c_methods_p = new JPanel();
        c_methods_p.setLayout (new BoxLayout(c_methods_p, BoxLayout.Y_AXIS));
        //c_methods_p.setBorder (BorderFactory.createEmptyBorder(5,5,5,5));
        //JLabel createMethodLabel = new JLabel("Creating methods"); 
        c_methods_p.add (new JLabel(messages.MessageProperties.EMPTY_STRING));
        c_methods_p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Creating methods"), BorderFactory
                    .createEmptyBorder(5, 5, 5, 5)));

        m_methods_p = new JPanel();
        m_methods_p.setLayout (new BoxLayout(m_methods_p, BoxLayout.Y_AXIS));
        //m_methods_p.setBorder (BorderFactory.createEmptyBorder(5,5,5,5));
        //m_methods_p.add (new JLabel("Modifying methods"));
        m_methods_p.add (new JLabel(messages.MessageProperties.EMPTY_STRING));
        m_methods_p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Modifying methods"), BorderFactory
                    .createEmptyBorder(5, 5, 5, 5)));
    
        e_methods_p = new JPanel();
        e_methods_p.setLayout (new BoxLayout(e_methods_p, BoxLayout.Y_AXIS));
        //e_methods_p.setBorder (BorderFactory.createEmptyBorder(5,5,5,5));
        //e_methods_p.add (new JLabel("Ending methods"));
        e_methods_p.add (new JLabel(messages.MessageProperties.EMPTY_STRING));
        e_methods_p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Ending methods"), BorderFactory
                    .createEmptyBorder(5, 5, 5, 5)));
    
        top_p.add (c_methods_p);
        top_p.add (Box.createHorizontalGlue());
        top_p.add (m_methods_p);
        top_p.add (Box.createHorizontalGlue());
        top_p.add (e_methods_p);

    }
    
    private void completeTable (Container p) {
        /// Table panel
        table.setPreferredScrollableViewportSize(
        		new Dimension(800, prefferedHight));
        JScrollPane scrollPane = new JScrollPane(table);
        p.add (scrollPane);
    }
}
