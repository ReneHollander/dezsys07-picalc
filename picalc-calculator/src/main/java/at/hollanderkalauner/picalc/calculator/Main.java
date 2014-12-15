package at.hollanderkalauner.picalc.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        CalculatorCLIParser ccp = new CalculatorCLIParser();
        if (!ccp.checkArgs(args)) {
            System.exit(0);
        }

        try {
            if (ccp.getMode() == CalculatorCLIParser.Mode.STANDALONE) {
                StandaloneCalculatorService scs = new StandaloneCalculatorService(ccp.getCalcBehav());
                scs.start(ccp.getPort());
            } else {
                BehindBalancerCalculatorService bcs = new BehindBalancerCalculatorService(ccp.getHost(), ccp.getPort());
                bcs.start(ccp.getPort());
            }
        } catch (Exception e) {
            LOG.error("Error occurred while initializing Balancer: " + e);
            System.exit(1);
        }
    }
}
