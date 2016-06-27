package selenium.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class Verify {

	protected static final Logger logger = LogManager.getLogger(Verify.class);

	public static void verifyTrue(FailureMonitor monitor, String errorMessage, boolean condition) {
		try {
			Assert.assertTrue(errorMessage, condition);
		} catch (AssertionError e) {
			monitor.addException(e);
			logger.error(e.getMessage());
		}

	}

	public static void verifyFalse(FailureMonitor monitor, String errorMessage, boolean condition) {
		try {
			Assert.assertFalse(errorMessage, condition);
		} catch (AssertionError e) {
			monitor.addException(e);
			logger.error(e.getMessage());
		}

	}

	public static void verifyEquals(FailureMonitor monitor, String errorMessage, Object expected, Object actual) {
		try {
			Assert.assertEquals(errorMessage, expected, actual);
		} catch (AssertionError e) {
			monitor.addException(e);
			logger.error(e.getMessage());
		}
	}

	public static void verifyNotNull(FailureMonitor monitor, String errorMessage, Object object) {
		try {
			Assert.assertNotNull(errorMessage, object);
		} catch (AssertionError e) {
			monitor.addException(e);
			logger.error(e.getMessage());
		}

	}

	public static void verifyNull(FailureMonitor monitor, String errorMessage, Object object) {
		try {
			Assert.assertNull(errorMessage, object);
		} catch (AssertionError e) {
			monitor.addException(e);
			logger.error(e.getMessage());
		}

	}

}
