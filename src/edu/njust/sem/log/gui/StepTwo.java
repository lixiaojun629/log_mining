package edu.njust.sem.log.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import edu.njust.sem.log.util.DBUtil;
import javax.swing.JLabel;

public class StepTwo extends JPanel {

	// private CustomTableModel model = null;
	// private JTable table = null;
	final JButton button, button2, button3, button4, button5;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private JTable table;
	private JScrollPane scrollPane;
	private Object[][] data;
	private String[] columnNames;
	private JLabel jlabDesc1 = new JLabel("");
	private JLabel jlabDesc2;
	/**
	 * Create the panel.
	 * 
	 * @throws
	 */
	public StepTwo() {
		// super(new GridLayout(1,0));
		setBorder(new EmptyBorder(1, 1, 1, 1));
		setBackground(Color.WHITE);
		setBounds(new Rectangle(398, 22, 665, 582));
		setLayout(null);

		button = new JButton("\u4E0B\u4E00\u4E2A");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("楷体", Font.PLAIN, 18));
		button.setBackground(new Color(0, 153, 255));
		button.setBounds(529, 530, 126, 42);
		add(button);

		button2 = new JButton("下一步");
		button2.setForeground(Color.WHITE);
		button2.setFont(new Font("楷体", Font.PLAIN, 16));
		button2.setBackground(new Color(0, 153, 255));
		button2.setBounds(529, 530, 126, 42);
		add(button2);
		button2.setVisible(false);

		button3 = new JButton("下一步");
		button3.setForeground(Color.WHITE);
		button3.setFont(new Font("楷体", Font.PLAIN, 16));
		button3.setBackground(new Color(0, 153, 255));
		button3.setBounds(529, 530, 126, 42);
		add(button3);
		button3.setVisible(false);

		button4 = new JButton("下一步");
		button4.setForeground(Color.WHITE);
		button4.setFont(new Font("楷体", Font.PLAIN, 16));
		button4.setBackground(new Color(0, 153, 255));
		button4.setBounds(529, 530, 126, 42);
		add(button4);
		button4.setVisible(false);

		button5 = new JButton("下一步");
		button5.setForeground(Color.WHITE);
		button5.setFont(new Font("楷体", Font.PLAIN, 16));
		button5.setBackground(new Color(0, 153, 255));
		button5.setBounds(529, 530, 126, 42);
		add(button5);
		button5.setVisible(false);

