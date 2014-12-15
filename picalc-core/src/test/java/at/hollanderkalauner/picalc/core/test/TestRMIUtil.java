package at.hollanderkalauner.picalc.core.test;

import at.hollanderkalauner.picalc.core.RMIUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing RMIUtil
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class TestRMIUtil {

    /**
     * Test setting up the policy file
     */
    @Test
    public void testSetupPolicy() {
        RMIUtil.setupPolicy();
        assertEquals(System.getProperty("java.security.policy"), System.class.getResource("/policy/java.policy").toString());
    }
}
