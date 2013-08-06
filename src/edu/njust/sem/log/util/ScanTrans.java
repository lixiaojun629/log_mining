package edu.njust.sem.log.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

public class ScanTrans {

	public static void OpenBrowser(int pathId) {
		// Connection conn = DBUtil.getConn();
		//
		// QueryRunner qr = new QueryRunner();
		// String sql = "SELECT url FROM tab_trans where session_id = ?";
		// try {
		// List<Object[]> al = qr.query(conn,sql, new ArrayListHandler(),
		// pathId);
		// System.out.println(al);
		// for(Object[] o : al){
		// for(Object a : o){
		// System.out.println(a);
		// }
		// }
		// } catch (SQLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }finally{
		// //DBUtil.closeConn();
		// }
		URI uri = null;
		try {
			if (pathId == 1) {
				uri = new URI(
						"file:///C:/Users/lixiaojun/Desktop/LogMining/pages/html/gallery.html");
			}
			if (pathId == 2) {
				uri = new URI(
						"file:///C:/Users/lixiaojun/Desktop/LogMining/pages/html/gallery2.html");
			}
			if (pathId == 3) {
				uri = new URI(
						"file:///C:/Users/lixiaojun/Desktop/LogMining/pages/html/gallery3.html");
				
			}
			Desktop.getDesktop().browse(uri);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
