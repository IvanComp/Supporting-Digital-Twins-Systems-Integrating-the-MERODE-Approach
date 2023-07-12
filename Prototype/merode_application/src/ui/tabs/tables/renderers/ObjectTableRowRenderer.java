package ui.tabs.tables.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ObjectTableRowRenderer implements TableCellRenderer {

	public static final DefaultTableCellRenderer DEFAULT_RENDERER 
					= new DefaultTableCellRenderer();
	
	public static final int COLUMN_ID = 0;
	public static final int COLUMN_FINAL_STATE = 3;
	public static final int COLUMN_CONSISTENCY = 4;

	
	public Component getTableCellRendererComponent(JTable table, final Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
        
//		Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(
//				table, value, isSelected, hasFocus, row, column);
		
		JLabel label = (JLabel)DEFAULT_RENDERER.getTableCellRendererComponent(  
                table, value, isSelected, hasFocus, row, column  
            );
		label.setIcon(null);
		
		Color foreground, background;
		if (isSelected) {
			foreground = Color.white;
			background = new Color(60, 175, 242);
		} else {
			if (row % 2 == 0) {
				foreground = Color.black;
				background = Color.white;
			} else {
				foreground = Color.black;
				background = new Color(220,220,224);//new Color(216,191,216);
			}
		}
		
		if (column == COLUMN_ID ) { 
			String[] hiddenValues = value.toString().split(":");
			String id = hiddenValues[0];
			String mandatoryConsistency = hiddenValues.length > 1 
				?  hiddenValues[1] : "";
			label.setText(id);
			label.setHorizontalTextPosition(JLabel.LEFT);
			if(!"".equals(mandatoryConsistency)){
				//label.setText((String) value);
				ImageIcon ic = new ImageIcon(this.getClass().getResource("lock.png"));
				label.setIcon(ic);
				//table.getModel().setValueAt( ic, row, column );

			} else {
				// clear values to have empty cells
				label.setIcon(null);
				//table.getModel().setValueAt(ic1, row, column);
			} 
		}
		
		if (column ==  /*COLUMN_FINAL_STATE*/ table.getColumnCount() - 2) {
			if (value.toString().equals("true")) {
				// table.getModel().setValueAt("<html><font color = 'red'>yes</font></html>",
				// row, column);
				// renderer.setForeground(Color.RED);
				label.setText("<html><font color = 'red'>&nbsp;&nbsp;&nbsp;&#10003;</font></html>");
				label.setIcon(null);
//				table.getModel()
//						.setValueAt(
//								"<html><font color = 'red'>&nbsp;&nbsp;&nbsp;&#10003;</font></html>",
//								row, column);
			} else if (value.toString().equals("false")) {
				// table.getModel().setValueAt("<html><font color = 'blue'>no</font></html>",
				// row, column);
				// renderer.setForeground(Color.BLUE);
				label.setText("");
				label.setIcon(null);
				//table.getModel().setValueAt("", row, column);
			} 
		}
//		if (column == COLUMN_CONSISTENCY ) {   // COLUMN_ID
//			//if (value.toString().equals("false")) {
//			if(!"".equals(value.toString())){
//				//label.setText((String) value);
//				label.setText("");
//				ImageIcon ic = new ImageIcon(this.getClass().getResource("lock.png"));
//				label.setIcon(ic);
//				//table.getModel().setValueAt( ic, row, column );
//			//} else if (value.toString().equals("true")) {
//			} else {
//				// clear values to have empty cells
//				label.setText("");
//				label.setIcon(null);
//				//table.getModel().setValueAt(ic1, row, column);
//			} 
//		}
		
		label.setForeground(foreground);
		label.setBackground(background);
		return label;
	}
	
}

