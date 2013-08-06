package edu.njust.sem.log.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyTreeCellRender extends DefaultTreeCellRenderer {
	// 定义图标和要显示的字符串
	ImageIcon icon = new ImageIcon("img/tree_node.png");
	String str = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent
	 * (javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int,
	 * boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		// TODO Auto-generated method stub
		// System.out.println("value:" + value);
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		if (value.toString().equals("Lighting Decoration")
				|| value.toString().equals("Stage Lighting")) {
			// System.out.println("red");
			setForeground(Color.RED);
		}
		setIcon(icon);

		return this;

	}

}
