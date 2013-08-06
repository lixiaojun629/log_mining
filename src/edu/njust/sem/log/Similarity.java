package edu.njust.sem.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.njust.sem.log.util.DBUtil;

public class Similarity {
	private Connection conn = DBUtil.getConn();
	private double[][] jz = null;

	public Similarity() {

	}

	public double[][] getSimilarityMatrix() {
		String queryDirPath = "select path_id ,dir_id from tab_dir_path1 where path_id = ?";
		String queryPathCount = "select path_id from tab_dir_path1 order by 1 desc limit 1";
		Statement stmt = null;
		PreparedStatement psDirPath = null;
		ResultSet rsDirPath = null;
		ResultSet rsPathCount = null;
		ArrayList<Integer> alnow = new ArrayList<Integer>();
		ArrayList<Integer> alnext = new ArrayList<Integer>();
		int now = 0;
		int next = 0;
		int pathCount = 0;
		double similarity = 0;
		try {
			stmt = conn.createStatement();
			rsPathCount = stmt.executeQuery(queryPathCount);
			rsPathCount.next();
			pathCount = rsPathCount.getInt(1);
			psDirPath = conn.prepareStatement(queryDirPath);
			System.out.println(pathCount);
			jz = new double[pathCount][];
			for (int i = 0; i < pathCount; i++) {
				jz[i] = new double[pathCount];
			}
			for (int i = 0; i < pathCount; i++) {
				psDirPath.setInt(1, i + 1);
				rsDirPath = psDirPath.executeQuery();
				while (rsDirPath.next()) {
					alnow.add(rsDirPath.getInt("dir_id"));
				}
				for (int j = i + 1; j < pathCount; j++) {
					
					psDirPath.setInt(1, j + 1);
					rsDirPath = psDirPath.executeQuery();
					while (rsDirPath.next()) {
						alnext.add(rsDirPath.getInt("dir_id"));
					}
					Integer[] pathNow = alnow.toArray(new Integer[0]);
					Integer[] pathNext = alnext.toArray(new Integer[0]);
					similarity = getSimilarity(pathNow, pathNext);
					jz[i][j] = similarity;
					alnext.clear();
					System.out.print(jz[i][j] + " ");
					//jz[j][i] = similarity;
				}
				System.out.println();
				alnow.clear();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jz;
	}

	public double getSimilarity(Integer[] pathNow, Integer[] pathNext) {
		int innerProudct = getInnerProduct(pathNow, pathNext);
		int length = Math.min(pathNow.length, pathNext.length);
		int innerProudct1 = getInnerProductOfTwoSamePath(pathNow, length);
		int innerProudct2 = getInnerProductOfTwoSamePath(pathNext, length);
		return innerProudct / Math.sqrt(innerProudct1 * innerProudct2);

	}

	public int getInnerProduct(Integer[] pathNow, Integer[] pathNext) {
		int length = Math.min(pathNow.length, pathNext.length);
		int innerProduct = 0;
		for (int i = 0; i < length; i++) {
			int[][] tiaoPathNow = getTiaoPath(pathNow, i);
			int[][] tiaoPathNext = getTiaoPath(pathNext, i);
			innerProduct += getPartInnerProduct(tiaoPathNow, tiaoPathNext);
			// 如果两条路径的0跳子路径的内积为0，则这两条路径的内积肯定为0,可直接返回0，不必继续运算
			if (innerProduct == 0) {
				return 0;
			}
		}
		return innerProduct;
	}

	public int getPartInnerProduct(int[][] tiaoPathNow, int[][] tiaoPathNext) {
		String[] pathNow = new String[tiaoPathNow.length];
		String[] pathNext = new String[tiaoPathNext.length];
		int r = tiaoPathNow[0].length;
		int partInnerProduct = 0;
		for (int i = 0; i < pathNow.length; i++) {
			pathNow[i] = getStringContent(tiaoPathNow[i]);
		}
		for (int i = 0; i < pathNext.length; i++) {
			pathNext[i] = getStringContent(tiaoPathNext[i]);
		}
		for (int i = 0; i < pathNow.length; i++) {
			for (int j = 0; j < pathNext.length; j++) {
				if (pathNow[i].equals(pathNext[j])) {
					partInnerProduct += r * r;
				}
			}
		}

		return partInnerProduct;
	}

	public String getStringContent(int[] a) {
		String str = "";
		for (int i = 0; i < a.length; i++) {
			str += a[i] + ',';
		}
		return str;
	}

	public int[][] getTiaoPath(Integer[] path, int r) {
		if (r >= path.length) {
			return null;
		} else {
			int tiaoPathCount = path.length - r;// r跳路径的个数
			int[][] tiaoPath = new int[tiaoPathCount][r + 1];
			for (int i = 0; i < tiaoPathCount; i++) {
				for (int j = 0; j < r + 1; j++) {
					tiaoPath[i][j] = path[i + j];
				}
			}
			return tiaoPath;
		}
	}

	public int getInnerProductOfTwoSamePath(Integer[] path, int length) {
		int innerProduct = 0;

		for (int i = 0; i < length; i++) {
			innerProduct += (path.length - i) * (i + 1) * (i + 1);
		}
		return innerProduct;

	}

	public static void main(String[] args) {
		Similarity sim = new Similarity();
		// Integer[] a ={1,2,3,4,5};
		// Integer[] b = {1,2,3,4,5};
		// System.out.println(sim.getInnerProduct(a, b));
		// System.out.println(sim.getInnerProductOfTwoSamePath(a,5));
		// System.out.println(sim.getInnerProductOfTwoSamePath(b,5));
		// System.out.println(sim.getSimilarity(a, b));
		double[][] m = sim.getSimilarityMatrix();
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.print(m[i][j] + ",");
			}
			System.out.println();
		}
	}
}
