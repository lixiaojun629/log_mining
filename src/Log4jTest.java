import org.apache.log4j.Logger;


public class Log4jTest {
	static Logger logger = Logger.getLogger(Log4jTest.class);
	public static void main(String[] args) {
		logger.error("debug");
		int[] a = new int[2];
		System.out.println(a.toString());
	}
}
