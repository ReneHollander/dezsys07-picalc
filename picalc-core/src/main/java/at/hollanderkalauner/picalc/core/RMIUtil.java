package at.hollanderkalauner.picalc.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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

    public static void setHostname() {
        try {
            System.setProperty("java.rmi.server.hostname", RMIUtil.getIP().getHostAddress());
        } catch (Exception e) {
            LOG.error("Error setting local ip", e);
            System.exit(1);
        }
    }

    public static InetAddress getIP() throws SocketException {
        Enumeration ifs = null;
        ifs = NetworkInterface.getNetworkInterfaces();

        // Iterate all interfaces
        while (ifs.hasMoreElements()) {
            NetworkInterface iface = (NetworkInterface) ifs.nextElement();

            // Fetch all IP addresses on this interface
            Enumeration ips = iface.getInetAddresses();

            // Iterate the IP addresses
            while (ips.hasMoreElements()) {
                InetAddress ip = (InetAddress) ips.nextElement();
                if ((ip instanceof Inet4Address) && !ip.isLoopbackAddress()) {
                    return (InetAddress) ip;
                }
            }
        }
        throw new RuntimeException("No valid ip found!");
    }

}
