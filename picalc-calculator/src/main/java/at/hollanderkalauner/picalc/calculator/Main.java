package at.hollanderkalauner.picalc.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;

/**
 * Main class of CalculatorService
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
        try {
            new CalculatorCLIParser(new CalculatorService()).start(args);
        } catch (RemoteException e) {
            LOG.error("Error while initializing CalculatorService: " + e.getMessage());
        }
    }
}
