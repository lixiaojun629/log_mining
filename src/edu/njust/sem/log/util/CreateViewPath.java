package edu.njust.sem.log.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class CreateViewPath {
	private Connection conn;
	@Test
	public void changeForm() {
		conn = DBUtil.getConn();
		String queryTrans = "select url,user from tab_session where session_id = ?";
		String insert = "insert  into tab_session_view (id , path) values(?,?)";
		PreparedStatement psQuery = null;
		PreparedStatement psInsert = null;
		ResultSet rs = null;
		int n = 1000;
		try {
			psQuery = conn.prepareStatement(queryTrans);
			psInsert = conn.prepareStatement(insert);
			for (int i = 0; i < n; i++) {
				psQuery.setInt(1, i + 1);
				rs = psQuery.executeQuery();
				if (!rs.next()) {
					continue;
				}
				String strUrls = "";
				rs.previous();
				while (rs.next()) {
					strUrls += "p" + rs.getInt("url");
					if (!rs.isLast()) {
						strUrls += ", ";
					}
				}
				psInsert.setInt(1, i+1);
				psInsert.setString(2, strUrls);
				psInsert.executeUpdate();
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			DBUtil.closeConn();
		}
	}
}
