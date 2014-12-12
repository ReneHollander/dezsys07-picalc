package at.hollanderkalauner.picalc.core;

/**
 * Created by Paul on 12.12.2014.
 */
public class RMIUtil {
    public static void setupPolicy() throws Exception {
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", System.class.getResource("/policy/java.policy").toString());
            System.setSecurityManager(new SecurityManager());
        }
    }
}
