package at.hollanderkalauner.picalc.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

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
        Client c = null;
        try {
            // TODO get hostname and port from cli
            c = new Client(null, -1);
        } catch (Exception e) {
            LOG.error("Error occurred while initializing Client: " + e);
            System.exit(1);
        }
        ClientCLIParser clp = new ClientCLIParser(c);
        if (clp.checkArgs(args)) {
            BigDecimal pi = c.calc();
            if (pi != null)
                System.out.println(pi);
        }
    }
}
