package edu.njust.sem.log;

import java.sql.*;

import org.junit.Test;

import edu.njust.sem.log.util.DBUtil;

public class ShowPath {
	private Connection conn;

	@Test
	public void translate() throws SQLException {
		conn = DBUtil.getConn();
		String sqlInsert = "insert into  tab_dir_path3(session_id ,user,dir,user_ip) values(?,?,?,?)";
		PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT session_id ,dir,user,user_ip FROM tab_dir_path2");
		String dirPath = "";
		int nowSessionId = 35;
		int preSessionId = 35;
		int user = 0;
		String user_ip = "";
		int count = 0;
		while (rs.next()) {
			nowSessionId = rs.getInt("session_id");
			if (nowSessionId == preSessionId) {
				dirPath += rs.getString("dir") + " , ";
				count++;
			} else {
				rs.previous();
				user = rs.getInt("user");
				user_ip = rs.getString("user_ip");

				dirPath = dirPath.substring(0, dirPath.length() - 3);

				rs.next();
				psInsert.setInt(1, preSessionId);
				psInsert.setInt(2, user);
				psInsert.setString(3, dirPath);
				psInsert.setString(4, user_ip);
				psInsert.executeUpdate();
				dirPath = rs.getString("dir") + " , ";
				user = 0;
				user_ip = "";
				count = 0;
			}

			preSessionId = nowSessionId;
		}
		DBUtil.closeConn();
	}

	@Test
	public void test() {
		String str = "test" + " , ";
		System.out.println(str.length());
		System.out.println(str.substring(0, str.length() - 3));
	}
	public static void main(String[] args) {
		ShowPath sp = new ShowPath();
		try {
			sp.translate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
