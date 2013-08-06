package edu.njust.sem.log;

import java.sql.*;

import edu.njust.sem.log.util.DBUtil;

/**
 * 删除目录路径中只有一个节点的路径
 * 
 * @author lixiaojun
 * 
 */
public class DelDirPath {
	public static void main(String[] args) throws SQLException {
		String query = "SELECT count(*) ,id  FROM tab_dir_path1 group by path_id";
		String delete = "delete from tab_dir_path1 where id = ?";
		Connection conn = DBUtil.getConn();
		PreparedStatement psDelete = conn.prepareStatement(delete);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		int count = 0;
		int id = 0;
		while (rs.next()){
			count = rs.getInt(1);
			id = rs.getInt(2);
			if(count < 2){
				psDelete.setInt(1, id);
				psDelete.executeUpdate();
			}
		}
		
	}
}
