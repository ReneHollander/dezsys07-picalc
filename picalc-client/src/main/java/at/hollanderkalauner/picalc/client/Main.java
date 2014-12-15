package at.hollanderkalauner.picalc.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;

/**
 * Main class of Client
 *
 * @author Paul Kalauner
 * @version 20141213.1
 */
public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    /**
     * Entry point of the program
     *
     * @param args passed CLI arguments
     */
    public static void main(String[] args) {
        ClientCLIParser clp = new ClientCLIParser();
        if (!clp.checkArgs(args)) {
            System.exit(0);
        }

        Client c = null;
        try {
            c = new Client();
            c.start(clp.getHost(), clp.getPort());
        } catch (Exception e) {
            LOG.error("Error occurred while initializing Client: " + e);
            System.exit(1);
        }

        BigDecimal pi = null;
        try {
            pi = c.calc(clp.getDecimalPlaces());
        } catch (RemoteException e) {
            LOG.error("Error calculating pi", e);
        }
        if (pi != null)
            System.out.println(pi);
    }
}
