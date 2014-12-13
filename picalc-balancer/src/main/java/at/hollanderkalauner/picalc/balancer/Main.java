package at.hollanderkalauner.picalc.balancer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;

/**
 * Main Class for the Balancer
 *
 * @author Paul Kalauner
 * @version 201411213.1
 */
public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    /**
     * Entry point of the program
     *
     * @param args passed CLI arguments
     */
    public static void main(String[] args) {
        try {
            new BalancerCLIParser(new Balancer()).start(args);
        } catch (RemoteException e) {
            LOG.error("Could not start Balancer", e);
        }
    }
}
