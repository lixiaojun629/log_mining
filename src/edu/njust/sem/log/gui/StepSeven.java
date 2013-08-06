package edu.njust.sem.log.gui;

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StepSeven extends JPanel {
	JButton button7;
	/**
	 * Create the panel.
	 */
	private JTextArea textArea;
	private String str;

	public StepSeven() {
		setBackground(Color.WHITE);
		setBounds(new Rectangle(0, 0, 665, 582));
		setLayout(null);

		button7 = new JButton("\u4E0B\u4E00\u6B65");
		button7.setForeground(Color.WHITE);
		button7.setFont(new Font("楷体", Font.PLAIN, 16));
		button7.setBackground(new Color(0, 153, 255));
		button7.setBounds(529, 530, 126, 42);
		add(button7);

		JLabel label = new JLabel(
				"\u7F51\u7AD9\u539F\u6709\u7684\u4EA7\u54C1\u5206\u7C7B\u76EE\u5F55");
		label.setFont(new Font("楷体", Font.PLAIN, 18));
		label.setBounds(39, 123, 216, 19);
		add(label);

		JLabel label_1 = new JLabel(
				"\u7528\u6237\u671F\u671B\u7684\u4EA7\u54C1\u5206\u7C7B\u76EE\u5F55");
		label_1.setFont(new Font("楷体", Font.PLAIN, 18));
		label_1.setBounds(317, 123, 306, 19);
		add(label_1);

		JButton btnpathfinder = new JButton(
				"\u8FDB\u884CPathFinder\u8BA1\u7B97\u76F8\u4F3C\u5EA6");
		btnpathfinder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				str = "GTDCC = 0.6302";
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textArea.append(str);
			}
		});
		btnpathfinder.setForeground(Color.WHITE);
		btnpathfinder.setFont(new Font("楷体", Font.PLAIN, 16));
		btnpathfinder.setBackground(new Color(0, 153, 255));
		btnpathfinder.setBounds(203, 538, 260, 27);
		add(btnpathfinder);

		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode(
				"Lights & Lighting") {
			{
				DefaultMutableTreeNode node_1;
				node_1 = new DefaultMutableTreeNode(
						"Indoor & Outdoor Lighting ");
				node_1.add(new DefaultMutableTreeNode(" Interior Lighting"));
				node_1.add(new DefaultMutableTreeNode("Outdoor Lighting"));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("Professio-nal Lighting ");
				node_1.add(new DefaultMutableTreeNode("Professional Lighting "));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("LED Lighting ");
				node_1.add(new DefaultMutableTreeNode("LED Lighting"));
				node_1.add(new DefaultMutableTreeNode("LED Display "));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("Lighting Accessories ");
				node_1.add(new DefaultMutableTreeNode("Stage Lighting"));
				add(node_1);
			}
		}));
		tree.setBounds(39, 152, 216, 279);
		add(tree);

		JTree tree_1 = new JTree();
		tree_1.setModel(new DefaultTreeModel(new DefaultMutableTreeNode(
				"Lights & Lighting") {
			{
				DefaultMutableTreeNode node_1;
				node_1 = new DefaultMutableTreeNode(
						" Indoor & Outdoor Lighting");
				node_1.add(new DefaultMutableTreeNode(" Interior Lighting"));
				node_1.add(new DefaultMutableTreeNode("Outdoor Lighting"));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("Professio-nal Lighting");
				node_1.add(new DefaultMutableTreeNode("Professional Lighting "));
				node_1.add(new DefaultMutableTreeNode("Stage Lighting"));
				add(node_1);
				node_1 = new DefaultMutableTreeNode("LED Lighting");
				node_1.add(new DefaultMutableTreeNode("LED Lighting"));
				node_1.add(new DefaultMutableTreeNode("LED Display"));
				node_1.add(new DefaultMutableTreeNode("Lighting Accessories"));
				add(node_1);
			}
		}));
		tree_1.setBounds(317, 152, 307, 279);
		add(tree_1);

		JLabel label_2 = new JLabel(
				"----------------------------------------------------------------------------------------------------------------------------------------------------");
		label_2.setBounds(46, 441, 593, 19);
		add(label_2);

		textArea = new JTextArea();
		textArea.setFont(new Font("楷体", Font.PLAIN, 18));
		textArea.setBounds(387, 470, 211, 27);
		add(textArea);

		JLabel lblpathfinder = new JLabel(
				"\u57FA\u4E8EPathFinder\u7B97\u6CD5\u5F97\u51FA\u7684\u5168\u5C40\u76F8\u4F3C\u6027\uFF1A");
		lblpathfinder.setFont(new Font("楷体", Font.PLAIN, 18));
		lblpathfinder.setBounds(38, 466, 335, 33);
		add(lblpathfinder);
		
		JLabel jlabDesc1 = new JLabel("通过对比分析用户期望目录与网站自身目录的差异系数,直观地给网站管");
		jlabDesc1.setFont(new Font("楷体", Font.PLAIN, 17));
		jlabDesc1.setBounds(73, 37, 550, 27);
		add(jlabDesc1);
		
		JLabel jlabDesc2 = new JLabel("理者显示需优化程度。");
		jlabDesc2.setFont(new Font("楷体", Font.PLAIN, 17));
		jlabDesc2.setBounds(39, 56, 584, 27);
		add(jlabDesc2);
		
		JLabel lblNewLabel = new JLabel("----------------------------------------------------------------------------------------------------------------------------------------------------");
		lblNewLabel.setBounds(39, 93, 584, 15);
		add(lblNewLabel);

	}
}
