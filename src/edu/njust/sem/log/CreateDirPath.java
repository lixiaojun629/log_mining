package edu.njust.sem.log;

import edu.njust.sem.log.util.DBUtil;
import java.sql.*;
/**
 * 替换事务路径中的url字段为该url所在目录的编号，得到目录路径
 * @author lixiaojun
 *
 */
public class CreateDirPath {
	public static void main(String[] args) throws SQLException {
		String query = "SELECT id, dir_id FROM log.tab_dir_path";
		String queryDirIdByUrl = "select dir_id  from tab_site1 where site_no = ?";
		String update = "update tab_dir_path set dir_id = ? where id = ?";
		Connection conn = DBUtil.getConn();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ResultSet rsDirId = null;
		PreparedStatement psQueryDirId = conn.prepareStatement(queryDirIdByUrl);
		PreparedStatement psUpdate = conn.prepareStatement(update);
		int urlId = 0;
		int id = 0;
		int dirId = 0;
		while(rs.next()){
			id = rs.getInt("id");
			urlId = rs.getInt("dir_id");
			psQueryDirId.setInt(1, urlId);
			rsDirId = psQueryDirId.executeQuery();
			if(rsDirId.next()){
				dirId = rsDirId.getInt("dir_id");
			}else{
				dirId = -1;
			}
			psUpdate.setInt(1, dirId);
			psUpdate.setInt(2, id);
			psUpdate.executeUpdate();
		}
	}
}
