package edu.njust.sem.log;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import edu.njust.sem.log.util.DBUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DirImport {
	private int[] id = new int[3];
	private int ID = 0;
	private int row = 0;
	private Workbook wb = null;
	private Sheet sheet = null;
	private Scanner scan = null;
	private Cell[] cell = null;
	private File file = null;
	private String strDir = null;
	private String[] dir = new String[3];
	private String insertTabDir = "insert into tab_dir (id ,dir)values(?,?)";
	private Connection conn = null;
	private PreparedStatement ps = null;

	public DirImport() {
		conn = DBUtil.getConn();
		try {
			ps = conn.prepareStatement(insertTabDir);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file = new File("制造网分类目录~~~(new).xls");
		for (int i = 0; i < dir.length; i++) {
			dir[i] = "";
		}
		for (int i = 0; i < id.length; i++) {
			id[i] = 0;
		}

	}

	public void dirImport() {

		try {
			wb = Workbook.getWorkbook(file);
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet = wb.getSheet(0);
		row = sheet.getRows();

		for (int i = 0; i < row; i++) {
			cell = sheet.getRow(i);
			for (int j = 0; j < cell.length; j++) {
				// 如果当前处理的目录与上次的同等级的目录不同，则相应等级的id[j]++，该等级以下的id[k]清零。
				strDir = cell[j].getContents();
				System.out.println(strDir);
				if (!dir[j].equals(strDir)) {
					id[j]++;
					if (id[j] > 99) {
						System.out.println("error!");
					}
					if (j < 2) {
						for (int k = j + 1; k < 3; k++) {
							id[k] = 0;
						}
					}

					dir[j] = cell[j].getContents();
					for (int k = 0; k < 3; k++) {
						ID += id[k] * Math.pow(10, (2 - k) * 2);
					}
					insertDirToDB(ID, dir[j]);
					ID = 0;
				}

			}
		}

	}

	public void insertDirToDB(int id, String dir) {

		try {

			ps.setInt(1, id);
			ps.setString(2, dir);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}