		final JPanel panel = new JPanel();
		panel.setBounds(10, 98, 624, 430);
		add(panel);
		panel.setLayout(new GridLayout(0, 1));

		
		conn = DBUtil.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from tab_first");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final int n = 100;
		data = new Object[n][6];
		try {
			for (int i = 0; i < n; i++) {
				if (!rs.next()) {
					break;
				}
				for (int j = 0; j < 6; j++) {
					data[i][j] = rs.getObject(j + 1);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		columnNames = new String[] { "用户的IP地址", "访问时间", "请求方式", "请求的URL",
				"服务器回应状", "来源URL" };

		table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
		jlabDesc1.setFont(new Font("楷体", Font.PLAIN, 17));
		String lab1 = "网络日志是服务器上记录的网页访问数据集合,可用于挖掘隐含其中的用户行为";
		jlabDesc1.setText(lab1);
		jlabDesc1.setBounds(41, 47, 593, 28);
		add(jlabDesc1);
		String lab2 = "规律";
		jlabDesc2 = new JLabel(lab2);
		jlabDesc2.setFont(new Font("楷体", Font.PLAIN, 17));
		jlabDesc2.setBounds(10, 71, 624, 30);
		add(jlabDesc2);

		/*
		 * 按钮响应部分
		 */
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// label_step2.setVisible(false);
				// label_step3.setVisible(true);
				button.setVisible(false);
				button2.setVisible(true);
				panel.remove(scrollPane);
				jlabDesc1.setText("会话路径是指用户对服务器的一次有效访问过程，通过分析用户连续请求的页");
				jlabDesc2.setText("面，可以获得用户在网站中的访问行为和浏览兴趣");
				data = new Object[n][3];
				String querySession = "select url,user from tab_session where session_id = ?";
				PreparedStatement ps = null;
				try {
					ps = conn.prepareStatement(querySession);

					for (int i = 0; i < n; i++) {
						ps.setInt(1, i + 1);
						rs = ps.executeQuery();
						if (!rs.next()) {
							break;
						}
						String strUrls = "";
						while (rs.next()) {
							strUrls += "p" + rs.getInt("url") ;
							if (!rs.isLast()) {
								strUrls += "→";
							}
						}
						rs.last();
						data[i][0] = i + 1;
						data[i][1] = strUrls;
						data[i][2] = rs.getInt("user");

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				columnNames = new String[] { "会话路径编号", "路径中url的编号（以箭头分隔）",
						"用户编号" };
				table = new JTable(data, columnNames);
				scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
			}
		});

		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// label_step3.setVisible(false);
				// label_step4.setVisible(true);
				jlabDesc1.setText("事务路径是指考虑用户前进后�?行为的更细微的会话路径�?通过其连续请求的");
				jlabDesc2.setText("页面，可以获得用户在网站中的访问行为和浏览兴趣�?");
				button2.setVisible(false);
				button3.setVisible(true);
				panel.remove(scrollPane);

				data = new Object[n][3];
				String queryTrans = "select url,user from tab_trans where session_id = ?";
				PreparedStatement ps = null;
				try {
					ps = conn.prepareStatement(queryTrans);
					int j = 0;
					for (int i = 0; i < n; i++) {
						ps.setInt(1, i + 1);
						rs = ps.executeQuery();
						if (!rs.next()) {

							continue;
						}
						String strUrls = "";
						rs.previous();
						while (rs.next()) {
							strUrls += "p" + rs.getInt("url");
							if (!rs.isLast()) {
								strUrls += "→";
							}
						}
						rs.last();
						data[j][0] = i + 1;
						data[j][1] = strUrls;
						data[j][2] = rs.getInt("user");
						j++;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				columnNames = new String[] { "事务路径编号", "路径中url的编号（以箭头分隔）",
						"用户编号" };
				table = new JTable(data, columnNames);
				scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
			}
		});

		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// label_step4.setVisible(false);
				// label_step5.setVisible(true);
				button3.setVisible(false);
				button4.setVisible(true);
				panel.remove(scrollPane);
				jlabDesc1.setText("目录路径是把事务路径中涉及的网页替换为对应的�?��目录说形成的目录");
				jlabDesc2.setText("列，通过大量目录路径，可以获得符合用户内心期望的目录体系");
				data = new Object[n][2];
				String queryDirPath = "select dir_path_id,dir_id from tab_dir_path  where dir_path_id = ?";
				String queryDir = "SELECT dir FROM tab_dir where id = ?";
				PreparedStatement psDirPath = null;
				PreparedStatement psDir = null;
				ResultSet rsDir = null;
				try {
					psDirPath = conn.prepareStatement(queryDirPath);
					psDir = conn.prepareStatement(queryDir);
					for (int i = 0; i < n; i++) {
						psDirPath.setInt(1, i + 1);
						rs = psDirPath.executeQuery();
						if (!rs.next()) {
							break;
						}
						rs.beforeFirst();

						int dirID = 0;
						String dirs = "";
						while (rs.next()) {
							dirID = rs.getInt("dir_id");
							psDir.setInt(1, dirID);
							// System.out.println(dirID);
							rsDir = psDir.executeQuery();
							rsDir.next();
							dirs += rsDir.getString("dir");
							if (!rs.isLast()) {
								dirs += "→";
							}
						}
						data[i][0] = i;
						data[i][1] = dirs;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				columnNames = new String[] { "目录路径编号", "目录路径" };
				table = new JTable(data, columnNames);
				scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
			}
		});

		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				button4.setVisible(false);
				button5.setVisible(true);
			}
		});
	}
}
