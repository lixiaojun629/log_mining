package edu.njust.sem.log.gui;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;


public class StepEight extends JPanel {

	/**
	 * Create the panel.
	 */
	public StepEight() {
		setBackground(Color.WHITE);
		setBounds(new Rectangle(0, 0, 665, 582));
		setLayout(null);
		
		JLabel label = new JLabel("\u7ECF\u8FC7\u4EE5\u4E0A\u4E00\u7CFB\u5217\u7684\u5904\u7406\u8FC7\u7A0B\uFF0C\u6211\u4EEC\u4E3A\u60A8\u7684\u7F51\u7AD9\u5206\u7C7B\u76EE\u5F55\u4F53\u7CFB\u63D0\u51FA\u5982\u4E0B\u6539\u8FDB\u5EFA\u8BAE\uFF1A");
		label.setFont(new Font("楷体", Font.PLAIN, 18));
		label.setBounds(10, 26, 645, 33);
		add(label);
		
		JButton btnNewButton = new JButton("\u9000\u51FA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setFont(new Font("楷体", Font.PLAIN, 18));
		btnNewButton.setBounds(523, 505, 111, 33);
		add(btnNewButton);
		
		JTextArea txtrgtdcc = new JTextArea();
		txtrgtdcc.setFont(new Font("楷体", Font.PLAIN, 18));
		txtrgtdcc.setText("	全局相似度GTDCC = 0.6302 < 0.7,说明用户的心智模型和网\n站的分类目录结构差异较大，建议修改原有的目录结构。");
		txtrgtdcc.setBounds(10, 95, 624, 110);
		add(txtrgtdcc);

	}
}
