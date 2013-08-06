package edu.njust.sem.log;
import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.njust.sem.log.LogTreatment;
import edu.njust.sem.log.util.DBUtil;

public class LogTreatmentTest {
	LogTreatment lt = null;
	Connection conn = null;

	@Before
	public void setUp() {
		conn = DBUtil.getConn();
		lt = LogTreatment.getLogTreatment();

	}

	@After
	public void tearDown() {
		DBUtil.closeConn();
		lt = null;

	}

	@Test
	public void testDelTrans() {

		lt.delTrans();

	}

}
