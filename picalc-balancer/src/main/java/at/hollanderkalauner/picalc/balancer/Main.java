package at.hollanderkalauner.picalc.balancer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        BalancerCLIParser bcp = new BalancerCLIParser();
        if (!bcp.checkArgs(args)) {
            System.exit(0);
        }

        try {
            Balancer b = new Balancer(bcp.getCalcBehav());
            b.start(bcp.getPort());
        } catch (Exception e) {
            LOG.error("Error occurred while initializing Balancer: " + e);
            System.exit(1);
        }
    }
}
