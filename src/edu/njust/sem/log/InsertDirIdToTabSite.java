package edu.njust.sem.log;

import java.sql.*;

import org.junit.Test;

import edu.njust.sem.log.util.DBUtil;

/**
 * 从tab_dir中找出和tab_site1中dir相同的记录的id，并把此id更新的表tab_site1中
 * 
 * @author lixiaojun
 * 
 */
public class InsertDirIdToTabSite {
 
	public static void main(String[] args) {
		
	 
		String query = "select site_no, dir from tab_site1 where dir <>''";
		String queryid = "select id from tab_dir where dir = ?";
		String update = "update tab_site1 set dir_id = ? where site_no = ?";
		Connection conn = DBUtil.getConn();
		try {
			PreparedStatement psUpdate = conn.prepareStatement(update);
			Statement stmt = conn.createStatement();
			PreparedStatement psSelectDirId = conn.prepareStatement(queryid);
			
			ResultSet rs = stmt.executeQuery(query);
			ResultSet rsId = null;
			int siteNo = 0;
			int dirId = 0;
			String dir = null;
			while (rs.next()) {
				siteNo = rs.getInt("site_no");
				dir = rs.getString("dir");
				psSelectDirId.setString(1, dir);
				rsId = psSelectDirId.executeQuery();
				if (rsId.next()) {
					dirId = rsId.getInt("id");
					psUpdate.setInt(1, dirId);
					psUpdate.setInt(2, siteNo);
					psUpdate.executeUpdate();
				} else {
					System.out.println("site_no : " + siteNo + "dir : " + dir);
					continue;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConn();
		}

	}

}
