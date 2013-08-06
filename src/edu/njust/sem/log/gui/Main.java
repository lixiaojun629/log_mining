package edu.njust.sem.log.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;
import edu.njust.sem.log.Task;
import edu.njust.sem.log.util.DBUtil;
import edu.njust.sem.log.util.ScanTrans;

public class Main extends JFrame {

	private JPanel contentPane = new ImagePanel(
			new ImageIcon("img/1.jpg").getImage());
	private JPanel tablePane;
	private Listener listener = new Listener();

	private JProgressBar jpbar;
	private JLabel jlabStatus;
	private JPanel welcomePane = new JPanel();;

	private JButton jbtLog;
	private JButton jbtUser;
	private JButton jbtUrl;
	private JButton jbtCatalog;
	private JButton jbtSession;
	private JButton jbtTrans;
	private JButton jbtDirs;
	
	private static Font font;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					font = new Font("宋体", Font.PLAIN, 17);
					UIManager.put("Label.font", font);
					UIManager.put("Button.font", font);
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());

				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		// JFrame部分

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"img/logo.png"));
		setTitle("网站产品分类目录优化系统 1.2Beta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 856, 574);
		contentPane.setBackground(new Color(153, 204, 255));

		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		// topPane部分，永久显示在界面上。
		JPanel topPane = new JPanel();
		topPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		topPane.setOpaque(false);

		contentPane.add(topPane, BorderLayout.NORTH);
		topPane.setLayout(new MigLayout("",
				"[92.00px][92.00px][92.00px][92.00px]", "[23px]"));

		JButton jbtHome = new ImageButton("首页", new ImageIcon("img/home.png"));
		topPane.add(jbtHome, "cell 0 0,grow");
		jbtHome.addActionListener(listener);

		JButton jbtImport = new ImageButton(
				"\u65E5\u5FD7\u6587\u4EF6\u5BFC\u5165", new ImageIcon(
						"img/import.png"));
		jbtImport.setText("日志导入");

		topPane.add(jbtImport, "cell 1 0,grow");
		jbtImport.addActionListener(listener);

		JButton jbtAnalyse = new ImageButton("\u65E5\u5FD7\u5206\u6790",
				new ImageIcon("img/analyse.png"));

		topPane.add(jbtAnalyse, "cell 2 0,grow");

		jbtAnalyse.addActionListener(listener);

		JButton jbtExport = new ImageButton(
				"\u5BFC\u51FA\u5206\u6790\u7ED3\u679C", new ImageIcon(
						"img/export.png"));
		jbtExport.setText("分析结果");
		jbtExport.addActionListener(listener);
		topPane.add(jbtExport, "cell 3 0,grow");

		listener.showWelcomePane();

	}

	/**
	 * 展开JTree的所有节点
	 * 
	 * @param tree
	 * @param parent
	 */

	private void expandTree(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandTree(tree, path);
			}
		}
		tree.expandPath(parent);
	}

	class Listener implements ActionListener {
		private Connection conn;
		private JPanel importPane = new JPanel();;
		private JSplitPane splitPane = new JSplitPane();
		JPanel resultPane = new JPanel();
		private File file;
		private JFileChooser jfc = new JFileChooser();
		private PrintWriter pw;
		private boolean splitPaneInited = false;
		private JLabel jlabDescribe;
		DefaultTreeCellRenderer treeRenderer1 = new MyTreeCellRender();
		DefaultTreeCellRenderer treeRenderer2 = new MyTreeCellRender2();

		private Listener() {
			conn = DBUtil.getConn();
			// treeRenderer.setIcon(new ImageIcon("img/tree_node.png"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("首页")) {
				showWelcomePane();
			}
			if (e.getActionCommand().equals("日志导入")) {
				contentPane.remove(splitPane);
				contentPane.remove(welcomePane);
				contentPane.remove(resultPane);
				contentPane.add(importPane, BorderLayout.CENTER);

				importPane.setLayout(null);
				importPane.setOpaque(false);
				final JTextField jtxtImport = new JTextField();
				jtxtImport.setBounds(172, 168, 406, 21);
				importPane.add(jtxtImport);
				jtxtImport.setColumns(10);

				JButton jbtScan = new JButton("\u6D4F\u89C8");
				jbtScan.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						jfc.setFileSelectionMode(0);// 设定只能选择到文�?
						int state = jfc.showOpenDialog(Main.this);// 此句是打�?��件�?择器界面的触发语�?
						if (state == 1) {
							return;// 撤销则返�?
						} else {
							file = jfc.getSelectedFile();// f为�?择到的文�?
							jtxtImport.setText(file.getAbsolutePath());
							splitPaneInited = false;//重新导入文件后，再次初始化日志分析
						}
					}

				});
				jbtScan.setBounds(588, 167, 93, 23);
				importPane.add(jbtScan);
				contentPane.revalidate();
				contentPane.repaint();

			}

			if (e.getActionCommand().equals("日志分析")) {

				if (file == null) {
					JOptionPane.showMessageDialog(contentPane, "请先导入日志文件",
							"错误", JOptionPane.ERROR_MESSAGE, null);
					return;
				}

				contentPane.remove(welcomePane);
				contentPane.remove(importPane);
				contentPane.remove(resultPane);
				
				if (!splitPaneInited) {
					//splitPane.removeAll();
					JPanel leftPane = new JPanel();
					leftPane.setBorder(new LineBorder(new Color(0, 153, 255)));
					splitPane.setLeftComponent(leftPane);
					leftPane.setLayout(new MigLayout("", "[93px]",
							"[23px][][][][][][][]"));

					jbtLog = new JButton("\u65E5\u5FD7");
					leftPane.add(jbtLog, "cell 0 0,growx,aligny top");
					jbtLog.addActionListener(listener);
					jbtLog.setEnabled(false);

					jbtUser = new JButton("\u7528\u6237\u7F16\u53F7");
					leftPane.add(jbtUser, "cell 0 1,growx");
					jbtUser.addActionListener(listener);
					jbtUser.setEnabled(false);

					jbtUrl = new JButton("URL");
					leftPane.add(jbtUrl, "cell 0 2,growx");
					jbtUrl.addActionListener(listener);
					jbtUrl.setEnabled(false);

					jbtCatalog = new JButton("\u76EE\u5F55");
					leftPane.add(jbtCatalog, "cell 0 3,growx");
					jbtCatalog.addActionListener(listener);
					jbtCatalog.setEnabled(false);

					jbtSession = new JButton("\u4F1A\u8BDD\u8DEF\u5F84");
					leftPane.add(jbtSession, "cell 0 4,growx");
					jbtSession.addActionListener(listener);
					jbtSession.setEnabled(false);

					jbtTrans = new JButton("\u4E8B\u52A1\u8DEF\u5F84");
					leftPane.add(jbtTrans, "cell 0 5,growx");
					jbtTrans.addActionListener(listener);
					jbtTrans.setEnabled(false);

					jbtDirs = new JButton("\u76EE\u5F55\u8DEF\u5F84");
					leftPane.add(jbtDirs, "cell 0 6,growx");
					jbtDirs.addActionListener(listener);
					jbtDirs.setEnabled(false);

					

					JPanel rightPane = new JPanel();
					rightPane.setBorder(new LineBorder(new Color(0, 153, 255)));
					splitPane.setRightComponent(rightPane);
					rightPane.setLayout(new BorderLayout(0, 0));

					JPanel statusPane = new JPanel();
					rightPane.add(statusPane, BorderLayout.SOUTH);
					statusPane.setLayout(new BorderLayout());

					jpbar = new JProgressBar(1, 11);
					statusPane.add(jpbar, BorderLayout.SOUTH);

					jlabStatus = new JLabel("");
					// jlabStatus.setHorizontalAlignment(SwingConstants.RIGHT);
					statusPane.add(jlabStatus);

					tablePane = new JPanel();
					rightPane.add(tablePane, BorderLayout.CENTER);
					tablePane.setLayout(new BorderLayout());

					JPanel labelPane = new JPanel();
					labelPane.setLayout(new BorderLayout());
					jlabDescribe = new JLabel();
					labelPane.add(jlabDescribe);
					rightPane.add(labelPane, BorderLayout.NORTH);
					splitPane.setOpaque(false);
					rightPane.setOpaque(false);
					leftPane.setOpaque(false);
					tablePane.setOpaque(false);

					Task task = Task.getTask();
					task.setFile(file);
					task.addPropertyChangeListener(new ProgressListener());
					task.execute();

					splitPaneInited = true;
				}
				contentPane.add(splitPane, BorderLayout.CENTER);
				contentPane.revalidate();
				contentPane.repaint();

			}
			if (e.getActionCommand().equals("分析结果")) {
				if (file == null) {
					JOptionPane.showMessageDialog(contentPane, "请先导入日志文件",
							"错误", JOptionPane.ERROR_MESSAGE, null);
					return;
				}
				if (!splitPaneInited) {
					JOptionPane.showMessageDialog(contentPane, "请先点击日志分析，处理日志",
							"错误", JOptionPane.ERROR_MESSAGE, null);
					return;
				}
				contentPane.remove(importPane);
				contentPane.remove(welcomePane);
				contentPane.remove(splitPane);
				resultPane = new JPanel();
				contentPane.add(resultPane, BorderLayout.CENTER);
				resultPane.setLayout(null);

				JSplitPane resultSplitPane = new JSplitPane();
				resultSplitPane.setBounds(10, 10, 820, 280);
				resultPane.add(resultSplitPane);
				JPanel rightPane = new JPanel();
				TitledBorder tbRight = new TitledBorder(null,
						"用户期望的目录路径",
						TitledBorder.LEADING, TitledBorder.TOP, null, null);
				tbRight.setTitleFont(font);
				rightPane.setBorder(tbRight);
				JPanel leftPane = new JPanel();
				TitledBorder tbLeft = new TitledBorder(
						UIManager.getBorder("TitledBorder.border"),
						"\u539F\u7F51\u7AD9\u76EE\u5F55", TitledBorder.LEADING,
						TitledBorder.TOP, null, null);
				tbLeft.setTitleFont(font);
				leftPane.setBorder(tbLeft);
				
				rightPane.setLayout(new BorderLayout(0, 0));

				final JTree leftTree = new JTree();
				final JTree rightTree = new JTree();
				
				
				leftPane.setLayout(new BorderLayout(0, 0));
				resultSplitPane.setDividerLocation(0.5);


				final JTextPane textPane = new JTextPane();
				TitledBorder tbOptimize = new TitledBorder(null, "目录结构优化建议",
						TitledBorder.LEADING, TitledBorder.TOP, null, null);
				tbOptimize.setTitleFont(font);
				textPane.setBorder(tbOptimize);
				if (file.toString().endsWith("1.xls")) {
					leftTree.setModel(TreeModelFactory.getTreeModel(1));
					rightTree.setModel(TreeModelFactory.getTreeModel(2));
					textPane.setText("    全局相似度GTDCC = 0.6302 < 0.7,说明用户的心智模型和网站的分类目录结构存在一定差异，建议修改原有的目录结构。");
					leftTree.setCellRenderer(treeRenderer1);
					rightTree.setCellRenderer(treeRenderer1);
				} else {
					leftTree.setCellRenderer(treeRenderer2);
					rightTree.setCellRenderer(treeRenderer2);
					leftTree.setModel(TreeModelFactory.getTreeModel(2));
					rightTree.setModel(TreeModelFactory.getTreeModel(3));
					textPane.setText("    全局相似度GTDCC = 0.7343 > 0.7,说明用户的心智模型和网站的分类目录结构差异较小，不必修改原有的目录结构。");
				}
				
				TreeNode leftRoot = (TreeNode) leftTree.getModel().getRoot();
				TreeNode rightRoot = (TreeNode) rightTree.getModel().getRoot();
				expandTree(leftTree, new TreePath(leftRoot));
				expandTree(rightTree, new TreePath(rightRoot));
				leftPane.add(leftTree);
				rightPane.add(rightTree);
				resultSplitPane.setLeftComponent(leftPane);
				resultSplitPane.setRightComponent(rightPane);
				
				textPane.setBounds(10, 315, 820, 65);
				resultPane.add(textPane);
				textPane.setFont(font);

				JButton jbtSave = new JButton("保存分析结果");
				jbtSave.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						int state = jfc.showSaveDialog(Main.this);
						if (state == 1) {
							return;// 撤销则返回
						} else {
							file = jfc.getSelectedFile();// f为选择到的文件

						}
						if (!file.exists()) {
							try {
								file.createNewFile();
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(Main.this,
										"不能创建文件，保存失败！", "错误",
										JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							}
						}
						try {
							pw = new PrintWriter(new FileWriter(file, true));
							pw.println("原网站目录");
							visitAllNodes(leftTree);
							pw.println("优化后的网站目录");
							visitAllNodes(rightTree);
							pw.println("目录优化建议：");
							pw.println(textPane.getText());
							pw.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				jbtSave.setBounds(10, 386, 150, 35);
				resultPane.add(jbtSave);

				contentPane.revalidate();
				contentPane.repaint();
			}
			if (e.getActionCommand().equals("日志")) {
				String query = null;
				if(file.toString().endsWith("1.xls")){
					
					query = "SELECT * FROM tab_first limit 0,100";
				}else{
					query = "SELECT * FROM log.tab_first_new limit 0,100";
				}
				String[] titles = { "用户的IP地址", "访问时间", "请求方式", "请求的URL",
						"服务器回应状", "来源URL" };
				String tableName = "tab_first";
				jlabDescribe.setText("网络日志是服务器上记录的网页访问数据集合,可用于挖掘隐含其中的用户行为.");
				showTable(query, tableName, titles, 0);
			}
			if (e.getActionCommand().equals("用户编号")) {
				String query = "SELECT user_no,ip FROM tab_user limit 0, 100";
				String[] titles = { "用户编号", "IP地址" };
				String tableName = "tab_user";
				jlabDescribe.setText("每一个用户都有一个特定的编号");
				showTable(query, tableName, titles, 0);
			}
			if (e.getActionCommand().equals("URL")) {
				String query = "SELECT site_no,url,dir FROM tab_site  where url like '%Light%' limit 0,100";
				String[] titles = { "URL编号", "URL", "目录" };
				String tableName = "tab_site";
				jlabDescribe.setText("每一个URL都有一个特定的编号，若该URL为某个目录下的网页，则显示该目录");
				showTable(query, tableName, titles, 0);
			}
			if (e.getActionCommand().equals("目录")) {
				jlabDescribe.setText("日志数据中所有的目录及其编号");
				String query = "SELECT id,dir FROM log.tab_dir where id >= 160000";
				String[] titles = { "目录编号", "目录" };
				String tableName = "tab_dir";

				showTable(query, tableName, titles, 0);
			}
			if (e.getActionCommand().equals("会话路径")) {
				String query = "SELECT id,path FROM tab_session_view limit 0,100";
				String[] titles = { "会话路径编号", "会话路径" };
				String tableName = "tab_session_view";
				jlabDescribe.setText("会话路径是指用户对服务器的一次有效访问过程");
				showTable(query, tableName, titles, 0);
			}
			if (e.getActionCommand().equals("事务路径")) {
				String query = "SELECT id,path FROM tab_trans_view limit 0,100";
				String[] titles = { "事务路径编号", "事务路径" };
				String tableName = "tab_trans_view";
				jlabDescribe.setText("事务路径是指考虑用户前进后行为的更细微的会话路径");
				showTable(query, tableName, titles, 0);
			}
			if (e.getActionCommand().equals("目录路径")) {
				String query = "SELECT id, dir FROM tab_dir_path_view";
				String[] titles = { "目录路径编号", "目录路径" };
				String tableName = "tab_dir_path_view";
				jlabDescribe.setText("目录路径是把事务路径中涉及的网页替换为对应的目录说形成的目录序列");
				showTable(query, tableName, titles, 1);
			}

		}
		public void showTable(String query, final String tableName,
				String[] columnNames, int row) {
			JTable table = new JTable();
			try {
				JDBCTableModel model = new JDBCTableModel(conn, query,
						tableName, columnNames);
				table.setModel(model);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (row > 0) {
				TableUtil.fixTableColumnWidth(table, row);
			} else {
				TableUtil.fixTableColumnWidth(table);
			}
			JScrollPane s = new JScrollPane(table);
			if (tableName.equals("tab_trans_view")||tableName.equals("tab_session_view")||tableName.equals("tab_dir_path_view")) {
				System.out.println(tableName);
				final JTable table1 = table;
				table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
						     int rowIndex = table1.rowAtPoint(e.getPoint());
						     int columnIndex = table1.columnAtPoint(e.getPoint());
						      // your code
						     if(columnIndex > 0){
						    	 
						    	 int pathId =  (int) table1.getValueAt(rowIndex, 0);
						    	 System.out.println(pathId);
						    	 if(tableName.equals("tab_session_view") && pathId == 2 ){
						    		 ScanTrans.OpenBrowser(1);
						    	 }
						    	 if(tableName.equals("tab_trans_view") && pathId == 2 ){
						    		 ScanTrans.OpenBrowser(2);
						    	 }
						    	 if(tableName.equals("tab_trans_view") && pathId == 3 ){
						    		 ScanTrans.OpenBrowser(3);
						    	 } 
						    	 if(tableName.equals("tab_dir_path_view") && pathId == 4908 ){
						    		 ScanTrans.OpenBrowser(2);
						    	 }
						    	 if(tableName.equals("tab_dir_path_view") && pathId == 4909 ){
						    		 ScanTrans.OpenBrowser(3);
						    	 }
						     }
						      
						    }
					}
				});
			}
			tablePane.removeAll();
			tablePane.add(s);
			contentPane.revalidate();
			contentPane.repaint();
		}
		public void visitAllNodes(JTree tree) {
			TreeNode root = (TreeNode) tree.getModel().getRoot();
			visitAllNodes(root);
		}

		String space = "";

		public void visitAllNodes(TreeNode node) {
			pw.println(space + node);
			if (node.getChildCount() >= 0) {
				space += "  ";
				for (Enumeration e = node.children(); e.hasMoreElements();) {
					TreeNode n = (TreeNode) e.nextElement();
					visitAllNodes(n);
				}
				space = space.substring(0, space.length() - 2);
			}
		}

		public void showWelcomePane() {
			contentPane.remove(splitPane);
			contentPane.remove(importPane);
			contentPane.remove(resultPane);
			contentPane.add(welcomePane, BorderLayout.CENTER);
			welcomePane.setLayout(null);

			JLabel jlabWelcome0 = new JLabel("欢迎进入产品分类目录优化系统，请按如下步骤执行本系统：");
			jlabWelcome0.setFont(new Font("楷体", Font.PLAIN, 22));
			jlabWelcome0.setForeground(new Color(0, 0, 0));
			jlabWelcome0.setBounds(148, 100, 628, 26);
			welcomePane.add(jlabWelcome0);

			JLabel jlabWelcome1 = new JLabel("1.导入日志文件（xls，xlsx或txt格式）。");
			jlabWelcome1.setFont(new Font("楷体", Font.PLAIN, 20));
			jlabWelcome1.setForeground(new Color(0, 0, 0));
			jlabWelcome1.setBounds(229, 150, 547, 15);
			welcomePane.add(jlabWelcome1);

			JLabel jlabWecome2 = new JLabel("2.点击日志分析按钮，查看分析过程。");
			jlabWecome2.setFont(new Font("楷体", Font.PLAIN, 20));
			jlabWecome2.setBounds(229, 190, 547, 15);
			welcomePane.add(jlabWecome2);

			JLabel jlabWeclom3 = new JLabel("3.查看并导出分析结果。");
			jlabWeclom3.setFont(new Font("楷体", Font.PLAIN, 20));
			jlabWeclom3.setBounds(229, 230, 547, 15);
			welcomePane.add(jlabWeclom3);
			welcomePane.setOpaque(false);
			contentPane.revalidate();
			contentPane.repaint();
		}

		

	}

	public class ProgressListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			if ("progress" == evt.getPropertyName()) {
				int progress = (Integer) evt.getNewValue();
				System.out.println("now is processing" + progress);
				jpbar.setValue(progress);
				if (progress == 1) {
					jlabStatus.setText("正在初始化数据库...");
				} else if (progress == 2) {
					jlabStatus.setText("正在导入日志到数据库...");
				} else if (progress == 3) {
					jbtLog.setEnabled(true);
					jlabStatus.setText("正在预处理数据库中的日志...");
				} else if (progress == 4) {
					jlabStatus.setText("正在对日志数据编码...");
				} else if (progress == 5) {
					jlabStatus.setText("正在识别用户...");
				} else if (progress == 6) {
					jbtUser.setEnabled(true);
					jbtUrl.setEnabled(true);
					jbtCatalog.setEnabled(true);
					jbtCatalog.setEnabled(true);
					jlabStatus.setText("正在生成会话路径...");
				} else if (progress == 7) {
					jbtSession.setEnabled(true);
					jlabStatus.setText("正在生成事务路径...");
				} else if (progress == 8) {

					jlabStatus.setText("正在删除不符合要求的事务路径...");
				} else if (progress == 9) {
					jbtTrans.setEnabled(true);
					jlabStatus.setText("正在生成目录路径...");
				} else if (progress == 10) {
					jbtDirs.setEnabled(true);
					jlabStatus.setText("正在聚类分析...");
				} else {
					jlabStatus.setText("日志分析已完成");
					
				}
			}
		}

	}
}
