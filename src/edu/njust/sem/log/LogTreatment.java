package edu.njust.sem.log;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimeZone;

import javax.swing.JOptionPane;


import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import edu.njust.sem.log.util.DBUtil;

public class LogTreatment {

	private static Connection conn;
	private static LogTreatment lt = null;
	private File file ;
	// ��ȡ���ݿ�����
	private LogTreatment() {
		conn = DBUtil.getConn();
	}

	public void LogImport() {
		int row = 2;
		Workbook wb = null;
		Sheet sheet = null;
		Scanner scan = null;
		Cell[] cell = null;
		DateCell dc = null;
		String sqlInsertLog = "insert into tab_first(IPTONUMBER,VISIT_TIME,METHOD,URL,WEB_STATUS,REFERER)values(?,?,?,?,?,?)";
		PreparedStatement ps = null;

		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "�ļ����ܴ�");
			System.exit(-1);
		}
		try {

			ps = conn.prepareStatement(sqlInsertLog);
			wb = Workbook.getWorkbook(file);
			sheet = wb.getSheet(0);

			scan = new Scanner(file);

			TimeZone gmtZone = TimeZone.getTimeZone("GMT");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(gmtZone);

			do {
				cell = sheet.getRow(row++);
				// j��ǰ���ݸ���excel�еĵڼ��У�iΪ��ǰ���ݸ������ݿ��еĵڼ���
				for (int i = 0, j = 0; j < cell.length; i++, j++) {
					if (cell.length == 7 && j == 5) {
						j++;
					}

					if (i == 1) {
						try {
							dc = (DateCell) sheet.getCell(1, row - 1);
						} catch (Exception e) {
							System.out.println(dc.getContents());

							dc = null;
						}

						if (dc != null) {
							ps.setObject(i + 1, dc.getDate());
						} else {
							continue;

						}

					} else {
						ps.setObject(i + 1, cell[j].getContents());
					}

				}
				ps.addBatch();
				if (row % 5000 == 0) {
					ps.executeBatch();
				}
			} while (row < sheet.getRows() && !cell[0].getContents().equals(""));
			ps.executeBatch();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (scan != null) {
					scan.close();
					scan = null;

				}
				if (ps != null) {
					ps.close();
					ps = null;

				}

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * ���tab_site,tab_user�������ݡ�
	 * �ȴӱ�tab_first�в�ѯ�����е�url,referer�ֶεļ�¼��Ȼ�����Щ��¼���뵽��tab_site_temp�У�
	 * �ٴӱ�tab_site_temp�в�ѯ�����е�url�ֶμ�¼������distinct����ʹ��ÿ��url����һ��Ψһ�ı�š�
	 * 
	 */

	public void insertTab1() {

		String query = "select  referer,url from tab_first";

		String insertTabSiteTemp = "insert into tab_site_temp (url)values(?)";
		String insertTabSite = "insert into tab_site(url) select distinct url from tab_site_temp";
		String insertUser = "insert into tab_user(ip) select distinct iptonumber from tab_first";
		int count = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// �������ݵ���tab_site_temp
		try {
			ps = conn.prepareStatement(insertTabSiteTemp);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				ps.setString(1, rs.getString("referer"));
				ps.addBatch();
				ps.setString(1, rs.getString("url"));
				ps.addBatch();
				count++;
				if (count % 5000 == 0) {
					ps.executeBatch();
				}
			}
			ps.executeBatch();
			stmt.executeUpdate(insertTabSite);
			stmt.executeUpdate(insertUser);// �������ݵ���tab_user��
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;

				}
				if (rs != null) {
					rs.close();
					rs = null;

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	// �������ݵ���tab_first_no��
	public void createTabFirstNo() {
		String copy = "insert into tab_first_no (visit_time,method,web_status) select visit_time,method,web_status from tab_first";
		String queryLog = "select iptonumber,url,referer from tab_first";
		String queryIp = "select user_no from tab_user where ip = ?";
		String queryUrl = "select site_no from tab_site where url = ?";
		String queryReferer = "select site_no from tab_site where url = ?";
		String updateTabFirstNo = "update tab_first_no set user = ? ,url= ?,referer = ? where id = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;

		PreparedStatement psIp = null;
		PreparedStatement psUrl = null;
		PreparedStatement psReferer = null;
		ResultSet rsTemp = null;
		int userID = 0;
		String ip = null;

		String url = null;
		int urlID = 0;
		int count = 0;
		String referer = null;
		int refererID = 0;
		int id = 1;
		try {
			stmt = conn.createStatement();
			ps = conn.prepareStatement(updateTabFirstNo);
			psIp = conn.prepareStatement(queryIp);
			psUrl = conn.prepareStatement(queryUrl);
			psReferer = conn.prepareStatement(queryReferer);
			stmt.executeUpdate(copy);
			rs = stmt.executeQuery(queryLog);
			while (rs.next()) {
				ip = rs.getString(1);
				url = rs.getString(2);
				referer = rs.getString(3);
				psIp.setString(1, ip);
				psUrl.setString(1, url);
				psReferer.setString(1, referer);

				rsTemp = psIp.executeQuery();
				rsTemp.next();
				userID = rsTemp.getInt(1);

				rsTemp = psUrl.executeQuery();
				rsTemp.next();
				urlID = rsTemp.getInt(1);

				rsTemp = psReferer.executeQuery();
				rsTemp.next();
				refererID = rsTemp.getInt(1);

				ps.setInt(1, userID);
				ps.setInt(2, urlID);
				ps.setInt(3, refererID);
				ps.setInt(4, id++);

				ps.addBatch();

				if (count++ % 5000 == 0) {

					ps.executeBatch();
				}
			}

			ps.executeBatch();
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (psIp != null) {
					psIp.close();
					psIp = null;
				}
				if (psUrl != null) {
					psUrl.close();
					psUrl = null;
				}
				if (psReferer != null) {
					psReferer.close();
					psReferer = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (rsTemp != null) {
					rsTemp.close();
					rsTemp = null;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
		}

	}

	/**
	 * ���tab_site_directory��tab_site_word��tab_fail�в�������
	 */

	public void insertTab2() {
		String sqlInsertTabFail = "insert into tab_fail select * from tab_first_no where web_status <> 200";
		String sqlInsertTabSiteWord = "insert into tab_site_word(url) select site_no from tab_site where tab_site.url like '%word=%'";
		String sqlInsertTabSiteDir = "insert into tab_site_directory (url) select site_no from tab_site where tab_site.url like'%productdirectory.do?%'";

		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sqlInsertTabSiteDir);// ���tab_site_directory�в�������
			stmt.executeUpdate(sqlInsertTabSiteWord);
			stmt.executeUpdate(sqlInsertTabFail);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ڱ�tab_first_no�в�ѯ��ͬһ���û�(user�����ͬ)�ļ�¼�����Ӽ�¼������������referer��url�ֶ�,
	 * �������referer1,url1,referer2,url2,referer3,url3......����վ���У�
	 * �ڸ������У��Ȱ�referer1���뵽�����У�Ȼ�����ΰѺ����Ԫ�ذ����¹��������У�
	 * ���������Ԫ������е����һ��Ԫ����ͬ���������ô�����Ԫ�أ�ѡȡ��һ��������Ԫ����ͬ���Ĵ��� �����������Ժ�Ϳ��Եõ�һ�������ĻỰ��
	 */
	public void createTabSession() {
		String query = "select user ,visit_time,url,referer from tab_first_no order by user";
		String insert = "insert into tab_session (session_id,url,user)values(?,?,?)";
		int count = 0;
		int userPre = 0;
		int userNow = 0;
		int sessionID = 1;
		Date datePre = null;
		Date dateNow = null;
		ArrayList<Integer> alSessionUrl = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			rs = stmt.executeQuery(query);
			ps = conn.prepareStatement(insert);
			rs.first();

			userPre = userNow = rs.getInt("user");
			datePre = dateNow = rs.getDate("visit_time");
			alSessionUrl.add(rs.getInt("referer"));// �Ȱ���վ��¼�����û���һ����ַ���뵽������
			do {
				userPre = userNow;
				userNow = rs.getInt("user");

				datePre = dateNow;
				dateNow = rs.getDate("visit_time");

				// ������ڵ�������¼�е�user����ͬ������ʱ�䳬��30���ӣ�������Ϊͬһ���Ự�����Ѿ�ʶ��ĻỰ·�����뵽���ݿ���
				if (userPre != userNow
						|| (dateNow.getTime() - datePre.getTime())
								/ (1000 * 60) >= 30) {

					for (int i = 0; i < alSessionUrl.size(); i++, count++) {
						ps.setInt(1, sessionID);
						ps.setInt(2, alSessionUrl.get(i));
						ps.setInt(3, userPre);
						ps.addBatch();
						if (count % 10000 == 0) {
							ps.executeBatch();
						}
					}
					sessionID++;
					alSessionUrl.clear();
					alSessionUrl.add(rs.getInt("referer"));
					if (rs.getInt("referer") != rs.getInt("url")) {
						alSessionUrl.add(rs.getInt("url"));
					}

				} else {

					if (rs.getInt("referer") != alSessionUrl.get(alSessionUrl
							.size() - 1)) {
						alSessionUrl.add(rs.getInt("referer"));
					}

					if (rs.getInt("url") != alSessionUrl.get(alSessionUrl
							.size() - 1)) {
						alSessionUrl.add(rs.getInt("url"));
					}
				}

			} while (rs.next());
			ps.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ���Ȼ�ȡһ���Ự·�����У��Ӹö��еĵڶ���Ԫ�ؿ�ʼ�����Ҹ�Ԫ����ߵ�Ԫ�صķ����뵱ǰԪ��now��ͬ��Ԫ��same
	 * ����У��������Ϊ�ӵ�һ��Ԫ�ؿ�ʼ����ǰԪ��now��ǰһ��Ԫ�ص�����Ϊһ������·�������Ҽ�¼��sameԪ�ص�λ��
	 * ����ʶ���������·�����뵽���ݿ��У�Ȼ��ɾ���Ự·����now��same֮�������Ԫ�أ�ɾ��������nowԪ����sameԪ����ͬ������һ�����ɣ�
	 * �õ��µĻỰ·�������Ժ���ͬ���Ĵ���
	 */
	public void createTabTrans() {
		String queryUrlUser = "select url,user from tab_session where session_id = ?";
		String insertTrans = "insert into tab_trans (session_id,url,user)values(?,?,?)";
		String queryRowCount = "select  session_id  from tab_session  order by session_id desc limit 1";
		int sessionCount = 0;// �ܵĻỰ·������
		int sessionID = 1;
		int transID = 1;
		PreparedStatement psInsertTrans = null;
		PreparedStatement psQueryUrlUser = null;
		ResultSet rsSessionCount = null;
		ResultSet rsUrlUser = null;
		Statement stmt = null;

		ArrayList<Integer> alSessionUrl = new ArrayList<>();
		// �����ʶ�������·�� ������������ݿ���
		ArrayList<Integer> alTransUrl = new ArrayList<>();

		try {
			stmt = conn.createStatement();
			psQueryUrlUser = conn.prepareStatement(queryUrlUser);
			psInsertTrans = conn.prepareStatement(insertTrans);
			rsSessionCount = stmt.executeQuery(queryRowCount);
			rsSessionCount.first();
			sessionCount = rsSessionCount.getInt(1);

			for (int m = 0; m < sessionCount; m++) {
				psQueryUrlUser.setInt(1, sessionID++);
				rsUrlUser = psQueryUrlUser.executeQuery();// ��ȡ�Ự����(url,user)
				if (!alSessionUrl.isEmpty()) {
					alSessionUrl.clear();
				}
				while (rsUrlUser.next()) {
					alSessionUrl.add(rsUrlUser.getInt("url"));
				}
				rsUrlUser.last();

				// ����Ự·�������е�Ԫ�ظ���ֻ��һ������ֱ�Ӳ���
				if (alSessionUrl.size() < 2) {
					if (!alSessionUrl.isEmpty()) {
						insertTransToDB(alSessionUrl, psInsertTrans,
								rsUrlUser.getInt("user"), transID++);
					}

				} else {

					for (int i = 1; i < alSessionUrl.size(); i++) {
						for (int j = 0; j < i; j++) {
							if ((int) alSessionUrl.get(i) == (int) alSessionUrl
									.get(j)) {
								for (int k = 0; k < i; k++) {
									alTransUrl.add(alSessionUrl.get(k));
								}
								insertTransToDB(alTransUrl, psInsertTrans,
										rsUrlUser.getInt("user"), transID++);
								alTransUrl.clear();

								for (int k = j; k < i; i--) {
									alSessionUrl.remove(k);
								}

							}

						}

					}
					insertTransToDB(alSessionUrl, psInsertTrans,
							rsUrlUser.getInt("user"), transID++);

				}

			}

		} catch (SQLException e) {
			printSQLException(e);
		}

	}

	public void insertTransToDB(ArrayList<Integer> al, PreparedStatement ps,
			int user, int sessionID) {
		int length = al.size();
		if (length == 0) {
			return;
		}
		try {
			for (int i = 0; i < length; i++) {
				ps.setInt(1, sessionID);
				ps.setInt(2, al.get(i));
				ps.setInt(3, user);
				ps.addBatch();

			}
			ps.executeBatch();

		} catch (SQLException e) {
			printSQLException(e);
		}

	}

	/**
	 * ɾ��δʹ���������������·�� ɾ��δʹ�÷���Ŀ¼������·�� ɾ������ʧ�ܵ�����·��
	 * ���ڱ�tab_trans��ѯ��һ������·����url���У�url1��url2��url3......
	 * Ȼ���ڱ�tab_site_word,tab_site_directory
	 * ,tab_fail���������url1,url2,url3.....�Ƿ��������Щ����,
	 * ��url1,url2,url3...ȫ��δ�����ڱ�tab_site��tab_site_directory�У���ɾ��������·��
	 * ��url1,url2,url3...�еĺ�������ַ��һ�������������ڱ�tab_fail�У�����user��ͬ����ɾ��������·��
	 */

	public void delTrans() {
		// ɾ��δʹ����������ĻỰ
		String queryTrans = "select url,user from tab_trans where session_id = ?";
		// String sqlConformWord =
		// "select count(*) from tab_site_word where url = ?";
		String sqlConformDir = "select count(*) from tab_site_directory where url = ?";
		String sqlConformFail = "select count(*) from tab_fail where url = ? and user = ?";
		String sqlDelSession = "delete  from tab_trans where session_id = ?";
		String sqlQueryTransCount = "select  session_id  from tab_trans  order by session_id desc limit 1";

		int id = 1;
		int url = 0;
		int transCount = 0;

		// boolean flagWord = true;
		boolean flagDir = true;
		boolean flagFail = false;

		ResultSet rsTransCount = null;
		ResultSet rsTrans = null;
		// ResultSet rsConformWord = null;
		ResultSet rsConformDir = null;
		ResultSet rsConformFail = null;

		PreparedStatement psQueryTrans = null;
		// PreparedStatement psConformWord = null;
		PreparedStatement psConformDir = null;
		PreparedStatement psConformFail = null;
		PreparedStatement psDelSession = null;

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			psQueryTrans = conn.prepareStatement(queryTrans);
			// psConformWord = conn.prepareStatement(sqlConformWord);
			psConformDir = conn.prepareStatement(sqlConformDir);
			psConformFail = conn.prepareStatement(sqlConformFail);
			psDelSession = conn.prepareStatement(sqlDelSession);

		} catch (SQLException e) {
			printSQLException(e);
		}

		try {
			rsTransCount = stmt.executeQuery(sqlQueryTransCount);
			rsTransCount.next();
			transCount = rsTransCount.getInt(1);// ��ȡ�ܵ�����·������
			rsTransCount.close();
			rsTransCount = null;
			for (int i = 0; i < transCount; i++, ++id) {
				psQueryTrans.setInt(1, id);
				if (rsTrans != null) {
					rsTrans.close();
					rsTrans = null;
				}
				rsTrans = psQueryTrans.executeQuery();
				rsTrans.last();
				int rowCount = rsTrans.getRow();// ��ȡ�������������
				rsTrans.beforeFirst();
				while (rsTrans.next()) {
					url = rsTrans.getInt("url");// ��ȡҪ�Ƚϵ�url
					// psConformWord.setInt(1, url);
					psConformDir.setInt(1, url);

					// rsConformWord = psConformWord.executeQuery();
					rsConformDir = psConformDir.executeQuery();

					// rsConformWord.next();
					rsConformDir.next();
					// int wordurl = rsConformWord.getInt(1);
					// if (rsConformWord.getInt(1) >= 1) {
					// flagWord = false;
					// }
					// int dirurl = rsConformDir.getInt(1);
					if (rsConformDir.getInt(1) >= 1) {
						flagDir = false;
					}
					rsConformDir.close();
					rsConformDir = null;
					// rsConformWord.close();
					// rsConformWord = null;
					// //
					// ��url1,url2,url3...�еĺ�������ַ��һ�������������ڱ�tab_fail�У�����user��ͬ����ɾ��������·��
					if (rowCount == rsTrans.getRow()) {
						psConformFail.setInt(1, rsTrans.getInt("url"));
						psConformFail.setInt(2, rsTrans.getInt("user"));

						rsConformFail = psConformFail.executeQuery();
						rsConformFail.next();

						// int failurl = rsConformFail.getInt(1);
						if (rsConformFail.getInt(1) >= 1) {
							flagFail = true;
						}
						rsConformFail.close();
						rsConformFail = null;

					}

				}
				// ����Ự·���е�����url��δʹ���������棬��δʹ�÷���Ŀ¼������һ������ʧ�ܷ��ʼ�¼����ɾ��������·��
				flagFail = false;
				if (flagDir || flagFail) {
					psDelSession.setInt(1, id);
					psDelSession.addBatch();
					if (id % 5000 == 0) {
						psDelSession.executeBatch();
					}
					psDelSession.executeBatch();
				}
			}

		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			try {
				// if (rsConformWord != null) {
				// rsConformWord.close();
				// rsConformWord = null;
				// }
				if (rsConformDir != null) {
					rsConformDir.close();
					rsConformDir = null;
				}
				if (rsConformFail != null) {
					rsConformFail.close();
					rsConformFail = null;
				}
				if (rsTrans != null) {
					rsTrans.close();
					rsTrans = null;
				}
				if (rsTransCount != null) {
					rsTransCount.close();
					rsTransCount = null;
				}

			} catch (SQLException e) {
				printSQLException(e);
			}
		}
	}

	public double[] getResult() {
		String queryLogCount = "SELECT count(*) FROM tab_first";
		String querySiteCount = "SELECT count(*) FROM tab_site";
		String queryUserCount = "SELECT count(*) FROM tab_user";
		String queryTransCount = "select session_id from tab_trans order by session_id desc limit 1";
		String queryUrlWordCount = "select count(*) from tab_site_word";
		String queryUrlDirCount = "select count(*) from tab_site_directory";
		int logCount = 0;
		int siteCount = 0;
		int userCount = 0;
		int transCount = 0;
		double urlWordCount = 0;
		double urlDirCount = 0;

		double wordUrl = 0;
		double dirUrl = 0;

		double[] result = new double[7];

		ResultSet rs = null;
		try (Statement stmt = conn.createStatement()) {
			rs = stmt.executeQuery(queryLogCount);
			if (rs.next()) {
				logCount = rs.getInt(1);
			}
			rs.close();
			rs = null;

			rs = stmt.executeQuery(querySiteCount);
			if (rs.next()) {
				siteCount = rs.getInt(1);
			}
			rs.close();
			rs = null;

			rs = stmt.executeQuery(queryUserCount);
			if (rs.next()) {
				userCount = rs.getInt(1);
			}
			rs.close();
			rs = null;

			rs = stmt.executeQuery(queryTransCount);
			if (rs.next()) {
				transCount = rs.getInt(1);
			}
			rs.close();
			rs = null;

			rs = stmt.executeQuery(queryUrlWordCount);
			if (rs.next()) {
				urlWordCount = rs.getInt(1);
			}
			rs.close();
			rs = null;

			rs = stmt.executeQuery(queryUrlDirCount);
			if (rs.next()) {
				urlDirCount = rs.getInt(1);
			}
			rs.close();
			rs = null;

			wordUrl = urlWordCount / siteCount;
			dirUrl = urlDirCount / siteCount;

			result[0] = logCount;
			result[1] = siteCount;
			result[2] = userCount;
			result[3] = transCount;
			result[4] = wordUrl;
			result[5] = dirUrl;

		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
		}
		return result;

	}

	public void clearAllTable() {

		String sql1 = "truncate tab_first";
		String sql2 = "truncate tab_first_no";
		String sql3 = "truncate tab_user";
		String sql4 = "truncate tab_site";
		String sql5 = "truncate tab_site_word";
		String sql6 = "truncate tab_site_directory";
		String sql7 = "truncate tab_trans";
		String sql8 = "truncate tab_session";
		String sql9 = "truncate tab_site_temp";
		String sql10 = "truncate tab_fail";

		String[] clear = { sql1, sql2, sql3, sql4, sql5, sql6, sql7, sql8,
				sql9, sql10 };
		try (Statement stmt = conn.createStatement()) {
			for (int i = 0; i < clear.length; i++) {
				stmt.executeUpdate(clear[i]);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}

	}

	public static void printSQLException(SQLException e) {
		// Unwraps the entire exception chain to unveil the real cause of the
		// Exception.
		while (e != null) {
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			e.printStackTrace();
			// for stack traces, refer to derby.log or uncomment this:
			// e.printStackTrace(System.err);
			e = e.getNextException();
		}
	}

	public static LogTreatment getLogTreatment() {
		if (lt == null) {
			lt = new LogTreatment();
		}
		return lt;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
