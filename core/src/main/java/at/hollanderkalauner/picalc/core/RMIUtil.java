package at.hollanderkalauner.picalc.core;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

    public static void setupRegistry() throws RemoteException {
        LocateRegistry.createRegistry(1099);
        addCodeBaseFor(Calculator.class);
    }

    private static void addCodeBaseFor(Class<?> clazz) {
        System.setProperty("java.rmi.server.codebase", clazz.getProtectionDomain().getCodeSource().getLocation().toString());
    }
}
