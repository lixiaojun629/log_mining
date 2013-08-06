package edu.njust.sem.log.gui;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JProgressBar;

import edu.njust.sem.log.Task;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class StepOne extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static File file;
	private JFileChooser jfc = new JFileChooser();// 文件选择�?
	private Task task = null;
	private JProgressBar jpb;
	private JLabel jlabStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StepOne frame = new StepOne();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.Title(
	 * "\u7F51\u7AD9\u5206\u7C7B\u76EE\u5F55\u4F18\u5316\u7CFB\u7EDF"); set
	 */
	public static File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	class Listener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName()) {
				int progress = (Integer) evt.getNewValue();
				System.out.println("now is processing" + progress);
				jpb.setValue(progress);
				if (progress == 1) {
					jlabStatus.setText("正在初始化数据库...");
				} else if (progress == 2) {
					jlabStatus.setText("正在导入日志到数据库...");
				} else if (progress == 3) {
					jlabStatus.setText("正在预处理数据库中的日志...");
				} else if (progress == 4) {
					jlabStatus.setText("正在对日志数据编�?..");
				} else if (progress == 5) {
					jlabStatus.setText("正在识别用户...");
				} else if (progress == 6) {
					jlabStatus.setText("正在生成会话路径...");
				} else if (progress == 7) {
					jlabStatus.setText("正在生成事务路径...");
				} else if (progress == 8) {
					jlabStatus.setText("正在删除不符合要求的事务路径...");
				} else if (progress == 9) {
					jlabStatus.setText("正在生成目录路径...");
				} else if (progress == 10) {
					jlabStatus.setText("正在聚类分析...");
				} else {
					jlabStatus.setText("日志分析已完成�?");
				}
			}

		}

	}

	public StepOne() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 20, 1100, 685);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JLabel label_step1 = new JLabel("");
		label_step1.setIcon(new ImageIcon("img\\step1.png"));
		label_step1.setBounds(128, 26, 343, 103);
		contentPane.add(label_step1);

		final JLabel label_step2 = new JLabel("");
		label_step2.setIcon(new ImageIcon("img\\step2.png"));
		label_step2.setBounds(128, 110, 343, 103);
		contentPane.add(label_step2);
		label_step2.setVisible(false);

		final JLabel label_step3 = new JLabel("");
		label_step3.setIcon(new ImageIcon("img\\step3.png"));
		label_step3.setBounds(128, 189, 343, 103);
		contentPane.add(label_step3);
		label_step3.setVisible(false);

		final JLabel label_step4 = new JLabel("");
		label_step4.setIcon(new ImageIcon("img\\step4.png"));
		label_step4.setBounds(128, 269, 343, 103);
		contentPane.add(label_step4);
		label_step4.setVisible(false);

		final JLabel label_step5 = new JLabel("");
		label_step5.setIcon(new ImageIcon("img\\step5.png"));
		label_step5.setBounds(128, 345, 343, 103);
		contentPane.add(label_step5);
		label_step5.setVisible(false);

		final JLabel label_step6 = new JLabel("");
		label_step6.setIcon(new ImageIcon("img\\step6.png"));
		label_step6.setBounds(128, 430, 343, 103);
		contentPane.add(label_step6);
		label_step6.setVisible(false);

		final JLabel label_step7 = new JLabel("");
		label_step7.setIcon(new ImageIcon("img\\step7.png"));
		label_step7.setBounds(128, 510, 343, 103);
		contentPane.add(label_step7);
		label_step7.setVisible(false);

		final JLabel label_step8 = new JLabel("");
		label_step8.setIcon(new ImageIcon("img\\step8.png"));
		label_step8.setBounds(128, 590, 343, 103);
		contentPane.add(label_step8);
		label_step8.setVisible(false);

		final JPanel panel_step1 = new JPanel();
		panel_step1.setBackground(Color.WHITE);
		panel_step1.setBounds(398, 22, 665, 582);
		contentPane.add(panel_step1);
		panel_step1.setLayout(null);

		/*
		 * ´´½¨ºóÐø²½ÖèËùÔÚµÄÈÝÆ÷£¬Ìí¼ÓÖÁµ±Ç°frame£¬²¢Ê¹Ö®²»¿É¼û¡£ÓÉÓÚÌáÇ°´´½¨£¬¶
		 * ÔÓ¦²½ÖèÖÐµÄËã·¨±ãÐèÒªÁíÍâÐ´·½·¨£¬²¢ÔÚ±¾ÎÄ¼þÖÐµ÷ÓÃ¡£
		 */
		final StepTwo st2 = new StepTwo();
		st2.setBounds(398, 22, 665, 582);
		contentPane.add(st2);
		st2.setVisible(false);

		// final StepSix st6 = new StepSix();
		// st6.setBounds(398, 22, 665, 582);
		// contentPane.add(st6);
		// st6.setVisible(false);

		final StepSeven st7 = new StepSeven();
		st7.setBounds(398, 22, 665, 582);
		contentPane.add(st7);
		st7.setVisible(false);

		final StepEight st8 = new StepEight();
		st8.setBounds(398, 22, 665, 582);
		contentPane.add(st8);
		st8.setVisible(false);

		JButton btnNewButton = new JButton("\u6D4F\u89C8");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc.setFileSelectionMode(0);// 设定只能选择到文�?
				int state = jfc.showOpenDialog(StepOne.this);// 此句是打�?��件�?择器界面的触发语�?
				if (state == 1) {
					return;// 撤销则返�?
				} else {
					setFile(jfc.getSelectedFile());// f为�?择到的文�?
					textField.setText(getFile().getAbsolutePath());
					// System.out.println(getFile());
				}
			}
		});
		btnNewButton.setFont(new Font("楷体", Font.PLAIN, 18));
		btnNewButton.setBackground(new Color(0, 153, 255));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(505, 162, 93, 23);
		panel_step1.add(btnNewButton);

		// String[] s = { ".xls", ".txt" };

		JLabel label = new JLabel(
				"\u8BF7\u9009\u62E9\u60A8\u7684\u65E5\u5FD7\u7C7B\u578B\uFF0C\u5E76\u5BFC\u5165\u60A8\u7684\u6570\u636E\u4EE5\u4FBF\u5F00\u59CB\u65E5\u5FD7\u5904\u7406\u3002");
		label.setFont(new Font("楷体", Font.PLAIN, 18));
		label.setBounds(102, 48, 496, 21);
		panel_step1.add(label);

		JLabel label_2 = new JLabel("\u65E5\u5FD7\u8DEF\u5F84\uFF1A");
		label_2.setFont(new Font("楷体", Font.PLAIN, 18));
		label_2.setBounds(102, 164, 99, 18);
		panel_step1.add(label_2);

		textField = new JTextField();
		textField.setBounds(199, 162, 296, 23);
		panel_step1.add(textField);
		textField.setColumns(10);

		JButton btnNewButton_1 = new JButton("�?��用户日志处理分析");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				task = Task.getTask();
				task.setFile(file);
				task.addPropertyChangeListener(new Listener());
				task.execute();

			}
		});
		btnNewButton_1.setFont(new Font("楷体", Font.PLAIN, 18));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 153, 255));
		btnNewButton_1.setBounds(102, 229, 285, 23);
		panel_step1.add(btnNewButton_1);

		jpb = new JProgressBar(1, 11);
		jpb.setBackground(Color.LIGHT_GRAY);
		jpb.setForeground(new Color(0, 153, 255));
		jpb.setBounds(102, 315, 496, 23);
		panel_step1.add(jpb);

		JButton btnNewButton_2 = new JButton("显示结果");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				st2.setVisible(true);
				panel_step1.setVisible(false);
				label_step1.setVisible(false);
				label_step2.setVisible(true);

			}
		});
		btnNewButton_2.setBackground(new Color(0, 153, 255));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("楷体", Font.PLAIN, 16));
		btnNewButton_2.setBounds(505, 454, 126, 42);
		panel_step1.add(btnNewButton_2);

		jlabStatus = new JLabel("");
		jlabStatus.setFont(new Font("楷体", Font.PLAIN, 18));
		jlabStatus.setBounds(102, 360, 496, 23);
		panel_step1.add(jlabStatus);

		JLabel label_bg = new JLabel("");
		label_bg.setIcon(new ImageIcon("img\\UIDesign3ed.png"));
		label_bg.setBounds(0, 0, 1084, 652);
		contentPane.add(label_bg);

		st2.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label_step2.setVisible(false);
				label_step3.setVisible(true);
			}
		});

		st2.button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label_step3.setVisible(false);
				label_step4.setVisible(true);
			}
		});
		st2.button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label_step4.setVisible(false);
				label_step5.setVisible(true);
			}
		});
		st2.button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label_step5.setVisible(false);
				label_step6.setVisible(true);
				st7.setVisible(true);
				st2.setVisible(false);
			}
		});
		// st6.button6.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		//
		// st7.setVisible(true);
		// st6.setVisible(false);
		// label_step6.setVisible(false);
		// label_step7.setVisible(true);
		// }
		// });

		st7.button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				st8.setVisible(true);
				st7.setVisible(false);
				label_step6.setVisible(false);
				label_step7.setVisible(true);
			}
		});

	}
}