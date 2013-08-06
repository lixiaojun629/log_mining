package edu.njust.sem.log.gui;



import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class TableUtil {
	


	public static void fixTableColumnWidth(JTable table) {
		int MaxWidth = 0;
		int width = 0;
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn col = table.getColumn(table.getColumnName(i));
			width = getPreferredWidthForColumn(table, col);
			while(width > 150){
				width-=20;
			}
			col.setPreferredWidth(width);
			System.out.println("preferered width:"+width);
			MaxWidth += width;
		}

	}
	public static void fixTableColumnWidth(JTable table,int row){
		for(int i = 0; i < table.getColumnCount(); i++){
			
			TableCellRenderer renderer = table.getCellRenderer(row, i);
			Component comp = renderer.getTableCellRendererComponent(table,
					table.getValueAt(row, i), false, false, row, i);
			int width = comp.getPreferredSize().width + 2;
			TableColumn col = table.getColumn(table.getColumnName(i));
			
			col.setPreferredWidth(width);
		}
	}
	public static int getPreferredWidthForColumn(JTable table, TableColumn col) {
		int hw = columnHeaderWidth(table, col), // hw = header width
		cw = widestCellInColumn(table, col); // cw = column width

		return hw > cw ? hw : cw;
	}

	private static int columnHeaderWidth(JTable table, TableColumn col) {
		TableCellRenderer renderer = col.getHeaderRenderer();
		if (renderer == null) {
			renderer = new DefaultTableCellRenderer();
			((DefaultTableCellRenderer) renderer).setText(col.getHeaderValue()
					.toString());
		}
		Component comp = renderer.getTableCellRendererComponent(table,
				col.getHeaderValue(), false, false, 0, 0);
		//System.out.println("preferered width:"+comp.getPreferredSize().width);
		return comp.getPreferredSize().width + 2;
	}

	private static int widestCellInColumn(JTable table, TableColumn col) {
		int c = col.getModelIndex(), width = 0, maxw = 0;
		for (int r = 0; r < table.getRowCount(); ++r) {
			TableCellRenderer renderer = table.getCellRenderer(r, c);
			Component comp = renderer.getTableCellRendererComponent(table,
					table.getValueAt(r, c), false, false, r, c);
			width = comp.getPreferredSize().width + 2;
			maxw = width > maxw ? width : maxw;
		}
		return maxw;
	}

}
