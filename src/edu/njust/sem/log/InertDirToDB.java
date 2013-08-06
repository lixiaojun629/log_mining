package edu.njust.sem.log;

import java.sql.*;

import org.apache.log4j.Logger;

import edu.njust.sem.log.util.DBUtil;

public class InertDirToDB {
	Connection conn;
	private String url;
	private long id;
	Logger logger = Logger.getLogger(this.getClass());

	public InertDirToDB() {
		conn = DBUtil.getConn();
	}

	public void getDirFromUrl() throws Exception {
		String queryAllUrls = "SELECT site_no,url  FROM tab_site1 "
				+ "where (url like '%-catalog/%' or url like '%/mic/%')and site_no >2168 ";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(queryAllUrls);
		String update = "update tab_site1 set dir = ? where site_no = ?";
		PreparedStatement psUpdate = conn.prepareStatement(update);
		while (rs.next()) {
			url = rs.getString("url");
			id = rs.getInt("site_no");
			System.out.println(url);
			if (url.matches("^http://www\\.made-in-china\\.com/.+")) {
				String dir = null;
				try {
					String html = GetDir.getHtmlByUrl(url);
					if (html == null | html.trim().equals("")) {
						continue;
					}
					dir = GetDir.getDirFromHtml(html);
				} catch (Exception e) {
					logger.error("site_no:" + id + "url:" + url);
					logger.error(e.getMessage());
					e.printStackTrace();
					continue;
				}
				psUpdate.setString(1, dir);
				psUpdate.setInt(2, rs.getInt("site_no"));
				psUpdate.executeUpdate();
				System.out.println(rs.getInt("site_no") + " : " + dir);
			} else {
				continue;
			}

		}
		// psUpdate.executeBatch();
	}

	public static void main(String[] args) {
		InertDirToDB dir = new InertDirToDB();
		try {
			dir.getDirFromUrl();
		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {
			DBUtil.closeConn();
		}
	}
}
