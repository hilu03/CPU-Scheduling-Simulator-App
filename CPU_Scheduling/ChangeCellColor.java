package CPU_Scheduling;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class ChangeCellColor implements TableCellRenderer {
	
	private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		Component c = RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (column == 4) { //Color column
			Object o = table.getModel().getValueAt(row, column);
			Color color = new Color(Integer.parseInt(o.toString()));
			c.setBackground(color);
			c.setForeground(color);
		}
		else {
			c.setBackground(Color.white);
			c.setForeground(Color.black);
		}
		return c;
	}

}
