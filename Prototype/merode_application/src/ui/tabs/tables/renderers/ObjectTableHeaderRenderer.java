package ui.tabs.tables.renderers;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;


public class ObjectTableHeaderRenderer extends DefaultTableCellRenderer {


  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus, 
                                                 int row, int column) {
    
    Icon icon = new ImageIcon(this.getClass().getResource("images/help_small.png"));
    
    addMouseListener(new MouseListener() {


		@Override
		public void mouseClicked(MouseEvent arg0) {
			JOptionPane.showMessageDialog(null,
					messages.MessageProperties.NO_ROW_SELECTED, "Error",
					JOptionPane.ERROR_MESSAGE);
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
      });

    Border border = LineBorder.createGrayLineBorder();
    setText((String)value);
    setHorizontalTextPosition(JLabel.LEFT);
    setVerticalTextPosition(JLabel.CENTER);
    //setBorder(border);
    setIcon(icon);
    return this;
  }
  
}
