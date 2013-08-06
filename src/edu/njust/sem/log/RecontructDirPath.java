package edu.njust.sem.log;

import edu.njust.sem.log.util.DBUtil;
import java.sql.*;

public class RecontructDirPath {
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getConn();
		Statement stmt = conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT path_id  FROM tab_dir_path1 group by 1";
		String update = "update tab_dir_path1 set path_id = ? where path_id = ?";
		PreparedStatement psUpdate= conn.prepareStatement(update);
		ResultSet rs = stmt.executeQuery(query);
		int i = 1;
		int pathId = 1;
		while(rs.next()){
			pathId = rs.getInt("path_id");
			psUpdate.setInt(1, i);
			psUpdate.setInt(2, pathId);
			i++;
			psUpdate.executeUpdate();
		}
	}
}
