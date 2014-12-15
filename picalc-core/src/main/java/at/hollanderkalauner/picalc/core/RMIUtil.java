package at.hollanderkalauner.picalc.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RMIUtil
 *
 * @author Rene Hollander
 * @author Paul Kalauner
 * @version 20141212.1
 */
public class RMIUtil {
    private static final Logger LOG = LogManager.getLogger(RMIUtil.class);

    /**
     * Sets up the policy
     */
    public static void setupPolicy() {
        LOG.info("Setting up Policy");
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", System.class.getResource("/policy/java.policy").toString());
            System.setSecurityManager(new SecurityManager());
        }
    }

}
